import { TabBar } from '@capawesome/capacitor-tab-bar';

const colors = [
  ['#ff375f', '#ff9f0a'],
  ['#30d158', '#64d2ff'],
  ['#5e5ce6', '#bf5af2'],
  ['#ff9f0a', '#ffd60a'],
  ['#0a84ff', '#5e5ce6'],
  ['#ff453a', '#bf5af2'],
];

let inboxBadge = 3;

const createTabs = () => [
  { id: 'home', title: 'Home', systemImage: 'house.fill' },
  { id: 'search', title: 'Search', systemImage: 'magnifyingglass' },
  {
    id: 'inbox',
    title: 'Inbox',
    systemImage: 'tray.fill',
    badge: inboxBadge > 0 ? `${inboxBadge}` : undefined,
  },
  { id: 'favorites', title: 'Favorites', image: 'custom-star' },
];

const log = message => {
  const item = document.createElement('li');
  item.textContent = `${new Date().toLocaleTimeString()} ${message}`;
  document.querySelector('#log').prepend(item);
};

const run = async action => {
  try {
    await action();
  } catch (error) {
    log(`Error: ${error.message}`);
  }
};

const bind = (selector, action) => {
  document.querySelector(selector).addEventListener('click', () => run(action));
};

const renderGallery = () => {
  const gallery = document.querySelector('#gallery');
  for (let index = 0; index < 24; index++) {
    const [from, to] = colors[index % colors.length];
    const tile = document.createElement('div');
    tile.className = 'tile';
    tile.style.background = `linear-gradient(135deg, ${from}, ${to})`;
    tile.textContent = `Item ${index + 1}`;
    gallery.append(tile);
  }
};

const observeBottomInset = () => {
  const probe = document.querySelector('#probe');
  const inset = document.querySelector('#inset');
  new ResizeObserver(() => {
    inset.textContent = getComputedStyle(probe).paddingBottom.replace('px', '');
  }).observe(probe, { box: 'border-box' });
};

document.addEventListener('DOMContentLoaded', () => {
  renderGallery();
  observeBottomInset();
  TabBar.addListener('tabSelected', event => log(`tabSelected: ${event.id}`));
  bind('#show', () => TabBar.show());
  bind('#hide', () => TabBar.hide());
  bind('#increase-badge', () => {
    inboxBadge++;
    return TabBar.setTabs({ tabs: createTabs() });
  });
  bind('#clear-badge', () => {
    inboxBadge = 0;
    return TabBar.setTabs({ tabs: createTabs() });
  });
  bind('#select-search', () => TabBar.selectTabById({ id: 'search' }));
  bind('#set-colors', () =>
    TabBar.setColors({ selectedColor: '#ff375f', unselectedColor: '#8e8e93' }),
  );
  bind('#reset-colors', () => TabBar.setColors({}));
});
