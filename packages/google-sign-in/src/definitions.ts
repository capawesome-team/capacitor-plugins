export interface GoogleSignInPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
