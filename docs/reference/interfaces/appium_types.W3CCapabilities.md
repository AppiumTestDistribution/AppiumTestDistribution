# Interface: W3CCapabilities<C\>

[@appium/types](../modules/appium_types.md).W3CCapabilities

Like [`Capabilities`](../modules/appium_types.md#capabilities), except W3C-style.

Does not contain [`BaseCapabilities`](../modules/appium_types.md#basecapabilities); see [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps).

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |

## Table of contents

### Properties

- [alwaysMatch](appium_types.W3CCapabilities.md#alwaysmatch)
- [firstMatch](appium_types.W3CCapabilities.md#firstmatch)

## Properties

### alwaysMatch

• **alwaysMatch**: `Partial`<[`CapsToNSCaps`](../modules/appium_types.md#capstonscaps)<[`ConstraintsToCaps`](../modules/appium_types.md#constraintstocaps)<`C`\>, ``"appium"``\>\>

#### Defined in

@appium/types/lib/capabilities.ts:108

___

### firstMatch

• **firstMatch**: `Partial`<[`CapsToNSCaps`](../modules/appium_types.md#capstonscaps)<[`ConstraintsToCaps`](../modules/appium_types.md#constraintstocaps)<`C`\>, ``"appium"``\>\>[]

#### Defined in

@appium/types/lib/capabilities.ts:109
