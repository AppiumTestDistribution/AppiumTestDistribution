# Interface: PluginMethodDef<T\>

[@appium/types](../modules/appium_types.md).PluginMethodDef

A definition of an exposed API command in a [`Plugin`](appium_types.Plugin.md).

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends [`Plugin`](appium_types.Plugin.md) |

## Hierarchy

- [`BaseMethodDef`](appium_types.BaseMethodDef.md)

  ↳ **`PluginMethodDef`**

## Table of contents

### Properties

- [command](appium_types.PluginMethodDef.md#command)
- [neverProxy](appium_types.PluginMethodDef.md#neverproxy)
- [payloadParams](appium_types.PluginMethodDef.md#payloadparams)

## Properties

### command

• `Optional` `Readonly` **command**: `ConditionalKeys`<`Required`<`T`\>, [`DriverCommand`](../modules/appium_types.md#drivercommand)\>

Name of the command.

#### Defined in

@appium/types/lib/command.ts:87

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
