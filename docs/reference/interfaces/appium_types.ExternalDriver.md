# Interface: ExternalDriver<C, Ctx, CArgs, Settings, CreateResult, DeleteResult, SessionData\>

[@appium/types](../modules/appium_types.md).ExternalDriver

External drivers must subclass `BaseDriver`, and can implement any of these methods.
None of these are implemented within Appium itself.

## Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](../modules/appium_types.md#constraints) = [`Constraints`](../modules/appium_types.md#constraints) |
| `Ctx` | `string` |
| `CArgs` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `Settings` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |
| `CreateResult` | [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<`C`\> |
| `DeleteResult` | [`DefaultDeleteSessionResult`](../modules/appium_types.md#defaultdeletesessionresult) |
| `SessionData` | extends [`StringRecord`](../modules/appium_types.md#stringrecord) = [`StringRecord`](../modules/appium_types.md#stringrecord) |

## Hierarchy

- [`Driver`](appium_types.Driver.md)<`C`, `CArgs`, `Settings`, `CreateResult`, `DeleteResult`, `SessionData`\>

  ↳ **`ExternalDriver`**

## Table of contents

### Properties

- [allowInsecure](appium_types.ExternalDriver.md#allowinsecure)
- [basePath](appium_types.ExternalDriver.md#basepath)
- [caps](appium_types.ExternalDriver.md#caps)
- [cliArgs](appium_types.ExternalDriver.md#cliargs)
- [denyInsecure](appium_types.ExternalDriver.md#denyinsecure)
- [desiredCapConstraints](appium_types.ExternalDriver.md#desiredcapconstraints)
- [driverData](appium_types.ExternalDriver.md#driverdata)
- [eventEmitter](appium_types.ExternalDriver.md#eventemitter)
- [eventHistory](appium_types.ExternalDriver.md#eventhistory)
- [helpers](appium_types.ExternalDriver.md#helpers)
- [implicitWaitMs](appium_types.ExternalDriver.md#implicitwaitms)
- [initialOpts](appium_types.ExternalDriver.md#initialopts)
- [isCommandsQueueEnabled](appium_types.ExternalDriver.md#iscommandsqueueenabled)
- [locatorStrategies](appium_types.ExternalDriver.md#locatorstrategies)
- [log](appium_types.ExternalDriver.md#log)
- [newCommandTimeoutMs](appium_types.ExternalDriver.md#newcommandtimeoutms)
- [opts](appium_types.ExternalDriver.md#opts)
- [originalCaps](appium_types.ExternalDriver.md#originalcaps)
- [protocol](appium_types.ExternalDriver.md#protocol)
- [relaxedSecurityEnabled](appium_types.ExternalDriver.md#relaxedsecurityenabled)
- [server](appium_types.ExternalDriver.md#server)
- [serverHost](appium_types.ExternalDriver.md#serverhost)
- [serverPath](appium_types.ExternalDriver.md#serverpath)
- [serverPort](appium_types.ExternalDriver.md#serverport)
- [sessionId](appium_types.ExternalDriver.md#sessionid)
- [settings](appium_types.ExternalDriver.md#settings)
- [shouldValidateCaps](appium_types.ExternalDriver.md#shouldvalidatecaps)
- [supportedLogTypes](appium_types.ExternalDriver.md#supportedlogtypes)
- [updateSettings](appium_types.ExternalDriver.md#updatesettings)
- [webLocatorStrategies](appium_types.ExternalDriver.md#weblocatorstrategies)

### Methods

- [activateApp](appium_types.ExternalDriver.md#activateapp)
- [activateIMEEngine](appium_types.ExternalDriver.md#activateimeengine)
- [active](appium_types.ExternalDriver.md#active)
- [addAuthCredential](appium_types.ExternalDriver.md#addauthcredential)
- [addManagedDriver](appium_types.ExternalDriver.md#addmanageddriver)
- [addVirtualAuthenticator](appium_types.ExternalDriver.md#addvirtualauthenticator)
- [assertFeatureEnabled](appium_types.ExternalDriver.md#assertfeatureenabled)
- [assignServer](appium_types.ExternalDriver.md#assignserver)
- [availableIMEEngines](appium_types.ExternalDriver.md#availableimeengines)
- [back](appium_types.ExternalDriver.md#back)
- [buttonDown](appium_types.ExternalDriver.md#buttondown)
- [buttonUp](appium_types.ExternalDriver.md#buttonup)
- [canProxy](appium_types.ExternalDriver.md#canproxy)
- [clear](appium_types.ExternalDriver.md#clear)
- [clearNewCommandTimeout](appium_types.ExternalDriver.md#clearnewcommandtimeout)
- [click](appium_types.ExternalDriver.md#click)
- [clickCurrent](appium_types.ExternalDriver.md#clickcurrent)
- [closeWindow](appium_types.ExternalDriver.md#closewindow)
- [createNewWindow](appium_types.ExternalDriver.md#createnewwindow)
- [createSession](appium_types.ExternalDriver.md#createsession)
- [deactivateIMEEngine](appium_types.ExternalDriver.md#deactivateimeengine)
- [deleteCookie](appium_types.ExternalDriver.md#deletecookie)
- [deleteCookies](appium_types.ExternalDriver.md#deletecookies)
- [deleteSession](appium_types.ExternalDriver.md#deletesession)
- [doubleClick](appium_types.ExternalDriver.md#doubleclick)
- [driverForSession](appium_types.ExternalDriver.md#driverforsession)
- [elementDisplayed](appium_types.ExternalDriver.md#elementdisplayed)
- [elementEnabled](appium_types.ExternalDriver.md#elementenabled)
- [elementSelected](appium_types.ExternalDriver.md#elementselected)
- [elementShadowRoot](appium_types.ExternalDriver.md#elementshadowroot)
- [endCoverage](appium_types.ExternalDriver.md#endcoverage)
- [equalsElement](appium_types.ExternalDriver.md#equalselement)
- [execute](appium_types.ExternalDriver.md#execute)
- [executeAsync](appium_types.ExternalDriver.md#executeasync)
- [executeCdp](appium_types.ExternalDriver.md#executecdp)
- [executeCommand](appium_types.ExternalDriver.md#executecommand)
- [executeMethod](appium_types.ExternalDriver.md#executemethod)
- [findElOrEls](appium_types.ExternalDriver.md#findelorels)
- [findElOrElsWithProcessing](appium_types.ExternalDriver.md#findelorelswithprocessing)
- [findElement](appium_types.ExternalDriver.md#findelement)
- [findElementFromElement](appium_types.ExternalDriver.md#findelementfromelement)
- [findElementFromShadowRoot](appium_types.ExternalDriver.md#findelementfromshadowroot)
- [findElements](appium_types.ExternalDriver.md#findelements)
- [findElementsFromElement](appium_types.ExternalDriver.md#findelementsfromelement)
- [findElementsFromShadowRoot](appium_types.ExternalDriver.md#findelementsfromshadowroot)
- [fingerprint](appium_types.ExternalDriver.md#fingerprint)
- [flick](appium_types.ExternalDriver.md#flick)
- [forward](appium_types.ExternalDriver.md#forward)
- [fullScreenWindow](appium_types.ExternalDriver.md#fullscreenwindow)
- [getActiveIMEEngine](appium_types.ExternalDriver.md#getactiveimeengine)
- [getAlertText](appium_types.ExternalDriver.md#getalerttext)
- [getAttribute](appium_types.ExternalDriver.md#getattribute)
- [getAuthCredential](appium_types.ExternalDriver.md#getauthcredential)
- [getComputedLabel](appium_types.ExternalDriver.md#getcomputedlabel)
- [getComputedRole](appium_types.ExternalDriver.md#getcomputedrole)
- [getContexts](appium_types.ExternalDriver.md#getcontexts)
- [getCookie](appium_types.ExternalDriver.md#getcookie)
- [getCookies](appium_types.ExternalDriver.md#getcookies)
- [getCssProperty](appium_types.ExternalDriver.md#getcssproperty)
- [getCurrentActivity](appium_types.ExternalDriver.md#getcurrentactivity)
- [getCurrentContext](appium_types.ExternalDriver.md#getcurrentcontext)
- [getCurrentPackage](appium_types.ExternalDriver.md#getcurrentpackage)
- [getDeviceTime](appium_types.ExternalDriver.md#getdevicetime)
- [getDisplayDensity](appium_types.ExternalDriver.md#getdisplaydensity)
- [getElementRect](appium_types.ExternalDriver.md#getelementrect)
- [getElementScreenshot](appium_types.ExternalDriver.md#getelementscreenshot)
- [getGeoLocation](appium_types.ExternalDriver.md#getgeolocation)
- [getLog](appium_types.ExternalDriver.md#getlog)
- [getLogEvents](appium_types.ExternalDriver.md#getlogevents)
- [getLogTypes](appium_types.ExternalDriver.md#getlogtypes)
- [getManagedDrivers](appium_types.ExternalDriver.md#getmanageddrivers)
- [getName](appium_types.ExternalDriver.md#getname)
- [getNetworkConnection](appium_types.ExternalDriver.md#getnetworkconnection)
- [getOrientation](appium_types.ExternalDriver.md#getorientation)
- [getPageIndex](appium_types.ExternalDriver.md#getpageindex)
- [getPageSource](appium_types.ExternalDriver.md#getpagesource)
- [getPerformanceData](appium_types.ExternalDriver.md#getperformancedata)
- [getPerformanceDataTypes](appium_types.ExternalDriver.md#getperformancedatatypes)
- [getProperty](appium_types.ExternalDriver.md#getproperty)
- [getProxyAvoidList](appium_types.ExternalDriver.md#getproxyavoidlist)
- [getRotation](appium_types.ExternalDriver.md#getrotation)
- [getScreenshot](appium_types.ExternalDriver.md#getscreenshot)
- [getSession](appium_types.ExternalDriver.md#getsession)
- [getSessions](appium_types.ExternalDriver.md#getsessions)
- [getSettings](appium_types.ExternalDriver.md#getsettings)
- [getStatus](appium_types.ExternalDriver.md#getstatus)
- [getSystemBars](appium_types.ExternalDriver.md#getsystembars)
- [getText](appium_types.ExternalDriver.md#gettext)
- [getTimeouts](appium_types.ExternalDriver.md#gettimeouts)
- [getUrl](appium_types.ExternalDriver.md#geturl)
- [getWindowHandle](appium_types.ExternalDriver.md#getwindowhandle)
- [getWindowHandles](appium_types.ExternalDriver.md#getwindowhandles)
- [getWindowRect](appium_types.ExternalDriver.md#getwindowrect)
- [gsmCall](appium_types.ExternalDriver.md#gsmcall)
- [gsmSignal](appium_types.ExternalDriver.md#gsmsignal)
- [gsmVoice](appium_types.ExternalDriver.md#gsmvoice)
- [hideKeyboard](appium_types.ExternalDriver.md#hidekeyboard)
- [implicitWait](appium_types.ExternalDriver.md#implicitwait)
- [implicitWaitForCondition](appium_types.ExternalDriver.md#implicitwaitforcondition)
- [implicitWaitMJSONWP](appium_types.ExternalDriver.md#implicitwaitmjsonwp)
- [implicitWaitW3C](appium_types.ExternalDriver.md#implicitwaitw3c)
- [installApp](appium_types.ExternalDriver.md#installapp)
- [isAppInstalled](appium_types.ExternalDriver.md#isappinstalled)
- [isFeatureEnabled](appium_types.ExternalDriver.md#isfeatureenabled)
- [isIMEActivated](appium_types.ExternalDriver.md#isimeactivated)
- [isKeyboardShown](appium_types.ExternalDriver.md#iskeyboardshown)
- [isMjsonwpProtocol](appium_types.ExternalDriver.md#ismjsonwpprotocol)
- [isW3CProtocol](appium_types.ExternalDriver.md#isw3cprotocol)
- [keyevent](appium_types.ExternalDriver.md#keyevent)
- [logCustomEvent](appium_types.ExternalDriver.md#logcustomevent)
- [logEvent](appium_types.ExternalDriver.md#logevent)
- [logExtraCaps](appium_types.ExternalDriver.md#logextracaps)
- [longPressKeyCode](appium_types.ExternalDriver.md#longpresskeycode)
- [maximizeWindow](appium_types.ExternalDriver.md#maximizewindow)
- [minimizeWindow](appium_types.ExternalDriver.md#minimizewindow)
- [mobileRotation](appium_types.ExternalDriver.md#mobilerotation)
- [networkSpeed](appium_types.ExternalDriver.md#networkspeed)
- [newCommandTimeout](appium_types.ExternalDriver.md#newcommandtimeout)
- [onUnexpectedShutdown](appium_types.ExternalDriver.md#onunexpectedshutdown)
- [openNotifications](appium_types.ExternalDriver.md#opennotifications)
- [pageLoadTimeoutMJSONWP](appium_types.ExternalDriver.md#pageloadtimeoutmjsonwp)
- [pageLoadTimeoutW3C](appium_types.ExternalDriver.md#pageloadtimeoutw3c)
- [parseTimeoutArgument](appium_types.ExternalDriver.md#parsetimeoutargument)
- [performActions](appium_types.ExternalDriver.md#performactions)
- [postAcceptAlert](appium_types.ExternalDriver.md#postacceptalert)
- [postDismissAlert](appium_types.ExternalDriver.md#postdismissalert)
- [powerAC](appium_types.ExternalDriver.md#powerac)
- [powerCapacity](appium_types.ExternalDriver.md#powercapacity)
- [pressKeyCode](appium_types.ExternalDriver.md#presskeycode)
- [proxyActive](appium_types.ExternalDriver.md#proxyactive)
- [proxyCommand](appium_types.ExternalDriver.md#proxycommand)
- [proxyRouteIsAvoided](appium_types.ExternalDriver.md#proxyrouteisavoided)
- [pullFile](appium_types.ExternalDriver.md#pullfile)
- [pullFolder](appium_types.ExternalDriver.md#pullfolder)
- [pushFile](appium_types.ExternalDriver.md#pushfile)
- [queryAppState](appium_types.ExternalDriver.md#queryappstate)
- [refresh](appium_types.ExternalDriver.md#refresh)
- [releaseActions](appium_types.ExternalDriver.md#releaseactions)
- [removeAllAuthCredentials](appium_types.ExternalDriver.md#removeallauthcredentials)
- [removeApp](appium_types.ExternalDriver.md#removeapp)
- [removeAuthCredential](appium_types.ExternalDriver.md#removeauthcredential)
- [removeVirtualAuthenticator](appium_types.ExternalDriver.md#removevirtualauthenticator)
- [replaceValue](appium_types.ExternalDriver.md#replacevalue)
- [reset](appium_types.ExternalDriver.md#reset)
- [scriptTimeoutMJSONWP](appium_types.ExternalDriver.md#scripttimeoutmjsonwp)
- [scriptTimeoutW3C](appium_types.ExternalDriver.md#scripttimeoutw3c)
- [sendSMS](appium_types.ExternalDriver.md#sendsms)
- [sessionExists](appium_types.ExternalDriver.md#sessionexists)
- [setAlertText](appium_types.ExternalDriver.md#setalerttext)
- [setContext](appium_types.ExternalDriver.md#setcontext)
- [setCookie](appium_types.ExternalDriver.md#setcookie)
- [setFrame](appium_types.ExternalDriver.md#setframe)
- [setGeoLocation](appium_types.ExternalDriver.md#setgeolocation)
- [setImplicitWait](appium_types.ExternalDriver.md#setimplicitwait)
- [setNetworkConnection](appium_types.ExternalDriver.md#setnetworkconnection)
- [setNewCommandTimeout](appium_types.ExternalDriver.md#setnewcommandtimeout)
- [setOrientation](appium_types.ExternalDriver.md#setorientation)
- [setRotation](appium_types.ExternalDriver.md#setrotation)
- [setUrl](appium_types.ExternalDriver.md#seturl)
- [setUserAuthVerified](appium_types.ExternalDriver.md#setuserauthverified)
- [setValue](appium_types.ExternalDriver.md#setvalue)
- [setWindow](appium_types.ExternalDriver.md#setwindow)
- [setWindowRect](appium_types.ExternalDriver.md#setwindowrect)
- [startActivity](appium_types.ExternalDriver.md#startactivity)
- [startNewCommandTimeout](appium_types.ExternalDriver.md#startnewcommandtimeout)
- [startUnexpectedShutdown](appium_types.ExternalDriver.md#startunexpectedshutdown)
- [switchToParentFrame](appium_types.ExternalDriver.md#switchtoparentframe)
- [terminateApp](appium_types.ExternalDriver.md#terminateapp)
- [timeouts](appium_types.ExternalDriver.md#timeouts)
- [title](appium_types.ExternalDriver.md#title)
- [toggleData](appium_types.ExternalDriver.md#toggledata)
- [toggleFlightMode](appium_types.ExternalDriver.md#toggleflightmode)
- [toggleLocationServices](appium_types.ExternalDriver.md#togglelocationservices)
- [toggleWiFi](appium_types.ExternalDriver.md#togglewifi)
- [touchDown](appium_types.ExternalDriver.md#touchdown)
- [touchLongClick](appium_types.ExternalDriver.md#touchlongclick)
- [touchMove](appium_types.ExternalDriver.md#touchmove)
- [touchUp](appium_types.ExternalDriver.md#touchup)
- [validateDesiredCaps](appium_types.ExternalDriver.md#validatedesiredcaps)
- [validateLocatorStrategy](appium_types.ExternalDriver.md#validatelocatorstrategy)

## Properties

### allowInsecure

• **allowInsecure**: `string`[]

#### Inherited from

[Driver](appium_types.Driver.md).[allowInsecure](appium_types.Driver.md#allowinsecure)

#### Defined in

@appium/types/lib/driver.ts:587

___

### basePath

• **basePath**: `string`

#### Inherited from

[Driver](appium_types.Driver.md).[basePath](appium_types.Driver.md#basepath)

#### Defined in

@appium/types/lib/driver.ts:585

___

### caps

• `Optional` **caps**: [`ConstraintsToCaps`](../modules/appium_types.md#constraintstocaps)<`C`\>

The processed capabilities used to start the session represented by the current driver instance

#### Inherited from

[Driver](appium_types.Driver.md).[caps](appium_types.Driver.md#caps)

#### Defined in

@appium/types/lib/driver.ts:719

___

### cliArgs

• **cliArgs**: `CArgs`

The set of command line arguments set for this driver

#### Inherited from

[Driver](appium_types.Driver.md).[cliArgs](appium_types.Driver.md#cliargs)

#### Defined in

@appium/types/lib/driver.ts:675

___

### denyInsecure

• **denyInsecure**: `string`[]

#### Inherited from

[Driver](appium_types.Driver.md).[denyInsecure](appium_types.Driver.md#denyinsecure)

#### Defined in

@appium/types/lib/driver.ts:588

___

### desiredCapConstraints

• **desiredCapConstraints**: `C`

The constraints object used to validate capabilities

#### Inherited from

[Driver](appium_types.Driver.md).[desiredCapConstraints](appium_types.Driver.md#desiredcapconstraints)

#### Defined in

@appium/types/lib/driver.ts:729

___

### driverData

• **driverData**: [`DriverData`](../modules/appium_types.md#driverdata)

#### Inherited from

[Driver](appium_types.Driver.md).[driverData](appium_types.Driver.md#driverdata)

#### Defined in

@appium/types/lib/driver.ts:596

___

### eventEmitter

• **eventEmitter**: `EventEmitter`

#### Inherited from

[Driver](appium_types.Driver.md).[eventEmitter](appium_types.Driver.md#eventemitter)

#### Defined in

@appium/types/lib/driver.ts:593

___

### eventHistory

• **eventHistory**: [`EventHistory`](appium_types.EventHistory.md)

#### Inherited from

[Driver](appium_types.Driver.md).[eventHistory](appium_types.Driver.md#eventhistory)

#### Defined in

@appium/types/lib/driver.ts:598

___

### helpers

• **helpers**: [`DriverHelpers`](appium_types.DriverHelpers.md)

#### Inherited from

[Driver](appium_types.Driver.md).[helpers](appium_types.Driver.md#helpers)

#### Defined in

@appium/types/lib/driver.ts:584

___

### implicitWaitMs

• **implicitWaitMs**: `number`

#### Inherited from

[Driver](appium_types.Driver.md).[implicitWaitMs](appium_types.Driver.md#implicitwaitms)

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

[Driver](appium_types.Driver.md).[initialOpts](appium_types.Driver.md#initialopts)

#### Defined in

@appium/types/lib/driver.ts:582

___

### isCommandsQueueEnabled

• **isCommandsQueueEnabled**: `boolean`

#### Inherited from

[Driver](appium_types.Driver.md).[isCommandsQueueEnabled](appium_types.Driver.md#iscommandsqueueenabled)

#### Defined in

@appium/types/lib/driver.ts:597

___

### locatorStrategies

• **locatorStrategies**: `string`[]

#### Inherited from

[Driver](appium_types.Driver.md).[locatorStrategies](appium_types.Driver.md#locatorstrategies)

#### Defined in

@appium/types/lib/driver.ts:591

___

### log

• **log**: [`AppiumLogger`](appium_types.AppiumLogger.md)

#### Inherited from

[Driver](appium_types.Driver.md).[log](appium_types.Driver.md#log)

#### Defined in

@appium/types/lib/driver.ts:595

___

### newCommandTimeoutMs

• **newCommandTimeoutMs**: `number`

#### Inherited from

[Driver](appium_types.Driver.md).[newCommandTimeoutMs](appium_types.Driver.md#newcommandtimeoutms)

#### Defined in

@appium/types/lib/driver.ts:589

___

### opts

• **opts**: [`DriverOpts`](../modules/appium_types.md#driveropts)<`C`\>

#### Inherited from

[Driver](appium_types.Driver.md).[opts](appium_types.Driver.md#opts)

#### Defined in

@appium/types/lib/driver.ts:581

___

### originalCaps

• `Optional` **originalCaps**: [`W3CCapabilities`](appium_types.W3CCapabilities.md)<`C`\>

The original capabilities used to start the session represented by the current driver instance

#### Inherited from

[Driver](appium_types.Driver.md).[originalCaps](appium_types.Driver.md#originalcaps)

#### Defined in

@appium/types/lib/driver.ts:724

___

### protocol

• `Optional` **protocol**: [`Protocol`](../modules/appium_types.md#protocol)

#### Inherited from

[Driver](appium_types.Driver.md).[protocol](appium_types.Driver.md#protocol)

#### Defined in

@appium/types/lib/driver.ts:583

___

### relaxedSecurityEnabled

• **relaxedSecurityEnabled**: `boolean`

#### Inherited from

[Driver](appium_types.Driver.md).[relaxedSecurityEnabled](appium_types.Driver.md#relaxedsecurityenabled)

#### Defined in

@appium/types/lib/driver.ts:586

___

### server

• `Optional` **server**: [`AppiumServer`](../modules/appium_types.md#appiumserver)

#### Inherited from

[Driver](appium_types.Driver.md).[server](appium_types.Driver.md#server)

#### Defined in

@appium/types/lib/driver.ts:677

___

### serverHost

• `Optional` **serverHost**: `string`

#### Inherited from

[Driver](appium_types.Driver.md).[serverHost](appium_types.Driver.md#serverhost)

#### Defined in

@appium/types/lib/driver.ts:678

___

### serverPath

• `Optional` **serverPath**: `string`

#### Inherited from

[Driver](appium_types.Driver.md).[serverPath](appium_types.Driver.md#serverpath)

#### Defined in

@appium/types/lib/driver.ts:680

___

### serverPort

• `Optional` **serverPort**: `number`

#### Inherited from

[Driver](appium_types.Driver.md).[serverPort](appium_types.Driver.md#serverport)

#### Defined in

@appium/types/lib/driver.ts:679

___

### sessionId

• **sessionId**: ``null`` \| `string`

#### Inherited from

[Driver](appium_types.Driver.md).[sessionId](appium_types.Driver.md#sessionid)

#### Defined in

@appium/types/lib/driver.ts:580

___

### settings

• **settings**: [`IDeviceSettings`](appium_types.IDeviceSettings.md)<`Settings`\>

#### Inherited from

[Driver](appium_types.Driver.md).[settings](appium_types.Driver.md#settings)

#### Defined in

@appium/types/lib/driver.ts:594

___

### shouldValidateCaps

• **shouldValidateCaps**: `boolean`

#### Inherited from

[Driver](appium_types.Driver.md).[shouldValidateCaps](appium_types.Driver.md#shouldvalidatecaps)

#### Defined in

@appium/types/lib/driver.ts:579

___

### supportedLogTypes

• **supportedLogTypes**: `Readonly`<[`LogDefRecord`](../modules/appium_types.md#logdefrecord)\>

Definition of the available log types

#### Inherited from

[Driver](appium_types.Driver.md).[supportedLogTypes](appium_types.Driver.md#supportedlogtypes)

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

[Driver](appium_types.Driver.md).[updateSettings](appium_types.Driver.md#updatesettings)

#### Defined in

@appium/types/lib/driver.ts:381

___

### webLocatorStrategies

• **webLocatorStrategies**: `string`[]

#### Inherited from

[Driver](appium_types.Driver.md).[webLocatorStrategies](appium_types.Driver.md#weblocatorstrategies)

#### Defined in

@appium/types/lib/driver.ts:592

## Methods

### activateApp

▸ `Optional` **activateApp**(`appId`, `options?`): `Promise`<`void`\>

Launch an app

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appId` | `string` | the package or bundle ID of the application |
| `options?` | `unknown` | driver-specific launch options |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1419

___

### activateIMEEngine

▸ `Optional` **activateIMEEngine**(`engine`): `Promise`<`void`\>

Activate an IME engine

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `engine` | `string` | the name of the engine |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1673

___

### active

▸ `Optional` **active**(): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

Get the active element

**`See`**

[https://w3c.github.io/webdriver/#get-active-element](https://w3c.github.io/webdriver/#get-active-element)

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

The JSON object encapsulating the active element reference

#### Defined in

@appium/types/lib/driver.ts:938

___

### addAuthCredential

▸ `Optional` **addAuthCredential**(`credentialId`, `isResidentCredential`, `rpId`, `privateKey`, `userHandle`, `signCount`, `authenticatorId`): `Promise`<`void`\>

Inject a public key credential source into a virtual authenticator

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-add-credential](https://www.w3.org/TR/webauthn-2/#sctn-automation-add-credential)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `credentialId` | `string` | the base64 encoded credential ID |
| `isResidentCredential` | `boolean` | if true, a client-side credential, otherwise a server-side credential |
| `rpId` | `string` | the relying party ID the credential is scoped to |
| `privateKey` | `string` | the base64 encoded private key package |
| `userHandle` | `string` | the base64 encoded user handle |
| `signCount` | `number` | the initial value for a signature counter |
| `authenticatorId` | `string` | - |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1934

___

### addManagedDriver

▸ **addManagedDriver**(`driver`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> |

#### Returns

`void`

#### Inherited from

[Driver](appium_types.Driver.md).[addManagedDriver](appium_types.Driver.md#addmanageddriver)

#### Defined in

@appium/types/lib/driver.ts:642

___

### addVirtualAuthenticator

▸ `Optional` **addVirtualAuthenticator**(`protocol`, `transport`, `hasResidentKey?`, `hasUserVerification?`, `isUserConsenting?`, `isUserVerified?`): `Promise`<`string`\>

Add a virtual authenticator to a browser

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-add-virtual-authenticator](https://www.w3.org/TR/webauthn-2/#sctn-automation-add-virtual-authenticator)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `protocol` | ``"ctap/u2f"`` \| ``"ctap2"`` \| ``"ctap2_1"`` | the protocol |
| `transport` | `string` | a valid AuthenticatorTransport value |
| `hasResidentKey?` | `boolean` | whether there is a resident key |
| `hasUserVerification?` | `boolean` | whether the authenticator has user verification |
| `isUserConsenting?` | `boolean` | whether it is a user consenting authenticator |
| `isUserVerified?` | `boolean` | whether the user is verified |

#### Returns

`Promise`<`string`\>

The authenticator ID

#### Defined in

@appium/types/lib/driver.ts:1905

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

[Driver](appium_types.Driver.md).[assertFeatureEnabled](appium_types.Driver.md#assertfeatureenabled)

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

#### Inherited from

[Driver](appium_types.Driver.md).[assignServer](appium_types.Driver.md#assignserver)

#### Defined in

@appium/types/lib/driver.ts:760

___

### availableIMEEngines

▸ `Optional` **availableIMEEngines**(): `Promise`<`string`[]\>

Get the list of IME engines

**`Deprecated`**

#### Returns

`Promise`<`string`[]\>

The list of IME engines

#### Defined in

@appium/types/lib/driver.ts:1639

___

### back

▸ `Optional` **back**(): `Promise`<`void`\>

Navigate back in the page history

**`See`**

[https://w3c.github.io/webdriver/#back](https://w3c.github.io/webdriver/#back)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:798

___

### buttonDown

▸ `Optional` **buttonDown**(`button?`): `Promise`<`void`\>

Trigger a mouse button down

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `button?` | `number` | the button ID |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1697

___

### buttonUp

▸ `Optional` **buttonUp**(`button?`): `Promise`<`void`\>

Trigger a mouse button up

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `button?` | `number` | the button ID |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1707

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

[Driver](appium_types.Driver.md).[canProxy](appium_types.Driver.md#canproxy)

#### Defined in

@appium/types/lib/driver.ts:640

___

### clear

▸ `Optional` **clear**(`elementId`): `Promise`<`void`\>

Clear the text/value of an editable element

**`See`**

[https://w3c.github.io/webdriver/#element-clear](https://w3c.github.io/webdriver/#element-clear)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1077

___

### clearNewCommandTimeout

▸ **clearNewCommandTimeout**(): `Promise`<`void`\>

#### Returns

`Promise`<`void`\>

#### Inherited from

[Driver](appium_types.Driver.md).[clearNewCommandTimeout](appium_types.Driver.md#clearnewcommandtimeout)

#### Defined in

@appium/types/lib/driver.ts:644

___

### click

▸ `Optional` **click**(`elementId`): `Promise`<`void`\>

Click/tap an element

**`See`**

[https://w3c.github.io/webdriver/#element-click](https://w3c.github.io/webdriver/#element-click)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1069

___

### clickCurrent

▸ `Optional` **clickCurrent**(`button?`): `Promise`<`void`\>

Click the current mouse location

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `button?` | `number` | the button ID |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1717

___

### closeWindow

▸ `Optional` **closeWindow**(): `Promise`<`string`[]\>

Close the current browsing context (window)

**`See`**

[https://w3c.github.io/webdriver/#close-window](https://w3c.github.io/webdriver/#close-window)

#### Returns

`Promise`<`string`[]\>

An array of window handles representing currently-open windows

#### Defined in

@appium/types/lib/driver.ts:845

___

### createNewWindow

▸ `Optional` **createNewWindow**(`type?`): `Promise`<[`NewWindow`](appium_types.NewWindow.md)\>

Create a new browser window

**`See`**

[https://w3c.github.io/webdriver/#new-window](https://w3c.github.io/webdriver/#new-window)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `type?` | [`NewWindowType`](../modules/appium_types.md#newwindowtype) | a hint to the driver whether to create a "tab" or "window" |

#### Returns

`Promise`<[`NewWindow`](appium_types.NewWindow.md)\>

An object containing the handle of the newly created window and its type

#### Defined in

@appium/types/lib/driver.ts:871

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

[Driver](appium_types.Driver.md).[createSession](appium_types.Driver.md#createsession)

#### Defined in

@appium/types/lib/driver.ts:430

___

### deactivateIMEEngine

▸ `Optional` **deactivateIMEEngine**(): `Promise`<`void`\>

Deactivate an IME engine

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1664

___

### deleteCookie

▸ `Optional` **deleteCookie**(`name`): `Promise`<`void`\>

Delete a named cookie

**`See`**

[https://w3c.github.io/webdriver/#delete-cookie](https://w3c.github.io/webdriver/#delete-cookie)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the name of the cookie to delete |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1146

___

### deleteCookies

▸ `Optional` **deleteCookies**(): `Promise`<`void`\>

Delete all cookies

**`See`**

[https://w3c.github.io/webdriver/#delete-all-cookies](https://w3c.github.io/webdriver/#delete-all-cookies)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1152

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

[Driver](appium_types.Driver.md).[deleteSession](appium_types.Driver.md#deletesession)

#### Defined in

@appium/types/lib/driver.ts:444

___

### doubleClick

▸ `Optional` **doubleClick**(): `Promise`<`void`\>

Double-click the current mouse location

**`Deprecated`**

Use the Actions API instead

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1725

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

[Driver](appium_types.Driver.md).[driverForSession](appium_types.Driver.md#driverforsession)

#### Defined in

@appium/types/lib/driver.ts:646

___

### elementDisplayed

▸ `Optional` **elementDisplayed**(`elementId`): `Promise`<`boolean`\>

Determine whether an element is displayed

**`See`**

[https://w3c.github.io/webdriver/#element-displayedness](https://w3c.github.io/webdriver/#element-displayedness)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`boolean`\>

True if any part of the element is rendered within the viewport, False otherwise

#### Defined in

@appium/types/lib/driver.ts:1061

___

### elementEnabled

▸ `Optional` **elementEnabled**(`elementId`): `Promise`<`boolean`\>

Determine whether an element is enabled

**`See`**

[https://w3c.github.io/webdriver/#is-element-enabled](https://w3c.github.io/webdriver/#is-element-enabled)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`boolean`\>

True if the element is enabled, False otherwise

#### Defined in

@appium/types/lib/driver.ts:1031

___

### elementSelected

▸ `Optional` **elementSelected**(`elementId`): `Promise`<`boolean`\>

Determine if the reference element is selected or not

**`See`**

[https://w3c.github.io/webdriver/#is-element-selected](https://w3c.github.io/webdriver/#is-element-selected)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`boolean`\>

True if the element is selected, False otherwise

#### Defined in

@appium/types/lib/driver.ts:958

___

### elementShadowRoot

▸ `Optional` **elementShadowRoot**(`elementId`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

Get the shadow root of an element

**`See`**

[https://w3c.github.io/webdriver/#get-element-shadow-root](https://w3c.github.io/webdriver/#get-element-shadow-root)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element to retrieve the shadow root for |

#### Returns

`Promise`<[`Element`](appium_types.Element.md)<`string`\>\>

The shadow root for an element, as an element

#### Defined in

@appium/types/lib/driver.ts:948

___

### endCoverage

▸ `Optional` **endCoverage**(`intent`, `path`): `Promise`<`void`\>

End platform-specific code coverage tracing

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `intent` | `string` | the Android intent for the coverage activity |
| `path` | `string` | the path to place the results |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1605

___

### equalsElement

▸ `Optional` **equalsElement**(`elementId`, `otherElementId`): `Promise`<`boolean`\>

Check whether two elements are identical

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the first element's ID |
| `otherElementId` | `string` | the second element's ID |

#### Returns

`Promise`<`boolean`\>

True if the elements are equal, false otherwise

#### Defined in

@appium/types/lib/driver.ts:1630

___

### execute

▸ `Optional` **execute**(`script`, `args`): `Promise`<`unknown`\>

Execute JavaScript (or some other kind of script) in the browser/app context

**`See`**

[https://w3c.github.io/webdriver/#execute-script](https://w3c.github.io/webdriver/#execute-script)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `script` | `string` | the string to be evaluated as the script, which will be made the body of an anonymous function in the case of JS |
| `args` | `unknown`[] | the list of arguments to be applied to the script as a function |

#### Returns

`Promise`<`unknown`\>

The return value of the script execution

#### Defined in

@appium/types/lib/driver.ts:1098

___

### executeAsync

▸ `Optional` **executeAsync**(`script`, `args`): `Promise`<`unknown`\>

Execute JavaScript (or some other kind of script) in the browser/app context, asynchronously

**`See`**

[https://w3c.github.io/webdriver/#execute-async-script](https://w3c.github.io/webdriver/#execute-async-script)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `script` | `string` | the string to be evaluated as the script, which will be made the body of an anonymous function in the case of JS |
| `args` | `unknown`[] | the list of arguments to be applied to the script as a function |

#### Returns

`Promise`<`unknown`\>

The promise resolution of the return value of the script execution (or an error
object if the promise is rejected)

#### Defined in

@appium/types/lib/driver.ts:1111

___

### executeCdp

▸ `Optional` **executeCdp**(`cmd`, `params`): `Promise`<`unknown`\>

Execute a devtools command

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `cmd` | `string` | the command |
| `params` | `unknown` | any command-specific command parameters |

#### Returns

`Promise`<`unknown`\>

The result of the command execution

#### Defined in

@appium/types/lib/driver.ts:1888

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

#### Inherited from

[Driver](appium_types.Driver.md).[executeCommand](appium_types.Driver.md#executecommand)

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

[Driver](appium_types.Driver.md).[executeMethod](appium_types.Driver.md#executemethod)

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

[Driver](appium_types.Driver.md).[findElOrEls](appium_types.Driver.md#findelorels)

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

[Driver](appium_types.Driver.md).[findElOrEls](appium_types.Driver.md#findelorels)

#### Defined in

@appium/types/lib/driver.ts:295

___

### findElOrElsWithProcessing

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

This is a wrapper for [`findElOrEls`](appium_types.ExternalDriver.md#findelorels) that validates locator strategies
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

[Driver](appium_types.Driver.md).[findElOrElsWithProcessing](appium_types.Driver.md#findelorelswithprocessing)

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

[Driver](appium_types.Driver.md).[findElOrElsWithProcessing](appium_types.Driver.md#findelorelswithprocessing)

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

[Driver](appium_types.Driver.md).[findElement](appium_types.Driver.md#findelement)

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

[Driver](appium_types.Driver.md).[findElementFromElement](appium_types.Driver.md#findelementfromelement)

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

[Driver](appium_types.Driver.md).[findElementFromShadowRoot](appium_types.Driver.md#findelementfromshadowroot)

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

[Driver](appium_types.Driver.md).[findElements](appium_types.Driver.md#findelements)

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

[Driver](appium_types.Driver.md).[findElementsFromElement](appium_types.Driver.md#findelementsfromelement)

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

[Driver](appium_types.Driver.md).[findElementsFromShadowRoot](appium_types.Driver.md#findelementsfromshadowroot)

#### Defined in

@appium/types/lib/driver.ts:278

___

### fingerprint

▸ `Optional` **fingerprint**(`fingerprintId`): `Promise`<`void`\>

Apply a synthetic fingerprint to the fingerprint detector of the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `fingerprintId` | `number` | the numeric ID of the fingerprint to use |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1283

___

### flick

▸ `Optional` **flick**(`element?`, `xSpeed?`, `ySpeed?`, `xOffset?`, `yOffset?`, `speed?`): `Promise`<`void`\>

Perform a flick event at the location specified

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `element?` | `string` | the element to make coordinates relative to |
| `xSpeed?` | `number` | the horizontal flick speed (in driver-specific units) |
| `ySpeed?` | `number` | the vertical flick speed (in driver-specific units) |
| `xOffset?` | `number` | the x coordinate |
| `yOffset?` | `number` | the y coordinate |
| `speed?` | `number` | the speed (unclear how this relates to xSpeed and ySpeed) |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1779

___

### forward

▸ `Optional` **forward**(): `Promise`<`void`\>

Navigate forward in the page history

**`See`**

[https://w3c.github.io/webdriver/#forward](https://w3c.github.io/webdriver/#forward)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:804

___

### fullScreenWindow

▸ `Optional` **fullScreenWindow**(): `Promise`<[`Rect`](appium_types.Rect.md)\>

Put the current window into full screen mode

**`See`**

[https://w3c.github.io/webdriver/#fullscreen-window](https://w3c.github.io/webdriver/#fullscreen-window)

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

The actual `Rect` of the window after running the command

#### Defined in

@appium/types/lib/driver.ts:930

___

### getActiveIMEEngine

▸ `Optional` **getActiveIMEEngine**(): `Promise`<`string`\>

Get the active IME engine

**`Deprecated`**

#### Returns

`Promise`<`string`\>

The name of the active engine

#### Defined in

@appium/types/lib/driver.ts:1648

___

### getAlertText

▸ `Optional` **getAlertText**(): `Promise`<``null`` \| `string`\>

Get the text of the displayed alert

**`See`**

[https://w3c.github.io/webdriver/#get-alert-text](https://w3c.github.io/webdriver/#get-alert-text)

#### Returns

`Promise`<``null`` \| `string`\>

The text of the alert

#### Defined in

@appium/types/lib/driver.ts:1186

___

### getAttribute

▸ `Optional` **getAttribute**(`name`, `elementId`): `Promise`<``null`` \| `string`\>

Retrieve the value of an element's attribute

**`See`**

[https://w3c.github.io/webdriver/#get-element-attribute](https://w3c.github.io/webdriver/#get-element-attribute)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the attribute name |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<``null`` \| `string`\>

The attribute value

#### Defined in

@appium/types/lib/driver.ts:969

___

### getAuthCredential

▸ `Optional` **getAuthCredential**(): `Promise`<[`Credential`](appium_types.Credential.md)[]\>

Get the list of public key credential sources

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-get-credentials](https://www.w3.org/TR/webauthn-2/#sctn-automation-get-credentials)

#### Returns

`Promise`<[`Credential`](appium_types.Credential.md)[]\>

The list of Credentials

#### Defined in

@appium/types/lib/driver.ts:1950

___

### getComputedLabel

▸ `Optional` **getComputedLabel**(`elementId`): `Promise`<``null`` \| `string`\>

Get the accessible name/label of an element

**`See`**

[https://w3c.github.io/webdriver/#get-computed-label](https://w3c.github.io/webdriver/#get-computed-label)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<``null`` \| `string`\>

The accessible name

#### Defined in

@appium/types/lib/driver.ts:1051

___

### getComputedRole

▸ `Optional` **getComputedRole**(`elementId`): `Promise`<``null`` \| `string`\>

Get the WAI-ARIA role of an element

**`See`**

[https://w3c.github.io/webdriver/#get-computed-role](https://w3c.github.io/webdriver/#get-computed-role)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<``null`` \| `string`\>

The role

#### Defined in

@appium/types/lib/driver.ts:1041

___

### getContexts

▸ `Optional` **getContexts**(): `Promise`<`Ctx`[]\>

Get the list of available contexts

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts)

#### Returns

`Promise`<`Ctx`[]\>

The list of context names

#### Defined in

@appium/types/lib/driver.ts:1827

___

### getCookie

▸ `Optional` **getCookie**(`name`): `Promise`<[`Cookie`](appium_types.Cookie.md)\>

Get a cookie by name

**`See`**

[https://w3c.github.io/webdriver/#get-named-cookie](https://w3c.github.io/webdriver/#get-named-cookie)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the name of the cookie |

#### Returns

`Promise`<[`Cookie`](appium_types.Cookie.md)\>

A serialized cookie

#### Defined in

@appium/types/lib/driver.ts:1129

___

### getCookies

▸ `Optional` **getCookies**(): `Promise`<[`Cookie`](appium_types.Cookie.md)[]\>

Get all cookies known to the browsing context

**`See`**

[https://w3c.github.io/webdriver/#get-all-cookies](https://w3c.github.io/webdriver/#get-all-cookies)

#### Returns

`Promise`<[`Cookie`](appium_types.Cookie.md)[]\>

A list of serialized cookies

#### Defined in

@appium/types/lib/driver.ts:1119

___

### getCssProperty

▸ `Optional` **getCssProperty**(`name`, `elementId`): `Promise`<`string`\>

Retrieve the value of a CSS property of an element

**`See`**

[https://w3c.github.io/webdriver/#get-element-css-value](https://w3c.github.io/webdriver/#get-element-css-value)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the CSS property name |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`string`\>

The property value

#### Defined in

@appium/types/lib/driver.ts:991

___

### getCurrentActivity

▸ `Optional` **getCurrentActivity**(): `Promise`<`string`\>

Get the current activity name

**`Deprecated`**

#### Returns

`Promise`<`string`\>

The activity name

#### Defined in

@appium/types/lib/driver.ts:1393

___

### getCurrentContext

▸ `Optional` **getCurrentContext**(): `Promise`<``null`` \| `Ctx`\>

Get the currently active context

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts)

#### Returns

`Promise`<``null`` \| `Ctx`\>

The context name

#### Defined in

@appium/types/lib/driver.ts:1811

___

### getCurrentPackage

▸ `Optional` **getCurrentPackage**(): `Promise`<`string`\>

Get the current active app package name/id

**`Deprecated`**

#### Returns

`Promise`<`string`\>

The package name

#### Defined in

@appium/types/lib/driver.ts:1403

___

### getDeviceTime

▸ `Optional` **getDeviceTime**(`format?`): `Promise`<`string`\>

Get the current time on the device under timeouts

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `format?` | `string` | the date/time format you would like the response into |

#### Returns

`Promise`<`string`\>

The formatted time

#### Defined in

@appium/types/lib/driver.ts:1223

___

### getDisplayDensity

▸ `Optional` **getDisplayDensity**(): `Promise`<`number`\>

Get the display's pixel density

**`Deprecated`**

#### Returns

`Promise`<`number`\>

The density

#### Defined in

@appium/types/lib/driver.ts:1594

___

### getElementRect

▸ `Optional` **getElementRect**(`elementId`): `Promise`<[`Rect`](appium_types.Rect.md)\>

Get the dimensions and position of an element

**`See`**

[https://w3c.github.io/webdriver/#get-element-rect](https://w3c.github.io/webdriver/#get-element-rect)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

The Rect object containing x, y, width, and height properties

#### Defined in

@appium/types/lib/driver.ts:1021

___

### getElementScreenshot

▸ `Optional` **getElementScreenshot**(`elementId`): `Promise`<`string`\>

Get an image of a single element as rendered on screen

**`See`**

[https://w3c.github.io/webdriver/#take-element-screenshot](https://w3c.github.io/webdriver/#take-element-screenshot)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`string`\>

A base64-encoded string representing the PNG image data for the element rect

#### Defined in

@appium/types/lib/driver.ts:1212

___

### getGeoLocation

▸ `Optional` **getGeoLocation**(): `Promise`<[`Location`](appium_types.Location.md)\>

Get the virtual or real geographical location of a device

#### Returns

`Promise`<[`Location`](appium_types.Location.md)\>

The location

#### Defined in

@appium/types/lib/driver.ts:1793

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

[Driver](appium_types.Driver.md).[getLog](appium_types.Driver.md#getlog)

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

[Driver](appium_types.Driver.md).[getLogEvents](appium_types.Driver.md#getlogevents)

#### Defined in

@appium/types/lib/driver.ts:157

___

### getLogTypes

▸ **getLogTypes**(): `Promise`<`string`[]\>

Get available log types as a list of strings

#### Returns

`Promise`<`string`[]\>

#### Inherited from

[Driver](appium_types.Driver.md).[getLogTypes](appium_types.Driver.md#getlogtypes)

#### Defined in

@appium/types/lib/driver.ts:339

___

### getManagedDrivers

▸ **getManagedDrivers**(): [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Returns

[`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>[]

#### Inherited from

[Driver](appium_types.Driver.md).[getManagedDrivers](appium_types.Driver.md#getmanageddrivers)

#### Defined in

@appium/types/lib/driver.ts:643

___

### getName

▸ `Optional` **getName**(`elementId`): `Promise`<`string`\>

Get the tag name of an element

**`See`**

[https://w3c.github.io/webdriver/#get-element-tag-name](https://w3c.github.io/webdriver/#get-element-tag-name)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`string`\>

The tag name

#### Defined in

@appium/types/lib/driver.ts:1011

___

### getNetworkConnection

▸ `Optional` **getNetworkConnection**(): `Promise`<`number`\>

Get the network connection state of a device

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-modes](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-modes)

#### Returns

`Promise`<`number`\>

A number which is a bitmask representing categories like Data, Wifi, and Airplane
mode status

#### Defined in

@appium/types/lib/driver.ts:1848

___

### getOrientation

▸ `Optional` **getOrientation**(): `Promise`<`string`\>

Get the device orientation

#### Returns

`Promise`<`string`\>

The orientation string

#### Defined in

@appium/types/lib/driver.ts:1680

___

### getPageIndex

▸ `Optional` **getPageIndex**(`elementId`): `Promise`<`string`\>

Get the index of an element on the page

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the element id |

#### Returns

`Promise`<`string`\>

The page index

#### Defined in

@appium/types/lib/driver.ts:1839

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

[Driver](appium_types.Driver.md).[getPageSource](appium_types.Driver.md#getpagesource)

#### Defined in

@appium/types/lib/driver.ts:327

___

### getPerformanceData

▸ `Optional` **getPerformanceData**(`packageName`, `dataType`, `dataReadTimeout?`): `Promise`<`any`\>

Get the list of performance data associated with a given type

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `packageName` | `string` | the package name / id of the app to retrieve data for |
| `dataType` | `string` | the performance data type; one of those retrieved in a call to getPerformanceDataTypes |
| `dataReadTimeout?` | `number` | how long to wait for data before timing out |

#### Returns

`Promise`<`any`\>

A list of performance data strings

#### Defined in

@appium/types/lib/driver.ts:1247

___

### getPerformanceDataTypes

▸ `Optional` **getPerformanceDataTypes**(): `Promise`<`string`[]\>

List the performance data types supported by this driver, which can be used in a call to get
the performance data by type.

**`Deprecated`**

#### Returns

`Promise`<`string`[]\>

The list of types

#### Defined in

@appium/types/lib/driver.ts:1233

___

### getProperty

▸ `Optional` **getProperty**(`name`, `elementId`): `Promise`<``null`` \| `string`\>

Retrieve the value of a named property of an element's JS object

**`See`**

[https://w3c.github.io/webdriver/#get-element-property](https://w3c.github.io/webdriver/#get-element-property)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the object property name |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<``null`` \| `string`\>

The property value

#### Defined in

@appium/types/lib/driver.ts:980

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

[Driver](appium_types.Driver.md).[getProxyAvoidList](appium_types.Driver.md#getproxyavoidlist)

#### Defined in

@appium/types/lib/driver.ts:639

___

### getRotation

▸ `Optional` **getRotation**(): `Promise`<[`Rotation`](appium_types.Rotation.md)\>

Get the current rotation state of the device

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-rotation](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-rotation)

#### Returns

`Promise`<[`Rotation`](appium_types.Rotation.md)\>

The Rotation object consisting of x, y, and z rotation values (0 <= n <= 360)

#### Defined in

@appium/types/lib/driver.ts:1866

___

### getScreenshot

▸ `Optional` **getScreenshot**(): `Promise`<`string`\>

Get a screenshot of the current document as rendered

**`See`**

[https://w3c.github.io/webdriver/#take-screenshot](https://w3c.github.io/webdriver/#take-screenshot)

#### Returns

`Promise`<`string`\>

A base64-encoded string representing the PNG image data

#### Defined in

@appium/types/lib/driver.ts:1202

___

### getSession

▸ **getSession**(): `Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

Get the data for the current session

#### Returns

`Promise`<[`SingularSessionData`](../modules/appium_types.md#singularsessiondata)<`C`, `SessionData`\>\>

A session data object

#### Inherited from

[Driver](appium_types.Driver.md).[getSession](appium_types.Driver.md#getsession)

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

[Driver](appium_types.Driver.md).[getSessions](appium_types.Driver.md#getsessions)

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

[Driver](appium_types.Driver.md).[getSettings](appium_types.Driver.md#getsettings)

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

[Driver](appium_types.Driver.md).[getStatus](appium_types.Driver.md#getstatus)

#### Defined in

@appium/types/lib/driver.ts:631

___

### getSystemBars

▸ `Optional` **getSystemBars**(): `Promise`<`unknown`\>

Get information from the system bars of a device

**`Deprecated`**

#### Returns

`Promise`<`unknown`\>

An array of information objects of driver-specific shape

#### Defined in

@appium/types/lib/driver.ts:1584

___

### getText

▸ `Optional` **getText**(`elementId`): `Promise`<`string`\>

Get the text of an element as rendered

**`See`**

[https://w3c.github.io/webdriver/#get-element-text](https://w3c.github.io/webdriver/#get-element-text)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`string`\>

The text rendered for the element

#### Defined in

@appium/types/lib/driver.ts:1001

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

[Driver](appium_types.Driver.md).[getTimeouts](appium_types.Driver.md#gettimeouts)

#### Defined in

@appium/types/lib/driver.ts:77

___

### getUrl

▸ `Optional` **getUrl**(): `Promise`<`string`\>

Get the current url

**`See`**

[https://w3c.github.io/webdriver/#get-current-url](https://w3c.github.io/webdriver/#get-current-url)

#### Returns

`Promise`<`string`\>

The url

#### Defined in

@appium/types/lib/driver.ts:792

___

### getWindowHandle

▸ `Optional` **getWindowHandle**(): `Promise`<`string`\>

Get the handle (id) associated with the current browser window

**`See`**

[https://w3c.github.io/webdriver/#get-window-handle](https://w3c.github.io/webdriver/#get-window-handle)

#### Returns

`Promise`<`string`\>

The handle string

#### Defined in

@appium/types/lib/driver.ts:837

___

### getWindowHandles

▸ `Optional` **getWindowHandles**(): `Promise`<`string`[]\>

Get a set of handles representing open browser windows

**`See`**

[https://w3c.github.io/webdriver/#get-window-handles](https://w3c.github.io/webdriver/#get-window-handles)

#### Returns

`Promise`<`string`[]\>

An array of window handles representing currently-open windows

#### Defined in

@appium/types/lib/driver.ts:861

___

### getWindowRect

▸ `Optional` **getWindowRect**(): `Promise`<[`Rect`](appium_types.Rect.md)\>

Get the size and position of the current window

**`See`**

[https://w3c.github.io/webdriver/#get-window-rect](https://w3c.github.io/webdriver/#get-window-rect)

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

A `Rect` JSON object with x, y, width, and height properties

#### Defined in

@appium/types/lib/driver.ts:893

___

### gsmCall

▸ `Optional` **gsmCall**(`phoneNumber`, `action`): `Promise`<`void`\>

Simulate triggering a phone call from a phone number and having the device take an action in
response

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `phoneNumber` | `string` | the number to pretend the call is from |
| `action` | `string` | the action to take in response (accept, reject, etc...) |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1304

___

### gsmSignal

▸ `Optional` **gsmSignal**(`signalStrength`): `Promise`<`void`\>

Simulate setting the GSM signal strength for a cell phone

**`Deprecated`**

#### Parameters

| Name | Type |
| :------ | :------ |
| `signalStrength` | `string` \| `number` |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1313

___

### gsmVoice

▸ `Optional` **gsmVoice**(`state`): `Promise`<`void`\>

Do something with GSM voice (unclear; this should not be implemented anywhere)

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `state` | `string` | the state |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1323

___

### hideKeyboard

▸ `Optional` **hideKeyboard**(`strategy?`, `key?`, `keyCode?`, `keyName?`): `Promise`<`boolean`\>

Attempt to hide a virtual keyboard

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `strategy?` | `string` | the driver-specific name of a hiding strategy to follow |
| `key?` | `string` | the text of a key to use to hide the keyboard |
| `keyCode?` | `string` | a key code to trigger to hide the keyboard |
| `keyName?` | `string` | the name of a key to use to hide the keyboard |

#### Returns

`Promise`<`boolean`\>

Whether the keyboard was successfully hidden. May never return `false` on some platforms

#### Defined in

@appium/types/lib/driver.ts:1467

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

[Driver](appium_types.Driver.md).[implicitWait](appium_types.Driver.md#implicitwait)

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

[Driver](appium_types.Driver.md).[implicitWaitForCondition](appium_types.Driver.md#implicitwaitforcondition)

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

[Driver](appium_types.Driver.md).[implicitWaitMJSONWP](appium_types.Driver.md#implicitwaitmjsonwp)

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

[Driver](appium_types.Driver.md).[implicitWaitW3C](appium_types.Driver.md#implicitwaitw3c)

#### Defined in

@appium/types/lib/driver.ts:84

___

### installApp

▸ `Optional` **installApp**(`appPath`, `options?`): `Promise`<`void`\>

Install an app on a device

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appPath` | `string` | the absolute path to a local app or a URL of a downloadable app bundle |
| `options?` | `unknown` | driver-specific install options |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1411

___

### isAppInstalled

▸ `Optional` **isAppInstalled**(`appId`): `Promise`<`boolean`\>

Determine whether an app is installed

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appId` | `string` | the package or bundle ID of the application |

#### Returns

`Promise`<`boolean`\>

#### Defined in

@appium/types/lib/driver.ts:1444

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

[Driver](appium_types.Driver.md).[isFeatureEnabled](appium_types.Driver.md#isfeatureenabled)

#### Defined in

@appium/types/lib/driver.ts:635

___

### isIMEActivated

▸ `Optional` **isIMEActivated**(): `Promise`<`boolean`\>

Determine whether an IME is active

**`Deprecated`**

#### Returns

`Promise`<`boolean`\>

True if the IME is activated

#### Defined in

@appium/types/lib/driver.ts:1657

___

### isKeyboardShown

▸ `Optional` **isKeyboardShown**(): `Promise`<`boolean`\>

Determine whether the keyboard is shown

#### Returns

`Promise`<`boolean`\>

Whether the keyboard is shown

#### Defined in

@appium/types/lib/driver.ts:1479

___

### isMjsonwpProtocol

▸ **isMjsonwpProtocol**(): `boolean`

#### Returns

`boolean`

#### Inherited from

[Driver](appium_types.Driver.md).[isMjsonwpProtocol](appium_types.Driver.md#ismjsonwpprotocol)

#### Defined in

@appium/types/lib/driver.ts:634

___

### isW3CProtocol

▸ **isW3CProtocol**(): `boolean`

#### Returns

`boolean`

#### Inherited from

[Driver](appium_types.Driver.md).[isW3CProtocol](appium_types.Driver.md#isw3cprotocol)

#### Defined in

@appium/types/lib/driver.ts:633

___

### keyevent

▸ `Optional` **keyevent**(`keycode`, `metastate?`): `Promise`<`void`\>

Simulate a keyevent on the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `keycode` | `string` \| `number` | the manufacturer defined keycode |
| `metastate?` | `string` \| `number` | the combination of meta startUnexpectedShutdown |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1360

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

[Driver](appium_types.Driver.md).[logCustomEvent](appium_types.Driver.md#logcustomevent)

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

[Driver](appium_types.Driver.md).[logEvent](appium_types.Driver.md#logevent)

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

#### Inherited from

[Driver](appium_types.Driver.md).[logExtraCaps](appium_types.Driver.md#logextracaps)

#### Defined in

@appium/types/lib/driver.ts:749

___

### longPressKeyCode

▸ `Optional` **longPressKeyCode**(`keycode`, `metastate?`, `flags?`): `Promise`<`void`\>

Press a device hardware key by its code for a longer duration

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `keycode` | `number` | the keycode |
| `metastate?` | `number` | the code denoting the simultaneous pressing of any meta keys (shift etc) |
| `flags?` | `number` | the code denoting the combination of extra flags |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1274

___

### maximizeWindow

▸ `Optional` **maximizeWindow**(): `Promise`<[`Rect`](appium_types.Rect.md)\>

Run the window-manager specific 'maximize' operation on the current window

**`See`**

[https://w3c.github.io/webdriver/#maximize-window](https://w3c.github.io/webdriver/#maximize-window)

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

The actual `Rect` of the window after running the command

#### Defined in

@appium/types/lib/driver.ts:914

___

### minimizeWindow

▸ `Optional` **minimizeWindow**(): `Promise`<[`Rect`](appium_types.Rect.md)\>

Run the window-manager specific 'minimize' operation on the current window

**`See`**

[https://w3c.github.io/webdriver/#minimize-window](https://w3c.github.io/webdriver/#minimize-window)

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

The actual `Rect` of the window after running the command

#### Defined in

@appium/types/lib/driver.ts:922

___

### mobileRotation

▸ `Optional` **mobileRotation**(`x`, `y`, `radius`, `rotation`, `touchCount`, `duration`, `elementId?`): `Promise`<`void`\>

Construct a rotation gesture? Unclear what this command does and it does not appear to be used

**`Deprecated`**

Use setRotation instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `x` | `number` | the x coordinate of the rotation center |
| `y` | `number` | the y coordinate of the rotation center |
| `radius` | `number` | the radius of the rotation circle |
| `rotation` | `number` | the rotation angle? idk |
| `touchCount` | `number` | how many fingers to rotate |
| `duration` | `string` | - |
| `elementId?` | `string` | if we're rotating around an element |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1375

___

### networkSpeed

▸ `Optional` **networkSpeed**(`netspeed`): `Promise`<`void`\>

Set the network speed of the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `netspeed` | `string` | the speed as a string, like '3G' |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1350

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

[Driver](appium_types.Driver.md).[newCommandTimeout](appium_types.Driver.md#newcommandtimeout)

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

[Driver](appium_types.Driver.md).[onUnexpectedShutdown](appium_types.Driver.md#onunexpectedshutdown)

#### Defined in

@appium/types/lib/driver.ts:599

___

### openNotifications

▸ `Optional` **openNotifications**(): `Promise`<`void`\>

Open the notifications shade/screen

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1545

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

[Driver](appium_types.Driver.md).[pageLoadTimeoutMJSONWP](appium_types.Driver.md#pageloadtimeoutmjsonwp)

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

[Driver](appium_types.Driver.md).[pageLoadTimeoutW3C](appium_types.Driver.md#pageloadtimeoutw3c)

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

[Driver](appium_types.Driver.md).[parseTimeoutArgument](appium_types.Driver.md#parsetimeoutargument)

#### Defined in

@appium/types/lib/driver.ts:138

___

### performActions

▸ `Optional` **performActions**(`actions`): `Promise`<`void`\>

Perform touch or keyboard actions

**`See`**

[https://w3c.github.io/webdriver/#perform-actions](https://w3c.github.io/webdriver/#perform-actions)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `actions` | [`ActionSequence`](../modules/appium_types.md#actionsequence)[] | the action sequence |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1160

___

### postAcceptAlert

▸ `Optional` **postAcceptAlert**(): `Promise`<`void`\>

Accept a simple dialog/alert

**`See`**

[https://w3c.github.io/webdriver/#accept-alert](https://w3c.github.io/webdriver/#accept-alert)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1178

___

### postDismissAlert

▸ `Optional` **postDismissAlert**(): `Promise`<`void`\>

Dismiss a simple dialog/alert

**`See`**

[https://w3c.github.io/webdriver/#dismiss-alert](https://w3c.github.io/webdriver/#dismiss-alert)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1172

___

### powerAC

▸ `Optional` **powerAC**(`state`): `Promise`<`void`\>

Set the AC-connected power state of the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `state` | `string` | whether the device is connected to power or not |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1341

___

### powerCapacity

▸ `Optional` **powerCapacity**(`percent`): `Promise`<`void`\>

Set the simulated power capacity of the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `percent` | `number` | how full the battery should become |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1332

___

### pressKeyCode

▸ `Optional` **pressKeyCode**(`keycode`, `metastate?`, `flags?`): `Promise`<`void`\>

Press a device hardware key by its code for the default duration

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `keycode` | `number` | the keycode |
| `metastate?` | `number` | the code denoting the simultaneous pressing of any meta keys (shift etc) |
| `flags?` | `number` | the code denoting the combination of extra flags |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1262

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

[Driver](appium_types.Driver.md).[proxyActive](appium_types.Driver.md#proxyactive)

#### Defined in

@appium/types/lib/driver.ts:638

___

### proxyCommand

▸ `Optional` **proxyCommand**<`TReq`, `TRes`\>(`url`, `method`, `body?`): `Promise`<`TRes`\>

Proxy a command to a connected WebDriver server

#### Type parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `TReq` | `any` | the type of the incoming body |
| `TRes` | `unknown` | the type of the return value |

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `url` | `string` | the incoming URL |
| `method` | [`HTTPMethod`](../modules/appium_types.md#httpmethod) | the incoming HTTP method |
| `body?` | `TReq` | the incoming HTTP body |

#### Returns

`Promise`<`TRes`\>

The return value of the proxied command

#### Defined in

@appium/types/lib/driver.ts:1986

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

[Driver](appium_types.Driver.md).[proxyRouteIsAvoided](appium_types.Driver.md#proxyrouteisavoided)

#### Defined in

@appium/types/lib/driver.ts:641

___

### pullFile

▸ `Optional` **pullFile**(`path`): `Promise`<`string`\>

Retrieve the data from a file on the device at a given path

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `path` | `string` | the remote path on the device to pull file data from |

#### Returns

`Promise`<`string`\>

The base64-encoded file data

#### Defined in

@appium/types/lib/driver.ts:1496

___

### pullFolder

▸ `Optional` **pullFolder**(`path`): `Promise`<`string`\>

Retrieve the data from a folder on the device at a given path

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `path` | `string` | the remote path of a directory on the device |

#### Returns

`Promise`<`string`\>

The base64-encoded zip file of the directory contents

#### Defined in

@appium/types/lib/driver.ts:1505

___

### pushFile

▸ `Optional` **pushFile**(`path`, `data`): `Promise`<`void`\>

Push data to a file at a remote path on the device

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `path` | `string` | the remote path on the device to create the file at |
| `data` | `string` | the base64-encoded data which will be decoded and written to `path` |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1487

___

### queryAppState

▸ `Optional` **queryAppState**(`appId`): `Promise`<``0`` \| ``2`` \| ``1`` \| ``3`` \| ``4``\>

Get the running state of an app

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appId` | `string` | the package or bundle ID of the application |

#### Returns

`Promise`<``0`` \| ``2`` \| ``1`` \| ``3`` \| ``4``\>

A number representing the state. `0` means not installed, `1` means not running, `2`
means running in background but suspended, `3` means running in the background, and `4` means
running in the foreground

#### Defined in

@appium/types/lib/driver.ts:1455

___

### refresh

▸ `Optional` **refresh**(): `Promise`<`void`\>

Refresh the page

**`See`**

[https://w3c.github.io/webdriver/#refresh](https://w3c.github.io/webdriver/#refresh)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:810

___

### releaseActions

▸ `Optional` **releaseActions**(): `Promise`<`void`\>

Release all keys or buttons that are currently pressed

**`See`**

[https://w3c.github.io/webdriver/#release-actions](https://w3c.github.io/webdriver/#release-actions)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1166

___

### removeAllAuthCredentials

▸ `Optional` **removeAllAuthCredentials**(): `Promise`<`void`\>

Remove all auth credentials

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-remove-all-credentials](https://www.w3.org/TR/webauthn-2/#sctn-automation-remove-all-credentials)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1956

___

### removeApp

▸ `Optional` **removeApp**(`appId`, `options?`): `Promise`<`boolean`\>

Remove / uninstall an app

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appId` | `string` | the package or bundle ID of the application |
| `options?` | `unknown` | driver-specific launch options |

#### Returns

`Promise`<`boolean`\>

`true` if successful

#### Defined in

@appium/types/lib/driver.ts:1429

___

### removeAuthCredential

▸ `Optional` **removeAuthCredential**(`credentialId`, `authenticatorId`): `Promise`<`void`\>

Remove a specific auth credential

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `credentialId` | `string` | the credential ID |
| `authenticatorId` | `string` | the authenticator ID |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1964

___

### removeVirtualAuthenticator

▸ `Optional` **removeVirtualAuthenticator**(`authenticatorId`): `Promise`<`void`\>

Remove a virtual authenticator

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-remove-virtual-authenticator](https://www.w3.org/TR/webauthn-2/#sctn-automation-remove-virtual-authenticator)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `authenticatorId` | `string` | the ID returned in the call to add the authenticator |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1920

___

### replaceValue

▸ `Optional` **replaceValue**(`value`, `elementId`): `Promise`<`void`\>

Set the value of a text field but ensure the current value is replace and not appended

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `value` | `string` | the text to set |
| `elementId` | `string` | the element to set it in |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1616

___

### reset

▸ **reset**(): `Promise`<`void`\>

Reset the current session (run the delete session and create session subroutines)

**`Deprecated`**

Use explicit session management commands instead

#### Returns

`Promise`<`void`\>

#### Inherited from

[Driver](appium_types.Driver.md).[reset](appium_types.Driver.md#reset)

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

[Driver](appium_types.Driver.md).[scriptTimeoutMJSONWP](appium_types.Driver.md#scripttimeoutmjsonwp)

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

[Driver](appium_types.Driver.md).[scriptTimeoutW3C](appium_types.Driver.md#scripttimeoutw3c)

#### Defined in

@appium/types/lib/driver.ts:114

___

### sendSMS

▸ `Optional` **sendSMS**(`phoneNumber`, `message`): `Promise`<`void`\>

Simulate sending an SMS message from a certain phone number to the device

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `phoneNumber` | `string` | the number to pretend the message is from |
| `message` | `string` | the SMS text |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1293

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

[Driver](appium_types.Driver.md).[sessionExists](appium_types.Driver.md#sessionexists)

#### Defined in

@appium/types/lib/driver.ts:632

___

### setAlertText

▸ `Optional` **setAlertText**(`text`): `Promise`<`void`\>

Set the text field of an alert prompt

**`See`**

[https://w3c.github.io/webdriver/#send-alert-text](https://w3c.github.io/webdriver/#send-alert-text)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `text` | `string` | the text to send to the prompt |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1194

___

### setContext

▸ `Optional` **setContext**(`name`, `...args`): `Promise`<`void`\>

Switch to a context by name

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#webviews-and-other-contexts)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `name` | `string` | the context name |
| `...args` | `any`[] | - |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1819

___

### setCookie

▸ `Optional` **setCookie**(`cookie`): `Promise`<`void`\>

Add a cookie to the browsing context

**`See`**

[https://w3c.github.io/webdriver/#add-cookie](https://w3c.github.io/webdriver/#add-cookie)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `cookie` | [`Cookie`](appium_types.Cookie.md) | the cookie data including properties like name, value, path, domain, secure, httpOnly, expiry, and samesite |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1138

___

### setFrame

▸ `Optional` **setFrame**(`id`): `Promise`<`void`\>

Switch the current browsing context to a frame

**`See`**

[https://w3c.github.io/webdriver/#switch-to-frame](https://w3c.github.io/webdriver/#switch-to-frame)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `id` | ``null`` \| `string` \| `number` | the frame id, index, or `null` (indicating the top-level context) |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:879

___

### setGeoLocation

▸ `Optional` **setGeoLocation**(`location`): `Promise`<[`Location`](appium_types.Location.md)\>

Set the virtual geographical location of a device

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `location` | `Partial`<[`Location`](appium_types.Location.md)\> | the location including latitude and longitude |

#### Returns

`Promise`<[`Location`](appium_types.Location.md)\>

The complete location

#### Defined in

@appium/types/lib/driver.ts:1801

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

[Driver](appium_types.Driver.md).[setImplicitWait](appium_types.Driver.md#setimplicitwait)

#### Defined in

@appium/types/lib/driver.ts:60

___

### setNetworkConnection

▸ `Optional` **setNetworkConnection**(`type`): `Promise`<`number`\>

Set the network connection of the device

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-modes](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-modes)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `type` | `number` | the bitmask representing network state |

#### Returns

`Promise`<`number`\>

A number which is a bitmask representing categories like Data, Wifi, and Airplane
mode status

#### Defined in

@appium/types/lib/driver.ts:1858

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

[Driver](appium_types.Driver.md).[setNewCommandTimeout](appium_types.Driver.md#setnewcommandtimeout)

#### Defined in

@appium/types/lib/driver.ts:43

___

### setOrientation

▸ `Optional` **setOrientation**(`orientation`): `Promise`<`void`\>

Set the device orientation

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `orientation` | `string` | the orientation string |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1687

___

### setRotation

▸ `Optional` **setRotation**(`x`, `y`, `z`): `Promise`<`void`\>

Set the device rotation state

**`See`**

[https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-rotation](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md#device-rotation)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `x` | `number` | the degree to which the device is rotated around the x axis (0 <= x <= 360) |
| `y` | `number` | the degree to which the device is rotated around the y axis (0 <= y <= 360) |
| `z` | `number` | the degree to which the device is rotated around the z axis (0 <= z <= 360) |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1876

___

### setUrl

▸ `Optional` **setUrl**(`url`): `Promise`<`void`\>

Navigate to a given url

**`See`**

[https://w3c.github.io/webdriver/#navigate-to](https://w3c.github.io/webdriver/#navigate-to)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `url` | `string` | the url |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:784

___

### setUserAuthVerified

▸ `Optional` **setUserAuthVerified**(`isUserVerified`, `authenticatorId`): `Promise`<`void`\>

Set the isUserVerified property of an authenticator

**`See`**

[https://www.w3.org/TR/webauthn-2/#sctn-automation-set-user-verified](https://www.w3.org/TR/webauthn-2/#sctn-automation-set-user-verified)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `isUserVerified` | `boolean` | the value of the isUserVerified property |
| `authenticatorId` | `string` | the authenticator id |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1973

___

### setValue

▸ `Optional` **setValue**(`text`, `elementId`): `Promise`<`void`\>

Send keystrokes to an element (or otherwise set its value)

**`See`**

[https://w3c.github.io/webdriver/#element-send-keys](https://w3c.github.io/webdriver/#element-send-keys)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `text` | `string` | the text to send to the element |
| `elementId` | `string` | the id of the element |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1086

___

### setWindow

▸ `Optional` **setWindow**(`handle`): `Promise`<`void`\>

Switch to a specified window

**`See`**

[https://w3c.github.io/webdriver/#switch-to-window](https://w3c.github.io/webdriver/#switch-to-window)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `handle` | `string` | the window handle of the window to make active |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:853

___

### setWindowRect

▸ `Optional` **setWindowRect**(`x`, `y`, `width`, `height`): `Promise`<[`Rect`](appium_types.Rect.md)\>

Set the current window's size and position

**`See`**

[https://w3c.github.io/webdriver/#set-window-rect](https://w3c.github.io/webdriver/#set-window-rect)

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `x` | `number` | the screen coordinate for the new left edge of the window |
| `y` | `number` | the screen coordinate for the new top edge of the window |
| `width` | `number` | the width in pixels to resize the window to |
| `height` | `number` | the height in pixels to resize the window to |

#### Returns

`Promise`<[`Rect`](appium_types.Rect.md)\>

The actual `Rect` of the window after running the command

#### Defined in

@appium/types/lib/driver.ts:906

___

### startActivity

▸ `Optional` **startActivity**(`appPackage`, `appActivity`, `appWaitPackage?`, `appWaitActivity?`, `intentAction?`, `intentCategory?`, `intentFlags?`, `optionalIntentArguments?`, `dontStopAppOnReset?`): `Promise`<`void`\>

Start an Android activity within an app

**`Deprecated`**

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appPackage` | `string` | the app package id |
| `appActivity` | `string` | the activity name |
| `appWaitPackage?` | `string` | the package id to wait for if different from the app package |
| `appWaitActivity?` | `string` | the activity name to wait for being active if different from appActivity |
| `intentAction?` | `string` | the action for the intent to use to start the activity |
| `intentCategory?` | `string` | the category for the intent |
| `intentFlags?` | `string` | - |
| `optionalIntentArguments?` | `string` | additional arguments to be passed to launching the intent |
| `dontStopAppOnReset?` | `boolean` | set to true to not stop the current app before launching the activity |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1565

___

### startNewCommandTimeout

▸ **startNewCommandTimeout**(): `Promise`<`void`\>

Start the timer for the New Command Timeout, which when it runs out, will stop the current
session

#### Returns

`Promise`<`void`\>

#### Inherited from

[Driver](appium_types.Driver.md).[startNewCommandTimeout](appium_types.Driver.md#startnewcommandtimeout)

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

#### Inherited from

[Driver](appium_types.Driver.md).[startUnexpectedShutdown](appium_types.Driver.md#startunexpectedshutdown)

#### Defined in

@appium/types/lib/driver.ts:700

___

### switchToParentFrame

▸ `Optional` **switchToParentFrame**(): `Promise`<`void`\>

Set the current browsing context to the parent of the current context

**`See`**

[https://w3c.github.io/webdriver/#switch-to-parent-frame](https://w3c.github.io/webdriver/#switch-to-parent-frame)

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:885

___

### terminateApp

▸ `Optional` **terminateApp**(`appId`, `options?`): `Promise`<`boolean`\>

Quit / terminate / stop a running application

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `appId` | `string` | the package or bundle ID of the application |
| `options?` | `unknown` | driver-specific launch options |

#### Returns

`Promise`<`boolean`\>

#### Defined in

@appium/types/lib/driver.ts:1437

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

[Driver](appium_types.Driver.md).[timeouts](appium_types.Driver.md#timeouts)

#### Defined in

@appium/types/lib/driver.ts:30

___

### title

▸ `Optional` **title**(): `Promise`<`string`\>

Get the current page title

**`See`**

[https://w3c.github.io/webdriver/#get-title](https://w3c.github.io/webdriver/#get-title)

**`Example`**

```js
await driver.getTitle()
```
```py
driver.title
```
```java
driver.getTitle();
```

#### Returns

`Promise`<`string`\>

The title

#### Defined in

@appium/types/lib/driver.ts:829

___

### toggleData

▸ `Optional` **toggleData**(): `Promise`<`void`\>

Toggle cell network data

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1521

___

### toggleFlightMode

▸ `Optional` **toggleFlightMode**(): `Promise`<`void`\>

Toggle airplane/flight mode for the device

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1513

___

### toggleLocationServices

▸ `Optional` **toggleLocationServices**(): `Promise`<`void`\>

Toggle location services for the device

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1537

___

### toggleWiFi

▸ `Optional` **toggleWiFi**(): `Promise`<`void`\>

Toggle WiFi radio status

**`Deprecated`**

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1529

___

### touchDown

▸ `Optional` **touchDown**(`element`, `x`, `y`): `Promise`<`void`\>

Perform a touch down event at the location specified

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `element` | `string` | - |
| `x` | `number` | the x coordinate |
| `y` | `number` | the y coordinate |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1735

___

### touchLongClick

▸ `Optional` **touchLongClick**(`element`, `x`, `y`, `duration`): `Promise`<`void`\>

Perform a long touch down event at the location specified

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type |
| :------ | :------ |
| `element` | `string` |
| `x` | `number` |
| `y` | `number` |
| `duration` | `number` |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1764

___

### touchMove

▸ `Optional` **touchMove**(`element`, `x`, `y`): `Promise`<`void`\>

Perform a touch move event at the location specified

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `element` | `string` | - |
| `x` | `number` | the x coordinate |
| `y` | `number` | the y coordinate |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1755

___

### touchUp

▸ `Optional` **touchUp**(`element`, `x`, `y`): `Promise`<`void`\>

Perform a touch up event at the location specified

**`Deprecated`**

Use the Actions API instead

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `element` | `string` | - |
| `x` | `number` | the x coordinate |
| `y` | `number` | the y coordinate |

#### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/driver.ts:1745

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

#### Inherited from

[Driver](appium_types.Driver.md).[validateDesiredCaps](appium_types.Driver.md#validatedesiredcaps)

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

[Driver](appium_types.Driver.md).[validateLocatorStrategy](appium_types.Driver.md#validatelocatorstrategy)

#### Defined in

@appium/types/lib/driver.ts:637
