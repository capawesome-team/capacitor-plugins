export interface AgeSignalsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
