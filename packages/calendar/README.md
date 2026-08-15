# Capacitor Calendar Plugin

Capacitor plugin to manage calendars and events on Android and iOS. Create, read, update and delete calendars and events, work with recurring events, present the system event dialogs, and listen for calendar changes.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Calendar plugin gives your app full access to the calendars and events on the device. Here are some of the key features:

- 📅 **Calendars**: Create, delete and retrieve the calendars on the device, including the default calendar for new events.
- 🗓️ **Events**: Create, read, update and delete events, and query all events in a time range with a single call.
- 🔁 **Recurring Events**: Create recurring events with a readable recurrence rule — and read that rule back, instead of only being able to write it.
- 🎯 **Single Occurrences**: Update or delete a single occurrence of a recurring event, or the occurrence and all future ones.
- 📱 **System Dialogs**: Let the user create or edit an event in the system dialog, prefilled with your event data.
- 🔔 **Change Listener**: Get notified when calendars or events change, including changes made by other apps.
- 🔒 **Granular Permissions**: Separate read and write permissions, including write-only calendar access on iOS 17 and newer.
- ⚠️ **Error Codes**: Every runtime failure rejects with a documented error code, so you can branch on it instead of parsing messages.
- 🌍 **All-Day & Time Zones**: A documented all-day and time zone contract that behaves identically on both platforms — no off-by-one-day surprises.
- 🤝 **Compatibility**: Works hand in hand with the [Contacts](https://capawesome.io/docs/sdks/capacitor/contacts/) and [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Calendar plugin is typically used whenever an app needs to read from or write to the calendars on the device, for example:

- **Booking and appointment apps**: Write a confirmed booking straight into the user's calendar, including an alert before the appointment, and update or remove it when the booking changes.
- **Field service and scheduling**: Show the agenda of the device next to your own schedule so that technicians and sales reps see conflicts before they accept a job.
- **Fitness and course apps**: Add recurring training sessions or course dates as a single recurring event, and let the user skip a single session without losing the series.
- **Reminders before appointments**: Attach alerts to an event so that the operating system reminds the user, even when your app is not running.
- **Calendar integrations**: Keep events in sync with your backend and react to changes that the user made in the calendar app.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-calendar` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-calendar
npx cap sync
```

### Android

#### Permissions

This API requires the following elements be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Required if you want to read calendars and events, for example with `getCalendars()` or `getEvents(...)`. -->
<uses-permission android:name="android.permission.READ_CALENDAR" />
<!-- Required if you want to create, update or delete calendars and events, for example with `createEvent(...)`. -->
<uses-permission android:name="android.permission.WRITE_CALENDAR" />
```

Only declare the permissions that your app actually needs. Keep in mind that `createEvent(...)`, `updateEventById(...)` and `deleteEventById(...)` require the `READ_CALENDAR` permission in addition to the `WRITE_CALENDAR` permission, because they have to look up the calendar or event first. Only `createCalendar(...)` and `deleteCalendarById(...)` work with the `WRITE_CALENDAR` permission alone.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the following keys to the `ios/App/App/Info.plist` file, which tell the user why your app needs access to the calendars:

```xml
<!-- Required on iOS 17 and newer if your app reads or modifies calendars or events. -->
<key>NSCalendarsFullAccessUsageDescription</key>
<string>The app needs access to your calendars to display and manage your events.</string>
<!-- Required on iOS 17 and newer if your app only requests the `writeCalendar` permission. -->
<key>NSCalendarsWriteOnlyAccessUsageDescription</key>
<string>The app needs access to your calendars to add events for your bookings.</string>
<!-- Required on iOS 16 and older. -->
<key>NSCalendarsUsageDescription</key>
<string>The app needs access to your calendars to display and manage your events.</string>
```

Which keys you need depends on the access that your app requests:

- `NSCalendarsFullAccessUsageDescription` is required on **iOS 17 and newer** whenever the `readCalendar` permission is requested and by every method that reads or modifies calendars or events. Modifying requires full access as well, because the plugin has to look up the calendar or event first.
- `NSCalendarsWriteOnlyAccessUsageDescription` is only required on **iOS 17 and newer** if `requestPermissions(...)` is called with only the `writeCalendar` permission. Write-only access lets your app add events without seeing the events of the user, but is not sufficient for the methods of this plugin.
- `NSCalendarsUsageDescription` is required on **iOS 16 and older**, which does not distinguish between read and write access.

If a required key is missing, `requestPermissions(...)` rejects with a clear error message.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to request permissions, work with calendars and events, create and modify recurring events, present the system event dialogs, and listen for calendar changes.

### Request permissions

Request read and write access to the calendars of the device. Pass the `permissions` option to request only a subset. On iOS 17 and newer, requesting only the `writeCalendar` permission requests write-only access, which does not give your app access to the existing events of the user. Methods such as `createEvent(...)` request full access when they are called, because they have to look up the calendar or event first:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const requestPermissions = async () => {
  const { readCalendar, writeCalendar } = await Calendar.requestPermissions();
  return readCalendar === 'granted' && writeCalendar === 'granted';
};

const requestWriteOnlyPermission = async () => {
  const { writeCalendar } = await Calendar.requestPermissions({
    permissions: ['writeCalendar'],
  });
  return writeCalendar === 'granted';
};
```

### Get the calendars

Retrieve all calendars on the device with `getCalendars()`, or only the calendar that the system uses for new events with `getDefaultCalendar()`. Use the `writable` property to filter out calendars that your app cannot write to, for example subscribed holiday calendars:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const getWritableCalendars = async () => {
  const { calendars } = await Calendar.getCalendars();
  return calendars.filter(calendar => calendar.writable);
};

const getDefaultCalendar = async () => {
  const { calendar } = await Calendar.getDefaultCalendar();
  return calendar;
};
```

### Create an event

Create an event with `createEvent(...)`. Only `title` and `startDate` are required. If no `calendarId` is provided, the event is created in the default calendar. The `alerts` are offsets in minutes before the start of the event:

```typescript
import { Calendar, EventAvailability } from '@capawesome-team/capacitor-calendar';

const createEvent = async (calendarId: string) => {
  const startDate = new Date('2026-09-01T10:00:00').getTime();
  const { id } = await Calendar.createEvent({
    event: {
      calendarId,
      title: 'Dentist appointment',
      startDate,
      endDate: startDate + 60 * 60 * 1000,
      location: 'Main Street 1, Springfield',
      description: 'Bring the insurance card.',
      availability: EventAvailability.Busy,
      alerts: [60, 15],
    },
  });
  return id;
};
```

### Create a recurring event

Add a `recurrence` rule to create a recurring event. The following example creates an event that repeats every week on Mondays and Wednesdays for ten occurrences:

```typescript
import {
  Calendar,
  RecurrenceFrequency,
  Weekday,
} from '@capawesome-team/capacitor-calendar';

const createRecurringEvent = async () => {
  const { id } = await Calendar.createEvent({
    event: {
      title: 'Team stand-up',
      startDate: new Date('2026-09-01T09:00:00').getTime(),
      recurrence: {
        frequency: RecurrenceFrequency.Weekly,
        interval: 1,
        count: 10,
        daysOfWeek: [Weekday.Monday, Weekday.Wednesday],
      },
    },
  });
  return id;
};
```

### Get the events in a range

Query all events that overlap a time range with `getEvents(...)`. Recurring events are expanded, so each occurrence is returned as a separate entry with its own `startDate`. Pass a `calendarId` to restrict the query to a single calendar:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const getEventsOfNextWeek = async () => {
  const from = Date.now();
  const to = from + 7 * 24 * 60 * 60 * 1000;
  const { events } = await Calendar.getEvents({ from, to });
  return events;
};
```

A single event can be retrieved by its identifier with `getEventById(...)`, which resolves with `null` if the event does not exist:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const getEventById = async (id: string) => {
  const { event } = await Calendar.getEventById({ id });
  return event;
};
```

### Update an event

Update an event with `updateEventById(...)`. Only the properties that you pass are changed, all others keep their current values. Setting a property to `null` (or an array property to `[]`) removes it from the event:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const rescheduleEvent = async (id: string, startDate: number) => {
  await Calendar.updateEventById({
    id,
    event: {
      startDate,
      endDate: startDate + 30 * 60 * 1000,
    },
  });
};

const clearEventDetails = async (id: string) => {
  await Calendar.updateEventById({
    id,
    event: {
      location: null,
      description: null,
      alerts: [],
    },
  });
};
```

### Delete a single occurrence

Pass the `instanceStartDate` of an occurrence, as returned by `getEvents(...)`, to apply an operation to a single occurrence of a recurring event instead of the whole series. The `span` option controls whether the operation affects only that occurrence or the occurrence and all future ones:

```typescript
import { Calendar, EventSpan } from '@capawesome-team/capacitor-calendar';

const deleteOccurrence = async (id: string, instanceStartDate: number) => {
  await Calendar.deleteEventById({
    id,
    instanceStartDate,
    span: EventSpan.ThisEvent,
  });
};

const deleteAllFutureOccurrences = async (
  id: string,
  instanceStartDate: number,
) => {
  await Calendar.deleteEventById({
    id,
    instanceStartDate,
    span: EventSpan.ThisAndFutureEvents,
  });
};
```

Without `instanceStartDate`, the entire recurring event is deleted.

### Display the system event dialog

Let the user create an event in the system dialog with `displayCreateEvent(...)`, optionally prefilled with your event data. On iOS, the identifier of the created event is returned if the user saved the event:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const displayCreateEvent = async () => {
  const { id } = await Calendar.displayCreateEvent({
    event: {
      title: 'Lunch with Jane',
      startDate: new Date('2026-09-01T12:00:00').getTime(),
      location: 'Main Street 1, Springfield',
    },
  });
  return id;
};
```

Use `displayUpdateEventById(...)` to let the user edit an existing event. On iOS, the `action` describes what the user did in the dialog:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const displayUpdateEventById = async (id: string) => {
  const { action } = await Calendar.displayUpdateEventById({ id });
  return action;
};
```

The system dialogs on Android do not report a result back to the app, so `id` and `action` are only available on iOS. On Android, only the event properties that are supported by the system intent are prefilled, and the remaining properties are silently ignored.

### Listen for calendar changes

Register a listener for the `calendarChange` event to reload your data whenever calendars or events change, including changes that were made in the calendar app or by other apps. The event carries no payload, because the operating systems do not report which entities changed:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const addCalendarChangeListener = async () => {
  return Calendar.addListener('calendarChange', () => {
    console.log('The calendars or events on the device have changed.');
  });
};

const removeAllListeners = async () => {
  await Calendar.removeAllListeners();
};
```

### Open the calendar app and the app settings

Open the calendar app of the device at a specific date with `openCalendar(...)`, for example after an event was created. Use `openSettings()` to send the user to the settings of your app so that a previously denied permission can be granted:

```typescript
import { Calendar } from '@capawesome-team/capacitor-calendar';

const openCalendar = async (date: number) => {
  await Calendar.openCalendar({ date });
};

const openSettings = async () => {
  await Calendar.openSettings();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`createCalendar(...)`](#createcalendar)
* [`createEvent(...)`](#createevent)
* [`deleteCalendarById(...)`](#deletecalendarbyid)
* [`deleteEventById(...)`](#deleteeventbyid)
* [`displayCreateEvent(...)`](#displaycreateevent)
* [`displayUpdateEventById(...)`](#displayupdateeventbyid)
* [`getCalendars()`](#getcalendars)
* [`getDefaultCalendar()`](#getdefaultcalendar)
* [`getEventById(...)`](#geteventbyid)
* [`getEvents(...)`](#getevents)
* [`openCalendar(...)`](#opencalendar)
* [`openSettings()`](#opensettings)
* [`requestPermissions(...)`](#requestpermissions)
* [`updateEventById(...)`](#updateeventbyid)
* [`addListener('calendarChange', ...)`](#addlistenercalendarchange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions to access the device calendar.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### createCalendar(...)

```typescript
createCalendar(options: CreateCalendarOptions) => Promise<CreateCalendarResult>
```

Create a new calendar on the device.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#createcalendaroptions">CreateCalendarOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createcalendarresult">CreateCalendarResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### createEvent(...)

```typescript
createEvent(options: CreateEventOptions) => Promise<CreateEventResult>
```

Create a new event in a calendar.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#createeventoptions">CreateEventOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createeventresult">CreateEventResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### deleteCalendarById(...)

```typescript
deleteCalendarById(options: DeleteCalendarByIdOptions) => Promise<void>
```

Delete a calendar from the device.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletecalendarbyidoptions">DeleteCalendarByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### deleteEventById(...)

```typescript
deleteEventById(options: DeleteEventByIdOptions) => Promise<void>
```

Delete an event from the device.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deleteeventbyidoptions">DeleteEventByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### displayCreateEvent(...)

```typescript
displayCreateEvent(options?: DisplayCreateEventOptions | undefined) => Promise<DisplayCreateEventResult>
```

Display the system user interface to create a new event.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displaycreateeventoptions">DisplayCreateEventOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#displaycreateeventresult">DisplayCreateEventResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### displayUpdateEventById(...)

```typescript
displayUpdateEventById(options: DisplayUpdateEventByIdOptions) => Promise<DisplayUpdateEventByIdResult>
```

Display the system user interface to update an existing event.

Only available on Android and iOS.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displayupdateeventbyidoptions">DisplayUpdateEventByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#displayupdateeventbyidresult">DisplayUpdateEventByIdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getCalendars()

```typescript
getCalendars() => Promise<GetCalendarsResult>
```

Get all calendars on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcalendarsresult">GetCalendarsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getDefaultCalendar()

```typescript
getDefaultCalendar() => Promise<GetDefaultCalendarResult>
```

Get the default calendar for new events.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdefaultcalendarresult">GetDefaultCalendarResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getEventById(...)

```typescript
getEventById(options: GetEventByIdOptions) => Promise<GetEventByIdResult>
```

Get a single event by its identifier.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#geteventbyidoptions">GetEventByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#geteventbyidresult">GetEventByIdResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getEvents(...)

```typescript
getEvents(options: GetEventsOptions) => Promise<GetEventsResult>
```

Get the events in a given time range.

Returns all events that overlap the time range, including
single occurrences of recurring events.

Rejects with the error code `CALENDAR_NOT_FOUND` if a `calendarId` is
provided but no calendar with that identifier exists.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#geteventsoptions">GetEventsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#geteventsresult">GetEventsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openCalendar(...)

```typescript
openCalendar(options?: OpenCalendarOptions | undefined) => Promise<void>
```

Open the calendar app of the device.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#opencalendaroptions">OpenCalendarOptions</a></code> |

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the settings of the app so that the user can grant or revoke
permissions.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions to access the device calendar.

On iOS 17+, requesting only the `writeCalendar` permission requests
write-only access. Requesting the `readCalendar` permission requests
full access.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### updateEventById(...)

```typescript
updateEventById(options: UpdateEventByIdOptions) => Promise<void>
```

Update an existing event.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updateeventbyidoptions">UpdateEventByIdOptions</a></code> |

**Since:** 0.0.1

--------------------


### addListener('calendarChange', ...)

```typescript
addListener(eventName: 'calendarChange', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when calendars or events are created, updated or deleted,
including by other apps.

Only available on Android and iOS.

| Param              | Type                          |
| ------------------ | ----------------------------- |
| **`eventName`**    | <code>'calendarChange'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>    |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### PermissionStatus

| Prop                | Type                                                        | Description                                                                                                                  | Since |
| ------------------- | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`readCalendar`**  | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for reading calendar data. On iOS, this is `prompt` as long as only write-only access has been granted. | 0.0.1 |
| **`writeCalendar`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for writing calendar data.                                                                              | 0.0.1 |


#### CreateCalendarResult

| Prop     | Type                | Description                             | Since |
| -------- | ------------------- | --------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the created calendar. | 0.0.1 |


#### CreateCalendarOptions

| Prop        | Type                | Description                                                        | Since |
| ----------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`color`** | <code>string</code> | The color of the calendar as a hex string in the format `#RRGGBB`. | 0.0.1 |
| **`title`** | <code>string</code> | The title of the calendar.                                         | 0.0.1 |


#### CreateEventResult

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The identifier of the created event. | 0.0.1 |


#### CreateEventOptions

| Prop        | Type                                              | Description          | Since |
| ----------- | ------------------------------------------------- | -------------------- | ----- |
| **`event`** | <code><a href="#eventinput">EventInput</a></code> | The event to create. | 0.0.1 |


#### EventInput

| Prop               | Type                                                            | Description                                                                                                                                                                                                                                                                     | Default                                              | Since |
| ------------------ | --------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- | ----- |
| **`alerts`**       | <code>number[]</code>                                           | The alerts of the event as offsets in minutes before the start of the event. Negative values represent minutes after the start.                                                                                                                                                 |                                                      | 0.0.1 |
| **`allDay`**       | <code>boolean</code>                                            | Whether the event is an all-day event. For all-day events, `startDate` and `endDate` are interpreted as midnight UTC of the respective calendar day.                                                                                                                            | <code>false</code>                                   | 0.0.1 |
| **`availability`** | <code><a href="#eventavailability">EventAvailability</a></code> | The availability of the event.                                                                                                                                                                                                                                                  |                                                      | 0.0.1 |
| **`calendarId`**   | <code>string</code>                                             | The identifier of the calendar in which the event is created.                                                                                                                                                                                                                   | <code>The identifier of the default calendar.</code> | 0.0.1 |
| **`description`**  | <code>string</code>                                             | The description of the event.                                                                                                                                                                                                                                                   |                                                      | 0.0.1 |
| **`endDate`**      | <code>number</code>                                             | The end date of the event as a timestamp in milliseconds. If not provided, the event ends one hour after `startDate` (all-day events: on the same day as `startDate`). For all-day events, the end date is exclusive (midnight UTC of the day after the last day of the event). |                                                      | 0.0.1 |
| **`location`**     | <code>string</code>                                             | The location of the event.                                                                                                                                                                                                                                                      |                                                      | 0.0.1 |
| **`recurrence`**   | <code><a href="#recurrencerule">RecurrenceRule</a></code>       | The recurrence rule of the event.                                                                                                                                                                                                                                               |                                                      | 0.0.1 |
| **`startDate`**    | <code>number</code>                                             | The start date of the event as a timestamp in milliseconds.                                                                                                                                                                                                                     |                                                      | 0.0.1 |
| **`timezone`**     | <code>string</code>                                             | The time zone of the event as an IANA time zone identifier.                                                                                                                                                                                                                     | <code>The default time zone of the device.</code>    | 0.0.1 |
| **`title`**        | <code>string</code>                                             | The title of the event.                                                                                                                                                                                                                                                         |                                                      | 0.0.1 |


#### RecurrenceRule

| Prop             | Type                                                                | Description                                                                                                                                                    | Default        | Since |
| ---------------- | ------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------- | ----- |
| **`count`**      | <code>number</code>                                                 | The number of occurrences after which the recurrence ends. Takes precedence over `until`.                                                                      |                | 0.0.1 |
| **`daysOfWeek`** | <code>Weekday[]</code>                                              | The days of the week on which the event recurs.                                                                                                                |                | 0.0.1 |
| **`frequency`**  | <code><a href="#recurrencefrequency">RecurrenceFrequency</a></code> | The frequency of the recurrence.                                                                                                                               |                | 0.0.1 |
| **`interval`**   | <code>number</code>                                                 | The interval between occurrences of the recurrence. For example, an interval of `2` with a `Weekly` frequency results in an event that recurs every two weeks. | <code>1</code> | 0.0.1 |
| **`until`**      | <code>number</code>                                                 | The date on which the recurrence ends as a timestamp in milliseconds.                                                                                          |                | 0.0.1 |


#### DeleteCalendarByIdOptions

| Prop     | Type                | Description                               | Since |
| -------- | ------------------- | ----------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the calendar to delete. | 0.0.1 |


#### DeleteEventByIdOptions

| Prop                    | Type                                            | Description                                                                                                                                                                                                                                                                                                              | Default                          | Since |
| ----------------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------- | ----- |
| **`id`**                | <code>string</code>                             | The identifier of the event to delete.                                                                                                                                                                                                                                                                                   |                                  | 0.0.1 |
| **`instanceStartDate`** | <code>number</code>                             | The start date of a single occurrence of a recurring event as a timestamp in milliseconds, as returned by `getEvents(...)`. If provided, only the given occurrence (or, depending on `span`, the given and all future occurrences) of the recurring event is deleted. If omitted, the entire recurring event is deleted. |                                  | 0.0.1 |
| **`span`**              | <code><a href="#eventspan">EventSpan</a></code> | The span of a recurring event to which the operation is applied. Only applied when `instanceStartDate` is provided.                                                                                                                                                                                                      | <code>EventSpan.ThisEvent</code> | 0.0.1 |


#### DisplayCreateEventResult

| Prop     | Type                | Description                                                                                       | Since |
| -------- | ------------------- | ------------------------------------------------------------------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the created event. Only returned if the event was saved. Only available on iOS. | 0.0.1 |


#### DisplayCreateEventOptions

| Prop        | Type                                                             | Description                                                                                                                                                                 | Since |
| ----------- | ---------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`event`** | <code>Partial&lt;<a href="#eventinput">EventInput</a>&gt;</code> | The event data with which the dialog is prefilled. On Android, only the properties supported by the system intent are applied. Unsupported properties are silently ignored. | 0.0.1 |


#### DisplayUpdateEventByIdResult

| Prop         | Type                                                        | Description                                                              | Since |
| ------------ | ----------------------------------------------------------- | ------------------------------------------------------------------------ | ----- |
| **`action`** | <code><a href="#eventeditaction">EventEditAction</a></code> | The action that the user performed in the dialog. Only available on iOS. | 0.0.1 |


#### DisplayUpdateEventByIdOptions

| Prop     | Type                | Description                            | Since |
| -------- | ------------------- | -------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the event to update. | 0.0.1 |


#### GetCalendarsResult

| Prop            | Type                    | Description                  | Since |
| --------------- | ----------------------- | ---------------------------- | ----- |
| **`calendars`** | <code>Calendar[]</code> | The calendars on the device. | 0.0.1 |


#### Calendar

| Prop           | Type                 | Description                                                        | Since |
| -------------- | -------------------- | ------------------------------------------------------------------ | ----- |
| **`color`**    | <code>string</code>  | The color of the calendar as a hex string in the format `#RRGGBB`. | 0.0.1 |
| **`id`**       | <code>string</code>  | The identifier of the calendar.                                    | 0.0.1 |
| **`title`**    | <code>string</code>  | The title of the calendar.                                         | 0.0.1 |
| **`writable`** | <code>boolean</code> | Whether events can be added, updated, or deleted in this calendar. | 0.0.1 |


#### GetDefaultCalendarResult

| Prop           | Type                                                  | Description                                                                                   | Since |
| -------------- | ----------------------------------------------------- | --------------------------------------------------------------------------------------------- | ----- |
| **`calendar`** | <code><a href="#calendar">Calendar</a> \| null</code> | The default calendar for new events. If no default calendar is available, `null` is returned. | 0.0.1 |


#### GetEventByIdResult

| Prop        | Type                                                            | Description                                                                     | Since |
| ----------- | --------------------------------------------------------------- | ------------------------------------------------------------------------------- | ----- |
| **`event`** | <code><a href="#calendarevent">CalendarEvent</a> \| null</code> | The event with the given identifier. If no event was found, `null` is returned. | 0.0.1 |


#### CalendarEvent

| Prop               | Type                                                            | Description                                                                                                                                                        | Since |
| ------------------ | --------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`alerts`**       | <code>number[]</code>                                           | The alerts of the event as offsets in minutes before the start of the event. Negative values represent minutes after the start.                                    | 0.0.1 |
| **`allDay`**       | <code>boolean</code>                                            | Whether the event is an all-day event. For all-day events, `startDate` and `endDate` are returned as midnight UTC of the respective calendar day.                  | 0.0.1 |
| **`availability`** | <code><a href="#eventavailability">EventAvailability</a></code> | The availability of the event.                                                                                                                                     | 0.0.1 |
| **`calendarId`**   | <code>string</code>                                             | The identifier of the calendar that the event belongs to.                                                                                                          | 0.0.1 |
| **`description`**  | <code>string</code>                                             | The description of the event.                                                                                                                                      | 0.0.1 |
| **`endDate`**      | <code>number</code>                                             | The end date of the event as a timestamp in milliseconds. For all-day events, the end date is exclusive (midnight UTC of the day after the last day of the event). | 0.0.1 |
| **`id`**           | <code>string</code>                                             | The identifier of the event. All occurrences of a recurring event share the same identifier.                                                                       | 0.0.1 |
| **`location`**     | <code>string</code>                                             | The location of the event.                                                                                                                                         | 0.0.1 |
| **`recurrence`**   | <code><a href="#recurrencerule">RecurrenceRule</a></code>       | The recurrence rule of the event.                                                                                                                                  | 0.0.1 |
| **`startDate`**    | <code>number</code>                                             | The start date of the event as a timestamp in milliseconds.                                                                                                        | 0.0.1 |
| **`status`**       | <code><a href="#eventstatus">EventStatus</a></code>             | The confirmation status of the event.                                                                                                                              | 0.0.1 |
| **`timezone`**     | <code>string</code>                                             | The time zone of the event as an IANA time zone identifier.                                                                                                        | 0.0.1 |
| **`title`**        | <code>string</code>                                             | The title of the event.                                                                                                                                            | 0.0.1 |


#### GetEventByIdOptions

| Prop     | Type                | Description                  | Since |
| -------- | ------------------- | ---------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the event. | 0.0.1 |


#### GetEventsResult

| Prop         | Type                         | Description                         | Since |
| ------------ | ---------------------------- | ----------------------------------- | ----- |
| **`events`** | <code>CalendarEvent[]</code> | The events in the given time range. | 0.0.1 |


#### GetEventsOptions

| Prop             | Type                | Description                                                                                                       | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`calendarId`** | <code>string</code> | The identifier of the calendar to get the events from. If not provided, the events of all calendars are returned. | 0.0.1 |
| **`from`**       | <code>number</code> | The start of the time range as a timestamp in milliseconds.                                                       | 0.0.1 |
| **`to`**         | <code>number</code> | The end of the time range as a timestamp in milliseconds.                                                         | 0.0.1 |


#### OpenCalendarOptions

| Prop       | Type                | Description                                                                  | Default                        | Since |
| ---------- | ------------------- | ---------------------------------------------------------------------------- | ------------------------------ | ----- |
| **`date`** | <code>number</code> | The date to which the calendar app is opened as a timestamp in milliseconds. | <code>The current time.</code> | 0.0.1 |


#### RequestPermissionsOptions

| Prop              | Type                                  | Description                 | Default                                        | Since |
| ----------------- | ------------------------------------- | --------------------------- | ---------------------------------------------- | ----- |
| **`permissions`** | <code>CalendarPermissionType[]</code> | The permissions to request. | <code>['readCalendar', 'writeCalendar']</code> | 0.0.1 |


#### UpdateEventByIdOptions

| Prop                    | Type                                                                                                     | Description                                                                                                                                                                                                                                                                                                                    | Default                          | Since |
| ----------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------- | ----- |
| **`event`**             | <code><a href="#nullable">Nullable</a>&lt;Partial&lt;<a href="#eventinput">EventInput</a>&gt;&gt;</code> | The updated event data. Missing properties are ignored and keep their existing values. Properties explicitly set to `null` (or empty arrays `[]`) will be removed from the event. Properties that are required for the event structure (`allDay`, `calendarId`, `endDate`, `startDate`, `timezone`) must not be set to `null`. |                                  | 0.0.1 |
| **`id`**                | <code>string</code>                                                                                      | The identifier of the event to update.                                                                                                                                                                                                                                                                                         |                                  | 0.0.1 |
| **`instanceStartDate`** | <code>number</code>                                                                                      | The start date of a single occurrence of a recurring event as a timestamp in milliseconds, as returned by `getEvents(...)`. If provided, only the given occurrence (or, depending on `span`, the given and all future occurrences) of the recurring event is updated. If omitted, the entire recurring event is updated.       |                                  | 0.0.1 |
| **`span`**              | <code><a href="#eventspan">EventSpan</a></code>                                                          | The span of a recurring event to which the operation is applied. Only applied when `instanceStartDate` is provided.                                                                                                                                                                                                            | <code>EventSpan.ThisEvent</code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### EventEditAction

The action that the user performed in a system event dialog.

<code>'canceled' | 'deleted' | 'saved'</code>


#### EventStatus

The confirmation status of an event.

<code>'canceled' | 'confirmed' | 'tentative'</code>


#### CalendarPermissionType

The permissions to request when calling `requestPermissions(...)`.

<code>'readCalendar' | 'writeCalendar'</code>


#### Nullable

<code>{ [K in keyof T]: T[K] | null }</code>


### Enums


#### EventAvailability

| Members           | Value                      | Description                                                                                 | Since |
| ----------------- | -------------------------- | ------------------------------------------------------------------------------------------- | ----- |
| **`Busy`**        | <code>'BUSY'</code>        | The time of the event is marked as busy.                                                    | 0.0.1 |
| **`Free`**        | <code>'FREE'</code>        | The time of the event is marked as free.                                                    | 0.0.1 |
| **`Tentative`**   | <code>'TENTATIVE'</code>   | The time of the event is marked as tentative.                                               | 0.0.1 |
| **`Unavailable`** | <code>'UNAVAILABLE'</code> | The time of the event is marked as unavailable. On Android, this value is mapped to `Busy`. | 0.0.1 |


#### Weekday

| Members         | Value                    | Description                     | Since |
| --------------- | ------------------------ | ------------------------------- | ----- |
| **`Friday`**    | <code>'FRIDAY'</code>    | The event recurs on Fridays.    | 0.0.1 |
| **`Monday`**    | <code>'MONDAY'</code>    | The event recurs on Mondays.    | 0.0.1 |
| **`Saturday`**  | <code>'SATURDAY'</code>  | The event recurs on Saturdays.  | 0.0.1 |
| **`Sunday`**    | <code>'SUNDAY'</code>    | The event recurs on Sundays.    | 0.0.1 |
| **`Thursday`**  | <code>'THURSDAY'</code>  | The event recurs on Thursdays.  | 0.0.1 |
| **`Tuesday`**   | <code>'TUESDAY'</code>   | The event recurs on Tuesdays.   | 0.0.1 |
| **`Wednesday`** | <code>'WEDNESDAY'</code> | The event recurs on Wednesdays. | 0.0.1 |


#### RecurrenceFrequency

| Members       | Value                  | Description               | Since |
| ------------- | ---------------------- | ------------------------- | ----- |
| **`Daily`**   | <code>'DAILY'</code>   | The event recurs daily.   | 0.0.1 |
| **`Monthly`** | <code>'MONTHLY'</code> | The event recurs monthly. | 0.0.1 |
| **`Weekly`**  | <code>'WEEKLY'</code>  | The event recurs weekly.  | 0.0.1 |
| **`Yearly`**  | <code>'YEARLY'</code>  | The event recurs yearly.  | 0.0.1 |


#### EventSpan

| Members                   | Value                                 | Description                                                                                         | Since |
| ------------------------- | ------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`ThisAndFutureEvents`** | <code>'THIS_AND_FUTURE_EVENTS'</code> | The operation is applied to the given occurrence and all future occurrences of the recurring event. | 0.0.1 |
| **`ThisEvent`**           | <code>'THIS_EVENT'</code>             | The operation is applied only to the given occurrence of the recurring event.                       | 0.0.1 |

</docgen-api>

## Recurring Events

Recurring events are stored as a single event with a recurrence rule, but they are displayed to the user as many occurrences. The plugin makes both views available and keeps them consistent across Android and iOS.

**Occurrence expansion**: `getEvents(...)` expands recurring events into their occurrences. Each occurrence is returned as a separate entry whose `startDate` and `endDate` describe that occurrence, while the `id` and the `recurrence` rule are the same for all occurrences of the same series. `getEventById(...)`, in contrast, always returns the series itself with its original start date.

**Targeting a single occurrence**: `updateEventById(...)` and `deleteEventById(...)` operate on the whole series by default. Pass the `startDate` of an occurrence as `instanceStartDate` to target that occurrence instead, and use `span` to choose the scope:

- `EventSpan.ThisEvent` (default) applies the change to the given occurrence only. The rest of the series remains untouched.
- `EventSpan.ThisAndFutureEvents` applies the change to the given occurrence and all following ones, while past occurrences remain untouched.

**Reading a rule back**: the `recurrence` property of an event is both written and read. A rule that uses parts which are not supported by the plugin — for example a rule created in another app — is read back as the closest supported subset, so `frequency` and `interval` are always correct even if a more exotic part is dropped.

**All-day events and time zones**: an event with `allDay: true` has no time of day. Its `startDate` and `endDate` are therefore interpreted and returned as **midnight UTC** of the respective calendar day on both platforms, independent of the time zone of the device. The `endDate` is **exclusive**: it is midnight UTC of the day after the last day of the event, so a one-day all-day event on `2026-09-01` has `startDate = Date.UTC(2026, 8, 1)` and `endDate = Date.UTC(2026, 8, 2)`. An `endDate` that is missing or not on a midnight boundary is normalized: it is floored to midnight UTC of its day and raised to at least one day after `startDate`, so the event always spans at least one full day. Build these timestamps in UTC, for example with `Date.UTC(2026, 8, 1)`, and format them in UTC as well — using the local time zone instead is what makes an all-day event appear on the wrong day. For events that are not all-day, the `timezone` property defines the time zone in which the event takes place and defaults to the time zone of the device.

## FAQ

### How is this plugin different from other similar plugins?

We focused on correctness instead of a long feature list. Every runtime failure rejects with a documented error code, so your app can react to a missing event or a read-only calendar programmatically. Recurrence rules can be read back, not just written, so a recurring event can round-trip through your app without losing information. Single occurrences of a recurring event can be updated and deleted, including all future occurrences. All-day events and time zones follow one documented contract on both platforms, and behavior that only one platform can provide is documented as such instead of being silently faked. On top of that, the plugin is covered by unit tests, is built from the ground up by the Capawesome Team, and comes with priority support.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Contacts](https://capawesome.io/docs/sdks/capacitor/contacts/): Read and write device contacts, for example to invite them to an event.
- [Datetime Picker](https://capawesome.io/docs/sdks/capacitor/datetime-picker/): Let the user pick the date and time of an event natively.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/calendar/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/calendar/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/calendar/LICENSE).
