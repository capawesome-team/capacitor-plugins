export interface SuperwallPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
