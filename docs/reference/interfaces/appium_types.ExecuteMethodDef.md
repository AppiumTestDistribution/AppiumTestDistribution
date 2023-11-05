# Interface: ExecuteMethodDef<Ext\>

[@appium/types](../modules/appium_types.md).ExecuteMethodDef

## Type parameters

| Name | Type |
| :------ | :------ |
| `Ext` | extends [`Plugin`](appium_types.Plugin.md) \| [`Driver`](appium_types.Driver.md) |

## Table of contents

### Properties

- [command](appium_types.ExecuteMethodDef.md#command)

## Properties

### command

• **command**: `ConditionalKeys`<`Required`<`Ext`\>, `Ext` extends [`Plugin`](appium_types.Plugin.md) ? [`PluginCommand`](../modules/appium_types.md#plugincommand) : `Ext` extends [`Driver`](appium_types.Driver.md)<[`Constraints`](../modules/appium_types.md#constraints), [`StringRecord`](../modules/appium_types.md#stringrecord), [`StringRecord`](../modules/appium_types.md#stringrecord), [`DefaultCreateSessionResult`](../modules/appium_types.md#defaultcreatesessionresult)<[`Constraints`](../modules/appium_types.md#constraints)\>, `void`, [`StringRecord`](../modules/appium_types.md#stringrecord)\> ? [`DriverCommand`](../modules/appium_types.md#drivercommand) : `never`\>

#### Defined in

@appium/types/lib/command.ts:102
