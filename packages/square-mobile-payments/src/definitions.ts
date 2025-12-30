export interface SquareMobilePaymentsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
