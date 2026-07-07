import { Permissions } from '@capawesome/capacitor-permissions';

const getSelectedPermissions = () =>
  document.querySelector('#permissions').value;

const displayStatuses = statuses => {
  const list = document.querySelector('#statuses');
  list.innerHTML = '';
  for (const status of statuses) {
    const item = document.createElement('ion-item');
    const label = document.createElement('ion-label');
    label.textContent = status.permission;
    const note = document.createElement('ion-note');
    note.slot = 'end';
    note.textContent = status.state;
    item.appendChild(label);
    item.appendChild(note);
    list.appendChild(item);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#check').addEventListener('click', async () => {
    const { statuses } = await Permissions.check({
      permissions: getSelectedPermissions(),
    });
    displayStatuses(statuses);
  });
  document.querySelector('#request').addEventListener('click', async () => {
    const { statuses } = await Permissions.request({
      permissions: getSelectedPermissions(),
    });
    displayStatuses(statuses);
  });
});
