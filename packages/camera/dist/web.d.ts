import { WebPlugin } from '@capacitor/core';
import type { CameraPlugin, MultiPhotoResult } from './definitions';
export declare class CameraWeb extends WebPlugin implements CameraPlugin {
    takeMultiplePhotos(_options?: any): Promise<MultiPhotoResult>;
}
