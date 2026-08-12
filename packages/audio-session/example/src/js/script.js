import { AudioSession } from '@capawesome/capacitor-audio-session';

document.addEventListener('DOMContentLoaded', () => {
  AudioSession.addListener('interruption', event => {
    console.log('interruption:', event);
  });
  AudioSession.addListener('routeChange', event => {
    console.log('routeChange:', event);
  });

  document.querySelector('#configure').addEventListener('click', async () => {
    await AudioSession.configure({
      category: 'playback',
      mode: 'moviePlayback',
      options: {
        mixWithOthers: false,
      },
    });
    console.log('configured');
  });
  document.querySelector('#setActive').addEventListener('click', async () => {
    await AudioSession.setActive({ active: true });
    console.log('active');
  });
  document.querySelector('#setInactive').addEventListener('click', async () => {
    await AudioSession.setActive({ active: false });
    console.log('inactive');
  });
  document
    .querySelector('#getCurrentOutputs')
    .addEventListener('click', async () => {
      const { outputs } = await AudioSession.getCurrentOutputs();
      console.log('outputs:', outputs);
    });
  document
    .querySelector('#overrideOutputSpeaker')
    .addEventListener('click', async () => {
      await AudioSession.overrideOutput({ type: 'speaker' });
      console.log('overridden to speaker');
    });
  document
    .querySelector('#overrideOutputDefault')
    .addEventListener('click', async () => {
      await AudioSession.overrideOutput({ type: 'default' });
      console.log('overridden to default');
    });
});
