export interface PhotoEditorPlugin {
  /**
   * Edit a photo at a given path.
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
