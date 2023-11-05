# Class: WSServer<T, U\>

[@appium/types](../modules/appium_types.md).WSServer

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends typeof `WebSocket.WebSocket` = typeof `WebSocket.WebSocket` |
| `U` | extends typeof `IncomingMessage` = typeof `IncomingMessage` |

## Hierarchy

- `EventEmitter`

  ↳ **`WSServer`**

## Table of contents

### Constructors

- [constructor](appium_types.WSServer.md#constructor)

### Properties

- [clients](appium_types.WSServer.md#clients)
- [options](appium_types.WSServer.md#options)
- [path](appium_types.WSServer.md#path)
- [captureRejectionSymbol](appium_types.WSServer.md#capturerejectionsymbol)
- [captureRejections](appium_types.WSServer.md#capturerejections)
- [defaultMaxListeners](appium_types.WSServer.md#defaultmaxlisteners)
- [errorMonitor](appium_types.WSServer.md#errormonitor)

### Methods

- [addListener](appium_types.WSServer.md#addlistener)
- [address](appium_types.WSServer.md#address)
- [close](appium_types.WSServer.md#close)
- [emit](appium_types.WSServer.md#emit)
- [eventNames](appium_types.WSServer.md#eventnames)
- [getMaxListeners](appium_types.WSServer.md#getmaxlisteners)
- [handleUpgrade](appium_types.WSServer.md#handleupgrade)
- [listenerCount](appium_types.WSServer.md#listenercount)
- [listeners](appium_types.WSServer.md#listeners)
- [off](appium_types.WSServer.md#off)
- [on](appium_types.WSServer.md#on)
- [once](appium_types.WSServer.md#once)
- [prependListener](appium_types.WSServer.md#prependlistener)
- [prependOnceListener](appium_types.WSServer.md#prependoncelistener)
- [rawListeners](appium_types.WSServer.md#rawlisteners)
- [removeAllListeners](appium_types.WSServer.md#removealllisteners)
- [removeListener](appium_types.WSServer.md#removelistener)
- [setMaxListeners](appium_types.WSServer.md#setmaxlisteners)
- [shouldHandle](appium_types.WSServer.md#shouldhandle)
- [getEventListeners](appium_types.WSServer.md#geteventlisteners)
- [listenerCount](appium_types.WSServer.md#listenercount-1)
- [on](appium_types.WSServer.md#on-1)
- [once](appium_types.WSServer.md#once-1)
- [setMaxListeners](appium_types.WSServer.md#setmaxlisteners-1)

## Constructors

### constructor

• **new WSServer**<`T`, `U`\>(`options?`, `callback?`)

#### Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends typeof `WebSocket` = typeof `WebSocket` |
| `U` | extends typeof `IncomingMessage` = typeof `IncomingMessage` |

#### Parameters

| Name | Type |
| :------ | :------ |
| `options?` | `ServerOptions`<`T`, `U`\> |
| `callback?` | () => `void` |

#### Overrides

EventEmitter.constructor

#### Defined in

@types/ws/index.d.ts:351

## Properties

### clients

• **clients**: `Set`<`InstanceType`<`T`\>\>

#### Defined in

@types/ws/index.d.ts:349

___

### options

• **options**: `ServerOptions`<`T`, `U`\>

#### Defined in

@types/ws/index.d.ts:347

___

### path

• **path**: `string`

#### Defined in

@types/ws/index.d.ts:348

___

### captureRejectionSymbol

▪ `Static` `Readonly` **captureRejectionSymbol**: typeof [`captureRejectionSymbol`](appium_types.WSServer.md#capturerejectionsymbol)

Value: `Symbol.for('nodejs.rejection')`

See how to write a custom `rejection handler`.

**`Since`**

v13.4.0, v12.16.0

#### Inherited from

EventEmitter.captureRejectionSymbol

#### Defined in

@types/node/events.d.ts:326

___

### captureRejections

▪ `Static` **captureRejections**: `boolean`

Value: [boolean](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Data_structures#Boolean_type)

Change the default `captureRejections` option on all new `EventEmitter` objects.

**`Since`**

v13.4.0, v12.16.0

#### Inherited from

EventEmitter.captureRejections

#### Defined in

@types/node/events.d.ts:333

___

### defaultMaxListeners

▪ `Static` **defaultMaxListeners**: `number`

By default, a maximum of `10` listeners can be registered for any single
event. This limit can be changed for individual `EventEmitter` instances
using the `emitter.setMaxListeners(n)` method. To change the default
for _all_`EventEmitter` instances, the `events.defaultMaxListeners`property can be used. If this value is not a positive number, a `RangeError`is thrown.

Take caution when setting the `events.defaultMaxListeners` because the
change affects _all_`EventEmitter` instances, including those created before
the change is made. However, calling `emitter.setMaxListeners(n)` still has
precedence over `events.defaultMaxListeners`.

This is not a hard limit. The `EventEmitter` instance will allow
more listeners to be added but will output a trace warning to stderr indicating
that a "possible EventEmitter memory leak" has been detected. For any single`EventEmitter`, the `emitter.getMaxListeners()` and `emitter.setMaxListeners()`methods can be used to
temporarily avoid this warning:

```js
import { EventEmitter } from 'node:events';
const emitter = new EventEmitter();
emitter.setMaxListeners(emitter.getMaxListeners() + 1);
emitter.once('event', () => {
  // do stuff
  emitter.setMaxListeners(Math.max(emitter.getMaxListeners() - 1, 0));
});
```

The `--trace-warnings` command-line flag can be used to display the
stack trace for such warnings.

The emitted warning can be inspected with `process.on('warning')` and will
have the additional `emitter`, `type`, and `count` properties, referring to
the event emitter instance, the event's name and the number of attached
listeners, respectively.
Its `name` property is set to `'MaxListenersExceededWarning'`.

**`Since`**

v0.11.2

#### Inherited from

EventEmitter.defaultMaxListeners

#### Defined in

@types/node/events.d.ts:370

___

### errorMonitor

▪ `Static` `Readonly` **errorMonitor**: typeof [`errorMonitor`](appium_types.WSServer.md#errormonitor)

This symbol shall be used to install a listener for only monitoring `'error'`events. Listeners installed using this symbol are called before the regular`'error'` listeners are called.

Installing a listener using this symbol does not change the behavior once an`'error'` event is emitted. Therefore, the process will still crash if no
regular `'error'` listener is installed.

**`Since`**

v13.6.0, v12.17.0

#### Inherited from

EventEmitter.errorMonitor

#### Defined in

@types/node/events.d.ts:319

## Methods

### addListener

▸ **addListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connection"`` |
| `cb` | (`client`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.addListener

#### Defined in

@types/ws/index.d.ts:388

▸ **addListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `cb` | (`err`: `Error`) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.addListener

#### Defined in

@types/ws/index.d.ts:389

▸ **addListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"headers"`` |
| `cb` | (`headers`: `string`[], `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.addListener

#### Defined in

@types/ws/index.d.ts:390

▸ **addListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` \| ``"listening"`` |
| `cb` | () => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.addListener

#### Defined in

@types/ws/index.d.ts:391

▸ **addListener**(`event`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.addListener

#### Defined in

@types/ws/index.d.ts:392

___

### address

▸ **address**(): `string` \| `AddressInfo`

#### Returns

`string` \| `AddressInfo`

#### Defined in

@types/ws/index.d.ts:353

___

### close

▸ **close**(`cb?`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `cb?` | (`err?`: `Error`) => `void` |

#### Returns

`void`

#### Defined in

@types/ws/index.d.ts:354

___

### emit

▸ **emit**(`eventName`, `...args`): `boolean`

Synchronously calls each of the listeners registered for the event named`eventName`, in the order they were registered, passing the supplied arguments
to each.

Returns `true` if the event had listeners, `false` otherwise.

```js
import { EventEmitter } from 'node:events';
const myEmitter = new EventEmitter();

// First listener
myEmitter.on('event', function firstListener() {
  console.log('Helloooo! first listener');
});
// Second listener
myEmitter.on('event', function secondListener(arg1, arg2) {
  console.log(`event with parameters ${arg1}, ${arg2} in second listener`);
});
// Third listener
myEmitter.on('event', function thirdListener(...args) {
  const parameters = args.join(', ');
  console.log(`event with parameters ${parameters} in third listener`);
});

console.log(myEmitter.listeners('event'));

myEmitter.emit('event', 1, 2, 3, 4, 5);

// Prints:
// [
//   [Function: firstListener],
//   [Function: secondListener],
//   [Function: thirdListener]
// ]
// Helloooo! first listener
// event with parameters 1, 2 in second listener
// event with parameters 1, 2, 3, 4, 5 in third listener
```

**`Since`**

v0.1.26

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` \| `symbol` |
| `...args` | `any`[] |

#### Returns

`boolean`

#### Inherited from

EventEmitter.emit

#### Defined in

@types/node/events.d.ts:652

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

EventEmitter.eventNames

#### Defined in

@types/node/events.d.ts:715

___

### getMaxListeners

▸ **getMaxListeners**(): `number`

Returns the current max listener value for the `EventEmitter` which is either
set by `emitter.setMaxListeners(n)` or defaults to [defaultMaxListeners](appium_types.WSServer.md#defaultmaxlisteners).

**`Since`**

v1.0.0

#### Returns

`number`

#### Inherited from

EventEmitter.getMaxListeners

#### Defined in

@types/node/events.d.ts:567

___

### handleUpgrade

▸ **handleUpgrade**(`request`, `socket`, `upgradeHead`, `callback`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `request` | `InstanceType`<`U`\> |
| `socket` | `Duplex` |
| `upgradeHead` | `Buffer` |
| `callback` | (`client`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

`void`

#### Defined in

@types/ws/index.d.ts:355

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

EventEmitter.listenerCount

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

EventEmitter.listeners

#### Defined in

@types/node/events.d.ts:580

___

### off

▸ **off**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connection"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `socket`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.off

#### Defined in

@types/ws/index.d.ts:379

▸ **off**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `error`: `Error`) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.off

#### Defined in

@types/ws/index.d.ts:383

▸ **off**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"headers"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `headers`: `string`[], `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.off

#### Defined in

@types/ws/index.d.ts:384

▸ **off**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` \| ``"listening"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.off

#### Defined in

@types/ws/index.d.ts:385

▸ **off**(`event`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, ...`args`: `any`[]) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.off

#### Defined in

@types/ws/index.d.ts:386

___

### on

▸ **on**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connection"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `socket`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.on

#### Defined in

@types/ws/index.d.ts:364

▸ **on**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `error`: `Error`) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.on

#### Defined in

@types/ws/index.d.ts:365

▸ **on**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"headers"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `headers`: `string`[], `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.on

#### Defined in

@types/ws/index.d.ts:366

▸ **on**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` \| ``"listening"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.on

#### Defined in

@types/ws/index.d.ts:367

▸ **on**(`event`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, ...`args`: `any`[]) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.on

#### Defined in

@types/ws/index.d.ts:368

___

### once

▸ **once**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connection"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `socket`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.once

#### Defined in

@types/ws/index.d.ts:370

▸ **once**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `error`: `Error`) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.once

#### Defined in

@types/ws/index.d.ts:374

▸ **once**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"headers"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, `headers`: `string`[], `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.once

#### Defined in

@types/ws/index.d.ts:375

▸ **once**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` \| ``"listening"`` |
| `cb` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.once

#### Defined in

@types/ws/index.d.ts:376

▸ **once**(`event`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (`this`: [`WSServer`](appium_types.WSServer.md)<`T`, typeof `IncomingMessage`\>, ...`args`: `any`[]) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.once

#### Defined in

@types/ws/index.d.ts:377

___

### prependListener

▸ **prependListener**(`eventName`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

Adds the `listener` function to the _beginning_ of the listeners array for the
event named `eventName`. No checks are made to see if the `listener` has
already been added. Multiple calls passing the same combination of `eventName`and `listener` will result in the `listener` being added, and called, multiple
times.

```js
server.prependListener('connection', (stream) => {
  console.log('someone connected!');
});
```

Returns a reference to the `EventEmitter`, so that calls can be chained.

**`Since`**

v6.0.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `eventName` | `string` \| `symbol` | The name of the event. |
| `listener` | (...`args`: `any`[]) => `void` | The callback function |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Inherited from

EventEmitter.prependListener

#### Defined in

@types/node/events.d.ts:679

___

### prependOnceListener

▸ **prependOnceListener**(`eventName`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

Adds a **one-time**`listener` function for the event named `eventName` to the _beginning_ of the listeners array. The next time `eventName` is triggered, this
listener is removed, and then invoked.

```js
server.prependOnceListener('connection', (stream) => {
  console.log('Ah, we have our first user!');
});
```

Returns a reference to the `EventEmitter`, so that calls can be chained.

**`Since`**

v6.0.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `eventName` | `string` \| `symbol` | The name of the event. |
| `listener` | (...`args`: `any`[]) => `void` | The callback function |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Inherited from

EventEmitter.prependOnceListener

#### Defined in

@types/node/events.d.ts:695

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

EventEmitter.rawListeners

#### Defined in

@types/node/events.d.ts:611

___

### removeAllListeners

▸ **removeAllListeners**(`event?`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

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

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Inherited from

EventEmitter.removeAllListeners

#### Defined in

@types/node/events.d.ts:551

___

### removeListener

▸ **removeListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"connection"`` |
| `cb` | (`client`: `InstanceType`<`T`\>, `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.removeListener

#### Defined in

@types/ws/index.d.ts:394

▸ **removeListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"error"`` |
| `cb` | (`err`: `Error`) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.removeListener

#### Defined in

@types/ws/index.d.ts:395

▸ **removeListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"headers"`` |
| `cb` | (`headers`: `string`[], `request`: `InstanceType`<`U`\>) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.removeListener

#### Defined in

@types/ws/index.d.ts:396

▸ **removeListener**(`event`, `cb`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | ``"close"`` \| ``"listening"`` |
| `cb` | () => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.removeListener

#### Defined in

@types/ws/index.d.ts:397

▸ **removeListener**(`event`, `listener`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `event` | `string` \| `symbol` |
| `listener` | (...`args`: `any`[]) => `void` |

#### Returns

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Overrides

EventEmitter.removeListener

#### Defined in

@types/ws/index.d.ts:398

___

### setMaxListeners

▸ **setMaxListeners**(`n`): [`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

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

[`WSServer`](appium_types.WSServer.md)<`T`, `U`\>

#### Inherited from

EventEmitter.setMaxListeners

#### Defined in

@types/node/events.d.ts:561

___

### shouldHandle

▸ **shouldHandle**(`request`): `boolean` \| `Promise`<`boolean`\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `request` | `InstanceType`<`U`\> |

#### Returns

`boolean` \| `Promise`<`boolean`\>

#### Defined in

@types/ws/index.d.ts:361

___

### getEventListeners

▸ `Static` **getEventListeners**(`emitter`, `name`): `Function`[]

Returns a copy of the array of listeners for the event named `eventName`.

For `EventEmitter`s this behaves exactly the same as calling `.listeners` on
the emitter.

For `EventTarget`s this is the only way to get the event listeners for the
event target. This is useful for debugging and diagnostic purposes.

```js
import { getEventListeners, EventEmitter } from 'node:events';

{
  const ee = new EventEmitter();
  const listener = () => console.log('Events are fun');
  ee.on('foo', listener);
  console.log(getEventListeners(ee, 'foo')); // [ [Function: listener] ]
}
{
  const et = new EventTarget();
  const listener = () => console.log('Events are fun');
  et.addEventListener('foo', listener);
  console.log(getEventListeners(et, 'foo')); // [ [Function: listener] ]
}
```

**`Since`**

v15.2.0, v14.17.0

#### Parameters

| Name | Type |
| :------ | :------ |
| `emitter` | `EventEmitter` \| `_DOMEventTarget` |
| `name` | `string` \| `symbol` |

#### Returns

`Function`[]

#### Inherited from

EventEmitter.getEventListeners

#### Defined in

@types/node/events.d.ts:296

___

### listenerCount

▸ `Static` **listenerCount**(`emitter`, `eventName`): `number`

A class method that returns the number of listeners for the given `eventName`registered on the given `emitter`.

```js
import { EventEmitter, listenerCount } from 'node:events';

const myEmitter = new EventEmitter();
myEmitter.on('event', () => {});
myEmitter.on('event', () => {});
console.log(listenerCount(myEmitter, 'event'));
// Prints: 2
```

**`Since`**

v0.9.12

**`Deprecated`**

Since v3.2.0 - Use `listenerCount` instead.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `emitter` | `EventEmitter` | The emitter to query |
| `eventName` | `string` \| `symbol` | The event name |

#### Returns

`number`

#### Inherited from

EventEmitter.listenerCount

#### Defined in

@types/node/events.d.ts:268

___

### on

▸ `Static` **on**(`emitter`, `eventName`, `options?`): `AsyncIterableIterator`<`any`\>

```js
import { on, EventEmitter } from 'node:events';
import process from 'node:process';

const ee = new EventEmitter();

// Emit later on
process.nextTick(() => {
  ee.emit('foo', 'bar');
  ee.emit('foo', 42);
});

for await (const event of on(ee, 'foo')) {
  // The execution of this inner block is synchronous and it
  // processes one event at a time (even with await). Do not use
  // if concurrent execution is required.
  console.log(event); // prints ['bar'] [42]
}
// Unreachable here
```

Returns an `AsyncIterator` that iterates `eventName` events. It will throw
if the `EventEmitter` emits `'error'`. It removes all listeners when
exiting the loop. The `value` returned by each iteration is an array
composed of the emitted event arguments.

An `AbortSignal` can be used to cancel waiting on events:

```js
import { on, EventEmitter } from 'node:events';
import process from 'node:process';

const ac = new AbortController();

(async () => {
  const ee = new EventEmitter();

  // Emit later on
  process.nextTick(() => {
    ee.emit('foo', 'bar');
    ee.emit('foo', 42);
  });

  for await (const event of on(ee, 'foo', { signal: ac.signal })) {
    // The execution of this inner block is synchronous and it
    // processes one event at a time (even with await). Do not use
    // if concurrent execution is required.
    console.log(event); // prints ['bar'] [42]
  }
  // Unreachable here
})();

process.nextTick(() => ac.abort());
```

**`Since`**

v13.6.0, v12.16.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `emitter` | `EventEmitter` | - |
| `eventName` | `string` | The name of the event being listened for |
| `options?` | `StaticEventEmitterOptions` | - |

#### Returns

`AsyncIterableIterator`<`any`\>

that iterates `eventName` events emitted by the `emitter`

#### Inherited from

EventEmitter.on

#### Defined in

@types/node/events.d.ts:250

___

### once

▸ `Static` **once**(`emitter`, `eventName`, `options?`): `Promise`<`any`[]\>

Creates a `Promise` that is fulfilled when the `EventEmitter` emits the given
event or that is rejected if the `EventEmitter` emits `'error'` while waiting.
The `Promise` will resolve with an array of all the arguments emitted to the
given event.

This method is intentionally generic and works with the web platform [EventTarget](https://dom.spec.whatwg.org/#interface-eventtarget) interface, which has no special`'error'` event
semantics and does not listen to the `'error'` event.

```js
import { once, EventEmitter } from 'node:events';
import process from 'node:process';

const ee = new EventEmitter();

process.nextTick(() => {
  ee.emit('myevent', 42);
});

const [value] = await once(ee, 'myevent');
console.log(value);

const err = new Error('kaboom');
process.nextTick(() => {
  ee.emit('error', err);
});

try {
  await once(ee, 'myevent');
} catch (err) {
  console.error('error happened', err);
}
```

The special handling of the `'error'` event is only used when `events.once()`is used to wait for another event. If `events.once()` is used to wait for the
'`error'` event itself, then it is treated as any other kind of event without
special handling:

```js
import { EventEmitter, once } from 'node:events';

const ee = new EventEmitter();

once(ee, 'error')
  .then(([err]) => console.log('ok', err.message))
  .catch((err) => console.error('error', err.message));

ee.emit('error', new Error('boom'));

// Prints: ok boom
```

An `AbortSignal` can be used to cancel waiting for the event:

```js
import { EventEmitter, once } from 'node:events';

const ee = new EventEmitter();
const ac = new AbortController();

async function foo(emitter, event, signal) {
  try {
    await once(emitter, event, { signal });
    console.log('event emitted!');
  } catch (error) {
    if (error.name === 'AbortError') {
      console.error('Waiting for the event was canceled!');
    } else {
      console.error('There was an error', error.message);
    }
  }
}

foo(ee, 'foo', ac.signal);
ac.abort(); // Abort waiting for the event
ee.emit('foo'); // Prints: Waiting for the event was canceled!
```

**`Since`**

v11.13.0, v10.16.0

#### Parameters

| Name | Type |
| :------ | :------ |
| `emitter` | `_NodeEventTarget` |
| `eventName` | `string` \| `symbol` |
| `options?` | `StaticEventEmitterOptions` |

#### Returns

`Promise`<`any`[]\>

#### Inherited from

EventEmitter.once

#### Defined in

@types/node/events.d.ts:189

▸ `Static` **once**(`emitter`, `eventName`, `options?`): `Promise`<`any`[]\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `emitter` | `_DOMEventTarget` |
| `eventName` | `string` |
| `options?` | `StaticEventEmitterOptions` |

#### Returns

`Promise`<`any`[]\>

#### Inherited from

EventEmitter.once

#### Defined in

@types/node/events.d.ts:190

___

### setMaxListeners

▸ `Static` **setMaxListeners**(`n?`, `...eventTargets`): `void`

```js
import { setMaxListeners, EventEmitter } from 'node:events';

const target = new EventTarget();
const emitter = new EventEmitter();

setMaxListeners(5, target, emitter);
```

**`Since`**

v15.4.0

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `n?` | `number` | A non-negative number. The maximum number of listeners per `EventTarget` event. |
| `...eventTargets` | (`EventEmitter` \| `_DOMEventTarget`)[] | - |

#### Returns

`void`

#### Inherited from

EventEmitter.setMaxListeners

#### Defined in

@types/node/events.d.ts:311
