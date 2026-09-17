import { CapacitorException, WebPlugin } from '@capacitor/core';

import type {
  CreatePlayerOptions,
  CreatePlayerResult,
  CueVideoOptions,
  GetCurrentTimeOptions,
  GetCurrentTimeResult,
  GetDurationOptions,
  GetDurationResult,
  LoadVideoOptions,
  MuteOptions,
  PauseOptions,
  PlayOptions,
  PlayerErrorCode,
  PlayerFrame,
  PlayerState,
  RemovePlayerOptions,
  SeekToOptions,
  SetPlaybackRateOptions,
  SetPlayerFrameOptions,
  SetVolumeOptions,
  UnmuteOptions,
  YoutubePlayerPlugin,
} from './definitions';
import { ErrorCode } from './definitions';

interface YouTubePlayer {
  cueVideoById(videoId: string, startSeconds?: number): void;
  destroy(): void;
  getCurrentTime(): number;
  getDuration(): number;
  getIframe(): HTMLIFrameElement;
  loadVideoById(videoId: string, startSeconds?: number): void;
  mute(): void;
  pauseVideo(): void;
  playVideo(): void;
  seekTo(seconds: number, allowSeekAhead: boolean): void;
  setPlaybackRate(rate: number): void;
  setVolume(volume: number): void;
  unMute(): void;
}

interface YouTubePlayerOptions {
  events: {
    onError: (event: { data: number }) => void;
    onPlaybackRateChange: (event: { data: number }) => void;
    onReady: () => void;
    onStateChange: (event: { data: number }) => void;
  };
  height: string;
  playerVars: Record<string, string | number>;
  videoId?: string;
  width: string;
}

interface YouTubeIframeApi {
  Player: new (
    element: HTMLElement,
    options: YouTubePlayerOptions,
  ) => YouTubePlayer;
}

interface YouTubeWindow {
  YT?: YouTubeIframeApi;
  onYouTubeIframeAPIReady?: () => void;
}

interface PlayerInstance {
  container: HTMLElement;
  fullscreen: boolean;
  handleFullscreenChange: () => void;
  intervalId: number | undefined;
  ownsContainer: boolean;
  player: YouTubePlayer;
  readyPromise: Promise<void>;
}

export class YoutubePlayerWeb extends WebPlugin implements YoutubePlayerPlugin {
  private static readonly allowedPlaybackRates = [
    0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2,
  ];
  private static readonly currentTimeChangeIntervalMs = 500;
  private static readonly errorApiFailedToLoad =
    'The YouTube IFrame API could not be loaded.';
  private static readonly errorElementNotFound =
    'no element found with the provided elementId.';
  private static readonly errorFrameInvalid =
    'frame must contain numeric x, y, width and height values.';
  private static readonly errorFrameMissing = 'frame must be provided.';
  private static readonly errorFrameTooSmall =
    'frame must be at least 200×200 pixels.';
  private static readonly errorPlayerAlreadyExists =
    'a player with the provided id already exists.';
  private static readonly errorPlayerIdInvalid =
    'no player found with the provided id.';
  private static readonly errorPlayerIdMissing = 'id must be provided.';
  private static readonly errorRateInvalid =
    'rate must be one of 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75 or 2.';
  private static readonly errorSecondsMissing = 'seconds must be provided.';
  private static readonly errorVideoIdMissing = 'videoId must be provided.';
  private static readonly errorVolumeInvalid =
    'volume must be a number between 0 and 100.';
  private static readonly eventCurrentTimeChange = 'currentTimeChange';
  private static readonly eventFullscreenChange = 'fullscreenChange';
  private static readonly eventPlaybackRateChange = 'playbackRateChange';
  private static readonly eventPlayerError = 'playerError';
  private static readonly eventPlayerReady = 'playerReady';
  private static readonly eventPlayerStateChange = 'playerStateChange';
  private static readonly minimumPlayerSize = 200;

  private apiPromise: Promise<YouTubeIframeApi> | undefined;
  private readonly players = new Map<string, PlayerInstance>();

