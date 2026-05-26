#!/usr/bin/env node
import { readdirSync } from 'node:fs';
import { spawnSync } from 'node:child_process';

const dir = '.maestro';
const flows = readdirSync(dir)
  .filter((f) => f.endsWith('.yaml'))
  .sort();

if (flows.length === 0) {
  console.error(`No Maestro flows found in ${dir}`);
  process.exit(1);
}

for (const flow of flows) {
  console.log(`\n=== Running ${flow} ===\n`);
  const result = spawnSync('maestro', ['test', `${dir}/${flow}`], { stdio: 'inherit' });
  if (result.status !== 0) {
    process.exit(result.status ?? 1);
  }
}
