# Interface: PostProcessOptions<Headers\>

[@appium/types](../modules/appium_types.md).PostProcessOptions

Options for the post-processing step

The generic can be supplied if using `axios`, where `headers` is a fancy object.

## Type parameters

| Name | Type |
| :------ | :------ |
| `Headers` | [`HTTPHeaders`](../modules/appium_types.md#httpheaders) |

## Table of contents

### Properties

- [appPath](appium_types.PostProcessOptions.md#apppath)
- [cachedAppInfo](appium_types.PostProcessOptions.md#cachedappinfo)
- [headers](appium_types.PostProcessOptions.md#headers)
- [isUrl](appium_types.PostProcessOptions.md#isurl)

## Properties

### appPath

• `Optional` **appPath**: `string`

A string containing full path to the preprocessed application package (either downloaded or a local one)

#### Defined in

@appium/types/lib/driver.ts:2118

___

### cachedAppInfo

• `Optional` **cachedAppInfo**: [`CachedAppInfo`](appium_types.CachedAppInfo.md)

The information about the previously cached app instance (if exists)

#### Defined in

@appium/types/lib/driver.ts:2104

___

### headers

• `Optional` **headers**: `Headers`

Optional headers object.

Only present if `isUrl` is `true` and if the server responds to `HEAD` requests. All header names are normalized to lowercase.

#### Defined in

@appium/types/lib/driver.ts:2114

___

### isUrl

• `Optional` **isUrl**: `boolean`

Whether the app has been downloaded from a remote URL

#### Defined in

@appium/types/lib/driver.ts:2108
