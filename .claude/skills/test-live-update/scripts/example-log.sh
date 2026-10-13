#!/usr/bin/env bash
# Print the log entries shown by example-ionic-provider (lines like "[12:00:00] syncManager → ok").
# Usage: example-log.sh <device id>
set -euo pipefail
maestro --device "$1" hierarchy 2>/dev/null | python3 -I -c '
import json, re, sys
entries = []
def walk(node):
    attributes = node.get("attributes", {})
    for key in ("text", "accessibilityText"):
        value = attributes.get(key, "")
        if re.match(r"\[\d\d:\d\d:\d\d\] ", value) and value not in entries:
            entries.append(value)
    for child in node.get("children", []):
        walk(child)
walk(json.load(sys.stdin))
print("\n".join(entries) if entries else "no log entries")
'
