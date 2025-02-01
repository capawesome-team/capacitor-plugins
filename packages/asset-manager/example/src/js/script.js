import { Directory, Filesystem } from '@capacitor/filesystem';
import { AssetManager } from '@capawesome/capacitor-asset-manager';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#copy').addEventListener('click', async () => {
    const { uri } = await Filesystem.getUri({
      directory: Directory.Cache,
      path: 'capacitor.config.json',
    });
    await AssetManager.copy({
      from: 'capacitor.config.json',
      to: uri,
    });
    await Filesystem.readdir({
      directory: Directory.Cache,
      path: '/',
    });
  });
  document.querySelector('#list').addEventListener('click', async () => {
    await AssetManager.list();
  });
  document.querySelector('#read').addEventListener('click', async () => {
    await AssetManager.read({
      path: 'capacitor.config.json',
      encoding: 'utf8',
    });
  });
});
