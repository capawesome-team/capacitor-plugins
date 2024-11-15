export interface AppReviewPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
