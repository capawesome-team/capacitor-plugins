export interface NavigationBarPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
