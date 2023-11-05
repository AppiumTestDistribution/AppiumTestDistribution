# Interface: DriverMethodDef<T, D\>

[@appium/types](../modules/appium_types.md).DriverMethodDef

A definition of an exposed API command in a [`Driver`](appium_types.Driver.md).

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends [`Driver`](appium_types.Driver.md) |
| `D` | extends `boolean` = `boolean` |

## Hierarchy

- [`BaseMethodDef`](appium_types.BaseMethodDef.md)

  ↳ **`DriverMethodDef`**

## Table of contents

### Properties

- [command](appium_types.DriverMethodDef.md#command)
- [deprecated](appium_types.DriverMethodDef.md#deprecated)
- [neverProxy](appium_types.DriverMethodDef.md#neverproxy)
- [payloadParams](appium_types.DriverMethodDef.md#payloadparams)

## Properties

### command

• `Optional` `Readonly` **command**: `D` extends ``true`` ? `string` : `ConditionalKeys`<`Required`<`T`\>, [`DriverCommand`](../modules/appium_types.md#drivercommand)\>

Name of the command.

#### Defined in

@appium/types/lib/command.ts:72

___

### deprecated

• `Optional` `Readonly` **deprecated**: `D`

If this is `true`, we do not validate `command`, because it may not exist in `ExternalDriver`.

#### Defined in

@appium/types/lib/command.ts:77

___

### neverProxy

• `Optional` `Readonly` **neverProxy**: `boolean`

If true, this `Method` will never proxy.

#### Inherited from

[BaseMethodDef](appium_types.BaseMethodDef.md).[neverProxy](appium_types.BaseMethodDef.md#neverproxy)

#### Defined in

@appium/types/lib/command.ts:57

___

### payloadParams

• `Optional` `Readonly` **payloadParams**: [`PayloadParams`](appium_types.PayloadParams.md)

Specifies shape of payload

#### Inherited from

[BaseMethodDef](appium_types.BaseMethodDef.md).[payloadParams](appium_types.BaseMethodDef.md#payloadparams)

#### Defined in

@appium/types/lib/command.ts:61
