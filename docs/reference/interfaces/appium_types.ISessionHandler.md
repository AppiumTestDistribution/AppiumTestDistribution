# Interface: ISessionHandler<C, CreateResult, DeleteResult, SessionData\>

[@appium/types](../modules/appium_types.md).ISessionHandler

An interface which creates and deletes sessions.

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) = [`Constraints`](../modules/appium_types.md#constraints) |
| `CreateResult` | [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<`C`\> |
| `DeleteResult` | [`DefaultDeleteSessionResult`](../modules/appium_types.md#defaultdeletesessionresult) |
| `SessionData` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- **`ISessionHandler`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Methods

- [createSession](appium_types.ISessionHandler.md#createsession)
- [deleteSession](appium_types.ISessionHandler.md#deletesession)
- [getSession](appium_types.ISessionHandler.md#getsession)
- [getSessions](appium_types.ISessionHandler.md#getsessions)

## Methods

### createSession

▸ **createSession**(`w3cCaps1`, `w3cCaps2?`, `w3cCaps3?`, `driverData?`): `Promise`<`CreateResult`\>

Start a new automation session

**`See`**

[https://w3c.github.io/webdriver/#new-session](https://w3c.github.io/webdriver/#new-session)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `w3cCaps1` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | the new session capabilities |
| `w3cCaps2?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | another place the new session capabilities could be sent (typically left undefined) |
| `w3cCaps3?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | another place the new session capabilities could be sent (typically left undefined) |
| `driverData?` | [`DriverData`](../modules/appium_types.md#driverdata)[] | a list of DriverData objects representing other sessions running for this driver on the same Appium server. This information can be used to help ensure no conflict of resources |

#### Returns

`Promise`<`CreateResult`\>

The capabilities object representing the created session

#### Defined in

@appium/types/lib/driver.ts:430

___

### deleteSession

▸ **deleteSession**(`sessionId?`, `driverData?`): `Promise`<`void` \| `DeleteResult`\>

Stop an automation session

**`See`**

[https://w3c.github.io/webdriver/#delete-session](https://w3c.github.io/webdriver/#delete-session)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `sessionId?` | `string` | the id of the session that is to be deleted |
| `driverData?` | [`DriverData`](../modules/appium_types.md#driverdata)[] | the driver data for other currently-running sessions |

#### Returns

`Promise`<`void` \| `DeleteResult`\>

#### Defined in

@appium/types/lib/driver.ts:444

___

### getSession

▸ **getSession**(): `Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

Get the data for the current session

#### Returns

`Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

A session data object

#### Defined in

@appium/types/lib/driver.ts:458

___

### getSessions

▸ **getSessions**(): `Promise`<[`MultiSessionData`](appium_types.MultiSessionData.md)<[`Constraints`](../modules/appium_types.md#constraints)\>[]\>

Get data for all sessions running on an Appium server

#### Returns

`Promise`<[`MultiSessionData`](appium_types.MultiSessionData.md)<[`Constraints`](../modules/appium_types.md#constraints)\>[]\>

A list of session data objects

#### Defined in

@appium/types/lib/driver.ts:451
