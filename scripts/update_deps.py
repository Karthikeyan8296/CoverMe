import sys
import json
import tomlkit

def load_updates_from_json(json_path: str, update_type: str, dependency_name: str = "") -> dict:

    with open(json_path, "r", encoding="utf-8") as f:
        data = json.load(f)

    # data shape: { "branch": "...", "major": [...], "minor": [...], "patch": [...] }
    # each entry: { "project", "group", "name", "current", "latest", "url" }

    entries = data.get(update_type, [])

    updates = {}
    for entry in entries:
        dep_name = entry["name"]

        # if caller asked for a specific dep, skip everything else
        if dependency_name and dependency_name.lower() not in dep_name.lower():
            continue

        updates[dep_name] = {
            "current": entry["current"],
            "latest":  entry["latest"],
            "group":   entry["group"],
        }

    return updates


def update_toml(updates: dict) -> list[str]:
    with open("gradle/libs.versions.toml", "r", encoding="utf-8") as f:
        doc = tomlkit.load(f)

    versions = doc["versions"]
    changed = []

    for dep_name, data in updates.items():
        for key in versions:
            if key.lower().replace("-", "") == dep_name.lower().replace("-", ""):
                versions[key] = data["latest"]
                changed.append(f"{key}: {data['current']} → {data['latest']}")
                break

    with open("gradle/libs.versions.toml", "w", encoding="utf-8") as f:
        tomlkit.dump(doc, f)

    return changed


if __name__ == "__main__":
    update_type     = sys.argv[1]                          # patch / minor / major
    dependency_name = sys.argv[2] if len(sys.argv) > 2 else ""

    # JSON path passed as arg 3; defaults to where the plugin writes it
    # (the workflow downloads the artifact to this path)
    json_path = sys.argv[3] if len(sys.argv) > 3 else "dependency-updates/updates.json"

    print(f"🔍 Finding {update_type} updates{f' for {dependency_name}' if dependency_name else ''}...")
    print(f"📄 Reading from {json_path}")

    updates = load_updates_from_json(json_path, update_type, dependency_name)
    print(f"Found {len(updates)} updates: {list(updates.keys())}")

    if not updates:
        print("No updates found.")
        sys.exit(0)

    changed = update_toml(updates)

    with open("changed_deps.txt", "w", encoding="utf-8") as f:
        f.write("\n".join(changed))

    print(f"\n✅ {len(changed)} dependencies updated!")