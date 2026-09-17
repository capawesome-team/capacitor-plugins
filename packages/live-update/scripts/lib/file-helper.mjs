/* eslint-disable */
import * as fs from 'fs';
import * as path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);

/**
 * Asserts that the given file contains the given expected value.
 */
export async function assertInFile(filePath, searchValue, expectedValue) {
  const data = fs.readFileSync(filePath, 'utf8');
  const actualValue = data.match(searchValue)?.[0] ?? 'no match';
  if (actualValue !== expectedValue) {
    throw new Error(
      `Expected \`${expectedValue}\` in ${filePath}, but found \`${actualValue}\`. Run \`npm run version\` and commit the result.`,
    );
  }
}

/**
 * Joins the given parts into a path relative to the root of the project.
 */
export function joinPath(...parts) {
  return path.join(__filename, '../', '../', '../', ...parts);
}

/**
 * Replaces the given search value with the given replace value in the given file.
 */
export async function replaceInFile(filePath, searchValue, replaceValue) {
  let data = fs.readFileSync(filePath, 'utf8');
  data = data.replace(searchValue, replaceValue);
  fs.writeFileSync(filePath, data, 'utf8');
}
