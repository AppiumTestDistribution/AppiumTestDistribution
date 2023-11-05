# Class: BaseDriver<C, CArgs, Settings, CreateResult, DeleteResult, SessionData\>

[@appium/base-driver](../modules/appium_base_driver.md).BaseDriver

`BaseDriver` implements this.  It contains default behavior;
external drivers are expected to implement ExternalDriver instead.

`C` should be the constraints of the driver.
`CArgs` would be the shape of `cliArgs`.
`Settings` is the shape of the raw device settings object (see IDeviceSettings)

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |
| `CArgs` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `CreateResult` | [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<`C`\> |
| `DeleteResult` | [`DefaultDeleteSessionResult`](../modules/appium_types.md#defaultdeletesessionresult) |
| `SessionData` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- [`DriverCore`](appium_base_driver.DriverCore.md)<`C`, `Settings`\>

- [`IEventCommands`](../interfaces/appium_types.IEventCommands.md)

- [`IFindCommands`](../interfaces/appium_types.IFindCommands.md)

- [`ILogCommands`](../interfaces/appium_types.ILogCommands.md)

- [`ITimeoutCommands`](../interfaces/appium_types.ITimeoutCommands.md)

- [`IExecuteCommands`](../interfaces/appium_types.IExecuteCommands.md)

  ↳ **`BaseDriver`**

## Implements

- [`Driver`](../interfaces/appium_types.Driver.md)<`C`, `CArgs`, `Settings`, `CreateResult`, `DeleteResult`, `SessionData`\>

## Table of contents

### Constructors

- [constructor](appium_base_driver.BaseDriver.md#constructor)

### Properties

- [\_eventHistory](appium_base_driver.BaseDriver.md#_eventhistory)
- [\_log](appium_base_driver.BaseDriver.md#_log)
- [allowInsecure](appium_base_driver.BaseDriver.md#allowinsecure)
- [basePath](appium_base_driver.BaseDriver.md#basepath)
- [caps](appium_base_driver.BaseDriver.md#caps)
- [cliArgs](appium_base_driver.BaseDriver.md#cliargs)
- [commandsQueueGuard](appium_base_driver.BaseDriver.md#commandsqueueguard)
- [denyInsecure](appium_base_driver.BaseDriver.md#denyinsecure)
- [desiredCapConstraints](appium_base_driver.BaseDriver.md#desiredcapconstraints)
- [eventEmitter](appium_base_driver.BaseDriver.md#eventemitter)
- [helpers](appium_base_driver.BaseDriver.md#helpers)
- [implicitWaitMs](appium_base_driver.BaseDriver.md#implicitwaitms)
- [initialOpts](appium_base_driver.BaseDriver.md#initialopts)
- [locatorStrategies](appium_base_driver.BaseDriver.md#locatorstrategies)
- [managedDrivers](appium_base_driver.BaseDriver.md#manageddrivers)
- [newCommandTimeoutMs](appium_base_driver.BaseDriver.md#newcommandtimeoutms)
- [noCommandTimer](appium_base_driver.BaseDriver.md#nocommandtimer)
- [opts](appium_base_driver.BaseDriver.md#opts)
- [originalCaps](appium_base_driver.BaseDriver.md#originalcaps)
- [protocol](appium_base_driver.BaseDriver.md#protocol)
- [relaxedSecurityEnabled](appium_base_driver.BaseDriver.md#relaxedsecurityenabled)
- [server](appium_base_driver.BaseDriver.md#server)
- [serverHost](appium_base_driver.BaseDriver.md#serverhost)
- [serverPath](appium_base_driver.BaseDriver.md#serverpath)
- [serverPort](appium_base_driver.BaseDriver.md#serverport)
- [sessionId](appium_base_driver.BaseDriver.md#sessionid)
- [settings](appium_base_driver.BaseDriver.md#settings)
- [shouldValidateCaps](appium_base_driver.BaseDriver.md#shouldvalidatecaps)
- [shutdownUnexpectedly](appium_base_driver.BaseDriver.md#shutdownunexpectedly)
- [supportedLogTypes](appium_base_driver.BaseDriver.md#supportedlogtypes)
- [webLocatorStrategies](appium_base_driver.BaseDriver.md#weblocatorstrategies)
- [baseVersion](appium_base_driver.BaseDriver.md#baseversion)

### Accessors

- [\_desiredCapConstraints](appium_base_driver.BaseDriver.md#_desiredcapconstraints)
- [driverData](appium_base_driver.BaseDriver.md#driverdata)
- [eventHistory](appium_base_driver.BaseDriver.md#eventhistory)
- [isCommandsQueueEnabled](appium_base_driver.BaseDriver.md#iscommandsqueueenabled)
- [log](appium_base_driver.BaseDriver.md#log)

### Methods

- [addManagedDriver](appium_base_driver.BaseDriver.md#addmanageddriver)
- [assertFeatureEnabled](appium_base_driver.BaseDriver.md#assertfeatureenabled)
- [assignServer](appium_base_driver.BaseDriver.md#assignserver)
- [canProxy](appium_base_driver.BaseDriver.md#canproxy)
- [clearNewCommandTimeout](appium_base_driver.BaseDriver.md#clearnewcommandtimeout)
- [createSession](appium_base_driver.BaseDriver.md#createsession)
- [deleteSession](appium_base_driver.BaseDriver.md#deletesession)
- [driverForSession](appium_base_driver.BaseDriver.md#driverforsession)
- [ensureFeatureEnabled](appium_base_driver.BaseDriver.md#ensurefeatureenabled)
- [executeCommand](appium_base_driver.BaseDriver.md#executecommand)
- [executeMethod](appium_base_driver.BaseDriver.md#executemethod)
- [findElOrEls](appium_base_driver.BaseDriver.md#findelorels)
- [findElOrElsWithProcessing](appium_base_driver.BaseDriver.md#findelorelswithprocessing)
- [findElement](appium_base_driver.BaseDriver.md#findelement)
- [findElementFromElement](appium_base_driver.BaseDriver.md#findelementfromelement)
- [findElementFromShadowRoot](appium_base_driver.BaseDriver.md#findelementfromshadowroot)
- [findElements](appium_base_driver.BaseDriver.md#findelements)
- [findElementsFromElement](appium_base_driver.BaseDriver.md#findelementsfromelement)
- [findElementsFromShadowRoot](appium_base_driver.BaseDriver.md#findelementsfromshadowroot)
- [getLog](appium_base_driver.BaseDriver.md#getlog)
- [getLogEvents](appium_base_driver.BaseDriver.md#getlogevents)
- [getLogTypes](appium_base_driver.BaseDriver.md#getlogtypes)
- [getManagedDrivers](appium_base_driver.BaseDriver.md#getmanageddrivers)
- [getPageSource](appium_base_driver.BaseDriver.md#getpagesource)
- [getProxyAvoidList](appium_base_driver.BaseDriver.md#getproxyavoidlist)
- [getSession](appium_base_driver.BaseDriver.md#getsession)
- [getSessions](appium_base_driver.BaseDriver.md#getsessions)
- [getSettings](appium_base_driver.BaseDriver.md#getsettings)
- [getStatus](appium_base_driver.BaseDriver.md#getstatus)
- [getTimeouts](appium_base_driver.BaseDriver.md#gettimeouts)
- [implicitWait](appium_base_driver.BaseDriver.md#implicitwait)
- [implicitWaitForCondition](appium_base_driver.BaseDriver.md#implicitwaitforcondition)
- [implicitWaitMJSONWP](appium_base_driver.BaseDriver.md#implicitwaitmjsonwp)
- [implicitWaitW3C](appium_base_driver.BaseDriver.md#implicitwaitw3c)
- [isFeatureEnabled](appium_base_driver.BaseDriver.md#isfeatureenabled)
- [isMjsonwpProtocol](appium_base_driver.BaseDriver.md#ismjsonwpprotocol)
- [isW3CProtocol](appium_base_driver.BaseDriver.md#isw3cprotocol)
- [logCustomEvent](appium_base_driver.BaseDriver.md#logcustomevent)
- [logEvent](appium_base_driver.BaseDriver.md#logevent)
- [logExtraCaps](appium_base_driver.BaseDriver.md#logextracaps)
- [newCommandTimeout](appium_base_driver.BaseDriver.md#newcommandtimeout)
- [onUnexpectedShutdown](appium_base_driver.BaseDriver.md#onunexpectedshutdown)
- [pageLoadTimeoutMJSONWP](appium_base_driver.BaseDriver.md#pageloadtimeoutmjsonwp)
- [pageLoadTimeoutW3C](appium_base_driver.BaseDriver.md#pageloadtimeoutw3c)
- [parseTimeoutArgument](appium_base_driver.BaseDriver.md#parsetimeoutargument)
- [proxyActive](appium_base_driver.BaseDriver.md#proxyactive)
- [proxyRouteIsAvoided](appium_base_driver.BaseDriver.md#proxyrouteisavoided)
- [reset](appium_base_driver.BaseDriver.md#reset)
- [scriptTimeoutMJSONWP](appium_base_driver.BaseDriver.md#scripttimeoutmjsonwp)
- [scriptTimeoutW3C](appium_base_driver.BaseDriver.md#scripttimeoutw3c)
- [sessionExists](appium_base_driver.BaseDriver.md#sessionexists)
- [setImplicitWait](appium_base_driver.BaseDriver.md#setimplicitwait)
- [setNewCommandTimeout](appium_base_driver.BaseDriver.md#setnewcommandtimeout)
- [setProtocolMJSONWP](appium_base_driver.BaseDriver.md#setprotocolmjsonwp)
- [setProtocolW3C](appium_base_driver.BaseDriver.md#setprotocolw3c)
- [startNewCommandTimeout](appium_base_driver.BaseDriver.md#startnewcommandtimeout)
- [startUnexpectedShutdown](appium_base_driver.BaseDriver.md#startunexpectedshutdown)
- [timeouts](appium_base_driver.BaseDriver.md#timeouts)
- [updateSettings](appium_base_driver.BaseDriver.md#updatesettings)
- [validateDesiredCaps](appium_base_driver.BaseDriver.md#validatedesiredcaps)
- [validateLocatorStrategy](appium_base_driver.BaseDriver.md#validatelocatorstrategy)

## Constructors

### constructor

• **new BaseDriver**<`C`, `CArgs`, `Settings`, `CreateResult`, `DeleteResult`, `SessionData`\>(`opts`, `shouldValidateCaps?`)

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) |
| `CArgs` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `CreateResult` | [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<`C`\> |
| `DeleteResult` | `void` |
| `SessionData` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

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

#### Overrides

[DriverCore](appium_base_driver.DriverCore.md).[constructor](appium_base_driver.DriverCore.md#constructor)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:54

## Properties

### \_eventHistory

• `Protected` **\_eventHistory**: [`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[_eventHistory](appium_base_driver.DriverCore.md#_eventhistory)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:76

___

### \_log

• `Protected` **\_log**: [`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[_log](appium_base_driver.DriverCore.md#_log)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:84

___

### allowInsecure

• **allowInsecure**: `string`[]

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[allowInsecure](../interfaces/appium_types.Driver.md#allowinsecure)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[allowInsecure](appium_base_driver.DriverCore.md#allowinsecure)

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

[Driver](../interfaces/appium_types.Driver.md).[basePath](../interfaces/appium_types.Driver.md#basepath)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[basePath](appium_base_driver.DriverCore.md#basepath)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:56

___

### caps

• **caps**: [`DriverCaps`](../modules/appium_types.md#drivercaps)<`C`\>

The processed capabilities used to start the session represented by the current driver instance

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[caps](../interfaces/appium_types.Driver.md#caps)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:46

___

### cliArgs

• **cliArgs**: `CArgs` & [`ServerArgs`](../modules/appium_types.md#serverargs)

The set of command line arguments set for this driver

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[cliArgs](../interfaces/appium_types.Driver.md#cliargs)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:45

___

### commandsQueueGuard

• `Protected` **commandsQueueGuard**: `AsyncLock`

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[commandsQueueGuard](appium_base_driver.DriverCore.md#commandsqueueguard)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:90

___

### denyInsecure

• **denyInsecure**: `string`[]

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[denyInsecure](../interfaces/appium_types.Driver.md#denyinsecure)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[denyInsecure](appium_base_driver.DriverCore.md#denyinsecure)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:62

___

### desiredCapConstraints

• **desiredCapConstraints**: `C`

The constraints object used to validate capabilities

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[desiredCapConstraints](../interfaces/appium_types.Driver.md#desiredcapconstraints)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:48

___

### eventEmitter

• **eventEmitter**: `EventEmitter`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[eventEmitter](../interfaces/appium_types.Driver.md#eventemitter)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[eventEmitter](appium_base_driver.DriverCore.md#eventemitter)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:79

___

### helpers

• **helpers**: [`DriverHelpers`](../interfaces/appium_types.DriverHelpers.md)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[helpers](../interfaces/appium_types.Driver.md#helpers)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[helpers](appium_base_driver.DriverCore.md#helpers)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:46

___

### implicitWaitMs

• **implicitWaitMs**: `number`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[implicitWaitMs](../interfaces/appium_types.Driver.md#implicitwaitms)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[implicitWaitMs](appium_base_driver.DriverCore.md#implicitwaitms)

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

[Driver](../interfaces/appium_types.Driver.md).[initialOpts](../interfaces/appium_types.Driver.md#initialopts)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[initialOpts](appium_base_driver.DriverCore.md#initialopts)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:44

___

### locatorStrategies

• **locatorStrategies**: `string`[]

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[locatorStrategies](../interfaces/appium_types.Driver.md#locatorstrategies)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[locatorStrategies](appium_base_driver.DriverCore.md#locatorstrategies)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:68

___

### managedDrivers

• **managedDrivers**: [`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[managedDrivers](appium_base_driver.DriverCore.md#manageddrivers)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:72

___

### newCommandTimeoutMs

• **newCommandTimeoutMs**: `number`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[newCommandTimeoutMs](../interfaces/appium_types.Driver.md#newcommandtimeoutms)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[newCommandTimeoutMs](appium_base_driver.DriverCore.md#newcommandtimeoutms)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:64

___

### noCommandTimer

• **noCommandTimer**: ``null`` \| `Timeout`

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[noCommandTimer](appium_base_driver.DriverCore.md#nocommandtimer)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:74

___

### opts

• **opts**: [`DriverOpts`](../modules/appium_types.md#driveropts)<`C`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[opts](../interfaces/appium_types.Driver.md#opts)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[opts](appium_base_driver.DriverCore.md#opts)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:42

___

### originalCaps

• **originalCaps**: [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\>

The original capabilities used to start the session represented by the current driver instance

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[originalCaps](../interfaces/appium_types.Driver.md#originalcaps)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:47

___

### protocol

• `Optional` **protocol**: [`Protocol`](../modules/appium_types.md#protocol)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[protocol](../interfaces/appium_types.Driver.md#protocol)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[protocol](appium_base_driver.DriverCore.md#protocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:100

___

### relaxedSecurityEnabled

• **relaxedSecurityEnabled**: `boolean`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[relaxedSecurityEnabled](../interfaces/appium_types.Driver.md#relaxedsecurityenabled)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[relaxedSecurityEnabled](appium_base_driver.DriverCore.md#relaxedsecurityenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:58

___

### server

• `Optional` **server**: [`AppiumServer`](../modules/appium_types.md#appiumserver)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[server](../interfaces/appium_types.Driver.md#server)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:49

___

### serverHost

• `Optional` **serverHost**: `string`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[serverHost](../interfaces/appium_types.Driver.md#serverhost)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:50

___

### serverPath

• `Optional` **serverPath**: `string`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[serverPath](../interfaces/appium_types.Driver.md#serverpath)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:52

___

### serverPort

• `Optional` **serverPort**: `number`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[serverPort](../interfaces/appium_types.Driver.md#serverport)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:51

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[sessionId](../interfaces/appium_types.Driver.md#sessionid)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[sessionId](appium_base_driver.DriverCore.md#sessionid)

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

[Driver](../interfaces/appium_types.Driver.md).[settings](../interfaces/appium_types.Driver.md#settings)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[settings](appium_base_driver.DriverCore.md#settings)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:98

___

### shouldValidateCaps

• **shouldValidateCaps**: `boolean`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[shouldValidateCaps](../interfaces/appium_types.Driver.md#shouldvalidatecaps)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[shouldValidateCaps](appium_base_driver.DriverCore.md#shouldvalidatecaps)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:88

___

### shutdownUnexpectedly

• **shutdownUnexpectedly**: `boolean`

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[shutdownUnexpectedly](appium_base_driver.DriverCore.md#shutdownunexpectedly)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:86

___

### supportedLogTypes

• **supportedLogTypes**: `Readonly`<[`LogDefRecord`](../modules/appium_types.md#logdefrecord)\>

Definition of the available log types

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[supportedLogTypes](../interfaces/appium_types.Driver.md#supportedlogtypes)

#### Inherited from

[ILogCommands](../interfaces/appium_types.ILogCommands.md).[supportedLogTypes](../interfaces/appium_types.ILogCommands.md#supportedlogtypes)

#### Defined in

@appium/types/build/lib/driver.d.ts:269

___

### webLocatorStrategies

• **webLocatorStrategies**: `string`[]

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[webLocatorStrategies](../interfaces/appium_types.Driver.md#weblocatorstrategies)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[webLocatorStrategies](appium_base_driver.DriverCore.md#weblocatorstrategies)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:70

___

### baseVersion

▪ `Static` **baseVersion**: `string` = `BASEDRIVER_VER`

Make the basedriver version available so for any driver which inherits from this package, we
know which version of basedriver it inherited from

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[baseVersion](appium_base_driver.DriverCore.md#baseversion)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:38

## Accessors

### \_desiredCapConstraints

• `Protected` `get` **_desiredCapConstraints**(): `Readonly`<{ `app`: { `isString`: ``true``  } ; `autoLaunch`: { `isBoolean`: ``true``  } ; `autoWebview`: { `isBoolean`: ``true``  } ; `automationName`: { `isString`: ``true``  } ; `eventTimings`: { `isBoolean`: ``true``  } ; `fullReset`: { `isBoolean`: ``true``  } ; `language`: { `isString`: ``true``  } ; `locale`: { `isString`: ``true``  } ; `newCommandTimeout`: { `isNumber`: ``true``  } ; `noReset`: { `isBoolean`: ``true``  } ; `orientation`: { `inclusion`: readonly [``"LANDSCAPE"``, ``"PORTRAIT"``]  } ; `platformName`: { `isString`: ``true`` ; `presence`: ``true``  } ; `platformVersion`: { `isString`: ``true``  } ; `printPageSourceOnFindFailure`: { `isBoolean`: ``true``  } ; `udid`: { `isString`: ``true``  } ; `webSocketUrl`: { `isBoolean`: ``true``  }  } & `C`\>

Contains the base constraints plus whatever the subclass wants to add.

Subclasses _shouldn't_ need to use this. If you need to use this, please create
an issue:

**`See`**

[https://github.com/appium/appium/issues/new](https://github.com/appium/appium/issues/new)

#### Returns

`Readonly`<{ `app`: { `isString`: ``true``  } ; `autoLaunch`: { `isBoolean`: ``true``  } ; `autoWebview`: { `isBoolean`: ``true``  } ; `automationName`: { `isString`: ``true``  } ; `eventTimings`: { `isBoolean`: ``true``  } ; `fullReset`: { `isBoolean`: ``true``  } ; `language`: { `isString`: ``true``  } ; `locale`: { `isString`: ``true``  } ; `newCommandTimeout`: { `isNumber`: ``true``  } ; `noReset`: { `isBoolean`: ``true``  } ; `orientation`: { `inclusion`: readonly [``"LANDSCAPE"``, ``"PORTRAIT"``]  } ; `platformName`: { `isString`: ``true`` ; `presence`: ``true``  } ; `platformVersion`: { `isString`: ``true``  } ; `printPageSourceOnFindFailure`: { `isBoolean`: ``true``  } ; `udid`: { `isString`: ``true``  } ; `webSocketUrl`: { `isBoolean`: ``true``  }  } & `C`\>

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:69

___

### driverData

• `get` **driverData**(): `Object`

This property is used by AppiumDriver to store the data of the
specific driver sessions. This data can be later used to adjust
properties for driver instances running in parallel.
Override it in inherited driver classes if necessary.

#### Returns

`Object`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[driverData](../interfaces/appium_types.Driver.md#driverdata)

#### Inherited from

DriverCore.driverData

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:160

___

### eventHistory

• `get` **eventHistory**(): [`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Returns

[`EventHistory`](../interfaces/appium_types.EventHistory.md)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[eventHistory](../interfaces/appium_types.Driver.md#eventhistory)

#### Inherited from

DriverCore.eventHistory

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

[Driver](../interfaces/appium_types.Driver.md).[isCommandsQueueEnabled](../interfaces/appium_types.Driver.md#iscommandsqueueenabled)

#### Inherited from

DriverCore.isCommandsQueueEnabled

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:175

___

### log

• `get` **log**(): [`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Returns

[`AppiumLogger`](../interfaces/appium_types.AppiumLogger.md)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[log](../interfaces/appium_types.Driver.md#log)

#### Inherited from

DriverCore.log

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

[Driver](../interfaces/appium_types.Driver.md).[addManagedDriver](../interfaces/appium_types.Driver.md#addmanageddriver)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[addManagedDriver](appium_base_driver.DriverCore.md#addmanageddriver)

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

[Driver](../interfaces/appium_types.Driver.md).[assertFeatureEnabled](../interfaces/appium_types.Driver.md#assertfeatureenabled)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[assertFeatureEnabled](appium_base_driver.DriverCore.md#assertfeatureenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:290

___

### assignServer

▸ **assignServer**(`server`, `host`, `port`, `path`): `void`

A helper function used to assign server information to the driver instance so the driver knows
where the server is Running

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `server` | [`AppiumServer`](../modules/appium_types.md#appiumserver) | the server object |
| `host` | `string` | the server hostname |
| `port` | `number` | the server port |
| `path` | `string` | the server base url |

#### Returns

`void`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[assignServer](../interfaces/appium_types.Driver.md#assignserver)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:184

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

[Driver](../interfaces/appium_types.Driver.md).[canProxy](../interfaces/appium_types.Driver.md#canproxy)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[canProxy](appium_base_driver.DriverCore.md#canproxy)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:324

___

### clearNewCommandTimeout

▸ **clearNewCommandTimeout**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[clearNewCommandTimeout](../interfaces/appium_types.Driver.md#clearnewcommandtimeout)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[clearNewCommandTimeout](appium_base_driver.DriverCore.md#clearnewcommandtimeout)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:373

___

### createSession

▸ **createSession**(`w3cCapabilities1`, `w3cCapabilities2?`, `w3cCapabilities?`, `driverData?`): `Promise`<`CreateResult`\>

Historically the first two arguments were reserved for JSONWP capabilities.
Appium 2 has dropped the support of these, so now we only accept capability
objects in W3C format and thus allow any of the three arguments to represent
the latter.

#### Parameters

| Name | Type |
| :------ | :------ |
| `w3cCapabilities1` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> |
| `w3cCapabilities2?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> |
| `w3cCapabilities?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> |
| `driverData?` | [`DriverData`](../modules/appium_types.md#driverdata)[] |

#### Returns

`Promise`<`CreateResult`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[createSession](../interfaces/appium_types.Driver.md#createsession)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:232

___

### deleteSession

▸ **deleteSession**(`sessionId?`): `Promise`<`void`\>

Stop an automation session

**`See`**

[https://w3c.github.io/webdriver/#delete-session](https://w3c.github.io/webdriver/#delete-session)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `sessionId?` | ``null`` \| `string` | the id of the session that is to be deleted |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[deleteSession](../interfaces/appium_types.Driver.md#deletesession)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:340

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

[Driver](../interfaces/appium_types.Driver.md).[driverForSession](../interfaces/appium_types.Driver.md#driverforsession)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[driverForSession](appium_base_driver.DriverCore.md#driverforsession)

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

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[ensureFeatureEnabled](appium_base_driver.DriverCore.md#ensurefeatureenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:280

___

### executeCommand

▸ **executeCommand**<`T`\>(`cmd`, `...args`): `Promise`<`T`\>

This is the main command handler for the driver. It wraps command
execution with timeout logic, checking that we have a valid session,
and ensuring that we execute commands one at a time. This method is called
by MJSONWP's express router.

#### Type parameters

| Name | Type |
| :------ | :------ |
| `T` | `unknown` |

#### Parameters

| Name | Type |
| :------ | :------ |
| `cmd` | `string` |
| `...args` | `any`[] |

#### Returns

`Promise`<`T`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[executeCommand](../interfaces/appium_types.Driver.md#executecommand)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:79

___

### executeMethod

▸ **executeMethod**<`TArgs`, `TReturn`\>(`script`, `args`): `Promise`<`TReturn`\>

Call an `Execute Method` by its name with the given arguments. This method will check that the
driver has registered the method matching the name, and send it the arguments.

#### Type parameters

| Name | Type |
| :------ | :------ |
| `TArgs` | extends readonly `any`[] \| readonly [[`StringRecord`](../modules/appium_types.md#stringrecord)<`unknown`\>] = `unknown`[] |
| `TReturn` | `unknown` |

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `script` | `string` | the name of the Execute Method |
| `args` | `TArgs` | a singleton array containing an arguments object |

#### Returns

`Promise`<`TReturn`\>

The result of calling the Execute Method

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[executeMethod](../interfaces/appium_types.Driver.md#executemethod)

#### Inherited from

[IExecuteCommands](../interfaces/appium_types.IExecuteCommands.md).[executeMethod](../interfaces/appium_types.IExecuteCommands.md#executemethod)

#### Defined in

@appium/types/build/lib/driver.d.ts:148

___

### findElOrEls

▸ **findElOrEls**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A helper method that returns one or more UI elements based on the search criteria

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector |
| `mult` | ``true`` | whether or not we want to find multiple elements |
| `context?` | `any` | the element to use as the search context basis if desiredCapabilities |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A single element or list of elements

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElOrEls](../interfaces/appium_types.Driver.md#findelorels)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElOrEls](../interfaces/appium_types.IFindCommands.md#findelorels)

#### Defined in

@appium/types/build/lib/driver.d.ts:242

▸ **findElOrEls**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `strategy` | `string` |
| `selector` | `string` |
| `mult` | ``false`` |
| `context?` | `any` |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElOrEls](../interfaces/appium_types.Driver.md#findelorels)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElOrEls](../interfaces/appium_types.IFindCommands.md#findelorels)

#### Defined in

@appium/types/build/lib/driver.d.ts:243

___

### findElOrElsWithProcessing

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

This is a wrapper for [`findElOrEls`](appium_base_driver.BaseDriver.md#findelorels) that validates locator strategies
and implements the `appium:printPageSourceOnFindFailure` capability

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector |
| `mult` | ``true`` | whether or not we want to find multiple elements |
| `context?` | `any` | the element to use as the search context basis if desiredCapabilities |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A single element or list of elements

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElOrElsWithProcessing](../interfaces/appium_types.Driver.md#findelorelswithprocessing)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElOrElsWithProcessing](../interfaces/appium_types.IFindCommands.md#findelorelswithprocessing)

#### Defined in

@appium/types/build/lib/driver.d.ts:255

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `strategy` | `string` |
| `selector` | `string` |
| `mult` | ``false`` |
| `context?` | `any` |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElOrElsWithProcessing](../interfaces/appium_types.Driver.md#findelorelswithprocessing)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElOrElsWithProcessing](../interfaces/appium_types.IFindCommands.md#findelorelswithprocessing)

#### Defined in

@appium/types/build/lib/driver.d.ts:256

___

### findElement

▸ **findElement**(`strategy`, `selector`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

Find a UI element given a locator strategy and a selector, erroring if it can't be found

**`See`**

[https://w3c.github.io/webdriver/#find-element](https://w3c.github.io/webdriver/#find-element)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific element |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

The element object encoding the element id which can be used in element-related
commands

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElement](../interfaces/appium_types.Driver.md#findelement)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElement](../interfaces/appium_types.IFindCommands.md#findelement)

#### Defined in

@appium/types/build/lib/driver.d.ts:176

___

### findElementFromElement

▸ **findElementFromElement**(`strategy`, `selector`, `elementId`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

Find a UI element given a locator strategy and a selector, erroring if it can't be found. Only
look for elements among the set of descendants of a given element

**`See`**

[https://w3c.github.io/webdriver/#find-element-from-element](https://w3c.github.io/webdriver/#find-element-from-element)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific element |
| `elementId` | `string` | the id of the element to use as the search basis |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

The element object encoding the element id which can be used in element-related
commands

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElementFromElement](../interfaces/appium_types.Driver.md#findelementfromelement)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElementFromElement](../interfaces/appium_types.IFindCommands.md#findelementfromelement)

#### Defined in

@appium/types/build/lib/driver.d.ts:199

___

### findElementFromShadowRoot

▸ `Optional` **findElementFromShadowRoot**(`strategy`, `selector`, `shadowId`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

Find an element from a shadow root

**`See`**

[https://w3c.github.io/webdriver/#find-element-from-shadow-root](https://w3c.github.io/webdriver/#find-element-from-shadow-root)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific elements |
| `shadowId` | `string` | the id of the element to use as the search basis |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>\>

The element inside the shadow root matching the selector

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElementFromShadowRoot](../interfaces/appium_types.Driver.md#findelementfromshadowroot)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElementFromShadowRoot](../interfaces/appium_types.IFindCommands.md#findelementfromshadowroot)

#### Defined in

@appium/types/build/lib/driver.d.ts:221

___

### findElements

▸ **findElements**(`strategy`, `selector`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

Find a a list of all UI elements matching a given a locator strategy and a selector

**`See`**

[https://w3c.github.io/webdriver/#find-elements](https://w3c.github.io/webdriver/#find-elements)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific elements |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A possibly-empty list of element objects

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElements](../interfaces/appium_types.Driver.md#findelements)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElements](../interfaces/appium_types.IFindCommands.md#findelements)

#### Defined in

@appium/types/build/lib/driver.d.ts:186

___

### findElementsFromElement

▸ **findElementsFromElement**(`strategy`, `selector`, `elementId`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

Find a a list of all UI elements matching a given a locator strategy and a selector. Only
look for elements among the set of descendants of a given element

**`See`**

[https://w3c.github.io/webdriver/#find-elements-from-element](https://w3c.github.io/webdriver/#find-elements-from-element)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific elements |
| `elementId` | `string` | the id of the element to use as the search basis |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A possibly-empty list of element objects

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElementsFromElement](../interfaces/appium_types.Driver.md#findelementsfromelement)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElementsFromElement](../interfaces/appium_types.IFindCommands.md#findelementsfromelement)

#### Defined in

@appium/types/build/lib/driver.d.ts:211

___

### findElementsFromShadowRoot

▸ `Optional` **findElementsFromShadowRoot**(`strategy`, `selector`, `shadowId`): `Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

Find elements from a shadow root

**`See`**

[https://w3c.github.io/webdriver/#find-element-from-shadow-root](https://w3c.github.io/webdriver/#find-element-from-shadow-root)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific elements |
| `shadowId` | `string` | the id of the element to use as the search basis |

#### Returns

`Promise`<[`Element`](../interfaces/appium_types.Element.md)<`string`\>[]\>

A possibly empty list of elements inside the shadow root matching the selector

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[findElementsFromShadowRoot](../interfaces/appium_types.Driver.md#findelementsfromshadowroot)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[findElementsFromShadowRoot](../interfaces/appium_types.IFindCommands.md#findelementsfromshadowroot)

#### Defined in

@appium/types/build/lib/driver.d.ts:231

___

### getLog

▸ **getLog**(`logType`): `Promise`<`any`\>

Get the log for a given log type.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `logType` | `string` | Name/key of log type as defined in ILogCommands.supportedLogTypes. |

#### Returns

`Promise`<`any`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getLog](../interfaces/appium_types.Driver.md#getlog)

#### Inherited from

[ILogCommands](../interfaces/appium_types.ILogCommands.md).[getLog](../interfaces/appium_types.ILogCommands.md#getlog)

#### Defined in

@appium/types/build/lib/driver.d.ts:279

___

### getLogEvents

▸ **getLogEvents**(`type?`): `Promise`<[`EventHistory`](../interfaces/appium_types.EventHistory.md) \| `Record`<`string`, `number`\>\>

Get a list of events that have occurred in the current session

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `type?` | `string` \| `string`[] | filter the returned events by including one or more types |

#### Returns

`Promise`<[`EventHistory`](../interfaces/appium_types.EventHistory.md) \| `Record`<`string`, `number`\>\>

The event history for the session

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getLogEvents](../interfaces/appium_types.Driver.md#getlogevents)

#### Inherited from

[IEventCommands](../interfaces/appium_types.IEventCommands.md).[getLogEvents](../interfaces/appium_types.IEventCommands.md#getlogevents)

#### Defined in

@appium/types/build/lib/driver.d.ts:136

___

### getLogTypes

▸ **getLogTypes**(): `Promise`<`string`[]\>

Get available log types as a list of strings

#### Returns

`Promise`<`string`[]\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getLogTypes](../interfaces/appium_types.Driver.md#getlogtypes)

#### Inherited from

[ILogCommands](../interfaces/appium_types.ILogCommands.md).[getLogTypes](../interfaces/appium_types.ILogCommands.md#getlogtypes)

#### Defined in

@appium/types/build/lib/driver.d.ts:273

___

### getManagedDrivers

▸ **getManagedDrivers**(): [`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Returns

[`Driver`](../interfaces/appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getManagedDrivers](../interfaces/appium_types.Driver.md#getmanageddrivers)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[getManagedDrivers](appium_base_driver.DriverCore.md#getmanageddrivers)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:369

___

### getPageSource

▸ **getPageSource**(): `Promise`<`string`\>

Get the current page/app source as HTML/XML

**`See`**

[https://w3c.github.io/webdriver/#get-page-source](https://w3c.github.io/webdriver/#get-page-source)

#### Returns

`Promise`<`string`\>

The UI hierarchy in a platform-appropriate format (e.g., HTML for a web page)

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getPageSource](../interfaces/appium_types.Driver.md#getpagesource)

#### Inherited from

[IFindCommands](../interfaces/appium_types.IFindCommands.md).[getPageSource](../interfaces/appium_types.IFindCommands.md#getpagesource)

#### Defined in

@appium/types/build/lib/driver.d.ts:263

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

[Driver](../interfaces/appium_types.Driver.md).[getProxyAvoidList](../interfaces/appium_types.Driver.md#getproxyavoidlist)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[getProxyAvoidList](appium_base_driver.DriverCore.md#getproxyavoidlist)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:320

___

### getSession

▸ **getSession**(): `Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

Returns capabilities for the session and event history (if applicable)

#### Returns

`Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getSession](../interfaces/appium_types.Driver.md#getsession)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:333

___

### getSessions

▸ **getSessions**(): `Promise`<[`MultiSessionData`](../interfaces/appium_types.MultiSessionData.md)<`C`\>[]\>

Get data for all sessions running on an Appium server

#### Returns

`Promise`<[`MultiSessionData`](../interfaces/appium_types.MultiSessionData.md)<`C`\>[]\>

A list of session data objects

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getSessions](../interfaces/appium_types.Driver.md#getsessions)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:317

___

### getSettings

▸ **getSettings**(): `Promise`<`Settings`\>

Get the current settings for the session

#### Returns

`Promise`<`Settings`\>

The settings object

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getSettings](../interfaces/appium_types.Driver.md#getsettings)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:394

___

### getStatus

▸ **getStatus**(): `Promise`<{}\>

#### Returns

`Promise`<{}\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getStatus](../interfaces/appium_types.Driver.md#getstatus)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[getStatus](appium_base_driver.DriverCore.md#getstatus)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:210

___

### getTimeouts

▸ **getTimeouts**(): `Promise`<`Record`<`string`, `number`\>\>

Get the current timeouts

**`See`**

[https://w3c.github.io/webdriver/#get-timeouts](https://w3c.github.io/webdriver/#get-timeouts)

#### Returns

`Promise`<`Record`<`string`, `number`\>\>

A map of timeout names to ms values

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[getTimeouts](../interfaces/appium_types.Driver.md#gettimeouts)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[getTimeouts](../interfaces/appium_types.ITimeoutCommands.md#gettimeouts)

#### Defined in

@appium/types/build/lib/driver.d.ts:66

___

### implicitWait

▸ **implicitWait**(`ms`): `Promise`<`void`\>

Set the implicit wait timeout

**`Deprecated`**

Use `timeouts` instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `string` \| `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[implicitWait](../interfaces/appium_types.Driver.md#implicitwait)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[implicitWait](../interfaces/appium_types.ITimeoutCommands.md#implicitwait)

#### Defined in

@appium/types/build/lib/driver.d.ts:45

___

### implicitWaitForCondition

▸ **implicitWaitForCondition**(`condition`): `Promise`<`unknown`\>

Periodically retry an async function up until the currently set implicit wait timeout

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `condition` | (...`args`: `any`[]) => `Promise`<`any`\> | the behaviour to retry until it returns truthy |

#### Returns

`Promise`<`unknown`\>

The return value of the condition

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[implicitWaitForCondition](../interfaces/appium_types.Driver.md#implicitwaitforcondition)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[implicitWaitForCondition](../interfaces/appium_types.ITimeoutCommands.md#implicitwaitforcondition)

#### Defined in

@appium/types/build/lib/driver.d.ts:59

___

### implicitWaitMJSONWP

▸ **implicitWaitMJSONWP**(`ms`): `Promise`<`void`\>

Set the implicit wait value that was sent in via the JSONWP

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[implicitWaitMJSONWP](../interfaces/appium_types.Driver.md#implicitwaitmjsonwp)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[implicitWaitMJSONWP](../interfaces/appium_types.ITimeoutCommands.md#implicitwaitmjsonwp)

#### Defined in

@appium/types/build/lib/driver.d.ts:79

___

### implicitWaitW3C

▸ **implicitWaitW3C**(`ms`): `Promise`<`void`\>

Set the implicit wait value that was sent in via the W3C protocol

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[implicitWaitW3C](../interfaces/appium_types.Driver.md#implicitwaitw3c)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[implicitWaitW3C](../interfaces/appium_types.ITimeoutCommands.md#implicitwaitw3c)

#### Defined in

@appium/types/build/lib/driver.d.ts:72

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

[Driver](../interfaces/appium_types.Driver.md).[isFeatureEnabled](../interfaces/appium_types.Driver.md#isfeatureenabled)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[isFeatureEnabled](appium_base_driver.DriverCore.md#isfeatureenabled)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:252

___

### isMjsonwpProtocol

▸ **isMjsonwpProtocol**(): `boolean`

#### Returns

`boolean`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[isMjsonwpProtocol](../interfaces/appium_types.Driver.md#ismjsonwpprotocol)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[isMjsonwpProtocol](appium_base_driver.DriverCore.md#ismjsonwpprotocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:231

___

### isW3CProtocol

▸ **isW3CProtocol**(): `boolean`

#### Returns

`boolean`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[isW3CProtocol](../interfaces/appium_types.Driver.md#isw3cprotocol)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[isW3CProtocol](appium_base_driver.DriverCore.md#isw3cprotocol)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:235

___

### logCustomEvent

▸ **logCustomEvent**(`vendor`, `event`): `Promise`<`void`\>

Add a custom-named event to the Appium event log

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `vendor` | `string` | the name of the vendor or tool the event belongs to, to namespace the event |
| `event` | `string` | the name of the event itself |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[logCustomEvent](../interfaces/appium_types.Driver.md#logcustomevent)

#### Inherited from

[IEventCommands](../interfaces/appium_types.IEventCommands.md).[logCustomEvent](../interfaces/appium_types.IEventCommands.md#logcustomevent)

#### Defined in

@appium/types/build/lib/driver.d.ts:128

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

[Driver](../interfaces/appium_types.Driver.md).[logEvent](../interfaces/appium_types.Driver.md#logevent)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[logEvent](appium_base_driver.DriverCore.md#logevent)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:190

___

### logExtraCaps

▸ **logExtraCaps**(`caps`): `void`

A helper function to log unrecognized capabilities to the console

**`Params`**

caps - the capabilities

#### Parameters

| Name | Type |
| :------ | :------ |
| `caps` | [`ConstraintsToCaps`](../modules/appium_types.md#constraintstocaps)<`C`\> |

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:354

___

### newCommandTimeout

▸ **newCommandTimeout**(`ms`): `Promise`<`void`\>

Set Appium's new command timeout

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[newCommandTimeout](../interfaces/appium_types.Driver.md#newcommandtimeout)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[newCommandTimeout](../interfaces/appium_types.ITimeoutCommands.md#newcommandtimeout)

#### Defined in

@appium/types/build/lib/driver.d.ts:111

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

[Driver](../interfaces/appium_types.Driver.md).[onUnexpectedShutdown](../interfaces/appium_types.Driver.md#onunexpectedshutdown)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[onUnexpectedShutdown](appium_base_driver.DriverCore.md#onunexpectedshutdown)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:150

___

### pageLoadTimeoutMJSONWP

▸ **pageLoadTimeoutMJSONWP**(`ms`): `Promise`<`void`\>

Set the page load timeout value that was sent in via the JSONWP

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[pageLoadTimeoutMJSONWP](../interfaces/appium_types.Driver.md#pageloadtimeoutmjsonwp)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[pageLoadTimeoutMJSONWP](../interfaces/appium_types.ITimeoutCommands.md#pageloadtimeoutmjsonwp)

#### Defined in

@appium/types/build/lib/driver.d.ts:92

___

### pageLoadTimeoutW3C

▸ **pageLoadTimeoutW3C**(`ms`): `Promise`<`void`\>

Set the page load timeout value that was sent in via the W3C protocol

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[pageLoadTimeoutW3C](../interfaces/appium_types.Driver.md#pageloadtimeoutw3c)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[pageLoadTimeoutW3C](../interfaces/appium_types.ITimeoutCommands.md#pageloadtimeoutw3c)

#### Defined in

@appium/types/build/lib/driver.d.ts:85

___

### parseTimeoutArgument

▸ **parseTimeoutArgument**(`ms`): `number`

Get a timeout value from a number or a string

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `string` \| `number` | the timeout value as a number or a string |

#### Returns

`number`

The timeout as a number in ms

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[parseTimeoutArgument](../interfaces/appium_types.Driver.md#parsetimeoutargument)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[parseTimeoutArgument](../interfaces/appium_types.ITimeoutCommands.md#parsetimeoutargument)

#### Defined in

@appium/types/build/lib/driver.d.ts:119

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

[Driver](../interfaces/appium_types.Driver.md).[proxyActive](../interfaces/appium_types.Driver.md#proxyactive)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[proxyActive](appium_base_driver.DriverCore.md#proxyactive)

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

[Driver](../interfaces/appium_types.Driver.md).[proxyRouteIsAvoided](../interfaces/appium_types.Driver.md#proxyrouteisavoided)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[proxyRouteIsAvoided](appium_base_driver.DriverCore.md#proxyrouteisavoided)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:341

___

### reset

▸ **reset**(): `Promise`<`void`\>

Reset the current session (run the delete session and create session subroutines)

**`Deprecated`**

Use explicit session management commands instead

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[reset](../interfaces/appium_types.Driver.md#reset)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:195

___

### scriptTimeoutMJSONWP

▸ **scriptTimeoutMJSONWP**(`ms`): `Promise`<`void`\>

Set the script timeout value that was sent in via the JSONWP

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[scriptTimeoutMJSONWP](../interfaces/appium_types.Driver.md#scripttimeoutmjsonwp)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[scriptTimeoutMJSONWP](../interfaces/appium_types.ITimeoutCommands.md#scripttimeoutmjsonwp)

#### Defined in

@appium/types/build/lib/driver.d.ts:105

___

### scriptTimeoutW3C

▸ **scriptTimeoutW3C**(`ms`): `Promise`<`void`\>

Set the script timeout value that was sent in via the W3C protocol

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[scriptTimeoutW3C](../interfaces/appium_types.Driver.md#scripttimeoutw3c)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[scriptTimeoutW3C](../interfaces/appium_types.ITimeoutCommands.md#scripttimeoutw3c)

#### Defined in

@appium/types/build/lib/driver.d.ts:98

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

[Driver](../interfaces/appium_types.Driver.md).[sessionExists](../interfaces/appium_types.Driver.md#sessionexists)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[sessionExists](appium_base_driver.DriverCore.md#sessionexists)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:218

___

### setImplicitWait

▸ **setImplicitWait**(`ms`): `void`

A helper method (not a command) used to set the implicit wait value

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the implicit wait in ms |

#### Returns

`void`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[setImplicitWait](../interfaces/appium_types.Driver.md#setimplicitwait)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[setImplicitWait](../interfaces/appium_types.ITimeoutCommands.md#setimplicitwait)

#### Defined in

@appium/types/build/lib/driver.d.ts:51

___

### setNewCommandTimeout

▸ **setNewCommandTimeout**(`ms`): `void`

Set the new command timeout

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `ms` | `number` | the timeout in ms |

#### Returns

`void`

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[setNewCommandTimeout](../interfaces/appium_types.Driver.md#setnewcommandtimeout)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[setNewCommandTimeout](../interfaces/appium_types.ITimeoutCommands.md#setnewcommandtimeout)

#### Defined in

@appium/types/build/lib/driver.d.ts:36

___

### setProtocolMJSONWP

▸ **setProtocolMJSONWP**(): `void`

#### Returns

`void`

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[setProtocolMJSONWP](appium_base_driver.DriverCore.md#setprotocolmjsonwp)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:239

___

### setProtocolW3C

▸ **setProtocolW3C**(): `void`

#### Returns

`void`

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[setProtocolW3C](appium_base_driver.DriverCore.md#setprotocolw3c)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:243

___

### startNewCommandTimeout

▸ **startNewCommandTimeout**(): `Promise`<`void`\>

Start the timer for the New Command Timeout, which when it runs out, will stop the current
session

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[startNewCommandTimeout](../interfaces/appium_types.Driver.md#startnewcommandtimeout)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:163

___

### startUnexpectedShutdown

▸ **startUnexpectedShutdown**(`err?`): `Promise`<`void`\>

Signify to any owning processes that this driver encountered an error which should cause the
session to terminate immediately (for example an upstream service failed)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `err` | `Error` | the Error object which is causing the shutdown |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[startUnexpectedShutdown](../interfaces/appium_types.Driver.md#startunexpectedshutdown)

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:149

___

### timeouts

▸ **timeouts**(`type`, `ms`, `script?`, `pageLoad?`, `implicit?`): `Promise`<`void`\>

Set the various timeouts associated with a session

**`See`**

[https://w3c.github.io/webdriver/#set-timeouts](https://w3c.github.io/webdriver/#set-timeouts)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `type` | `string` | used only for the old (JSONWP) command, the type of the timeout |
| `ms` | `string` \| `number` | used only for the old (JSONWP) command, the ms for the timeout |
| `script?` | `number` | the number in ms for the script timeout, used for the W3C command |
| `pageLoad?` | `number` | the number in ms for the pageLoad timeout, used for the W3C command |
| `implicit?` | `string` \| `number` | the number in ms for the implicit wait timeout, used for the W3C command |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Driver](../interfaces/appium_types.Driver.md).[timeouts](../interfaces/appium_types.Driver.md#timeouts)

#### Inherited from

[ITimeoutCommands](../interfaces/appium_types.ITimeoutCommands.md).[timeouts](../interfaces/appium_types.ITimeoutCommands.md#timeouts)

#### Defined in

@appium/types/build/lib/driver.d.ts:30

___

### updateSettings

▸ **updateSettings**(`newSettings`): `Promise`<`void`\>

Update the session's settings dictionary with a new settings object

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `newSettings` | `Settings` | A key-value map of setting names to values. Settings not named in the map will not have their value adjusted. |

#### Returns

`Promise`<`void`\>

#### Implementation of

Driver.updateSettings

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:387

___

### validateDesiredCaps

▸ **validateDesiredCaps**(`caps`): caps is DriverCaps<C\>

Validate the capabilities used to start a session

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `caps` | `any` | the capabilities |

#### Returns

caps is DriverCaps<C\>

Whether or not the capabilities are valid

#### Defined in

@appium/base-driver/lib/basedriver/driver.ts:366

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

[Driver](../interfaces/appium_types.Driver.md).[validateLocatorStrategy](../interfaces/appium_types.Driver.md#validatelocatorstrategy)

#### Inherited from

[DriverCore](appium_base_driver.DriverCore.md).[validateLocatorStrategy](appium_base_driver.DriverCore.md#validatelocatorstrategy)

#### Defined in

@appium/base-driver/lib/basedriver/core.ts:301
