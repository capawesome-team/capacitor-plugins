import { Posthog } from '@capawesome/capacitor-posthog';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#setup-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.setup({
          apiKey: 'phc_1234567890abcdef1234567890abcdef1234567890',
          host: 'https://us.i.posthog.com',
        });
        console.log('PostHog setup complete');
      } catch (error) {
        console.error('Setup error:', error);
      }
    });

  document
    .querySelector('#alias-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.alias({ alias: 'user_alias_456' });
        console.log('Alias set: user_alias_456');
      } catch (error) {
        console.error('Alias error:', error);
      }
    });

  document
    .querySelector('#capture-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.capture({
          event: 'button_clicked',
          properties: {
            button_name: 'submit_button',
            screen: 'home',
          },
        });
        console.log('Event captured: button_clicked');
      } catch (error) {
        console.error('Capture error:', error);
      }
    });

  document
    .querySelector('#flush-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.flush();
        console.log('Events flushed');
      } catch (error) {
        console.error('Flush error:', error);
      }
    });

  document
    .querySelector('#get-feature-flag-button')
    .addEventListener('click', async () => {
      try {
        const result = await Posthog.getFeatureFlag({ key: 'new_feature' });
        console.log('Feature flag value:', result);
      } catch (error) {
        console.error('Get feature flag error:', error);
      }
    });

  document
    .querySelector('#get-feature-flag-payload-button')
    .addEventListener('click', async () => {
      try {
        const result = await Posthog.getFeatureFlagPayload({
          key: 'new_feature',
        });
        console.log('Feature flag payload:', result);
      } catch (error) {
        console.error('Get feature flag payload error:', error);
      }
    });

  document
    .querySelector('#group-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.group({
          type: 'company',
          key: 'company_id_123',
          groupProperties: {
            name: 'Acme Inc',
            plan: 'enterprise',
          },
        });
        console.log('Group set: company/company_id_123');
      } catch (error) {
        console.error('Group error:', error);
      }
    });

  document
    .querySelector('#identify-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.identify({
          distinctId: 'user123',
          userProperties: {
            email: 'user@example.com',
            name: 'John Doe',
          },
        });
        console.log('User identified: user123');
      } catch (error) {
        console.error('Identify error:', error);
      }
    });

  document
    .querySelector('#is-feature-enabled-button')
    .addEventListener('click', async () => {
      try {
        const result = await Posthog.isFeatureEnabled({ key: 'new_feature' });
        console.log('Is feature enabled:', result);
      } catch (error) {
        console.error('Is feature enabled error:', error);
      }
    });

  document
    .querySelector('#register-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.register({ key: 'app_version', value: '1.0.0' });
        console.log('Super property registered: app_version=1.0.0');
      } catch (error) {
        console.error('Register error:', error);
      }
    });

  document
    .querySelector('#reload-feature-flags-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.reloadFeatureFlags();
        console.log('Feature flags reloaded');
      } catch (error) {
        console.error('Reload feature flags error:', error);
      }
    });

  document
    .querySelector('#reset-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.reset();
        console.log('PostHog reset');
      } catch (error) {
        console.error('Reset error:', error);
      }
    });

  document
    .querySelector('#screen-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.screen({
          screenTitle: 'Home Screen',
          properties: {
            referrer: 'onboarding',
          },
        });
        console.log('Screen event sent: Home Screen');
      } catch (error) {
        console.error('Screen error:', error);
      }
    });

  document
    .querySelector('#start-session-recording-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.startSessionRecording();
        console.log('Session recording started');
      } catch (error) {
        console.error('Start session recording error:', error);
      }
    });

  document
    .querySelector('#stop-session-recording-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.stopSessionRecording();
        console.log('Session recording stopped');
      } catch (error) {
        console.error('Stop session recording error:', error);
      }
    });

  document
    .querySelector('#unregister-button')
    .addEventListener('click', async () => {
      try {
        await Posthog.unregister({ key: 'app_version' });
        console.log('Super property unregistered: app_version');
      } catch (error) {
        console.error('Unregister error:', error);
      }
    });
});
