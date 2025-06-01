# camera-multi-capture

Capacitor plugin that lets the user Capture multiple photos with a customizable UI overlay in a single camera session

## Install

```bash
npm install camera-multi-capture
npx cap sync
```

## API

<docgen-index>

* [`start(...)`](#start)
* [`capture()`](#capture)
* [`stop()`](#stop)
* [`switchCamera()`](#switchcamera)
* [`setZoom(...)`](#setzoom)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### start(...)

```typescript
start(options?: CameraOverlayOptions | undefined) => Promise<CameraOverlayResult>
```

Starts the camera overlay session.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#cameraoverlayoptions">CameraOverlayOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#cameraoverlayresult">CameraOverlayResult</a>&gt;</code>

--------------------


### capture()

```typescript
capture() => Promise<{ value: CameraImageData; }>
```

Captures a single frame.

**Returns:** <code>Promise&lt;{ value: <a href="#cameraimagedata">CameraImageData</a>; }&gt;</code>

--------------------


### stop()

```typescript
stop() => Promise<void>
```

Stops and tears down the camera session.

--------------------


### switchCamera()

```typescript
switchCamera() => Promise<void>
```

Switches the camera between front and back.

--------------------


### setZoom(...)

```typescript
setZoom(options: { zoom: number; }) => Promise<void>
```

Sets the zoom level of the camera.

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ zoom: number; }</code> |

--------------------


### Interfaces


#### CameraOverlayResult

| Prop            | Type                           |
| --------------- | ------------------------------ |
| **`images`**    | <code>CameraImageData[]</code> |
| **`cancelled`** | <code>boolean</code>           |


#### CameraImageData

Structure for image data returned by the camera

| Prop         | Type                |
| ------------ | ------------------- |
| **`uri`**    | <code>string</code> |
| **`base64`** | <code>string</code> |


#### CameraOverlayOptions

| Prop                 | Type                                                                  |
| -------------------- | --------------------------------------------------------------------- |
| **`buttons`**        | <code><a href="#cameraoverlaybuttons">CameraOverlayButtons</a></code> |
| **`thumbnailStyle`** | <code>{ width: string; height: string; }</code>                       |
| **`quality`**        | <code>number</code>                                                   |
| **`containerId`**    | <code>string</code>                                                   |
| **`previewRect`**    | <code><a href="#camerapreviewrect">CameraPreviewRect</a></code>       |
| **`direction`**      | <code><a href="#cameradirection">CameraDirection</a></code>           |
| **`captureMode`**    | <code><a href="#capturemode">CaptureMode</a></code>                   |
| **`resolution`**     | <code><a href="#resolution">Resolution</a></code>                     |
| **`zoom`**           | <code>number</code>                                                   |
| **`autoFocus`**      | <code>boolean</code>                                                  |


#### CameraOverlayButtons

| Prop               | Type                                                                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| **`capture`**      | <code>{ icon?: string; style?: <a href="#buttonstyle">ButtonStyle</a>; position?: 'center' \| 'left' \| 'right' \| 'custom'; }</code> |
| **`done`**         | <code>{ icon?: string; style?: <a href="#buttonstyle">ButtonStyle</a>; text?: string; }</code>                                        |
| **`cancel`**       | <code>{ icon?: string; style?: <a href="#buttonstyle">ButtonStyle</a>; text?: string; }</code>                                        |
| **`switchCamera`** | <code>{ icon?: string; style?: <a href="#buttonstyle">ButtonStyle</a>; position?: 'custom' \| 'topLeft' \| 'topRight'; }</code>       |
| **`zoom`**         | <code>{ icon?: string; style?: <a href="#buttonstyle">ButtonStyle</a>; levels?: number[]; }</code>                                    |


#### ButtonStyle

| Prop                  | Type                |
| --------------------- | ------------------- |
| **`radius`**          | <code>number</code> |
| **`color`**           | <code>string</code> |
| **`backgroundColor`** | <code>string</code> |
| **`padding`**         | <code>string</code> |
| **`size`**            | <code>number</code> |
| **`activeColor`**     | <code>string</code> |
| **`border`**          | <code>string</code> |


#### CameraPreviewRect

| Prop         | Type                |
| ------------ | ------------------- |
| **`width`**  | <code>number</code> |
| **`height`** | <code>number</code> |
| **`x`**      | <code>number</code> |
| **`y`**      | <code>number</code> |


#### Resolution

| Prop         | Type                |
| ------------ | ------------------- |
| **`width`**  | <code>number</code> |
| **`height`** | <code>number</code> |


### Type Aliases


#### ButtonStyle

Defines the style properties for camera buttons

<code>OriginalButtonStyle</code>


#### CameraDirection

<code>'front' | 'back'</code>


#### CaptureMode

<code>'minimizeLatency' | 'maxQuality'</code>

</docgen-api>
