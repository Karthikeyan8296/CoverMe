import sys
import re
import subprocess

def get_dependency_updates(update_type, dependency_name=""):
    result = subprocess.run(
        ["./gradlew", "dependencyDoctor"],
        capture_output=True, text=True
    )
    output = result.stdout
    print("=== dependencyDoctor output ===")
    print(output)
    print("=== end output ===")

    updates = {}
    current_section = None
    last_dep_name = None      # ✅ track dep name from previous line
    last_dep_group = None

    for line in output.splitlines():
        line = line.strip()

        # detect section headers
        if "Major Updates" in line:
            current_section = "major"
            continue
        elif "Minor Updates" in line:
            current_section = "minor"
            continue
        elif "Patch Updates" in line:
            current_section = "patch"
            continue
        elif "Summary" in line:
            current_section = None
            continue

        # ✅ line 1: catch the dep name line e.g. "[app] com.squareup.moshi:moshi"
        dep_name_match = re.search(r'\[[\w]+\]\s+([\w.\-]+):([\w.\-]+)', line)
        if dep_name_match:
            last_dep_group = dep_name_match.group(1)
            last_dep_name = dep_name_match.group(2)
            continue

        # ✅ line 2: catch the version line e.g. "1.15.0 → 1.15.2"
        version_match = re.search(r'([\d.]+)\s+→\s+([\d.]+)', line)
        if version_match and last_dep_name and current_section == update_type:
            current_ver = version_match.group(1)
            latest_ver = version_match.group(2)

            # filter by specific dep name if requested
            if dependency_name and dependency_name.lower() not in last_dep_name.lower():
                last_dep_name = None
                continue

            updates[last_dep_name] = {
                "current": current_ver,
                "latest": latest_ver,
                "group": last_dep_group
            }
            last_dep_name = None  # reset after consuming

    return updates


def update_toml(updates):
    toml_path = "gradle/libs.versions.toml"

    with open(toml_path, "r") as f:
        content = f.read()

    changed = []

    for dep_name, versions in updates.items():
        current = versions["current"]
        latest = versions["latest"]

        # ✅ ^ anchors to start of line, \b is word boundary
        # so "moshi" only matches "moshi = ..." NOT "converterMoshi = ..."
        pattern = rf'(^{re.escape(dep_name)}\s*=\s*")[^"]+(")'
        new_content = re.sub(
            pattern,
            rf'\g<1>{latest}\g<2>',
            content,
            flags=re.IGNORECASE | re.MULTILINE  # ✅ MULTILINE makes ^ match each line start
        )

        if new_content != content:
            content = new_content
            changed.append(f"{dep_name}: {current} → {latest}")
            print(f"✅ Updated {dep_name}: {current} → {latest}")
        else:
            print(f"⚠️  Could not find version key for '{dep_name}' in toml")

    with open(toml_path, "w") as f:
        f.write(content)

    return changed


if __name__ == "__main__":
    update_type = sys.argv[1]
    dependency_name = sys.argv[2] if len(sys.argv) > 2 else ""

    print(f"🔍 Finding {update_type} updates{f' for {dependency_name}' if dependency_name else ''}...")

    updates = get_dependency_updates(update_type, dependency_name)
    print(f"Found {len(updates)} updates: {list(updates.keys())}")

    if not updates:
        print("No updates found.")
        sys.exit(0)

    changed = update_toml(updates)

    with open("changed_deps.txt", "w") as f:
        f.write("\n".join(changed))

    print(f"\n✅ {len(changed)} dependencies updated!")