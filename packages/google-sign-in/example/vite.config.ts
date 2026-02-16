import { defineConfig } from 'vite';

export default defineConfig({
  root: './src',
  build: {
    outDir: '../dist',
    minify: false,
    emptyOutDir: true,
  },
  server: {
    allowedHosts: ['want-convertible-awards-machinery.trycloudflare.com'],
  },
});
