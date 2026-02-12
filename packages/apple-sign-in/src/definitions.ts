export interface AppleSignInPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
