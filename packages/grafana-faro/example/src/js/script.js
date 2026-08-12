import { GrafanaFaro } from '@capawesome/capacitor-grafana-faro';

window.initialize = async () => {
  await GrafanaFaro.initialize({
    app: {
      environment: 'development',
      name: 'capacitor-grafana-faro-example',
      version: '0.0.1',
    },
    instrumentations: {
      anrTracking: true,
      console: true,
      errors: true,
      nativeCrashReporting: true,
      performance: true,
      view: true,
      webVitals: true,
    },
    url: 'https://faro-collector-prod-us-central-0.grafana.net/collect/REPLACE_ME',
  });
  console.log('GrafanaFaro initialized');
};

window.pushLog = async () => {
  await GrafanaFaro.pushLog({
    context: { source: 'example-app' },
    level: 'info',
    message: 'Hello from the example app',
  });
};

window.pushEvent = async () => {
  await GrafanaFaro.pushEvent({
    attributes: { button: 'push-event' },
    name: 'example_button_pressed',
  });
};

window.pushError = async () => {
  await GrafanaFaro.pushError({
    context: { source: 'example-app' },
    fatal: false,
    type: 'ExampleError',
    value: 'Manually-pushed example error',
  });
};

window.pushErrorFromCaught = async () => {
  try {
    JSON.parse('{invalid');
  } catch (error) {
    await GrafanaFaro.pushError({
      type: error.name,
      value: error.message,
    });
  }
};

window.pushMeasurement = async () => {
  await GrafanaFaro.pushMeasurement({
    type: 'example_latency',
    values: { duration_ms: 123 },
  });
};

window.setUser = async () => {
  await GrafanaFaro.setUser({
    email: 'jane@example.com',
    id: 'user-123',
    username: 'jane',
  });
};

window.resetUser = async () => {
  await GrafanaFaro.resetUser();
};

window.setSession = async () => {
  await GrafanaFaro.setSession({
    attributes: { plan: 'pro' },
  });
};

window.resetSession = async () => {
  await GrafanaFaro.resetSession();
};

window.getSession = async () => {
  console.log('session:', await GrafanaFaro.getSession());
};

window.setView = async () => {
  await GrafanaFaro.setView({ name: 'ExampleView' });
};

window.getView = async () => {
  console.log('view:', await GrafanaFaro.getView());
};

window.pause = async () => {
  await GrafanaFaro.pause();
};

window.unpause = async () => {
  await GrafanaFaro.unpause();
};
