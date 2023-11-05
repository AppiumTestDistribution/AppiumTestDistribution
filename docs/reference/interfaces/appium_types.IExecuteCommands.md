# Interface: IExecuteCommands

[@appium/types](../modules/appium_types.md).IExecuteCommands

## Hierarchy

- **`IExecuteCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Methods

- [executeMethod](appium_types.IExecuteCommands.md#executemethod)

## Methods

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

#### Defined in

@appium/types/lib/driver.ts:170
