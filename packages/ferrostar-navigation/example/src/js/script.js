import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

// Sample Valhalla response in OSRM format from the Ferrostar test fixtures.
import route from './route.json';

document.addEventListener('DOMContentLoaded', () => {
  const osrmResponseJson = JSON.stringify(route);

  FerrostarNavigation.addListener('navigationStateChanged', event => {
    console.log('navigationStateChanged', event);
    document.querySelector('#status-text').textContent = event.status;
    document.querySelector('#instruction-text').textContent = event.instruction
      ? `${event.instruction.text} (${event.instruction.maneuverType} ${event.instruction.maneuverModifier})`
      : '';
    document.querySelector('#progress-text').textContent =
      event.status === 'NAVIGATING'
        ? `Next maneuver in ${Math.round(event.distanceToNextManeuverMeters)} m, ` +
          `${Math.round(event.distanceRemainingMeters)} m / ${Math.round(event.durationRemainingSeconds)} s remaining, ` +
          `${Math.round(event.distanceTraveledMeters)} m traveled` +
          (event.isOffRoute ? ', OFF ROUTE' : '')
        : '';
    document.querySelector('#location-text').textContent = event.location
      ? `${event.location.latitude.toFixed(6)}, ${event.location.longitude.toFixed(6)} (${event.location.courseDegrees ?? '-'}°)`
      : '';
  });
  FerrostarNavigation.addListener('routeDeviationDetected', event => {
    console.log('routeDeviationDetected', event);
  });
  FerrostarNavigation.addListener('navigationCompleted', () => {
    console.log('navigationCompleted');
  });

  document
    .querySelector('#start-navigation-button')
    .addEventListener('click', async () => {
      await FerrostarNavigation.startNavigation({
        osrmResponseJson,
        simulateLocation: document.querySelector('#simulate-location-toggle')
          .checked,
        simulationSpeedMultiplier: 10,
        voiceLanguage: 'en-US',
        continueInBackground: document.querySelector(
          '#continue-in-background-toggle',
        ).checked,
        androidNotification: {
          title: 'Capacitor Ferrostar Navigation',
        },
      });
    });
  document
    .querySelector('#replace-route-button')
    .addEventListener('click', async () => {
      await FerrostarNavigation.replaceRoute({ osrmResponseJson });
    });
  document
    .querySelector('#stop-navigation-button')
    .addEventListener('click', async () => {
      await FerrostarNavigation.stopNavigation();
    });
  document
    .querySelector('#mute-voice-button')
    .addEventListener('click', async () => {
      await FerrostarNavigation.muteVoice();
    });
  document
    .querySelector('#unmute-voice-button')
    .addEventListener('click', async () => {
      await FerrostarNavigation.unmuteVoice();
    });
});
