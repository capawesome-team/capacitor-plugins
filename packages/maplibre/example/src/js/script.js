// The stylesheet of MapLibre GL JS is required by the web implementation of the
// plugin. It is not shipped with the plugin.
import 'maplibre-gl/dist/maplibre-gl.css';

import {
  LayerType,
  MapLibre,
  MarkerIconAnchor,
  UserTrackingMode,
} from '@capawesome/capacitor-maplibre';

const elementId = 'map';
const geoJsonLayerId = 'cities-layer';
const geoJsonSourceId = 'cities-source';
const mapId = 'main-map';
const markerId = 'munich-marker';
const polylineId = 'route-polyline';

const augsburg = { latitude: 48.370545, longitude: 10.89779 };
const berlin = { latitude: 52.520008, longitude: 13.404954 };
const cologne = { latitude: 50.937531, longitude: 6.960279 };
const hamburg = { latitude: 53.551086, longitude: 9.993682 };
const munich = { latitude: 48.137154, longitude: 11.576124 };
const nuremberg = { latitude: 49.45203, longitude: 11.076665 };

const germanyBounds = {
  northeast: { latitude: 55.058347, longitude: 15.041896 },
  southwest: { latitude: 47.270111, longitude: 5.866342 },
};

const styleUrls = {
  demoTiles: 'https://demotiles.maplibre.org/style.json',
  positron: 'https://basemaps.cartocdn.com/gl/positron-gl-style/style.json',
};

const markerIconUrl = `data:image/svg+xml;utf8,${encodeURIComponent(
  '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><circle cx="16" cy="16" r="13" fill="#0f62fe" stroke="#ffffff" stroke-width="4"/></svg>',
)}`;

const citiesGeoJson = {
  features: [cologne, hamburg, nuremberg].map(city => ({
    geometry: { coordinates: [city.longitude, city.latitude], type: 'Point' },
    properties: {},
    type: 'Feature',
  })),
  type: 'FeatureCollection',
};

const eventNames = [
  'cameraIdle',
  'cameraMoveStarted',
  'mapClick',
  'markerClick',
  'markerDrag',
  'markerDragEnd',
  'markerDragStart',
  'userLocationChange',
];

const maxLogEntries = 50;

const logElement = document.querySelector('#event-log');
const logEntries = [];

let gesturesEnabled = true;
let markerMoved = false;

function log(message) {
  logEntries.unshift(`${new Date().toLocaleTimeString()}  ${message}`);
  logEntries.length = Math.min(logEntries.length, maxLogEntries);
  logElement.textContent = logEntries.join('\n');
}

function onClick(buttonId, handler) {
  const button = document.querySelector(`#${buttonId}`);
  button.addEventListener('click', async () => {
    try {
      await handler();
    } catch (error) {
      log(`error: ${error.message}`);
    }
  });
}

async function setStyle(url) {
  await MapLibre.setStyle({ mapId, url });
  log('style loaded, all markers, polylines and sources must be added again');
}

for (const eventName of eventNames) {
  MapLibre.addListener(eventName, event => {
    log(`${eventName}: ${JSON.stringify(event)}`);
  });
}

onClick('create-map-button', async () => {
  await MapLibre.createMap({
    center: munich,
    elementId,
    mapId,
    styleUrl: styleUrls.demoTiles,
    zoom: 5,
  });
  log('map created');
});

onClick('destroy-map-button', async () => {
  await MapLibre.destroyMap({ mapId });
  log('map destroyed');
});

onClick('toggle-gestures-button', async () => {
  gesturesEnabled = !gesturesEnabled;
  await MapLibre.setGesturesEnabled({
    mapId,
    pan: gesturesEnabled,
    rotate: gesturesEnabled,
    tilt: gesturesEnabled,
    zoom: gesturesEnabled,
  });
  log(`gestures ${gesturesEnabled ? 'enabled' : 'disabled'}`);
});

