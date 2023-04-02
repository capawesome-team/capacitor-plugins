import * as fs from "fs";
import { execute } from "./lib/cli.mjs";
import { exec } from "./lib/subprocess.mjs";

execute(async () => {
  const packagesParam = process.argv.find((arg) =>
    arg.startsWith("--packages="),
  );
  if (!packagesParam) {
    console.error("ERR: --packages parameter is missing.");
    process.exit(1);
  }

  const packagesParamValue = packagesParam.split("=")[1];
  const packages = JSON.parse(packagesParamValue);
  const packagesToPublish = packages.filter((pkg) => {
    const dirName = pkg.name.split("/")[1];
    return (
      pkg.name.startsWith("@capawesome-team/") &&
      !pkg.version.includes("-") &&
      fs.existsSync(`./packages/${dirName}/package.json`)
    );
  });

  for (const pkg of packagesToPublish) {
    const dirName = pkg.name.split("/")[1];
    console.log(`Publishing ${pkg.name}@${pkg.version}...`);
    await exec(
      `npm pkg set repository.url=git+https://github.com/capawesome-team/sponsorware.git`,
      {
        cwd: `./packages/${dirName}`,
      },
    );
    await exec(
      `npm pkg set publishConfig.registry=https://npm.pkg.github.com`,
      { cwd: `./packages/${dirName}` },
    );
    if (process.env.CI) {
      await exec(`npm publish`, { cwd: `./packages/${dirName}` });
    }
  }
  console.log(`${packagesToPublish.length} package(s) published.`);
});
