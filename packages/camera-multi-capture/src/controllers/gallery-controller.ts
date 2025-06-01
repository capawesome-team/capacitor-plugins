/**
 * Controller for gallery operations and captured images
 */
import { CameraImageData, CapturedImage } from '../definitions';
import { createThumbnailContainer } from '../ui/ui-factory';

/**
 * Manages gallery operations and captured images
 */
export class GalleryController {
  private galleryElement: HTMLElement;
  private images: CapturedImage[] = [];
  private thumbnailStyle: { width?: string; height?: string };
  private onImageRemoved: (images: CapturedImage[]) => void;
  
  constructor(
    galleryElement: HTMLElement, 
    thumbnailStyle: { width?: string; height?: string } = { width: '80px' },
    onImageRemoved?: (images: CapturedImage[]) => void
  ) {
    this.galleryElement = galleryElement;
    this.thumbnailStyle = thumbnailStyle;
    this.onImageRemoved = onImageRemoved || (() => {});
  }
  
  /**
   * Adds a new image to the gallery
   */
  addImage(imageData: CameraImageData): void {
    const id = `img_${Date.now()}_${this.images.length}`;
    const newImage: CapturedImage = { id, data: imageData };
    
    this.images.push(newImage);
    this.renderGallery();
  }
  
  /**
   * Removes an image from the gallery
   */
  removeImage(imageId: string): void {
    this.images = this.images.filter(img => img.id !== imageId);
    this.renderGallery();
    this.onImageRemoved(this.images);
  }
  
  /**
   * Clears all images from the gallery
   */
  clearGallery(): void {
    this.images = [];
    this.renderGallery();
    this.onImageRemoved(this.images);
  }
  
  /**
   * Gets all captured images
   */
  getImages(): CapturedImage[] {
    return this.images;
  }
  
  /**
   * Renders the gallery with current images
   */
  private renderGallery(): void {
    this.galleryElement.innerHTML = '';
    
    this.images.forEach(image => {
      const thumbnailContainer = createThumbnailContainer(
        image.data.base64,
        this.thumbnailStyle,
        () => this.removeImage(image.id)
      );
      
      this.galleryElement.appendChild(thumbnailContainer);
    });
  }
}
