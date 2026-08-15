import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface MapLibrePlugin {
  /**
   * Add a GeoJSON source to the map.
   *
   * Use `addLayer(...)` to render the data of the source.
   *
   * @since 0.1.0
   */
  addGeoJsonSource(options: AddGeoJsonSourceOptions): Promise<void>;
  /**
   * Add a layer to the map that renders the data of a GeoJSON source.
   *
   * @since 0.1.0
   */
  addLayer(options: AddLayerOptions): Promise<void>;
  /**
   * Add a marker to the map.
   *
   * @since 0.1.0
   */
  addMarker(options: AddMarkerOptions): Promise<void>;
  /**
   * Add multiple markers to the map.
   *
   * @since 0.1.0
   */
  addMarkers(options: AddMarkersOptions): Promise<void>;
  /**
   * Add a polyline to the map.
   *
   * @since 0.1.0
   */
  addPolyline(options: AddPolylineOptions): Promise<void>;
  /**
   * Add multiple polylines to the map.
   *
   * @since 0.1.0
   */
  addPolylines(options: AddPolylinesOptions): Promise<void>;
  /**
   * Check the location permission.
   *
   * @since 0.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Create a new map.
   *
   * The map is rendered into the element with the given element ID. On
   * Android and iOS, the map is rendered as a native view behind the web
   * view, so the element and everything above the map must be transparent.
   *
   * The promise resolves as soon as the style of the map has finished
   * loading.
   *
   * @since 0.1.0
   */
  createMap(options: CreateMapOptions): Promise<void>;
  /**
   * Destroy a map and release all its resources.
   *
   * @since 0.1.0
   */
  destroyMap(options: DestroyMapOptions): Promise<void>;
  /**
   * Stop displaying the location of the user on the map.
   *
   * @since 0.1.0
   */
  disableUserLocation(options: DisableUserLocationOptions): Promise<void>;
  /**
   * Answer an `elementFromPointRequest` event.
   *
   * This method is called automatically by the plugin and must not be
   * called manually.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  elementFromPointResult(options: ElementFromPointResultOptions): Promise<void>;
  /**
   * Display the location of the user on the map.
   *
   * Call this method again to change the tracking mode.
   *
   * @since 0.1.0
   */
  enableUserLocation(options: EnableUserLocationOptions): Promise<void>;
  /**
   * Move the camera so that the given bounds are visible.
   *
   * @since 0.1.0
   */
  fitBounds(options: FitBoundsOptions): Promise<void>;
  /**
   * Get the current camera of the map.
   *
   * @since 0.1.0
   */
  getCamera(options: GetCameraOptions): Promise<GetCameraResult>;
  /**
   * Remove all markers from the map.
   *
   * @since 0.1.0
   */
  removeAllMarkers(options: RemoveAllMarkersOptions): Promise<void>;
  /**
   * Remove all polylines from the map.
   *
   * @since 0.1.0
   */
  removeAllPolylines(options: RemoveAllPolylinesOptions): Promise<void>;
  /**
   * Remove a GeoJSON source from the map.
   *
   * All layers that use the source must be removed first.
   *
   * @since 0.1.0
   */
  removeGeoJsonSourceById(
    options: RemoveGeoJsonSourceByIdOptions,
  ): Promise<void>;
  /**
   * Remove a layer from the map.
   *
   * @since 0.1.0
   */
  removeLayerById(options: RemoveLayerByIdOptions): Promise<void>;
  /**
   * Remove a marker from the map.
   *
   * @since 0.1.0
   */
  removeMarkerById(options: RemoveMarkerByIdOptions): Promise<void>;
  /**
   * Remove multiple markers from the map.
   *
   * @since 0.1.0
   */
  removeMarkersByIds(options: RemoveMarkersByIdsOptions): Promise<void>;
  /**
   * Remove a polyline from the map.
   *
   * @since 0.1.0
   */
  removePolylineById(options: RemovePolylineByIdOptions): Promise<void>;
  /**
   * Remove multiple polylines from the map.
   *
   * @since 0.1.0
   */
  removePolylinesByIds(options: RemovePolylinesByIdsOptions): Promise<void>;
  /**
   * Request the location permission.
   *
   * @since 0.1.0
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Move the camera of the map.
   *
   * Only the provided properties are changed.
   *
   * @since 0.1.0
   */
  setCamera(options: SetCameraOptions): Promise<void>;
  /**
   * Update the position and size of the map viewport.
   *
   * This method is called automatically by the plugin and usually does not
   * need to be called manually.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setFrame(options: SetFrameOptions): Promise<void>;
  /**
   * Enable or disable the gestures of the map.
   *
   * Only the provided gestures are changed.
   *
   * @since 0.1.0
   */
  setGesturesEnabled(options: SetGesturesEnabledOptions): Promise<void>;
  /**
   * Load a new style into the map.
   *
   * All markers, polylines, sources and layers must be added again after
   * the new style has been loaded.
   *
   * @since 0.1.0
   */
  setStyle(options: SetStyleOptions): Promise<void>;
  /**
   * Update the data of a GeoJSON source.
   *
   * @since 0.1.0
   */
  updateGeoJsonSourceById(
    options: UpdateGeoJsonSourceByIdOptions,
  ): Promise<void>;
  /**
   * Update a marker on the map.
   *
   * Only the provided properties are changed.
   *
   * @since 0.1.0
   */
  updateMarkerById(options: UpdateMarkerByIdOptions): Promise<void>;
  /**
   * Update a polyline on the map.
   *
   * Only the provided properties are changed.
   *
   * @since 0.1.0
   */
  updatePolylineById(options: UpdatePolylineByIdOptions): Promise<void>;
  /**
   * Called when the camera of a map has stopped moving.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'cameraIdle',
    listenerFunc: (event: CameraIdleEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the camera of a map has started moving.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'cameraMoveStarted',
    listenerFunc: (event: CameraMoveStartedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the plugin needs to know which map is located at a point of
   * the screen.
   *
   * This event is handled automatically by the plugin and must not be
   * handled manually.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'elementFromPointRequest',
    listenerFunc: (event: ElementFromPointRequestEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user taps on a map.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'mapClick',
    listenerFunc: (event: MapClickEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user taps on a marker.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'markerClick',
    listenerFunc: (event: MarkerClickEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called while the user drags a marker.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'markerDrag',
    listenerFunc: (event: MarkerDragEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user has stopped dragging a marker.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'markerDragEnd',
    listenerFunc: (event: MarkerDragEndEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the user has started dragging a marker.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'markerDragStart',
    listenerFunc: (event: MarkerDragStartEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the location of the user changes.
   *
   * The event is only emitted while the location of the user is displayed
   * on the map.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'userLocationChange',
    listenerFunc: (event: UserLocationChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface AddGeoJsonSourceOptions {
  /**
   * The GeoJSON data of the source.
   *
   * Exactly one of `data` and `url` must be provided.
   *
   * @since 0.1.0
   */
  data?: Record<string, unknown>;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the source.
   *
   * @since 0.1.0
   * @example 'my-source'
   */
  sourceId: string;
  /**
   * The URL of the GeoJSON data of the source.
   *
   * Exactly one of `data` and `url` must be provided.
   *
   * @since 0.1.0
   * @example 'https://example.com/data.geojson'
   */
  url?: string;
}

