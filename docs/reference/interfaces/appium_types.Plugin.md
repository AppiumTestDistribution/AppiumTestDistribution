# Interface: Plugin

[@appium/types](../modules/appium_types.md).Plugin

An instance of a "plugin" extension.

Likewise, the `prototype` of a [`Plugin` class](../modules/appium_types.md#pluginclass).

## Table of contents

### Properties

- [cliArgs](appium_types.Plugin.md#cliargs)
- [handle](appium_types.Plugin.md#handle)
- [logger](appium_types.Plugin.md#logger)
- [name](appium_types.Plugin.md#name)
- [onUnexpectedShutdown](appium_types.Plugin.md#onunexpectedshutdown)

## Properties

### cliArgs

• **cliArgs**: `Record`<`string`, `any`\>

CLI args for this plugin (if any are accepted and provided).

#### Defined in

@appium/types/lib/plugin.ts:75

___

### handle

• `Optional` **handle**: [`PluginCommand`](../modules/appium_types.md#plugincommand)<[`ExternalDriver`](appium_types.ExternalDriver.md)<[`Constraints`](../modules/appium_types.md#constraints), `string`, [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>, [cmdName: string, ...args: any[]], `void`, `unknown`\>

Handle an Appium command, optionally running and using or throwing away the value of the
original Appium behavior (or the behavior of the next plugin in a plugin chain).

#### Defined in

@appium/types/lib/plugin.ts:84

___

### logger

• **logger**: [`AppiumLogger`](appium_types.AppiumLogger.md)

A logger with prefix identifying the plugin

#### Defined in

@appium/types/lib/plugin.ts:71

___

### name

• **name**: `string`

Name of the plugin.  Derived from the metadata.

#### Defined in

@appium/types/lib/plugin.ts:67

___

### onUnexpectedShutdown

• `Optional` **onUnexpectedShutdown**: (`driver`: [`ExternalDriver`](appium_types.ExternalDriver.md)<[`Constraints`](../modules/appium_types.md#constraints), `string`, [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\>, `cause`: `string` \| `Error`) => `Promise`<`void`\>

#### Type declaration

▸ (`driver`, `cause`): `Promise`<`void`\>

Listener for unexpected server shutdown, which allows a plugin to do cleanup or take custom actions.

##### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | [`ExternalDriver`](appium_types.ExternalDriver.md)<[`Constraints`](../modules/appium_types.md#constraints), `string`, [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> |
| `cause` | `string` \| `Error` |

##### Returns

`Promise`<`void`\>

#### Defined in

@appium/types/lib/plugin.ts:79
