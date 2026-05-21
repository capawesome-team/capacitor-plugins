export interface GrafanaFaroPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
