/**
 * Factory for creating UI elements
 */
import { ButtonStyle, IconButtonConfig } from '../types/ui-types';

/**
 * Creates a button with the specified configuration
 */
export function createButton(config: IconButtonConfig, text?: string): HTMLButtonElement {
  const btn = document.createElement('button');
  
  if (text) {
    btn.textContent = text;
  } else if (config.text) {
    btn.textContent = config.text;
  } else if (config.icon) {
    btn.innerHTML = config.icon;
  }
  
  const style = config.style || {};
  
  applyButtonStyle(btn, style);
  
  return btn;
}

/**
 * Applies styles to a button element
 */
export function applyButtonStyle(element: HTMLButtonElement, style: ButtonStyle): void {
  Object.assign(element.style, {
    borderRadius: style.radius ? `${style.radius}px` : '30px',
    padding: style.padding || '10px',
    backgroundColor: style.backgroundColor || '#ffffff',
    color: style.color || '#000000',
    border: style.border || 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: 'pointer',
    outline: 'none',
    minWidth: '44px',
    minHeight: '44px'
  });
  
  if (style.size && element.querySelector('svg')) {
    const svg = element.querySelector('svg');
    svg?.setAttribute('width', `${style.size}px`);
    svg?.setAttribute('height', `${style.size}px`);
  }
}

/**
 * Creates a thumbnail image container
 */
export function createThumbnailContainer(
  imageData: string, 
  thumbnailStyle: { width?: string; height?: string },
  onRemove: () => void
): HTMLElement {
  const thumbnailContainer = document.createElement('div');
  const width = thumbnailStyle?.width || '80px';
  const height = thumbnailStyle?.height || '80px';
  Object.assign(thumbnailContainer.style, {
    position: 'relative',
    display: 'inline-block',
    width: width,
    height: height,
    marginRight: '10px',
    borderRadius: '4px',
    overflow: 'hidden',
    flexShrink: '0',
    boxSizing: 'border-box'
  });
  
  const thumbnail = document.createElement('img');
  thumbnail.src = imageData.startsWith('data:') ? 
    imageData : 
    `data:image/jpeg;base64,${imageData}`;
  
  Object.assign(thumbnail.style, {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    borderRadius: '4px',
    cursor: 'pointer'
  });
  
  const removeBtn = createRemoveButton(onRemove);
  
  thumbnailContainer.appendChild(thumbnail);
  thumbnailContainer.appendChild(removeBtn);
  
  return thumbnailContainer;
}

/**
 * Creates a remove button for thumbnails
 */
export function createRemoveButton(onRemove: (e: Event) => void): HTMLButtonElement {
  const removeBtn = document.createElement('button');
  Object.assign(removeBtn.style, {
    position: 'absolute',
    top: '3px',
    right: '3px',
    width: '24px',
    height: '24px',
    borderRadius: '50%',
    background: 'rgba(0, 0, 0, 0.5)',
    color: 'white',
    border: 'none',
    cursor: 'pointer',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0',
    fontSize: '14px',
    fontWeight: 'bold',
    zIndex: '3'
  });
  
  removeBtn.innerHTML = '×';
  
  removeBtn.onclick = (e) => {
    e.stopPropagation();
    onRemove(e);
  };
  
  return removeBtn;
}
