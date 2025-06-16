# @capawesome-team/capacitor-contacts

Capacitor plugin to read, write, or select device contacts. Supports Android, iOS and Web with advanced features like contact groups, pagination, and native modals.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for contacts. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 📇 **Contacts**: Create, update, delete and retrieve device contacts.
- 📌 **Groups**: Create, update, delete and retrieve contact groups on iOS.
- 🎫 **Accounts**: Add contacts to specific accounts on Android.
- 📖 **Pagination**: Paginate through contacts to avoid performance issues.
- 🔍 **Filtering**: Filter contacts by ID, email, phone number, etc. (Coming soon!)
- 📱 **Native Modals**: Create, update and display contacts in native modals.
- 🎯 **Picking**: Let the user select a device contact.
- 🖼️ **Photos**: Set, update and retrieve contact photos.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-contacts
npx cap sync
```

### Android

#### Permissions

This API requires the following elements be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Required if you want to read contacts. -->
<uses-permission android:name="android.permission.READ_CONTACTS" />
<!-- Required if you want to write contacts. -->
<uses-permission android:name="android.permission.WRITE_CONTACTS" />
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the `NSContactsUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the user's contacts:

```xml
<key>NSContactsUsageDescription</key>
<string>We need access to your contacts to display them in the app.</string>
```

#### Entitlements

To access the `note` field of a contact, your app must have the `com.apple.developer.contacts.notes` entitlement. Check out the [Apple documentation](https://developer.apple.com/documentation/bundleresources/entitlements/com.apple.developer.contacts.notes) for more information.

If you don't need access to the `note` field, you can skip this step.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { 
  Contacts,
  EmailAddressType,
  PhoneNumberType,
  PostalAddressType
} from '@capawesome-team/capacitor-contacts';

const countContacts = async () => {
  const { total } = await Contacts.countContacts();
  return total;
};

const createContact = async () => {
  return Contacts.createContact({
    contact: {
      birthday: {
        day: 1,
        month: 1,
        year: 1990
      },
      givenName: 'John',
      familyName: 'Doe',
      emailAddresses: [
        {
          value: 'mail@example.com',
          type: EmailAddressType.Home,
          isPrimary: true
        }
      ],
      phoneNumbers: [
        {
          value: '1234567890',
          type: PhoneNumberType.Mobile,
          isPrimary: true
        }
      ],
      postalAddresses: [
        {
          street: '123 Main St',
          city: 'Springfield',
          state: 'IL',
          postalCode: '62701',
          country: 'USA',
          type: PostalAddressType.Home,
          isPrimary: true
        }
      ]
    }
  });
};

const createGroup = async () => {
  return Contacts.createGroup({
    group: {
      name: 'My Group'
    }
  });
};

const deleteContactById = async (id: string) => {
  await Contacts.deleteContactById({ id });
};

const deleteGroupById = async (id: string) => {
  await Contacts.deleteGroupById({ id });
};

const displayContactById = async (id: string) => {
  await Contacts.displayContactById({ id });
};

const displayCreateContact = async () => {
  const { id } = await Contacts.displayCreateContact({
    contact: {
      givenName: 'John',
      familyName: 'Doe'
    }
  });
  return id;
};

const displayUpdateContactById = async (id: string) => {
  await Contacts.displayUpdateContactById({ id });
};

const getAccounts = async () => {
  const { accounts } = await Contacts.getAccounts();
  return accounts;
};

const getContactById = async (id: string) => {
  const { contact } = await Contacts.getContactById({ id });
  return contact;
};

const getContacts = async () => {
  const { contacts } = await Contacts.getContacts();
  return contacts;
};

const getGroupById = async (id: string) => {
  const { group } = await Contacts.getGroupById({ id });
  return group;
};

const getGroups = async () => {
  const { groups } = await Contacts.getGroups();
  return groups;
};

const isSupported = async () => {
  const { isSupported } = await Contacts.isSupported();
  return isSupported;
};

const pickContacts = async () => {
  const { contacts } = await Contacts.pickContacts();
  return contacts;
};

const updateContactById = async (id: string) => {
  await Contacts.updateContactById({
    id,
    contact: {
      givenName: 'John',
      familyName: 'Doe'
    }
  });
};

const checkPermissions = async () => {
  return Contacts.checkPermissions();
};

