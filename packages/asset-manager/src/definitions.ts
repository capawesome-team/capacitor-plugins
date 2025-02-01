export interface AssetManagerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
