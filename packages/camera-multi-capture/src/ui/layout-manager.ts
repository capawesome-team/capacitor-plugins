/**
 * Layout manager for camera overlay UI
 */
import { UIPosition } from '../types/ui-types';

/**
 * Interface for position containers
 */
export interface PositionContainers {
  topLeft: HTMLElement;
  topRight: HTMLElement;
  center: HTMLElement;
  bottomGrid: HTMLElement;
  zoomRow: HTMLElement;
}

/**
 * Interface for bottom grid cells
 */
export interface BottomGridCells {
  left: HTMLElement;
  middle: HTMLElement;
  right: HTMLElement;
}

/**
 * Creates and styles the overlay container
 */
export function createOverlayContainer(containerId: string): HTMLElement {
  const container = document.getElementById(containerId);
  if (!container) {
    throw new Error(`Container element with ID '${containerId}' not found`);
  }
  
  // Make sure the container has position relative for proper positioning of child elements
  if (getComputedStyle(container).position === 'static') {
    container.style.position = 'relative';
  }
  
  const overlay = document.createElement('div');
  overlay.id = 'camera-overlay';
  Object.assign(overlay.style, {
    position: 'absolute',
    top: '0', left: '0', width: '100%', height: '100%',
    background: 'transparent', 
    zIndex: '1',
    display: 'flex', 
    flexDirection: 'column', 
    justifyContent: 'flex-end', 
    alignItems: 'center', 
    padding: '10px',
    color: '#000',
    boxSizing: 'border-box'
  });
  
  container.appendChild(overlay);
  
  return overlay;
}

/**
 * Creates the position containers for UI elements
 */
export function createPositionContainers(overlay: HTMLElement): PositionContainers {
  const positions: PositionContainers = {
    topLeft: document.createElement('div'),
    topRight: document.createElement('div'),
    center: document.createElement('div'),
    bottomGrid: document.createElement('div'),
    zoomRow: document.createElement('div')
  };
  
  Object.assign(positions.topLeft.style, {
    position: 'absolute',
    top: '10px',
    left: '10px',
    display: 'flex',
    gap: '10px',
    zIndex: '2'
  });
  
  Object.assign(positions.topRight.style, {
    position: 'absolute',
    top: '10px',
    right: '10px',
    display: 'flex',
    gap: '10px',
    zIndex: '2'
  });
  
  Object.assign(positions.center.style, {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    display: 'flex',
    gap: '10px',
    zIndex: '2'
  });
  
  Object.assign(positions.bottomGrid.style, {
    position: 'absolute',
    bottom: '20px',
    left: '0',
    width: '100%',
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr',  // 3 equal columns
    alignItems: 'center',
    padding: '0 10px',
    boxSizing: 'border-box',
    zIndex: '2',
    height: '70px'  // Fixed height to prevent overlap
  });
  
  Object.assign(positions.zoomRow.style, {
    position: 'absolute',
    bottom: '110px',  // Increased distance from bottom grid
    left: '0',
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    gap: '10px',
    padding: '10px',
    boxSizing: 'border-box',
    zIndex: '2',
    height: '40px'  // Fixed height for zoom row
  });
  
  Object.values(positions).forEach(posContainer => {
    overlay.appendChild(posContainer);
  });
  
  return positions;
}

/**
 * Creates the bottom grid cells
 */
export function createBottomGridCells(bottomGrid: HTMLElement): BottomGridCells {
  const cells: BottomGridCells = {
    left: document.createElement('div'),
    middle: document.createElement('div'),
    right: document.createElement('div')
  };
  
  Object.assign(cells.left.style, {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  });
  
  Object.assign(cells.middle.style, {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  });
  
  Object.assign(cells.right.style, {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  });
  
  bottomGrid.appendChild(cells.left);
  bottomGrid.appendChild(cells.middle);
  bottomGrid.appendChild(cells.right);
  
  return cells;
}

/**
 * Creates the gallery container for thumbnails
 */
export function createGallery(overlay: HTMLElement): HTMLElement {
  const gallery = document.createElement('div');
  Object.assign(gallery.style, {
    position: 'absolute',
    bottom: '160px',
    left: '0',
    width: '100%',
    display: 'flex', 
    gap: '10px', 
    padding: '10px',
    overflowX: 'auto',
    overflowY: 'hidden',
    whiteSpace: 'nowrap',
    justifyContent: 'center',
    zIndex: '2',
    scrollbarWidth: 'none',
    WebkitOverflowScrolling: 'touch',
    flexWrap: 'nowrap'
  });
  
  gallery.style.setProperty('-ms-overflow-style', 'none');
  
  gallery.addEventListener('mouseenter', () => {
    gallery.style.overflow = 'auto';
  });
  gallery.addEventListener('mouseleave', () => {
    gallery.style.overflow = 'hidden';
  });
  
  overlay.appendChild(gallery);
  
  return gallery;
}

/**
 * Gets the position container based on the specified position
 */
export function getPositionContainer(
  positions: PositionContainers,
  position?: UIPosition
): HTMLElement {
  switch (position) {
    case 'topLeft':
      return positions.topLeft;
    case 'topRight':
      return positions.topRight;
    case 'center':
      return positions.center;
    default:
      return positions.topRight; // Default fallback
  }
}
