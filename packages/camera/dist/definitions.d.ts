export interface Photo {
    path?: string;
    webPath?: string;
    format: string;
}
export interface MultiPhotoResult {
    photos: Photo[];
}
export interface CameraPlugin {
    takeMultiplePhotos(options?: any): Promise<MultiPhotoResult>;
}
