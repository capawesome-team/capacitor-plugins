import { WebPlugin } from '@capacitor/core';

import type { ShowOptions, ToastPlugin } from './definitions';
import { ToastDuration, ToastPosition } from './definitions';

export class ToastWeb extends WebPlugin implements ToastPlugin {
  private static readonly durationLongInMilliseconds = 3500;
  private static readonly durationShortInMilliseconds = 2000;
  private static readonly errorTextMissing = 'text must be provided.';
  private static readonly fadeDurationInMilliseconds = 300;

  private currentElement: HTMLElement | undefined;
  private currentHideTimeout: ReturnType<typeof setTimeout> | undefined;

  async show(options: ShowOptions): Promise<void> {
    if (!options.text) {
      throw new Error(ToastWeb.errorTextMissing);
    }
    this.removeCurrentToast(true);
    const duration = options.duration ?? ToastDuration.Short;
    const position = options.position ?? ToastPosition.Bottom;
    const element = this.createToastElement(options.text, position);
    document.body.appendChild(element);
    this.currentElement = element;
    // Force a reflow so the fade-in transition is applied.
    void element.offsetHeight;
    element.style.opacity = '1';
    const durationInMilliseconds =
      duration === ToastDuration.Long
        ? ToastWeb.durationLongInMilliseconds
        : ToastWeb.durationShortInMilliseconds;
    this.currentHideTimeout = setTimeout(() => {
      this.removeCurrentToast(false);
    }, durationInMilliseconds);
  }

  private createToastElement(
    text: string,
    position: ToastPosition,
  ): HTMLElement {
    const element = document.createElement('div');
    element.textContent = text;
    const styles: Partial<CSSStyleDeclaration> = {
      position: 'fixed',
      left: '50%',
      transform: 'translateX(-50%)',
      maxWidth: '80%',
      padding: '12px 20px',
      borderRadius: '20px',
      backgroundColor: 'rgba(0, 0, 0, 0.8)',
      color: '#ffffff',
      fontSize: '14px',
      fontFamily: 'sans-serif',
      textAlign: 'center',
      lineHeight: '1.4',
      zIndex: '2147483647',
      opacity: '0',
      transition: `opacity ${ToastWeb.fadeDurationInMilliseconds}ms ease-in-out`,
      pointerEvents: 'none',
      boxSizing: 'border-box',
    };
    switch (position) {
      case ToastPosition.Top:
        styles.top = 'calc(env(safe-area-inset-top, 0px) + 24px)';
        break;
      case ToastPosition.Center:
        styles.top = '50%';
        styles.transform = 'translate(-50%, -50%)';
        break;
      case ToastPosition.Bottom:
      default:
        styles.bottom = 'calc(env(safe-area-inset-bottom, 0px) + 24px)';
        break;
    }
    Object.assign(element.style, styles);
    return element;
  }

  private removeCurrentToast(immediate: boolean): void {
    if (this.currentHideTimeout) {
      clearTimeout(this.currentHideTimeout);
      this.currentHideTimeout = undefined;
    }
    const element = this.currentElement;
    if (!element) {
      return;
    }
    this.currentElement = undefined;
    if (immediate) {
      element.remove();
      return;
    }
    element.style.opacity = '0';
    setTimeout(() => {
      element.remove();
    }, ToastWeb.fadeDurationInMilliseconds);
  }
}
