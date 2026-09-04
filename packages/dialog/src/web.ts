import { CapacitorException, WebPlugin } from '@capacitor/core';

import type {
  AlertOptions,
  ConfirmOptions,
  ConfirmResult,
  DialogPlugin,
  PromptOptions,
  PromptResult,
} from './definitions';
import { ErrorCode } from './definitions';

export class DialogWeb extends WebPlugin implements DialogPlugin {
  private static readonly errorCanceled = 'The user canceled the dialog.';
  private static readonly errorMessageMissing = 'message must be provided.';

  async alert(options: AlertOptions): Promise<void> {
    if (!options.message) {
      throw new Error(DialogWeb.errorMessageMissing);
    }
    window.alert(options.message);
  }

  async confirm(options: ConfirmOptions): Promise<ConfirmResult> {
    if (!options.message) {
      throw new Error(DialogWeb.errorMessageMissing);
    }
    const value = window.confirm(options.message);
    return { value };
  }

  async prompt(options: PromptOptions): Promise<PromptResult> {
    if (!options.message) {
      throw new Error(DialogWeb.errorMessageMissing);
    }
    const value = window.prompt(options.message, options.inputText ?? '');
    if (value === null) {
      throw new CapacitorException(DialogWeb.errorCanceled, undefined, {
        code: ErrorCode.Canceled,
      });
    }
    return { value };
  }
}
