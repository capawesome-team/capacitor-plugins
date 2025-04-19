export interface LibsqlPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
