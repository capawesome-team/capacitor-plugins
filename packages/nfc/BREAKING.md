# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor NFC plugin.

## Versions

- [Version 6.x.x](#version-6xx)

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
