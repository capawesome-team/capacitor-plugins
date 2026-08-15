import { Capacitor, CapacitorException, registerPlugin } from '@capacitor/core';
import type { PluginListenerHandle } from '@capacitor/core';

import { ErrorCode } from './definitions';
import type {
  CreateMapOptions,
  DestroyMapOptions,
  ElementFromPointRequestEvent,
  MapContentSize,
  MapFrame,
  MapLibrePlugin,
} from './definitions';

interface MapRegistration {
  cleanUp: () => void;
  element: HTMLElement;
  spacer?: MapSpacer;
}

interface MapSpacer {
  element: HTMLElement;
  heightOffset: number;
}

type NativeCreateMapOptions = CreateMapOptions & {
  contentSize: MapContentSize;
  frame: MapFrame;
};

const errorElementNotFound = 'element not found.';

const plugin = registerPlugin<MapLibrePlugin>('MapLibre', {
  web: () => import('./web').then(m => new m.MapLibreWeb()),
});

const registrations = new Map<string, MapRegistration>();

let createdMapCount = 0;
let elementFromPointRequestListener: Promise<PluginListenerHandle> | undefined;

async function addElementFromPointRequestListener(): Promise<void> {
  if (!elementFromPointRequestListener) {
    elementFromPointRequestListener = plugin.addListener(
      'elementFromPointRequest',
      handleElementFromPointRequest,
    );
  }
  await elementFromPointRequestListener;
}

async function createMap(options: CreateMapOptions): Promise<void> {
  const platform = Capacitor.getPlatform();
  if (platform === 'web') {
    return plugin.createMap(options);
  }
  const element = document.getElementById(options.elementId);
  if (!element) {
    throw new CapacitorException(errorElementNotFound, undefined, {
      code: ErrorCode.ElementNotFound,
    });
  }
  element.style.background = 'transparent';
  const spacer = platform === 'ios' ? createScrollSpacer(element) : undefined;
  if (platform === 'android') {
    await addElementFromPointRequestListener();
  }
  const nativeOptions: NativeCreateMapOptions = {
    ...options,
    contentSize: getContentSize(element),
    frame: getFrame(element),
  };
  try {
    await plugin.createMap(nativeOptions);
  } catch (error) {
    spacer?.element.remove();
    throw error;
  }
  registrations.set(options.mapId, {
    cleanUp: startFrameSync(options.mapId, element, spacer),
    element,
    spacer,
  });
}

function createScrollSpacer(mapElement: HTMLElement): MapSpacer {
  // The map element must be scrollable and taller than its viewport so that
  // WebKit creates the scroll view the native map view is inserted into. The
  // height offset makes the content size of every map unique, which is how the
  // native implementation identifies the scroll view of a map.
  const heightOffset = ++createdMapCount;
  const spacer = document.createElement('div');
  spacer.style.height = `${getSpacerHeight(mapElement, heightOffset)}px`;
  spacer.style.pointerEvents = 'none';
  spacer.style.width = '100%';
  mapElement.style.overflow = 'scroll';
  mapElement.appendChild(spacer);
  return { element: spacer, heightOffset };
}

async function destroyMap(options: DestroyMapOptions): Promise<void> {
  if (Capacitor.getPlatform() === 'web') {
    return plugin.destroyMap(options);
  }
  await plugin.destroyMap(options);
  const registration = registrations.get(options.mapId);
  if (!registration) {
    return;
  }
  registrations.delete(options.mapId);
  registration.cleanUp();
  registration.spacer?.element.remove();
}

function findMapIdByElement(element: Element): string | undefined {
  for (const [mapId, registration] of registrations) {
    if (registration.element.contains(element)) {
      return mapId;
    }
  }
  return undefined;
}

function getContentSize(element: HTMLElement): MapContentSize {
  return { height: element.scrollHeight, width: element.scrollWidth };
}

function getFrame(element: HTMLElement): MapFrame {
  const rect = element.getBoundingClientRect();
  return { height: rect.height, width: rect.width, x: rect.left, y: rect.top };
}

function getSpacerHeight(
  mapElement: HTMLElement,
  heightOffset: number,
): number {
  return mapElement.clientHeight * 2 + heightOffset;
}

function handleElementFromPointRequest(
  event: ElementFromPointRequestEvent,
): void {
  const element = document.elementFromPoint(event.x, event.y);
  const mapId = element ? findMapIdByElement(element) : undefined;
  void plugin.elementFromPointResult({
    mapId: mapId ?? null,
    requestId: event.requestId,
  });
}

function startFrameSync(
  mapId: string,
  element: HTMLElement,
  spacer: MapSpacer | undefined,
): () => void {
  let animationFrameId: number | undefined;
  const handleLayoutChange = (): void => {
    if (animationFrameId !== undefined) {
      return;
    }
    animationFrameId = requestAnimationFrame(() => {
      animationFrameId = undefined;
      if (spacer) {
        updateSpacerHeight(element, spacer);
      }
      void plugin.setFrame({
        contentSize: getContentSize(element),
        frame: getFrame(element),
        mapId,
      });
    });
  };
  const resizeObserver = new ResizeObserver(handleLayoutChange);
  resizeObserver.observe(element);
  window.addEventListener('scroll', handleLayoutChange, {
    capture: true,
    passive: true,
  });
  window.addEventListener('resize', handleLayoutChange);
  window.addEventListener('orientationchange', handleLayoutChange);
  return () => {
    if (animationFrameId !== undefined) {
      cancelAnimationFrame(animationFrameId);
    }
    resizeObserver.disconnect();
    window.removeEventListener('scroll', handleLayoutChange, { capture: true });
    window.removeEventListener('resize', handleLayoutChange);
    window.removeEventListener('orientationchange', handleLayoutChange);
  };
}

function updateSpacerHeight(mapElement: HTMLElement, spacer: MapSpacer): void {
  const height = `${getSpacerHeight(mapElement, spacer.heightOffset)}px`;
  if (spacer.element.style.height !== height) {
    spacer.element.style.height = height;
  }
}

const MapLibre = new Proxy(plugin, {
  get(target, property) {
    switch (property) {
      case 'createMap':
        return createMap;
      case 'destroyMap':
        return destroyMap;
      default:
        return Reflect.get(target, property);
    }
  },
});

export * from './definitions';
export { MapLibre };
