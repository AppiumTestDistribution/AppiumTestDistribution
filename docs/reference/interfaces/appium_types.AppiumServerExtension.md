# Interface: AppiumServerExtension

[@appium/types](../modules/appium_types.md).AppiumServerExtension

## Table of contents

### Properties

- [webSocketsMapping](appium_types.AppiumServerExtension.md#websocketsmapping)

### Methods

- [addWebSocketHandler](appium_types.AppiumServerExtension.md#addwebsockethandler)
- [close](appium_types.AppiumServerExtension.md#close)
- [getWebSocketHandlers](appium_types.AppiumServerExtension.md#getwebsockethandlers)
- [removeAllWebSocketHandlers](appium_types.AppiumServerExtension.md#removeallwebsockethandlers)
- [removeWebSocketHandler](appium_types.AppiumServerExtension.md#removewebsockethandler)

## Properties

### webSocketsMapping

• **webSocketsMapping**: `Record`<`string`, [`WSServer`](../classes/appium_types.WSServer.md)<typeof `WebSocket`, typeof `IncomingMessage`\>\>

#### Defined in

@appium/types/lib/server.ts:47

## Methods

### addWebSocketHandler

▸ **addWebSocketHandler**(`this`, `handlerPathname`, `handlerServer`): `Promise`<`void`\>

Adds websocket handler to an [`AppiumServer`](../modules/appium_types.md#appiumserver).

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `this` | [`AppiumServer`](../modules/appium_types.md#appiumserver) | - |
| `handlerPathname` | `string` | Web socket endpoint path starting with a single slash character. It is recommended to always prepend `/ws` to all web socket pathnames. |
| `handlerServer` | [`WSServer`](../classes/appium_types.WSServer.md)<typeof `WebSocket`, typeof `IncomingMessage`\> | WebSocket server instance. See https://github.com/websockets/ws/pull/885 for more details on how to configure the handler properly. |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/server.ts:19

___

### close

▸ **close**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/server.ts:13

___

### getWebSocketHandlers

▸ **getWebSocketHandlers**(`this`, `keysFilter?`): `Promise`<`Record`<`string`, [`WSServer`](../classes/appium_types.WSServer.md)<typeof `WebSocket`, typeof `IncomingMessage`\>\>\>

Returns web socket handlers registered for the given server
instance.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `this` | [`AppiumServer`](../modules/appium_types.md#appiumserver) | - |
| `keysFilter?` | ``null`` \| `string` | Only include pathnames with given value if set. All pairs will be included by default. |

#### Returns

`Promise`<`Record`<`string`, [`WSServer`](../classes/appium_types.WSServer.md)<typeof `WebSocket`, typeof `IncomingMessage`\>\>\>

Pathnames to WS server instances mapping matching the search criteria, if any found.

#### Defined in

@appium/types/lib/server.ts:43

___

### removeAllWebSocketHandlers

▸ **removeAllWebSocketHandlers**(`this`): `Promise`<`boolean`\>

Removes all existing WebSocket handlers from the server instance.

#### Parameters

| Name | Type |
| :------ | :------ |
| `this` | [`AppiumServer`](../modules/appium_types.md#appiumserver) |

#### Returns

`Promise`<`boolean`\>

`true` if at least one handler was deleted; `false` otherwise.

#### Defined in

@appium/types/lib/server.ts:36

___

### removeWebSocketHandler

▸ **removeWebSocketHandler**(`this`, `handlerPathname`): `Promise`<`boolean`\>

Removes existing WebSocket handler from the server instance.

The call is ignored if the given `handlerPathname` handler is not present in the handlers list.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `this` | [`AppiumServer`](../modules/appium_types.md#appiumserver) | - |
| `handlerPathname` | `string` | WebSocket endpoint path |

#### Returns

`Promise`<`boolean`\>

`true` if the `handlerPathname` was found and deleted; `false` otherwise.

#### Defined in

@appium/types/lib/server.ts:31
