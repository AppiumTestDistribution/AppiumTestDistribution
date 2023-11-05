# Interface: ServerOpts<\>

[@appium/base-driver](../modules/appium_base_driver.md).ServerOpts

## Table of contents

### Properties

- [allowCors](appium_base_driver.ServerOpts.md#allowcors)
- [basePath](appium_base_driver.ServerOpts.md#basepath)
- [cliArgs](appium_base_driver.ServerOpts.md#cliargs)
- [extraMethodMap](appium_base_driver.ServerOpts.md#extramethodmap)
- [hostname](appium_base_driver.ServerOpts.md#hostname)
- [keepAliveTimeout](appium_base_driver.ServerOpts.md#keepalivetimeout)
- [port](appium_base_driver.ServerOpts.md#port)
- [routeConfiguringFunction](appium_base_driver.ServerOpts.md#routeconfiguringfunction)
- [serverUpdaters](appium_base_driver.ServerOpts.md#serverupdaters)

## Properties

### allowCors

• **allowCors**: `undefined` \| `boolean`

#### Defined in

@appium/base-driver/lib/express/server.js:343

___

### basePath

• **basePath**: `undefined` \| `string`

#### Defined in

@appium/base-driver/lib/express/server.js:344

___

### cliArgs

• **cliArgs**: `undefined` \| [`ServerArgs`](../modules/appium_types.md#serverargs)

#### Defined in

@appium/base-driver/lib/express/server.js:341

___

### extraMethodMap

• **extraMethodMap**: `undefined` \| `Readonly`<[`DriverMethodMap`](appium_types.DriverMethodMap.md)<[`ExternalDriver`](appium_types.ExternalDriver.md)<[`Constraints`](../modules/appium_types.md#constraints), `string`, [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>\>\>

#### Defined in

@appium/base-driver/lib/express/server.js:345

___

### hostname

• **hostname**: `undefined` \| `string`

#### Defined in

@appium/base-driver/lib/express/server.js:342

___

### keepAliveTimeout

• **keepAliveTimeout**: `undefined` \| `number`

#### Defined in

@appium/base-driver/lib/express/server.js:347

___

### port

• **port**: `number`

#### Defined in

@appium/base-driver/lib/express/server.js:340

___

### routeConfiguringFunction

• **routeConfiguringFunction**: `RouteConfiguringFunction`

#### Defined in

@appium/base-driver/lib/express/server.js:339

___

### serverUpdaters

• **serverUpdaters**: `undefined` \| [`UpdateServerCallback`](../modules/appium_types.md#updateservercallback)[]

#### Defined in

@appium/base-driver/lib/express/server.js:346
