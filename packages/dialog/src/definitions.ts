export interface DialogPlugin {
  /**
   * Display an alert dialog with a single button.
   *
   * @since 0.1.0
   */
  alert(options: AlertOptions): Promise<void>;
  /**
   * Display a confirmation dialog with two buttons.
   *
   * @since 0.1.0
   */
  confirm(options: ConfirmOptions): Promise<ConfirmResult>;
  /**
   * Display a prompt dialog with a text input, a confirm and a cancel button.
   *
   * @since 0.1.0
   */
  prompt(options: PromptOptions): Promise<PromptResult>;
}

/**
 * @since 0.1.0
 */
export interface AlertOptions {
  /**
   * The title of the button that confirms the dialog.
   *
   * On the web, the button title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @default 'OK'
   * @example 'Got it'
   */
  buttonTitle?: string;
  /**
   * The message to display in the dialog.
   *
   * @since 0.1.0
   * @example 'Your changes have been saved.'
   */
  message: string;
  /**
   * The title of the dialog.
   *
   * On the web, the title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @example 'Success'
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface ConfirmOptions {
  /**
   * The title of the button that cancels the dialog.
   *
   * On the web, the button title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @default 'Cancel'
   * @example 'No'
   */
  cancelButtonTitle?: string;
  /**
   * The message to display in the dialog.
   *
   * @since 0.1.0
   * @example 'Do you want to delete this item?'
   */
  message: string;
  /**
   * The title of the button that confirms the dialog.
   *
   * On the web, the button title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @default 'OK'
   * @example 'Yes'
   */
  okButtonTitle?: string;
  /**
   * The title of the dialog.
   *
   * On the web, the title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @example 'Confirm'
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface ConfirmResult {
  /**
   * Whether the user confirmed the dialog.
   *
   * @since 0.1.0
   * @example true
   */
  value: boolean;
}

/**
 * @since 0.1.0
 */
export interface PromptOptions {
  /**
   * The title of the button that cancels the dialog.
   *
   * On the web, the button title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @default 'Cancel'
   * @example 'No'
   */
  cancelButtonTitle?: string;
  /**
   * The placeholder of the text input.
   *
   * On the web, the placeholder cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @example 'Enter your name'
   */
  inputPlaceholder?: string;
  /**
   * The initial value of the text input.
   *
   * @since 0.1.0
   * @example 'John Doe'
   */
  inputText?: string;
  /**
   * The message to display in the dialog.
   *
   * @since 0.1.0
   * @example 'What is your name?'
   */
  message: string;
  /**
   * The title of the button that confirms the dialog.
   *
   * On the web, the button title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @default 'OK'
   * @example 'Submit'
   */
  okButtonTitle?: string;
  /**
   * The title of the dialog.
   *
   * On the web, the title cannot be customized and is ignored.
   *
   * @since 0.1.0
   * @example 'Name'
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface PromptResult {
  /**
   * Whether the user canceled the dialog.
   *
   * @since 0.1.0
   * @example false
   */
  canceled: boolean;
  /**
   * The value of the text input.
   *
   * @since 0.1.0
   * @example 'John Doe'
   */
  value: string;
}
