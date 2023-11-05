# Interface: PluginStatic<P\>

[@appium/types](../modules/appium_types.md).PluginStatic

The interface describing the constructor and static properties of a Plugin.

## Type parameters

| Name | Type |
| :------ | :------ |
| `P` | extends [`Plugin`](appium_types.Plugin.md) |

## Table of contents

### Properties

- [executeMethodMap](appium_types.PluginStatic.md#executemethodmap)
- [newMethodMap](appium_types.PluginStatic.md#newmethodmap)
- [updateServer](appium_types.PluginStatic.md#updateserver)

## Properties

### executeMethodMap

• `Optional` **executeMethodMap**: [`ExecuteMethodMap`](../modules/appium_types.md#executemethodmap)<`P`\>

#### Defined in

@appium/types/lib/plugin.ts:28

___

### newMethodMap

• `Optional` **newMethodMap**: [`MethodMap`](../modules/appium_types.md#methodmap)<`P`\>

Plugins can define new methods for the Appium server to map to command names, of the same
format as used in Appium's `routes.js`, for example, this would be a valid `newMethodMap`:

**`Example`**

```ts
{
   *   '/session/:sessionId/new_method': {
   *     GET: {command: 'getNewThing'},
   *     POST: {command: 'setNewThing', payloadParams: {required: ['someParam']}}
   *   }
   * }
```

#### Defined in

@appium/types/lib/plugin.ts:27

___

### updateServer

• `Optional` **updateServer**: [`UpdateServerCallback`](../modules/appium_types.md#updateservercallback)

Allows a plugin to modify the Appium server instance.

#### Defined in

@appium/types/lib/plugin.ts:15
