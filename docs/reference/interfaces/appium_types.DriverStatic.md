# Interface: DriverStatic<T\>

[@appium/types](../modules/appium_types.md).DriverStatic

Static members of a [`DriverClass`](../modules/appium_types.md#driverclass).

This is likely unusable by external consumers, but YMMV!

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends [`Driver`](appium_types.Driver.md) |

## Table of contents

### Properties

- [baseVersion](appium_types.DriverStatic.md#baseversion)
- [executeMethodMap](appium_types.DriverStatic.md#executemethodmap)
- [newMethodMap](appium_types.DriverStatic.md#newmethodmap)
- [updateServer](appium_types.DriverStatic.md#updateserver)

## Properties

### baseVersion

• **baseVersion**: `string`

#### Defined in

@appium/types/lib/driver.ts:1999

___

### executeMethodMap

• `Optional` **executeMethodMap**: [`ExecuteMethodMap`](../modules/appium_types.md#executemethodmap)<`T`\>

#### Defined in

@appium/types/lib/driver.ts:2002

___

### newMethodMap

• `Optional` **newMethodMap**: [`MethodMap`](../modules/appium_types.md#methodmap)<`T`\>

#### Defined in

@appium/types/lib/driver.ts:2001

___

### updateServer

• `Optional` **updateServer**: [`UpdateServerCallback`](../modules/appium_types.md#updateservercallback)

#### Defined in

@appium/types/lib/driver.ts:2000