/**
 * @since 0.1.0
 */
export interface AddLayerOptions {
  /**
   * The unique identifier of the layer below which the new layer is
   * inserted.
   *
   * If not provided, the layer is added on top of all other layers.
   *
   * @since 0.1.0
   */
  belowLayerId?: string;
  /**
   * The unique identifier of the layer.
   *
   * @since 0.1.0
   * @example 'my-layer'
   */
  layerId: string;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The maximum zoom level at which the layer is visible.
   *
   * @since 0.1.0
   */
  maxZoom?: number;
  /**
   * The minimum zoom level at which the layer is visible.
   *
   * @since 0.1.0
   */
  minZoom?: number;
  /**
   * The paint properties of the layer.
   *
   * @since 0.1.0
   */
  paint?: LayerPaint;
  /**
   * The unique identifier of the source whose data is rendered by the
   * layer.
   *
   * @since 0.1.0
   */
  sourceId: string;
  /**
   * The type of the layer.
   *
   * @since 0.1.0
   */
  type: LayerType;
}

/**
 * @since 0.1.0
 */
export interface AddMarkerOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The marker to add.
   *
   * @since 0.1.0
   */
  marker: Marker;
}

/**
 * @since 0.1.0
 */
export interface AddMarkersOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The markers to add.
   *
   * @since 0.1.0
   */
  markers: Marker[];
}

