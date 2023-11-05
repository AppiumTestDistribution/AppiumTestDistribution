# Class: JWProxy

[@appium/base-driver](../modules/appium_base_driver.md).JWProxy

## Table of contents

### Constructors

- [constructor](appium_base_driver.JWProxy.md#constructor)

### Properties

- [\_activeRequests](appium_base_driver.JWProxy.md#_activerequests)
- [\_downstreamProtocol](appium_base_driver.JWProxy.md#_downstreamprotocol)
- [\_log](appium_base_driver.JWProxy.md#_log)
- [base](appium_base_driver.JWProxy.md#base)
- [httpAgent](appium_base_driver.JWProxy.md#httpagent)
- [httpsAgent](appium_base_driver.JWProxy.md#httpsagent)
- [port](appium_base_driver.JWProxy.md#port)
- [protocolConverter](appium_base_driver.JWProxy.md#protocolconverter)
- [reqBasePath](appium_base_driver.JWProxy.md#reqbasepath)
- [scheme](appium_base_driver.JWProxy.md#scheme)
- [server](appium_base_driver.JWProxy.md#server)
- [sessionId](appium_base_driver.JWProxy.md#sessionid)
- [timeout](appium_base_driver.JWProxy.md#timeout)

### Accessors

- [downstreamProtocol](appium_base_driver.JWProxy.md#downstreamprotocol)
- [log](appium_base_driver.JWProxy.md#log)

### Methods

- [cancelActiveRequests](appium_base_driver.JWProxy.md#cancelactiverequests)
- [command](appium_base_driver.JWProxy.md#command)
- [endpointRequiresSessionId](appium_base_driver.JWProxy.md#endpointrequiressessionid)
- [getActiveRequestsCount](appium_base_driver.JWProxy.md#getactiverequestscount)
- [getProtocolFromResBody](appium_base_driver.JWProxy.md#getprotocolfromresbody)
- [getSessionIdFromUrl](appium_base_driver.JWProxy.md#getsessionidfromurl)
- [getUrlForProxy](appium_base_driver.JWProxy.md#geturlforproxy)
- [proxy](appium_base_driver.JWProxy.md#proxy)
- [proxyCommand](appium_base_driver.JWProxy.md#proxycommand)
- [proxyReqRes](appium_base_driver.JWProxy.md#proxyreqres)
- [request](appium_base_driver.JWProxy.md#request)
- [requestToCommandName](appium_base_driver.JWProxy.md#requesttocommandname)

## Constructors

### constructor

• **new JWProxy**(`opts?`)

#### Parameters

| Name | Type |
| :------ | :------ |
| `opts` | `Object` |

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:53

## Properties

### \_activeRequests

• **\_activeRequests**: `any`[]

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:70

@appium/base-driver/lib/jsonwp-proxy/proxy.js:111

___

### \_downstreamProtocol

• **\_downstreamProtocol**: `any`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:71

@appium/base-driver/lib/jsonwp-proxy/proxy.js:119

___

### \_log

• **\_log**: `any`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:80

___

### base

• **base**: `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:45

___

### httpAgent

• **httpAgent**: `Agent`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:77

___

### httpsAgent

• **httpsAgent**: `Agent`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:78

___

### port

• **port**: `number`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:43

___

### protocolConverter

• **protocolConverter**: `ProtocolConverter`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:79

___

### reqBasePath

• **reqBasePath**: `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:47

___

### scheme

• **scheme**: `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:39

___

### server

• **server**: `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:41

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:49

___

### timeout

• **timeout**: `number`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:51

## Accessors

### downstreamProtocol

• `get` **downstreamProtocol**(): `any`

#### Returns

`any`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:123

• `set` **downstreamProtocol**(`value`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `value` | `any` |

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:118

___

### log

• `get` **log**(): `any`

#### Returns

`any`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:83

## Methods

### cancelActiveRequests

▸ **cancelActiveRequests**(): `void`

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:110

___

### command

▸ **command**(`url`, `method`, `body?`): `Promise`<`unknown`\>

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `url` | `string` | `undefined` |
| `method` | [`HTTPMethod`](../modules/appium_types.md#httpmethod) | `undefined` |
| `body` | `any` | `null` |

#### Returns

`Promise`<`unknown`\>

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:346

___

### endpointRequiresSessionId

▸ **endpointRequiresSessionId**(`endpoint`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `endpoint` | `any` |

#### Returns

`boolean`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:114

___

### getActiveRequestsCount

▸ **getActiveRequestsCount**(): `number`

#### Returns

`number`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:106

___

### getProtocolFromResBody

▸ **getProtocolFromResBody**(`resObj`): `undefined` \| ``"MJSONWP"`` \| ``"W3C"``

#### Parameters

| Name | Type |
| :------ | :------ |
| `resObj` | `any` |

#### Returns

`undefined` \| ``"MJSONWP"`` \| ``"W3C"``

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:284

___

### getSessionIdFromUrl

▸ **getSessionIdFromUrl**(`url`): `any`

#### Parameters

| Name | Type |
| :------ | :------ |
| `url` | `any` |

#### Returns

`any`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:398

___

### getUrlForProxy

▸ **getUrlForProxy**(`url`): `string`

#### Parameters

| Name | Type |
| :------ | :------ |
| `url` | `any` |

#### Returns

`string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:127

___

### proxy

▸ **proxy**(`url`, `method`, `body?`): `Promise`<`any`[]\>

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `url` | `any` | `undefined` |
| `method` | `any` | `undefined` |
| `body` | ``null`` | `null` |

#### Returns

`Promise`<`any`[]\>

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:185

___

### proxyCommand

▸ **proxyCommand**(`url`, `method`, `body?`): `Promise`<`any`\>

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `url` | `string` | `undefined` |
| `method` | [`HTTPMethod`](../modules/appium_types.md#httpmethod) | `undefined` |
| `body` | `any` | `null` |

#### Returns

`Promise`<`any`\>

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:329

___

### proxyReqRes

▸ **proxyReqRes**(`req`, `res`): `Promise`<`void`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `req` | `any` |
| `res` | `any` |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:403

___

### request

▸ `Private` **request**(`requestConfig`): `Promise`<`AxiosResponse`<`any`, `any`\>\>

Performs requests to the downstream server

 - Do not call this method directly,
it uses client-specific arguments and responses!

#### Parameters

| Name | Type |
| :------ | :------ |
| `requestConfig` | `RawAxiosRequestConfig`<`any`\> |

#### Returns

`Promise`<`AxiosResponse`<`any`, `any`\>\>

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:96

___

### requestToCommandName

▸ **requestToCommandName**(`url`, `method`): `undefined` \| `string`

#### Parameters

| Name | Type |
| :------ | :------ |
| `url` | `string` |
| `method` | [`HTTPMethod`](../modules/appium_types.md#httpmethod) |

#### Returns

`undefined` \| `string`

#### Defined in

@appium/base-driver/lib/jsonwp-proxy/proxy.js:299
