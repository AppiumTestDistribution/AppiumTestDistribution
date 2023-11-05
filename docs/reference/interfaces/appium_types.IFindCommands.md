# Interface: IFindCommands

[@appium/types](../modules/appium_types.md).IFindCommands

## Hierarchy

- **`IFindCommands`**

  ↳ [`Driver`](appium_types.Driver.md)

## Table of contents

### Methods

- [findElOrEls](appium_types.IFindCommands.md#findelorels)
- [findElOrElsWithProcessing](appium_types.IFindCommands.md#findelorelswithprocessing)
- [findElement](appium_types.IFindCommands.md#findelement)
- [findElementFromElement](appium_types.IFindCommands.md#findelementfromelement)
- [findElementFromShadowRoot](appium_types.IFindCommands.md#findelementfromshadowroot)
- [findElements](appium_types.IFindCommands.md#findelements)
- [findElementsFromElement](appium_types.IFindCommands.md#findelementsfromelement)
- [findElementsFromShadowRoot](appium_types.IFindCommands.md#findelementsfromshadowroot)
- [getPageSource](appium_types.IFindCommands.md#getpagesource)

## Methods

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

#### Defined in

@appium/types/lib/driver.ts:295

___

### findElOrElsWithProcessing

▸ **findElOrElsWithProcessing**(`strategy`, `selector`, `mult`, `context?`): `Promise`<[`Element`](appium_types.Element.md)<`string`\>[]\>

This is a wrapper for [`findElOrEls`](appium_types.IFindCommands.md#findelorels) that validates locator strategies
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

#### Defined in

@appium/types/lib/driver.ts:278

___

### getPageSource

▸ **getPageSource**(): `Promise`<`string`\>

Get the current page/app source as HTML/XML

**`See`**

[https://w3c.github.io/webdriver/#get-page-source](https://w3c.github.io/webdriver/#get-page-source)

#### Returns

`Promise`<`string`\>

The UI hierarchy in a platform-appropriate format (e.g., HTML for a web page)

#### Defined in

@appium/types/lib/driver.ts:327
