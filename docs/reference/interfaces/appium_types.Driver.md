# Interface: Driver<C, CArgs, Settings, CreateResult, DeleteResult, SessionData\>

[@appium/types](../modules/appium_types.md).Driver

`BaseDriver` implements this.  It contains default behavior;
external drivers are expected to implement [`ExternalDriver`](appium_types.ExternalDriver.md) instead.

`C` should be the constraints of the driver.
`CArgs` would be the shape of `cliArgs`.
`Settings` is the shape of the raw device settings object (see [`IDeviceSettings`](appium_types.IDeviceSettings.md))

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) = [`Constraints`](../modules/appium_types.md#constraints) |
| `CArgs` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `CreateResult` | [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<`C`\> |
| `DeleteResult` | [`DefaultDeleteSessionResult`](../modules/appium_types.md#defaultdeletesessionresult) |
| `SessionData` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- [`ILogCommands`](appium_types.ILogCommands.md)

- [`IFindCommands`](appium_types.IFindCommands.md)

- [`ISettingsCommands`](appium_types.ISettingsCommands.md)<`Settings`\>

- [`ITimeoutCommands`](appium_types.ITimeoutCommands.md)

- [`IEventCommands`](appium_types.IEventCommands.md)

- [`IExecuteCommands`](appium_types.IExecuteCommands.md)

- [`ISessionHandler`](appium_types.ISessionHandler.md)<`C`, `CreateResult`, `DeleteResult`, `SessionData`\>

- [`Core`](appium_types.Core.md)<`C`, `Settings`\>

  ↳ **`Driver`**

  ↳↳ [`ExternalDriver`](appium_types.ExternalDriver.md)

## Table of contents

### Properties

- [allowInsecure](appium_types.Driver.md#allowinsecure)
- [basePath](appium_types.Driver.md#basepath)
- [caps](appium_types.Driver.md#caps)
- [cliArgs](appium_types.Driver.md#cliargs)
- [denyInsecure](appium_types.Driver.md#denyinsecure)
- [desiredCapConstraints](appium_types.Driver.md#desiredcapconstraints)
- [driverData](appium_types.Driver.md#driverdata)
- [eventEmitter](appium_types.Driver.md#eventemitter)
- [eventHistory](appium_types.Driver.md#eventhistory)
- [helpers](appium_types.Driver.md#helpers)
- [implicitWaitMs](appium_types.Driver.md#implicitwaitms)
- [initialOpts](appium_types.Driver.md#initialopts)
- [isCommandsQueueEnabled](appium_types.Driver.md#iscommandsqueueenabled)
- [locatorStrategies](appium_types.Driver.md#locatorstrategies)
- [log](appium_types.Driver.md#log)
- [newCommandTimeoutMs](appium_types.Driver.md#newcommandtimeoutms)
- [opts](appium_types.Driver.md#opts)
- [originalCaps](appium_types.Driver.md#originalcaps)
- [protocol](appium_types.Driver.md#protocol)
- [relaxedSecurityEnabled](appium_types.Driver.md#relaxedsecurityenabled)
- [server](appium_types.Driver.md#server)
- [serverHost](appium_types.Driver.md#serverhost)
- [serverPath](appium_types.Driver.md#serverpath)
- [serverPort](appium_types.Driver.md#serverport)
- [sessionId](appium_types.Driver.md#sessionid)
- [settings](appium_types.Driver.md#settings)
- [shouldValidateCaps](appium_types.Driver.md#shouldvalidatecaps)
- [supportedLogTypes](appium_types.Driver.md#supportedlogtypes)
- [updateSettings](appium_types.Driver.md#updatesettings)
- [webLocatorStrategies](appium_types.Driver.md#weblocatorstrategies)

### Methods

- [addManagedDriver](appium_types.Driver.md#addmanageddriver)
- [assertFeatureEnabled](appium_types.Driver.md#assertfeatureenabled)
- [assignServer](appium_types.Driver.md#assignserver)
- [canProxy](appium_types.Driver.md#canproxy)
- [clearNewCommandTimeout](appium_types.Driver.md#clearnewcommandtimeout)
- [createSession](appium_types.Driver.md#createsession)
- [deleteSession](appium_types.Driver.md#deletesession)
- [driverForSession](appium_types.Driver.md#driverforsession)
- [executeCommand](appium_types.Driver.md#executecommand)
- [executeMethod](appium_types.Driver.md#executemethod)
- [findElOrEls](appium_types.Driver.md#findelorels)
- [findElOrElsWithProcessing](appium_types.Driver.md#findelorelswithprocessing)
- [findElement](appium_types.Driver.md#findelement)
- [findElementFromElement](appium_types.Driver.md#findelementfromelement)
- [findElementFromShadowRoot](appium_types.Driver.md#findelementfromshadowroot)
- [findElements](appium_types.Driver.md#findelements)
- [findElementsFromElement](appium_types.Driver.md#findelementsfromelement)
- [findElementsFromShadowRoot](appium_types.Driver.md#findelementsfromshadowroot)
- [getLog](appium_types.Driver.md#getlog)
- [getLogEvents](appium_types.Driver.md#getlogevents)
- [getLogTypes](appium_types.Driver.md#getlogtypes)
- [getManagedDrivers](appium_types.Driver.md#getmanageddrivers)
- [getPageSource](appium_types.Driver.md#getpagesource)
- [getProxyAvoidList](appium_types.Driver.md#getproxyavoidlist)
- [getSession](appium_types.Driver.md#getsession)
- [getSessions](appium_types.Driver.md#getsessions)
- [getSettings](appium_types.Driver.md#getsettings)
- [getStatus](appium_types.Driver.md#getstatus)
- [getTimeouts](appium_types.Driver.md#gettimeouts)
- [implicitWait](appium_types.Driver.md#implicitwait)
- [implicitWaitForCondition](appium_types.Driver.md#implicitwaitforcondition)
- [implicitWaitMJSONWP](appium_types.Driver.md#implicitwaitmjsonwp)
- [implicitWaitW3C](appium_types.Driver.md#implicitwaitw3c)
- [isFeatureEnabled](appium_types.Driver.md#isfeatureenabled)
- [isMjsonwpProtocol](appium_types.Driver.md#ismjsonwpprotocol)
- [isW3CProtocol](appium_types.Driver.md#isw3cprotocol)
- [logCustomEvent](appium_types.Driver.md#logcustomevent)
- [logEvent](appium_types.Driver.md#logevent)
- [logExtraCaps](appium_types.Driver.md#logextracaps)
- [newCommandTimeout](appium_types.Driver.md#newcommandtimeout)
- [onUnexpectedShutdown](appium_types.Driver.md#onunexpectedshutdown)
- [pageLoadTimeoutMJSONWP](appium_types.Driver.md#pageloadtimeoutmjsonwp)
- [pageLoadTimeoutW3C](appium_types.Driver.md#pageloadtimeoutw3c)
- [parseTimeoutArgument](appium_types.Driver.md#parsetimeoutargument)
- [proxyActive](appium_types.Driver.md#proxyactive)
- [proxyRouteIsAvoided](appium_types.Driver.md#proxyrouteisavoided)
- [reset](appium_types.Driver.md#reset)
- [scriptTimeoutMJSONWP](appium_types.Driver.md#scripttimeoutmjsonwp)
- [scriptTimeoutW3C](appium_types.Driver.md#scripttimeoutw3c)
- [sessionExists](appium_types.Driver.md#sessionexists)
- [setImplicitWait](appium_types.Driver.md#setimplicitwait)
- [setNewCommandTimeout](appium_types.Driver.md#setnewcommandtimeout)
- [startNewCommandTimeout](appium_types.Driver.md#startnewcommandtimeout)
- [startUnexpectedShutdown](appium_types.Driver.md#startunexpectedshutdown)
- [timeouts](appium_types.Driver.md#timeouts)
- [validateDesiredCaps](appium_types.Driver.md#validatedesiredcaps)
- [validateLocatorStrategy](appium_types.Driver.md#validatelocatorstrategy)

## Properties

### allowInsecure

• **allowInsecure**: `string`[]

#### Inherited from

[Core](appium_types.Core.md).[allowInsecure](appium_types.Core.md#allowinsecure)

#### Defined in

@appium/types/lib/driver.ts:587

___

### basePath

• **basePath**: `string`

#### Inherited from

[Core](appium_types.Core.md).[basePath](appium_types.Core.md#basepath)

#### Defined in

@appium/types/lib/driver.ts:585

___

### caps

• `Optional` **caps**: [`ConstraintsToCaps`](../modules/appium_types.md#constraintstocaps)<`C`\>

The processed capabilities used to start the session represented by the current driver instance

#### Defined in

@appium/types/lib/driver.ts:719

___

### cliArgs

• **cliArgs**: `CArgs`

The set of command line arguments set for this driver

#### Defined in

@appium/types/lib/driver.ts:675

___

### denyInsecure

• **denyInsecure**: `string`[]

#### Inherited from

[Core](appium_types.Core.md).[denyInsecure](appium_types.Core.md#denyinsecure)

#### Defined in

@appium/types/lib/driver.ts:588

___

### desiredCapConstraints

• **desiredCapConstraints**: `C`

The constraints object used to validate capabilities

#### Defined in

@appium/types/lib/driver.ts:729

___

### driverData

• **driverData**: [`DriverData`](../modules/appium_types.md#driverdata)

#### Inherited from

[Core](appium_types.Core.md).[driverData](appium_types.Core.md#driverdata)

#### Defined in

@appium/types/lib/driver.ts:596

___

### eventEmitter

• **eventEmitter**: `EventEmitter`

#### Inherited from

[Core](appium_types.Core.md).[eventEmitter](appium_types.Core.md#eventemitter)

#### Defined in

@appium/types/lib/driver.ts:593

___

### eventHistory

• **eventHistory**: [`EventHistory`](appium_types.EventHistory.md)

#### Inherited from

[Core](appium_types.Core.md).[eventHistory](appium_types.Core.md#eventhistory)

#### Defined in

@appium/types/lib/driver.ts:598

___

### helpers

• **helpers**: [`DriverHelpers`](appium_types.DriverHelpers.md)

#### Inherited from

[Core](appium_types.Core.md).[helpers](appium_types.Core.md#helpers)

#### Defined in

@appium/types/lib/driver.ts:584

___

### implicitWaitMs

• **implicitWaitMs**: `number`

#### Inherited from

[Core](appium_types.Core.md).[implicitWaitMs](appium_types.Core.md#implicitwaitms)

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

#### Inherited from

[Core](appium_types.Core.md).[initialOpts](appium_types.Core.md#initialopts)

#### Defined in

@appium/types/lib/driver.ts:582

___

### isCommandsQueueEnabled

• **isCommandsQueueEnabled**: `boolean`

#### Inherited from

[Core](appium_types.Core.md).[isCommandsQueueEnabled](appium_types.Core.md#iscommandsqueueenabled)

#### Defined in

@appium/types/lib/driver.ts:597

___

### locatorStrategies

• **locatorStrategies**: `string`[]

#### Inherited from

[Core](appium_types.Core.md).[locatorStrategies](appium_types.Core.md#locatorstrategies)

#### Defined in

@appium/types/lib/driver.ts:591

___

### log

• **log**: [`AppiumLogger`](appium_types.AppiumLogger.md)

#### Inherited from

[Core](appium_types.Core.md).[log](appium_types.Core.md#log)

#### Defined in

@appium/types/lib/driver.ts:595

___

### newCommandTimeoutMs

• **newCommandTimeoutMs**: `number`

#### Inherited from

[Core](appium_types.Core.md).[newCommandTimeoutMs](appium_types.Core.md#newcommandtimeoutms)

#### Defined in

@appium/types/lib/driver.ts:589

___

### opts

• **opts**: [`DriverOpts`](../modules/appium_types.md#driveropts)<`C`\>

#### Inherited from

[Core](appium_types.Core.md).[opts](appium_types.Core.md#opts)

#### Defined in

@appium/types/lib/driver.ts:581

___

### originalCaps

• `Optional` **originalCaps**: [`W3CCapabilities`](appium_types.W3CCapabilities.md)<`C`\>

The original capabilities used to start the session represented by the current driver instance

#### Defined in

@appium/types/lib/driver.ts:724

___

### protocol

• `Optional` **protocol**: [`Protocol`](../modules/appium_types.md#protocol)

#### Inherited from

[Core](appium_types.Core.md).[protocol](appium_types.Core.md#protocol)

#### Defined in

@appium/types/lib/driver.ts:583

___

### relaxedSecurityEnabled

• **relaxedSecurityEnabled**: `boolean`

#### Inherited from

[Core](appium_types.Core.md).[relaxedSecurityEnabled](appium_types.Core.md#relaxedsecurityenabled)

#### Defined in

@appium/types/lib/driver.ts:586

___

### server

• `Optional` **server**: [`AppiumServer`](../modules/appium_types.md#appiumserver)

#### Defined in

@appium/types/lib/driver.ts:677

___

### serverHost

• `Optional` **serverHost**: `string`

#### Defined in

@appium/types/lib/driver.ts:678

___

### serverPath

• `Optional` **serverPath**: `string`

#### Defined in

@appium/types/lib/driver.ts:680

___

### serverPort

• `Optional` **serverPort**: `number`

#### Defined in

@appium/types/lib/driver.ts:679

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Inherited from

[Core](appium_types.Core.md).[sessionId](appium_types.Core.md#sessionid)

#### Defined in

@appium/types/lib/driver.ts:580

___

### settings

• **settings**: [`IDeviceSettings`](appium_types.IDeviceSettings.md)<`Settings`\>

#### Inherited from

[Core](appium_types.Core.md).[settings](appium_types.Core.md#settings)

#### Defined in

@appium/types/lib/driver.ts:594

___

### shouldValidateCaps

• **shouldValidateCaps**: `boolean`

#### Inherited from

[Core](appium_types.Core.md).[shouldValidateCaps](appium_types.Core.md#shouldvalidatecaps)

#### Defined in

@appium/types/lib/driver.ts:579

___

### supportedLogTypes

• **supportedLogTypes**: `Readonly`<[`LogDefRecord`](../modules/appium_types.md#logdefrecord)\>

Definition of the available log types

#### Inherited from

[ILogCommands](appium_types.ILogCommands.md).[supportedLogTypes](appium_types.ILogCommands.md#supportedlogtypes)

#### Defined in

@appium/types/lib/driver.ts:334

___

### updateSettings

• **updateSettings**: (`settings`: `Settings`) => `Promise`<`void`\>

#### Type declaration

▸ (`settings`): `Promise`<`void`\>

Update the session's settings dictionary with a new settings object

##### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `settings` | `Settings` | A key-value map of setting names to values. Settings not named in the map will not have their value adjusted. |

##### Returns

`Promise`<`void`\>

#### Inherited from

[ISettingsCommands](appium_types.ISettingsCommands.md).[updateSettings](appium_types.ISettingsCommands.md#updatesettings)

#### Defined in

@appium/types/lib/driver.ts:381

___

### webLocatorStrategies

• **webLocatorStrategies**: `string`[]

#### Inherited from

[Core](appium_types.Core.md).[webLocatorStrategies](appium_types.Core.md#weblocatorstrategies)

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

#### Inherited from

[Core](appium_types.Core.md).[addManagedDriver](appium_types.Core.md#addmanageddriver)

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

#### Inherited from

[Core](appium_types.Core.md).[assertFeatureEnabled](appium_types.Core.md#assertfeatureenabled)

#### Defined in

@appium/types/lib/driver.ts:636

___

### assignServer

▸ `Optional` **assignServer**(`server`, `host`, `port`, `path`): `void`

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

#### Defined in

@appium/types/lib/driver.ts:760

___

### canProxy

▸ **canProxy**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[canProxy](appium_types.Core.md#canproxy)

#### Defined in

@appium/types/lib/driver.ts:640

___

### clearNewCommandTimeout

▸ **clearNewCommandTimeout**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Inherited from

[Core](appium_types.Core.md).[clearNewCommandTimeout](appium_types.Core.md#clearnewcommandtimeout)

#### Defined in

@appium/types/lib/driver.ts:644

___

### createSession

▸ **createSession**(`w3cCaps1`, `w3cCaps2?`, `w3cCaps3?`, `driverData?`): `Promise`<`CreateResult`\>

Start a new automation session

**`See`**

[https://w3c.github.io/webdriver/#new-session](https://w3c.github.io/webdriver/#new-session)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `w3cCaps1` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | the new session capabilities |
| `w3cCaps2?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | another place the new session capabilities could be sent (typically left undefined) |
| `w3cCaps3?` | [`W3CDriverCaps`](../modules/appium_types.md#w3cdrivercaps)<`C`\> | another place the new session capabilities could be sent (typically left undefined) |
| `driverData?` | [`DriverData`](../modules/appium_types.md#driverdata)[] | a list of DriverData objects representing other sessions running for this driver on the same Appium server. This information can be used to help ensure no conflict of resources |

#### Returns

`Promise`<`CreateResult`\>

The capabilities object representing the created session

#### Inherited from

[ISessionHandler](appium_types.ISessionHandler.md).[createSession](appium_types.ISessionHandler.md#createsession)

#### Defined in

@appium/types/lib/driver.ts:430

___

### deleteSession

▸ **deleteSession**(`sessionId?`, `driverData?`): `Promise`<`void` \| `DeleteResult`\>

Stop an automation session

**`See`**

[https://w3c.github.io/webdriver/#delete-session](https://w3c.github.io/webdriver/#delete-session)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `sessionId?` | `string` | the id of the session that is to be deleted |
| `driverData?` | [`DriverData`](../modules/appium_types.md#driverdata)[] | the driver data for other currently-running sessions |

#### Returns

`Promise`<`void` \| `DeleteResult`\>

#### Inherited from

[ISessionHandler](appium_types.ISessionHandler.md).[deleteSession](appium_types.ISessionHandler.md#deletesession)

#### Defined in

@appium/types/lib/driver.ts:444

___

### driverForSession

▸ **driverForSession**(`sessionId`): ``null`` \| [`Core`](appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId` | `string` |

#### Returns

``null`` \| [`Core`](appium_types.Core.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord)\>

#### Inherited from

[Core](appium_types.Core.md).[driverForSession](appium_types.Core.md#driverforsession)

#### Defined in

@appium/types/lib/driver.ts:646

___

### executeCommand

▸ **executeCommand**(`cmd`, `...args`): `Promise`<`any`\>

Execute a driver (WebDriver-protocol) command by its name as defined in the routes file

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `cmd` | `string` | the name of the command |
| `...args` | `any`[] | arguments to pass to the command |

#### Returns

`Promise`<`any`\>

The result of running the command

#### Defined in

@appium/types/lib/driver.ts:692

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

#### Inherited from

[IExecuteCommands](appium_types.IExecuteCommands.md).[executeMethod](appium_types.IExecuteCommands.md#executemethod)

#### Defined in

@appium/types/lib/driver.ts:170

___

### findElOrEls

▸ **findElOrEls**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A helper method that returns one or more UI elements based on the search criteria

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector |
| `mult` | ``true`` | whether or not we want to find multiple elements |
| `context?` | `any` | the element to use as the search context basis if desiredCapabilities |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A single element or list of elements

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElOrEls](appium_types.IFindCommands.md#findelorels)

#### Defined in

@appium/types/lib/driver.ts:294

▸ **findElOrEls**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `strategy` | `string` |
| `selector` | `string` |
| `mult` | ``false`` |
| `context?` | `any` |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElOrEls](appium_types.IFindCommands.md#findelorels)

#### Defined in

@appium/types/lib/driver.ts:295

___

### findElOrElsWithProcessing

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

This is a wrapper for [`findElOrEls`](appium_types.Driver.md#findelorels) that validates locator strategies
and implements the `appium:printPageSourceOnFindFailure` capability

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector |
| `mult` | ``true`` | whether or not we want to find multiple elements |
| `context?` | `any` | the element to use as the search context basis if desiredCapabilities |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A single element or list of elements

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElOrElsWithProcessing](appium_types.IFindCommands.md#findelorelswithprocessing)

#### Defined in

@appium/types/lib/driver.ts:308

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `strategy` | `string` |
| `selector` | `string` |
| `mult` | ``false`` |
| `context?` | `any` |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElOrElsWithProcessing](appium_types.IFindCommands.md#findelorelswithprocessing)

#### Defined in

@appium/types/lib/driver.ts:314

___

### findElement

▸ **findElement**(`strategy`, `selector`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

Find a UI element given a locator strategy and a selector, erroring if it can't be found

**`See`**

[https://w3c.github.io/webdriver/#find-element](https://w3c.github.io/webdriver/#find-element)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific element |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

The element object encoding the element id which can be used in element-related
commands

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElement](appium_types.IFindCommands.md#findelement)

#### Defined in

@appium/types/lib/driver.ts:210

___

### findElementFromElement

▸ **findElementFromElement**(`strategy`, `selector`, `elementId`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

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

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

The element object encoding the element id which can be used in element-related
commands

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElementFromElement](appium_types.IFindCommands.md#findelementfromelement)

#### Defined in

@appium/types/lib/driver.ts:235

___

### findElementFromShadowRoot

▸ `Optional` **findElementFromShadowRoot**(`strategy`, `selector`, `shadowId`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

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

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

The element inside the shadow root matching the selector

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElementFromShadowRoot](appium_types.IFindCommands.md#findelementfromshadowroot)

#### Defined in

@appium/types/lib/driver.ts:263

___

### findElements

▸ **findElements**(`strategy`, `selector`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

Find a a list of all UI elements matching a given a locator strategy and a selector

**`See`**

[https://w3c.github.io/webdriver/#find-elements](https://w3c.github.io/webdriver/#find-elements)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy` | `string` | the locator strategy |
| `selector` | `string` | the selector to combine with the strategy to find the specific elements |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A possibly-empty list of element objects

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElements](appium_types.IFindCommands.md#findelements)

#### Defined in

@appium/types/lib/driver.ts:221

___

### findElementsFromElement

▸ **findElementsFromElement**(`strategy`, `selector`, `elementId`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

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

`Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A possibly-empty list of element objects

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElementsFromElement](appium_types.IFindCommands.md#findelementsfromelement)

#### Defined in

@appium/types/lib/driver.ts:248

___

### findElementsFromShadowRoot

▸ `Optional` **findElementsFromShadowRoot**(`strategy`, `selector`, `shadowId`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

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

`Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

A possibly empty list of elements inside the shadow root matching the selector

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[findElementsFromShadowRoot](appium_types.IFindCommands.md#findelementsfromshadowroot)

#### Defined in

@appium/types/lib/driver.ts:278

___

### getLog

▸ **getLog**(`logType`): `Promise`<`any`\>

Get the log for a given log type.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `logType` | `string` | Name/key of log type as defined in [`supportedLogTypes`](appium_types.ILogCommands.md#supportedlogtypes). |

#### Returns

`Promise`<`any`\>

#### Inherited from

[ILogCommands](appium_types.ILogCommands.md).[getLog](appium_types.ILogCommands.md#getlog)

#### Defined in

@appium/types/lib/driver.ts:346

___

### getLogEvents

▸ **getLogEvents**(`type?`): `Promise`<[`EventHistory`](appium_types.EventHistory.md) \| `Record`<`string`, `number`\>\>

Get a list of events that have occurred in the current session

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `type?` | `string` \| `string`[] | filter the returned events by including one or more types |

#### Returns

`Promise`<[`EventHistory`](appium_types.EventHistory.md) \| `Record`<`string`, `number`\>\>

The event history for the session

#### Inherited from

[IEventCommands](appium_types.IEventCommands.md).[getLogEvents](appium_types.IEventCommands.md#getlogevents)

#### Defined in

@appium/types/lib/driver.ts:157

___

### getLogTypes

▸ **getLogTypes**(): `Promise`<`string`[]\>

Get available log types as a list of strings

#### Returns

`Promise`<`string`[]\>

#### Inherited from

[ILogCommands](appium_types.ILogCommands.md).[getLogTypes](appium_types.ILogCommands.md#getlogtypes)

#### Defined in

@appium/types/lib/driver.ts:339

___

### getManagedDrivers

▸ **getManagedDrivers**(): [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Returns

[`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Inherited from

[Core](appium_types.Core.md).[getManagedDrivers](appium_types.Core.md#getmanageddrivers)

#### Defined in

@appium/types/lib/driver.ts:643

___

### getPageSource

▸ **getPageSource**(): `Promise`<`string`\>

Get the current page/app source as HTML/XML

**`See`**

[https://w3c.github.io/webdriver/#get-page-source](https://w3c.github.io/webdriver/#get-page-source)

#### Returns

`Promise`<`string`\>

The UI hierarchy in a platform-appropriate format (e.g., HTML for a web page)

#### Inherited from

[IFindCommands](appium_types.IFindCommands.md).[getPageSource](appium_types.IFindCommands.md#getpagesource)

#### Defined in

@appium/types/lib/driver.ts:327

___

### getProxyAvoidList

▸ **getProxyAvoidList**(`sessionId?`): [`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

[`RouteMatcher`](../modules/appium_types.md#routematcher)[]

#### Inherited from

[Core](appium_types.Core.md).[getProxyAvoidList](appium_types.Core.md#getproxyavoidlist)

#### Defined in

@appium/types/lib/driver.ts:639

___

### getSession

▸ **getSession**(): `Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

Get the data for the current session

#### Returns

`Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

A session data object

#### Inherited from

[ISessionHandler](appium_types.ISessionHandler.md).[getSession](appium_types.ISessionHandler.md#getsession)

#### Defined in

@appium/types/lib/driver.ts:458

___

### getSessions

▸ **getSessions**(): `Promise`<[`MultiSessionData`](appium_types.MultiSessionData.md)<[`Constraints`](../modules/appium_types.md#constraints)\>[]\>

Get data for all sessions running on an Appium server

#### Returns

`Promise`<[`MultiSessionData`](appium_types.MultiSessionData.md)<[`Constraints`](../modules/appium_types.md#constraints)\>[]\>

A list of session data objects

#### Inherited from

[ISessionHandler](appium_types.ISessionHandler.md).[getSessions](appium_types.ISessionHandler.md#getsessions)

#### Defined in

@appium/types/lib/driver.ts:451

___

### getSettings

▸ **getSettings**(): `Promise`<`Settings`\>

Get the current settings for the session

#### Returns

`Promise`<`Settings`\>

The settings object

#### Inherited from

[ISettingsCommands](appium_types.ISettingsCommands.md).[getSettings](appium_types.ISettingsCommands.md#getsettings)

#### Defined in

@appium/types/lib/driver.ts:388

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

#### Inherited from

[Core](appium_types.Core.md).[getStatus](appium_types.Core.md#getstatus)

#### Defined in

@appium/types/lib/driver.ts:631

___

### getTimeouts

▸ **getTimeouts**(): `Promise`<`Record`<`string`, `number`\>\>

Get the current timeouts

**`See`**

[https://w3c.github.io/webdriver/#get-timeouts](https://w3c.github.io/webdriver/#get-timeouts)

#### Returns

`Promise`<`Record`<`string`, `number`\>\>

A map of timeout names to ms values

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[getTimeouts](appium_types.ITimeoutCommands.md#gettimeouts)

#### Defined in

@appium/types/lib/driver.ts:77

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[implicitWait](appium_types.ITimeoutCommands.md#implicitwait)

#### Defined in

@appium/types/lib/driver.ts:53

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[implicitWaitForCondition](appium_types.ITimeoutCommands.md#implicitwaitforcondition)

#### Defined in

@appium/types/lib/driver.ts:69

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[implicitWaitMJSONWP](appium_types.ITimeoutCommands.md#implicitwaitmjsonwp)

#### Defined in

@appium/types/lib/driver.ts:92

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[implicitWaitW3C](appium_types.ITimeoutCommands.md#implicitwaitw3c)

#### Defined in

@appium/types/lib/driver.ts:84

___

### isFeatureEnabled

▸ **isFeatureEnabled**(`name`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `name` | `string` |

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[isFeatureEnabled](appium_types.Core.md#isfeatureenabled)

#### Defined in

@appium/types/lib/driver.ts:635

___

### isMjsonwpProtocol

▸ **isMjsonwpProtocol**(): `boolean`

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[isMjsonwpProtocol](appium_types.Core.md#ismjsonwpprotocol)

#### Defined in

@appium/types/lib/driver.ts:634

___

### isW3CProtocol

▸ **isW3CProtocol**(): `boolean`

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[isW3CProtocol](appium_types.Core.md#isw3cprotocol)

#### Defined in

@appium/types/lib/driver.ts:633

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

#### Inherited from

[IEventCommands](appium_types.IEventCommands.md).[logCustomEvent](appium_types.IEventCommands.md#logcustomevent)

#### Defined in

@appium/types/lib/driver.ts:148

___

### logEvent

▸ **logEvent**(`eventName`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `eventName` | `string` |

#### Returns

`void`

#### Inherited from

[Core](appium_types.Core.md).[logEvent](appium_types.Core.md#logevent)

#### Defined in

@appium/types/lib/driver.ts:645

___

### logExtraCaps

▸ **logExtraCaps**(`caps`): `void`

A helper function to log unrecognized capabilities to the console

**`Params`**

caps - the capabilities

#### Parameters

| Name | Type |
| :------ | :------ |
| `caps` | [`DriverCaps`](../modules/appium_types.md#drivercaps)<`C`\> |

#### Returns

`void`

#### Defined in

@appium/types/lib/driver.ts:749

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[newCommandTimeout](appium_types.ITimeoutCommands.md#newcommandtimeout)

#### Defined in

@appium/types/lib/driver.ts:129

___

### onUnexpectedShutdown

▸ **onUnexpectedShutdown**(`handler`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `handler` | () => `any` |

#### Returns

`void`

#### Inherited from

[Core](appium_types.Core.md).[onUnexpectedShutdown](appium_types.Core.md#onunexpectedshutdown)

#### Defined in

@appium/types/lib/driver.ts:599

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[pageLoadTimeoutMJSONWP](appium_types.ITimeoutCommands.md#pageloadtimeoutmjsonwp)

#### Defined in

@appium/types/lib/driver.ts:107

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[pageLoadTimeoutW3C](appium_types.ITimeoutCommands.md#pageloadtimeoutw3c)

#### Defined in

@appium/types/lib/driver.ts:99

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[parseTimeoutArgument](appium_types.ITimeoutCommands.md#parsetimeoutargument)

#### Defined in

@appium/types/lib/driver.ts:138

___

### proxyActive

▸ **proxyActive**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[proxyActive](appium_types.Core.md#proxyactive)

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

#### Inherited from

[Core](appium_types.Core.md).[proxyRouteIsAvoided](appium_types.Core.md#proxyrouteisavoided)

#### Defined in

@appium/types/lib/driver.ts:641

___

### reset

▸ **reset**(): `Promise`<`void`\>

Reset the current session (run the delete session and create session subroutines)

**`Deprecated`**

Use explicit session management commands instead

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:714

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[scriptTimeoutMJSONWP](appium_types.ITimeoutCommands.md#scripttimeoutmjsonwp)

#### Defined in

@appium/types/lib/driver.ts:122

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[scriptTimeoutW3C](appium_types.ITimeoutCommands.md#scripttimeoutw3c)

#### Defined in

@appium/types/lib/driver.ts:114

___

### sessionExists

▸ **sessionExists**(`sessionId?`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `sessionId?` | `string` |

#### Returns

`boolean`

#### Inherited from

[Core](appium_types.Core.md).[sessionExists](appium_types.Core.md#sessionexists)

#### Defined in

@appium/types/lib/driver.ts:632

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[setImplicitWait](appium_types.ITimeoutCommands.md#setimplicitwait)

#### Defined in

@appium/types/lib/driver.ts:60

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[setNewCommandTimeout](appium_types.ITimeoutCommands.md#setnewcommandtimeout)

#### Defined in

@appium/types/lib/driver.ts:43

___

### startNewCommandTimeout

▸ **startNewCommandTimeout**(): `Promise`<`void`\>

Start the timer for the New Command Timeout, which when it runs out, will stop the current
session

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:706

___

### startUnexpectedShutdown

▸ **startUnexpectedShutdown**(`err?`): `Promise`<`void`\>

Signify to any owning processes that this driver encountered an error which should cause the
session to terminate immediately (for example an upstream service failed)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `err?` | `Error` | the Error object which is causing the shutdown |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:700

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

#### Inherited from

[ITimeoutCommands](appium_types.ITimeoutCommands.md).[timeouts](appium_types.ITimeoutCommands.md#timeouts)

#### Defined in

@appium/types/lib/driver.ts:30

___

### validateDesiredCaps

▸ **validateDesiredCaps**(`caps`): `boolean`

Validate the capabilities used to start a session

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `caps` | [`DriverCaps`](../modules/appium_types.md#drivercaps)<`C`\> | the capabilities |

#### Returns

`boolean`

Whether or not the capabilities are valid

#### Defined in

@appium/types/lib/driver.ts:740

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

#### Inherited from

[Core](appium_types.Core.md).[validateLocatorStrategy](appium_types.Core.md#validatelocatorstrategy)

#### Defined in

@appium/types/lib/driver.ts:637
