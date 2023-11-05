# Interface: PayloadParams

[@appium/types](../modules/appium_types.md).PayloadParams

Defines the shape of a payload for a MethodDef.

## Table of contents

### Properties

- [makeArgs](appium_types.PayloadParams.md#makeargs)
- [optional](appium_types.PayloadParams.md#optional)
- [required](appium_types.PayloadParams.md#required)
- [unwrap](appium_types.PayloadParams.md#unwrap)
- [validate](appium_types.PayloadParams.md#validate)
- [wrap](appium_types.PayloadParams.md#wrap)

## Properties

### makeArgs

• `Optional` **makeArgs**: (`obj`: `any`) => `any`

#### Type declaration

▸ (`obj`): `any`

##### Parameters

| Name | Type |
| :------ | :------ |
| `obj` | `any` |

##### Returns

`any`

#### Defined in

@appium/types/lib/command.ts:15

___

### optional

• `Optional` **optional**: readonly `string`[] \| readonly readonly string[][]

#### Defined in

@appium/types/lib/command.ts:13

___

### required

• `Optional` **required**: readonly `string`[] \| readonly readonly string[][]

#### Defined in

@appium/types/lib/command.ts:12

___

### unwrap

• `Optional` **unwrap**: `string`

#### Defined in

@appium/types/lib/command.ts:11

___

### validate

• `Optional` **validate**: (`obj`: `any`, `protocol`: `string`) => `undefined` \| `string` \| `boolean`

#### Type declaration

▸ (`obj`, `protocol`): `undefined` \| `string` \| `boolean`

##### Parameters

| Name | Type |
| :------ | :------ |
| `obj` | `any` |
| `protocol` | `string` |

##### Returns

`undefined` \| `string` \| `boolean`

#### Defined in

@appium/types/lib/command.ts:14

___

### wrap

• `Optional` **wrap**: `string`

#### Defined in

@appium/types/lib/command.ts:10
