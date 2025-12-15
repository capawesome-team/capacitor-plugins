# Testing

The following test cases should be covered when testing the plugin:

- [ ] Verify that the live update fails when the wrong public key is used.
- [ ] Verify that the live update fails when the checksum of the downloaded file does not match the expected checksum.
- [ ] Verify that a rollback is performed when the `ready()` method is not called within the `readyTimeout` period.
- [ ] Verify that no rollback is performed when the `ready()` method is called within the `readyTimeout` period.
- [ ] Verify that the live update is still applied after restarting the app.
- [ ] Verify that no error is thrown on internal server errors/not found responses.
- [ ] Verify that the correct timeout error is thrown when the server does not respond within the `timeout` period.
- [ ] Verify that the `background` auto update strategy performs updates in the background on app start and resumes.
- [ ] Verify that roll backed bundles are blocked from being applied again if `autoBlockRollBackedBundles` is set to true.