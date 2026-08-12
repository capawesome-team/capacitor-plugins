export interface PdfGeneratorPlugin {
  /**
   * Generate a paginated PDF file from an HTML string.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  generateFromHtml(options: GenerateFromHtmlOptions): Promise<GenerateResult>;
  /**
   * Generate a paginated PDF file from a URL.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  generateFromUrl(options: GenerateFromUrlOptions): Promise<GenerateResult>;
}

/**
 * @since 0.1.0
 */
export interface GenerateFromHtmlOptions {
  /**
   * The base URL to resolve relative resources (e.g. images, stylesheets)
   * in the HTML content against.
   *
   * @since 0.1.0
   * @example 'https://capawesome.io/'
   */
  baseUrl?: string;
  /**
   * The name of the generated PDF file.
   *
   * If the name does not end with `.pdf`, the extension is appended.
   *
   * @since 0.1.0
   * @default A randomly generated file name.
   * @example 'invoice.pdf'
   */
  fileName?: string;
  /**
   * The HTML content to generate the PDF file from.
   *
   * @since 0.1.0
   * @example '<h1>Hello World</h1>'
   */
  html: string;
  /**
   * The page orientation of the generated PDF file.
   *
   * @since 0.1.0
   * @default Orientation.Portrait
   */
  orientation?: Orientation;
  /**
   * The page size of the generated PDF file.
   *
   * @since 0.1.0
   * @default PageSize.A4
   */
  pageSize?: PageSize;
  /**
   * The maximum time in milliseconds to wait for the PDF generation
   * to complete before the call is rejected with the `TIMEOUT` error code.
   *
   * @since 0.1.0
   * @default 30000
   * @example 10000
   */
  timeout?: number;
}

/**
 * @since 0.1.0
 */
export interface GenerateFromUrlOptions {
  /**
   * The name of the generated PDF file.
   *
   * If the name does not end with `.pdf`, the extension is appended.
   *
   * @since 0.1.0
   * @default A randomly generated file name.
   * @example 'invoice.pdf'
   */
  fileName?: string;
  /**
   * The page orientation of the generated PDF file.
   *
   * @since 0.1.0
   * @default Orientation.Portrait
   */
  orientation?: Orientation;
  /**
   * The page size of the generated PDF file.
   *
   * @since 0.1.0
   * @default PageSize.A4
   */
  pageSize?: PageSize;
  /**
   * The maximum time in milliseconds to wait for the PDF generation
   * to complete before the call is rejected with the `TIMEOUT` error code.
   *
   * @since 0.1.0
   * @default 30000
   * @example 10000
   */
  timeout?: number;
  /**
   * The URL of the web page to generate the PDF file from.
   *
   * @since 0.1.0
   * @example 'https://capawesome.io/'
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface GenerateResult {
  /**
   * The path of the generated PDF file.
   *
   * The file is stored in the cache directory and deleted on the next app
   * launch. Move it to a permanent location if you want to keep it.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/capawesome_capacitor_pdf_generator_documents/invoice.pdf'
   */
  path: string;
}

/**
 * The page orientation of the generated PDF file.
 *
 * @since 0.1.0
 */
export enum Orientation {
  /**
   * Landscape orientation.
   *
   * @since 0.1.0
   */
  Landscape = 'LANDSCAPE',
  /**
   * Portrait orientation.
   *
   * @since 0.1.0
   */
  Portrait = 'PORTRAIT',
}

/**
 * The page size of the generated PDF file.
 *
 * @since 0.1.0
 */
export enum PageSize {
  /**
   * ISO A3 (297 x 420 mm).
   *
   * @since 0.1.0
   */
  A3 = 'A3',
  /**
   * ISO A4 (210 x 297 mm).
   *
   * @since 0.1.0
   */
  A4 = 'A4',
  /**
   * ISO A5 (148 x 210 mm).
   *
   * @since 0.1.0
   */
  A5 = 'A5',
  /**
   * US Letter (8.5 x 11 in).
   *
   * @since 0.1.0
   */
  Letter = 'LETTER',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The PDF file could not be generated.
   *
   * @since 0.1.0
   */
  GenerationFailed = 'GENERATION_FAILED',
  /**
   * The HTML content or URL could not be loaded.
   *
   * @since 0.1.0
   */
  LoadFailed = 'LOAD_FAILED',
  /**
   * The PDF generation timed out.
   *
   * @since 0.1.0
   */
  Timeout = 'TIMEOUT',
}
