# Interface: DriverExecuteMethodDef<T\>

[@appium/types](../modules/appium_types.md).DriverExecuteMethodDef

A definition of an execute method in a [`Driver`](appium_types.Driver.md).

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends [`Driver`](appium_types.Driver.md) |

## Hierarchy

- [`BaseExecuteMethodDef`](appium_types.BaseExecuteMethodDef.md)

  ↳ **`DriverExecuteMethodDef`**

## Table of contents

### Properties

- [command](appium_types.DriverExecuteMethodDef.md#command)
- [params](appium_types.DriverExecuteMethodDef.md#params)

## Properties

### command

• **command**: `ConditionalKeys`<`T`, [`DriverCommand`](../modules/appium_types.md#drivercommand)\>

#### Defined in

@appium/types/lib/command.ts:122

___

### params

• `Optional` **params**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `optional?` | readonly `string`[] |
| `required?` | readonly `string`[] |

#### Inherited from

[BaseExecuteMethodDef](appium_types.BaseExecuteMethodDef.md).[params](appium_types.BaseExecuteMethodDef.md#params)

#### Defined in

@appium/types/lib/command.ts:112
