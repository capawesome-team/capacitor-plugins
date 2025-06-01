/**
 * Default styles and icons for camera overlay UI
 */
import { 
  ButtonStyle, 
  ButtonsConfig 
} from '../types/ui-types';

/**
 * Default SVG icons for camera controls
 */
export const defaultIcons = {
  captureIcon: `<svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><circle cx="12" cy="12" r="10" fill="currentColor"/></svg>`,
  doneIcon: `<svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z" fill="currentColor"/></svg>`,
  cancelIcon: `<svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" fill="currentColor"/></svg>`,
  switchCameraIcon: `<svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><path d="M20 4h-3.17L15 2H9L7.17 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm-5 11.5V13H9v2.5L5.5 12 9 8.5V11h6V8.5l3.5 3.5-3.5 3.5z" fill="currentColor"/></svg>`,
  zoomIcon: `<svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" fill="currentColor"/><path d="M12 10h-2v2H9v-2H7V9h2V7h1v2h2v1z" fill="currentColor"/></svg>`,
};

/**
 * Default button style
 */
export const defaultButtonStyle: ButtonStyle = {
  radius: 30,
  backgroundColor: '#ffffff',
  color: '#000000',
  padding: '10px',
  size: 24
};

/**
 * Default button configurations
 */
export const defaultButtons: ButtonsConfig = {
  capture: {
    icon: defaultIcons.captureIcon,
    style: {
      radius: 40,
      backgroundColor: '#ffffff',
      color: '#000000',
      padding: '12px',
      size: 32
    },
    position: 'center'
  },
  done: {
    icon: defaultIcons.doneIcon,
    style: {
      radius: 30,
      backgroundColor: '#28a745',
      color: '#ffffff',
      padding: '10px',
      size: 24
    }
  },
  cancel: {
    icon: defaultIcons.cancelIcon,
    style: {
      radius: 30,
      backgroundColor: '#dc3545',
      color: '#ffffff',
      padding: '10px',
      size: 24
    }
  },
  switchCamera: {
    icon: defaultIcons.switchCameraIcon,
    style: {
      radius: 30,
      backgroundColor: 'rgba(0,0,0,0.5)',
      color: '#ffffff',
      padding: '10px',
      size: 24
    },
    position: 'topRight'
  },
  zoom: {
    icon: defaultIcons.zoomIcon,
    style: {
      radius: 30,
      backgroundColor: 'rgba(0,0,0,0.5)',
      color: '#ffffff',
      padding: '10px',
      size: 24
    },
    levels: [1, 2, 3, 4]
  }
};
