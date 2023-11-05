# Interface: StandardCapabilities

[@appium/types](../modules/appium_types.md).StandardCapabilities

## Table of contents

### Properties

- [acceptInsecureCerts](appium_types.StandardCapabilities.md#acceptinsecurecerts)
- [browserName](appium_types.StandardCapabilities.md#browsername)
- [browserVersion](appium_types.StandardCapabilities.md#browserversion)
- [pageLoadStrategy](appium_types.StandardCapabilities.md#pageloadstrategy)
- [platformName](appium_types.StandardCapabilities.md#platformname)
- [proxy](appium_types.StandardCapabilities.md#proxy)
- [setWindowRect](appium_types.StandardCapabilities.md#setwindowrect)
- [strictFileInteractability](appium_types.StandardCapabilities.md#strictfileinteractability)
- [timeouts](appium_types.StandardCapabilities.md#timeouts)
- [unhandledPromptBehavior](appium_types.StandardCapabilities.md#unhandledpromptbehavior)
- [webSocketUrl](appium_types.StandardCapabilities.md#websocketurl)

## Properties

### acceptInsecureCerts

• `Optional` **acceptInsecureCerts**: `boolean`

Indicates whether untrusted and self-signed TLS certificates are implicitly trusted on navigation for the duration of the session.

#### Defined in

@appium/types/lib/standard-caps.ts:36

___

### browserName

• `Optional` **browserName**: `string`

Identifies the user agent.

#### Defined in

@appium/types/lib/standard-caps.ts:24

___

### browserVersion

• `Optional` **browserVersion**: `string`

Identifies the version of the user agent.

#### Defined in

@appium/types/lib/standard-caps.ts:28

___

### pageLoadStrategy

• `Optional` **pageLoadStrategy**: `PageLoadingStrategy`

Defines the current session’s page load strategy.

#### Defined in

@appium/types/lib/standard-caps.ts:40

___

### platformName

• `Optional` **platformName**: `string`

Identifies the operating system of the endpoint node.

#### Defined in

@appium/types/lib/standard-caps.ts:32

___

### proxy

• `Optional` **proxy**: `ProxyObject`

Defines the current session’s proxy configuration.

#### Defined in

@appium/types/lib/standard-caps.ts:44

___

### setWindowRect

• `Optional` **setWindowRect**: `boolean`

Indicates whether the remote end supports all of the resizing and repositioning commands.

#### Defined in

@appium/types/lib/standard-caps.ts:48

___

### strictFileInteractability

• `Optional` **strictFileInteractability**: `boolean`

Defines the current session’s strict file interactability.

#### Defined in

@appium/types/lib/standard-caps.ts:56

___

### timeouts

• `Optional` **timeouts**: `Timeouts`

Describes the timeouts imposed on certain session operations.

#### Defined in

@appium/types/lib/standard-caps.ts:52

___

### unhandledPromptBehavior

• `Optional` **unhandledPromptBehavior**: `string`

Describes the current session’s user prompt handler. Defaults to the dismiss and notify state.

#### Defined in

@appium/types/lib/standard-caps.ts:60

___

### webSocketUrl

• `Optional` **webSocketUrl**: `boolean`

WebDriver clients opt in to a bidirectional connection by requesting a capability with the name "webSocketUrl" and value true.

#### Defined in

@appium/types/lib/standard-caps.ts:64
