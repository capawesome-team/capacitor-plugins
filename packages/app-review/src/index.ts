import { registerPlugin } from '@capacitor/core';

import type { AppReviewPlugin } from './definitions';

const AppReview = registerPlugin<AppReviewPlugin>('AppReview', {
  web: () => import('./web').then(m => new m.AppReviewWeb()),
});

export * from './definitions';
export { AppReview };
