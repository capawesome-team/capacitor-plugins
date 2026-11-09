package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.FerrostarNavigationHelper;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomExceptions;
import uniffi.ferrostar.CourseFiltering;

public class StartNavigationOptions {

    @NonNull
    private final AndroidNotification androidNotification;

    private final int arrivalStepAdvanceDistanceMeters;

    private final boolean continueInBackground;

    @NonNull
    private final CourseFiltering courseFiltering;

    private final boolean muteVoice;

    @NonNull
    private final String osrmResponseJson;

    private final int polylinePrecision;

    private final double routeDeviationThresholdMeters;

    private final boolean simulateLocation;

    private final int simulationSpeedMultiplier;

    private final boolean speakInstructions;

    private final int stepAdvanceEntryDistanceMeters;

    private final int stepAdvanceExitDistanceMeters;

    @Nullable
    private final String voiceLanguage;

    private final double waypointAdvanceDistanceMeters;

    public StartNavigationOptions(@NonNull PluginCall call) throws Exception {
        this.androidNotification = new AndroidNotification(call.getObject("androidNotification"));
        this.arrivalStepAdvanceDistanceMeters = call.getInt("arrivalStepAdvanceDistanceMeters", 10);
        this.continueInBackground = call.getBoolean("continueInBackground", false);
        this.courseFiltering = StartNavigationOptions.getCourseFilteringFromCall(call);
        this.muteVoice = call.getBoolean("muteVoice", false);
        this.osrmResponseJson = FerrostarNavigationHelper.getOsrmResponseJsonFromCall(call);
        this.polylinePrecision = FerrostarNavigationHelper.getPolylinePrecisionFromCall(call);
        this.routeDeviationThresholdMeters = call.getDouble("routeDeviationThresholdMeters", 50.0);
        this.simulateLocation = call.getBoolean("simulateLocation", false);
        this.simulationSpeedMultiplier = call.getInt("simulationSpeedMultiplier", 1);
        this.speakInstructions = call.getBoolean("speakInstructions", true);
        this.stepAdvanceEntryDistanceMeters = call.getInt("stepAdvanceEntryDistanceMeters", 30);
        this.stepAdvanceExitDistanceMeters = call.getInt("stepAdvanceExitDistanceMeters", 5);
        this.voiceLanguage = call.getString("voiceLanguage");
        this.waypointAdvanceDistanceMeters = call.getDouble("waypointAdvanceDistanceMeters", 100.0);
    }

    @NonNull
    public AndroidNotification getAndroidNotification() {
        return androidNotification;
    }

    public int getArrivalStepAdvanceDistanceMeters() {
        return arrivalStepAdvanceDistanceMeters;
    }

    public boolean getContinueInBackground() {
        return continueInBackground;
    }

    @NonNull
    public CourseFiltering getCourseFiltering() {
        return courseFiltering;
    }

    public boolean getMuteVoice() {
        return muteVoice;
    }

    @NonNull
    public String getOsrmResponseJson() {
        return osrmResponseJson;
    }

    public int getPolylinePrecision() {
        return polylinePrecision;
    }

    public double getRouteDeviationThresholdMeters() {
        return routeDeviationThresholdMeters;
    }

    public boolean getSimulateLocation() {
        return simulateLocation;
    }

    public int getSimulationSpeedMultiplier() {
        return simulationSpeedMultiplier;
    }

    public boolean getSpeakInstructions() {
        return speakInstructions;
    }

    public int getStepAdvanceEntryDistanceMeters() {
        return stepAdvanceEntryDistanceMeters;
    }

    public int getStepAdvanceExitDistanceMeters() {
        return stepAdvanceExitDistanceMeters;
    }

    @Nullable
    public String getVoiceLanguage() {
        return voiceLanguage;
    }

    public double getWaypointAdvanceDistanceMeters() {
        return waypointAdvanceDistanceMeters;
    }

    @NonNull
    private static CourseFiltering getCourseFilteringFromCall(@NonNull PluginCall call) throws Exception {
        String courseFiltering = call.getString("courseFiltering", CourseFiltering.SNAP_TO_ROUTE.name());
        try {
            return CourseFiltering.valueOf(courseFiltering);
        } catch (IllegalArgumentException exception) {
            throw CustomExceptions.COURSE_FILTERING_INVALID;
        }
    }
}
