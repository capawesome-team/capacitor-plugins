import type { PluginListenerHandle } from '@capacitor/core';

export interface YoutubePlayerPlugin {
  /**
   * Create a new YouTube player.
   *
   * On Android and iOS, the player is rendered as a native view that is
   * positioned above the web view at the given frame. The frame is not
   * scrolled with the web content. Use `setPlayerFrame(...)` to keep the
   * frame in sync with your layout (see the README for a recipe).
   *
   * The player must be at least 200×200 CSS pixels, as required by the
   * [YouTube Terms of Service](https://developers.google.com/youtube/terms/required-minimum-functionality#embedded-player-size).
   *
   * @since 0.1.0
   */
  createPlayer(options: CreatePlayerOptions): Promise<CreatePlayerResult>;
  /**
   * Load a video into the player without starting playback.
   *
   * @since 0.1.0
   */
  cueVideo(options: CueVideoOptions): Promise<void>;
  /**
   * Get the current playback time of the player.
   *
   * On Android, the value is answered from the most recent value pushed by
   * the player (updated multiple times per second during playback).
   *
   * @since 0.1.0
   */
  getCurrentTime(options: GetCurrentTimeOptions): Promise<GetCurrentTimeResult>;
  /**
   * Get the duration of the currently loaded video.
   *
   * On Android, the value is answered from the most recent value pushed by
   * the player.
   *
   * @since 0.1.0
   */
  getDuration(options: GetDurationOptions): Promise<GetDurationResult>;
  /**
   * Load a video into the player and start playback.
   *
   * @since 0.1.0
   */
  loadVideo(options: LoadVideoOptions): Promise<void>;
  /**
   * Mute the player.
   *
   * @since 0.1.0
   */
  mute(options: MuteOptions): Promise<void>;
  /**
   * Pause playback.
   *
   * @since 0.1.0
   */
  pause(options: PauseOptions): Promise<void>;
  /**
   * Start or resume playback.
   *
   * @since 0.1.0
   */
  play(options: PlayOptions): Promise<void>;
  /**
   * Remove the player and release all its resources.
   *
   * @since 0.1.0
   */
  removePlayer(options: RemovePlayerOptions): Promise<void>;
  /**
   * Seek to the given time.
   *
   * @since 0.1.0
   */
  seekTo(options: SeekToOptions): Promise<void>;
  /**
   * Set the playback rate of the player.
   *
   * @since 0.1.0
   */
  setPlaybackRate(options: SetPlaybackRateOptions): Promise<void>;
  /**
   * Update the frame of the player.
   *
   * Call this method whenever the layout changes (e.g. on scroll, resize or
   * orientation change) to keep the player in sync with your layout.
   *
   * @since 0.1.0
   */
  setPlayerFrame(options: SetPlayerFrameOptions): Promise<void>;
  /**
   * Set the volume of the player.
   *
   * On iOS, the operating system does not allow changing the volume
   * programmatically, so this method has no effect. Use `mute()` and
   * `unmute()` instead.
   *
   * @since 0.1.0
   */
  setVolume(options: SetVolumeOptions): Promise<void>;
  /**
   * Unmute the player.
   *
   * @since 0.1.0
   */
  unmute(options: UnmuteOptions): Promise<void>;
  /**
   * Called when the current playback time of a player changes.
   *
   * The event is emitted multiple times per second during playback. The
   * exact frequency depends on the platform.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'currentTimeChange',
    listenerFunc: (event: CurrentTimeChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a player enters or exits fullscreen.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'fullscreenChange',
    listenerFunc: (event: FullscreenChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the playback rate of a player changes.
   *
   * On iOS, this event is only emitted for `setPlaybackRate(...)` calls.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'playbackRateChange',
    listenerFunc: (event: PlaybackRateChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an error occurs in a player.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'playerError',
    listenerFunc: (event: PlayerErrorEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a player has finished loading and is ready to receive
   * commands.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'playerReady',
    listenerFunc: (event: PlayerReadyEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the state of a player changes.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'playerStateChange',
    listenerFunc: (event: PlayerStateChangeEvent) => void,
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
export interface CreatePlayerOptions {
  /**
   * The frame of the player in CSS pixels, relative to the viewport.
   *
   * Must be at least 200×200 CSS pixels.
   *
   * @since 0.1.0
   */
  frame: PlayerFrame;
  /**
   * The unique identifier of the player.
   *
   * If not provided, a random identifier is generated.
   *
   * @since 0.1.0
   * @example 'my-player'
   */
  id?: string;
  /**
   * The player options.
   *
   * @since 0.1.0
   */
  options?: PlayerOptions;
  /**
   * The ID of the YouTube video to load into the player.
   *
   * If not provided, the player is created without a video. Load one with
   * `loadVideo(...)` or `cueVideo(...)`.
   *
   * @since 0.1.0
   * @example 'dQw4w9WgXcQ'
   */
  videoId?: string;
  /**
   * Options that are only available on Web.
   *
   * @since 0.1.0
   */
  web?: CreatePlayerWebOptions;
}

