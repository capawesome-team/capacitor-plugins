#!/usr/bin/env bash
# Inspect or manipulate the example app's plugin storage on a device.
# Usage: device.sh <ios|android> <inspect|bundles|plant-leftover|force-stop> [simulator udid | adb serial]
set -euo pipefail
platform=$1; action=$2; device=${3:-}
app=com.example.plugin
downloads=capawesome_capacitor_live_update_downloads

if [ "$platform" = ios ]; then
  container=$(xcrun simctl get_app_container "${device:-booted}" "$app" data)
  bundles="$container/Library/NoCloud/ionic_built_snapshots"
  cache="$container/Library/Caches"
  case $action in
    bundles) ls "$bundles" 2>/dev/null || true ;;
    inspect)
      echo "bundles: $(ls "$bundles" 2>/dev/null | tr '\n' ' ')"
      echo "downloads dir: [$(ls "$cache/$downloads" 2>/dev/null | tr '\n' ' ')] (absent or empty = clean)"
      echo "stray zips in cache root: $(ls "$cache" | grep -c '\.zip$' || true)" ;;
    plant-leftover) mkdir -p "$cache/$downloads/leftover" && echo x > "$cache/$downloads/leftover/bundle.zip" && echo planted ;;
    force-stop) xcrun simctl terminate "${device:-booted}" "$app" ;;
  esac
else
  adb=(adb ${device:+-s "$device"})
  ra() { "${adb[@]}" shell run-as "$app" "$@" 2>/dev/null || true; }
  case $action in
    bundles) ra ls files/_capacitor_live_update_bundles ;;
    inspect)
      echo "bundles: $(ra ls files/_capacitor_live_update_bundles | tr '\n' ' ')"
      echo "downloads dir: [$(ra ls cache/$downloads | tr '\n' ' ')] (absent or empty = clean)"
      echo "cache root: $(ra ls cache | tr '\n' ' ')"
      echo "serverBasePath: $(ra cat shared_prefs/CapWebViewSettings.xml | grep -o 'serverBasePath[^<]*' | head -1)" ;;
    plant-leftover) ra mkdir -p cache/$downloads/leftover; ra touch cache/$downloads/leftover/bundle.zip; echo planted ;;
    force-stop) "${adb[@]}" shell am force-stop "$app" ;;
  esac
fi