  async createPlayer(
    options: CreatePlayerOptions,
  ): Promise<CreatePlayerResult> {
    const id = options.id ?? this.generatePlayerId();
    if (this.players.has(id)) {
      throw this.createException(YoutubePlayerWeb.errorPlayerAlreadyExists);
    }
    const frame = this.getValidFrame(options.frame);
    const api = await this.loadApi();
    let container: HTMLElement;
    let ownsContainer: boolean;
    const elementId = options.web?.elementId;
    if (elementId) {
      const element = document.getElementById(elementId);
      if (!element) {
        throw this.createException(YoutubePlayerWeb.errorElementNotFound);
      }
      container = element;
      ownsContainer = false;
    } else {
      container = document.createElement('div');
      this.applyFrameToContainer(container, frame);
      document.body.appendChild(container);
      ownsContainer = true;
    }
    const mountElement = document.createElement('div');
    container.appendChild(mountElement);
    let resolveReady = (): void => undefined;
    const readyPromise = new Promise<void>(resolve => {
      resolveReady = resolve;
    });
    const player = new api.Player(mountElement, {
      events: {
        onError: event => {
          this.notifyListeners(YoutubePlayerWeb.eventPlayerError, {
            code: this.mapPlayerErrorCode(event.data),
            id,
          });
        },
        onPlaybackRateChange: event => {
          this.notifyListeners(YoutubePlayerWeb.eventPlaybackRateChange, {
            id,
            rate: event.data,
          });
        },
        onReady: () => {
          resolveReady();
          this.notifyListeners(YoutubePlayerWeb.eventPlayerReady, { id });
        },
        onStateChange: event => {
          this.handlePlayerStateChange(id, event.data);
        },
      },
      height: '100%',
      playerVars: this.createPlayerVars(options),
      videoId: options.videoId,
      width: '100%',
    });
    const handleFullscreenChange = (): void => {
      this.handleFullscreenChange(id);
    };
    document.addEventListener('fullscreenchange', handleFullscreenChange);
    this.players.set(id, {
      container,
      fullscreen: false,
      handleFullscreenChange,
      intervalId: undefined,
      ownsContainer,
      player,
      readyPromise,
    });
    return { id };
  }

  async cueVideo(options: CueVideoOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    const videoId = this.getValidVideoId(options.videoId);
    await instance.readyPromise;
    instance.player.cueVideoById(videoId, options.startSeconds ?? 0);
  }

