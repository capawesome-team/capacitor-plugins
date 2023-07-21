export interface PhotoEditorPlugin {
  /**
   * Edit a photo at a given path.
   *
   * **Attention**: A suitable photo editing app must be installed (e.g. Google Photos)
   * and the user should overwrite the image when saving
   * so that the path to the image is not lost.
   *
   * Only available on Android.
   */
  editPhoto(options: EditPhotoOptions): Promise<void>;
}

export interface EditPhotoOptions {
  /**
   * The path of the file to edit.
   */
  path: string;
}
