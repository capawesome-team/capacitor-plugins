#!/usr/bin/env bash
# Inspect or manipulate the example app's plugin storage on a device.
# Usage: device.sh <ios|android> <inspect|bundles|files|plant-leftover|force-stop> [simulator udid | adb serial]
# Fails loudly when the device or the app is not reachable; only a missing directory is reported as absent.
set -euo pipefail
platform=$1; action=$2; device=${3:-}
app=com.example.plugin
downloads=capawesome_capacitor_live_update_downloads

title_of() { grep -o '<ion-title>[^<]*</ion-title>' | head -1; }

if [ "$platform" = ios ]; then
  container=$(xcrun simctl get_app_container "${device:-booted}" "$app" data)
  bundles="$container/Library/NoCloud/ionic_built_snapshots"
  cache="$container/Library/Caches"
  case $action in
    bundles) [ -d "$bundles" ] && ls "$bundles" || true ;;
    files)
      [ -d "$bundles" ] || { echo "no bundles installed"; exit 0; }
      for b in "$bundles"/*/; do echo "== $(basename "$b") | title: $(title_of < "$b/index.html" 2>/dev/null || echo none)"; (cd "$b" && find . -type f | sort | sed 's#^\./#  #'); done ;;
    inspect)
      echo "bundles: $([ -d "$bundles" ] && ls "$bundles" | tr '\n' ' ')"
      if [ -d "$cache/$downloads" ]; then echo "downloads dir: [$(ls "$cache/$downloads" | tr '\n' ' ')] (empty = clean)"; else echo "downloads dir: absent (clean)"; fi
      echo "stray zips in cache root: $(ls "$cache" | grep -c '\.zip$' || true)" ;;
    plant-leftover) mkdir -p "$cache/$downloads/leftover" && echo x > "$cache/$downloads/leftover/bundle.zip" && echo planted ;;
    force-stop) xcrun simctl terminate "${device:-booted}" "$app" ;;
    *) echo "unknown action: $action" >&2; exit 2 ;;
  esac
else
  adb=(adb ${device:+-s "$device"})
  "${adb[@]}" shell run-as "$app" true >/dev/null 2>&1 || { echo "cannot run-as $app on ${device:-the connected device}: is the app installed and the device online?" >&2; exit 1; }
  ra() { "${adb[@]}" shell run-as "$app" "$@"; }
  bundles=files/_capacitor_live_update_bundles
  case $action in
    bundles) ra test -d $bundles && ra ls $bundles || true ;;
    files)
      ra test -d $bundles || { echo "no bundles installed"; exit 0; }
      for b in $(ra ls $bundles | tr -d '\r'); do echo "== $b | title: $(ra cat $bundles/$b/index.html 2>/dev/null | title_of || echo none)"; ra find $bundles/$b -type f | tr -d '\r' | sed "s#^$bundles/$b/#  #" | sort; done ;;
    inspect)
      echo "bundles: $(ra test -d $bundles && ra ls $bundles | tr '\n' ' ')"
      if ra test -d cache/$downloads; then echo "downloads dir: [$(ra ls cache/$downloads | tr '\n' ' ')] (empty = clean)"; else echo "downloads dir: absent (clean)"; fi
      echo "cache root: $(ra ls cache | tr '\n' ' ')"
      echo "serverBasePath: $(ra cat shared_prefs/CapWebViewSettings.xml 2>/dev/null | grep -o 'serverBasePath[^<]*' | head -1 || true)" ;;
    plant-leftover) ra mkdir -p cache/$downloads/leftover; ra touch cache/$downloads/leftover/bundle.zip; echo planted ;;
    force-stop) "${adb[@]}" shell am force-stop "$app" ;;
    *) echo "unknown action: $action" >&2; exit 2 ;;
  esac
fi
