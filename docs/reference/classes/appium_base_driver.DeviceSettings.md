# Class: DeviceSettings<T\>

[@appium/base-driver](../modules/appium_base_driver.md).DeviceSettings

**`Implements`**

## Type parameters

| Name |
| :------ |
| `T` |

## Table of contents

### Constructors

- [constructor](appium_base_driver.DeviceSettings.md#constructor)

### Properties

- [\_onSettingsUpdate](appium_base_driver.DeviceSettings.md#_onsettingsupdate)
- [\_settings](appium_base_driver.DeviceSettings.md#_settings)

### Methods

- [getSettings](appium_base_driver.DeviceSettings.md#getsettings)
- [update](appium_base_driver.DeviceSettings.md#update)

## Constructors

### constructor

• **new DeviceSettings**<`T`\>(`defaultSettings?`, `onSettingsUpdate?`)

Creates a _shallow copy_ of the `defaultSettings` parameter!

#### Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends `StringRecord` |

#### Parameters

| Name | Type |
| :------ | :------ |
| `defaultSettings?` | `T` |
| `onSettingsUpdate?` | [`SettingsUpdateListener`](../modules/appium_types.md#settingsupdatelistener)<`T`\> |

#### Defined in

@appium/base-driver/lib/basedriver/device-settings.js:33

## Properties

### \_onSettingsUpdate

• `Protected` **\_onSettingsUpdate**: [`SettingsUpdateListener`](../modules/appium_types.md#settingsupdatelistener)<`T`\>

#### Defined in

@appium/base-driver/lib/basedriver/device-settings.js:26

___

### \_settings

• `Protected` **\_settings**: `T`

#### Defined in

@appium/base-driver/lib/basedriver/device-settings.js:20

## Methods

### getSettings

▸ **getSettings**(): `T`

#### Returns

`T`

#### Defined in

@appium/base-driver/lib/basedriver/device-settings.js:69

___

### update

▸ **update**(`newSettings`): `Promise`<`void`\>

calls updateSettings from implementing driver every time a setting is changed.

#### Parameters

| Name | Type |
| :------ | :------ |
| `newSettings` | `T` |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/base-driver/lib/basedriver/device-settings.js:42
