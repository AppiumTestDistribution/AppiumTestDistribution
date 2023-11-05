# Interface: DriverHelpers

[@appium/types](../modules/appium_types.md).DriverHelpers

## Table of contents

### Properties

- [configureApp](appium_types.DriverHelpers.md#configureapp)
- [duplicateKeys](appium_types.DriverHelpers.md#duplicatekeys)
- [generateDriverLogPrefix](appium_types.DriverHelpers.md#generatedriverlogprefix)
- [isPackageOrBundle](appium_types.DriverHelpers.md#ispackageorbundle)
- [parseCapsArray](appium_types.DriverHelpers.md#parsecapsarray)

## Properties

### configureApp

• **configureApp**: (`app`: `string`, `supportedAppExtensions?`: `string` \| `string`[] \| [`ConfigureAppOptions`](appium_types.ConfigureAppOptions.md)) => `Promise`<`string`\>

#### Type declaration

▸ (`app`, `supportedAppExtensions?`): `Promise`<`string`\>

##### Parameters

| Name | Type |
| :------ | :------ |
| `app` | `string` |
| `supportedAppExtensions?` | `string` \| `string`[] \| [`ConfigureAppOptions`](appium_types.ConfigureAppOptions.md) |

##### Returns

`Promise`<`string`\>

#### Defined in

@appium/types/lib/driver.ts:486

___

### duplicateKeys

• **duplicateKeys**: <T\>(`input`: `T`, `firstKey`: `string`, `secondKey`: `string`) => `T`

#### Type declaration

▸ <`T`\>(`input`, `firstKey`, `secondKey`): `T`

##### Type parameters

| Name |
| :------ |
| `T` |

##### Parameters

| Name | Type |
| :------ | :------ |
| `input` | `T` |
| `firstKey` | `string` |
| `secondKey` | `string` |

##### Returns

`T`

#### Defined in

@appium/types/lib/driver.ts:491

___

### generateDriverLogPrefix

• **generateDriverLogPrefix**: <C\>(`obj`: [`Core`](appium_types.Core.md)<`C`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>, `sessionId?`: `string`) => `string`

#### Type declaration

▸ <`C`\>(`obj`, `sessionId?`): `string`

##### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |

##### Parameters

| Name | Type |
| :------ | :------ |
| `obj` | [`Core`](appium_types.Core.md)<`C`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> |
| `sessionId?` | `string` |

##### Returns

`string`

#### Defined in

@appium/types/lib/driver.ts:493

___

### isPackageOrBundle

• **isPackageOrBundle**: (`app`: `string`) => `boolean`

#### Type declaration

▸ (`app`): `boolean`

##### Parameters

| Name | Type |
| :------ | :------ |
| `app` | `string` |

##### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:490

___

### parseCapsArray

• **parseCapsArray**: (`cap`: `string` \| `string`[]) => `string`[]

#### Type declaration

▸ (`cap`): `string`[]

##### Parameters

| Name | Type |
| :------ | :------ |
| `cap` | `string` \| `string`[] |

##### Returns

`string`[]

#### Defined in

@appium/types/lib/driver.ts:492