onClick('demo-tiles-style-button', () => setStyle(styleUrls.demoTiles));

onClick('positron-style-button', () => setStyle(styleUrls.positron));

onClick('set-camera-button', () =>
  MapLibre.setCamera({
    animate: true,
    animationDuration: 1000,
    bearing: 30,
    center: berlin,
    mapId,
    pitch: 45,
    zoom: 11,
  }),
);

onClick('fit-bounds-button', () =>
  MapLibre.fitBounds({
    animate: true,
    animationDuration: 1000,
    bounds: germanyBounds,
    mapId,
    padding: { bottom: 32, left: 32, right: 32, top: 32 },
  }),
);

onClick('get-camera-button', async () => {
  const result = await MapLibre.getCamera({ mapId });
  log(`getCamera: ${JSON.stringify(result)}`);
});

onClick('recenter-button', () =>
  MapLibre.setCamera({ animate: true, center: munich, mapId, zoom: 10 }),
);

onClick('add-markers-button', () =>
  MapLibre.addMarkers({
    mapId,
    markers: [
      { coordinates: munich, draggable: true, id: markerId },
      {
        coordinates: berlin,
        iconAnchor: MarkerIconAnchor.Center,
        iconSize: { height: 32, width: 32 },
        iconUrl: markerIconUrl,
        id: 'berlin-marker',
      },
    ],
  }),
);

onClick('update-marker-button', () => {
  markerMoved = !markerMoved;
  return MapLibre.updateMarkerById({
    animate: true,
    animationDuration: 1000,
    coordinates: markerMoved ? augsburg : munich,
    mapId,
    markerId,
    rotation: markerMoved ? 90 : 0,
  });
});

onClick('remove-marker-button', () =>
  MapLibre.removeMarkerById({ mapId, markerId }),
);

onClick('remove-all-markers-button', () =>
  MapLibre.removeAllMarkers({ mapId }),
);

onClick('add-polyline-button', () =>
  MapLibre.addPolyline({
    mapId,
    polyline: {
      color: '#ff6b00',
      coordinates: [munich, nuremberg, berlin],
      id: polylineId,
      width: 5,
    },
  }),
);

onClick('update-polyline-button', () =>
  MapLibre.updatePolylineById({
    color: '#00a878',
    coordinates: [munich, nuremberg, berlin, hamburg],
    mapId,
    opacity: 0.6,
    polylineId,
    width: 8,
  }),
);

onClick('remove-polyline-button', () =>
  MapLibre.removePolylineById({ mapId, polylineId }),
);

onClick('add-geojson-button', async () => {
  await MapLibre.addGeoJsonSource({
    data: citiesGeoJson,
    mapId,
    sourceId: geoJsonSourceId,
  });
  await MapLibre.addLayer({
    layerId: geoJsonLayerId,
    mapId,
    paint: {
      circleColor: '#7b3fe4',
      circleRadius: 8,
      circleStrokeColor: '#ffffff',
      circleStrokeWidth: 2,
    },
    sourceId: geoJsonSourceId,
    type: LayerType.Circle,
  });
});

onClick('remove-geojson-button', async () => {
  await MapLibre.removeLayerById({ layerId: geoJsonLayerId, mapId });
  await MapLibre.removeGeoJsonSourceById({ mapId, sourceId: geoJsonSourceId });
});

onClick('enable-user-location-button', () =>
  MapLibre.enableUserLocation({ mapId, trackingMode: UserTrackingMode.Follow }),
);

onClick('disable-user-location-button', () =>
  MapLibre.disableUserLocation({ mapId }),
);

onClick('check-permissions-button', async () => {
  const result = await MapLibre.checkPermissions();
  log(`checkPermissions: ${JSON.stringify(result)}`);
});

onClick('request-permissions-button', async () => {
  const result = await MapLibre.requestPermissions();
  log(`requestPermissions: ${JSON.stringify(result)}`);
});
