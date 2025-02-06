import { registerPlugin } from "@capacitor/core";

import type { EdgeToEdgePlugin } from "./definitions";

const EdgeToEdge = registerPlugin<EdgeToEdgePlugin>("EdgeToEdge", {
  web: () => import("./web").then((m) => new m.EdgeToEdgeWeb()),
});

export * from "./definitions";
export { EdgeToEdge };
