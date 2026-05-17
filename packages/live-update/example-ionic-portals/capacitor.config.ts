/// <reference types="@capawesome/capacitor-live-update" />
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.plugin',
  appName: 'example-ionic-portals',
  webDir: 'dist',
  plugins: {
    LiveUpdate: {
      // Pre-fill with a fallback Capawesome Cloud app ID. The test UI lets you
      // override this per-manager via the `appId` config field.
      appId: '46d641f5-2703-4e99-b498-006192c70484',
    },
  },
};

export default config;
