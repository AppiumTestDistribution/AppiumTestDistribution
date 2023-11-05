# Interface: LogDef

[@appium/types](../modules/appium_types.md).LogDef

A definition of a log type

## Table of contents

### Properties

- [description](appium_types.LogDef.md#description)
- [getter](appium_types.LogDef.md#getter)

## Properties

### description

• **description**: `string`

Description of the log type.

The only place this is used is in error messages if the client provides an invalid log type
via [`getLog`](appium_types.ILogCommands.md#getlog).

#### Defined in

@appium/types/lib/driver.ts:365

___

### getter

• **getter**: (`driver`: `any`) => `unknown`

#### Type declaration

▸ (`driver`): `unknown`

Returns all the log data for the given type

This implementation *should* drain, truncate or otherwise reset the log buffer.

##### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | `any` |

##### Returns

`unknown`

#### Defined in

@appium/types/lib/driver.ts:371
