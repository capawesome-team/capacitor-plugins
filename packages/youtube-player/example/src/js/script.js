import { YoutubePlayer } from '@capawesome/capacitor-youtube-player';

const videoId = 'dQw4w9WgXcQ';
const playerIds = ['player-one', 'player-two'];

document.addEventListener('DOMContentLoaded', () => {
  YoutubePlayer.addListener('playerReady', event => {
    console.log('playerReady', event);
  });
  YoutubePlayer.addListener('playerStateChange', event => {
    console.log('playerStateChange', event);
  });
  YoutubePlayer.addListener('currentTimeChange', event => {
    const label = document.querySelector('#current-time-label');
    label.textContent = `Current time: ${event.id} at ${event.currentTime.toFixed(1)}s`;
  });
  YoutubePlayer.addListener('playbackRateChange', event => {
    console.log('playbackRateChange', event);
  });
  YoutubePlayer.addListener('playerError', event => {
    console.log('playerError', event);
  });
  YoutubePlayer.addListener('fullscreenChange', event => {
    console.log('fullscreenChange', event);
  });

  const getSelectedPlayerId = () =>
    document.querySelector('#player-select').value;

  const getFrame = id => {
    const rect = document.querySelector(`#${id}`).getBoundingClientRect();
    return {
      x: rect.x,
      y: rect.y,
      width: rect.width,
      height: rect.height,
    };
  };

  const syncPlayerFrames = async () => {
    for (const id of playerIds) {
      try {
        await YoutubePlayer.setPlayerFrame({ id, frame: getFrame(id) });
      } catch {
        // Ignore errors for players that have not been created yet.
      }
    }
  };

  document
    .querySelector('ion-content')
    .addEventListener('ionScroll', syncPlayerFrames);
  window.addEventListener('resize', syncPlayerFrames);

  document
    .querySelector('#create-players-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.createPlayer({
        id: 'player-one',
        frame: getFrame('player-one'),
        videoId,
        options: {
          mute: true,
        },
      });
      await YoutubePlayer.createPlayer({
        id: 'player-two',
        frame: getFrame('player-two'),
        options: {
          controls: false,
          fullscreen: true,
        },
      });
    });
  document
    .querySelector('#load-video-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.loadVideo({
        id: getSelectedPlayerId(),
        videoId,
        startSeconds: 10,
      });
    });
  document
    .querySelector('#cue-video-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.cueVideo({ id: getSelectedPlayerId(), videoId });
    });
  document.querySelector('#play-button').addEventListener('click', async () => {
    await YoutubePlayer.play({ id: getSelectedPlayerId() });
  });
  document
    .querySelector('#pause-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.pause({ id: getSelectedPlayerId() });
    });
  document
    .querySelector('#seek-to-button')
    .addEventListener('click', async () => {
      const id = getSelectedPlayerId();
      const { currentTime } = await YoutubePlayer.getCurrentTime({ id });
      await YoutubePlayer.seekTo({ id, seconds: currentTime + 10 });
    });
  document.querySelector('#mute-button').addEventListener('click', async () => {
    await YoutubePlayer.mute({ id: getSelectedPlayerId() });
  });
  document
    .querySelector('#unmute-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.unmute({ id: getSelectedPlayerId() });
    });
  document
    .querySelector('#set-volume-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.setVolume({ id: getSelectedPlayerId(), volume: 50 });
    });
  document
    .querySelector('#set-playback-rate-button')
    .addEventListener('click', async () => {
      await YoutubePlayer.setPlaybackRate({
        id: getSelectedPlayerId(),
        rate: 1.5,
      });
    });
  document
    .querySelector('#get-current-time-button')
    .addEventListener('click', async () => {
      const result = await YoutubePlayer.getCurrentTime({
        id: getSelectedPlayerId(),
      });
      console.log('getCurrentTime', result);
    });
  document
    .querySelector('#get-duration-button')
    .addEventListener('click', async () => {
      const result = await YoutubePlayer.getDuration({
        id: getSelectedPlayerId(),
      });
      console.log('getDuration', result);
    });
  document
    .querySelector('#remove-players-button')
    .addEventListener('click', async () => {
      for (const id of playerIds) {
        try {
          await YoutubePlayer.removePlayer({ id });
        } catch {
          // Ignore errors for players that have not been created yet.
        }
      }
    });
});
