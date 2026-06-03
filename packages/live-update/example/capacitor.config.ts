/// <reference types="@capawesome/capacitor-live-update" />
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.plugin',
  appName: 'example',
  webDir: 'dist',
  plugins: {
    LiveUpdate: {
      appId: '5fc57c46-4b64-4209-b924-14fa960ccc88',
      // publicKey:
      //   '-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtGTM5t++b2W9u8P7H4x1SGZPfeHyrhkxAIItxizIN9MSlRAdrnAreQVVj76GDdmWe/oKPnDUB/PR0yj2mzK8TqZhUxgh3SMhkxxLRWuicKiiOn9vNL5lHQdQotuh4REsoIwdIdVHQ5km73853NDk33xGb3qrQ7AJXCl3s0YRVfCqJd5Q2HWB9FEWm6Ojmryqwibz3S6lF2sjPqpif0XI2YBlDpjR/BTNKn351DgnxzXwAAW8uJV823DlOlcamGd3yNlv5wf3HMwZCkXPeYmAXs0F+QtrAOtUToURvXHnWX13s9/nx6wAwRPTzpWvPTo+rk8qqwuXJPbkN3F8J8j2twIDAQAB-----END PUBLIC KEY-----',
      // readyTimeout: 10000,
    },
  },
};

export default config;
