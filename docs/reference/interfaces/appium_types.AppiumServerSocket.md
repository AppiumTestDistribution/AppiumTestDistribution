# Interface: AppiumServerSocket

[@appium/types](../modules/appium_types.md).AppiumServerSocket

## Hierarchy

- `Socket`

  ↳ **`AppiumServerSocket`**

## Table of contents

### Properties

- [\_openReqCount](appium_types.AppiumServerSocket.md#_openreqcount)
- [allowHalfOpen](appium_types.AppiumServerSocket.md#allowhalfopen)
- [bufferSize](appium_types.AppiumServerSocket.md#buffersize)
- [bytesRead](appium_types.AppiumServerSocket.md#bytesread)
- [bytesWritten](appium_types.AppiumServerSocket.md#byteswritten)
- [closed](appium_types.AppiumServerSocket.md#closed)
- [connecting](appium_types.AppiumServerSocket.md#connecting)
- [destroyed](appium_types.AppiumServerSocket.md#destroyed)
- [errored](appium_types.AppiumServerSocket.md#errored)
- [localAddress](appium_types.AppiumServerSocket.md#localaddress)
- [localFamily](appium_types.AppiumServerSocket.md#localfamily)
- [localPort](appium_types.AppiumServerSocket.md#localport)
- [pending](appium_types.AppiumServerSocket.md#pending)
- [readable](appium_types.AppiumServerSocket.md#readable)
- [readableAborted](appium_types.AppiumServerSocket.md#readableaborted)
- [readableDidRead](appium_types.AppiumServerSocket.md#readabledidread)
- [readableEncoding](appium_types.AppiumServerSocket.md#readableencoding)
- [readableEnded](appium_types.AppiumServerSocket.md#readableended)
- [readableFlowing](appium_types.AppiumServerSocket.md#readableflowing)
- [readableHighWaterMark](appium_types.AppiumServerSocket.md#readablehighwatermark)
- [readableLength](appium_types.AppiumServerSocket.md#readablelength)
- [readableObjectMode](appium_types.AppiumServerSocket.md#readableobjectmode)
- [readyState](appium_types.AppiumServerSocket.md#readystate)
- [remoteAddress](appium_types.AppiumServerSocket.md#remoteaddress)
- [remoteFamily](appium_types.AppiumServerSocket.md#remotefamily)
- [remotePort](appium_types.AppiumServerSocket.md#remoteport)
- [timeout](appium_types.AppiumServerSocket.md#timeout)
- [writable](appium_types.AppiumServerSocket.md#writable)
- [writableCorked](appium_types.AppiumServerSocket.md#writablecorked)
- [writableEnded](appium_types.AppiumServerSocket.md#writableended)
- [writableFinished](appium_types.AppiumServerSocket.md#writablefinished)
- [writableHighWaterMark](appium_types.AppiumServerSocket.md#writablehighwatermark)
- [writableLength](appium_types.AppiumServerSocket.md#writablelength)
- [writableNeedDrain](appium_types.AppiumServerSocket.md#writableneeddrain)
- [writableObjectMode](appium_types.AppiumServerSocket.md#writableobjectmode)

### Methods

- [[asyncIterator]](appium_types.AppiumServerSocket.md#[asynciterator])
- [\_construct](appium_types.AppiumServerSocket.md#_construct)
- [\_destroy](appium_types.AppiumServerSocket.md#_destroy)
- [\_final](appium_types.AppiumServerSocket.md#_final)
- [\_read](appium_types.AppiumServerSocket.md#_read)
- [\_write](appium_types.AppiumServerSocket.md#_write)
- [\_writev](appium_types.AppiumServerSocket.md#_writev)
- [addListener](appium_types.AppiumServerSocket.md#addlistener)
- [address](appium_types.AppiumServerSocket.md#address)
- [connect](appium_types.AppiumServerSocket.md#connect)
- [cork](appium_types.AppiumServerSocket.md#cork)
- [destroy](appium_types.AppiumServerSocket.md#destroy)
- [emit](appium_types.AppiumServerSocket.md#emit)
- [end](appium_types.AppiumServerSocket.md#end)
- [eventNames](appium_types.AppiumServerSocket.md#eventnames)
- [getMaxListeners](appium_types.AppiumServerSocket.md#getmaxlisteners)
- [isPaused](appium_types.AppiumServerSocket.md#ispaused)
- [listenerCount](appium_types.AppiumServerSocket.md#listenercount)
- [listeners](appium_types.AppiumServerSocket.md#listeners)
- [off](appium_types.AppiumServerSocket.md#off)
- [on](appium_types.AppiumServerSocket.md#on)
- [once](appium_types.AppiumServerSocket.md#once)
- [pause](appium_types.AppiumServerSocket.md#pause)
- [pipe](appium_types.AppiumServerSocket.md#pipe)
- [prependListener](appium_types.AppiumServerSocket.md#prependlistener)
- [prependOnceListener](appium_types.AppiumServerSocket.md#prependoncelistener)
- [push](appium_types.AppiumServerSocket.md#push)
- [rawListeners](appium_types.AppiumServerSocket.md#rawlisteners)
- [read](appium_types.AppiumServerSocket.md#read)
- [ref](appium_types.AppiumServerSocket.md#ref)
- [removeAllListeners](appium_types.AppiumServerSocket.md#removealllisteners)
- [removeListener](appium_types.AppiumServerSocket.md#removelistener)
- [resetAndDestroy](appium_types.AppiumServerSocket.md#resetanddestroy)
- [resume](appium_types.AppiumServerSocket.md#resume)
- [setDefaultEncoding](appium_types.AppiumServerSocket.md#setdefaultencoding)
- [setEncoding](appium_types.AppiumServerSocket.md#setencoding)
- [setKeepAlive](appium_types.AppiumServerSocket.md#setkeepalive)
- [setMaxListeners](appium_types.AppiumServerSocket.md#setmaxlisteners)
- [setNoDelay](appium_types.AppiumServerSocket.md#setnodelay)
- [setTimeout](appium_types.AppiumServerSocket.md#settimeout)
- [uncork](appium_types.AppiumServerSocket.md#uncork)
- [unpipe](appium_types.AppiumServerSocket.md#unpipe)
- [unref](appium_types.AppiumServerSocket.md#unref)
- [unshift](appium_types.AppiumServerSocket.md#unshift)
- [wrap](appium_types.AppiumServerSocket.md#wrap)
- [write](appium_types.AppiumServerSocket.md#write)

## Properties

### \_openReqCount

• **\_openReqCount**: `number`

#### Defined in

@appium/types/lib/server.ts:51

___

### allowHalfOpen

• **allowHalfOpen**: `boolean`

If `false` then the stream will automatically end the writable side when the
readable side ends. Set initially by the `allowHalfOpen` constructor option,
which defaults to `true`.

This can be changed manually to change the half-open behavior of an existing`Duplex` stream instance, but must be changed before the `'end'` event is
emitted.

**`Since`**

v0.9.4

#### Inherited from

Socket.allowHalfOpen

#### Defined in

@types/node/stream.d.ts:881

___

### bufferSize

• `Readonly` **bufferSize**: `number`

This property shows the number of characters buffered for writing. The buffer
may contain strings whose length after encoding is not yet known. So this number
is only an approximation of the number of bytes in the buffer.

`net.Socket` has the property that `socket.write()` always works. This is to
help users get up and running quickly. The computer cannot always keep up
with the amount of data that is written to a socket. The network connection
simply might be too slow. Node.js will internally queue up the data written to a
socket and send it out over the wire when it is possible.

The consequence of this internal buffering is that memory may grow.
Users who experience large or growing `bufferSize` should attempt to
"throttle" the data flows in their program with `socket.pause()` and `socket.resume()`.

**`Since`**

v0.3.8

**`Deprecated`**

Since v14.6.0 - Use `writableLength` instead.

#### Inherited from

Socket.bufferSize

#### Defined in

@types/node/net.d.ts:250

___

### bytesRead

• `Readonly` **bytesRead**: `number`

The amount of received bytes.

**`Since`**

v0.5.3

#### Inherited from

Socket.bytesRead

#### Defined in

@types/node/net.d.ts:255

___

### bytesWritten

• `Readonly` **bytesWritten**: `number`

The amount of bytes sent.

**`Since`**

v0.5.3

#### Inherited from

Socket.bytesWritten

#### Defined in

@types/node/net.d.ts:260

___

### closed

• `Readonly` **closed**: `boolean`

#### Inherited from

Socket.closed

#### Defined in

@types/node/stream.d.ts:870

___

### connecting

• `Readonly` **connecting**: `boolean`

If `true`,`socket.connect(options[, connectListener])` was
called and has not yet finished. It will stay `true` until the socket becomes
connected, then it is set to `false` and the `'connect'` event is emitted. Note
that the `socket.connect(options[, connectListener])` callback is a listener for the `'connect'` event.

**`Since`**

v6.1.0

#### Inherited from

Socket.connecting

#### Defined in

@types/node/net.d.ts:268

___

### destroyed

• `Readonly` **destroyed**: `boolean`

See `writable.destroyed` for further details.

#### Inherited from

Socket.destroyed

#### Defined in

@types/node/net.d.ts:278

___

### errored

• `Readonly` **errored**: ``null`` \| `Error`

#### Inherited from

Socket.errored

#### Defined in

@types/node/stream.d.ts:871

___

### localAddress

• `Optional` `Readonly` **localAddress**: `string`

The string representation of the local IP address the remote client is
connecting on. For example, in a server listening on `'0.0.0.0'`, if a client
connects on `'192.168.1.1'`, the value of `socket.localAddress` would be`'192.168.1.1'`.

**`Since`**

v0.9.6

#### Inherited from

Socket.localAddress

#### Defined in

@types/node/net.d.ts:285

___

### localFamily

• `Optional` `Readonly` **localFamily**: `string`

The string representation of the local IP family. `'IPv4'` or `'IPv6'`.

**`Since`**

v18.8.0, v16.18.0

#### Inherited from

Socket.localFamily

#### Defined in

@types/node/net.d.ts:295

___

### localPort

• `Optional` `Readonly` **localPort**: `number`

The numeric representation of the local port. For example, `80` or `21`.

**`Since`**

v0.9.6

#### Inherited from

Socket.localPort

#### Defined in

@types/node/net.d.ts:290

___

### pending

• `Readonly` **pending**: `boolean`

This is `true` if the socket is not connected yet, either because `.connect()`has not yet been called or because it is still in the process of connecting
(see `socket.connecting`).

**`Since`**

v11.2.0, v10.16.0

#### Inherited from

Socket.pending

#### Defined in

@types/node/net.d.ts:274

___

### readable

• **readable**: `boolean`

Is `true` if it is safe to call `readable.read()`, which means
the stream has not been destroyed or emitted `'error'` or `'end'`.

**`Since`**

v11.4.0

#### Inherited from

Socket.readable

#### Defined in

@types/node/stream.d.ts:57

___

### readableAborted

• `Readonly` **readableAborted**: `boolean`

Returns whether the stream was destroyed or errored before emitting `'end'`.

**`Since`**

v16.8.0

#### Inherited from

Socket.readableAborted

#### Defined in

@types/node/stream.d.ts:51

___

### readableDidRead

• `Readonly` **readableDidRead**: `boolean`

Returns whether `'data'` has been emitted.

**`Since`**

v16.7.0, v14.18.0

#### Inherited from

Socket.readableDidRead

#### Defined in

@types/node/stream.d.ts:63

___

### readableEncoding

• `Readonly` **readableEncoding**: ``null`` \| `BufferEncoding`

Getter for the property `encoding` of a given `Readable` stream. The `encoding`property can be set using the `readable.setEncoding()` method.

**`Since`**

v12.7.0

#### Inherited from

Socket.readableEncoding

#### Defined in

@types/node/stream.d.ts:68

___

### readableEnded

• `Readonly` **readableEnded**: `boolean`

Becomes `true` when `'end'` event is emitted.

**`Since`**

v12.9.0

#### Inherited from

Socket.readableEnded

#### Defined in

@types/node/stream.d.ts:73

___

### readableFlowing

• `Readonly` **readableFlowing**: ``null`` \| `boolean`

This property reflects the current state of a `Readable` stream as described
in the `Three states` section.

**`Since`**

v9.4.0

#### Inherited from

Socket.readableFlowing

#### Defined in

@types/node/stream.d.ts:79

___

### readableHighWaterMark

• `Readonly` **readableHighWaterMark**: `number`

Returns the value of `highWaterMark` passed when creating this `Readable`.

**`Since`**

v9.3.0

#### Inherited from

Socket.readableHighWaterMark

#### Defined in

@types/node/stream.d.ts:84

___

### readableLength

• `Readonly` **readableLength**: `number`

This property contains the number of bytes (or objects) in the queue
ready to be read. The value provides introspection data regarding
the status of the `highWaterMark`.

**`Since`**

v9.4.0

#### Inherited from

Socket.readableLength

#### Defined in

@types/node/stream.d.ts:91

___

### readableObjectMode

• `Readonly` **readableObjectMode**: `boolean`

Getter for the property `objectMode` of a given `Readable` stream.

**`Since`**

v12.3.0

#### Inherited from

Socket.readableObjectMode

#### Defined in

@types/node/stream.d.ts:96

___

### readyState

• `Readonly` **readyState**: `SocketReadyState`

This property represents the state of the connection as a string.

* If the stream is connecting `socket.readyState` is `opening`.
* If the stream is readable and writable, it is `open`.
* If the stream is readable and not writable, it is `readOnly`.
* If the stream is not readable and writable, it is `writeOnly`.

**`Since`**

v0.5.0

#### Inherited from

Socket.readyState

#### Defined in

@types/node/net.d.ts:305

___

### remoteAddress

• `Optional` `Readonly` **remoteAddress**: `string`

The string representation of the remote IP address. For example,`'74.125.127.100'` or `'2001:4860:a005::68'`. Value may be `undefined` if
the socket is destroyed (for example, if the client disconnected).

**`Since`**

v0.5.10

#### Inherited from

Socket.remoteAddress

#### Defined in

@types/node/net.d.ts:311

___

### remoteFamily

• `Optional` `Readonly` **remoteFamily**: `string`

The string representation of the remote IP family. `'IPv4'` or `'IPv6'`. Value may be `undefined` if
the socket is destroyed (for example, if the client disconnected).

**`Since`**

v0.11.14

#### Inherited from

Socket.remoteFamily

#### Defined in

@types/node/net.d.ts:317

___

### remotePort

• `Optional` `Readonly` **remotePort**: `number`

The numeric representation of the remote port. For example, `80` or `21`. Value may be `undefined` if
the socket is destroyed (for example, if the client disconnected).

**`Since`**

v0.5.10

#### Inherited from

Socket.remotePort

#### Defined in

@types/node/net.d.ts:323

___

### timeout

• `Optional` `Readonly` **timeout**: `number`

The socket timeout in milliseconds as set by `socket.setTimeout()`.
It is `undefined` if a timeout has not been set.

**`Since`**

v10.7.0

#### Inherited from

Socket.timeout

#### Defined in

@types/node/net.d.ts:329

___

### writable

• `Readonly` **writable**: `boolean`

#### Inherited from

Socket.writable

#### Defined in

@types/node/stream.d.ts:862

___

### writableCorked

• `Readonly` **writableCorked**: `number`

#### Inherited from

Socket.writableCorked

#### Defined in

@types/node/stream.d.ts:868

___

### writableEnded

• `Readonly` **writableEnded**: `boolean`

#### Inherited from

Socket.writableEnded

#### Defined in

@types/node/stream.d.ts:863

___

### writableFinished

• `Readonly` **writableFinished**: `boolean`

#### Inherited from

Socket.writableFinished

#### Defined in

@types/node/stream.d.ts:864

___

### writableHighWaterMark

• `Readonly` **writableHighWaterMark**: `number`

#### Inherited from

Socket.writableHighWaterMark

#### Defined in

@types/node/stream.d.ts:865

___

### writableLength

• `Readonly` **writableLength**: `number`

#### Inherited from

Socket.writableLength

#### Defined in

@types/node/stream.d.ts:866

___

### writableNeedDrain

• `Readonly` **writableNeedDrain**: `boolean`

#### Inherited from

Socket.writableNeedDrain

#### Defined in

@types/node/stream.d.ts:869

___

### writableObjectMode

• `Readonly` **writableObjectMode**: `boolean`

#### Inherited from

Socket.writableObjectMode

#### Defined in

@types/node/stream.d.ts:867

## Methods

### [asyncIterator]

▸ **[asyncIterator]**(): `AsyncIterableIterator`<`any`\>

#### Returns

`AsyncIterableIterator`<`any`\>

#### Inherited from

Socket.\_\_@asyncIterator@15437

#### Defined in

@types/node/stream.d.ts:475

___

### \_construct

▸ `Optional` **_construct**(`callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `callback` | (`error?`: ``null`` \| `Error`) => `void` |

#### Returns

`void`

#### Inherited from

Socket.\_construct

#### Defined in

@types/node/stream.d.ts:113

___

### \_destroy

▸ **_destroy**(`error`, `callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `error` | ``null`` \| `Error` |
| `callback` | (`error`: ``null`` \| `Error`) => `void` |

#### Returns

`void`

#### Inherited from

Socket.\_destroy

#### Defined in

@types/node/stream.d.ts:913

___

### \_final

▸ **_final**(`callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `callback` | (`error?`: ``null`` \| `Error`) => `void` |

#### Returns

`void`

#### Inherited from

Socket.\_final

#### Defined in

@types/node/stream.d.ts:914

___

### \_read

▸ **_read**(`size`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `size` | `number` |

#### Returns

`void`

#### Inherited from

Socket.\_read

#### Defined in

@types/node/stream.d.ts:114

___

### \_write

▸ **_write**(`chunk`, `encoding`, `callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `chunk` | `any` |
| `encoding` | `BufferEncoding` |
| `callback` | (`error?`: ``null`` \| `Error`) => `void` |

#### Returns

`void`

#### Inherited from

Socket.\_write

#### Defined in

@types/node/stream.d.ts:905

___

### \_writev

▸ `Optional` **_writev**(`chunks`, `callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `chunks` | { `chunk`: `any` ; `encoding`: `BufferEncoding`  }[] |
| `callback` | (`error?`: ``null`` \| `Error`) => `void` |

#### Returns

`void`

#### Inherited from

Socket.\_writev

#### Defined in

@types/node/stream.d.ts:906

___

### addListener

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

events.EventEmitter
  1. close
  2. connect
  3. data
  4. drain
  5. end
  6. error
  7. lookup
  8. ready
  9. timeout

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:355

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | (`hadError`: `boolean`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:356

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:357

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`data`: `Buffer`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:358

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:359

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:360

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:361

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `listener` | (`err`: `Error`, `address`: `string`, `family`: `string` \| `number`, `host`: `string`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:362

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:363

▸ **addListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.addListener

#### Defined in

@types/node/net.d.ts:364

___

### address

▸ **address**(): {} \| `AddressInfo`

Returns the bound `address`, the address `family` name and `port` of the
socket as reported by the operating system:`{ port: 12346, family: 'IPv4', address: '127.0.0.1' }`

**`Since`**

v0.1.90

#### Returns

{} \| `AddressInfo`

#### Inherited from

Socket.address

#### Defined in

@types/node/net.d.ts:218

___

### connect

▸ **connect**(`options`, `connectionListener?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Initiate a connection on a given socket.

Possible signatures:

* `socket.connect(options[, connectListener])`
* `socket.connect(path[, connectListener])` for `IPC` connections.
* `socket.connect(port[, host][, connectListener])` for TCP connections.
* Returns: `net.Socket` The socket itself.

This function is asynchronous. When the connection is established, the `'connect'` event will be emitted. If there is a problem connecting,
instead of a `'connect'` event, an `'error'` event will be emitted with
the error passed to the `'error'` listener.
The last parameter `connectListener`, if supplied, will be added as a listener
for the `'connect'` event **once**.

This function should only be used for reconnecting a socket after`'close'` has been emitted or otherwise it may lead to undefined
behavior.

#### Parameters

| Name | Type |
| :------ | :------ |
| `options` | `SocketConnectOpts` |
| `connectionListener?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.connect

#### Defined in

@types/node/net.d.ts:126

▸ **connect**(`port`, `host`, `connectionListener?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `port` | `number` |
| `host` | `string` |
| `connectionListener?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.connect

#### Defined in

@types/node/net.d.ts:127

▸ **connect**(`port`, `connectionListener?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `port` | `number` |
| `connectionListener?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.connect

#### Defined in

@types/node/net.d.ts:128

▸ **connect**(`path`, `connectionListener?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `path` | `string` |
| `connectionListener?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.connect

#### Defined in

@types/node/net.d.ts:129

___

### cork

▸ **cork**(): `void`

#### Returns

`void`

#### Inherited from

Socket.cork

#### Defined in

@types/node/stream.d.ts:921

___

### destroy

▸ **destroy**(`error?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Destroy the stream. Optionally emit an `'error'` event, and emit a `'close'`event (unless `emitClose` is set to `false`). After this call, the readable
stream will release any internal resources and subsequent calls to `push()`will be ignored.

Once `destroy()` has been called any further calls will be a no-op and no
further errors except from `_destroy()` may be emitted as `'error'`.

Implementors should not override this method, but instead implement `readable._destroy()`.

**`Since`**

v8.0.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `error?` | `Error` | Error which will be passed as payload in `'error'` event |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.destroy

#### Defined in

@types/node/stream.d.ts:407

___

### emit

▸ **emit**(`event`, `...args`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `...args` | `any`[] |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:365

▸ **emit**(`event`, `hadError`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `hadError` | `boolean` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:366

▸ **emit**(`event`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:367

▸ **emit**(`event`, `data`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `data` | `Buffer` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:368

▸ **emit**(`event`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:369

▸ **emit**(`event`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:370

▸ **emit**(`event`, `err`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `err` | `Error` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:371

▸ **emit**(`event`, `err`, `address`, `family`, `host`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `err` | `Error` |
| `address` | `string` |
| `family` | `string` \| `number` |
| `host` | `string` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:372

▸ **emit**(`event`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:373

▸ **emit**(`event`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |

#### Returns

`boolean`

#### Inherited from

Socket.emit

#### Defined in

@types/node/net.d.ts:374

___

### end

▸ **end**(`callback?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Half-closes the socket. i.e., it sends a FIN packet. It is possible the
server will still send some data.

See `writable.end()` for further details.

**`Since`**

v0.1.90

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `callback?` | () => `void` | Optional callback for when the socket is finished. |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.end

#### Defined in

@types/node/net.d.ts:340

▸ **end**(`buffer`, `callback?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `buffer` | `string` \| `Uint8Array` |
| `callback?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.end

#### Defined in

@types/node/net.d.ts:341

▸ **end**(`str`, `encoding?`, `callback?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `str` | `string` \| `Uint8Array` |
| `encoding?` | `BufferEncoding` |
| `callback?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.end

#### Defined in

@types/node/net.d.ts:342

___

### eventNames

▸ **eventNames**(): (`string` \| `symbol`)[]

Returns an array listing the events for which the emitter has registered
listeners. The values in the array are strings or `Symbol`s.

```js
import { EventEmitter } from 'node:events';

const myEE = new EventEmitter();
myEE.on('foo', () => {});
myEE.on('bar', () => {});

const sym = Symbol('symbol');
myEE.on(sym, () => {});

console.log(myEE.eventNames());
// Prints: [ 'foo', 'bar', Symbol(symbol) ]
```

**`Since`**

v6.0.0

#### Returns

(`string` \| `symbol`)[]

#### Inherited from

Socket.eventNames

#### Defined in

@types/node/events.d.ts:715

___

### getMaxListeners

▸ **getMaxListeners**(): `number`

Returns the current max listener value for the `EventEmitter` which is either
set by `emitter.setMaxListeners(n)` or defaults to defaultMaxListeners.

**`Since`**

v1.0.0

#### Returns

`number`

#### Inherited from

Socket.getMaxListeners

#### Defined in

@types/node/events.d.ts:567

___

### isPaused

▸ **isPaused**(): `boolean`

The `readable.isPaused()` method returns the current operating state of the`Readable`. This is used primarily by the mechanism that underlies the`readable.pipe()` method. In most
typical cases, there will be no reason to
use this method directly.

```js
const readable = new stream.Readable();

readable.isPaused(); // === false
readable.pause();
readable.isPaused(); // === true
readable.resume();
readable.isPaused(); // === false
```

**`Since`**

v0.11.14

#### Returns

`boolean`

#### Inherited from

Socket.isPaused

#### Defined in

@types/node/stream.d.ts:274

___

### listenerCount

▸ **listenerCount**(`eventName`, `listener?`): `number`

Returns the number of listeners listening for the event named `eventName`.
If `listener` is provided, it will return how many times the listener is found
in the list of the listeners of the event.

**`Since`**

v3.2.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `eventName` | `string` \| `symbol` | The name of the event being listened for |
| `listener?` | `Function` | The event handler function |

#### Returns

`number`

#### Inherited from

Socket.listenerCount

#### Defined in

@types/node/events.d.ts:661

___

### listeners

▸ **listeners**(`eventName`): `Function`[]

Returns a copy of the array of listeners for the event named `eventName`.

```js
server.on('connection', (stream) => {
  console.log('someone connected!');
});
console.log(util.inspect(server.listeners('connection')));
// Prints: [ [Function] ]
```

**`Since`**

v0.1.26

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` \| `symbol` |

#### Returns

`Function`[]

#### Inherited from

Socket.listeners

#### Defined in

@types/node/events.d.ts:580

___

### off

▸ **off**(`eventName`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Alias for `emitter.removeListener()`.

**`Since`**

v10.0.0

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` \| `symbol` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.off

#### Defined in

@types/node/events.d.ts:540

___

### on

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:375

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | (`hadError`: `boolean`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:376

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:377

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`data`: `Buffer`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:378

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:379

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:380

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:381

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `listener` | (`err`: `Error`, `address`: `string`, `family`: `string` \| `number`, `host`: `string`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:382

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:383

▸ **on**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.on

#### Defined in

@types/node/net.d.ts:384

___

### once

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:385

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | (`hadError`: `boolean`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:386

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:387

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`data`: `Buffer`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:388

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:389

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:390

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:391

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `listener` | (`err`: `Error`, `address`: `string`, `family`: `string` \| `number`, `host`: `string`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:392

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:393

▸ **once**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.once

#### Defined in

@types/node/net.d.ts:394

___

### pause

▸ **pause**(): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Pauses the reading of data. That is, `'data'` events will not be emitted.
Useful to throttle back an upload.

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.pause

#### Defined in

@types/node/net.d.ts:141

___

### pipe

▸ **pipe**<`T`\>(`destination`, `options?`): `T`

#### Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends `WritableStream`<`T`\> |

#### Parameters

| Name | Type |
| :------ | :------ |
| `destination` | `T` |
| `options?` | `Object` |
| `options.end?` | `boolean` |

#### Returns

`T`

#### Inherited from

Socket.pipe

#### Defined in

@types/node/stream.d.ts:26

___

### prependListener

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:395

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | (`hadError`: `boolean`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:396

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:397

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`data`: `Buffer`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:398

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:399

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:400

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:401

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `listener` | (`err`: `Error`, `address`: `string`, `family`: `string` \| `number`, `host`: `string`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:402

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:403

▸ **prependListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependListener

#### Defined in

@types/node/net.d.ts:404

___

### prependOnceListener

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:405

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | (`hadError`: `boolean`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:406

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connect"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:407

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`data`: `Buffer`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:408

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:409

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:410

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:411

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"lookup"`` |
| `listener` | (`err`: `Error`, `address`: `string`, `family`: `string` \| `number`, `host`: `string`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:412

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"ready"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:413

▸ **prependOnceListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"timeout"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.prependOnceListener

#### Defined in

@types/node/net.d.ts:414

___

### push

▸ **push**(`chunk`, `encoding?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `chunk` | `any` |
| `encoding?` | `BufferEncoding` |

#### Returns

`boolean`

#### Inherited from

Socket.push

#### Defined in

@types/node/stream.d.ts:394

___

### rawListeners

▸ **rawListeners**(`eventName`): `Function`[]

Returns a copy of the array of listeners for the event named `eventName`,
including any wrappers (such as those created by `.once()`).

```js
import { EventEmitter } from 'node:events';
const emitter = new EventEmitter();
emitter.once('log', () => console.log('log once'));

// Returns a new Array with a function `onceWrapper` which has a property
// `listener` which contains the original listener bound above
const listeners = emitter.rawListeners('log');
const logFnWrapper = listeners[0];

// Logs "log once" to the console and does not unbind the `once` event
logFnWrapper.listener();

// Logs "log once" to the console and removes the listener
logFnWrapper();

emitter.on('log', () => console.log('log persistently'));
// Will return a new Array with a single function bound by `.on()` above
const newListeners = emitter.rawListeners('log');

// Logs "log persistently" twice
newListeners[0]();
emitter.emit('log');
```

**`Since`**

v9.4.0

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` \| `symbol` |

#### Returns

`Function`[]

#### Inherited from

Socket.rawListeners

#### Defined in

@types/node/events.d.ts:611

___

### read

▸ **read**(`size?`): `any`

The `readable.read()` method reads data out of the internal buffer and
returns it. If no data is available to be read, `null` is returned. By default,
the data is returned as a `Buffer` object unless an encoding has been
specified using the `readable.setEncoding()` method or the stream is operating
in object mode.

The optional `size` argument specifies a specific number of bytes to read. If`size` bytes are not available to be read, `null` will be returned _unless_the stream has ended, in which
case all of the data remaining in the internal
buffer will be returned.

If the `size` argument is not specified, all of the data contained in the
internal buffer will be returned.

The `size` argument must be less than or equal to 1 GiB.

The `readable.read()` method should only be called on `Readable` streams
operating in paused mode. In flowing mode, `readable.read()` is called
automatically until the internal buffer is fully drained.

```js
const readable = getReadableStreamSomehow();

// 'readable' may be triggered multiple times as data is buffered in
readable.on('readable', () => {
  let chunk;
  console.log('Stream is readable (new data received in buffer)');
  // Use a loop to make sure we read all currently available data
  while (null !== (chunk = readable.read())) {
    console.log(`Read ${chunk.length} bytes of data...`);
  }
});

// 'end' will be triggered once when there is no more data available
readable.on('end', () => {
  console.log('Reached end of stream.');
});
```

Each call to `readable.read()` returns a chunk of data, or `null`. The chunks
are not concatenated. A `while` loop is necessary to consume all data
currently in the buffer. When reading a large file `.read()` may return `null`,
having consumed all buffered content so far, but there is still more data to
come not yet buffered. In this case a new `'readable'` event will be emitted
when there is more data in the buffer. Finally the `'end'` event will be
emitted when there is no more data to come.

Therefore to read a file's whole contents from a `readable`, it is necessary
to collect chunks across multiple `'readable'` events:

```js
const chunks = [];

readable.on('readable', () => {
  let chunk;
  while (null !== (chunk = readable.read())) {
    chunks.push(chunk);
  }
});

readable.on('end', () => {
  const content = chunks.join('');
});
```

A `Readable` stream in object mode will always return a single item from
a call to `readable.read(size)`, regardless of the value of the`size` argument.

If the `readable.read()` method returns a chunk of data, a `'data'` event will
also be emitted.

Calling [read](appium_types.AppiumServerSocket.md#read) after the `'end'` event has
been emitted will return `null`. No runtime error will be raised.

**`Since`**

v0.9.4

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `size?` | `number` | Optional argument to specify how much data to read. |

#### Returns

`any`

#### Inherited from

Socket.read

#### Defined in

@types/node/stream.d.ts:191

___

### ref

▸ **ref**(): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Opposite of `unref()`, calling `ref()` on a previously `unref`ed socket will _not_ let the program exit if it's the only socket left (the default behavior).
If the socket is `ref`ed calling `ref` again will have no effect.

**`Since`**

v0.9.1

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.ref

#### Defined in

@types/node/net.d.ts:232

___

### removeAllListeners

▸ **removeAllListeners**(`event?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Removes all listeners, or those of the specified `eventName`.

It is bad practice to remove listeners added elsewhere in the code,
particularly when the `EventEmitter` instance was created by some other
component or module (e.g. sockets or file streams).

Returns a reference to the `EventEmitter`, so that calls can be chained.

**`Since`**

v0.1.26

#### Parameters

| Name | Type |
| :------ | :------ |
| `event?` | `string` \| `symbol` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeAllListeners

#### Defined in

@types/node/events.d.ts:551

___

### removeListener

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1031

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"data"`` |
| `listener` | (`chunk`: `any`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1032

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"drain"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1033

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"end"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1034

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `listener` | (`err`: `Error`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1035

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"finish"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1036

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"pause"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1037

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"pipe"`` |
| `listener` | (`src`: `Readable`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1038

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"readable"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1039

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"resume"`` |
| `listener` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1040

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"unpipe"`` |
| `listener` | (`src`: `Readable`) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1041

▸ **removeListener**(`event`, `listener`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.removeListener

#### Defined in

@types/node/stream.d.ts:1042

___

### resetAndDestroy

▸ **resetAndDestroy**(): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Close the TCP connection by sending an RST packet and destroy the stream.
If this TCP socket is in connecting status, it will send an RST packet and destroy this TCP socket once it is connected.
Otherwise, it will call `socket.destroy` with an `ERR_SOCKET_CLOSED` Error.
If this is not a TCP socket (for example, a pipe), calling this method will immediately throw an `ERR_INVALID_HANDLE_TYPE` Error.

**`Since`**

v18.3.0, v16.17.0

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.resetAndDestroy

#### Defined in

@types/node/net.d.ts:149

___

### resume

▸ **resume**(): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Resumes reading after a call to `socket.pause()`.

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.resume

#### Defined in

@types/node/net.d.ts:154

___

### setDefaultEncoding

▸ **setDefaultEncoding**(`encoding`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Parameters

| Name | Type |
| :------ | :------ |
| `encoding` | `BufferEncoding` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.setDefaultEncoding

#### Defined in

@types/node/stream.d.ts:917

___

### setEncoding

▸ **setEncoding**(`encoding?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Set the encoding for the socket as a `Readable Stream`. See `readable.setEncoding()` for more information.

**`Since`**

v0.1.90

#### Parameters

| Name | Type |
| :------ | :------ |
| `encoding?` | `BufferEncoding` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.setEncoding

#### Defined in

@types/node/net.d.ts:135

___

### setKeepAlive

▸ **setKeepAlive**(`enable?`, `initialDelay?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Enable/disable keep-alive functionality, and optionally set the initial
delay before the first keepalive probe is sent on an idle socket.

Set `initialDelay` (in milliseconds) to set the delay between the last
data packet received and the first keepalive probe. Setting `0` for`initialDelay` will leave the value unchanged from the default
(or previous) setting.

Enabling the keep-alive functionality will set the following socket options:

* `SO_KEEPALIVE=1`
* `TCP_KEEPIDLE=initialDelay`
* `TCP_KEEPCNT=10`
* `TCP_KEEPINTVL=1`

**`Since`**

v0.1.92

#### Parameters

| Name | Type |
| :------ | :------ |
| `enable?` | `boolean` |
| `initialDelay?` | `number` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.setKeepAlive

#### Defined in

@types/node/net.d.ts:212

___

### setMaxListeners

▸ **setMaxListeners**(`n`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

By default `EventEmitter`s will print a warning if more than `10` listeners are
added for a particular event. This is a useful default that helps finding
memory leaks. The `emitter.setMaxListeners()` method allows the limit to be
modified for this specific `EventEmitter` instance. The value can be set to`Infinity` (or `0`) to indicate an unlimited number of listeners.

Returns a reference to the `EventEmitter`, so that calls can be chained.

**`Since`**

v0.3.5

#### Parameters

| Name | Type |
| :------ | :------ |
| `n` | `number` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.setMaxListeners

#### Defined in

@types/node/events.d.ts:561

___

### setNoDelay

▸ **setNoDelay**(`noDelay?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Enable/disable the use of Nagle's algorithm.

When a TCP connection is created, it will have Nagle's algorithm enabled.

Nagle's algorithm delays data before it is sent via the network. It attempts
to optimize throughput at the expense of latency.

Passing `true` for `noDelay` or not passing an argument will disable Nagle's
algorithm for the socket. Passing `false` for `noDelay` will enable Nagle's
algorithm.

**`Since`**

v0.1.90

#### Parameters

| Name | Type |
| :------ | :------ |
| `noDelay?` | `boolean` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.setNoDelay

#### Defined in

@types/node/net.d.ts:192

___

### setTimeout

▸ **setTimeout**(`timeout`, `callback?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Sets the socket to timeout after `timeout` milliseconds of inactivity on
the socket. By default `net.Socket` do not have a timeout.

When an idle timeout is triggered the socket will receive a `'timeout'` event but the connection will not be severed. The user must manually call `socket.end()` or `socket.destroy()` to
end the connection.

```js
socket.setTimeout(3000);
socket.on('timeout', () => {
  console.log('socket timeout');
  socket.end();
});
```

If `timeout` is 0, then the existing idle timeout is disabled.

The optional `callback` parameter will be added as a one-time listener for the `'timeout'` event.

**`Since`**

v0.1.90

#### Parameters

| Name | Type |
| :------ | :------ |
| `timeout` | `number` |
| `callback?` | () => `void` |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.setTimeout

#### Defined in

@types/node/net.d.ts:176

___

### uncork

▸ **uncork**(): `void`

#### Returns

`void`

#### Inherited from

Socket.uncork

#### Defined in

@types/node/stream.d.ts:922

___

### unpipe

▸ **unpipe**(`destination?`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The `readable.unpipe()` method detaches a `Writable` stream previously attached
using the [pipe](appium_types.AppiumServerSocket.md#pipe) method.

If the `destination` is not specified, then _all_ pipes are detached.

If the `destination` is specified, but no pipe is set up for it, then
the method does nothing.

```js
const fs = require('node:fs');
const readable = getReadableStreamSomehow();
const writable = fs.createWriteStream('file.txt');
// All the data from readable goes into 'file.txt',
// but only for the first second.
readable.pipe(writable);
setTimeout(() => {
  console.log('Stop writing to file.txt.');
  readable.unpipe(writable);
  console.log('Manually close the file stream.');
  writable.end();
}, 1000);
```

**`Since`**

v0.9.4

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `destination?` | `WritableStream` | Optional specific stream to unpipe |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.unpipe

#### Defined in

@types/node/stream.d.ts:301

___

### unref

▸ **unref**(): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Calling `unref()` on a socket will allow the program to exit if this is the only
active socket in the event system. If the socket is already `unref`ed calling`unref()` again will have no effect.

**`Since`**

v0.9.1

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

The socket itself.

#### Inherited from

Socket.unref

#### Defined in

@types/node/net.d.ts:225

___

### unshift

▸ **unshift**(`chunk`, `encoding?`): `void`

Passing `chunk` as `null` signals the end of the stream (EOF) and behaves the
same as `readable.push(null)`, after which no more data can be written. The EOF
signal is put at the end of the buffer and any buffered data will still be
flushed.

The `readable.unshift()` method pushes a chunk of data back into the internal
buffer. This is useful in certain situations where a stream is being consumed by
code that needs to "un-consume" some amount of data that it has optimistically
pulled out of the source, so that the data can be passed on to some other party.

The `stream.unshift(chunk)` method cannot be called after the `'end'` event
has been emitted or a runtime error will be thrown.

Developers using `stream.unshift()` often should consider switching to
use of a `Transform` stream instead. See the `API for stream implementers` section for more information.

```js
// Pull off a header delimited by \n\n.
// Use unshift() if we get too much.
// Call the callback with (error, header, stream).
const { StringDecoder } = require('node:string_decoder');
function parseHeader(stream, callback) {
  stream.on('error', callback);
  stream.on('readable', onReadable);
  const decoder = new StringDecoder('utf8');
  let header = '';
  function onReadable() {
    let chunk;
    while (null !== (chunk = stream.read())) {
      const str = decoder.write(chunk);
      if (str.includes('\n\n')) {
        // Found the header boundary.
        const split = str.split(/\n\n/);
        header += split.shift();
        const remaining = split.join('\n\n');
        const buf = Buffer.from(remaining, 'utf8');
        stream.removeListener('error', callback);
        // Remove the 'readable' listener before unshifting.
        stream.removeListener('readable', onReadable);
        if (buf.length)
          stream.unshift(buf);
        // Now the body of the message can be read from the stream.
        callback(null, header, stream);
        return;
      }
      // Still reading the header.
      header += str;
    }
  }
}
```

Unlike [push](appium_types.AppiumServerSocket.md#push), `stream.unshift(chunk)` will not
end the reading process by resetting the internal reading state of the stream.
This can cause unexpected results if `readable.unshift()` is called during a
read (i.e. from within a [_read](appium_types.AppiumServerSocket.md#_read) implementation on a
custom stream). Following the call to `readable.unshift()` with an immediate [push](appium_types.AppiumServerSocket.md#push) will reset the reading state appropriately,
however it is best to simply avoid calling `readable.unshift()` while in the
process of performing a read.

**`Since`**

v0.9.11

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `chunk` | `any` | Chunk of data to unshift onto the read queue. For streams not operating in object mode, `chunk` must be a string, `Buffer`, `Uint8Array`, or `null`. For object mode streams, `chunk` may be any JavaScript value. |
| `encoding?` | `BufferEncoding` | Encoding of string chunks. Must be a valid `Buffer` encoding, such as `'utf8'` or `'ascii'`. |

#### Returns

`void`

#### Inherited from

Socket.unshift

#### Defined in

@types/node/stream.d.ts:367

___

### wrap

▸ **wrap**(`stream`): [`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

Prior to Node.js 0.10, streams did not implement the entire `node:stream`module API as it is currently defined. (See `Compatibility` for more
information.)

When using an older Node.js library that emits `'data'` events and has a [pause](appium_types.AppiumServerSocket.md#pause) method that is advisory only, the`readable.wrap()` method can be used to create a `Readable`
stream that uses
the old stream as its data source.

It will rarely be necessary to use `readable.wrap()` but the method has been
provided as a convenience for interacting with older Node.js applications and
libraries.

```js
const { OldReader } = require('./old-api-module.js');
const { Readable } = require('node:stream');
const oreader = new OldReader();
const myReader = new Readable().wrap(oreader);

myReader.on('readable', () => {
  myReader.read(); // etc.
});
```

**`Since`**

v0.9.4

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `stream` | `ReadableStream` | An "old style" readable stream |

#### Returns

[`AppiumServerSocket`](appium_types.AppiumServerSocket.md)

#### Inherited from

Socket.wrap

#### Defined in

@types/node/stream.d.ts:393

___

### write

▸ **write**(`buffer`, `cb?`): `boolean`

Sends data on the socket. The second parameter specifies the encoding in the
case of a string. It defaults to UTF8 encoding.

Returns `true` if the entire data was flushed successfully to the kernel
buffer. Returns `false` if all or part of the data was queued in user memory.`'drain'` will be emitted when the buffer is again free.

The optional `callback` parameter will be executed when the data is finally
written out, which may not be immediately.

See `Writable` stream `write()` method for more
information.

**`Since`**

v0.1.90

#### Parameters

| Name | Type |
| :------ | :------ |
| `buffer` | `string` \| `Uint8Array` |
| `cb?` | (`err?`: `Error`) => `void` |

#### Returns

`boolean`

#### Inherited from

Socket.write

#### Defined in

@types/node/net.d.ts:105

▸ **write**(`str`, `encoding?`, `cb?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `str` | `string` \| `Uint8Array` |
| `encoding?` | `BufferEncoding` |
| `cb?` | (`err?`: `Error`) => `void` |

#### Returns

`boolean`

#### Inherited from

Socket.write

#### Defined in

@types/node/net.d.ts:106
