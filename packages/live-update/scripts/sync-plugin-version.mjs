/* eslint-disable */
import { readFile } from 'fs/promises';

import { execute } from './lib/cli.mjs';
import { assertInFile, joinPath, replaceInFile } from './lib/file-helper.mjs';

execute(async () => {
  const packageJson = await readFile('./package.json')
    .then((json) => JSON.parse(json.toString()))
    .catch(() => null);
  const version = packageJson?.version;
  if (!version) {
    throw new Error('Could not read version from package.json');
  }
  const targets = [
    {
      path: joinPath(
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
      pattern: /public static final String VERSION = "(\d+\.\d+\.\d+)"/,
      replacement: 'public static final String VERSION = "' + version + '"',
    },
    {
      path: joinPath('ios', 'Plugin', 'LiveUpdatePlugin.swift'),
      pattern: /public static let version = "(\d+\.\d+\.\d+)"/,
      replacement: 'public static let version = "' + version + '"',
    },
  ];
  // Verify the version in the native plugins instead of updating it
  const check = process.argv.includes('--check');
  for (const target of targets) {
    await (check ? assertInFile : replaceInFile)(target.path, target.pattern, target.replacement);
  }
});
