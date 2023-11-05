# Interface: BaseMethodDef

[@appium/types](../modules/appium_types.md).BaseMethodDef

Both [`DriverMethodDef`](appium_types.DriverMethodDef.md) and [`PluginMethodDef`](appium_types.PluginMethodDef.md) share these properties.

## Hierarchy

- **`BaseMethodDef`**

  ↳ [`DriverMethodDef`](appium_types.DriverMethodDef.md)

  ↳ [`PluginMethodDef`](appium_types.PluginMethodDef.md)

## Table of contents

### Properties

- [neverProxy](appium_types.BaseMethodDef.md#neverproxy)
- [payloadParams](appium_types.BaseMethodDef.md#payloadparams)

## Properties

### neverProxy

• `Optional` `Readonly` **neverProxy**: `boolean`

If true, this `Method` will never proxy.

#### Defined in

@appium/types/lib/command.ts:57

___

### payloadParams

• `Optional` `Readonly` **payloadParams**: [`PayloadParams`](appium_types.PayloadParams.md)

Specifies shape of payload

#### Defined in

@appium/types/lib/command.ts:61
