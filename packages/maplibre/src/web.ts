import { CapacitorException, WebPlugin } from '@capacitor/core';
import type * as MapLibreGl from 'maplibre-gl';

import type {
  AddGeoJsonSourceOptions,
  AddLayerOptions,
  AddMarkerOptions,
  AddMarkersOptions,
  AddPolylineOptions,
  AddPolylinesOptions,
  Camera,
  CreateMapOptions,
  DestroyMapOptions,
  DisableUserLocationOptions,
  EnableUserLocationOptions,
  FitBoundsOptions,
  GestureSettings,
  GetCameraOptions,
  GetCameraResult,
  LatLng,
  LayerPaint,
  MapLibrePlugin,
  Marker,
  Padding,
  PermissionStatus,
  Polyline,
  RemoveAllMarkersOptions,
  RemoveAllPolylinesOptions,
  RemoveGeoJsonSourceByIdOptions,
  RemoveLayerByIdOptions,
  RemoveMarkerByIdOptions,
  RemoveMarkersByIdsOptions,
  RemovePolylineByIdOptions,
  RemovePolylinesByIdsOptions,
  SetCameraOptions,
  SetGesturesEnabledOptions,
  SetStyleOptions,
  UpdateGeoJsonSourceByIdOptions,
  UpdateMarkerByIdOptions,
  UpdatePolylineByIdOptions,
} from './definitions';
import {
  CameraMoveReason,
  ErrorCode,
  LayerType,
  MarkerIconAnchor,
  UserTrackingMode,
} from './definitions';

type GeoJsonData = Parameters<MapLibreGl.GeoJSONSource['setData']>[0];

type MapStyle = NonNullable<Parameters<MapLibreGl.Map['setStyle']>[0]>;

interface GestureHandler {
  disable(): void;
  enable(): void;
}

interface MapState {
  gestures: Required<GestureSettings>;
  map: MapLibreGl.Map;
  markers: Map<string, MarkerState>;
  polylines: Map<string, Polyline>;
  userLocation?: UserLocationState;
}

interface MarkerState {
  animationFrameId?: number;
  marker: MapLibreGl.Marker;
  options: Marker;
}

interface PolylinePaint {
  'line-color': string;
  'line-opacity': number;
  'line-width': number;
}

interface UserLocationState {
  marker: MapLibreGl.Marker;
  markerAdded: boolean;
  trackingMode: UserTrackingMode;
  watchId: number;
}

export class MapLibreWeb extends WebPlugin implements MapLibrePlugin {
  private static readonly defaultAnimationDuration = 300;
  private static readonly defaultMarkerColor = '#3887be';
  private static readonly defaultPolylineColor = '#3887be';
  private static readonly defaultPolylineWidth = 4;
  private static readonly defaultStyleUrl =
    'https://demotiles.maplibre.org/style.json';
  private static readonly errorElementNotFound = 'element not found.';
  private static readonly errorGeoJsonDataInvalid =
    'exactly one of data and url must be provided.';
  private static readonly errorGeolocationUnavailable =
    'Geolocation API not available in this browser.';
  private static readonly errorLayerNotFound = 'layer not found.';
  private static readonly errorLocationPermissionDenied =
    'location permission denied.';
  private static readonly errorMapNotFound = 'map not found.';
  private static readonly errorMarkerNotFound = 'marker not found.';
  private static readonly errorPolylineNotFound = 'polyline not found.';
  private static readonly errorSourceNotFound = 'source not found.';
  private static readonly errorStyleInvalid =
    'exactly one of json and url must be provided.';
  private static readonly errorStyleLoadFailed = 'style load failed.';
  private static readonly errorUnavailable =
    'This method is not available on this platform.';
  private static readonly eventCameraIdle = 'cameraIdle';
  private static readonly eventCameraMoveStarted = 'cameraMoveStarted';
  private static readonly eventMapClick = 'mapClick';
  private static readonly eventMarkerClick = 'markerClick';
  private static readonly eventMarkerDrag = 'markerDrag';
  private static readonly eventMarkerDragEnd = 'markerDragEnd';
  private static readonly eventMarkerDragStart = 'markerDragStart';
  private static readonly eventUserLocationChange = 'userLocationChange';
  private static readonly permissionRequestTimeoutMs = 10000;
  private static readonly polylineIdPrefix = 'polyline-';
  private static readonly userLocationColor = '#1a73e8';

