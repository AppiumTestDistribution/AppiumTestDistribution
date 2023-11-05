# Interface: ISettingsCommands<T\>

[@appium/types](../modules/appium_types.md).ISettingsCommands

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends `object` = `object` |

## Hierarchy

- **`ISettingsCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Properties

- [updateSettings](appium_types.ISettingsCommands.md#updatesettings)

### Methods

- [getSettings](appium_types.ISettingsCommands.md#getsettings)

## Properties

### updateSettings

• **updateSettings**: (`settings`: `T`) => `Promise`<`void`\>

#### Type declaration

▸ (`settings`): `Promise`<`void`\>

Update the session's settings dictionary with a new settings object

##### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `settings` | `T` | A key-value map of setting names to values. Settings not named in the map will not have their value adjusted. |

##### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:381

## Methods

### getSettings

▸ **getSettings**(): `Promise`<`T`\>

Get the current settings for the session

#### Returns

`Promise`<`T`\>

The settings object

#### Defined in

@appium/types/lib/driver.ts:388
