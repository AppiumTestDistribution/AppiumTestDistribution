# Interface: AppiumLogger

[@appium/types](../modules/appium_types.md).AppiumLogger

Describes the `npmlog`-based internal logger.

**`See`**

https://npm.im/npmlog

## Table of contents

### Properties

- [level](appium_types.AppiumLogger.md#level)
- [levels](appium_types.AppiumLogger.md#levels)
- [prefix](appium_types.AppiumLogger.md#prefix)

### Methods

- [debug](appium_types.AppiumLogger.md#debug)
- [error](appium_types.AppiumLogger.md#error)
- [errorAndThrow](appium_types.AppiumLogger.md#errorandthrow)
- [http](appium_types.AppiumLogger.md#http)
- [info](appium_types.AppiumLogger.md#info)
- [silly](appium_types.AppiumLogger.md#silly)
- [unwrap](appium_types.AppiumLogger.md#unwrap)
- [verbose](appium_types.AppiumLogger.md#verbose)
- [warn](appium_types.AppiumLogger.md#warn)

## Properties

### level

• **level**: [`AppiumLoggerLevel`](../modules/appium_types.md#appiumloggerlevel)

#### Defined in

@appium/types/lib/logger.ts:27

___

### levels

• **levels**: [`AppiumLoggerLevel`](../modules/appium_types.md#appiumloggerlevel)[]

#### Defined in

@appium/types/lib/logger.ts:28

___

### prefix

• `Optional` **prefix**: [`AppiumLoggerPrefix`](../modules/appium_types.md#appiumloggerprefix)

Log prefix, if applicable.

#### Defined in

@appium/types/lib/logger.ts:32

## Methods

### debug

▸ **debug**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:33

___

### error

▸ **error**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:36

___

### errorAndThrow

▸ **errorAndThrow**(`...args`): `never`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`never`

#### Defined in

@appium/types/lib/logger.ts:40

___

### http

▸ **http**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:39

___

### info

▸ **info**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:34

___

### silly

▸ **silly**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:38

___

### unwrap

▸ **unwrap**(): `Logger`

Returns the underlying `npmlog` Logger.

#### Returns

`Logger`

#### Defined in

@appium/types/lib/logger.ts:26

___

### verbose

▸ **verbose**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:37

___

### warn

▸ **warn**(`...args`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `...args` | `any`[] |

#### Returns

`void`

#### Defined in

@appium/types/lib/logger.ts:35
