# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor NFC plugin.

## Versions

- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 7.x.x

### `NfcTag.id` property

On **iOS**, the `id` property of the `NfcTag` interface was returned reversed for ISO 15693 tags. This issue has been fixed in this version. The `id` property is now returned correctly and behaves the same way as on Android. More information can be found in the discussion [#200](https://github.com/capawesome-team/capacitor-plugins/discussions/200).

## Version 6.x.x

### Capacitor 6

This plugin now only supports Capacitor 6.

### `transceive(...)` method

The `techType` property of the `TransceiveOptions` interface is now only available for iOS. On Android, the `connect(...)` method must first be called to establish the connection to an NFC tag. The `transceive(...)` method can then be used to send and receive data. Finally, the `close(...)` method must be called to terminate the connection.

```typescript
import { Nfc, NfcTagTechType } from '@capawesome-team/capacitor-nfc';
import { Capacitor } from '@capacitor/core';

const readSignature = async () => {
  return new Promise((resolve) => {
    Nfc.addListener('nfcTagScanned', async (event) => {
      if (Capacitor.getPlatform() === 'android') {
        // 1. Connect to the tag.
        await Nfc.connect({ techType: NfcTagTechType.NfcA }); 
        // 2. Send one or more commands to the tag and receive the response.
        const result = await Nfc.transceive({ data: [60, 0] });
        // 3. Close the connection to the tag.
        await Nfc.close();
        await Nfc.stopScanSession();
        resolve(response);
      } else {
        // 1. Send one or more commands to the tag and receive the response.
        const result = await Nfc.transceive({ techType: NfcTagTechType.NfcA, data: [60, 0] });
        await Nfc.stopScanSession();
        resolve(response);
      }
    });

    Nfc.startScanSession();
  });
};
```
