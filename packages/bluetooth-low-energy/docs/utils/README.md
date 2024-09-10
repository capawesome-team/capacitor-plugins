# Utils

## API

<docgen-index>

* [`convertBytesToHex(...)`](#convertbytestohex)
* [`convertHexToBytes(...)`](#converthextobytes)
* [Interfaces](#interfaces)

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

**Since:** 6.0.0

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

**Since:** 6.0.0

--------------------


### Interfaces


#### ConvertBytesToHexOptions

| Prop            | Type                  | Description                                | Default           | Since |
| --------------- | --------------------- | ------------------------------------------ | ----------------- | ----- |
| **`bytes`**     | <code>number[]</code> | The byte array to convert to a hex string. |                   | 6.0.0 |
| **`start`**     | <code>string</code>   | The text to prepend to the hex string.     | <code>'0x'</code> | 6.0.0 |
| **`separator`** | <code>string</code>   | The separator to use between each byte.    | <code>''</code>   | 6.0.0 |


#### ConvertHexToBytesOptions

| Prop            | Type                | Description                                              | Default           | Since |
| --------------- | ------------------- | -------------------------------------------------------- | ----------------- | ----- |
| **`hex`**       | <code>string</code> | The hex string to convert to a byte array.               |                   | 6.0.0 |
| **`start`**     | <code>string</code> | The text to remove from the beginning of the hex string. | <code>'0x'</code> | 6.0.0 |
| **`separator`** | <code>string</code> | The separator which is used between each byte.           | <code>''</code>   | 6.0.0 |

</docgen-api>