/**
 * @since 0.1.0
 */
export interface AddPolylineOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The polyline to add.
   *
   * @since 0.1.0
   */
  polyline: Polyline;
}

/**
 * @since 0.1.0
 */
export interface AddPolylinesOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The polylines to add.
   *
   * @since 0.1.0
   */
  polylines: Polyline[];
}

/**
 * A geographical area, defined by its southwest and northeast corner.
 *
 * @since 0.1.0
 */
export interface Bounds {
  /**
   * The northeast corner of the area.
   *
   * @since 0.1.0
   */
  northeast: LatLng;
  /**
   * The southwest corner of the area.
   *
   * @since 0.1.0
   */
  southwest: LatLng;
}

/**
 * The camera of a map.
 *
 * @since 0.1.0
 */
export interface Camera {
  /**
   * The direction the camera points in, in degrees clockwise from north.
   *
   * @since 0.1.0
   */
  bearing: number;
  /**
   * The geographical coordinate at the center of the map.
   *
   * @since 0.1.0
   */
  center: LatLng;
  /**
   * The tilt of the camera in degrees, measured from the plane of the map.
   *
   * @since 0.1.0
   */
  pitch: number;
  /**
   * The zoom level of the map.
   *
   * @since 0.1.0
   */
  zoom: number;
}

/**
 * @since 0.1.0
 */
export interface CameraIdleEvent {
  /**
   * The camera of the map after the movement.
   *
   * @since 0.1.0
   */
  camera: Camera;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface CameraMoveStartedEvent {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The reason why the camera started moving.
   *
   * @since 0.1.0
   */
  reason: CameraMoveReason;
}

/**
 * @since 0.1.0
 */
export interface CreateMapOptions {
  /**
   * The direction the camera points in, in degrees clockwise from north.
   *
   * @default 0
   * @since 0.1.0
   */
  bearing?: number;
  /**
   * The geographical coordinate at the center of the map.
   *
   * If not provided, the center of the style is used.
   *
   * @since 0.1.0
   */
  center?: LatLng;
  /**
   * The ID of the DOM element the map is rendered into.
   *
   * The element must be empty and must not have a background.
   *
   * @since 0.1.0
   * @example 'my-map'
   */
  elementId: string;
  /**
   * The gestures the user can perform on the map.
   *
   * @since 0.1.0
   */
  gestures?: GestureSettings;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   * @example 'my-map'
   */
  mapId: string;
  /**
   * The maximum zoom level of the map.
   *
   * If not provided, the maximum zoom level of the style is used.
   *
   * @since 0.1.0
   */
  maxZoom?: number;
  /**
   * The minimum zoom level of the map.
   *
   * If not provided, the minimum zoom level of the style is used.
   *
   * @since 0.1.0
   */
  minZoom?: number;
  /**
   * The tilt of the camera in degrees, measured from the plane of the map.
   *
   * @default 0
   * @since 0.1.0
   */
  pitch?: number;
  /**
   * The style of the map as a JSON string.
   *
   * At most one of `styleJson` and `styleUrl` may be provided.
   *
   * @since 0.1.0
   */
  styleJson?: string;
  /**
   * The URL of the style of the map.
   *
   * At most one of `styleJson` and `styleUrl` may be provided.
   *
   * @default 'https://demotiles.maplibre.org/style.json'
   * @since 0.1.0
   */
  styleUrl?: string;
  /**
   * The zoom level of the map.
   *
   * @default 0
   * @since 0.1.0
   */
  zoom?: number;
}

/**
 * @since 0.1.0
 */
export interface DestroyMapOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface DisableUserLocationOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface ElementFromPointRequestEvent {
  /**
   * The unique identifier of the request.
   *
   * @since 0.1.0
   */
  requestId: string;
  /**
   * The x-coordinate of the point in CSS pixels, relative to the viewport.
   *
   * @since 0.1.0
   */
  x: number;
  /**
   * The y-coordinate of the point in CSS pixels, relative to the viewport.
   *
   * @since 0.1.0
   */
  y: number;
}

/**
 * @since 0.1.0
 */
export interface ElementFromPointResultOptions {
  /**
   * The unique identifier of the map at the requested point.
   *
   * Must be `null` if no map is located at the requested point.
   *
   * @since 0.1.0
   */
  mapId: string | null;
  /**
   * The unique identifier of the request.
   *
   * @since 0.1.0
   */
  requestId: string;
}

/**
 * @since 0.1.0
 */
export interface EnableUserLocationOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The mode used to track the location of the user with the camera.
   *
   * On Web, `FollowWithCourse` and `FollowWithHeading` behave like
   * `Follow`.
   *
   * @default UserTrackingMode.None
   * @since 0.1.0
   */
  trackingMode?: UserTrackingMode;
}

