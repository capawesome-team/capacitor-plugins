export interface FormbricksPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
