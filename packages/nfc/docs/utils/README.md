# Utils

## Usage

```ts
import { NfcUtils } from '@capawesome-team/capacitor-nfc';

const convertBytesToHex = () => {
  const utils = new NfcUtils();
  const { hex } = utils.convertBytesToHex({ bytes: [0, 1, 2], start: '0x', separator: '' });
  return hex;
};

const convertBytesToString = () => {
  const utils = new NfcUtils();
  const { text } = utils.convertBytesToString({ bytes: [72, 101, 108, 108, 111] });
  return text;
};

const convertHexToBytes = () => {
  const utils = new NfcUtils();
  const { bytes } = utils.convertHexToBytes({ hex: '0x000102' });
  return bytes;
};

const convertHexToNumber = () => {
  const utils = new NfcUtils();
  const { number } = utils.convertHexToNumber({ hex: '0x000102' });
  return number;
};

const convertNumberToHex = () => {
  const utils = new NfcUtils();
  const { hex } = utils.convertNumberToHex({ number: 258 });
  return hex;
};

const convertStringToBytes = () => {
  const utils = new NfcUtils();
  const { bytes } = utils.convertStringToBytes({ text: 'Hello' });
  return bytes;
};
```

## API

<docgen-index>

* [`convertBytesToHex(...)`](#convertbytestohex)
* [`convertBytesToString(...)`](#convertbytestostring)
* [`convertHexToBytes(...)`](#converthextobytes)
* [`convertHexToNumber(...)`](#converthextonumber)
* [`convertNumberToHex(...)`](#convertnumbertohex)
* [`convertStringToBytes(...)`](#convertstringtobytes)
* [`createNdefRecord(...)`](#createndefrecord)
* [`createNdefEmptyRecord()`](#createndefemptyrecord)
* [`createNdefTextRecord(...)`](#createndeftextrecord)
* [`createNdefUriRecord(...)`](#createndefurirecord)
* [`createNdefAbsoluteUriRecord(...)`](#createndefabsoluteurirecord)
* [`createNdefMimeMediaRecord(...)`](#createndefmimemediarecord)
* [`createNdefExternalRecord(...)`](#createndefexternalrecord)
* [`getIdentifierCodeFromNdefUriRecord(...)`](#getidentifiercodefromndefurirecord)
* [`getLanguageFromNdefTextRecord(...)`](#getlanguagefromndeftextrecord)
* [`getTextFromNdefTextRecord(...)`](#gettextfromndeftextrecord)
* [`getUriFromNdefUriRecord(...)`](#geturifromndefurirecord)
* [`mapBytesToRecordTypeDefinition(...)`](#mapbytestorecordtypedefinition)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### convertBytesToHex(...)

```typescript
convertBytesToHex(options: ConvertBytesToHexOptions) => { hex: string; }
```

Convert a byte array to a hex string.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#convertbytestohexoptions">ConvertBytesToHexOptions</a></code> |

**Returns:** <code>{ hex: string; }</code>

**Since:** 0.3.1

--------------------


### convertBytesToString(...)

```typescript
convertBytesToString(options: ConvertBytesToStringOptions) => { text: string; }
```

Convert a byte array to a string.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#convertbytestostringoptions">ConvertBytesToStringOptions</a></code> |

**Returns:** <code>{ text: string; }</code>

**Since:** 0.0.1

--------------------


### convertHexToBytes(...)

```typescript
convertHexToBytes(options: ConvertHexToBytesOptions) => { bytes: number[]; }
```

Convert a hex string to a byte array.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#converthextobytesoptions">ConvertHexToBytesOptions</a></code> |

**Returns:** <code>{ bytes: number[]; }</code>

**Since:** 0.3.1

--------------------


### convertHexToNumber(...)

```typescript
convertHexToNumber(options: ConvertHexToNumberOptions) => { number: number; }
```

Convert a hex string to a number.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#converthextonumberoptions">ConvertHexToNumberOptions</a></code> |

**Returns:** <code>{ number: number; }</code>

**Since:** 0.3.1

--------------------


### convertNumberToHex(...)

```typescript
convertNumberToHex(options: ConvertNumberToHexOptions) => { hex: string; }
```

Convert a number to a hex string.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#convertnumbertohexoptions">ConvertNumberToHexOptions</a></code> |

**Returns:** <code>{ hex: string; }</code>

**Since:** 0.3.1

--------------------


### convertStringToBytes(...)

```typescript
convertStringToBytes(options: ConvertStringToBytesOptions) => { bytes: number[]; }
```

Convert a string to a byte array.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#convertstringtobytesoptions">ConvertStringToBytesOptions</a></code> |

**Returns:** <code>{ bytes: number[]; }</code>

**Since:** 0.0.1

--------------------


### createNdefRecord(...)

```typescript
createNdefRecord(options: CreateNdefRecordOptions) => CreateNdefRecordResult
```

Create a NDEF record.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndefrecordoptions">CreateNdefRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefEmptyRecord()

```typescript
createNdefEmptyRecord() => CreateNdefRecordResult
```

Create an empty NDEF record.

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefTextRecord(...)

```typescript
createNdefTextRecord(options: CreateNdefTextRecordOptions) => CreateNdefRecordResult
```

Create a NDEF text record.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndeftextrecordoptions">CreateNdefTextRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefUriRecord(...)

```typescript
createNdefUriRecord(options: CreateNdefUriRecordOptions) => CreateNdefRecordResult
```

Create a NDEF URI record.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndefurirecordoptions">CreateNdefUriRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefAbsoluteUriRecord(...)

```typescript
createNdefAbsoluteUriRecord(options: CreateNdefAbsoluteUriRecordOptions) => CreateNdefRecordResult
```

Create a NDEF absolute URI record.

| Param         | Type                                                                                              |
| ------------- | ------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndefabsoluteurirecordoptions">CreateNdefAbsoluteUriRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefMimeMediaRecord(...)

```typescript
createNdefMimeMediaRecord(options: CreateNdefMimeMediaRecordOptions) => CreateNdefRecordResult
```

Create a NDEF mime media record.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndefmimemediarecordoptions">CreateNdefMimeMediaRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### createNdefExternalRecord(...)

```typescript
createNdefExternalRecord(options: CreateNdefExternalRecordOptions) => CreateNdefRecordResult
```

Create a NDEF external type record.

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#createndefexternalrecordoptions">CreateNdefExternalRecordOptions</a></code> |

**Returns:** <code><a href="#createndefrecordresult">CreateNdefRecordResult</a></code>

**Since:** 0.0.1

--------------------


### getIdentifierCodeFromNdefUriRecord(...)

```typescript
getIdentifierCodeFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions) => { identifierCode: UriIdentifierCode | undefined; }
```

Get the identifier code from a NDEF URI record.

This method assumes that the record has a valid URI identifier code.

| Param         | Type                                                                                                            |
| ------------- | --------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getidentifiercodefromndefurirecordoptions">GetIdentifierCodeFromNdefUriRecordOptions</a></code> |

**Returns:** <code>{ identifierCode: <a href="#uriidentifiercode">UriIdentifierCode</a>; }</code>

**Since:** 0.3.1

--------------------


### getLanguageFromNdefTextRecord(...)

```typescript
getLanguageFromNdefTextRecord(options: GetLanguageFromNdefTextRecordOptions) => { language: string | undefined; }
```

Get the language code from a NDEF text record.

| Param         | Type                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getlanguagefromndeftextrecordoptions">GetLanguageFromNdefTextRecordOptions</a></code> |

**Returns:** <code>{ language: string; }</code>

**Since:** 0.0.1

--------------------


### getTextFromNdefTextRecord(...)

```typescript
getTextFromNdefTextRecord(options: GetTextFromNdefTextRecordOptions) => { text: string | undefined; }
```

Get the text from a NDEF text record.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#gettextfromndeftextrecordoptions">GetTextFromNdefTextRecordOptions</a></code> |

**Returns:** <code>{ text: string; }</code>

**Since:** 0.0.1

--------------------


### getUriFromNdefUriRecord(...)

```typescript
getUriFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions) => { uri: string | undefined; }
```

Get the uri from a NDEF URI record.

This method assumes that the record has a valid URI identifier code.

| Param         | Type                                                                                                            |
| ------------- | --------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getidentifiercodefromndefurirecordoptions">GetIdentifierCodeFromNdefUriRecordOptions</a></code> |

**Returns:** <code>{ uri: string; }</code>

**Since:** 0.3.1

--------------------


### mapBytesToRecordTypeDefinition(...)

```typescript
mapBytesToRecordTypeDefinition(options: { bytes: number[]; }) => { type: RecordTypeDefinition | undefined; }
```

Map a byte array to a the corresponding NDEF record type.

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ bytes: number[]; }</code> |

**Returns:** <code>{ type: <a href="#recordtypedefinition">RecordTypeDefinition</a>; }</code>

**Since:** 0.0.1

--------------------


### Interfaces


#### ConvertBytesToHexOptions

| Prop            | Type                  | Description                                | Default           | Since |
| --------------- | --------------------- | ------------------------------------------ | ----------------- | ----- |
| **`bytes`**     | <code>number[]</code> | The byte array to convert to a hex string. |                   | 0.3.1 |
| **`start`**     | <code>string</code>   | The text to prepend to the hex string.     | <code>'0x'</code> | 0.3.1 |
| **`separator`** | <code>string</code>   | The separator to use between each byte.    | <code>''</code>   | 0.3.1 |


#### ConvertBytesToStringOptions

| Prop        | Type                  | Description                            | Since |
| ----------- | --------------------- | -------------------------------------- | ----- |
| **`bytes`** | <code>number[]</code> | The byte array to convert to a string. | 0.0.1 |


#### ConvertHexToBytesOptions

| Prop            | Type                | Description                                              | Default           | Since |
| --------------- | ------------------- | -------------------------------------------------------- | ----------------- | ----- |
| **`hex`**       | <code>string</code> | The hex string to convert to a byte array.               |                   | 0.3.1 |
| **`start`**     | <code>string</code> | The text to remove from the beginning of the hex string. | <code>'0x'</code> | 0.3.1 |
| **`separator`** | <code>string</code> | The separator which is used between each byte.           | <code>''</code>   | 0.3.1 |


#### ConvertHexToNumberOptions

| Prop      | Type                | Description                                  | Since |
| --------- | ------------------- | -------------------------------------------- | ----- |
| **`hex`** | <code>string</code> | The hex string to convert to a number array. | 0.3.1 |


#### ConvertNumberToHexOptions

| Prop         | Type                | Description                            | Since |
| ------------ | ------------------- | -------------------------------------- | ----- |
| **`number`** | <code>number</code> | The number to convert to a hex string. | 0.3.1 |


#### ConvertStringToBytesOptions

| Prop       | Type                | Description                            | Since |
| ---------- | ------------------- | -------------------------------------- | ----- |
| **`text`** | <code>string</code> | The string to convert to a byte array. | 0.0.1 |


#### CreateNdefRecordResult

| Prop         | Type                                              | Since |
| ------------ | ------------------------------------------------- | ----- |
| **`record`** | <code><a href="#ndefrecord">NdefRecord</a></code> | 0.0.1 |


#### NdefRecord

| Prop          | Type                                                      | Description                                                                                                              | Since |
| ------------- | --------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`id`**      | <code>number[]</code>                                     | The record identifier as byte array.                                                                                     | 0.0.1 |
| **`payload`** | <code>number[]</code>                                     | The payload field data as byte array.                                                                                    | 0.0.1 |
| **`tnf`**     | <code><a href="#typenameformat">TypeNameFormat</a></code> | The record type name format which indicates the structure of the value of the `type` field.                              | 0.0.1 |
| **`type`**    | <code>number[]</code>                                     | The type of the record payload. This should be used in conjunction with the `tnf` field to determine the payload format. | 0.0.1 |


#### CreateNdefRecordOptions

| Prop          | Type                                                      | Since |
| ------------- | --------------------------------------------------------- | ----- |
| **`id`**      | <code>string \| number[]</code>                           | 0.0.1 |
| **`payload`** | <code>string \| number[]</code>                           | 0.0.1 |
| **`tnf`**     | <code><a href="#typenameformat">TypeNameFormat</a></code> | 0.0.1 |
| **`type`**    | <code>string \| number[]</code>                           | 0.0.1 |


#### CreateNdefTextRecordOptions

| Prop           | Type                            | Description                       | Default         | Since |
| -------------- | ------------------------------- | --------------------------------- | --------------- | ----- |
| **`id`**       | <code>string \| number[]</code> |                                   |                 | 0.0.1 |
| **`text`**     | <code>string \| number[]</code> |                                   |                 | 0.0.1 |
| **`language`** | <code>string \| number[]</code> | The ISO/IANA language identifier. | <code>en</code> | 0.0.1 |


#### CreateNdefUriRecordOptions

| Prop                 | Type                            | Description              | Default                             | Since |
| -------------------- | ------------------------------- | ------------------------ | ----------------------------------- | ----- |
| **`id`**             | <code>string \| number[]</code> |                          |                                     | 0.0.1 |
| **`uri`**            | <code>string \| number[]</code> |                          |                                     | 0.0.1 |
| **`identifierCode`** | <code>number</code>             | The URI identifier code. | <code>UriIdentifierCode.None</code> | 0.3.1 |


#### CreateNdefAbsoluteUriRecordOptions

| Prop      | Type                            | Since |
| --------- | ------------------------------- | ----- |
| **`id`**  | <code>string \| number[]</code> | 0.0.1 |
| **`uri`** | <code>string \| number[]</code> | 0.0.1 |


#### CreateNdefMimeMediaRecordOptions

| Prop           | Type                            | Description                       | Since |
| -------------- | ------------------------------- | --------------------------------- | ----- |
| **`id`**       | <code>string \| number[]</code> |                                   | 0.0.1 |
| **`mimeType`** | <code>string \| number[]</code> | A valid MIME type.                | 0.0.1 |
| **`mimeData`** | <code>string \| number[]</code> | The MIME data as bytes or string. | 0.0.1 |


#### CreateNdefExternalRecordOptions

| Prop          | Type                            | Description                              | Since |
| ------------- | ------------------------------- | ---------------------------------------- | ----- |
| **`id`**      | <code>string \| number[]</code> |                                          | 0.0.1 |
| **`payload`** | <code>string \| number[]</code> |                                          | 0.0.1 |
| **`domain`**  | <code>string \| number[]</code> | The domain-name of issuing organization. | 0.0.1 |
| **`type`**    | <code>string \| number[]</code> | The domain-specific type of data.        | 0.0.1 |


#### GetIdentifierCodeFromNdefUriRecordOptions

| Prop         | Type                                              | Since |
| ------------ | ------------------------------------------------- | ----- |
| **`record`** | <code><a href="#ndefrecord">NdefRecord</a></code> | 0.3.1 |


#### GetLanguageFromNdefTextRecordOptions

| Prop         | Type                                              | Since |
| ------------ | ------------------------------------------------- | ----- |
| **`record`** | <code><a href="#ndefrecord">NdefRecord</a></code> | 0.0.1 |


#### GetTextFromNdefTextRecordOptions

| Prop         | Type                                              | Since |
| ------------ | ------------------------------------------------- | ----- |
| **`record`** | <code><a href="#ndefrecord">NdefRecord</a></code> | 0.0.1 |


### Enums


#### TypeNameFormat

| Members           | Value          | Description                                                                                            | Since |
| ----------------- | -------------- | ------------------------------------------------------------------------------------------------------ | ----- |
| **`Empty`**       | <code>0</code> | An empty record with no type or payload.                                                               | 0.0.1 |
| **`WellKnown`**   | <code>1</code> | A predefined type defined in the RTD specification of the NFC Forum.                                   | 0.0.1 |
| **`MimeMedia`**   | <code>2</code> | An Internet media type as defined in RFC 2046.                                                         | 0.0.1 |
| **`AbsoluteUri`** | <code>3</code> | A URI as defined in RFC 3986.                                                                          | 0.0.1 |
| **`External`**    | <code>4</code> | A user-defined value that is based on the rules of the NFC Forum Record Type Definition specification. | 0.0.1 |
| **`Unknown`**     | <code>5</code> | Type is unknown.                                                                                       | 0.0.1 |
| **`Unchanged`**   | <code>6</code> | Indicates the payload is an intermediate or final chunk of a chunked NDEF Record.                      | 0.0.1 |


#### RecordTypeDefinition

| Members                  | Value                          | Since |
| ------------------------ | ------------------------------ | ----- |
| **`AndroidApp`**         | <code>'android.com:pkg'</code> | 0.0.1 |
| **`AlternativeCarrier`** | <code>'ac'</code>              | 0.0.1 |
| **`HandoverCarrier`**    | <code>'Hc'</code>              | 0.0.1 |
| **`HandoverRequest`**    | <code>'Hr'</code>              | 0.0.1 |
| **`HandoverSelect`**     | <code>'Hs'</code>              | 0.0.1 |
| **`SmartPoster`**        | <code>'Sp'</code>              | 0.0.1 |
| **`Text`**               | <code>'T'</code>               | 0.0.1 |
| **`Uri`**                | <code>'U'</code>               | 0.0.1 |


#### UriIdentifierCode

| Members            | Value           | Description                                           | Since |
| ------------------ | --------------- | ----------------------------------------------------- | ----- |
| **`None`**         | <code>0</code>  | No prepending is done.                                | 0.3.1 |
| **`HttpWww`**      | <code>1</code>  | `http://www.` is prepended to the URI.                | 0.3.1 |
| **`HttpsWww`**     | <code>2</code>  | `https://www.` is prepended to the URI.               | 0.3.1 |
| **`Http`**         | <code>3</code>  | `http:` is prepended to the URI.                      | 0.3.1 |
| **`Https`**        | <code>4</code>  | `https:` is prepended to the URI.                     | 0.3.1 |
| **`Tel`**          | <code>5</code>  | `tel:` is prepended to the URI.                       | 0.3.1 |
| **`Mailto`**       | <code>6</code>  | `mailto:` is prepended to the URI.                    | 0.3.1 |
| **`FtpAnonymous`** | <code>7</code>  | `ftp://anonymous:anonymous@` is prepended to the URI. | 0.3.1 |
| **`FtpFtp`**       | <code>8</code>  | `ftp://ftp.` is prepended to the URI.                 | 0.3.1 |
| **`Ftps`**         | <code>9</code>  | `ftps://` is prepended to the URI.                    | 0.3.1 |
| **`Sftp`**         | <code>10</code> | `sftp://` is prepended to the URI.                    | 0.3.1 |
| **`Smb`**          | <code>11</code> | `smb://` is prepended to the URI.                     | 0.3.1 |
| **`Nfs`**          | <code>12</code> | `nfs://` is prepended to the URI.                     | 0.3.1 |
| **`Ftp`**          | <code>13</code> | `ftp://` is prepended to the URI.                     | 0.3.1 |
| **`Dav`**          | <code>14</code> | `dav://` is prepended to the URI.                     | 0.3.1 |
| **`News`**         | <code>15</code> | `news:` is prepended to the URI.                      | 0.3.1 |
| **`Telnet`**       | <code>16</code> | `telnet://` is prepended to the URI.                  | 0.3.1 |
| **`Imap`**         | <code>17</code> | `imap:` is prepended to the URI.                      | 0.3.1 |
| **`Rtsp`**         | <code>18</code> | `rtsp://` is prepended to the URI.                    | 0.3.1 |
| **`Urn`**          | <code>19</code> | `urn:` is prepended to the URI.                       | 0.3.1 |
| **`Pop`**          | <code>20</code> | `pop:` is prepended to the URI.                       | 0.3.1 |
| **`Sip`**          | <code>21</code> | `sip:` is prepended to the URI.                       | 0.3.1 |
| **`Sips`**         | <code>22</code> | `sips:` is prepended to the URI.                      | 0.3.1 |
| **`Tftp`**         | <code>23</code> | `tftp:` is prepended to the URI.                      | 0.3.1 |
| **`Btspp`**        | <code>24</code> | `btspp://` is prepended to the URI.                   | 0.3.1 |
| **`Btl2cap`**      | <code>25</code> | `btl2cap://` is prepended to the URI.                 | 0.3.1 |
| **`Btgoep`**       | <code>26</code> | `btgoep://` is prepended to the URI.                  | 0.3.1 |
| **`Tcpobex`**      | <code>27</code> | `tcpobex://` is prepended to the URI.                 | 0.3.1 |
| **`Irdaobex`**     | <code>28</code> | `irdaobex://` is prepended to the URI.                | 0.3.1 |
| **`File`**         | <code>29</code> | `file://` is prepended to the URI.                    | 0.3.1 |
| **`UrnEpcId`**     | <code>30</code> | `urn:epc:id:` is prepended to the URI.                | 0.3.1 |
| **`UrnEpcTag`**    | <code>31</code> | `urn:epc:tag:` is prepended to the URI.               | 0.3.1 |
| **`UrnEpcPat`**    | <code>32</code> | `urn:epc:pat:` is prepended to the URI.               | 0.3.1 |
| **`UrnEpcRaw`**    | <code>33</code> | `urn:epc:raw:` is prepended to the URI.               | 0.3.1 |
| **`UrnEpc`**       | <code>34</code> | `urn:epc:` is prepended to the URI.                   | 0.3.1 |
| **`UrnNfc`**       | <code>35</code> | `urn:nfc:` is prepended to the URI.                   | 0.3.1 |

</docgen-api>
