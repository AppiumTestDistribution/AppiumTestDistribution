# Class: DriverCore<C, Settings\>

[@appium/base-driver](../modules/appium_base_driver.md).DriverCore

Methods and properties which both `AppiumDriver` and `BaseDriver` inherit.

This should not be used directly by external code.

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- **`DriverCore`**

  ↳ [`BaseDriver`](appium_base_driver.BaseDriver.md)

## Implements

- [`Core`](../interfaces/appium_types.Core.md)<`C`, `Settings`\>

## Table of contents

### Constructors

- [constructor](appium_base_driver.DriverCore.md#constructor)

### Properties

- [\_eventHistory](appium_base_driver.DriverCore.md#_eventhistory)
- [\_log](appium_base_driver.DriverCore.md#_log)
- [allowInsecure](appium_base_driver.DriverCore.md#allowinsecure)
- [basePath](appium_base_driver.DriverCore.md#basepath)
- [commandsQueueGuard](appium_base_driver.DriverCore.md#commandsqueueguard)
- [denyInsecure](appium_base_driver.DriverCore.md#denyinsecure)
- [eventEmitter](appium_base_driver.DriverCore.md#eventemitter)
- [helpers](appium_base_driver.DriverCore.md#helpers)
- [implicitWaitMs](appium_base_driver.DriverCore.md#implicitwaitms)
- [initialOpts](appium_base_driver.DriverCore.md#initialopts)
- [locatorStrategies](appium_base_driver.DriverCore.md#locatorstrategies)
- [managedDrivers](appium_base_driver.DriverCore.md#manageddrivers)
- [newCommandTimeoutMs](appium_base_driver.DriverCore.md#newcommandtimeoutms)
- [noCommandTimer](appium_base_driver.DriverCore.md#nocommandtimer)
- [opts](appium_base_driver.DriverCore.md#opts)
- [protocol](appium_base_driver.DriverCore.md#protocol)
- [relaxedSecurityEnabled](appium_base_driver.DriverCore.md#relaxedsecurityenabled)
- [sessionId](appium_base_driver.DriverCore.md#sessionid)
- [settings](appium_base_driver.DriverCore.md#settings)
- [shouldValidateCaps](appium_base_driver.DriverCore.md#shouldvalidatecaps)
- [shutdownUnexpectedly](appium_base_driver.DriverCore.md#shutdownunexpectedly)
- [webLocatorStrategies](appium_base_driver.DriverCore.md#weblocatorstrategies)
- [baseVersion](appium_base_driver.DriverCore.md#baseversion)

### Accessors

- [driverData](appium_base_driver.DriverCore.md#driverdata)
- [eventHistory](appium_base_driver.DriverCore.md#eventhistory)
- [isCommandsQueueEnabled](appium_base_driver.DriverCore.md#iscommandsqueueenabled)
- [log](appium_base_driver.DriverCore.md#log)

### Methods

- [addManagedDriver](appium_base_driver.DriverCore.md#addmanageddriver)
- [assertFeatureEnabled](appium_base_driver.DriverCore.md#assertfeatureenabled)
- [canProxy](appium_base_driver.DriverCore.md#canproxy)
- [clearNewCommandTimeout](appium_base_driver.DriverCore.md#clearnewcommandtimeout)
- [driverForSession](appium_base_driver.DriverCore.md#driverforsession)
- [ensureFeatureEnabled](appium_base_driver.DriverCore.md#ensurefeatureenabled)
- [getManagedDrivers](appium_base_driver.DriverCore.md#getmanageddrivers)
- [getProxyAvoidList](appium_base_driver.DriverCore.md#getproxyavoidlist)
- [getStatus](appium_base_driver.DriverCore.md#getstatus)
- [isFeatureEnabled](appium_base_driver.DriverCore.md#isfeatureenabled)
- [isMjsonwpProtocol](appium_base_driver.DriverCore.md#ismjsonwpprotocol)
- [isW3CProtocol](appium_base_driver.DriverCore.md#isw3cprotocol)
- [logEvent](appium_base_driver.DriverCore.md#logevent)
- [onUnexpectedShutdown](appium_base_driver.DriverCore.md#onunexpectedshutdown)
- [proxyActive](appium_base_driver.DriverCore.md#proxyactive)
- [proxyRouteIsAvoided](appium_base_driver.DriverCore.md#proxyrouteisavoided)
- [sessionExists](appium_base_driver.DriverCore.md#sessionexists)
- [setProtocolMJSONWP](appium_base_driver.DriverCore.md#setprotocolmjsonwp)
- [setProtocolW3C](appium_base_driver.DriverCore.md#setprotocolw3c)
- [validateLocatorStrategy](appium_base_driver.DriverCore.md#validatelocatorstrategy)

## Constructors

### constructor

• **new DriverCore**<`C`, `Settings`\>(`opts?`, `shouldValidateCaps?`)

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `opts` | `Object` | `undefined` |
| `opts.address` | `string` | `undefined` |
| `opts.allowCors` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.allowInsecure` | [`AllowInsecureConfig`](../modules/appium_types.md#allowinsecureconfig) | `undefined` |
| `opts.basePath` | `string` | `undefined` |
| `opts.callbackAddress` | `undefined` \| `string` | `undefined` |
| `opts.callbackPort` | `number` | `undefined` |
| `opts.debugLogSpacing` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.defaultCapabilities` | `undefined` \| [`DefaultCapabilitiesConfig`](../interfaces/appium_types.DefaultCapabilitiesConfig.md) | `undefined` |
| `opts.denyInsecure` | [`DenyInsecureConfig`](../modules/appium_types.md#denyinsecureconfig) | `undefined` |
| `opts.driver` | `undefined` \| [`DriverConfig`](../interfaces/appium_types.DriverConfig.md) | `undefined` |
| `opts.fastReset?` | `boolean` | `undefined` |
| `opts.keepAliveTimeout` | `number` | `undefined` |
| `opts.localTimezone` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.logFile` | `undefined` \| `string` | `undefined` |
| `opts.logFilters` | `undefined` \| [`LogFiltersConfig`](../modules/appium_types.md#logfiltersconfig) | `undefined` |
| `opts.logNoColors` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.logTimestamp` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.loglevel` | `NonNullable`<`undefined` \| [`LogLevelConfig`](../modules/appium_types.md#loglevelconfig)\> | `undefined` |
| `opts.longStacktrace` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.noPermsCheck` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.nodeconfig` | `undefined` \| [`NodeconfigConfig`](../interfaces/appium_types.NodeconfigConfig.md) | `undefined` |
| `opts.plugin` | `undefined` \| [`PluginConfig`](../interfaces/appium_types.PluginConfig.md) | `undefined` |
| `opts.port` | `number` | `undefined` |
| `opts.relaxedSecurityEnabled` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.sessionOverride` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.skipUninstall?` | `boolean` | `undefined` |
| `opts.sslCertificatePath` | `undefined` \| `string` | `undefined` |
| `opts.sslKeyPath` | `undefined` \| `string` | `undefined` |
| `opts.strictCaps` | `NonNullable`<`undefined` \| `boolean`\> | `undefined` |
| `opts.tmpDir` | `undefined` \| `string` | `undefined` |
| `opts.traceDir` | `undefined` \| `string` | `undefined` |
| `opts.useDrivers` | [`UseDriversConfig`](../modules/appium_types.md#usedriversconfig) | `undefined` |
| `opts.usePlugins` | [`UsePluginsConfig`](../modules/appium_types.md#usepluginsconfig) | `undefined` |
| `opts.webhook` | `undefined` \| `string` | `undefined` |
| `shouldValidateCaps` | `boolean` | `true` |

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:102

## Properties

### \_eventHistory

• `Protected` **\_eventHistory**: [`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:76

___

### \_log

• `Protected` **\_log**: [`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:84

___

### allowInsecure

• **allowInsecure**: `string`[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[allowInsecure](../interfaces/appium_types.Core.md#allowinsecure)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:60

___

### basePath

• **basePath**: `string`

basePath is used for several purposes, for example in setting up
proxying to other drivers, since we need to know what the base path
of any incoming request might look like. We set it to the default
initially but it is automatically updated during any actual program
execution by the routeConfiguringFunction, which is necessarily run as
the entrypoint for any Appium server

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[basePath](../interfaces/appium_types.Core.md#basepath)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:56

___

### commandsQueueGuard

• `Protected` **commandsQueueGuard**: `AsyncLock`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:90

___

### denyInsecure

• **denyInsecure**: `string`[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[denyInsecure](../interfaces/appium_types.Core.md#denyinsecure)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:62

___

### eventEmitter

• **eventEmitter**: `EventEmitter`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[eventEmitter](../interfaces/appium_types.Core.md#eventemitter)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:79

___

### helpers

• **helpers**: [`DriverHelpers`](../interfaces/appium_types.DriverHelpers.md)

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[helpers](../interfaces/appium_types.Core.md#helpers)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:46

___

### implicitWaitMs

• **implicitWaitMs**: `number`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[implicitWaitMs](../interfaces/appium_types.Core.md#implicitwaitms)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:66

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
| `defaultCapabilities` | `undefined` \| [`DefaultCapabilitiesConfig`](../interfaces/appium_types.DefaultCapabilitiesConfig.md) |
| `denyInsecure` | [`DenyInsecureConfig`](../modules/appium_types.md#denyinsecureconfig) |
| `driver` | `undefined` \| [`DriverConfig`](../interfaces/appium_types.DriverConfig.md) |
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
| `nodeconfig` | `undefined` \| [`NodeconfigConfig`](../interfaces/appium_types.NodeconfigConfig.md) |
| `plugin` | `undefined` \| [`PluginConfig`](../interfaces/appium_types.PluginConfig.md) |
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

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[initialOpts](../interfaces/appium_types.Core.md#initialopts)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:44

___

### locatorStrategies

• **locatorStrategies**: `string`[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[locatorStrategies](../interfaces/appium_types.Core.md#locatorstrategies)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:68

___

### managedDrivers

• **managedDrivers**: [`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:72

___

### newCommandTimeoutMs

• **newCommandTimeoutMs**: `number`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[newCommandTimeoutMs](../interfaces/appium_types.Core.md#newcommandtimeoutms)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:64

___

### noCommandTimer

• **noCommandTimer**: ``null`` \| `Timeout`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:74

___

### opts

• **opts**: [`DriverOpts`](../modules/appium_types.md#driveropts)<`C`\>

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[opts](../interfaces/appium_types.Core.md#opts)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:42

___

### protocol

• `Optional` **protocol**: [`Protocol`](../modules/appium_types.md#protocol)

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[protocol](../interfaces/appium_types.Core.md#protocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:100

___

### relaxedSecurityEnabled

• **relaxedSecurityEnabled**: `boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[relaxedSecurityEnabled](../interfaces/appium_types.Core.md#relaxedsecurityenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:58

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[sessionId](../interfaces/appium_types.Core.md#sessionid)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:40

___

### settings

• **settings**: [`DeviceSettings`](appium_base_driver.DeviceSettings.md)<`Settings`\>

settings should be instantiated by drivers which extend BaseDriver, but
we set it to an empty DeviceSettings instance here to make sure that the
default settings are applied even if an extending driver doesn't utilize
the settings functionality itself

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[settings](../interfaces/appium_types.Core.md#settings)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:98

___

### shouldValidateCaps

• **shouldValidateCaps**: `boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[shouldValidateCaps](../interfaces/appium_types.Core.md#shouldvalidatecaps)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:88

___

### shutdownUnexpectedly

• **shutdownUnexpectedly**: `boolean`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:86

___

### webLocatorStrategies

• **webLocatorStrategies**: `string`[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[webLocatorStrategies](../interfaces/appium_types.Core.md#weblocatorstrategies)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:70

___

### baseVersion

▪ `Static` **baseVersion**: `string` = `BASEDRIVER_VER`

Make the basedriver version available so for any driver which inherits from this package, we
know which version of basedriver it inherited from

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:38

## Accessors

### driverData

• `get` **driverData**(): `Object`

This property is used by AppiumDriver to store the data of the
specific driver sessions. This data can be later used to adjust
properties for driver instances running in parallel.
Override it in inherited driver classes if necessary.

#### Returns

`Object`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[driverData](../interfaces/appium_types.Core.md#driverdata)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:160

___

### eventHistory

• `get` **eventHistory**(): [`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Returns

[`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[eventHistory](../interfaces/appium_types.Core.md#eventhistory)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:183

___

### isCommandsQueueEnabled

• `get` **isCommandsQueueEnabled**(): `boolean`

This property controls the way the `executeCommand` method
handles new driver commands received from the client.
Override it for inherited classes only in special cases.

#### Returns

`boolean`

If the returned value is true (default) then all the commands
  received by the particular driver instance are going to be put into the queue,
  so each following command will not be executed until the previous command
  execution is completed. False value disables that queue, so each driver command
  is executed independently and does not wait for anything.

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[isCommandsQueueEnabled](../interfaces/appium_types.Core.md#iscommandsqueueenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:175

___

### log

• `get` **log**(): [`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Returns

[`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[log](../interfaces/appium_types.Core.md#log)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:137

## Methods

### addManagedDriver

▸ **addManagedDriver**(`driver`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | [`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> |

#### Returns

`void`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[addManagedDriver](../interfaces/appium_types.Core.md#addmanageddriver)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:365

___

### assertFeatureEnabled

▸ **assertFeatureEnabled**(`name`): `void`

Assert that a given feature is enabled and throw a helpful error if it's
not

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | name of feature/command |

#### Returns

`void`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[assertFeatureEnabled](../interfaces/appium_types.Core.md#assertfeatureenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:290

___

### canProxy

▸ **canProxy**(`sessionId`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[canProxy](../interfaces/appium_types.Core.md#canproxy)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:324

___

### clearNewCommandTimeout

▸ **clearNewCommandTimeout**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[clearNewCommandTimeout](../interfaces/appium_types.Core.md#clearnewcommandtimeout)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:373

___

### driverForSession

▸ **driverForSession**(`sessionId`): ``null`` \| [`Core`](../interfaces/appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

method required by MJSONWP in order to determine if the command should
be proxied directly to the driver

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

``null`` \| [`Core`](../interfaces/appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[driverForSession](../interfaces/appium_types.Core.md#driverforsession)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:227

___

### ensureFeatureEnabled

▸ **ensureFeatureEnabled**(`name`): `void`

Assert that a given feature is enabled and throw a helpful error if it's
not

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | name of feature/command |

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:280

___

### getManagedDrivers

▸ **getManagedDrivers**(): [`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Returns

[`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[getManagedDrivers](../interfaces/appium_types.Core.md#getmanageddrivers)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:369

___

### getProxyAvoidList

▸ **getProxyAvoidList**(`sessionId`): [`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

[`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[getProxyAvoidList](../interfaces/appium_types.Core.md#getproxyavoidlist)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:320

___

### getStatus

▸ **getStatus**(): `Promise`<{}\>

#### Returns

`Promise`<{}\>

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[getStatus](../interfaces/appium_types.Core.md#getstatus)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:210

___

### isFeatureEnabled

▸ **isFeatureEnabled**(`name`): `boolean`

Check whether a given feature is enabled via its name

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | name of feature/command |

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[isFeatureEnabled](../interfaces/appium_types.Core.md#isfeatureenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:252

___

### isMjsonwpProtocol

▸ **isMjsonwpProtocol**(): `boolean`

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[isMjsonwpProtocol](../interfaces/appium_types.Core.md#ismjsonwpprotocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:231

___

### isW3CProtocol

▸ **isW3CProtocol**(): `boolean`

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[isW3CProtocol](../interfaces/appium_types.Core.md#isw3cprotocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:235

___

### logEvent

▸ **logEvent**(`eventName`): `void`

API method for driver developers to log timings for important events

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` |

#### Returns

`void`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[logEvent](../interfaces/appium_types.Core.md#logevent)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:190

___

### onUnexpectedShutdown

▸ **onUnexpectedShutdown**(`handler`): `void`

Set a callback handler if needed to execute a custom piece of code
when the driver is shut down unexpectedly. Multiple calls to this method
will cause the handler to be executed mutiple times

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `handler` | (...`args`: `any`[]) => `void` | The code to be executed on unexpected shutdown. The function may accept one argument, which is the actual error instance, which caused the driver to shut down. |

#### Returns

`void`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[onUnexpectedShutdown](../interfaces/appium_types.Core.md#onunexpectedshutdown)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:150

___

### proxyActive

▸ **proxyActive**(`sessionId`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[proxyActive](../interfaces/appium_types.Core.md#proxyactive)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:316

___

### proxyRouteIsAvoided

▸ **proxyRouteIsAvoided**(`sessionId`, `method`, `url`, `body?`): `boolean`

Whether a given command route (expressed as method and url) should not be
proxied according to this driver

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `sessionId` | `string` | the current sessionId (in case the driver runs multiple session ids and requires it). This is not used in this method but should be made available to overridden methods. |
| `method` | [`HTTPMethod`](../modules/appium_types.md#httpmethod) | HTTP method of the route |
| `url` | `string` | url of the route |
| `body?` | `any` | webdriver request body |

#### Returns

`boolean`

whether the route should be avoided

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[proxyRouteIsAvoided](../interfaces/appium_types.Core.md#proxyrouteisavoided)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:341

___

### sessionExists

▸ **sessionExists**(`sessionId`): `boolean`

method required by MJSONWP in order to determine whether it should
respond with an invalid session response

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

`boolean`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[sessionExists](../interfaces/appium_types.Core.md#sessionexists)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:218

___

### setProtocolMJSONWP

▸ **setProtocolMJSONWP**(): `void`

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:239

___

### setProtocolW3C

▸ **setProtocolW3C**(): `void`

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:243

___

### validateLocatorStrategy

▸ **validateLocatorStrategy**(`strategy`, `webContext?`): `void`

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `strategy` | `string` | `undefined` |
| `webContext` | `boolean` | `false` |

#### Returns

`void`

#### Implementation of

[Core](../interfaces/appium_types.Core.md).[validateLocatorStrategy](../interfaces/appium_types.Core.md#validatelocatorstrategy)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:301
