import type { PluginsConfig } from '@capacitor/cli';

/**
 * Structural subset of the `@capawesome/capacitor-electron` plugin contract.
 *
 * The platform's contract is the static plugin marker property, so no
 * build-time dependency on the platform package is required.
 */
export interface BundlesService {
  /**
   * Absolute path of the currently active web bundle directory, or `null`
   * when the packaged app bundle is active.
   */
  getActiveBundlePath(): string | null;
  /**
   * Repoint the serving protocol to the given bundle directory and reload
   * all app windows. Pass `null` to revert to the packaged app bundle.
   *
   * Pass `{ bootWatchdog: false }` to opt out of the platform's failed-boot
   * rollback watchdog for this activation. The live update engine owns
   * rollback, so the adapter always opts out.
   */
  setActiveBundle(
    bundleDirectory: string | null,
    options?: { bootWatchdog?: boolean },
  ): Promise<void>;
}

export interface ElectronPluginContext {
  config: {
    plugins?: PluginsConfig;
    [key: string]: unknown;
  };
  notifyListeners: (eventName: string, data?: unknown) => void;
  services: {
    bundles: BundlesService;
  };
}
