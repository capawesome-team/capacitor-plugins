export interface RealtimeKitPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
