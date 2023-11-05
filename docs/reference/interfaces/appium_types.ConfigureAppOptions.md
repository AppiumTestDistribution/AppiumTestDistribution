# Interface: ConfigureAppOptions

[@appium/types](../modules/appium_types.md).ConfigureAppOptions

## Table of contents

### Properties

- [onPostProcess](appium_types.ConfigureAppOptions.md#onpostprocess)
- [supportedExtensions](appium_types.ConfigureAppOptions.md#supportedextensions)

## Properties

### onPostProcess

• `Optional` **onPostProcess**: (`obj`: [`PostProcessOptions`](appium_types.PostProcessOptions.md)<[`HTTPHeaders`](../modules/appium_types.md#httpheaders)\>) => `undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md) \| `Promise`<`undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md)\>

#### Type declaration

▸ (`obj`): `undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md) \| `Promise`<`undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md)\>

Optional function, which should be applied to the application after it is
downloaded/preprocessed.

This function may be async and is expected to accept single object parameter. The function is
expected to either return a falsy value, which means the app must not be cached and a fresh
copy of it is downloaded each time, _or_ if this function returns an object containing an
`appPath` property, then the integrity of it will be verified and stored into the cache.

##### Parameters

| Name | Type |
| :------ | :------ |
| `obj` | [`PostProcessOptions`](appium_types.PostProcessOptions.md)<[`HTTPHeaders`](../modules/appium_types.md#httpheaders)\> |

##### Returns

`undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md) \| `Promise`<`undefined` \| [`PostProcessResult`](appium_types.PostProcessResult.md)\>

#### Defined in

@appium/types/lib/driver.ts:2133

___

### supportedExtensions

• **supportedExtensions**: `string`[]

#### Defined in

@appium/types/lib/driver.ts:2136
