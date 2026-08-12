#!/usr/bin/env node
import { readdirSync } from 'node:fs';
import { spawnSync } from 'node:child_process';

const dir = '.maestro';
const MAX_ATTEMPTS = 3;
const flows = readdirSync(dir)
  .filter((f) => f.endsWith('.yaml'))
  .sort();

if (flows.length === 0) {
  console.error(`No Maestro flows found in ${dir}`);
  process.exit(1);
}

for (const flow of flows) {
  for (let attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
    console.log(`\n=== Running ${flow} (attempt ${attempt}/${MAX_ATTEMPTS}) ===\n`);
    const result = spawnSync('maestro', ['test', `${dir}/${flow}`], { stdio: 'inherit' });
    if (result.status === 0) {
      break;
    }
    if (attempt === MAX_ATTEMPTS) {
      process.exit(result.status ?? 1);
    }
    console.log(`\n=== ${flow} failed, retrying... ===\n`);
  }
}
