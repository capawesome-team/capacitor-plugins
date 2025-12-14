/**
 * Extend the ScreenOrientation interface to include lock() and unlock() methods.
 * These methods are part of the Screen Orientation API but were removed from
 * TypeScript 5.x DOM type definitions.
 */
type OrientationLockType = "any" | "landscape" | "landscape-primary" | "landscape-secondary" | "natural" | "portrait" | "portrait-primary" | "portrait-secondary";

interface ScreenOrientation extends EventTarget {
  lock(orientation: OrientationLockType): Promise<void>;
}
