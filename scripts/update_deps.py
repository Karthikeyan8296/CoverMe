import sys
import re
import subprocess
import tomlkit

def get_dependency_updates(update_type, dependency_name=""):
    result = subprocess.run(
        ["./gradlew", "dependencyDoctor"],
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        text=True,
        encoding="utf-8"
    )
    output = result.stdout
    print("=== dependencyDoctor output ===")
    print(output)
    print("=== end output ===")

    updates = {}
    current_section = None
    last_dep_name = None
    last_dep_group = None

    for line in output.splitlines():
        line = line.strip()

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

        dep_name_match = re.search(r'\[[\w]+\]\s+([\w.\-]+):([\w.\-]+)', line)
        if dep_name_match:
            last_dep_group = dep_name_match.group(1)
            last_dep_name = dep_name_match.group(2)
            continue

        # ✅ handle both → (unicode) and -> (ascii) just in case
        version_match = re.search(r'([\d.]+(?:-[\w.]+)?)\s+(?:→|->)\s+([\d.]+(?:-[\w.]+)?)', line)
        if version_match and last_dep_name and current_section == update_type:
            current_ver = version_match.group(1)
            latest_ver = version_match.group(2)

            if dependency_name and dependency_name.lower() not in last_dep_name.lower():
                last_dep_name = None
                continue

            updates[last_dep_name] = {
                "current": current_ver,
                "latest": latest_ver,
                "group": last_dep_group
            }
            last_dep_name = None

    return updates


def update_toml(updates):
    with open("gradle/libs.versions.toml", "r") as f:
        doc = tomlkit.load(f)

    versions = doc["versions"]
    changed = []

    for dep_name, data in updates.items():
        # try all name variants
        for key in versions:
            if key.lower().replace("-","") == dep_name.lower().replace("-",""):
                versions[key] = data["latest"]
                changed.append(f"{key}: {data['current']} → {data['latest']}")
                break

    with open("gradle/libs.versions.toml", "w") as f:
        tomlkit.dump(doc, f)

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