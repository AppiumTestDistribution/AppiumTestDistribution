# Interface: PluginExecuteMethodDef<T\>

[@appium/types](../modules/appium_types.md).PluginExecuteMethodDef

A definition of an execute method in a [`Plugin`](appium_types.Plugin.md).

## Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends [`Plugin`](appium_types.Plugin.md) |

## Hierarchy

- [`BaseExecuteMethodDef`](appium_types.BaseExecuteMethodDef.md)

  ↳ **`PluginExecuteMethodDef`**

## Table of contents

### Properties

- [command](appium_types.PluginExecuteMethodDef.md#command)
- [params](appium_types.PluginExecuteMethodDef.md#params)

## Properties

### command

• **command**: `ConditionalKeys`<`T`, [`PluginCommand`](../modules/appium_types.md#plugincommand)\>

#### Defined in

@appium/types/lib/command.ts:129

___

### params

• `Optional` **params**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `optional?` | readonly `string`[] |
| `required?` | readonly `string`[] |

#### Inherited from

[BaseExecuteMethodDef](appium_types.BaseExecuteMethodDef.md).[params](appium_types.BaseExecuteMethodDef.md#params)

#### Defined in

@appium/types/lib/command.ts:112
