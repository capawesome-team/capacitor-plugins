---
'@capawesome/capacitor-posthog': patch
---

**feat: add generic configuration support to setup method**: Added optional `config` parameter to `SetupOptions` interface allowing developers to pass custom PostHog SDK configuration options during initialization. This enables fine-grained control over PostHog behavior such as `autocapture`, `enable_recording_console_log`, and other SDK-specific settings across all platforms (Web, Android, iOS).
