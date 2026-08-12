import { WebPlugin } from '@capacitor/core';

import type {
  ActionSheetPlugin,
  ShowActionsOptions,
  ShowActionsResult,
} from './definitions';

export class ActionSheetWeb extends WebPlugin implements ActionSheetPlugin {
  async showActions(_options: ShowActionsOptions): Promise<ShowActionsResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
