# Interface: ILogCommands

[@appium/types](../modules/appium_types.md).ILogCommands

## Hierarchy

- **`ILogCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Properties

- [supportedLogTypes](appium_types.ILogCommands.md#supportedlogtypes)

### Methods

- [getLog](appium_types.ILogCommands.md#getlog)
- [getLogTypes](appium_types.ILogCommands.md#getlogtypes)

## Properties

### supportedLogTypes

• **supportedLogTypes**: `Readonly`<[`LogDefRecord`](../modules/appium_types.md#logdefrecord)\>

Definition of the available log types

#### Defined in

@appium/types/lib/driver.ts:334

## Methods

### getLog

▸ **getLog**(`logType`): `Promise`<`any`\>

Get the log for a given log type.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `logType` | `string` | Name/key of log type as defined in [`supportedLogTypes`](appium_types.ILogCommands.md#supportedlogtypes). |

#### Returns

`Promise`<`any`\>

#### Defined in

@appium/types/lib/driver.ts:346

___

### getLogTypes

▸ **getLogTypes**(): `Promise`<`string`[]\>

Get available log types as a list of strings

#### Returns

`Promise`<`string`[]\>

#### Defined in

@appium/types/lib/driver.ts:339
