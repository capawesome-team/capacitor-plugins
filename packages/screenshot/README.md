# @capawesome/capacitor-screenshot

Capacitor plugin for taking screenshots.

## Install

```bash
npm install @capawesome/capacitor-screenshot
npx cap sync
```

## API

<docgen-index>

* [`take()`](#take)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### take()

```typescript
take() => Promise<TakeResult>
```

Take a screenshot.

**Returns:** <code>Promise&lt;<a href="#takeresult">TakeResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### Interfaces


#### TakeResult

| Prop      | Type                | Description                                                                                                  | Since |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------------ | ----- |
| **`uri`** | <code>string</code> | The file path (Android and iOS) or data URI (Web) of the screenshot. Only available on Android, iOS and Web. | 6.0.0 |

</docgen-api>
