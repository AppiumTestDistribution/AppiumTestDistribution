# Interface: ITimeoutCommands

[@appium/types](../modules/appium_types.md).ITimeoutCommands

## Hierarchy

- **`ITimeoutCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Methods

- [getTimeouts](appium_types.ITimeoutCommands.md#gettimeouts)
- [implicitWait](appium_types.ITimeoutCommands.md#implicitwait)
- [implicitWaitForCondition](appium_types.ITimeoutCommands.md#implicitwaitforcondition)
- [implicitWaitMJSONWP](appium_types.ITimeoutCommands.md#implicitwaitmjsonwp)
- [implicitWaitW3C](appium_types.ITimeoutCommands.md#implicitwaitw3c)
- [newCommandTimeout](appium_types.ITimeoutCommands.md#newcommandtimeout)
- [pageLoadTimeoutMJSONWP](appium_types.ITimeoutCommands.md#pageloadtimeoutmjsonwp)
- [pageLoadTimeoutW3C](appium_types.ITimeoutCommands.md#pageloadtimeoutw3c)
- [parseTimeoutArgument](appium_types.ITimeoutCommands.md#parsetimeoutargument)
- [scriptTimeoutMJSONWP](appium_types.ITimeoutCommands.md#scripttimeoutmjsonwp)
- [scriptTimeoutW3C](appium_types.ITimeoutCommands.md#scripttimeoutw3c)
- [setImplicitWait](appium_types.ITimeoutCommands.md#setimplicitwait)
- [setNewCommandTimeout](appium_types.ITimeoutCommands.md#setnewcommandtimeout)
- [timeouts](appium_types.ITimeoutCommands.md#timeouts)

## Methods

### getTimeouts

▸ **getTimeouts**(): `Promise`<`Record`<`string`, `number`\>\>

Get the current timeouts

**`See`**

[https://w3c.github.io/webdriver/#get-timeouts](https://w3c.github.io/webdriver/#get-timeouts)

#### Returns

`Promise`<`Record`<`string`, `number`\>\>

A map of timeout names to ms values

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

#### Defined in

@appium/types/lib/driver.ts:84

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

#### Defined in

@appium/types/lib/driver.ts:129

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

#### Defined in

@appium/types/lib/driver.ts:138

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

#### Defined in

@appium/types/lib/driver.ts:114

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

#### Defined in

@appium/types/lib/driver.ts:43

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

#### Defined in

@appium/types/lib/driver.ts:30
