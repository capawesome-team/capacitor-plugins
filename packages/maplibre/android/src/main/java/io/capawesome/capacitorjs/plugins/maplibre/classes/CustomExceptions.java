package io.capawesome.capacitorjs.plugins.maplibre.classes;

public class CustomExceptions {

    public static final CustomException BOUNDS_MISSING = new CustomException(null, "bounds must be provided.");
    public static final CustomException COLOR_INVALID = new CustomException(
        null,
        "color must be a hexadecimal string in the format #RRGGBB or #RRGGBBAA."
    );
    public static final CustomException COORDINATES_INVALID = new CustomException(
        null,
        "coordinates must contain a numeric latitude and longitude value."
    );
    public static final CustomException COORDINATES_MISSING = new CustomException(null, "coordinates must be provided.");
    public static final CustomException DATA_OR_URL_MISSING = new CustomException(null, "exactly one of data and url must be provided.");
    public static final CustomException FRAME_MISSING = new CustomException(null, "frame must be provided.");
    public static final CustomException ICON_LOAD_FAILED = new CustomException(null, "the icon of the marker could not be loaded.");
    public static final CustomException JSON_OR_URL_MISSING = new CustomException(null, "exactly one of json and url must be provided.");
    public static final CustomException LAYER_ID_MISSING = new CustomException(null, "layerId must be provided.");
    public static final CustomException LAYER_NOT_FOUND = new CustomException("LAYER_NOT_FOUND", "layer not found.");
    public static final CustomException LAYER_TYPE_INVALID = new CustomException(null, "type must be one of circle, fill or line.");
    public static final CustomException LOCATION_PERMISSION_DENIED = new CustomException(
        "LOCATION_PERMISSION_DENIED",
        "location permission denied."
    );
    public static final CustomException MAP_ALREADY_EXISTS = new CustomException(null, "a map with the provided mapId already exists.");
    public static final CustomException MAP_CREATE_FAILED = new CustomException(null, "the map could not be created.");
    public static final CustomException MAP_ID_MISSING = new CustomException(null, "mapId must be provided.");
    public static final CustomException MAP_NOT_FOUND = new CustomException("MAP_NOT_FOUND", "map not found.");
    public static final CustomException MAP_NOT_READY = new CustomException(null, "the map is not ready yet.");
    public static final CustomException MARKER_ID_MISSING = new CustomException(null, "markerId must be provided.");
    public static final CustomException MARKER_IDS_MISSING = new CustomException(null, "markerIds must be provided.");
    public static final CustomException MARKER_MISSING = new CustomException(null, "marker must be provided.");
    public static final CustomException MARKER_NOT_FOUND = new CustomException("MARKER_NOT_FOUND", "marker not found.");
    public static final CustomException MARKERS_MISSING = new CustomException(null, "markers must be provided.");
    public static final CustomException POLYLINE_ID_MISSING = new CustomException(null, "polylineId must be provided.");
    public static final CustomException POLYLINE_IDS_MISSING = new CustomException(null, "polylineIds must be provided.");
    public static final CustomException POLYLINE_MISSING = new CustomException(null, "polyline must be provided.");
    public static final CustomException POLYLINE_NOT_FOUND = new CustomException("POLYLINE_NOT_FOUND", "polyline not found.");
    public static final CustomException POLYLINES_MISSING = new CustomException(null, "polylines must be provided.");
    public static final CustomException REQUEST_ID_MISSING = new CustomException(null, "requestId must be provided.");
    public static final CustomException SOURCE_ID_MISSING = new CustomException(null, "sourceId must be provided.");
    public static final CustomException SOURCE_NOT_FOUND = new CustomException("SOURCE_NOT_FOUND", "source not found.");
    public static final CustomException STYLE_LOAD_FAILED = new CustomException("STYLE_LOAD_FAILED", "style load failed.");
    public static final CustomException STYLE_NOT_LOADED = new CustomException(null, "the style of the map is not loaded yet.");
}