const requestPermissions = async () => {
  return Contacts.requestPermissions();
};
```

## API

<docgen-index>

* [`countContacts()`](#countcontacts)
* [`createContact(...)`](#createcontact)
* [`createGroup(...)`](#creategroup)
* [`deleteContactById(...)`](#deletecontactbyid)
* [`deleteGroupById(...)`](#deletegroupbyid)
* [`displayContactById(...)`](#displaycontactbyid)
* [`displayCreateContact(...)`](#displaycreatecontact)
* [`displayUpdateContactById(...)`](#displayupdatecontactbyid)
* [`getAccounts()`](#getaccounts)
* [`getContactById(...)`](#getcontactbyid)
* [`getContacts(...)`](#getcontacts)
* [`getGroupById(...)`](#getgroupbyid)
* [`getGroups()`](#getgroups)
* [`isSupported()`](#issupported)
* [`pickContact(...)`](#pickcontact)
* [`pickContacts(...)`](#pickcontacts)
* [`updateContactById(...)`](#updatecontactbyid)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### countContacts()

```typescript
countContacts() => Promise<CountContactsResult>
```

Count the number of contacts on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#countcontactsresult">CountContactsResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### createContact(...)

```typescript
createContact(options: CreateContactOptions) => Promise<CreateContactResult>
```

Create a new contact on the device.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#createcontactoptions">CreateContactOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createcontactresult">CreateContactResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### createGroup(...)

```typescript
createGroup(options: CreateGroupOptions) => Promise<CreateGroupResult>
```

Create a new contact group on the device.

Only available on iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#creategroupoptions">CreateGroupOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#creategroupresult">CreateGroupResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### deleteContactById(...)

```typescript
deleteContactById(options: DeleteContactByIdOptions) => Promise<void>
```

Delete a contact from the device.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletecontactbyidoptions">DeleteContactByIdOptions</a></code> |

**Since:** 7.0.0

--------------------


### deleteGroupById(...)

```typescript
deleteGroupById(options: DeleteGroupByIdOptions) => Promise<void>
```

Delete a contact group from the device.

Only available on iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletegroupbyidoptions">DeleteGroupByIdOptions</a></code> |

**Since:** 7.4.0

--------------------


### displayContactById(...)

```typescript
displayContactById(options: DisplayContactByIdOptions) => Promise<void>
```

Display an existing contact by identifier.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displaycontactbyidoptions">DisplayContactByIdOptions</a></code> |

**Since:** 7.4.0

--------------------


### displayCreateContact(...)

```typescript
displayCreateContact(options?: DisplayCreateContactOptions | undefined) => Promise<DisplayCreateContactResult>
```

Open a native modal to create a new device contact.

This allows the user to update the contact information before saving it
and does not require any permissions.

Only available on Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displaycreatecontactoptions">DisplayCreateContactOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#displaycreatecontactresult">DisplayCreateContactResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


### displayUpdateContactById(...)

```typescript
displayUpdateContactById(options: DisplayUpdateContactByIdOptions) => Promise<void>
```

Open a native modal to update a contact.

Only available on Android and iOS.

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displayupdatecontactbyidoptions">DisplayUpdateContactByIdOptions</a></code> |

**Since:** 7.4.0

--------------------


### getAccounts()

```typescript
getAccounts() => Promise<GetAccountsResult>
```

List all accounts on the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getaccountsresult">GetAccountsResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getContactById(...)

```typescript
getContactById(options: GetContactByIdOptions) => Promise<GetContactByIdResult>
```

Find a contact by identifier.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcontactbyidoptions">GetContactByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcontactbyidresult">GetContactByIdResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### getContacts(...)

```typescript
getContacts(options?: GetContactsOptions | undefined) => Promise<GetContactsResult>
```

List all contacts on the device.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getcontactsoptions">GetContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcontactsresult">GetContactsResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### getGroupById(...)

```typescript
getGroupById(options: GetGroupByIdOptions) => Promise<GetGroupByIdResult>
```

Find a contact group by identifier.

Only available on iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#getgroupbyidoptions">GetGroupByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getgroupbyidresult">GetGroupByIdResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### getGroups()

```typescript
getGroups() => Promise<GetGroupsResult>
```

List all contact groups on the device.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getgroupsresult">GetGroupsResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Check if the contacts API is available on the device.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### pickContact(...)

```typescript
pickContact(options?: PickContactsOptions | undefined) => Promise<PickContactResult>
```

Open the contact picker to select a contact from the device.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### pickContacts(...)

```typescript
pickContacts(options?: PickContactsOptions | undefined) => Promise<PickContactsResult>
```

Open the contact picker to select a contact from the device.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

**Since:** 7.4.0

--------------------


### updateContactById(...)

```typescript
updateContactById(options: UpdateContactByIdOptions) => Promise<void>
```

Update an existing contact on the device.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatecontactbyidoptions">UpdateContactByIdOptions</a></code> |

**Since:** 7.4.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions to access contacts.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions to access contacts.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### Interfaces


#### CountContactsResult

| Prop        | Type                | Description             | Since |
| ----------- | ------------------- | ----------------------- | ----- |
| **`total`** | <code>number</code> | The number of contacts. | 7.4.0 |


#### CreateContactResult

| Prop     | Type                | Description                             | Since |
| -------- | ------------------- | --------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier for the created contact. | 7.0.0 |


#### CreateContactOptions

| Prop          | Type                                                                              | Description            | Since |
| ------------- | --------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | The contact to create. | 7.0.0 |


#### Contact

| Prop                   | Type                                          | Description                                                                     | Since |
| ---------------------- | --------------------------------------------- | ------------------------------------------------------------------------------- | ----- |
| **`account`**          | <code><a href="#account">Account</a></code>   | The account associated with the contact. Only available on Android.             | 7.4.0 |
| **`birthday`**         | <code><a href="#birthday">Birthday</a></code> | The birthday of the contact.                                                    | 7.3.0 |
| **`emailAddresses`**   | <code>EmailAddress[]</code>                   | The list of email addresses for the contact.                                    | 7.0.0 |
| **`familyName`**       | <code>string</code>                           | The family name of the contact. Only available on Android and iOS.              | 7.0.0 |
| **`givenName`**        | <code>string</code>                           | The given name of the contact. Only available on Android and iOS.               | 7.0.0 |
| **`groupIds`**         | <code>string[]</code>                         | The identifier of the groups the contact belongs to. Only available on iOS.     | 7.4.0 |
| **`id`**               | <code>string</code>                           | The identifier for the contact. Only available on Android and iOS.              | 7.0.0 |
| **`jobTitle`**         | <code>string</code>                           | The job title of the contact. Only available on Android and iOS.                | 7.0.0 |
| **`middleName`**       | <code>string</code>                           | The middle name of the contact. Only available on Android and iOS.              | 7.0.0 |
| **`fullName`**         | <code>string</code>                           | The full name of the contact. Only available on Web.                            | 7.0.0 |
| **`namePrefix`**       | <code>string</code>                           | The name prefix of the contact. Only available on Android and iOS.              | 7.0.0 |
| **`nameSuffix`**       | <code>string</code>                           | The name suffix of the contact. Only available on Android and iOS.              | 7.0.0 |
| **`note`**             | <code>string</code>                           | A note about the contact. Only available on Android and iOS.                    | 7.0.0 |
| **`organizationName`** | <code>string</code>                           | The organization name of the contact. Only available on Android and iOS.        | 7.0.0 |
| **`phoneNumbers`**     | <code>PhoneNumber[]</code>                    | The list of phone numbers for the contact.                                      | 7.0.0 |
| **`photo`**            | <code>string</code>                           | The photo of the contact as a base64 string. Only available on Android and iOS. | 7.0.0 |
| **`postalAddresses`**  | <code>PostalAddress[]</code>                  | The list of postal addresses for the contact.                                   | 7.0.0 |
| **`urlAddresses`**     | <code>UrlAddress[]</code>                     | The list of URL addresses for the contact. Only available on Android and iOS.   | 7.0.0 |


#### Account

| Prop       | Type                | Description                                  | Since |
| ---------- | ------------------- | -------------------------------------------- | ----- |
| **`name`** | <code>string</code> | The account name. Only available on Android. | 7.4.0 |
| **`type`** | <code>string</code> | The account type. Only available on Android. | 7.4.0 |


#### Birthday

| Prop        | Type                | Description                                                                                                                                               | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`day`**   | <code>number</code> | The day of the birthdate.                                                                                                                                 | 7.3.0 |
| **`month`** | <code>number</code> | The month of the birthdate.                                                                                                                               | 7.3.0 |
| **`year`**  | <code>number</code> | The year of the birthdate. On **Android**, this must be provided if the `day` and `month` are provided when using the `displayCreateContact(...)` method. | 7.3.0 |


#### EmailAddress

| Prop            | Type                                                          | Description                                                                                                                                        | Default                             | Since |
| --------------- | ------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------- | ----- |
| **`isPrimary`** | <code>boolean</code>                                          | Whether this email address is the primary one for the contact.                                                                                     | <code>false</code>                  | 7.0.0 |
| **`label`**     | <code>string</code>                                           | A custom label for the email address. On **iOS**, this label is only set if the type is <a href="#emailaddresstype">`EmailAddressType.Custom`</a>. |                                     | 7.0.0 |
| **`type`**      | <code><a href="#emailaddresstype">EmailAddressType</a></code> | The type of email address.                                                                                                                         | <code>EmailAddressType.Other</code> | 7.0.0 |
| **`value`**     | <code>string</code>                                           | The email address.                                                                                                                                 |                                     | 7.0.0 |


#### PhoneNumber

| Prop            | Type                                                        | Description                                                                                                                                     | Default                            | Since |
| --------------- | ----------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- | ----- |
| **`isPrimary`** | <code>boolean</code>                                        | Whether this email address is the primary one for the contact.                                                                                  |                                    | 7.0.0 |
| **`label`**     | <code>string</code>                                         | A custom label for the phone number. On **iOS**, this label is only set if the type is <a href="#phonenumbertype">`PhoneNumberType.Custom`</a>. |                                    | 7.0.0 |
| **`type`**      | <code><a href="#phonenumbertype">PhoneNumberType</a></code> | The type of phone number.                                                                                                                       | <code>PhoneNumberType.Other</code> | 7.0.0 |
| **`value`**     | <code>string</code>                                         | The phone number.                                                                                                                               |                                    |       |


#### PostalAddress

| Prop                 | Type                                                            | Description                                                                                                                                                                              | Default                              | Since |
| -------------------- | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------ | ----- |
| **`city`**           | <code>string</code>                                             | The city for the postal address.                                                                                                                                                         |                                      | 7.0.0 |
| **`country`**        | <code>string</code>                                             | The country for the postal address.                                                                                                                                                      |                                      | 7.0.0 |
| **`formatted`**      | <code>string</code>                                             | The formatted postal address.                                                                                                                                                            |                                      | 7.0.0 |
| **`isoCountryCode`** | <code>string</code>                                             | The ISO country code for the postal address. Only available on iOS.                                                                                                                      |                                      | 7.0.0 |
| **`isPrimary`**      | <code>boolean</code>                                            | Whether this postal address is the primary one for the contact. Only available on Android and iOS.                                                                                       | <code>false</code>                   | 7.0.0 |
| **`label`**          | <code>string</code>                                             | A custom label for the postal address. On **iOS**, this label is only set if the type is <a href="#postaladdresstype">`PostalAddressType.Custom`</a>. Only available on Android and iOS. |                                      | 7.0.0 |
| **`neighborhood`**   | <code>string</code>                                             | The neighborhood for the postal address. Only available on Android and iOS.                                                                                                              |                                      | 7.0.0 |
| **`postalCode`**     | <code>string</code>                                             | The postal code for the postal address. Only available on Android and iOS.                                                                                                               |                                      | 7.0.0 |
| **`state`**          | <code>string</code>                                             | The state for the postal address.                                                                                                                                                        |                                      | 7.0.0 |
| **`street`**         | <code>string</code>                                             | The street for the postal address. Only available on Android and iOS.                                                                                                                    |                                      | 7.0.0 |
| **`type`**           | <code><a href="#postaladdresstype">PostalAddressType</a></code> | The type of postal address. Only available on Android and iOS.                                                                                                                           | <code>PostalAddressType.Other</code> | 7.0.0 |


#### UrlAddress

| Prop        | Type                                                      | Description                         | Default                           | Since |
| ----------- | --------------------------------------------------------- | ----------------------------------- | --------------------------------- | ----- |
| **`label`** | <code>string</code>                                       | A custom label for the URL address. |                                   | 7.5.0 |
| **`type`**  | <code><a href="#urladdresstype">UrlAddressType</a></code> | The type of URL address.            | <code>UrlAddressType.Other</code> | 7.5.0 |
| **`value`** | <code>string</code>                                       | The URL address.                    |                                   |       |


#### CreateGroupResult

| Prop     | Type                | Description                           | Since |
| -------- | ------------------- | ------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier for the created group. | 7.4.0 |


#### CreateGroupOptions

| Prop        | Type                                                                          | Description          | Since |
| ----------- | ----------------------------------------------------------------------------- | -------------------- | ----- |
| **`group`** | <code><a href="#omit">Omit</a>&lt;<a href="#group">Group</a>, 'id'&gt;</code> | The group to create. | 7.4.0 |


#### Group

| Prop       | Type                | Description                   | Since |
| ---------- | ------------------- | ----------------------------- | ----- |
| **`id`**   | <code>string</code> | The identifier for the group. | 7.4.0 |
| **`name`** | <code>string</code> | The name of the group.        | 7.4.0 |


#### DeleteContactByIdOptions

| Prop     | Type                | Description                     | Since |
| -------- | ------------------- | ------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier for the contact. | 7.0.0 |


#### DeleteGroupByIdOptions

| Prop     | Type                | Description                   | Since |
| -------- | ------------------- | ----------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier for the group. | 7.4.0 |


#### DisplayContactByIdOptions

| Prop     | Type                | Description                               | Since |
| -------- | ------------------- | ----------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the contact to display. | 7.4.0 |


#### DisplayCreateContactResult

| Prop     | Type                | Description                                                                                                              | Since |
| -------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`id`** | <code>string</code> | The identifier for the created contact. On **Android**, you need the `readContacts` permission to return the identifier. | 7.4.0 |


#### DisplayCreateContactOptions

| Prop          | Type                                                                              | Description                                         | Since |
| ------------- | --------------------------------------------------------------------------------- | --------------------------------------------------- | ----- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | The contact to display in the create contact modal. | 7.2.0 |


#### DisplayUpdateContactByIdOptions

| Prop     | Type                | Description                              | Since |
| -------- | ------------------- | ---------------------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the contact to update. | 7.4.0 |


#### GetAccountsResult

| Prop           | Type                   | Description                                   | Since |
| -------------- | ---------------------- | --------------------------------------------- | ----- |
| **`accounts`** | <code>Account[]</code> | An array of available accounts on the device. | 7.4.0 |


#### GetContactByIdResult

| Prop          | Type                                                |
| ------------- | --------------------------------------------------- |
| **`contact`** | <code><a href="#contact">Contact</a> \| null</code> |


#### GetContactByIdOptions

| Prop         | Type                                                  | Description                           | Default                                                                                                                                                                                                   | Since |
| ------------ | ----------------------------------------------------- | ------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> | The fields to return for the contact. | <code>['birthday', 'emailAddresses', 'familyName', 'givenName', 'id', 'jobTitle', 'middleName', 'namePrefix', 'nameSuffix', 'organizationName', 'phoneNumbers', 'postalAddresses', 'urlAddresses']</code> | 7.1.0 |
| **`id`**     | <code>string</code>                                   | The identifier for the contact.       |                                                                                                                                                                                                           | 7.0.0 |


#### GetContactsResult

| Prop           | Type                   | Description                                                                                       | Since |
| -------------- | ---------------------- | ------------------------------------------------------------------------------------------------- | ----- |
| **`contacts`** | <code>Contact[]</code> | The list of contacts on the device. **Note**: No photos are returned to avoid performance issues. | 7.0.0 |


#### GetContactsOptions

| Prop         | Type                                                  | Description                             | Default                                                                                                                                                                                       | Since |
| ------------ | ----------------------------------------------------- | --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> | The fields to return for the contact.   | <code>['emailAddresses', 'familyName', 'givenName', 'id', 'jobTitle', 'middleName', 'namePrefix', 'nameSuffix', 'organizationName', 'phoneNumbers', 'postalAddresses', 'urlAddresses']</code> | 7.1.0 |
| **`limit`**  | <code>number</code>                                   | Limit the number of contacts returned.  | <code>1000</code>                                                                                                                                                                             | 7.4.0 |
| **`offset`** | <code>number</code>                                   | Offset the number of contacts returned. | <code>0</code>                                                                                                                                                                                | 7.4.0 |


#### GetGroupByIdResult

| Prop        | Type                                            | Description                              | Since |
| ----------- | ----------------------------------------------- | ---------------------------------------- | ----- |
| **`group`** | <code><a href="#group">Group</a> \| null</code> | The group with the specified identifier. | 7.4.0 |


#### GetGroupByIdOptions

| Prop     | Type                | Description                   | Since |
| -------- | ------------------- | ----------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier for the group. | 7.4.0 |


#### GetGroupsResult

| Prop         | Type                 | Description                       | Since |
| ------------ | -------------------- | --------------------------------- | ----- |
| **`groups`** | <code>Group[]</code> | The list of groups on the device. | 7.4.0 |


#### IsSupportedResult

| Prop              | Type                 | Description                                                                                    | Since |
| ----------------- | -------------------- | ---------------------------------------------------------------------------------------------- | ----- |
| **`isSupported`** | <code>boolean</code> | Whether the contacts API is available on the device. This is always `true` on Android and iOS. | 7.0.0 |


#### PickContactsResult

| Prop           | Type                   | Description                                         | Since |
| -------------- | ---------------------- | --------------------------------------------------- | ----- |
| **`contacts`** | <code>Contact[]</code> | The selected contacts. Empty if none were selected. | 7.0.0 |


#### PickContactsOptions

| Prop           | Type                                                  | Description                                                              | Default                                                                                                                                                                                                   | Since |
| -------------- | ----------------------------------------------------- | ------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`fields`**   | <code>(keyof <a href="#contact">Contact</a>)[]</code> | The fields to return for the contact. Only available on Android and iOS. | <code>['birthday', 'emailAddresses', 'familyName', 'givenName', 'id', 'jobTitle', 'middleName', 'namePrefix', 'nameSuffix', 'organizationName', 'phoneNumbers', 'postalAddresses', 'urlAddresses']</code> | 7.4.0 |
| **`multiple`** | <code>boolean</code>                                  | Whether to allow selecting multiple contacts. Only available on Web.     | <code>false</code>                                                                                                                                                                                        | 7.0.0 |


#### UpdateContactByIdOptions

| Prop          | Type                                                                              | Description                                                                                                                                                                          | Since |
| ------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | The updated contact information. **Attention**: All fields are required to be provided, even if they are not updated. Fields that are not provided will be removed from the contact. | 7.4.0 |
| **`id`**      | <code>string</code>                                                               | The identifier for the contact.                                                                                                                                                      | 7.4.0 |


#### PermissionStatus

| Prop                | Type                                                                        | Description                                                               | Since |
| ------------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------- | ----- |
| **`readContacts`**  | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> | Permission state for reading contacts. Only available on Android and iOS. | 7.0.0 |
| **`writeContacts`** | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> | Permission state for writing contacts. Only available on Android and iOS. | 7.0.0 |


#### RequestPermissionsOptions

| Prop              | Type                                  | Description                 | Default                                        | Since |
| ----------------- | ------------------------------------- | --------------------------- | ---------------------------------------------- | ----- |
| **`permissions`** | <code>ContactsPermissionType[]</code> | The permissions to request. | <code>['readContacts', 'writeContacts']</code> | 7.0.0 |


### Type Aliases


#### Omit

Construct a type with the properties of T except for those in type K.

<code><a href="#pick">Pick</a>&lt;T, <a href="#exclude">Exclude</a>&lt;keyof T, K&gt;&gt;</code>


#### Pick

From T, pick a set of properties whose keys are in the union K

<code>{
 [P in K]: T[P];
 }</code>


#### Exclude

<a href="#exclude">Exclude</a> from T those types that are assignable to U

<code>T extends U ? never : T</code>


#### ContactField

<code>keyof <a href="#contact">Contact</a></code>


#### PickContactOptions

<code><a href="#pickcontactsoptions">PickContactsOptions</a></code>


#### PickContactResult

<code><a href="#pickcontactsresult">PickContactsResult</a></code>


#### ContactsPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ContactsPermissionType

<code>'readContacts' | 'writeContacts'</code>


### Enums


#### EmailAddressType

| Members      | Value                 | Description                | Since |
| ------------ | --------------------- | -------------------------- | ----- |
| **`Custom`** | <code>'CUSTOM'</code> |                            | 7.0.0 |
| **`Home`**   | <code>'HOME'</code>   |                            | 7.0.0 |
| **`ICloud`** | <code>'ICLOUD'</code> | Only available on iOS.     | 7.0.0 |
| **`Mobile`** | <code>'MOBILE'</code> | Only available on Android. | 7.0.0 |
| **`Other`**  | <code>'OTHER'</code>  |                            | 7.0.0 |
| **`School`** | <code>'SCHOOL'</code> | Only available on iOS.     | 7.0.0 |
| **`Work`**   | <code>'WORK'</code>   |                            | 7.0.0 |


#### PhoneNumberType

| Members           | Value                       | Description                | Since |
| ----------------- | --------------------------- | -------------------------- | ----- |
| **`Assistant`**   | <code>'ASSISTANT'</code>    | Only available on Android. | 7.0.0 |
| **`Callback`**    | <code>'CALLBACK'</code>     | Only available on Android. | 7.0.0 |
| **`Car`**         | <code>'CAR'</code>          | Only available on Android. | 7.0.0 |
| **`CompanyMain`** | <code>'COMPANY_MAIN'</code> | Only available on Android. | 7.0.0 |
| **`Custom`**      | <code>'CUSTOM'</code>       |                            | 7.0.0 |
| **`FaxHome`**     | <code>'FAX_HOME'</code>     |                            | 7.0.0 |
| **`FaxOther`**    | <code>'FAX_OTHER'</code>    |                            | 7.0.0 |
| **`FaxWork`**     | <code>'FAX_WORK'</code>     |                            | 7.0.0 |
| **`Home`**        | <code>'HOME'</code>         |                            | 7.0.0 |
| **`IPhone`**      | <code>'IPHONE'</code>       | Only available on iOS.     | 7.0.0 |
| **`Isdn`**        | <code>'ISDN'</code>         | Only available on Android. | 7.0.0 |
| **`Main`**        | <code>'MAIN'</code>         |                            | 7.0.0 |
| **`Mms`**         | <code>'MMS'</code>          | Only available on Android. | 7.0.0 |
| **`Mobile`**      | <code>'MOBILE'</code>       |                            | 7.0.0 |
| **`Other`**       | <code>'OTHER'</code>        |                            | 7.0.0 |
| **`Pager`**       | <code>'PAGER'</code>        |                            | 7.0.0 |
| **`Radio`**       | <code>'RADIO'</code>        | Only available on Android. | 7.0.0 |
| **`Telex`**       | <code>'TELEX'</code>        | Only available on Android. | 7.0.0 |
| **`TtyTdd`**      | <code>'TTY_TDD'</code>      | Only available on Android. | 7.0.0 |
| **`Work`**        | <code>'WORK'</code>         |                            | 7.0.0 |
| **`WorkMobile`**  | <code>'WORK_MOBILE'</code>  | Only available on Android. | 7.0.0 |
| **`WorkPager`**   | <code>'WORK_PAGER'</code>   | Only available on Android. | 7.0.0 |


#### PostalAddressType

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`Custom`** | <code>'CUSTOM'</code> | 7.0.0 |
| **`Home`**   | <code>'HOME'</code>   | 7.0.0 |
| **`Other`**  | <code>'OTHER'</code>  | 7.0.0 |
| **`Work`**   | <code>'WORK'</code>   | 7.0.0 |


#### UrlAddressType

| Members        | Value                   | Description                | Since |
| -------------- | ----------------------- | -------------------------- | ----- |
| **`Blog`**     | <code>'BLOG'</code>     | Only available on Android. | 7.5.0 |
| **`Custom`**   | <code>'CUSTOM'</code>   |                            | 7.5.0 |
| **`Ftp`**      | <code>'FTP'</code>      | Only available on Android. | 7.5.0 |
| **`Home`**     | <code>'HOME'</code>     |                            | 7.5.0 |
| **`Homepage`** | <code>'HOMEPAGE'</code> |                            | 7.5.0 |
| **`Other`**    | <code>'OTHER'</code>    |                            | 7.5.0 |
| **`Profile`**  | <code>'PROFILE'</code>  | Only available on Android. | 7.5.0 |
| **`School`**   | <code>'SCHOOL'</code>   | Only available on iOS.     | 7.5.0 |
| **`Work`**     | <code>'WORK'</code>     |                            | 7.5.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/contacts/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/contacts/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/contacts/LICENSE).