  async getCurrentTime(
    options: GetCurrentTimeOptions,
  ): Promise<GetCurrentTimeResult> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    return { currentTime: instance.player.getCurrentTime() };
  }

  async getDuration(options: GetDurationOptions): Promise<GetDurationResult> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    return { duration: instance.player.getDuration() };
  }

  async loadVideo(options: LoadVideoOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    const videoId = this.getValidVideoId(options.videoId);
    await instance.readyPromise;
    instance.player.loadVideoById(videoId, options.startSeconds ?? 0);
  }

  async mute(options: MuteOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    instance.player.mute();
  }

  async pause(options: PauseOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    instance.player.pauseVideo();
  }

  async play(options: PlayOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    instance.player.playVideo();
  }

  async removePlayer(options: RemovePlayerOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    if (instance.intervalId !== undefined) {
      window.clearInterval(instance.intervalId);
    }
    document.removeEventListener(
      'fullscreenchange',
      instance.handleFullscreenChange,
    );
    instance.player.destroy();
    if (instance.ownsContainer) {
      instance.container.remove();
    }
    this.players.delete(options.id);
  }

  async seekTo(options: SeekToOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    if (options.seconds === undefined) {
      throw this.createException(
        YoutubePlayerWeb.errorSecondsMissing,
        ErrorCode.SecondsMissing,
      );
    }
    await instance.readyPromise;
    instance.player.seekTo(options.seconds, true);
  }

  async setPlaybackRate(options: SetPlaybackRateOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    if (!YoutubePlayerWeb.allowedPlaybackRates.includes(options.rate)) {
      throw this.createException(
        YoutubePlayerWeb.errorRateInvalid,
        ErrorCode.RateInvalid,
      );
    }
    await instance.readyPromise;
    instance.player.setPlaybackRate(options.rate);
  }

  async setPlayerFrame(options: SetPlayerFrameOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    const frame = this.getValidFrame(options.frame);
    if (instance.ownsContainer) {
      this.applyFrameToContainer(instance.container, frame);
    }
  }

  async setVolume(options: SetVolumeOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    if (
      typeof options.volume !== 'number' ||
      options.volume < 0 ||
      options.volume > 100
    ) {
      throw this.createException(
        YoutubePlayerWeb.errorVolumeInvalid,
        ErrorCode.VolumeInvalid,
      );
    }
    await instance.readyPromise;
    instance.player.setVolume(options.volume);
  }

  async unmute(options: UnmuteOptions): Promise<void> {
    const instance = this.getPlayerInstance(options.id);
    await instance.readyPromise;
    instance.player.unMute();
  }

  private applyFrameToContainer(
    container: HTMLElement,
    frame: PlayerFrame,
  ): void {
    container.style.position = 'fixed';
    container.style.left = `${frame.x}px`;
    container.style.top = `${frame.y}px`;
    container.style.width = `${frame.width}px`;
    container.style.height = `${frame.height}px`;
    container.style.zIndex = '9999';
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

  private createPlayerVars(
    options: CreatePlayerOptions,
  ): Record<string, string | number> {
    const playerOptions = options.options;
    const playerVars: Record<string, string | number> = {
      autoplay: playerOptions?.autoplay ? 1 : 0,
      controls: playerOptions?.controls === false ? 0 : 1,
      fs: playerOptions?.fullscreen ? 1 : 0,
      iv_load_policy: playerOptions?.ivLoadPolicy ? 1 : 3,
      mute: playerOptions?.mute ? 1 : 0,
      origin: window.location.origin,
      playsinline: 1,
      rel: playerOptions?.rel ? 1 : 0,
    };
    if (playerOptions?.ccLoadPolicy) {
      playerVars.cc_load_policy = 1;
    }
    if (playerOptions?.end !== undefined) {
      playerVars.end = playerOptions.end;
    }
    if (playerOptions?.start !== undefined) {
      playerVars.start = playerOptions.start;
    }
    return playerVars;
  }

  private generatePlayerId(): string {
    return `player-${Date.now()}-${Math.random().toString(36).slice(2, 10)}`;
  }

  private getPlayerInstance(id: string | undefined): PlayerInstance {
    if (!id) {
      throw this.createException(
        YoutubePlayerWeb.errorPlayerIdMissing,
        ErrorCode.PlayerIdMissing,
      );
    }
    const instance = this.players.get(id);
    if (!instance) {
      throw this.createException(
        YoutubePlayerWeb.errorPlayerIdInvalid,
        ErrorCode.PlayerIdInvalid,
      );
    }
    return instance;
  }

  private getValidFrame(frame: PlayerFrame | undefined): PlayerFrame {
    if (!frame) {
      throw this.createException(
        YoutubePlayerWeb.errorFrameMissing,
        ErrorCode.FrameMissing,
      );
    }
    if (
      !Number.isFinite(frame.x) ||
      !Number.isFinite(frame.y) ||
      !Number.isFinite(frame.width) ||
      !Number.isFinite(frame.height)
    ) {
      throw this.createException(
        YoutubePlayerWeb.errorFrameInvalid,
        ErrorCode.FrameInvalid,
      );
    }
    if (
      frame.width < YoutubePlayerWeb.minimumPlayerSize ||
      frame.height < YoutubePlayerWeb.minimumPlayerSize
    ) {
      throw this.createException(
        YoutubePlayerWeb.errorFrameTooSmall,
        ErrorCode.FrameInvalid,
      );
    }
    return frame;
  }

  private getValidVideoId(videoId: string | undefined): string {
    if (!videoId) {
      throw this.createException(
        YoutubePlayerWeb.errorVideoIdMissing,
        ErrorCode.VideoIdMissing,
      );
    }
    return videoId;
  }

  private handleFullscreenChange(id: string): void {
    const instance = this.players.get(id);
    if (!instance) {
      return;
    }
    const fullscreen =
      document.fullscreenElement === instance.player.getIframe();
    if (fullscreen === instance.fullscreen) {
      return;
    }
    instance.fullscreen = fullscreen;
    this.notifyListeners(YoutubePlayerWeb.eventFullscreenChange, {
      fullscreen,
      id,
    });
  }

  private handlePlayerStateChange(id: string, data: number): void {
    const instance = this.players.get(id);
    if (!instance) {
      return;
    }
    if (data === 1) {
      if (instance.intervalId === undefined) {
        instance.intervalId = window.setInterval(() => {
          this.notifyListeners(YoutubePlayerWeb.eventCurrentTimeChange, {
            currentTime: instance.player.getCurrentTime(),
            id,
          });
        }, YoutubePlayerWeb.currentTimeChangeIntervalMs);
      }
    } else if (instance.intervalId !== undefined) {
      window.clearInterval(instance.intervalId);
      instance.intervalId = undefined;
    }
    const state = this.mapPlayerState(data);
    if (state) {
      this.notifyListeners(YoutubePlayerWeb.eventPlayerStateChange, {
        id,
        state,
      });
    }
  }

  private loadApi(): Promise<YouTubeIframeApi> {
    if (!this.apiPromise) {
      this.apiPromise = new Promise<YouTubeIframeApi>((resolve, reject) => {
        const youtubeWindow = window as YouTubeWindow;
        if (youtubeWindow.YT?.Player) {
          resolve(youtubeWindow.YT);
          return;
        }
        const previousCallback = youtubeWindow.onYouTubeIframeAPIReady;
        youtubeWindow.onYouTubeIframeAPIReady = () => {
          previousCallback?.();
          const api = (window as YouTubeWindow).YT;
          if (api) {
            resolve(api);
          } else {
            reject(
              this.createException(
                YoutubePlayerWeb.errorApiFailedToLoad,
                ErrorCode.CreateFailed,
              ),
            );
          }
        };
        const script = document.createElement('script');
        script.src = 'https://www.youtube.com/iframe_api';
        script.onerror = () => {
          this.apiPromise = undefined;
          reject(
            this.createException(
              YoutubePlayerWeb.errorApiFailedToLoad,
              ErrorCode.CreateFailed,
            ),
          );
        };
        document.head.appendChild(script);
      });
    }
    return this.apiPromise;
  }

  private mapPlayerErrorCode(data: number): PlayerErrorCode {
    switch (data) {
      case 2:
        return 'invalid-parameter';
      case 5:
        return 'html5-error';
      case 100:
        return 'video-not-found';
      case 101:
      case 150:
        return 'not-embeddable';
      default:
        return 'unknown';
    }
  }

  private mapPlayerState(data: number): PlayerState | undefined {
    switch (data) {
      case -1:
        return 'unstarted';
      case 0:
        return 'ended';
      case 1:
        return 'playing';
      case 2:
        return 'paused';
      case 3:
        return 'buffering';
      case 5:
        return 'cued';
      default:
        return undefined;
    }
  }
}