/**
 * @since 0.1.0
 */
export interface FitBoundsOptions {
  /**
   * Whether the camera movement is animated.
   *
   * @default false
   * @since 0.1.0
   */
  animate?: boolean;
  /**
   * The duration of the animation in milliseconds.
   *
   * @default 300
   * @since 0.1.0
   */
  animationDuration?: number;
  /**
   * The bounds that must be visible after the camera movement.
   *
   * @since 0.1.0
   */
  bounds: Bounds;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The maximum zoom level the camera may reach.
   *
   * @since 0.1.0
   */
  maxZoom?: number;
  /**
   * The padding between the bounds and the edges of the map.
   *
   * @since 0.1.0
   */
  padding?: Padding;
}

/**
 * The gestures the user can perform on a map.
 *
 * @since 0.1.0
 */
export interface GestureSettings {
  /**
   * Whether the user can move the camera by dragging the map.
   *
   * @default true
   * @since 0.1.0
   */
  pan?: boolean;
  /**
   * Whether the user can rotate the camera.
   *
   * @default true
   * @since 0.1.0
   */
  rotate?: boolean;
  /**
   * Whether the user can tilt the camera.
   *
   * @default true
   * @since 0.1.0
   */
  tilt?: boolean;
  /**
   * Whether the user can zoom the camera.
   *
   * @default true
   * @since 0.1.0
   */
  zoom?: boolean;
}

/**
 * @since 0.1.0
 */
export interface GetCameraOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface GetCameraResult {
  /**
   * The current camera of the map.
   *
   * @since 0.1.0
   */
  camera: Camera;
}

/**
 * A geographical coordinate.
 *
 * @since 0.1.0
 */
export interface LatLng {
  /**
   * The latitude in degrees.
   *
   * @since 0.1.0
   * @example 48.137154
   */
  latitude: number;
  /**
   * The longitude in degrees.
   *
   * @since 0.1.0
   * @example 11.576124
   */
  longitude: number;
}

/**
 * The paint properties of a layer.
 *
 * Properties that do not apply to the type of the layer are ignored.
 *
 * @since 0.1.0
 */
export interface LayerPaint {
  /**
   * The fill color of the circles as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * Only applies to layers of type `circle`.
   *
   * @since 0.1.0
   * @example '#3887be'
   */
  circleColor?: string;
  /**
   * The opacity of the circles as a value between `0` and `1`.
   *
   * Only applies to layers of type `circle`.
   *
   * @since 0.1.0
   */
  circleOpacity?: number;
  /**
   * The radius of the circles in CSS pixels.
   *
   * Only applies to layers of type `circle`.
   *
   * @since 0.1.0
   */
  circleRadius?: number;
  /**
   * The stroke color of the circles as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * Only applies to layers of type `circle`.
   *
   * @since 0.1.0
   * @example '#ffffff'
   */
  circleStrokeColor?: string;
  /**
   * The stroke width of the circles in CSS pixels.
   *
   * Only applies to layers of type `circle`.
   *
   * @since 0.1.0
   */
  circleStrokeWidth?: number;
  /**
   * The fill color of the areas as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * Only applies to layers of type `fill`.
   *
   * @since 0.1.0
   * @example '#3887be'
   */
  fillColor?: string;
  /**
   * The opacity of the areas as a value between `0` and `1`.
   *
   * Only applies to layers of type `fill`.
   *
   * @since 0.1.0
   */
  fillOpacity?: number;
  /**
   * The outline color of the areas as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * Only applies to layers of type `fill`.
   *
   * @since 0.1.0
   * @example '#ffffff'
   */
  fillOutlineColor?: string;
  /**
   * The color of the lines as a hexadecimal string in the format `#RRGGBB`
   * or `#RRGGBBAA`.
   *
   * Only applies to layers of type `line`.
   *
   * @since 0.1.0
   * @example '#3887be'
   */
  lineColor?: string;
  /**
   * The opacity of the lines as a value between `0` and `1`.
   *
   * Only applies to layers of type `line`.
   *
   * @since 0.1.0
   */
  lineOpacity?: number;
  /**
   * The width of the lines in CSS pixels.
   *
   * Only applies to layers of type `line`.
   *
   * @since 0.1.0
   */
  lineWidth?: number;
}

