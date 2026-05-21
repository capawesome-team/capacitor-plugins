import { registerPlugin } from "@capacitor/core";

import type { GrafanaFaroPlugin } from "./definitions";

const GrafanaFaro = registerPlugin<GrafanaFaroPlugin>("GrafanaFaro", {
  web: () => import("./web").then((m) => new m.GrafanaFaroWeb()),
});

export * from "./definitions";
export { GrafanaFaro };
