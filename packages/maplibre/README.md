# Capacitor MapLibre Plugin

Unofficial Capacitor plugin to create native [MapLibre](https://maplibre.org/) maps on Android, iOS, and Web.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor MapLibre plugin renders interactive maps with the native MapLibre SDKs on Android and iOS, and with MapLibre GL JS on the Web. Here are some of the key features:

- 🗺️ **Native Rendering**: Renders a native map view on Android and iOS instead of a map inside the web view.
- 🆓 **No API Key**: Built on the open-source MapLibre stack, so no vendor account or API key is required.
- 🎨 **Map Styles**: Load any MapLibre style from a URL or a JSON string, and swap it at runtime.
- 🎥 **Camera Controls**: Set center, zoom, bearing and pitch, or fit bounds — animated or instantly.
- 📍 **Markers**: Add markers with custom icons, anchors, sizes, opacity and rotation, and update them with smooth animations.
- ✋ **Draggable Markers**: Let users drag markers and react to drag events on Android and Web.
- 〰️ **Polylines**: Draw and update routes and tracks with custom color, width and opacity.
- 🧬 **GeoJSON**: Add GeoJSON sources from data or URL and render them with styled line, fill and circle layers.
- 🧭 **User Location**: Display the location of the user and follow it with course or heading tracking modes.
- 🎛️ **Gesture Controls**: Enable or disable panning, zooming, rotating and tilting at any time.
- 🧩 **Multi-Instance**: Create and control multiple maps at the same time by ID.
- 🪟 **DOM Overlays**: Place HTML elements such as floating action buttons or bottom sheets above the map — they just work.
- 🔄 **Automatic Sync**: The native map follows the position and scroll offset of its element automatically.
- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The MapLibre plugin is typically used wherever you want to show geographical data in your app, for example:

- **Delivery & fleet tracking**: Follow vehicles live with animated markers and rotation.
- **Store & branch locators**: Display locations as markers and zoom to the results of a search.
- **Outdoor & fitness apps**: Render recorded tracks as polylines and follow the location of the user.
- **Field service**: Show jobs, areas and routes from GeoJSON data on a native map.
- **Real estate & travel**: Browse listings on a map with custom styles that match your brand.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-maplibre` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-maplibre
npx cap sync
```

### Android

On Android, the plugin uses the [MapLibre Native SDK for Android](https://github.com/maplibre/maplibre-native).

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$mapLibreVersion` version of `org.maplibre.gl:android-sdk-vulkan-opengl` (default: `13.5.0`)
- `$mapLibreAnnotationVersion` version of `org.maplibre.gl:android-plugin-annotation` (default: `4.0.0`)

#### Permissions

Location permissions are only required if you want to display the location of the user on the map. In that case, add the following elements to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Required if you want to display the location of the user on the map. -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

### iOS

On iOS, the plugin uses the [MapLibre Native SDK for iOS](https://github.com/maplibre/maplibre-native). It can be integrated via Swift Package Manager or CocoaPods.

If you want to display the location of the user on the map, add the `NSLocationWhenInUseUsageDescription` key to your `Info.plist` file:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>The app needs your location to display it on the map.</string>
```

Without this key, `checkPermissions()`, `requestPermissions()` and `enableUserLocation(...)` reject with an error.

### Web

The web implementation uses [MapLibre GL JS](https://maplibre.org/maplibre-gl-js/docs/), which is installed automatically as a dependency of the plugin. Its stylesheet is not bundled with the plugin, so you must import it once in your app:

```typescript
import 'maplibre-gl/dist/maplibre-gl.css';
```

Without the stylesheet, the map canvas and the markers are mispositioned.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Getting started

Add an **empty** element to your app that defines the position and size of the map:

```html
<div id="map"></div>
```

```css
#map {
  /* The map must not have a background so that the native map stays visible. */
  background: transparent;
  height: 400px;
  width: 100%;
}
```

On Android and iOS, the map is rendered as a native view **behind** the web view. Your app must therefore fulfill the following contract:

- The map element must be **empty**. Do not render any content into it.
- The map element **and every ancestor** that covers the map region (including `body`) must have a transparent background (`background: transparent`). Otherwise the web view paints over the native map and the map is not visible.

Elements that are not ancestors of the map element, such as floating action buttons or bottom sheets, can be layered above the map as usual. The plugin keeps the position and size of the native map in sync with the map element automatically, including while the page is scrolled or resized.

### Create a map

```typescript
import { MapLibre } from '@capawesome/capacitor-maplibre';

const createMap = async () => {
  await MapLibre.createMap({
    center: { latitude: 48.137154, longitude: 11.576124 },
    elementId: 'map',
    mapId: 'my-map',
    styleUrl: 'https://basemaps.cartocdn.com/gl/positron-gl-style/style.json',
    zoom: 12,
  });
};
```

If no style is provided, the [MapLibre demo style](https://demotiles.maplibre.org/style.json) is used, which is a test style and not intended for production use (see [FAQ](#where-do-i-get-map-styles-and-do-i-need-an-api-key)).

### Move the camera

```typescript
import { MapLibre } from '@capawesome/capacitor-maplibre';

const moveCamera = async () => {
  await MapLibre.setCamera({
    animate: true,
    animationDuration: 1000,
    bearing: 30,
    center: { latitude: 52.520008, longitude: 13.404954 },
    mapId: 'my-map',
    pitch: 45,
    zoom: 11,
  });
  await MapLibre.fitBounds({
    animate: true,
    bounds: {
      northeast: { latitude: 55.058347, longitude: 15.041896 },
      southwest: { latitude: 47.270111, longitude: 5.866342 },
    },
    mapId: 'my-map',
    padding: { bottom: 32, left: 32, right: 32, top: 32 },
  });
};
```

### Add markers

```typescript
import { MapLibre, MarkerIconAnchor } from '@capawesome/capacitor-maplibre';

const addMarker = async () => {
  await MapLibre.addMarker({
    mapId: 'my-map',
    marker: {
      coordinates: { latitude: 48.137154, longitude: 11.576124 },
      iconAnchor: MarkerIconAnchor.Center,
      iconSize: { height: 32, width: 32 },
      iconUrl: 'https://example.com/marker.png',
      id: 'my-marker',
    },
  });
};

const moveMarker = async () => {
  await MapLibre.updateMarkerById({
    animate: true,
    animationDuration: 1000,
    coordinates: { latitude: 48.370545, longitude: 10.89779 },
    mapId: 'my-map',
    markerId: 'my-marker',
    rotation: 90,
  });
};

const removeMarker = async () => {
  await MapLibre.removeMarkerById({ mapId: 'my-map', markerId: 'my-marker' });
};
```

### Add polylines

```typescript
import { MapLibre } from '@capawesome/capacitor-maplibre';

const addPolyline = async () => {
  await MapLibre.addPolyline({
    mapId: 'my-map',
    polyline: {
      color: '#3887be',
      coordinates: [
        { latitude: 48.137154, longitude: 11.576124 },
        { latitude: 49.45203, longitude: 11.076665 },
        { latitude: 52.520008, longitude: 13.404954 },
      ],
      id: 'my-polyline',
      width: 5,
    },
  });
};
```

### Add GeoJSON data

```typescript
import { LayerType, MapLibre } from '@capawesome/capacitor-maplibre';

const addGeoJson = async () => {
  await MapLibre.addGeoJsonSource({
    mapId: 'my-map',
    sourceId: 'my-source',
    url: 'https://example.com/routes.geojson',
  });
  await MapLibre.addLayer({
    layerId: 'my-layer',
    mapId: 'my-map',
    paint: { lineColor: '#3887be', lineWidth: 4 },
    sourceId: 'my-source',
    type: LayerType.Line,
  });
};
```

### Display the location of the user

```typescript
import { MapLibre, UserTrackingMode } from '@capawesome/capacitor-maplibre';

const enableUserLocation = async () => {
  let status = await MapLibre.checkPermissions();
  if (status.location === 'prompt') {
    status = await MapLibre.requestPermissions();
  }
  if (status.location !== 'granted') {
    return;
  }
  await MapLibre.enableUserLocation({
    mapId: 'my-map',
    trackingMode: UserTrackingMode.Follow,
  });
};
```

### Listen for events

```typescript
import { MapLibre } from '@capawesome/capacitor-maplibre';

const addListeners = async () => {
  await MapLibre.addListener('mapClick', event => {
    console.log('Map clicked:', event.coordinates);
  });
  await MapLibre.addListener('markerClick', event => {
    console.log('Marker clicked:', event.markerId);
  });
  await MapLibre.addListener('cameraIdle', event => {
    console.log('Camera idle:', event.camera);
  });
};
```

### Destroy a map

```typescript
import { MapLibre } from '@capawesome/capacitor-maplibre';

const destroyMap = async () => {
  await MapLibre.destroyMap({ mapId: 'my-map' });
};
```

## API

<docgen-index>

* [`addGeoJsonSource(...)`](#addgeojsonsource)
* [`addLayer(...)`](#addlayer)
* [`addMarker(...)`](#addmarker)
* [`addMarkers(...)`](#addmarkers)
* [`addPolyline(...)`](#addpolyline)
* [`addPolylines(...)`](#addpolylines)
* [`checkPermissions()`](#checkpermissions)
* [`createMap(...)`](#createmap)
* [`destroyMap(...)`](#destroymap)
* [`disableUserLocation(...)`](#disableuserlocation)
* [`elementFromPointResult(...)`](#elementfrompointresult)
* [`enableUserLocation(...)`](#enableuserlocation)
* [`fitBounds(...)`](#fitbounds)
* [`getCamera(...)`](#getcamera)
* [`removeAllMarkers(...)`](#removeallmarkers)
* [`removeAllPolylines(...)`](#removeallpolylines)
* [`removeGeoJsonSourceById(...)`](#removegeojsonsourcebyid)
* [`removeLayerById(...)`](#removelayerbyid)
* [`removeMarkerById(...)`](#removemarkerbyid)
* [`removeMarkersByIds(...)`](#removemarkersbyids)
* [`removePolylineById(...)`](#removepolylinebyid)
* [`removePolylinesByIds(...)`](#removepolylinesbyids)
* [`requestPermissions()`](#requestpermissions)
* [`setCamera(...)`](#setcamera)
* [`setFrame(...)`](#setframe)
* [`setGesturesEnabled(...)`](#setgesturesenabled)
* [`setStyle(...)`](#setstyle)
* [`updateGeoJsonSourceById(...)`](#updategeojsonsourcebyid)
* [`updateMarkerById(...)`](#updatemarkerbyid)
* [`updatePolylineById(...)`](#updatepolylinebyid)
* [`addListener('cameraIdle', ...)`](#addlistenercameraidle-)
* [`addListener('cameraMoveStarted', ...)`](#addlistenercameramovestarted-)
* [`addListener('elementFromPointRequest', ...)`](#addlistenerelementfrompointrequest-)
* [`addListener('mapClick', ...)`](#addlistenermapclick-)
* [`addListener('markerClick', ...)`](#addlistenermarkerclick-)
* [`addListener('markerDrag', ...)`](#addlistenermarkerdrag-)
* [`addListener('markerDragEnd', ...)`](#addlistenermarkerdragend-)
* [`addListener('markerDragStart', ...)`](#addlistenermarkerdragstart-)
* [`addListener('userLocationChange', ...)`](#addlisteneruserlocationchange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addGeoJsonSource(...)

```typescript
addGeoJsonSource(options: AddGeoJsonSourceOptions) => Promise<void>
```

Add a GeoJSON source to the map.

Use `addLayer(...)` to render the data of the source.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#addgeojsonsourceoptions">AddGeoJsonSourceOptions</a></code> |

**Since:** 0.1.0

--------------------


### addLayer(...)

```typescript
addLayer(options: AddLayerOptions) => Promise<void>
```

Add a layer to the map that renders the data of a GeoJSON source.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#addlayeroptions">AddLayerOptions</a></code> |

**Since:** 0.1.0

--------------------


### addMarker(...)

```typescript
addMarker(options: AddMarkerOptions) => Promise<void>
```

Add a marker to the map.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#addmarkeroptions">AddMarkerOptions</a></code> |

**Since:** 0.1.0

--------------------


### addMarkers(...)

```typescript
addMarkers(options: AddMarkersOptions) => Promise<void>
```

Add multiple markers to the map.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#addmarkersoptions">AddMarkersOptions</a></code> |

**Since:** 0.1.0

--------------------


### addPolyline(...)

```typescript
addPolyline(options: AddPolylineOptions) => Promise<void>
```

Add a polyline to the map.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#addpolylineoptions">AddPolylineOptions</a></code> |

**Since:** 0.1.0

--------------------


### addPolylines(...)

```typescript
addPolylines(options: AddPolylinesOptions) => Promise<void>
```

Add multiple polylines to the map.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#addpolylinesoptions">AddPolylinesOptions</a></code> |

**Since:** 0.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the location permission.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### createMap(...)

```typescript
createMap(options: CreateMapOptions) => Promise<void>
```

Create a new map.

The map is rendered into the element with the given element ID. On
Android and iOS, the map is rendered as a native view behind the web
view, so the element and everything above the map must be transparent.

The promise resolves as soon as the style of the map has finished
loading.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#createmapoptions">CreateMapOptions</a></code> |

**Since:** 0.1.0

--------------------


### destroyMap(...)

```typescript
destroyMap(options: DestroyMapOptions) => Promise<void>
```

Destroy a map and release all its resources.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#destroymapoptions">DestroyMapOptions</a></code> |

**Since:** 0.1.0

--------------------


### disableUserLocation(...)

```typescript
disableUserLocation(options: DisableUserLocationOptions) => Promise<void>
```

Stop displaying the location of the user on the map.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#disableuserlocationoptions">DisableUserLocationOptions</a></code> |

**Since:** 0.1.0

--------------------


### elementFromPointResult(...)

```typescript
elementFromPointResult(options: ElementFromPointResultOptions) => Promise<void>
```

Answer an `elementFromPointRequest` event.

This method is called automatically by the plugin and must not be
called manually.

Only available on Android.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#elementfrompointresultoptions">ElementFromPointResultOptions</a></code> |

**Since:** 0.1.0

--------------------


### enableUserLocation(...)

```typescript
enableUserLocation(options: EnableUserLocationOptions) => Promise<void>
```

Display the location of the user on the map.

Call this method again to change the tracking mode.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#enableuserlocationoptions">EnableUserLocationOptions</a></code> |

**Since:** 0.1.0

--------------------


### fitBounds(...)

```typescript
fitBounds(options: FitBoundsOptions) => Promise<void>
```

Move the camera so that the given bounds are visible.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#fitboundsoptions">FitBoundsOptions</a></code> |

**Since:** 0.1.0

--------------------


### getCamera(...)

```typescript
getCamera(options: GetCameraOptions) => Promise<GetCameraResult>
```

Get the current camera of the map.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#getcameraoptions">GetCameraOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcameraresult">GetCameraResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllMarkers(...)

```typescript
removeAllMarkers(options: RemoveAllMarkersOptions) => Promise<void>
```

Remove all markers from the map.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removeallmarkersoptions">RemoveAllMarkersOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeAllPolylines(...)

```typescript
removeAllPolylines(options: RemoveAllPolylinesOptions) => Promise<void>
```

Remove all polylines from the map.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removeallpolylinesoptions">RemoveAllPolylinesOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeGeoJsonSourceById(...)

```typescript
removeGeoJsonSourceById(options: RemoveGeoJsonSourceByIdOptions) => Promise<void>
```

Remove a GeoJSON source from the map.

All layers that use the source must be removed first.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removegeojsonsourcebyidoptions">RemoveGeoJsonSourceByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeLayerById(...)

```typescript
removeLayerById(options: RemoveLayerByIdOptions) => Promise<void>
```

Remove a layer from the map.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removelayerbyidoptions">RemoveLayerByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeMarkerById(...)

```typescript
removeMarkerById(options: RemoveMarkerByIdOptions) => Promise<void>
```

Remove a marker from the map.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removemarkerbyidoptions">RemoveMarkerByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeMarkersByIds(...)

```typescript
removeMarkersByIds(options: RemoveMarkersByIdsOptions) => Promise<void>
```

Remove multiple markers from the map.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removemarkersbyidsoptions">RemoveMarkersByIdsOptions</a></code> |

**Since:** 0.1.0

--------------------


### removePolylineById(...)

```typescript
removePolylineById(options: RemovePolylineByIdOptions) => Promise<void>
```

Remove a polyline from the map.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removepolylinebyidoptions">RemovePolylineByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### removePolylinesByIds(...)

```typescript
removePolylinesByIds(options: RemovePolylinesByIdsOptions) => Promise<void>
```

Remove multiple polylines from the map.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removepolylinesbyidsoptions">RemovePolylinesByIdsOptions</a></code> |

**Since:** 0.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the location permission.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### setCamera(...)

```typescript
setCamera(options: SetCameraOptions) => Promise<void>
```

Move the camera of the map.

Only the provided properties are changed.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setcameraoptions">SetCameraOptions</a></code> |

**Since:** 0.1.0

--------------------


### setFrame(...)

```typescript
setFrame(options: SetFrameOptions) => Promise<void>
```

Update the position and size of the map viewport.

This method is called automatically by the plugin and usually does not
need to be called manually.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setframeoptions">SetFrameOptions</a></code> |

**Since:** 0.1.0

--------------------


### setGesturesEnabled(...)

```typescript
setGesturesEnabled(options: SetGesturesEnabledOptions) => Promise<void>
```

Enable or disable the gestures of the map.

Only the provided gestures are changed.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setgesturesenabledoptions">SetGesturesEnabledOptions</a></code> |

**Since:** 0.1.0

--------------------


### setStyle(...)

```typescript
setStyle(options: SetStyleOptions) => Promise<void>
```

Load a new style into the map.

All markers, polylines, sources and layers must be added again after
the new style has been loaded.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setstyleoptions">SetStyleOptions</a></code> |

**Since:** 0.1.0

--------------------


### updateGeoJsonSourceById(...)

```typescript
updateGeoJsonSourceById(options: UpdateGeoJsonSourceByIdOptions) => Promise<void>
```

Update the data of a GeoJSON source.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updategeojsonsourcebyidoptions">UpdateGeoJsonSourceByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### updateMarkerById(...)

```typescript
updateMarkerById(options: UpdateMarkerByIdOptions) => Promise<void>
```

Update a marker on the map.

Only the provided properties are changed.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatemarkerbyidoptions">UpdateMarkerByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### updatePolylineById(...)

```typescript
updatePolylineById(options: UpdatePolylineByIdOptions) => Promise<void>
```

Update a polyline on the map.

Only the provided properties are changed.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatepolylinebyidoptions">UpdatePolylineByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('cameraIdle', ...)

```typescript
addListener(eventName: 'cameraIdle', listenerFunc: (event: CameraIdleEvent) => void) => Promise<PluginListenerHandle>
```

Called when the camera of a map has stopped moving.

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'cameraIdle'</code>                                                       |
| **`listenerFunc`** | <code>(event: <a href="#cameraidleevent">CameraIdleEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('cameraMoveStarted', ...)

```typescript
addListener(eventName: 'cameraMoveStarted', listenerFunc: (event: CameraMoveStartedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the camera of a map has started moving.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'cameraMoveStarted'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#cameramovestartedevent">CameraMoveStartedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('elementFromPointRequest', ...)

```typescript
addListener(eventName: 'elementFromPointRequest', listenerFunc: (event: ElementFromPointRequestEvent) => void) => Promise<PluginListenerHandle>
```

Called when the plugin needs to know which map is located at a point of
the screen.

This event is handled automatically by the plugin and must not be
handled manually.

Only available on Android.

| Param              | Type                                                                                                      |
| ------------------ | --------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'elementFromPointRequest'</code>                                                                    |
| **`listenerFunc`** | <code>(event: <a href="#elementfrompointrequestevent">ElementFromPointRequestEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('mapClick', ...)

```typescript
addListener(eventName: 'mapClick', listenerFunc: (event: MapClickEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user taps on a map.

| Param              | Type                                                                        |
| ------------------ | --------------------------------------------------------------------------- |
| **`eventName`**    | <code>'mapClick'</code>                                                     |
| **`listenerFunc`** | <code>(event: <a href="#mapclickevent">MapClickEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('markerClick', ...)

```typescript
addListener(eventName: 'markerClick', listenerFunc: (event: MarkerClickEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user taps on a marker.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'markerClick'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#markerclickevent">MarkerClickEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('markerDrag', ...)

```typescript
addListener(eventName: 'markerDrag', listenerFunc: (event: MarkerDragEvent) => void) => Promise<PluginListenerHandle>
```

Called while the user drags a marker.

Only available on Android and Web.

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'markerDrag'</code>                                                       |
| **`listenerFunc`** | <code>(event: <a href="#markerdragevent">MarkerDragEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('markerDragEnd', ...)

```typescript
addListener(eventName: 'markerDragEnd', listenerFunc: (event: MarkerDragEndEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user has stopped dragging a marker.

Only available on Android and Web.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'markerDragEnd'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#markerdragendevent">MarkerDragEndEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('markerDragStart', ...)

```typescript
addListener(eventName: 'markerDragStart', listenerFunc: (event: MarkerDragStartEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user has started dragging a marker.

Only available on Android and Web.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'markerDragStart'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#markerdragstartevent">MarkerDragStartEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('userLocationChange', ...)

```typescript
addListener(eventName: 'userLocationChange', listenerFunc: (event: UserLocationChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the location of the user changes.

The event is only emitted while the location of the user is displayed
on the map.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'userLocationChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#userlocationchangeevent">UserLocationChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### AddGeoJsonSourceOptions

| Prop           | Type                                       | Description                                                                                  | Since |
| -------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------- | ----- |
| **`data`**     | <code>Record&lt;string, unknown&gt;</code> | The GeoJSON data of the source. Exactly one of `data` and `url` must be provided.            | 0.1.0 |
| **`mapId`**    | <code>string</code>                        | The unique identifier of the map.                                                            | 0.1.0 |
| **`sourceId`** | <code>string</code>                        | The unique identifier of the source.                                                         | 0.1.0 |
| **`url`**      | <code>string</code>                        | The URL of the GeoJSON data of the source. Exactly one of `data` and `url` must be provided. | 0.1.0 |


#### AddLayerOptions

| Prop               | Type                                              | Description                                                                                                                               | Since |
| ------------------ | ------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`belowLayerId`** | <code>string</code>                               | The unique identifier of the layer below which the new layer is inserted. If not provided, the layer is added on top of all other layers. | 0.1.0 |
| **`layerId`**      | <code>string</code>                               | The unique identifier of the layer.                                                                                                       | 0.1.0 |
| **`mapId`**        | <code>string</code>                               | The unique identifier of the map.                                                                                                         | 0.1.0 |
| **`maxZoom`**      | <code>number</code>                               | The maximum zoom level at which the layer is visible.                                                                                     | 0.1.0 |
| **`minZoom`**      | <code>number</code>                               | The minimum zoom level at which the layer is visible.                                                                                     | 0.1.0 |
| **`paint`**        | <code><a href="#layerpaint">LayerPaint</a></code> | The paint properties of the layer.                                                                                                        | 0.1.0 |
| **`sourceId`**     | <code>string</code>                               | The unique identifier of the source whose data is rendered by the layer.                                                                  | 0.1.0 |
| **`type`**         | <code><a href="#layertype">LayerType</a></code>   | The type of the layer.                                                                                                                    | 0.1.0 |


#### LayerPaint

The paint properties of a layer.

Properties that do not apply to the type of the layer are ignored.

| Prop                    | Type                | Description                                                                                                                              | Since |
| ----------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`circleColor`**       | <code>string</code> | The fill color of the circles as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. Only applies to layers of type `circle`.   | 0.1.0 |
| **`circleOpacity`**     | <code>number</code> | The opacity of the circles as a value between `0` and `1`. Only applies to layers of type `circle`.                                      | 0.1.0 |
| **`circleRadius`**      | <code>number</code> | The radius of the circles in CSS pixels. Only applies to layers of type `circle`.                                                        | 0.1.0 |
| **`circleStrokeColor`** | <code>string</code> | The stroke color of the circles as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. Only applies to layers of type `circle`. | 0.1.0 |
| **`circleStrokeWidth`** | <code>number</code> | The stroke width of the circles in CSS pixels. Only applies to layers of type `circle`.                                                  | 0.1.0 |
| **`fillColor`**         | <code>string</code> | The fill color of the areas as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. Only applies to layers of type `fill`.       | 0.1.0 |
| **`fillOpacity`**       | <code>number</code> | The opacity of the areas as a value between `0` and `1`. Only applies to layers of type `fill`.                                          | 0.1.0 |
| **`fillOutlineColor`**  | <code>string</code> | The outline color of the areas as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. Only applies to layers of type `fill`.    | 0.1.0 |
| **`lineColor`**         | <code>string</code> | The color of the lines as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. Only applies to layers of type `line`.            | 0.1.0 |
| **`lineOpacity`**       | <code>number</code> | The opacity of the lines as a value between `0` and `1`. Only applies to layers of type `line`.                                          | 0.1.0 |
| **`lineWidth`**         | <code>number</code> | The width of the lines in CSS pixels. Only applies to layers of type `line`.                                                             | 0.1.0 |


#### AddMarkerOptions

| Prop         | Type                                      | Description                       | Since |
| ------------ | ----------------------------------------- | --------------------------------- | ----- |
| **`mapId`**  | <code>string</code>                       | The unique identifier of the map. | 0.1.0 |
| **`marker`** | <code><a href="#marker">Marker</a></code> | The marker to add.                | 0.1.0 |


#### Marker

A marker on a map.

| Prop              | Type                                                          | Description                                                                                                          | Default                              | Since |
| ----------------- | ------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- | ------------------------------------ | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code>                     | The geographical coordinate of the marker.                                                                           |                                      | 0.1.0 |
| **`draggable`**   | <code>boolean</code>                                          | Whether the user can drag the marker. Only available on Android and Web.                                             | <code>false</code>                   | 0.1.0 |
| **`iconAnchor`**  | <code><a href="#markericonanchor">MarkerIconAnchor</a></code> | The part of the icon that is placed on the coordinate of the marker.                                                 | <code>MarkerIconAnchor.Bottom</code> | 0.1.0 |
| **`iconSize`**    | <code><a href="#markericonsize">MarkerIconSize</a></code>     | The size the icon is scaled to. If not provided, the intrinsic size of the icon is used.                             |                                      | 0.1.0 |
| **`iconUrl`**     | <code>string</code>                                           | The URL of the icon of the marker. Must be a `https` URL or a data URI. If not provided, a default pin icon is used. |                                      | 0.1.0 |
| **`id`**          | <code>string</code>                                           | The unique identifier of the marker.                                                                                 |                                      | 0.1.0 |
| **`opacity`**     | <code>number</code>                                           | The opacity of the marker as a value between `0` and `1`.                                                            | <code>1</code>                       | 0.1.0 |
| **`rotation`**    | <code>number</code>                                           | The rotation of the icon in degrees clockwise.                                                                       | <code>0</code>                       | 0.1.0 |


#### LatLng

A geographical coordinate.

| Prop            | Type                | Description               | Since |
| --------------- | ------------------- | ------------------------- | ----- |
| **`latitude`**  | <code>number</code> | The latitude in degrees.  | 0.1.0 |
| **`longitude`** | <code>number</code> | The longitude in degrees. | 0.1.0 |


#### MarkerIconSize

The size of the icon of a marker in CSS pixels.

| Prop         | Type                | Description                           | Since |
| ------------ | ------------------- | ------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the icon in CSS pixels. | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the icon in CSS pixels.  | 0.1.0 |


#### AddMarkersOptions

| Prop          | Type                  | Description                       | Since |
| ------------- | --------------------- | --------------------------------- | ----- |
| **`mapId`**   | <code>string</code>   | The unique identifier of the map. | 0.1.0 |
| **`markers`** | <code>Marker[]</code> | The markers to add.               | 0.1.0 |


#### AddPolylineOptions

| Prop           | Type                                          | Description                       | Since |
| -------------- | --------------------------------------------- | --------------------------------- | ----- |
| **`mapId`**    | <code>string</code>                           | The unique identifier of the map. | 0.1.0 |
| **`polyline`** | <code><a href="#polyline">Polyline</a></code> | The polyline to add.              | 0.1.0 |


#### Polyline

A polyline on a map.

| Prop              | Type                  | Description                                                                               | Default                | Since |
| ----------------- | --------------------- | ----------------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`color`**       | <code>string</code>   | The color of the polyline as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. | <code>'#3887be'</code> | 0.1.0 |
| **`coordinates`** | <code>LatLng[]</code> | The geographical coordinates of the polyline.                                             |                        | 0.1.0 |
| **`id`**          | <code>string</code>   | The unique identifier of the polyline.                                                    |                        | 0.1.0 |
| **`opacity`**     | <code>number</code>   | The opacity of the polyline as a value between `0` and `1`.                               | <code>1</code>         | 0.1.0 |
| **`width`**       | <code>number</code>   | The width of the polyline in CSS pixels.                                                  | <code>4</code>         | 0.1.0 |


#### AddPolylinesOptions

| Prop            | Type                    | Description                       | Since |
| --------------- | ----------------------- | --------------------------------- | ----- |
| **`mapId`**     | <code>string</code>     | The unique identifier of the map. | 0.1.0 |
| **`polylines`** | <code>Polyline[]</code> | The polylines to add.             | 0.1.0 |


#### PermissionStatus

| Prop           | Type                                                        | Description                                      | Since |
| -------------- | ----------------------------------------------------------- | ------------------------------------------------ | ----- |
| **`location`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of the location permission. | 0.1.0 |


#### CreateMapOptions

| Prop            | Type                                                        | Description                                                                                                   | Default                                                  | Since |
| --------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- | ----- |
| **`bearing`**   | <code>number</code>                                         | The direction the camera points in, in degrees clockwise from north.                                          | <code>0</code>                                           | 0.1.0 |
| **`center`**    | <code><a href="#latlng">LatLng</a></code>                   | The geographical coordinate at the center of the map. If not provided, the center of the style is used.       |                                                          | 0.1.0 |
| **`elementId`** | <code>string</code>                                         | The ID of the DOM element the map is rendered into. The element must be empty and must not have a background. |                                                          | 0.1.0 |
| **`gestures`**  | <code><a href="#gesturesettings">GestureSettings</a></code> | The gestures the user can perform on the map.                                                                 |                                                          | 0.1.0 |
| **`mapId`**     | <code>string</code>                                         | The unique identifier of the map.                                                                             |                                                          | 0.1.0 |
| **`maxZoom`**   | <code>number</code>                                         | The maximum zoom level of the map. If not provided, the maximum zoom level of the style is used.              |                                                          | 0.1.0 |
| **`minZoom`**   | <code>number</code>                                         | The minimum zoom level of the map. If not provided, the minimum zoom level of the style is used.              |                                                          | 0.1.0 |
| **`pitch`**     | <code>number</code>                                         | The tilt of the camera in degrees, measured from the plane of the map.                                        | <code>0</code>                                           | 0.1.0 |
| **`styleJson`** | <code>string</code>                                         | The style of the map as a JSON string. At most one of `styleJson` and `styleUrl` may be provided.             |                                                          | 0.1.0 |
| **`styleUrl`**  | <code>string</code>                                         | The URL of the style of the map. At most one of `styleJson` and `styleUrl` may be provided.                   | <code>'https://demotiles.maplibre.org/style.json'</code> | 0.1.0 |
| **`zoom`**      | <code>number</code>                                         | The zoom level of the map.                                                                                    | <code>0</code>                                           | 0.1.0 |


#### GestureSettings

The gestures the user can perform on a map.

| Prop         | Type                 | Description                                               | Default           | Since |
| ------------ | -------------------- | --------------------------------------------------------- | ----------------- | ----- |
| **`pan`**    | <code>boolean</code> | Whether the user can move the camera by dragging the map. | <code>true</code> | 0.1.0 |
| **`rotate`** | <code>boolean</code> | Whether the user can rotate the camera.                   | <code>true</code> | 0.1.0 |
| **`tilt`**   | <code>boolean</code> | Whether the user can tilt the camera.                     | <code>true</code> | 0.1.0 |
| **`zoom`**   | <code>boolean</code> | Whether the user can zoom the camera.                     | <code>true</code> | 0.1.0 |


#### DestroyMapOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`mapId`** | <code>string</code> | The unique identifier of the map. | 0.1.0 |


#### DisableUserLocationOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`mapId`** | <code>string</code> | The unique identifier of the map. | 0.1.0 |


#### ElementFromPointResultOptions

| Prop            | Type                        | Description                                                                                                          | Since |
| --------------- | --------------------------- | -------------------------------------------------------------------------------------------------------------------- | ----- |
| **`mapId`**     | <code>string \| null</code> | The unique identifier of the map at the requested point. Must be `null` if no map is located at the requested point. | 0.1.0 |
| **`requestId`** | <code>string</code>         | The unique identifier of the request.                                                                                | 0.1.0 |


#### EnableUserLocationOptions

| Prop               | Type                                                          | Description                                                                                                                               | Default                            | Since |
| ------------------ | ------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- | ----- |
| **`mapId`**        | <code>string</code>                                           | The unique identifier of the map.                                                                                                         |                                    | 0.1.0 |
| **`trackingMode`** | <code><a href="#usertrackingmode">UserTrackingMode</a></code> | The mode used to track the location of the user with the camera. On Web, `FollowWithCourse` and `FollowWithHeading` behave like `Follow`. | <code>UserTrackingMode.None</code> | 0.1.0 |


#### FitBoundsOptions

| Prop                    | Type                                        | Description                                                | Default            | Since |
| ----------------------- | ------------------------------------------- | ---------------------------------------------------------- | ------------------ | ----- |
| **`animate`**           | <code>boolean</code>                        | Whether the camera movement is animated.                   | <code>false</code> | 0.1.0 |
| **`animationDuration`** | <code>number</code>                         | The duration of the animation in milliseconds.             | <code>300</code>   | 0.1.0 |
| **`bounds`**            | <code><a href="#bounds">Bounds</a></code>   | The bounds that must be visible after the camera movement. |                    | 0.1.0 |
| **`mapId`**             | <code>string</code>                         | The unique identifier of the map.                          |                    | 0.1.0 |
| **`maxZoom`**           | <code>number</code>                         | The maximum zoom level the camera may reach.               |                    | 0.1.0 |
| **`padding`**           | <code><a href="#padding">Padding</a></code> | The padding between the bounds and the edges of the map.   |                    | 0.1.0 |


#### Bounds

A geographical area, defined by its southwest and northeast corner.

| Prop            | Type                                      | Description                       | Since |
| --------------- | ----------------------------------------- | --------------------------------- | ----- |
| **`northeast`** | <code><a href="#latlng">LatLng</a></code> | The northeast corner of the area. | 0.1.0 |
| **`southwest`** | <code><a href="#latlng">LatLng</a></code> | The southwest corner of the area. | 0.1.0 |


#### Padding

The padding between the content of a map and its edges in CSS pixels.

| Prop         | Type                | Description                                   | Default        | Since |
| ------------ | ------------------- | --------------------------------------------- | -------------- | ----- |
| **`bottom`** | <code>number</code> | The padding at the bottom edge in CSS pixels. | <code>0</code> | 0.1.0 |
| **`left`**   | <code>number</code> | The padding at the left edge in CSS pixels.   | <code>0</code> | 0.1.0 |
| **`right`**  | <code>number</code> | The padding at the right edge in CSS pixels.  | <code>0</code> | 0.1.0 |
| **`top`**    | <code>number</code> | The padding at the top edge in CSS pixels.    | <code>0</code> | 0.1.0 |


#### GetCameraResult

| Prop         | Type                                      | Description                    | Since |
| ------------ | ----------------------------------------- | ------------------------------ | ----- |
| **`camera`** | <code><a href="#camera">Camera</a></code> | The current camera of the map. | 0.1.0 |


#### Camera

The camera of a map.

| Prop          | Type                                      | Description                                                            | Since |
| ------------- | ----------------------------------------- | ---------------------------------------------------------------------- | ----- |
| **`bearing`** | <code>number</code>                       | The direction the camera points in, in degrees clockwise from north.   | 0.1.0 |
| **`center`**  | <code><a href="#latlng">LatLng</a></code> | The geographical coordinate at the center of the map.                  | 0.1.0 |
| **`pitch`**   | <code>number</code>                       | The tilt of the camera in degrees, measured from the plane of the map. | 0.1.0 |
| **`zoom`**    | <code>number</code>                       | The zoom level of the map.                                             | 0.1.0 |


#### GetCameraOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`mapId`** | <code>string</code> | The unique identifier of the map. | 0.1.0 |


#### RemoveAllMarkersOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`mapId`** | <code>string</code> | The unique identifier of the map. | 0.1.0 |


#### RemoveAllPolylinesOptions

| Prop        | Type                | Description                       | Since |
| ----------- | ------------------- | --------------------------------- | ----- |
| **`mapId`** | <code>string</code> | The unique identifier of the map. | 0.1.0 |


#### RemoveGeoJsonSourceByIdOptions

| Prop           | Type                | Description                          | Since |
| -------------- | ------------------- | ------------------------------------ | ----- |
| **`mapId`**    | <code>string</code> | The unique identifier of the map.    | 0.1.0 |
| **`sourceId`** | <code>string</code> | The unique identifier of the source. | 0.1.0 |


#### RemoveLayerByIdOptions

| Prop          | Type                | Description                         | Since |
| ------------- | ------------------- | ----------------------------------- | ----- |
| **`layerId`** | <code>string</code> | The unique identifier of the layer. | 0.1.0 |
| **`mapId`**   | <code>string</code> | The unique identifier of the map.   | 0.1.0 |


#### RemoveMarkerByIdOptions

| Prop           | Type                | Description                          | Since |
| -------------- | ------------------- | ------------------------------------ | ----- |
| **`mapId`**    | <code>string</code> | The unique identifier of the map.    | 0.1.0 |
| **`markerId`** | <code>string</code> | The unique identifier of the marker. | 0.1.0 |


#### RemoveMarkersByIdsOptions

| Prop            | Type                  | Description                            | Since |
| --------------- | --------------------- | -------------------------------------- | ----- |
| **`mapId`**     | <code>string</code>   | The unique identifier of the map.      | 0.1.0 |
| **`markerIds`** | <code>string[]</code> | The unique identifiers of the markers. | 0.1.0 |


#### RemovePolylineByIdOptions

| Prop             | Type                | Description                            | Since |
| ---------------- | ------------------- | -------------------------------------- | ----- |
| **`mapId`**      | <code>string</code> | The unique identifier of the map.      | 0.1.0 |
| **`polylineId`** | <code>string</code> | The unique identifier of the polyline. | 0.1.0 |


#### RemovePolylinesByIdsOptions

| Prop              | Type                  | Description                              | Since |
| ----------------- | --------------------- | ---------------------------------------- | ----- |
| **`mapId`**       | <code>string</code>   | The unique identifier of the map.        | 0.1.0 |
| **`polylineIds`** | <code>string[]</code> | The unique identifiers of the polylines. | 0.1.0 |


#### SetCameraOptions

| Prop                    | Type                                        | Description                                                            | Default            | Since |
| ----------------------- | ------------------------------------------- | ---------------------------------------------------------------------- | ------------------ | ----- |
| **`animate`**           | <code>boolean</code>                        | Whether the camera movement is animated.                               | <code>false</code> | 0.1.0 |
| **`animationDuration`** | <code>number</code>                         | The duration of the animation in milliseconds.                         | <code>300</code>   | 0.1.0 |
| **`bearing`**           | <code>number</code>                         | The direction the camera points in, in degrees clockwise from north.   |                    | 0.1.0 |
| **`center`**            | <code><a href="#latlng">LatLng</a></code>   | The geographical coordinate at the center of the map.                  |                    | 0.1.0 |
| **`mapId`**             | <code>string</code>                         | The unique identifier of the map.                                      |                    | 0.1.0 |
| **`padding`**           | <code><a href="#padding">Padding</a></code> | The padding between the content of the map and its edges.              |                    | 0.1.0 |
| **`pitch`**             | <code>number</code>                         | The tilt of the camera in degrees, measured from the plane of the map. |                    | 0.1.0 |
| **`zoom`**              | <code>number</code>                         | The zoom level of the map.                                             |                    | 0.1.0 |


#### SetFrameOptions

| Prop              | Type                                                      | Description                                            | Since |
| ----------------- | --------------------------------------------------------- | ------------------------------------------------------ | ----- |
| **`contentSize`** | <code><a href="#mapcontentsize">MapContentSize</a></code> | The size of the scrollable content of the map element. | 0.1.0 |
| **`frame`**       | <code><a href="#mapframe">MapFrame</a></code>             | The new position and size of the map viewport.         | 0.1.0 |
| **`mapId`**       | <code>string</code>                                       | The unique identifier of the map.                      | 0.1.0 |


#### MapContentSize

The size of the scrollable content of a map element in CSS pixels.

| Prop         | Type                | Description                              | Since |
| ------------ | ------------------- | ---------------------------------------- | ----- |
| **`height`** | <code>number</code> | The height of the content in CSS pixels. | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the content in CSS pixels.  | 0.1.0 |


#### MapFrame

The position and size of a map in CSS pixels, relative to the viewport.

| Prop         | Type                | Description                                | Since |
| ------------ | ------------------- | ------------------------------------------ | ----- |
| **`height`** | <code>number</code> | The height of the map in CSS pixels.       | 0.1.0 |
| **`width`**  | <code>number</code> | The width of the map in CSS pixels.        | 0.1.0 |
| **`x`**      | <code>number</code> | The x-coordinate of the map in CSS pixels. | 0.1.0 |
| **`y`**      | <code>number</code> | The y-coordinate of the map in CSS pixels. | 0.1.0 |


#### SetGesturesEnabledOptions

| Prop         | Type                 | Description                                                                                             | Since |
| ------------ | -------------------- | ------------------------------------------------------------------------------------------------------- | ----- |
| **`mapId`**  | <code>string</code>  | The unique identifier of the map.                                                                       | 0.1.0 |
| **`pan`**    | <code>boolean</code> | Whether the user can move the camera by dragging the map. If not provided, the current setting is kept. | 0.1.0 |
| **`rotate`** | <code>boolean</code> | Whether the user can rotate the camera. If not provided, the current setting is kept.                   | 0.1.0 |
| **`tilt`**   | <code>boolean</code> | Whether the user can tilt the camera. If not provided, the current setting is kept.                     | 0.1.0 |
| **`zoom`**   | <code>boolean</code> | Whether the user can zoom the camera. If not provided, the current setting is kept.                     | 0.1.0 |


#### SetStyleOptions

| Prop        | Type                | Description                                                                              | Since |
| ----------- | ------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`json`**  | <code>string</code> | The style of the map as a JSON string. Exactly one of `json` and `url` must be provided. | 0.1.0 |
| **`mapId`** | <code>string</code> | The unique identifier of the map.                                                        | 0.1.0 |
| **`url`**   | <code>string</code> | The URL of the style of the map. Exactly one of `json` and `url` must be provided.       | 0.1.0 |


#### UpdateGeoJsonSourceByIdOptions

| Prop           | Type                                       | Description                                                                                      | Since |
| -------------- | ------------------------------------------ | ------------------------------------------------------------------------------------------------ | ----- |
| **`data`**     | <code>Record&lt;string, unknown&gt;</code> | The new GeoJSON data of the source. Exactly one of `data` and `url` must be provided.            | 0.1.0 |
| **`mapId`**    | <code>string</code>                        | The unique identifier of the map.                                                                | 0.1.0 |
| **`sourceId`** | <code>string</code>                        | The unique identifier of the source.                                                             | 0.1.0 |
| **`url`**      | <code>string</code>                        | The URL of the new GeoJSON data of the source. Exactly one of `data` and `url` must be provided. | 0.1.0 |


#### UpdateMarkerByIdOptions

| Prop                    | Type                                                          | Description                                                                 | Default            | Since |
| ----------------------- | ------------------------------------------------------------- | --------------------------------------------------------------------------- | ------------------ | ----- |
| **`animate`**           | <code>boolean</code>                                          | Whether the changes of the coordinates and the rotation are animated.       | <code>false</code> | 0.1.0 |
| **`animationDuration`** | <code>number</code>                                           | The duration of the animation in milliseconds.                              | <code>300</code>   | 0.1.0 |
| **`coordinates`**       | <code><a href="#latlng">LatLng</a></code>                     | The new geographical coordinate of the marker.                              |                    | 0.1.0 |
| **`draggable`**         | <code>boolean</code>                                          | Whether the user can drag the marker. Only available on Android and Web.    |                    | 0.1.0 |
| **`iconAnchor`**        | <code><a href="#markericonanchor">MarkerIconAnchor</a></code> | The part of the icon that is placed on the coordinate of the marker.        |                    | 0.1.0 |
| **`iconSize`**          | <code><a href="#markericonsize">MarkerIconSize</a></code>     | The size the icon is scaled to.                                             |                    | 0.1.0 |
| **`iconUrl`**           | <code>string</code>                                           | The URL of the new icon of the marker. Must be a `https` URL or a data URI. |                    | 0.1.0 |
| **`mapId`**             | <code>string</code>                                           | The unique identifier of the map.                                           |                    | 0.1.0 |
| **`markerId`**          | <code>string</code>                                           | The unique identifier of the marker.                                        |                    | 0.1.0 |
| **`opacity`**           | <code>number</code>                                           | The opacity of the marker as a value between `0` and `1`.                   |                    | 0.1.0 |
| **`rotation`**          | <code>number</code>                                           | The rotation of the icon in degrees clockwise.                              |                    | 0.1.0 |


#### UpdatePolylineByIdOptions

| Prop              | Type                  | Description                                                                               | Since |
| ----------------- | --------------------- | ----------------------------------------------------------------------------------------- | ----- |
| **`color`**       | <code>string</code>   | The color of the polyline as a hexadecimal string in the format `#RRGGBB` or `#RRGGBBAA`. | 0.1.0 |
| **`coordinates`** | <code>LatLng[]</code> | The new geographical coordinates of the polyline.                                         | 0.1.0 |
| **`mapId`**       | <code>string</code>   | The unique identifier of the map.                                                         | 0.1.0 |
| **`opacity`**     | <code>number</code>   | The opacity of the polyline as a value between `0` and `1`.                               | 0.1.0 |
| **`polylineId`**  | <code>string</code>   | The unique identifier of the polyline.                                                    | 0.1.0 |
| **`width`**       | <code>number</code>   | The width of the polyline in CSS pixels.                                                  | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CameraIdleEvent

| Prop         | Type                                      | Description                               | Since |
| ------------ | ----------------------------------------- | ----------------------------------------- | ----- |
| **`camera`** | <code><a href="#camera">Camera</a></code> | The camera of the map after the movement. | 0.1.0 |
| **`mapId`**  | <code>string</code>                       | The unique identifier of the map.         | 0.1.0 |


#### CameraMoveStartedEvent

| Prop         | Type                                                          | Description                               | Since |
| ------------ | ------------------------------------------------------------- | ----------------------------------------- | ----- |
| **`mapId`**  | <code>string</code>                                           | The unique identifier of the map.         | 0.1.0 |
| **`reason`** | <code><a href="#cameramovereason">CameraMoveReason</a></code> | The reason why the camera started moving. | 0.1.0 |


#### ElementFromPointRequestEvent

| Prop            | Type                | Description                                                            | Since |
| --------------- | ------------------- | ---------------------------------------------------------------------- | ----- |
| **`requestId`** | <code>string</code> | The unique identifier of the request.                                  | 0.1.0 |
| **`x`**         | <code>number</code> | The x-coordinate of the point in CSS pixels, relative to the viewport. | 0.1.0 |
| **`y`**         | <code>number</code> | The y-coordinate of the point in CSS pixels, relative to the viewport. | 0.1.0 |


#### MapClickEvent

| Prop              | Type                                          | Description                                     | Since |
| ----------------- | --------------------------------------------- | ----------------------------------------------- | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code>     | The geographical coordinate the user tapped on. | 0.1.0 |
| **`mapId`**       | <code>string</code>                           | The unique identifier of the map.               | 0.1.0 |
| **`point`**       | <code><a href="#mappoint">MapPoint</a></code> | The point the user tapped on.                   | 0.1.0 |


#### MapPoint

A point on a map in CSS pixels, relative to the map element.

| Prop    | Type                | Description                                  | Since |
| ------- | ------------------- | -------------------------------------------- | ----- |
| **`x`** | <code>number</code> | The x-coordinate of the point in CSS pixels. | 0.1.0 |
| **`y`** | <code>number</code> | The y-coordinate of the point in CSS pixels. | 0.1.0 |


#### MarkerClickEvent

| Prop              | Type                                      | Description                                | Since |
| ----------------- | ----------------------------------------- | ------------------------------------------ | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code> | The geographical coordinate of the marker. | 0.1.0 |
| **`mapId`**       | <code>string</code>                       | The unique identifier of the map.          | 0.1.0 |
| **`markerId`**    | <code>string</code>                       | The unique identifier of the marker.       | 0.1.0 |


#### MarkerDragEvent

| Prop              | Type                                      | Description                                        | Since |
| ----------------- | ----------------------------------------- | -------------------------------------------------- | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code> | The current geographical coordinate of the marker. | 0.1.0 |
| **`mapId`**       | <code>string</code>                       | The unique identifier of the map.                  | 0.1.0 |
| **`markerId`**    | <code>string</code>                       | The unique identifier of the marker.               | 0.1.0 |


#### MarkerDragEndEvent

| Prop              | Type                                      | Description                                            | Since |
| ----------------- | ----------------------------------------- | ------------------------------------------------------ | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code> | The geographical coordinate the marker was dropped at. | 0.1.0 |
| **`mapId`**       | <code>string</code>                       | The unique identifier of the map.                      | 0.1.0 |
| **`markerId`**    | <code>string</code>                       | The unique identifier of the marker.                   | 0.1.0 |


#### MarkerDragStartEvent

| Prop              | Type                                      | Description                                              | Since |
| ----------------- | ----------------------------------------- | -------------------------------------------------------- | ----- |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code> | The geographical coordinate the marker was dragged from. | 0.1.0 |
| **`mapId`**       | <code>string</code>                       | The unique identifier of the map.                        | 0.1.0 |
| **`markerId`**    | <code>string</code>                       | The unique identifier of the marker.                     | 0.1.0 |


#### UserLocationChangeEvent

| Prop              | Type                                      | Description                                                          | Since |
| ----------------- | ----------------------------------------- | -------------------------------------------------------------------- | ----- |
| **`accuracy`**    | <code>number</code>                       | The accuracy of the location in meters.                              | 0.1.0 |
| **`coordinates`** | <code><a href="#latlng">LatLng</a></code> | The geographical coordinate of the user.                             | 0.1.0 |
| **`heading`**     | <code>number</code>                       | The direction the device points in, in degrees clockwise from north. | 0.1.0 |
| **`mapId`**       | <code>string</code>                       | The unique identifier of the map.                                    | 0.1.0 |
| **`speed`**       | <code>number</code>                       | The speed of the device in meters per second.                        | 0.1.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### LayerType

| Members      | Value                 | Description                                    | Since |
| ------------ | --------------------- | ---------------------------------------------- | ----- |
| **`Circle`** | <code>'circle'</code> | A layer that renders points as circles.        | 0.1.0 |
| **`Fill`**   | <code>'fill'</code>   | A layer that renders polygons as filled areas. | 0.1.0 |
| **`Line`**   | <code>'line'</code>   | A layer that renders line strings as lines.    | 0.1.0 |


#### MarkerIconAnchor

| Members      | Value                 | Description                  | Since |
| ------------ | --------------------- | ---------------------------- | ----- |
| **`Bottom`** | <code>'bottom'</code> | The bottom edge of the icon. | 0.1.0 |
| **`Center`** | <code>'center'</code> | The center of the icon.      | 0.1.0 |
| **`Left`**   | <code>'left'</code>   | The left edge of the icon.   | 0.1.0 |
| **`Right`**  | <code>'right'</code>  | The right edge of the icon.  | 0.1.0 |
| **`Top`**    | <code>'top'</code>    | The top edge of the icon.    | 0.1.0 |


#### UserTrackingMode

| Members                 | Value                            | Description                                                                                   | Since |
| ----------------------- | -------------------------------- | --------------------------------------------------------------------------------------------- | ----- |
| **`Follow`**            | <code>'follow'</code>            | The camera follows the location of the user.                                                  | 0.1.0 |
| **`FollowWithCourse`**  | <code>'followWithCourse'</code>  | The camera follows the location of the user and points in the direction the user moves in.    | 0.1.0 |
| **`FollowWithHeading`** | <code>'followWithHeading'</code> | The camera follows the location of the user and points in the direction the device points in. | 0.1.0 |
| **`None`**              | <code>'none'</code>              | The camera does not follow the location of the user.                                          | 0.1.0 |


#### CameraMoveReason

| Members       | Value                  | Description                                    | Since |
| ------------- | ---------------------- | ---------------------------------------------- | ----- |
| **`Api`**     | <code>'api'</code>     | The camera was moved by a method call.         | 0.1.0 |
| **`Gesture`** | <code>'gesture'</code> | The camera was moved by a gesture of the user. | 0.1.0 |

</docgen-api>

## FAQ

### Where do I get map styles and do I need an API key?

No API key is required by the plugin itself. MapLibre is open source (BSD-licensed) and renders any style that follows the [MapLibre Style Spec](https://maplibre.org/maplibre-style-spec/). The default [demo style](https://demotiles.maplibre.org/style.json) is a test style and should not be used in production. Free providers include [CARTO basemaps](https://github.com/CartoDB/basemap-styles), [OpenFreeMap](https://openfreemap.org/) and [Versatiles](https://versatiles.org/). Commercial providers such as [MapTiler](https://www.maptiler.com/) offer styles that require their own API key. Always follow the attribution requirements and terms of service of the provider you choose.

### Why is my map not visible?

On Android and iOS, the map is rendered behind the web view. If the map element or one of its ancestors has a background color, the web view paints over the map. Make sure the map element is empty and that it and all ancestors covering the map region use `background: transparent` (see [Getting started](#getting-started)).

### Can I display HTML content above the map?

Yes. Since the native map is rendered behind the web view, any DOM element that is not an ancestor of the map element is displayed above the map. Floating action buttons, bottom sheets, overlays and dialogs work exactly as they do everywhere else in your app.

### Why are marker drag events not available on iOS?

On iOS, markers are rendered as symbol layers, which do not support native dragging yet. The `draggable` property and the `markerDrag`, `markerDragStart` and `markerDragEnd` events are therefore only available on Android and Web.

### Does this plugin work offline?

No. Styles, fonts, sprites and tiles are loaded from the network by the map. Offline tile management is not part of this version of the plugin.

## Related Plugins

- [Compass](https://capawesome.io/docs/sdks/capacitor/compass/): Read the device compass heading.
- [Geocoder](https://capawesome.io/docs/sdks/capacitor/geocoder/): Convert addresses into coordinates and vice versa.
- [Maps Launcher](https://capawesome.io/docs/sdks/capacitor/maps-launcher/): Launch navigation apps with turn-by-turn directions.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/maplibre/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/maplibre/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by the MapLibre organization or any of its contributors. "MapLibre" is a trademark of the MapLibre organization.
