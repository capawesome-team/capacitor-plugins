import type { 
  CameraOverlayOptions, 
  CameraOverlayResult, 
  CameraOverlayButtons,
} from './definitions';
import { OverlayManager } from './overlay-manager';
import { CameraMultiCapture } from './registration';
import { CameraOverlayUIOptions, ButtonsConfig } from './types/ui-types';

// Helper function to convert CameraOverlayButtons to ButtonsConfig
function convertButtonsConfig(buttons?: CameraOverlayButtons): Partial<ButtonsConfig> | undefined {
  if (!buttons) return undefined;
  
  return {
    capture: buttons.capture,
    done: buttons.done,
    cancel: buttons.cancel,
    switchCamera: buttons.switchCamera,
    zoom: buttons.zoom,
  };
}

export const initialize = (options: CameraOverlayOptions): Promise<CameraOverlayResult> => {
  const plugin = CameraMultiCapture;
  // Create an overlay manager with the provided options
  // Map the public API options to internal UI options
  const uiOptions: CameraOverlayUIOptions = {
    containerId: options.containerId,
    quality: options.quality,
    thumbnailStyle: options.thumbnailStyle,
    buttons: convertButtonsConfig(options.buttons)
  };
  
  const overlayManager = new OverlayManager(plugin, uiOptions);
  
  return overlayManager.setup();
}