/**
 * @since 0.1.0
 */
export interface MapClickEvent {
  /**
   * The geographical coordinate the user tapped on.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The point the user tapped on.
   *
   * @since 0.1.0
   */
  point: MapPoint;
}

/**
 * The size of the scrollable content of a map element in CSS pixels.
 *
 * @since 0.1.0
 */
export interface MapContentSize {
  /**
   * The height of the content in CSS pixels.
   *
   * @since 0.1.0
   */
  height: number;
  /**
   * The width of the content in CSS pixels.
   *
   * @since 0.1.0
   */
  width: number;
}

/**
 * The position and size of a map in CSS pixels, relative to the viewport.
 *
 * @since 0.1.0
 */
export interface MapFrame {
  /**
   * The height of the map in CSS pixels.
   *
   * @since 0.1.0
   */
  height: number;
  /**
   * The width of the map in CSS pixels.
   *
   * @since 0.1.0
   */
  width: number;
  /**
   * The x-coordinate of the map in CSS pixels.
   *
   * @since 0.1.0
   */
  x: number;
  /**
   * The y-coordinate of the map in CSS pixels.
   *
   * @since 0.1.0
   */
  y: number;
}

/**
 * A point on a map in CSS pixels, relative to the map element.
 *
 * @since 0.1.0
 */
export interface MapPoint {
  /**
   * The x-coordinate of the point in CSS pixels.
   *
   * @since 0.1.0
   */
  x: number;
  /**
   * The y-coordinate of the point in CSS pixels.
   *
   * @since 0.1.0
   */
  y: number;
}

/**
 * A marker on a map.
 *
 * @since 0.1.0
 */
export interface Marker {
  /**
   * The geographical coordinate of the marker.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * Whether the user can drag the marker.
   *
   * Only available on Android and Web.
   *
   * @default false
   * @since 0.1.0
   */
  draggable?: boolean;
  /**
   * The part of the icon that is placed on the coordinate of the marker.
   *
   * @default MarkerIconAnchor.Bottom
   * @since 0.1.0
   */
  iconAnchor?: MarkerIconAnchor;
  /**
   * The size the icon is scaled to.
   *
   * If not provided, the intrinsic size of the icon is used.
   *
   * @since 0.1.0
   */
  iconSize?: MarkerIconSize;
  /**
   * The URL of the icon of the marker.
   *
   * Must be a `https` URL or a data URI. If not provided, a default pin
   * icon is used.
   *
   * @since 0.1.0
   * @example 'https://example.com/marker.png'
   */
  iconUrl?: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   * @example 'my-marker'
   */
  id: string;
  /**
   * The opacity of the marker as a value between `0` and `1`.
   *
   * @default 1
   * @since 0.1.0
   */
  opacity?: number;
  /**
   * The rotation of the icon in degrees clockwise.
   *
   * @default 0
   * @since 0.1.0
   */
  rotation?: number;
}

/**
 * @since 0.1.0
 */
