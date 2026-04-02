import sys
import re
import subprocess

def get_dependency_updates(update_type, dependency_name=""):
    """Run dependencyDoctor and parse its output"""
    result = subprocess.run(
        ["./gradlew", "dependencyDoctor"],
        capture_output=True, text=True
    )
    output = result.stdout

    updates = {}
    current_section = None

    for line in output.splitlines():
        if "Major Updates" in line:
            current_section = "major"
        elif "Minor Updates" in line:
            current_section = "minor"
        elif "Patch Updates" in line:
            current_section = "patch"
        elif "→" in line and current_section == update_type:
            # line looks like: [app] com.squareup.moshi:moshi    1.15.0 → 1.15.2
            match = re.search(r'([\w.\-]+):([\w.\-]+)\s+([\d.]+)\s+→\s+([\d.]+)', line)
            if match:
                name = match.group(2)      # e.g. moshi
                current = match.group(3)   # e.g. 1.15.0
                latest = match.group(4)    # e.g. 1.15.2

                # if specific dep requested, filter
                if dependency_name and dependency_name.lower() not in name.lower():
                    continue

                updates[name] = {"current": current, "latest": latest}

    return updates


def update_toml(updates):
    """Update libs.versions.toml with new versions"""
    toml_path = "gradle/libs.versions.toml"

    with open(toml_path, "r") as f:
        content = f.read()

    changed = []

    for dep_name, versions in updates.items():
        current = versions["current"]
        latest = versions["latest"]

        # find any key in [versions] section that contains this dep name
        # e.g. moshi = "1.15.0"  →  moshi = "1.15.2"
        pattern = rf'({re.escape(dep_name)}\s*=\s*")[^"]+(")'
        new_content = re.sub(pattern, rf'\g<1>{latest}\g<2>', content, flags=re.IGNORECASE)

        if new_content != content:
            content = new_content
            changed.append(f"{dep_name}: {current} → {latest}")
            print(f"✅ Updated {dep_name}: {current} → {latest}")
        else:
            print(f"⚠️  Could not find version key for {dep_name} in toml")

    with open(toml_path, "w") as f:
        f.write(content)

    return changed


if __name__ == "__main__":
    update_type = sys.argv[1]                          # patch / minor / major
    dependency_name = sys.argv[2] if len(sys.argv) > 2 else ""

    print(f"🔍 Finding {update_type} updates{f' for {dependency_name}' if dependency_name else ''}...")

    updates = get_dependency_updates(update_type, dependency_name)

    if not updates:
        print("No updates found.")
        sys.exit(0)

    changed = update_toml(updates)

    # write changed list to file so workflow can read it
    with open("changed_deps.txt", "w") as f:
        f.write("\n".join(changed))

    print(f"\n✅ {len(changed)} dependencies updated!")