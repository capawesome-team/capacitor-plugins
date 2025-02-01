import { registerPlugin } from "@capacitor/core";

import type { AssetManagerPlugin } from "./definitions";

const AssetManager = registerPlugin<AssetManagerPlugin>("AssetManager", {
  web: () => import("./web").then((m) => new m.AssetManagerWeb()),
});

export * from "./definitions";
export { AssetManager };