  private mapLibreGlPromise: Promise<typeof MapLibreGl> | undefined;
  private readonly maps = new Map<string, MapState>();

  async addGeoJsonSource(options: AddGeoJsonSourceOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    mapState.map.addSource(options.sourceId, {
      data: this.getGeoJsonData(options.data, options.url),
      type: 'geojson',
    });
  }

  async addLayer(options: AddLayerOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const layer: Record<string, unknown> = {
      id: options.layerId,
      paint: this.createLayerPaint(options.type, options.paint),
      source: options.sourceId,
      type: options.type,
    };
    if (options.maxZoom !== undefined) {
      layer.maxzoom = options.maxZoom;
    }
    if (options.minZoom !== undefined) {
      layer.minzoom = options.minZoom;
    }
    mapState.map.addLayer(
      layer as MapLibreGl.AddLayerObject,
      options.belowLayerId,
    );
  }

  async addMarker(options: AddMarkerOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    await this.addMarkerToMap(mapState, options.mapId, options.marker);
  }

  async addMarkers(options: AddMarkersOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const marker of options.markers) {
      await this.addMarkerToMap(mapState, options.mapId, marker);
    }
  }

  async addPolyline(options: AddPolylineOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    this.addPolylineToMap(mapState, options.polyline);
  }

  async addPolylines(options: AddPolylinesOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const polyline of options.polylines) {
      this.addPolylineToMap(mapState, polyline);
    }
  }

  async checkPermissions(): Promise<PermissionStatus> {
    if (!navigator.permissions?.query) {
      return { location: 'prompt' };
    }
    try {
      const status = await navigator.permissions.query({ name: 'geolocation' });
      return { location: status.state };
    } catch {
      return { location: 'prompt' };
    }
  }

  async createMap(options: CreateMapOptions): Promise<void> {
    const element = document.getElementById(options.elementId);
    if (!element) {
      throw this.createException(
        MapLibreWeb.errorElementNotFound,
        ErrorCode.ElementNotFound,
      );
    }
    const mapLibreGl = await this.getMapLibreGl();
    const map = new mapLibreGl.Map({
      bearing: options.bearing ?? 0,
      center: options.center ? this.toLngLat(options.center) : undefined,
      container: element,
      maxZoom: options.maxZoom,
      minZoom: options.minZoom,
      pitch: options.pitch ?? 0,
      style: this.createStyle(options.styleJson, options.styleUrl),
      zoom: options.zoom ?? 0,
    });
    const gestures = {
      pan: options.gestures?.pan ?? true,
      rotate: options.gestures?.rotate ?? true,
      tilt: options.gestures?.tilt ?? true,
      zoom: options.gestures?.zoom ?? true,
    };
    this.applyGestures(map, gestures);
    try {
      await this.waitForStyle(map);
    } catch (error) {
      map.remove();
      throw error;
    }
    this.addMapListeners(map, options.mapId);
    this.maps.set(options.mapId, {
      gestures,
      map,
      markers: new Map(),
      polylines: new Map(),
    });
  }

  async destroyMap(options: DestroyMapOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    this.stopUserLocation(mapState);
    for (const markerState of mapState.markers.values()) {
      this.cancelMarkerAnimation(markerState);
    }
    mapState.map.remove();
    this.maps.delete(options.mapId);
  }

  async disableUserLocation(
    options: DisableUserLocationOptions,
  ): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    this.stopUserLocation(mapState);
  }

  async elementFromPointResult(): Promise<void> {
    throw this.unavailable(MapLibreWeb.errorUnavailable);
  }

  async enableUserLocation(options: EnableUserLocationOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    if (!navigator.geolocation) {
      throw this.unavailable(MapLibreWeb.errorGeolocationUnavailable);
    }
    const trackingMode = options.trackingMode ?? UserTrackingMode.None;
    if (mapState.userLocation) {
      mapState.userLocation.trackingMode = trackingMode;
      return;
    }
    const permissions = await this.checkPermissions();
    if (permissions.location === 'denied') {
      throw this.createException(
        MapLibreWeb.errorLocationPermissionDenied,
        ErrorCode.LocationPermissionDenied,
      );
    }
    const mapLibreGl = await this.getMapLibreGl();
    const marker = new mapLibreGl.Marker({
      element: this.createUserLocationElement(),
    });
    mapState.userLocation = {
      marker,
      markerAdded: false,
      trackingMode,
      watchId: navigator.geolocation.watchPosition(
        position => this.handleUserLocationChange(options.mapId, position),
        undefined,
        { enableHighAccuracy: true },
      ),
    };
  }

  async fitBounds(options: FitBoundsOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const southwest = this.toLngLat(options.bounds.southwest);
    const northeast = this.toLngLat(options.bounds.northeast);
    mapState.map.fitBounds([southwest, northeast], {
      animate: options.animate ?? false,
      duration:
        options.animationDuration ?? MapLibreWeb.defaultAnimationDuration,
      maxZoom: options.maxZoom,
      padding: this.toPadding(options.padding),
    });
  }

  async getCamera(options: GetCameraOptions): Promise<GetCameraResult> {
    const mapState = this.getMapState(options.mapId);
    return { camera: this.getCameraOfMap(mapState.map) };
  }

  async removeAllMarkers(options: RemoveAllMarkersOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const markerId of Array.from(mapState.markers.keys())) {
      this.removeMarkerFromMap(mapState, markerId);
    }
  }

  async removeAllPolylines(options: RemoveAllPolylinesOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const polylineId of Array.from(mapState.polylines.keys())) {
      this.removePolylineFromMap(mapState, polylineId);
    }
  }

  async removeGeoJsonSourceById(
    options: RemoveGeoJsonSourceByIdOptions,
  ): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    if (!mapState.map.getSource(options.sourceId)) {
      throw this.createException(
        MapLibreWeb.errorSourceNotFound,
        ErrorCode.SourceNotFound,
      );
    }
    mapState.map.removeSource(options.sourceId);
  }

  async removeLayerById(options: RemoveLayerByIdOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    if (!mapState.map.getLayer(options.layerId)) {
      throw this.createException(
        MapLibreWeb.errorLayerNotFound,
        ErrorCode.LayerNotFound,
      );
    }
    mapState.map.removeLayer(options.layerId);
  }

  async removeMarkerById(options: RemoveMarkerByIdOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    this.getMarkerState(mapState, options.markerId);
    this.removeMarkerFromMap(mapState, options.markerId);
  }

  async removeMarkersByIds(options: RemoveMarkersByIdsOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const markerId of options.markerIds) {
      this.getMarkerState(mapState, markerId);
    }
    for (const markerId of options.markerIds) {
      this.removeMarkerFromMap(mapState, markerId);
    }
  }

  async removePolylineById(options: RemovePolylineByIdOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    this.getPolyline(mapState, options.polylineId);
    this.removePolylineFromMap(mapState, options.polylineId);
  }

  async removePolylinesByIds(
    options: RemovePolylinesByIdsOptions,
  ): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    for (const polylineId of options.polylineIds) {
      this.getPolyline(mapState, polylineId);
    }
    for (const polylineId of options.polylineIds) {
      this.removePolylineFromMap(mapState, polylineId);
    }
  }

  async requestPermissions(): Promise<PermissionStatus> {
    if (!navigator.geolocation) {
      throw this.unavailable(MapLibreWeb.errorGeolocationUnavailable);
    }
    await new Promise<void>(resolve => {
      navigator.geolocation.getCurrentPosition(
        () => resolve(),
        () => resolve(),
        { timeout: MapLibreWeb.permissionRequestTimeoutMs },
      );
    });
    return this.checkPermissions();
  }

  async setCamera(options: SetCameraOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const camera: MapLibreGl.JumpToOptions = {};
    if (options.bearing !== undefined) {
      camera.bearing = options.bearing;
    }
    if (options.center) {
      camera.center = this.toLngLat(options.center);
    }
    if (options.padding) {
      camera.padding = this.toPadding(options.padding);
    }
    if (options.pitch !== undefined) {
      camera.pitch = options.pitch;
    }
    if (options.zoom !== undefined) {
      camera.zoom = options.zoom;
    }
    if (options.animate) {
      mapState.map.easeTo({
        ...camera,
        duration:
          options.animationDuration ?? MapLibreWeb.defaultAnimationDuration,
      });
    } else {
      mapState.map.jumpTo(camera);
    }
  }

  async setFrame(): Promise<void> {
    throw this.unavailable(MapLibreWeb.errorUnavailable);
  }

  async setGesturesEnabled(options: SetGesturesEnabledOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    mapState.gestures = {
      pan: options.pan ?? mapState.gestures.pan,
      rotate: options.rotate ?? mapState.gestures.rotate,
      tilt: options.tilt ?? mapState.gestures.tilt,
      zoom: options.zoom ?? mapState.gestures.zoom,
    };
    this.applyGestures(mapState.map, mapState.gestures);
  }

  async setStyle(options: SetStyleOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    if ((options.json === undefined) === (options.url === undefined)) {
      throw this.createException(MapLibreWeb.errorStyleInvalid);
    }
    // A new style replaces all sources and layers of the map, so everything
    // that is managed by the plugin must be added again by the app.
    for (const markerId of Array.from(mapState.markers.keys())) {
      this.removeMarkerFromMap(mapState, markerId);
    }
    mapState.polylines.clear();
    mapState.map.setStyle(this.createStyle(options.json, options.url));
    await this.waitForStyle(mapState.map);
  }

  async updateGeoJsonSourceById(
    options: UpdateGeoJsonSourceByIdOptions,
  ): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const source = mapState.map.getSource(options.sourceId);
    if (!source) {
      throw this.createException(
        MapLibreWeb.errorSourceNotFound,
        ErrorCode.SourceNotFound,
      );
    }
    (source as MapLibreGl.GeoJSONSource).setData(
      this.getGeoJsonData(options.data, options.url),
    );
  }

  async updateMarkerById(options: UpdateMarkerByIdOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const markerState = this.getMarkerState(mapState, options.markerId);
    this.cancelMarkerAnimation(markerState);
    const animate = options.animate ?? false;
    const previousOptions = markerState.options;
    markerState.options = {
      ...previousOptions,
      coordinates: options.coordinates ?? previousOptions.coordinates,
      draggable: options.draggable ?? previousOptions.draggable,
      iconAnchor: options.iconAnchor ?? previousOptions.iconAnchor,
      iconSize: options.iconSize ?? previousOptions.iconSize,
      iconUrl: options.iconUrl ?? previousOptions.iconUrl,
      opacity: options.opacity ?? previousOptions.opacity,
      rotation: options.rotation ?? previousOptions.rotation,
    };
    if (this.isIconUpdate(options)) {
      // The icon element and the anchor of a MapLibre marker cannot be changed
      // after the marker has been created.
      markerState.marker.remove();
      markerState.marker = await this.createMarker(mapState, options.mapId, {
        ...markerState.options,
        coordinates: animate
          ? previousOptions.coordinates
          : markerState.options.coordinates,
        rotation: animate
          ? previousOptions.rotation
          : markerState.options.rotation,
      });
    } else {
      if (options.draggable !== undefined) {
        markerState.marker.setDraggable(options.draggable);
      }
      if (options.opacity !== undefined) {
        markerState.marker.setOpacity(options.opacity);
      }
      if (!animate && options.coordinates) {
        markerState.marker.setLngLat(this.toLngLat(options.coordinates));
      }
      if (!animate && options.rotation !== undefined) {
        markerState.marker.setRotation(options.rotation);
      }
    }
    if (animate && (options.coordinates || options.rotation !== undefined)) {
      this.animateMarker(
        markerState,
        options.coordinates,
        options.rotation,
        options.animationDuration ?? MapLibreWeb.defaultAnimationDuration,
      );
    }
  }

  async updatePolylineById(options: UpdatePolylineByIdOptions): Promise<void> {
    const mapState = this.getMapState(options.mapId);
    const previousPolyline = this.getPolyline(mapState, options.polylineId);
    const polyline: Polyline = {
      ...previousPolyline,
      color: options.color ?? previousPolyline.color,
      coordinates: options.coordinates ?? previousPolyline.coordinates,
      opacity: options.opacity ?? previousPolyline.opacity,
      width: options.width ?? previousPolyline.width,
    };
    mapState.polylines.set(polyline.id, polyline);
    const layerId = this.getPolylineLayerId(polyline.id);
    if (options.coordinates) {
      const source = mapState.map.getSource(
        layerId,
      ) as MapLibreGl.GeoJSONSource;
      source.setData(this.createPolylineData(options.coordinates));
    }
    const paint = this.createPolylinePaint(polyline);
    mapState.map.setPaintProperty(layerId, 'line-color', paint['line-color']);
    mapState.map.setPaintProperty(
      layerId,
      'line-opacity',
      paint['line-opacity'],
    );
    mapState.map.setPaintProperty(layerId, 'line-width', paint['line-width']);
  }

  private addMapListeners(map: MapLibreGl.Map, mapId: string): void {
    map.on('click', event => {
      this.notifyListeners(MapLibreWeb.eventMapClick, {
        coordinates: this.toLatLng(event.lngLat),
        mapId,
        point: { x: event.point.x, y: event.point.y },
      });
    });
    map.on('movestart', event => {
      this.notifyListeners(MapLibreWeb.eventCameraMoveStarted, {
        mapId,
        reason: event.originalEvent
          ? CameraMoveReason.Gesture
          : CameraMoveReason.Api,
      });
    });
    map.on('moveend', () => {
      this.notifyListeners(MapLibreWeb.eventCameraIdle, {
        camera: this.getCameraOfMap(map),
        mapId,
      });
    });
  }

  private async addMarkerToMap(
    mapState: MapState,
    mapId: string,
    marker: Marker,
  ): Promise<void> {
    this.removeMarkerFromMap(mapState, marker.id);
    mapState.markers.set(marker.id, {
      marker: await this.createMarker(mapState, mapId, marker),
      options: marker,
    });
  }

  private addPolylineToMap(mapState: MapState, polyline: Polyline): void {
    this.removePolylineFromMap(mapState, polyline.id);
    const layerId = this.getPolylineLayerId(polyline.id);
    mapState.map.addSource(layerId, {
      data: this.createPolylineData(polyline.coordinates),
      type: 'geojson',
    });
    mapState.map.addLayer({
      id: layerId,
      layout: { 'line-cap': 'round', 'line-join': 'round' },
      paint: this.createPolylinePaint(polyline),
      source: layerId,
      type: 'line',
    });
    mapState.polylines.set(polyline.id, polyline);
  }

  private animateMarker(
    markerState: MarkerState,
    coordinates: LatLng | undefined,
    rotation: number | undefined,
    duration: number,
  ): void {
    const marker = markerState.marker;
    const startCoordinates = this.toLatLng(marker.getLngLat());
    const startRotation = marker.getRotation();
    const startTime = performance.now();
    const step = (time: number): void => {
      const progress =
        duration > 0 ? Math.min((time - startTime) / duration, 1) : 1;
      if (coordinates) {
        marker.setLngLat([
          this.interpolate(
            startCoordinates.longitude,
            coordinates.longitude,
            progress,
          ),
          this.interpolate(
            startCoordinates.latitude,
            coordinates.latitude,
            progress,
          ),
        ]);
      }
      if (rotation !== undefined) {
        marker.setRotation(this.interpolate(startRotation, rotation, progress));
      }
      markerState.animationFrameId =
        progress < 1 ? requestAnimationFrame(step) : undefined;
    };
    markerState.animationFrameId = requestAnimationFrame(step);
  }

  /**
   * MapLibre bundles its gestures into handlers that do not match the gestures
   * of the plugin one to one:
   *
   * - `pan` toggles the drag and the keyboard handler.
   * - `zoom` toggles the scroll, double click, box and two finger handler.
   * - `rotate` toggles the drag rotation and the two finger rotation, which is
   *   only available while `zoom` is enabled.
   * - `tilt` toggles the two finger pitch handler.
   */
  private applyGestures(
    map: MapLibreGl.Map,
    gestures: Required<GestureSettings>,
  ): void {
    this.toggleGestureHandler(map.dragPan, gestures.pan);
    this.toggleGestureHandler(map.keyboard, gestures.pan);
    this.toggleGestureHandler(map.boxZoom, gestures.zoom);
    this.toggleGestureHandler(map.doubleClickZoom, gestures.zoom);
    this.toggleGestureHandler(map.scrollZoom, gestures.zoom);
    this.toggleGestureHandler(map.touchZoomRotate, gestures.zoom);
    this.toggleGestureHandler(map.dragRotate, gestures.rotate);
    if (gestures.rotate) {
      map.touchZoomRotate.enableRotation();
    } else {
      map.touchZoomRotate.disableRotation();
    }
    this.toggleGestureHandler(map.touchPitch, gestures.tilt);
  }

  private cancelMarkerAnimation(markerState: MarkerState): void {
    if (markerState.animationFrameId !== undefined) {
      cancelAnimationFrame(markerState.animationFrameId);
      markerState.animationFrameId = undefined;
    }
  }

  private createException(
    message: string,
    code?: ErrorCode,
  ): CapacitorException {
    return new CapacitorException(
      message,
      undefined,
      code === undefined ? undefined : { code },
    );
  }

  private createLayerPaint(
    type: LayerType,
    paint: LayerPaint | undefined,
  ): Record<string, unknown> {
    const result: Record<string, unknown> = {};
    if (!paint) {
      return result;
    }
    switch (type) {
      case LayerType.Circle:
        this.setPaintProperty(result, 'circle-color', paint.circleColor);
        this.setPaintProperty(result, 'circle-opacity', paint.circleOpacity);
        this.setPaintProperty(result, 'circle-radius', paint.circleRadius);
        this.setPaintProperty(
          result,
          'circle-stroke-color',
          paint.circleStrokeColor,
        );
        this.setPaintProperty(
          result,
          'circle-stroke-width',
          paint.circleStrokeWidth,
        );
        break;
      case LayerType.Fill:
        this.setPaintProperty(result, 'fill-color', paint.fillColor);
        this.setPaintProperty(result, 'fill-opacity', paint.fillOpacity);
        this.setPaintProperty(
          result,
          'fill-outline-color',
          paint.fillOutlineColor,
        );
        break;
      case LayerType.Line:
        this.setPaintProperty(result, 'line-color', paint.lineColor);
        this.setPaintProperty(result, 'line-opacity', paint.lineOpacity);
        this.setPaintProperty(result, 'line-width', paint.lineWidth);
        break;
    }
    return result;
  }

  private async createMarker(
    mapState: MapState,
    mapId: string,
    marker: Marker,
  ): Promise<MapLibreGl.Marker> {
    const mapLibreGl = await this.getMapLibreGl();
    const instance = new mapLibreGl.Marker(this.createMarkerOptions(marker));
    instance.setLngLat(this.toLngLat(marker.coordinates)).addTo(mapState.map);
    instance.getElement().addEventListener('click', event => {
      // The marker is a child of the map, so the click would also be reported
      // as a click on the map.
      event.stopPropagation();
      this.notifyListeners(MapLibreWeb.eventMarkerClick, {
        coordinates: this.toLatLng(instance.getLngLat()),
        mapId,
        markerId: marker.id,
      });
    });
    instance.on('dragstart', () => {
      this.notifyMarkerDrag(
        MapLibreWeb.eventMarkerDragStart,
        instance,
        mapId,
        marker.id,
      );
    });
    instance.on('drag', () => {
      this.notifyMarkerDrag(
        MapLibreWeb.eventMarkerDrag,
        instance,
        mapId,
        marker.id,
      );
    });
    instance.on('dragend', () => {
      this.notifyMarkerDrag(
        MapLibreWeb.eventMarkerDragEnd,
        instance,
        mapId,
        marker.id,
      );
    });
    return instance;
  }

  private createMarkerIconElement(
    iconUrl: string,
    marker: Marker,
  ): HTMLElement {
    const element = document.createElement('img');
    element.src = iconUrl;
    element.style.display = 'block';
    if (marker.iconSize) {
      element.style.height = `${marker.iconSize.height}px`;
      element.style.width = `${marker.iconSize.width}px`;
    }
    return element;
  }

  private createMarkerOptions(marker: Marker): MapLibreGl.MarkerOptions {
    const options: MapLibreGl.MarkerOptions = {
      color: MapLibreWeb.defaultMarkerColor,
      draggable: marker.draggable ?? false,
      opacity: marker.opacity,
      rotation: marker.rotation ?? 0,
      rotationAlignment: 'map',
    };
    if (marker.iconUrl) {
      options.anchor = marker.iconAnchor ?? MarkerIconAnchor.Bottom;
      options.element = this.createMarkerIconElement(marker.iconUrl, marker);
    }
    return options;
  }

  private createPolylineData(coordinates: LatLng[]): GeoJsonData {
    return {
      geometry: {
        coordinates: coordinates.map(coordinate => [
          coordinate.longitude,
          coordinate.latitude,
        ]),
        type: 'LineString',
      },
      properties: {},
      type: 'Feature',
    };
  }

  private createPolylinePaint(polyline: Polyline): PolylinePaint {
    return {
      'line-color': polyline.color ?? MapLibreWeb.defaultPolylineColor,
      'line-opacity': polyline.opacity ?? 1,
      'line-width': polyline.width ?? MapLibreWeb.defaultPolylineWidth,
    };
  }

  private createStyle(json?: string, url?: string): MapStyle {
    if (json) {
      return JSON.parse(json) as MapStyle;
    }
    return url ?? MapLibreWeb.defaultStyleUrl;
  }

  private createUserLocationElement(): HTMLElement {
    const element = document.createElement('div');
    element.style.backgroundColor = MapLibreWeb.userLocationColor;
    element.style.border = '2px solid #ffffff';
    element.style.borderRadius = '50%';
    element.style.boxShadow = '0 1px 4px rgba(0, 0, 0, 0.35)';
    element.style.boxSizing = 'border-box';
    element.style.height = '12px';
    element.style.width = '12px';
    return element;
  }

  private getCameraOfMap(map: MapLibreGl.Map): Camera {
    return {
      bearing: map.getBearing(),
      center: this.toLatLng(map.getCenter()),
      pitch: map.getPitch(),
      zoom: map.getZoom(),
    };
  }

  private getGeoJsonData(
    data: Record<string, unknown> | undefined,
    url: string | undefined,
  ): GeoJsonData {
    if ((data === undefined) === (url === undefined)) {
      throw this.createException(MapLibreWeb.errorGeoJsonDataInvalid);
    }
    return (data ?? url) as GeoJsonData;
  }

  private getMapLibreGl(): Promise<typeof MapLibreGl> {
    if (!this.mapLibreGlPromise) {
      this.mapLibreGlPromise = import('maplibre-gl');
    }
    return this.mapLibreGlPromise;
  }

  private getMapState(mapId: string): MapState {
    const mapState = this.maps.get(mapId);
    if (!mapState) {
      throw this.createException(
        MapLibreWeb.errorMapNotFound,
        ErrorCode.MapNotFound,
      );
    }
    return mapState;
  }

  private getMarkerState(mapState: MapState, markerId: string): MarkerState {
    const markerState = mapState.markers.get(markerId);
    if (!markerState) {
      throw this.createException(
        MapLibreWeb.errorMarkerNotFound,
        ErrorCode.MarkerNotFound,
      );
    }
    return markerState;
  }

  private getPolyline(mapState: MapState, polylineId: string): Polyline {
    const polyline = mapState.polylines.get(polylineId);
    if (!polyline) {
      throw this.createException(
        MapLibreWeb.errorPolylineNotFound,
        ErrorCode.PolylineNotFound,
      );
    }
    return polyline;
  }

  private getPolylineLayerId(polylineId: string): string {
    return `${MapLibreWeb.polylineIdPrefix}${polylineId}`;
  }

  private handleUserLocationChange(
    mapId: string,
    position: GeolocationPosition,
  ): void {
    const mapState = this.maps.get(mapId);
    const userLocation = mapState?.userLocation;
    if (!mapState || !userLocation) {
      return;
    }
    const coordinates: LatLng = {
      latitude: position.coords.latitude,
      longitude: position.coords.longitude,
    };
    userLocation.marker.setLngLat(this.toLngLat(coordinates));
    if (!userLocation.markerAdded) {
      userLocation.marker.addTo(mapState.map);
      userLocation.markerAdded = true;
    }
    if (userLocation.trackingMode !== UserTrackingMode.None) {
      mapState.map.easeTo({ center: this.toLngLat(coordinates) });
    }
    this.notifyListeners(MapLibreWeb.eventUserLocationChange, {
      accuracy: position.coords.accuracy,
      coordinates,
      heading: position.coords.heading ?? undefined,
      mapId,
      speed: position.coords.speed ?? undefined,
    });
  }

  private interpolate(from: number, to: number, progress: number): number {
    return from + (to - from) * progress;
  }

  private isIconUpdate(options: UpdateMarkerByIdOptions): boolean {
    return (
      options.iconAnchor !== undefined ||
      options.iconSize !== undefined ||
      options.iconUrl !== undefined
    );
  }

  private notifyMarkerDrag(
    eventName: string,
    marker: MapLibreGl.Marker,
    mapId: string,
    markerId: string,
  ): void {
    this.notifyListeners(eventName, {
      coordinates: this.toLatLng(marker.getLngLat()),
      mapId,
      markerId,
    });
  }

  private removeMarkerFromMap(mapState: MapState, markerId: string): void {
    const markerState = mapState.markers.get(markerId);
    if (!markerState) {
      return;
    }
    this.cancelMarkerAnimation(markerState);
    markerState.marker.remove();
    mapState.markers.delete(markerId);
  }

  private removePolylineFromMap(mapState: MapState, polylineId: string): void {
    if (!mapState.polylines.has(polylineId)) {
      return;
    }
    const layerId = this.getPolylineLayerId(polylineId);
    mapState.map.removeLayer(layerId);
    mapState.map.removeSource(layerId);
    mapState.polylines.delete(polylineId);
  }

  private setPaintProperty(
    paint: Record<string, unknown>,
    name: string,
    value: number | string | undefined,
  ): void {
    if (value !== undefined) {
      paint[name] = value;
    }
  }

  private stopUserLocation(mapState: MapState): void {
    if (!mapState.userLocation) {
      return;
    }
    navigator.geolocation.clearWatch(mapState.userLocation.watchId);
    mapState.userLocation.marker.remove();
    mapState.userLocation = undefined;
  }

  private toggleGestureHandler(
    handler: GestureHandler,
    enabled: boolean,
  ): void {
    if (enabled) {
      handler.enable();
    } else {
      handler.disable();
    }
  }

  private toLatLng(lngLat: { lat: number; lng: number }): LatLng {
    return { latitude: lngLat.lat, longitude: lngLat.lng };
  }

  private toLngLat(coordinates: LatLng): [number, number] {
    return [coordinates.longitude, coordinates.latitude];
  }

  private toPadding(
    padding: Padding | undefined,
  ): { bottom: number; left: number; right: number; top: number } | undefined {
    if (!padding) {
      return undefined;
    }
    return {
      bottom: padding.bottom ?? 0,
      left: padding.left ?? 0,
      right: padding.right ?? 0,
      top: padding.top ?? 0,
    };
  }

  private waitForStyle(map: MapLibreGl.Map): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      const handleError = (): void => {
        removeListeners();
        reject(
          this.createException(
            MapLibreWeb.errorStyleLoadFailed,
            ErrorCode.StyleLoadFailed,
          ),
        );
      };
      const handleStyleLoad = (): void => {
        removeListeners();
        resolve();
      };
      const removeListeners = (): void => {
        map.off('error', handleError);
        map.off('style.load', handleStyleLoad);
      };
      map.on('error', handleError);
      map.on('style.load', handleStyleLoad);
    });
  }
}