/**
 * @since 0.1.0
 */
export interface CreatePlayerResult {
  /**
   * The unique identifier of the created player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface CreatePlayerWebOptions {
  /**
   * The ID of an existing DOM element to mount the player into.
   *
   * If provided, the player fills this element instead of being positioned
   * at the given frame, and `setPlayerFrame(...)` has no effect.
   *
   * Only available on Web.
   *
   * @since 0.1.0
   * @example 'youtube-player'
   */
  elementId?: string;
}

/**
 * @since 0.1.0
 */
export interface CueVideoOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The time in seconds from which the video should start playing once it
   * is played.
   *
   * @default 0
   * @since 0.1.0
   */
  startSeconds?: number;
  /**
   * The ID of the YouTube video to cue.
   *
   * @since 0.1.0
   * @example 'dQw4w9WgXcQ'
   */
  videoId: string;
}

/**
 * @since 0.1.0
 */
export interface CurrentTimeChangeEvent {
  /**
   * The current playback time in seconds.
   *
   * @since 0.1.0
   */
  currentTime: number;
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface FullscreenChangeEvent {
  /**
   * Whether the player is in fullscreen.
   *
   * @since 0.1.0
   */
  fullscreen: boolean;
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentTimeOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentTimeResult {
  /**
   * The current playback time in seconds.
   *
   * @since 0.1.0
   */
  currentTime: number;
}

/**
 * @since 0.1.0
 */
export interface GetDurationOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface GetDurationResult {
  /**
   * The duration of the currently loaded video in seconds.
   *
   * Returns `0` if no video is loaded.
   *
   * @since 0.1.0
   */
  duration: number;
}

/**
 * @since 0.1.0
 */
export interface LoadVideoOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The time in seconds from which the video should start playing.
   *
   * @default 0
   * @since 0.1.0
   */
  startSeconds?: number;
  /**
   * The ID of the YouTube video to load.
   *
   * @since 0.1.0
   * @example 'dQw4w9WgXcQ'
   */
  videoId: string;
}

/**
 * @since 0.1.0
 */
export interface MuteOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface PauseOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface PlaybackRateChangeEvent {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The new playback rate.
   *
   * @since 0.1.0
   */
  rate: number;
}

/**
 * @since 0.1.0
 */
export interface PlayerErrorEvent {
  /**
   * The error code.
   *
   * @since 0.1.0
   */
  code: PlayerErrorCode;
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * The frame of a player in CSS pixels, relative to the viewport.
 *
 * @since 0.1.0
 */
export interface PlayerFrame {
  /**
   * The height of the player in CSS pixels.
   *
   * Must be at least `200`.
   *
   * @since 0.1.0
   */
  height: number;
  /**
   * The width of the player in CSS pixels.
   *
   * Must be at least `200`.
   *
   * @since 0.1.0
   */
  width: number;
  /**
   * The x-coordinate of the player in CSS pixels.
   *
   * @since 0.1.0
   */
  x: number;
  /**
   * The y-coordinate of the player in CSS pixels.
   *
   * @since 0.1.0
   */
  y: number;
}

/**
 * @since 0.1.0
 */
export interface PlayerOptions {
  /**
   * Whether the video starts playing automatically when the player is
   * created.
   *
   * @default false
   * @since 0.1.0
   */
  autoplay?: boolean;
  /**
   * Whether closed captions are shown by default, even if the user has
   * turned captions off.
   *
   * @default false
   * @since 0.1.0
   */
  ccLoadPolicy?: boolean;
  /**
   * Whether the player controls are displayed.
   *
   * @default true
   * @since 0.1.0
   */
  controls?: boolean;
  /**
   * The time in seconds at which the player should stop playing the video.
   *
   * @since 0.1.0
   */
  end?: number;
  /**
   * Whether the fullscreen button is displayed.
   *
   * Only available on Android and Web.
   *
   * @default false
   * @since 0.1.0
   */
  fullscreen?: boolean;
  /**
   * Whether video annotations are shown by default.
   *
   * @default false
   * @since 0.1.0
   */
  ivLoadPolicy?: boolean;
  /**
   * Whether the player is muted when it is created.
   *
   * @default false
   * @since 0.1.0
   */
  mute?: boolean;
  /**
   * Whether related videos from other channels are shown when playback
   * ends. If `false`, related videos are limited to the video's channel.
   *
   * @default false
   * @since 0.1.0
   */
  rel?: boolean;
  /**
   * The time in seconds from which the video should start playing.
   *
   * @since 0.1.0
   */
  start?: number;
}

/**
 * @since 0.1.0
 */
export interface PlayerReadyEvent {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface PlayerStateChangeEvent {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The new state of the player.
   *
   * @since 0.1.0
   */
  state: PlayerState;
}

/**
 * @since 0.1.0
 */
export interface PlayOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface RemovePlayerOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface SeekToOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The time in seconds to seek to.
   *
   * @since 0.1.0
   */
  seconds: number;
}

/**
 * @since 0.1.0
 */
export interface SetPlaybackRateOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The playback rate.
   *
   * Must be one of `0.25`, `0.5`, `0.75`, `1`, `1.25`, `1.5`, `1.75` or
   * `2`.
   *
   * @since 0.1.0
   */
  rate: number;
}

/**
 * @since 0.1.0
 */
export interface SetPlayerFrameOptions {
  /**
   * The new frame of the player in CSS pixels, relative to the viewport.
   *
   * Must be at least 200×200 CSS pixels.
   *
   * @since 0.1.0
   */
  frame: PlayerFrame;
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface SetVolumeOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The volume as a value between `0` and `100`.
   *
   * @since 0.1.0
   */
  volume: number;
}

/**
 * @since 0.1.0
 */
export interface UnmuteOptions {
  /**
   * The unique identifier of the player.
   *
   * @since 0.1.0
   */
  id: string;
}

/**
 * The error code of a player error.
 *
 * @since 0.1.0
 */
export type PlayerErrorCode =
  | 'html5-error'
  | 'invalid-parameter'
  | 'not-embeddable'
  | 'unknown'
  | 'video-not-found';

/**
 * The state of a player.
 *
 * @since 0.1.0
 */
export type PlayerState =
  | 'buffering'
  | 'cued'
  | 'ended'
  | 'paused'
  | 'playing'
  | 'unstarted';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The player could not be created.
   *
   * @since 0.1.0
   */
  CreateFailed = 'CREATE_FAILED',
  /**
   * The provided frame is invalid, e.g. smaller than the required minimum
   * size of 200×200 CSS pixels.
   *
   * @since 0.1.0
   */
  FrameInvalid = 'FRAME_INVALID',
  /**
   * No frame was provided.
   *
   * @since 0.1.0
   */
  FrameMissing = 'FRAME_MISSING',
  /**
   * No player exists with the provided identifier.
   *
   * @since 0.1.0
   */
  PlayerIdInvalid = 'PLAYER_ID_INVALID',
  /**
   * No player identifier was provided.
   *
   * @since 0.1.0
   */
  PlayerIdMissing = 'PLAYER_ID_MISSING',
  /**
   * The provided playback rate is invalid.
   *
   * @since 0.1.0
   */
  RateInvalid = 'RATE_INVALID',
  /**
   * No seconds value was provided.
   *
   * @since 0.1.0
   */
  SecondsMissing = 'SECONDS_MISSING',
  /**
   * No video ID was provided.
   *
   * @since 0.1.0
   */
  VideoIdMissing = 'VIDEO_ID_MISSING',
  /**
   * The provided volume is invalid.
   *
   * @since 0.1.0
   */
  VolumeInvalid = 'VOLUME_INVALID',
}