export interface MarkerClickEvent {
  /**
   * The geographical coordinate of the marker.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
}

/**
 * @since 0.1.0
 */
export interface MarkerDragEndEvent {
  /**
   * The geographical coordinate the marker was dropped at.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
}

/**
 * @since 0.1.0
 */
export interface MarkerDragEvent {
  /**
   * The current geographical coordinate of the marker.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
}

/**
 * @since 0.1.0
 */
export interface MarkerDragStartEvent {
  /**
   * The geographical coordinate the marker was dragged from.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
}

/**
 * The size of the icon of a marker in CSS pixels.
 *
 * @since 0.1.0
 */
export interface MarkerIconSize {
  /**
   * The height of the icon in CSS pixels.
   *
   * @since 0.1.0
   */
  height: number;
  /**
   * The width of the icon in CSS pixels.
   *
   * @since 0.1.0
   */
  width: number;
}

/**
 * The padding between the content of a map and its edges in CSS pixels.
 *
 * @since 0.1.0
 */
export interface Padding {
  /**
   * The padding at the bottom edge in CSS pixels.
   *
   * @default 0
   * @since 0.1.0
   */
  bottom?: number;
  /**
   * The padding at the left edge in CSS pixels.
   *
   * @default 0
   * @since 0.1.0
   */
  left?: number;
  /**
   * The padding at the right edge in CSS pixels.
   *
   * @default 0
   * @since 0.1.0
   */
  right?: number;
  /**
   * The padding at the top edge in CSS pixels.
   *
   * @default 0
   * @since 0.1.0
   */
  top?: number;
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * The permission state of the location permission.
   *
   * @since 0.1.0
   */
  location: PermissionState;
}

/**
 * A polyline on a map.
 *
 * @since 0.1.0
 */
export interface Polyline {
  /**
   * The color of the polyline as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * @default '#3887be'
   * @since 0.1.0
   */
  color?: string;
  /**
   * The geographical coordinates of the polyline.
   *
   * @since 0.1.0
   */
  coordinates: LatLng[];
  /**
   * The unique identifier of the polyline.
   *
   * @since 0.1.0
   * @example 'my-polyline'
   */
  id: string;
  /**
   * The opacity of the polyline as a value between `0` and `1`.
   *
   * @default 1
   * @since 0.1.0
   */
  opacity?: number;
  /**
   * The width of the polyline in CSS pixels.
   *
   * @default 4
   * @since 0.1.0
   */
  width?: number;
}

/**
 * @since 0.1.0
 */
export interface RemoveAllMarkersOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface RemoveAllPolylinesOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface RemoveGeoJsonSourceByIdOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the source.
   *
   * @since 0.1.0
   */
  sourceId: string;
}

/**
 * @since 0.1.0
 */
export interface RemoveLayerByIdOptions {
  /**
   * The unique identifier of the layer.
   *
   * @since 0.1.0
   */
  layerId: string;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface RemoveMarkerByIdOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
}

/**
 * @since 0.1.0
 */
export interface RemoveMarkersByIdsOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifiers of the markers.
   *
   * @since 0.1.0
   */
  markerIds: string[];
}

/**
 * @since 0.1.0
 */
export interface RemovePolylineByIdOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the polyline.
   *
   * @since 0.1.0
   */
  polylineId: string;
}

/**
 * @since 0.1.0
 */
export interface RemovePolylinesByIdsOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifiers of the polylines.
   *
   * @since 0.1.0
   */
  polylineIds: string[];
}

/**
 * @since 0.1.0
 */
export interface SetCameraOptions {
  /**
   * Whether the camera movement is animated.
   *
   * @default false
   * @since 0.1.0
   */
  animate?: boolean;
  /**
   * The duration of the animation in milliseconds.
   *
   * @default 300
   * @since 0.1.0
   */
  animationDuration?: number;
  /**
   * The direction the camera points in, in degrees clockwise from north.
   *
   * @since 0.1.0
   */
  bearing?: number;
  /**
   * The geographical coordinate at the center of the map.
   *
   * @since 0.1.0
   */
  center?: LatLng;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The padding between the content of the map and its edges.
   *
   * @since 0.1.0
   */
  padding?: Padding;
  /**
   * The tilt of the camera in degrees, measured from the plane of the map.
   *
   * @since 0.1.0
   */
  pitch?: number;
  /**
   * The zoom level of the map.
   *
   * @since 0.1.0
   */
  zoom?: number;
}

/**
 * @since 0.1.0
 */
