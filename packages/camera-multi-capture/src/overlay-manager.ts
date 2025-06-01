/**
 * Overlay Manager - Main controller for camera overlay UI
 */
import type { CameraMultiCapturePlugin, CameraOverlayResult } from './definitions';
import { CameraController } from './controllers/camera-controller';
import { GalleryController } from './controllers/gallery-controller';
import { merge } from 'lodash';
import { defaultButtons } from './ui/default-styles';
import { createButton } from './ui/ui-factory';
import {
  createOverlayContainer,
  createPositionContainers,
  createBottomGridCells,
  createGallery
} from './ui/layout-manager';
import type {
  ButtonsConfig,
  CameraOverlayUIOptions,
} from './types/ui-types';

/**
 * Main class to manage camera overlay UI
 */
export class OverlayManager {
  private options: CameraOverlayUIOptions;
  private overlayElement: HTMLElement | null = null;
  private cameraController: CameraController;
  private galleryController: GalleryController | null = null;
  private isActive = false;
  private resolvePromise: ((value: CameraOverlayResult) => void) | null = null;
  private bodyBackgroundColor: string | null = null;

  constructor(plugin: CameraMultiCapturePlugin, options: CameraOverlayUIOptions) {
    this.options = options;
    this.cameraController = new CameraController(plugin);
  }

  /**
   * Shows the camera overlay and returns captured images
   */
  async setup(): Promise<CameraOverlayResult> {
    // Only allow one active overlay at a time
    if (this.isActive) {
      throw new Error('Camera overlay is already active');
    }

    this.isActive = true;

    return new Promise<CameraOverlayResult>(async (resolve) => {
      this.resolvePromise = resolve;

      try {
        this.createOverlayUI();

        const container = document.getElementById(this.options.containerId);
        if (!container) {
          throw new Error(`Container with ID ${this.options.containerId} not found`);
        }

        this.bodyBackgroundColor = document.body.style.backgroundColor;
        document.body.style.backgroundColor = 'transparent';

        await this.cameraController.initialize(
          container,
          this.options.quality ?? 90,
        );

      } catch (error) {
        console.error('Failed to initialize camera overlay', error);
        resolve({ images: [], cancelled: true });
        this.cleanup();
      }
    });
  }

  /**
   * Creates the overlay UI
   */
  private createOverlayUI(): void {
    const container = document.getElementById(this.options.containerId);
    if (!container) {
      throw new Error(`Container with ID ${this.options.containerId} not found`);
    }

    // Create overlay container
    this.overlayElement = createOverlayContainer(this.options.containerId);

    // Create position containers
    const positions = createPositionContainers(this.overlayElement);

    // Create bottom grid cells
    const bottomCells = createBottomGridCells(positions.bottomGrid);

    // Create gallery
    const galleryElement = createGallery(this.overlayElement);
    this.galleryController = new GalleryController(
      galleryElement,
      this.options.thumbnailStyle
    );

    // Merge default buttons with user-provided options
    const buttons: ButtonsConfig = merge(
      defaultButtons,
      this.options.buttons || {}
    );

    // Create buttons
    const captureBtn = createButton(buttons.capture);
    bottomCells.middle.appendChild(captureBtn);

    captureBtn.onclick = async () => {
      try {
        const imageData = await this.cameraController.captureImage();
        if (imageData && this.galleryController) {
          this.galleryController.addImage(imageData);
        }
      } catch (error) {
        console.error('Failed to capture image', error);
      }
    };

    const doneBtn = createButton(buttons.done);
    bottomCells.right.appendChild(doneBtn);

    doneBtn.onclick = () => {
      this.completeCapture(false);
    };

    const cancelBtn = createButton(buttons.cancel);
    bottomCells.left.appendChild(cancelBtn);

    cancelBtn.onclick = () => {
      this.completeCapture(true);
    };

    if (buttons.switchCamera) {
      this.createSwitchCameraButton(buttons.switchCamera, positions.topRight);
    }

    if (buttons.zoom) {
      this.createZoomButtons(buttons.zoom, positions.zoomRow);
    }
  }

  /**
   * Creates the switch camera button
   */
  private createSwitchCameraButton(config: any, container: HTMLElement): void {
    const switchBtn = createButton(config);

    switchBtn.onclick = async () => {
      try {
        await this.cameraController.switchCamera();
      } catch (error) {
        console.error('Failed to switch camera', error);
      }
    };

    container.appendChild(switchBtn);
  }

  /**
   * Creates zoom level buttons
   */
  private createZoomButtons(config: any, container: HTMLElement): void {
    const levels = config.levels || [1, 2, 3, 4];

    // Add zoom buttons in a horizontal row
    levels.forEach((level: number) => {
      // Create a button for each zoom level
      const zoomBtn = createButton({...config, text: `${level}x`});

      // Make zoom buttons smaller and more compact
      Object.assign(zoomBtn.style, {
        padding: '5px',
        minWidth: '30px',
        minHeight: '30px',
        margin: '0 3px'  // Add horizontal spacing between zoom buttons
      });

      zoomBtn.onclick = async () => {
        try {
          await this.cameraController.setZoom(level);
        } catch (error) {
          console.error(`Failed to set zoom to ${level}x`, error);
        }
      };

      container.appendChild(zoomBtn);
    });
  }

  /**
   * Completes the capture process
   */
  private completeCapture(cancelled: boolean): void {
    const images = this.galleryController?.getImages() || [];

    this.cleanup();

    if (this.resolvePromise) {
      this.resolvePromise({
        images: images.map(img => img.data),
        cancelled
      });
      this.resolvePromise = null;
    }
  }

  /**
   * Cleans up resources
   */
  private cleanup(): void {
    this.cameraController.stop().catch(err => {
      console.warn('Error stopping camera', err);
    });

    if (this.overlayElement && this.overlayElement.parentElement) {
      this.overlayElement.parentElement.removeChild(this.overlayElement);
    }

    this.overlayElement = null;
    this.galleryController = null;
    this.isActive = false;
    if (this.bodyBackgroundColor) {
      document.body.style.backgroundColor = this.bodyBackgroundColor;
    }
  }
}
