# Interface: IEventCommands

[@appium/types](../modules/appium_types.md).IEventCommands

## Hierarchy

- **`IEventCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Methods

- [getLogEvents](appium_types.IEventCommands.md#getlogevents)
- [logCustomEvent](appium_types.IEventCommands.md#logcustomevent)

## Methods

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

#### Defined in

@appium/types/lib/driver.ts:157

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

#### Defined in

@appium/types/lib/driver.ts:148
