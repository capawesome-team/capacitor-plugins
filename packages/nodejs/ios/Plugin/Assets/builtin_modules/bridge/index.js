/**
 * Bridge module between the Capacitor app and the Node.js runtime.
 *
 * Based on the `rn-bridge` module of the `nodejs-mobile-react-native`
 * project (MIT licensed, https://github.com/nodejs-mobile/nodejs-mobile-react-native).
 */
const EventEmitter = require('events');

const NativeBridge = process._linkedBinding('capacitor_bridge');

/**
 * Built-in events channel to exchange events between the Capacitor app
 * and the Node.js app. It allows to emit user defined event types with
 * optional arguments.
 */
const EVENT_CHANNEL = '_EVENTS_';

/**
 * Built-in, one-way event channel reserved for sending events from
 * the plugin's native layer to the Node.js app.
 */
const SYSTEM_CHANNEL = '_SYSTEM_';

/**
 * The MessageCodec class provides two static methods to serialize and
 * deserialize the data sent through the events channel. The same codec
 * is implemented in the plugin's native layer.
 */
class MessageCodec {
  constructor(event, ...payload) {
    this.event = event;
    this.payload = JSON.stringify(payload);
  }

  static serialize(event, ...payload) {
    const envelope = new MessageCodec(event, ...payload);
    return JSON.stringify(envelope);
  }

  static deserialize(message) {
    const envelope = JSON.parse(message);
    if (typeof envelope.payload !== 'undefined') {
      envelope.payload = JSON.parse(envelope.payload);
    }
    return envelope;
  }
}

/**
 * Channel super class. The `emit` method is renamed to `emitLocal` to
 * clarify that emitting on this object has a local scope: it emits the
 * event on the Node.js side only, it doesn't send the event to the
 * Capacitor app.
 */
class ChannelSuper extends EventEmitter {
  constructor(name) {
    super();
    this.name = name;
    this.emitLocal = this.emit;
    delete this.emit;
  }

  emitWrapper(type, ...msg) {
    setImmediate(() => {
      this.emitLocal(type, ...msg);
    });
  }
}

/**
 * Events channel class that supports user defined event types with
 * optional arguments. Allows to send any serializable JavaScript object
 * supported by `JSON.stringify()`.
 */
class EventChannel extends ChannelSuper {
  post(event, ...msg) {
    NativeBridge.sendMessage(this.name, MessageCodec.serialize(event, ...msg));
  }

  send(...msg) {
    this.post('message', ...msg);
  }

  processData(data) {
    const envelope = MessageCodec.deserialize(data);
    this.emitWrapper(envelope.event, ...envelope.payload);
  }
}

/**
 * Helper class to handle lock acquisition and release in system event
 * handlers. Calls a callback after every lock has been released.
 */
class SystemEventLock {
  constructor(callback, startingLocks) {
    this._locksAcquired = startingLocks;
    this._callback = callback;
    this._hasReleased = false;
    this._checkRelease();
  }

  release() {
    if (this._hasReleased) {
      return;
    }
    this._locksAcquired--;
    this._checkRelease();
  }

  _checkRelease() {
    if (this._locksAcquired <= 0) {
      this._hasReleased = true;
      this._callback();
    }
  }
}

/**
 * System channel class. Emits `pause` and `resume` events when the app
 * goes to the background and foreground.
 */
class SystemChannel extends ChannelSuper {
  constructor(name) {
    super(name);
    this._cacheDataDir = null;
  }

  emitWrapper(type) {
    if (type.startsWith('pause')) {
      setImmediate(() => {
        // The expected format for the release message is
        // "release-pause-event|{eventId}" where eventId comes from the
        // pause event, with the format "pause|{eventId}".
        let releaseMessage = 'release-pause-event';
        const eventArguments = type.split('|');
        if (eventArguments.length >= 2) {
          releaseMessage = releaseMessage + '|' + eventArguments[1];
        }
        // Create a lock for each current event listener. All listeners
        // need to call release() before the native side is signaled.
        const eventLock = new SystemEventLock(() => {
          NativeBridge.sendMessage(this.name, releaseMessage);
        }, this.listenerCount('pause'));
        this.emitLocal('pause', eventLock);
      });
    } else {
      setImmediate(() => {
        this.emitLocal(type);
      });
    }
  }

  processData(data) {
    this.emitWrapper(data);
  }

  /**
   * Get a writable data directory for persistent file storage.
   */
  datadir() {
    if (this._cacheDataDir === null) {
      this._cacheDataDir = NativeBridge.getDataDir();
    }
    return this._cacheDataDir;
  }
}

/**
 * Manage the registered channels to emit events/messages received by the
 * Capacitor app or by the plugin itself (i.e. the system channel).
 */
const channels = {};

function bridgeListener(channelName, data) {
  if (Object.prototype.hasOwnProperty.call(channels, channelName)) {
    channels[channelName].processData(data);
  } else {
    console.error('ERROR: Channel not found:', channelName);
  }
}

/**
 * The bridge's native code processes each channel's messages in a
 * dedicated per-channel queue, therefore each channel needs to be
 * registered within the native code.
 */
function registerChannel(channel) {
  channels[channel.name] = channel;
  NativeBridge.registerChannel(channel.name, bridgeListener);
}

const systemChannel = new SystemChannel(SYSTEM_CHANNEL);
registerChannel(systemChannel);

const eventChannel = new EventChannel(EVENT_CHANNEL);
registerChannel(eventChannel);

// Signal that the Node.js app is ready to receive events, so the native
// code won't send events before Node.js is ready to handle those.
NativeBridge.sendMessage(SYSTEM_CHANNEL, 'ready-for-app-events');

module.exports = exports = {
  app: systemChannel,
  channel: eventChannel,
};
