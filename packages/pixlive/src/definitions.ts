export interface PixlivePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
