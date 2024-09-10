/* eslint-disable */
import { readFile } from 'fs/promises';

import { execute } from './lib/cli.mjs';
import { joinPath, replaceInFile } from './lib/file-helper.mjs';

execute(async () => {
  const packageJson = await readFile('./package.json')
    .then((json) => JSON.parse(json.toString()))
    .catch(() => null);
  const version = packageJson?.version;
  if (!version) {
    throw new Error('Could not read version from package.json');
  }
  // Replace version in Android plugin
  await replaceInFile(
    joinPath(
      'android',
      'src',
      'main',
      'java',
      'io',
      'capawesome',
      'capacitorjs',
      'plugins',
      'liveupdate',
      'LiveUpdatePlugin.java'
    ),
    /public static final String VERSION = "(\d+\.\d+\.\d+)"/,
    'public static final String VERSION = "' + version + '"'
  );
  // Replace version in iOS plugin
  await replaceInFile(
    joinPath('ios', 'Plugin', 'LiveUpdatePlugin.swift'),
    /public static let version = "(\d+\.\d+\.\d+)"/,
    'public static let version = "' + version + '"'
  );
});
