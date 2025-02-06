export interface EdgeToEdgePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
