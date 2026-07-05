import { Nodejs } from '@capawesome/capacitor-nodejs';

const setStatus = status => {
  document.getElementById('statusOutput').innerText = status;
};

const appendMessage = message => {
  const listItem = document.createElement('li');
  listItem.innerText = message;
  document.getElementById('messagesOutput').appendChild(listItem);
};

void Nodejs.addListener('ready', () => {
  setStatus('Ready');
});

void Nodejs.addListener('message', event => {
  appendMessage(`${event.eventName}: ${JSON.stringify(event.args)}`);
});

window.testIsReady = async () => {
  const { ready } = await Nodejs.isReady();
  setStatus(ready ? 'Ready' : 'Not ready');
};

window.testSend = async () => {
  await Nodejs.send({
    eventName: 'echo',
    args: [document.getElementById('messageInput').value],
  });
};