export interface SetFrameOptions {
  /**
   * The size of the scrollable content of the map element.
   *
   * @since 0.1.0
   */
  contentSize?: MapContentSize;
  /**
   * The new position and size of the map viewport.
   *
   * @since 0.1.0
   */
  frame: MapFrame;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
}

/**
 * @since 0.1.0
 */
export interface SetGesturesEnabledOptions {
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * Whether the user can move the camera by dragging the map.
   *
   * If not provided, the current setting is kept.
   *
   * @since 0.1.0
   */
  pan?: boolean;
  /**
   * Whether the user can rotate the camera.
   *
   * If not provided, the current setting is kept.
   *
   * @since 0.1.0
   */
  rotate?: boolean;
  /**
   * Whether the user can tilt the camera.
   *
   * If not provided, the current setting is kept.
   *
   * @since 0.1.0
   */
  tilt?: boolean;
  /**
   * Whether the user can zoom the camera.
   *
   * If not provided, the current setting is kept.
   *
   * @since 0.1.0
   */
  zoom?: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetStyleOptions {
  /**
   * The style of the map as a JSON string.
   *
   * Exactly one of `json` and `url` must be provided.
   *
   * @since 0.1.0
   */
  json?: string;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The URL of the style of the map.
   *
   * Exactly one of `json` and `url` must be provided.
   *
   * @since 0.1.0
   * @example 'https://demotiles.maplibre.org/style.json'
   */
  url?: string;
}

/**
 * @since 0.1.0
 */
export interface UpdateGeoJsonSourceByIdOptions {
  /**
   * The new GeoJSON data of the source.
   *
   * Exactly one of `data` and `url` must be provided.
   *
   * @since 0.1.0
   */
  data?: Record<string, unknown>;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the source.
   *
   * @since 0.1.0
   */
  sourceId: string;
  /**
   * The URL of the new GeoJSON data of the source.
   *
   * Exactly one of `data` and `url` must be provided.
   *
   * @since 0.1.0
   * @example 'https://example.com/data.geojson'
   */
  url?: string;
}

/**
 * @since 0.1.0
 */
export interface UpdateMarkerByIdOptions {
  /**
   * Whether the changes of the coordinates and the rotation are animated.
   *
   * @default false
   * @since 0.1.0
   */
  animate?: boolean;
  /**
   * The duration of the animation in milliseconds.
   *
   * @default 300
   * @since 0.1.0
   */
  animationDuration?: number;
  /**
   * The new geographical coordinate of the marker.
   *
   * @since 0.1.0
   */
  coordinates?: LatLng;
  /**
   * Whether the user can drag the marker.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  draggable?: boolean;
  /**
   * The part of the icon that is placed on the coordinate of the marker.
   *
   * @since 0.1.0
   */
  iconAnchor?: MarkerIconAnchor;
  /**
   * The size the icon is scaled to.
   *
   * @since 0.1.0
   */
  iconSize?: MarkerIconSize;
  /**
   * The URL of the new icon of the marker.
   *
   * Must be a `https` URL or a data URI.
   *
   * @since 0.1.0
   * @example 'https://example.com/marker.png'
   */
  iconUrl?: string;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The unique identifier of the marker.
   *
   * @since 0.1.0
   */
  markerId: string;
  /**
   * The opacity of the marker as a value between `0` and `1`.
   *
   * @since 0.1.0
   */
  opacity?: number;
  /**
   * The rotation of the icon in degrees clockwise.
   *
   * @since 0.1.0
   */
  rotation?: number;
}

/**
 * @since 0.1.0
 */
export interface UpdatePolylineByIdOptions {
  /**
   * The color of the polyline as a hexadecimal string in the format
   * `#RRGGBB` or `#RRGGBBAA`.
   *
   * @since 0.1.0
   */
  color?: string;
  /**
   * The new geographical coordinates of the polyline.
   *
   * @since 0.1.0
   */
  coordinates?: LatLng[];
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The opacity of the polyline as a value between `0` and `1`.
   *
   * @since 0.1.0
   */
  opacity?: number;
  /**
   * The unique identifier of the polyline.
   *
   * @since 0.1.0
   */
  polylineId: string;
  /**
   * The width of the polyline in CSS pixels.
   *
   * @since 0.1.0
   */
  width?: number;
}

/**
 * @since 0.1.0
 */
export interface UserLocationChangeEvent {
  /**
   * The accuracy of the location in meters.
   *
   * @since 0.1.0
   */
  accuracy?: number;
  /**
   * The geographical coordinate of the user.
   *
   * @since 0.1.0
   */
  coordinates: LatLng;
  /**
   * The direction the device points in, in degrees clockwise from north.
   *
   * @since 0.1.0
   */
  heading?: number;
  /**
   * The unique identifier of the map.
   *
   * @since 0.1.0
   */
  mapId: string;
  /**
   * The speed of the device in meters per second.
   *
   * @since 0.1.0
   */
  speed?: number;
}

/**
 * The reason why the camera of a map started moving.
 *
 * @since 0.1.0
 */
export enum CameraMoveReason {
  /**
   * The camera was moved by a method call.
   *
   * @since 0.1.0
   */
  Api = 'api',
  /**
   * The camera was moved by a gesture of the user.
   *
   * @since 0.1.0
   */
  Gesture = 'gesture',
}

/**
 * The type of a layer.
 *
 * @since 0.1.0
 */
export enum LayerType {
  /**
   * A layer that renders points as circles.
   *
   * @since 0.1.0
   */
  Circle = 'circle',
  /**
   * A layer that renders polygons as filled areas.
   *
   * @since 0.1.0
   */
  Fill = 'fill',
  /**
   * A layer that renders line strings as lines.
   *
   * @since 0.1.0
   */
  Line = 'line',
}

/**
 * The part of the icon of a marker that is placed on its coordinate.
 *
 * @since 0.1.0
 */
export enum MarkerIconAnchor {
  /**
   * The bottom edge of the icon.
   *
   * @since 0.1.0
   */
  Bottom = 'bottom',
  /**
   * The center of the icon.
   *
   * @since 0.1.0
   */
  Center = 'center',
  /**
   * The left edge of the icon.
   *
   * @since 0.1.0
   */
  Left = 'left',
  /**
   * The right edge of the icon.
   *
   * @since 0.1.0
   */
  Right = 'right',
  /**
   * The top edge of the icon.
   *
   * @since 0.1.0
   */
  Top = 'top',
}

/**
 * The mode used to track the location of the user with the camera.
 *
 * @since 0.1.0
 */
export enum UserTrackingMode {
  /**
   * The camera follows the location of the user.
   *
   * @since 0.1.0
   */
  Follow = 'follow',
  /**
   * The camera follows the location of the user and points in the direction
   * the user moves in.
   *
   * @since 0.1.0
   */
  FollowWithCourse = 'followWithCourse',
  /**
   * The camera follows the location of the user and points in the direction
   * the device points in.
   *
   * @since 0.1.0
   */
  FollowWithHeading = 'followWithHeading',
  /**
   * The camera does not follow the location of the user.
   *
   * @since 0.1.0
   */
  None = 'none',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * No element exists with the provided element ID.
   *
   * @since 0.1.0
   */
  ElementNotFound = 'ELEMENT_NOT_FOUND',
  /**
   * No layer exists with the provided layer ID.
   *
   * @since 0.1.0
   */
  LayerNotFound = 'LAYER_NOT_FOUND',
  /**
   * The user denied the location permission.
   *
   * @since 0.1.0
   */
  LocationPermissionDenied = 'LOCATION_PERMISSION_DENIED',
  /**
   * No map exists with the provided map ID.
   *
   * @since 0.1.0
   */
  MapNotFound = 'MAP_NOT_FOUND',
  /**
   * No marker exists with the provided marker ID.
   *
   * @since 0.1.0
   */
  MarkerNotFound = 'MARKER_NOT_FOUND',
  /**
   * No polyline exists with the provided polyline ID.
   *
   * @since 0.1.0
   */
  PolylineNotFound = 'POLYLINE_NOT_FOUND',
  /**
   * No source exists with the provided source ID.
   *
   * @since 0.1.0
   */
  SourceNotFound = 'SOURCE_NOT_FOUND',
  /**
   * The style of the map could not be loaded.
   *
   * @since 0.1.0
   */
  StyleLoadFailed = 'STYLE_LOAD_FAILED',
}
