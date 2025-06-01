import type { 
  ButtonStyle as OriginalButtonStyle, 
  ThumbnailStyle as PublicThumbnailStyle
} from '../definitions';

/**
 * Defines the style properties for camera buttons
 */
export type ButtonStyle = OriginalButtonStyle;

/**
 * Defines the position options for UI elements
 */
export type UIPosition = 'topLeft' | 'topRight' | 'center' | 'left' | 'right' | 'custom';

/**
 * Base interface for all camera buttons
 */
export interface BaseButtonConfig {
  style?: ButtonStyle;
  position?: UIPosition;
}

/**
 * Configuration for buttons with icons
 */
export interface IconButtonConfig extends BaseButtonConfig {
  icon?: string;
  text?: string;
}

/**
 * Configuration for toggle buttons (like flash when added in future)
 */
export interface ToggleButtonConfig extends BaseButtonConfig {
  onIcon?: string;
  offIcon?: string;
}

/**
 * Configuration for zoom controls
 */
export interface ZoomButtonConfig extends IconButtonConfig {
  levels?: number[];
}

/**
 * Configuration for all UI buttons
 */
export interface ButtonsConfig {
  capture: IconButtonConfig;
  done: IconButtonConfig;
  cancel: IconButtonConfig;
  switchCamera?: IconButtonConfig;
  zoom?: ZoomButtonConfig;
}

/**
 * Configuration for thumbnail display - extends the public API version with additional style properties
 */
export interface ThumbnailStyle extends PublicThumbnailStyle {
  borderRadius?: string;
  margin?: string;
}


/**
 * Camera overlay options
 */
export interface CameraOverlayUIOptions {
  containerId: string;
  buttons?: Partial<ButtonsConfig>;
  thumbnailStyle?: ThumbnailStyle;
  quality?: number;
}
