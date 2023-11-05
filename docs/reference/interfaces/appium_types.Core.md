# Interface: Core<C, Settings\>

[@appium/types](../modules/appium_types.md).Core

Methods and properties which both `AppiumDriver` and `BaseDriver` inherit.

This should not be used directly by external code.

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- **`Core`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Properties

- [allowInsecure](appium_types.Core.md#allowinsecure)
- [basePath](appium_types.Core.md#basepath)
- [denyInsecure](appium_types.Core.md#denyinsecure)
- [driverData](appium_types.Core.md#driverdata)
- [eventEmitter](appium_types.Core.md#eventemitter)
- [eventHistory](appium_types.Core.md#eventhistory)
- [helpers](appium_types.Core.md#helpers)
- [implicitWaitMs](appium_types.Core.md#implicitwaitms)
- [initialOpts](appium_types.Core.md#initialopts)
- [isCommandsQueueEnabled](appium_types.Core.md#iscommandsqueueenabled)
- [locatorStrategies](appium_types.Core.md#locatorstrategies)
- [log](appium_types.Core.md#log)
- [newCommandTimeoutMs](appium_types.Core.md#newcommandtimeoutms)
- [opts](appium_types.Core.md#opts)
- [protocol](appium_types.Core.md#protocol)
- [relaxedSecurityEnabled](appium_types.Core.md#relaxedsecurityenabled)
- [sessionId](appium_types.Core.md#sessionid)
- [settings](appium_types.Core.md#settings)
- [shouldValidateCaps](appium_types.Core.md#shouldvalidatecaps)
- [webLocatorStrategies](appium_types.Core.md#weblocatorstrategies)

### Methods

- [addManagedDriver](appium_types.Core.md#addmanageddriver)
- [assertFeatureEnabled](appium_types.Core.md#assertfeatureenabled)
- [canProxy](appium_types.Core.md#canproxy)
- [clearNewCommandTimeout](appium_types.Core.md#clearnewcommandtimeout)
- [driverForSession](appium_types.Core.md#driverforsession)
- [getManagedDrivers](appium_types.Core.md#getmanageddrivers)
- [getProxyAvoidList](appium_types.Core.md#getproxyavoidlist)
- [getStatus](appium_types.Core.md#getstatus)
- [isFeatureEnabled](appium_types.Core.md#isfeatureenabled)
- [isMjsonwpProtocol](appium_types.Core.md#ismjsonwpprotocol)
- [isW3CProtocol](appium_types.Core.md#isw3cprotocol)
- [logEvent](appium_types.Core.md#logevent)
- [onUnexpectedShutdown](appium_types.Core.md#onunexpectedshutdown)
- [proxyActive](appium_types.Core.md#proxyactive)
- [proxyRouteIsAvoided](appium_types.Core.md#proxyrouteisavoided)
- [sessionExists](appium_types.Core.md#sessionexists)
- [validateLocatorStrategy](appium_types.Core.md#validatelocatorstrategy)

## Properties

### allowInsecure

• **allowInsecure**: `string`[]

#### Defined in

@appium/types/lib/driver.ts:587

___

### basePath

• **basePath**: `string`

#### Defined in

@appium/types/lib/driver.ts:585

___

### denyInsecure

• **denyInsecure**: `string`[]

#### Defined in

@appium/types/lib/driver.ts:588

___

### driverData

• **driverData**: [`DriverData`](../modules/appium_types.md#driverdata)

#### Defined in

@appium/types/lib/driver.ts:596

___

### eventEmitter

• **eventEmitter**: `EventEmitter`

#### Defined in

@appium/types/lib/driver.ts:593

___

### eventHistory

• **eventHistory**: [`EventHistory`](appium_types.EventHistory.md)

#### Defined in

@appium/types/lib/driver.ts:598

___

### helpers

• **helpers**: [`DriverHelpers`](appium_types.DriverHelpers.md)

#### Defined in

@appium/types/lib/driver.ts:584

___

### implicitWaitMs

• **implicitWaitMs**: `number`

#### Defined in

@appium/types/lib/driver.ts:590

___

### initialOpts

• **initialOpts**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `address` | `string` |
| `allowCors` | `NonNullable`<`undefined` \| `boolean`\> |
| `allowInsecure` | [`AllowInsecureConfig`](../modules/appium_types.md#allowinsecureconfig) |
| `basePath` | `string` |
| `callbackAddress` | `undefined` \| `string` |
| `callbackPort` | `number` |
| `debugLogSpacing` | `NonNullable`<`undefined` \| `boolean`\> |
| `defaultCapabilities` | `undefined` \| [`DefaultCapabilitiesConfig`](appium_types.DefaultCapabilitiesConfig.md) |
| `denyInsecure` | [`DenyInsecureConfig`](../modules/appium_types.md#denyinsecureconfig) |
| `driver` | `undefined` \| [`DriverConfig`](appium_types.DriverConfig.md) |
| `fastReset?` | `boolean` |
| `keepAliveTimeout` | `number` |
| `localTimezone` | `NonNullable`<`undefined` \| `boolean`\> |
| `logFile` | `undefined` \| `string` |
| `logFilters` | `undefined` \| [`LogFiltersConfig`](../modules/appium_types.md#logfiltersconfig) |
| `logNoColors` | `NonNullable`<`undefined` \| `boolean`\> |
| `logTimestamp` | `NonNullable`<`undefined` \| `boolean`\> |
| `loglevel` | `NonNullable`<`undefined` \| [`LogLevelConfig`](../modules/appium_types.md#loglevelconfig)\> |
| `longStacktrace` | `NonNullable`<`undefined` \| `boolean`\> |
| `noPermsCheck` | `NonNullable`<`undefined` \| `boolean`\> |
| `nodeconfig` | `undefined` \| [`NodeconfigConfig`](appium_types.NodeconfigConfig.md) |
| `plugin` | `undefined` \| [`PluginConfig`](appium_types.PluginConfig.md) |
| `port` | `number` |
| `relaxedSecurityEnabled` | `NonNullable`<`undefined` \| `boolean`\> |
| `sessionOverride` | `NonNullable`<`undefined` \| `boolean`\> |
| `skipUninstall?` | `boolean` |
| `sslCertificatePath` | `undefined` \| `string` |
| `sslKeyPath` | `undefined` \| `string` |
| `strictCaps` | `NonNullable`<`undefined` \| `boolean`\> |
| `tmpDir` | `undefined` \| `string` |
| `traceDir` | `undefined` \| `string` |
| `useDrivers` | [`UseDriversConfig`](../modules/appium_types.md#usedriversconfig) |
| `usePlugins` | [`UsePluginsConfig`](../modules/appium_types.md#usepluginsconfig) |
| `webhook` | `undefined` \| `string` |

#### Defined in

@appium/types/lib/driver.ts:582

___

### isCommandsQueueEnabled

• **isCommandsQueueEnabled**: `boolean`

#### Defined in

@appium/types/lib/driver.ts:597

___

### locatorStrategies

• **locatorStrategies**: `string`[]

#### Defined in

@appium/types/lib/driver.ts:591

___

### log

• **log**: [`AppiumLogger`](appium_types.AppiumLogger.md)

#### Defined in

@appium/types/lib/driver.ts:595

___

### newCommandTimeoutMs

• **newCommandTimeoutMs**: `number`

#### Defined in

@appium/types/lib/driver.ts:589

___

### opts

• **opts**: [`DriverOpts`](../modules/appium_types.md#driveropts)<`C`\>

#### Defined in

@appium/types/lib/driver.ts:581

___

### protocol

• `Optional` **protocol**: [`Protocol`](../modules/appium_types.md#protocol)

#### Defined in

@appium/types/lib/driver.ts:583

___

### relaxedSecurityEnabled

• **relaxedSecurityEnabled**: `boolean`

#### Defined in

@appium/types/lib/driver.ts:586

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Defined in

@appium/types/lib/driver.ts:580

___

### settings

• **settings**: [`IDeviceSettings`](appium_types.IDeviceSettings.md)<`Settings`\>

#### Defined in

@appium/types/lib/driver.ts:594

___

### shouldValidateCaps

• **shouldValidateCaps**: `boolean`

#### Defined in

@appium/types/lib/driver.ts:579

___

### webLocatorStrategies

• **webLocatorStrategies**: `string`[]

#### Defined in

@appium/types/lib/driver.ts:592

## Methods

### addManagedDriver

▸ **addManagedDriver**(`driver`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:642

___

### assertFeatureEnabled

▸ **assertFeatureEnabled**(`name`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `name` | `string` |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:636

___

### canProxy

▸ **canProxy**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:640

___

### clearNewCommandTimeout

▸ **clearNewCommandTimeout**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:644

___

### driverForSession

▸ **driverForSession**(`sessionId`): ``null`` \| [`Core`](appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

``null`` \| [`Core`](appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

#### Defined in

@appium/types/lib/driver.ts:646

___

### getManagedDrivers

▸ **getManagedDrivers**(): [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Returns

[`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Defined in

@appium/types/lib/driver.ts:643

___

### getProxyAvoidList

▸ **getProxyAvoidList**(`sessionId?`): [`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

[`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Defined in

@appium/types/lib/driver.ts:639

___

### getStatus

▸ **getStatus**(): `Promise`<`any`\>

**`Summary`**

Retrieve the server's current status.

**`Description`**

Returns information about whether a remote end is in a state in which it can create new sessions and can additionally include arbitrary meta information that is specific to the implementation.

The readiness state is represented by the ready property of the body, which is false if an attempt to create a session at the current time would fail. However, the value true does not guarantee that a New Session command will succeed.

Implementations may optionally include additional meta information as part of the body, but the top-level properties ready and message are reserved and must not be overwritten.

**`Example`**

```js
// webdriver.io example
await driver.status();
```

```python
driver.get_status()
```

```java
driver.getStatus();
```

```ruby
# ruby_lib example
remote_status

# ruby_lib_core example
@driver.remote_status
```

#### Returns

`Promise`<`any`\>

#### Defined in

@appium/types/lib/driver.ts:631

___

### isFeatureEnabled

▸ **isFeatureEnabled**(`name`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `name` | `string` |

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:635

___

### isMjsonwpProtocol

▸ **isMjsonwpProtocol**(): `boolean`

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:634

___

### isW3CProtocol

▸ **isW3CProtocol**(): `boolean`

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:633

___

### logEvent

▸ **logEvent**(`eventName`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:645

___

### onUnexpectedShutdown

▸ **onUnexpectedShutdown**(`handler`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `handler` | () => `any` |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:599

___

### proxyActive

▸ **proxyActive**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:638

___

### proxyRouteIsAvoided

▸ **proxyRouteIsAvoided**(`sessionId`, `method`, `url`, `body?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |
| `method` | `string` |
| `url` | `string` |
| `body?` | `any` |

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:641

___

### sessionExists

▸ **sessionExists**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Defined in

@appium/types/lib/driver.ts:632

___

### validateLocatorStrategy

▸ **validateLocatorStrategy**(`strategy`, `webContext?`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `strategy` | `string` |
| `webContext?` | `boolean` |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:637
