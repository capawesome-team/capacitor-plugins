/// <reference types="@capawesome/capacitor-posthog" />
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.plugin',
  appName: 'example',
  webDir: 'dist',
  plugins: {
    Posthog: {
      apiKey: 'phc_1234567890abcdef1234567890abcdef1234567890',
      host: 'https://us.i.posthog.com',
    },
  },
};

export default config;
