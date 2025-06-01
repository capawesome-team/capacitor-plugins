export interface ThumbnailStyle {
  width?: string;
  height?: string;
}

export type CameraDirection = 'front' | 'back';

export type CaptureMode = 'minimizeLatency' | 'maxQuality';

export interface Resolution {
  width: number;
  height: number;
}

export interface ButtonStyle {
  radius?: number;
  color?: string;
  backgroundColor?: string;
  padding?: string;
  size?: number;
  activeColor?: string;
  border?: string;
}

export interface CameraOverlayButtons {
  capture?: {
    icon?: string;
    style?: ButtonStyle;
    position?: 'center' | 'left' | 'right' | 'custom';
  };
  done?: {
    icon?: string;
    style?: ButtonStyle;
    text?: string;
  };
  cancel?: {
    icon?: string;
    style?: ButtonStyle;
    text?: string;
  };
  switchCamera?: {
    icon?: string;
    style?: ButtonStyle;
    position?: 'topLeft' | 'topRight' | 'custom';
  };
  zoom?: {
    icon?: string;
    style?: ButtonStyle;
    levels?: number[];
  };
}

export interface CameraPreviewRect {
  width?: number;
  height?: number;
  x?: number;
  y?: number;
}

export interface CameraOverlayOptions {
  buttons?: CameraOverlayButtons;
  thumbnailStyle?: { width: string; height: string };
  quality?: number;
  containerId: string;
  previewRect?: CameraPreviewRect;
  direction?: CameraDirection;
  captureMode?: CaptureMode;
  resolution?: Resolution;
  zoom?: number;
  autoFocus?: boolean;
}


/**
 * Interface for captured images
 */
export interface CapturedImage {
  id: string;
  data: CameraImageData;
}

/**
 * Structure for image data returned by the camera
 */
export interface CameraImageData {
  uri: string;
  base64: string;
}

export interface CameraOverlayResult {
  images: CameraImageData[];
  cancelled: boolean;
}

export interface CameraMultiCapturePlugin {
  /**
   * Starts the camera overlay session.
   */
  start(options?: CameraOverlayOptions): Promise<CameraOverlayResult>;

  /**
   * Captures a single frame.
   */
  capture(): Promise<{ value: CameraImageData }>;

  /**
   * Stops and tears down the camera session.
   */
  stop(): Promise<void>;

  /**
   * Switches the camera between front and back.
   */
  switchCamera(): Promise<void>;

  /**
   * Sets the zoom level of the camera.
   */
  setZoom(options: { zoom: number }): Promise<void>;
}
