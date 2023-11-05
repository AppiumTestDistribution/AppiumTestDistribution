# Module: @appium/base-driver

## Table of contents

### References

- [default](appium_base_driver.md#default)
- [errorFromMJSONWPStatusCode](appium_base_driver.md#errorfrommjsonwpstatuscode)

### Classes

- [BaseDriver](../classes/appium_base_driver.BaseDriver.md)
- [DeviceSettings](../classes/appium_base_driver.DeviceSettings.md)
- [DriverCore](../classes/appium_base_driver.DriverCore.md)
- [JWProxy](../classes/appium_base_driver.JWProxy.md)

### Interfaces

- [ServerOpts](../interfaces/appium_base_driver.ServerOpts.md)

### Variables

- [ALL\_COMMANDS](appium_base_driver.md#all_commands)
- [CREATE\_SESSION\_COMMAND](appium_base_driver.md#create_session_command)
- [DEFAULT\_BASE\_PATH](appium_base_driver.md#default_base_path)
- [DEFAULT\_WS\_PATHNAME\_PREFIX](appium_base_driver.md#default_ws_pathname_prefix)
- [DELETE\_SESSION\_COMMAND](appium_base_driver.md#delete_session_command)
- [GET\_STATUS\_COMMAND](appium_base_driver.md#get_status_command)
- [METHOD\_MAP](appium_base_driver.md#method_map)
- [NO\_SESSION\_ID\_COMMANDS](appium_base_driver.md#no_session_id_commands)
- [PREFIXED\_APPIUM\_OPTS\_CAP](appium_base_driver.md#prefixed_appium_opts_cap)
- [PROTOCOLS](appium_base_driver.md#protocols)
- [STANDARD\_CAPS](appium_base_driver.md#standard_caps)
- [STATIC\_DIR](appium_base_driver.md#static_dir)
- [W3C\_ELEMENT\_KEY](appium_base_driver.md#w3c_element_key)
- [errors](appium_base_driver.md#errors)
- [statusCodes](appium_base_driver.md#statuscodes)

### Functions

- [checkParams](appium_base_driver.md#checkparams)
- [determineProtocol](appium_base_driver.md#determineprotocol)
- [errorFromCode](appium_base_driver.md#errorfromcode)
- [errorFromW3CJsonCode](appium_base_driver.md#errorfromw3cjsoncode)
- [getSummaryByCode](appium_base_driver.md#getsummarybycode)
- [isErrorType](appium_base_driver.md#iserrortype)
- [isSessionCommand](appium_base_driver.md#issessioncommand)
- [isStandardCap](appium_base_driver.md#isstandardcap)
- [makeArgs](appium_base_driver.md#makeargs)
- [normalizeBasePath](appium_base_driver.md#normalizebasepath)
- [processCapabilities](appium_base_driver.md#processcapabilities)
- [promoteAppiumOptions](appium_base_driver.md#promoteappiumoptions)
- [promoteAppiumOptionsForObject](appium_base_driver.md#promoteappiumoptionsforobject)
- [routeConfiguringFunction](appium_base_driver.md#routeconfiguringfunction)
- [routeToCommandName](appium_base_driver.md#routetocommandname)
- [server](appium_base_driver.md#server)
- [validateCaps](appium_base_driver.md#validatecaps)
- [validateExecuteMethodParams](appium_base_driver.md#validateexecutemethodparams)

## References

### default

Renames and re-exports [BaseDriver](../classes/appium_base_driver.BaseDriver.md)

___

### errorFromMJSONWPStatusCode

Renames and re-exports [errorFromCode](appium_base_driver.md#errorfromcode)

## Variables

### ALL\_COMMANDS

• **ALL\_COMMANDS**: `any`[] = `[]`

#### Defined in

@appium/base-driver/lib/protocol/routes.js:931

___

### CREATE\_SESSION\_COMMAND

• `Const` **CREATE\_SESSION\_COMMAND**: ``"createSession"``

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:18

___

### DEFAULT\_BASE\_PATH

• `Const` **DEFAULT\_BASE\_PATH**: ``""``

#### Defined in

@appium/base-driver/lib/constants.ts:19

___

### DEFAULT\_WS\_PATHNAME\_PREFIX

• `Const` **DEFAULT\_WS\_PATHNAME\_PREFIX**: ``"/ws"``

#### Defined in

@appium/base-driver/lib/express/websocket.js:7

___

### DELETE\_SESSION\_COMMAND

• `Const` **DELETE\_SESSION\_COMMAND**: ``"deleteSession"``

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:19

___

### GET\_STATUS\_COMMAND

• `Const` **GET\_STATUS\_COMMAND**: ``"getStatus"``

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:20

___

### METHOD\_MAP

• `Const` **METHOD\_MAP**: `Object`

define the routes, mapping of HTTP methods to particular driver commands, and
any parameters that are expected in a request parameters can be `required` or
`optional`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `/session` | { `POST`: { `command`: ``"createSession"`` = 'createSession'; `payloadParams`: { `optional`: readonly [``"desiredCapabilities"``, ``"requiredCapabilities"``, ``"capabilities"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"desiredCapabilities\" or \"capabilities\" object"``  }  }  } |
| `/session.POST` | { `command`: ``"createSession"`` = 'createSession'; `payloadParams`: { `optional`: readonly [``"desiredCapabilities"``, ``"requiredCapabilities"``, ``"capabilities"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"desiredCapabilities\" or \"capabilities\" object"``  }  } |
| `/session.POST.command` | ``"createSession"`` |
| `/session.POST.payloadParams` | { `optional`: readonly [``"desiredCapabilities"``, ``"requiredCapabilities"``, ``"capabilities"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"desiredCapabilities\" or \"capabilities\" object"``  } |
| `/session.POST.payloadParams.optional` | readonly [``"desiredCapabilities"``, ``"requiredCapabilities"``, ``"capabilities"``] |
| `/session.POST.payloadParams.validate` | (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"desiredCapabilities\" or \"capabilities\" object"`` |
| `/session/:sessionId` | { `DELETE`: { `command`: ``"deleteSession"`` = 'deleteSession' } ; `GET`: { `command`: ``"getSession"`` = 'getSession' }  } |
| `/session/:sessionId.DELETE` | { `command`: ``"deleteSession"`` = 'deleteSession' } |
| `/session/:sessionId.DELETE.command` | ``"deleteSession"`` |
| `/session/:sessionId.GET` | { `command`: ``"getSession"`` = 'getSession' } |
| `/session/:sessionId.GET.command` | ``"getSession"`` |
| `/session/:sessionId/:vendor/cdp/execute` | { `POST`: { `command`: ``"executeCdp"`` = 'executeCdp'; `payloadParams`: { `required`: readonly [``"cmd"``, ``"params"``]  }  }  } |
| `/session/:sessionId/:vendor/cdp/execute.POST` | { `command`: ``"executeCdp"`` = 'executeCdp'; `payloadParams`: { `required`: readonly [``"cmd"``, ``"params"``]  }  } |
| `/session/:sessionId/:vendor/cdp/execute.POST.command` | ``"executeCdp"`` |
| `/session/:sessionId/:vendor/cdp/execute.POST.payloadParams` | { `required`: readonly [``"cmd"``, ``"params"``]  } |
| `/session/:sessionId/:vendor/cdp/execute.POST.payloadParams.required` | readonly [``"cmd"``, ``"params"``] |
| `/session/:sessionId/accept_alert` | { `POST`: { `command`: ``"postAcceptAlert"`` = 'postAcceptAlert' }  } |
| `/session/:sessionId/accept_alert.POST` | { `command`: ``"postAcceptAlert"`` = 'postAcceptAlert' } |
| `/session/:sessionId/accept_alert.POST.command` | ``"postAcceptAlert"`` |
| `/session/:sessionId/actions` | { `DELETE`: { `command`: ``"releaseActions"`` = 'releaseActions' } ; `POST`: { `command`: ``"performActions"`` = 'performActions'; `payloadParams`: { `required`: readonly [``"actions"``]  }  }  } |
| `/session/:sessionId/actions.DELETE` | { `command`: ``"releaseActions"`` = 'releaseActions' } |
| `/session/:sessionId/actions.DELETE.command` | ``"releaseActions"`` |
| `/session/:sessionId/actions.POST` | { `command`: ``"performActions"`` = 'performActions'; `payloadParams`: { `required`: readonly [``"actions"``]  }  } |
| `/session/:sessionId/actions.POST.command` | ``"performActions"`` |
| `/session/:sessionId/actions.POST.payloadParams` | { `required`: readonly [``"actions"``]  } |
| `/session/:sessionId/actions.POST.payloadParams.required` | readonly [``"actions"``] |
| `/session/:sessionId/alert/accept` | { `POST`: { `command`: ``"postAcceptAlert"`` = 'postAcceptAlert' }  } |
| `/session/:sessionId/alert/accept.POST` | { `command`: ``"postAcceptAlert"`` = 'postAcceptAlert' } |
| `/session/:sessionId/alert/accept.POST.command` | ``"postAcceptAlert"`` |
| `/session/:sessionId/alert/dismiss` | { `POST`: { `command`: ``"postDismissAlert"`` = 'postDismissAlert' }  } |
| `/session/:sessionId/alert/dismiss.POST` | { `command`: ``"postDismissAlert"`` = 'postDismissAlert' } |
| `/session/:sessionId/alert/dismiss.POST.command` | ``"postDismissAlert"`` |
| `/session/:sessionId/alert/text` | { `GET`: { `command`: ``"getAlertText"`` = 'getAlertText' } ; `POST`: { `command`: ``"setAlertText"`` = 'setAlertText'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } = SET\_ALERT\_TEXT\_PAYLOAD\_PARAMS }  } |
| `/session/:sessionId/alert/text.GET` | { `command`: ``"getAlertText"`` = 'getAlertText' } |
| `/session/:sessionId/alert/text.GET.command` | ``"getAlertText"`` |
| `/session/:sessionId/alert/text.POST` | { `command`: ``"setAlertText"`` = 'setAlertText'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } = SET\_ALERT\_TEXT\_PAYLOAD\_PARAMS } |
| `/session/:sessionId/alert/text.POST.command` | ``"setAlertText"`` |
| `/session/:sessionId/alert/text.POST.payloadParams` | { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } |
| `/session/:sessionId/alert/text.POST.payloadParams.makeArgs` | (`jsonObj`: `any`) => `any`[] |
| `/session/:sessionId/alert/text.POST.payloadParams.optional` | `string`[] |
| `/session/:sessionId/alert/text.POST.payloadParams.validate` | (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"`` |
| `/session/:sessionId/alert_text` | { `GET`: { `command`: ``"getAlertText"`` = 'getAlertText' } ; `POST`: { `command`: ``"setAlertText"`` = 'setAlertText'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } = SET\_ALERT\_TEXT\_PAYLOAD\_PARAMS }  } |
| `/session/:sessionId/alert_text.GET` | { `command`: ``"getAlertText"`` = 'getAlertText' } |
| `/session/:sessionId/alert_text.GET.command` | ``"getAlertText"`` |
| `/session/:sessionId/alert_text.POST` | { `command`: ``"setAlertText"`` = 'setAlertText'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } = SET\_ALERT\_TEXT\_PAYLOAD\_PARAMS } |
| `/session/:sessionId/alert_text.POST.command` | ``"setAlertText"`` |
| `/session/:sessionId/alert_text.POST.payloadParams` | { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: `string`[] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"``  } |
| `/session/:sessionId/alert_text.POST.payloadParams.makeArgs` | (`jsonObj`: `any`) => `any`[] |
| `/session/:sessionId/alert_text.POST.payloadParams.optional` | `string`[] |
| `/session/:sessionId/alert_text.POST.payloadParams.validate` | (`jsonObj`: `any`) => ``false`` \| ``"either \"text\" or \"value\" must be set"`` |
| `/session/:sessionId/appium/app/background` | { `POST`: { `command`: ``"background"`` = 'background'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"seconds"``]  }  }  } |
| `/session/:sessionId/appium/app/background.POST` | { `command`: ``"background"`` = 'background'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"seconds"``]  }  } |
| `/session/:sessionId/appium/app/background.POST.command` | ``"background"`` |
| `/session/:sessionId/appium/app/background.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/background.POST.payloadParams` | { `required`: readonly [``"seconds"``]  } |
| `/session/:sessionId/appium/app/background.POST.payloadParams.required` | readonly [``"seconds"``] |
| `/session/:sessionId/appium/app/close` | { `POST`: { `command`: ``"closeApp"`` = 'closeApp'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/app/close.POST` | { `command`: ``"closeApp"`` = 'closeApp'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/app/close.POST.command` | ``"closeApp"`` |
| `/session/:sessionId/appium/app/close.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/end_test_coverage` | { `POST`: { `command`: ``"endCoverage"`` = 'endCoverage'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"intent"``, ``"path"``]  }  }  } |
| `/session/:sessionId/appium/app/end_test_coverage.POST` | { `command`: ``"endCoverage"`` = 'endCoverage'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"intent"``, ``"path"``]  }  } |
| `/session/:sessionId/appium/app/end_test_coverage.POST.command` | ``"endCoverage"`` |
| `/session/:sessionId/appium/app/end_test_coverage.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/end_test_coverage.POST.payloadParams` | { `required`: readonly [``"intent"``, ``"path"``]  } |
| `/session/:sessionId/appium/app/end_test_coverage.POST.payloadParams.required` | readonly [``"intent"``, ``"path"``] |
| `/session/:sessionId/appium/app/launch` | { `POST`: { `command`: ``"launchApp"`` = 'launchApp'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/app/launch.POST` | { `command`: ``"launchApp"`` = 'launchApp'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/app/launch.POST.command` | ``"launchApp"`` |
| `/session/:sessionId/appium/app/launch.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/reset` | { `POST`: { `command`: ``"reset"`` = 'reset'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/app/reset.POST` | { `command`: ``"reset"`` = 'reset'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/app/reset.POST.command` | ``"reset"`` |
| `/session/:sessionId/appium/app/reset.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/strings` | { `POST`: { `command`: ``"getStrings"`` = 'getStrings'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"language"``, ``"stringFile"``]  }  }  } |
| `/session/:sessionId/appium/app/strings.POST` | { `command`: ``"getStrings"`` = 'getStrings'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"language"``, ``"stringFile"``]  }  } |
| `/session/:sessionId/appium/app/strings.POST.command` | ``"getStrings"`` |
| `/session/:sessionId/appium/app/strings.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/app/strings.POST.payloadParams` | { `optional`: readonly [``"language"``, ``"stringFile"``]  } |
| `/session/:sessionId/appium/app/strings.POST.payloadParams.optional` | readonly [``"language"``, ``"stringFile"``] |
| `/session/:sessionId/appium/device/activate_app` | { `POST`: { `command`: ``"activateApp"`` = 'activateApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  }  } |
| `/session/:sessionId/appium/device/activate_app.POST` | { `command`: ``"activateApp"`` = 'activateApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/activate_app.POST.command` | ``"activateApp"`` |
| `/session/:sessionId/appium/device/activate_app.POST.payloadParams` | { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/activate_app.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/appium/device/activate_app.POST.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/app_installed` | { `POST`: { `command`: ``"isAppInstalled"`` = 'isAppInstalled'; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  }  } |
| `/session/:sessionId/appium/device/app_installed.POST` | { `command`: ``"isAppInstalled"`` = 'isAppInstalled'; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/app_installed.POST.command` | ``"isAppInstalled"`` |
| `/session/:sessionId/appium/device/app_installed.POST.payloadParams` | { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/app_installed.POST.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/app_state` | { `GET`: { `command`: ``"queryAppState"`` = 'queryAppState'; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } ; `POST`: { `command`: ``"queryAppState"`` = 'queryAppState'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  }  } |
| `/session/:sessionId/appium/device/app_state.GET` | { `command`: ``"queryAppState"`` = 'queryAppState'; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/app_state.GET.command` | ``"queryAppState"`` |
| `/session/:sessionId/appium/device/app_state.GET.payloadParams` | { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/app_state.GET.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/app_state.POST` | { `command`: ``"queryAppState"`` = 'queryAppState'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/app_state.POST.command` | ``"queryAppState"`` |
| `/session/:sessionId/appium/device/app_state.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/app_state.POST.payloadParams` | { `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/app_state.POST.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/current_activity` | { `GET`: { `command`: ``"getCurrentActivity"`` = 'getCurrentActivity'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/current_activity.GET` | { `command`: ``"getCurrentActivity"`` = 'getCurrentActivity'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/current_activity.GET.command` | ``"getCurrentActivity"`` |
| `/session/:sessionId/appium/device/current_activity.GET.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/current_package` | { `GET`: { `command`: ``"getCurrentPackage"`` = 'getCurrentPackage'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/current_package.GET` | { `command`: ``"getCurrentPackage"`` = 'getCurrentPackage'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/current_package.GET.command` | ``"getCurrentPackage"`` |
| `/session/:sessionId/appium/device/current_package.GET.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/display_density` | { `GET`: { `command`: ``"getDisplayDensity"`` = 'getDisplayDensity'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/display_density.GET` | { `command`: ``"getDisplayDensity"`` = 'getDisplayDensity'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/display_density.GET.command` | ``"getDisplayDensity"`` |
| `/session/:sessionId/appium/device/display_density.GET.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/finger_print` | { `POST`: { `command`: ``"fingerprint"`` = 'fingerprint'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"fingerprintId"``]  }  }  } |
| `/session/:sessionId/appium/device/finger_print.POST` | { `command`: ``"fingerprint"`` = 'fingerprint'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"fingerprintId"``]  }  } |
| `/session/:sessionId/appium/device/finger_print.POST.command` | ``"fingerprint"`` |
| `/session/:sessionId/appium/device/finger_print.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/finger_print.POST.payloadParams` | { `required`: readonly [``"fingerprintId"``]  } |
| `/session/:sessionId/appium/device/finger_print.POST.payloadParams.required` | readonly [``"fingerprintId"``] |
| `/session/:sessionId/appium/device/get_clipboard` | { `POST`: { `command`: ``"getClipboard"`` = 'getClipboard'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"contentType"``]  }  }  } |
| `/session/:sessionId/appium/device/get_clipboard.POST` | { `command`: ``"getClipboard"`` = 'getClipboard'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"contentType"``]  }  } |
| `/session/:sessionId/appium/device/get_clipboard.POST.command` | ``"getClipboard"`` |
| `/session/:sessionId/appium/device/get_clipboard.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/get_clipboard.POST.payloadParams` | { `optional`: readonly [``"contentType"``]  } |
| `/session/:sessionId/appium/device/get_clipboard.POST.payloadParams.optional` | readonly [``"contentType"``] |
| `/session/:sessionId/appium/device/gsm_call` | { `POST`: { `command`: ``"gsmCall"`` = 'gsmCall'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"phoneNumber"``, ``"action"``]  }  }  } |
| `/session/:sessionId/appium/device/gsm_call.POST` | { `command`: ``"gsmCall"`` = 'gsmCall'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"phoneNumber"``, ``"action"``]  }  } |
| `/session/:sessionId/appium/device/gsm_call.POST.command` | ``"gsmCall"`` |
| `/session/:sessionId/appium/device/gsm_call.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/gsm_call.POST.payloadParams` | { `required`: readonly [``"phoneNumber"``, ``"action"``]  } |
| `/session/:sessionId/appium/device/gsm_call.POST.payloadParams.required` | readonly [``"phoneNumber"``, ``"action"``] |
| `/session/:sessionId/appium/device/gsm_signal` | { `POST`: { `command`: ``"gsmSignal"`` = 'gsmSignal'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"signalStrength"``]  }  }  } |
| `/session/:sessionId/appium/device/gsm_signal.POST` | { `command`: ``"gsmSignal"`` = 'gsmSignal'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"signalStrength"``]  }  } |
| `/session/:sessionId/appium/device/gsm_signal.POST.command` | ``"gsmSignal"`` |
| `/session/:sessionId/appium/device/gsm_signal.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/gsm_signal.POST.payloadParams` | { `required`: readonly [``"signalStrength"``]  } |
| `/session/:sessionId/appium/device/gsm_signal.POST.payloadParams.required` | readonly [``"signalStrength"``] |
| `/session/:sessionId/appium/device/gsm_voice` | { `POST`: { `command`: ``"gsmVoice"`` = 'gsmVoice'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"state"``]  }  }  } |
| `/session/:sessionId/appium/device/gsm_voice.POST` | { `command`: ``"gsmVoice"`` = 'gsmVoice'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"state"``]  }  } |
| `/session/:sessionId/appium/device/gsm_voice.POST.command` | ``"gsmVoice"`` |
| `/session/:sessionId/appium/device/gsm_voice.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/gsm_voice.POST.payloadParams` | { `required`: readonly [``"state"``]  } |
| `/session/:sessionId/appium/device/gsm_voice.POST.payloadParams.required` | readonly [``"state"``] |
| `/session/:sessionId/appium/device/hide_keyboard` | { `POST`: { `command`: ``"hideKeyboard"`` = 'hideKeyboard'; `payloadParams`: { `optional`: readonly [``"strategy"``, ``"key"``, ``"keyCode"``, ``"keyName"``]  }  }  } |
| `/session/:sessionId/appium/device/hide_keyboard.POST` | { `command`: ``"hideKeyboard"`` = 'hideKeyboard'; `payloadParams`: { `optional`: readonly [``"strategy"``, ``"key"``, ``"keyCode"``, ``"keyName"``]  }  } |
| `/session/:sessionId/appium/device/hide_keyboard.POST.command` | ``"hideKeyboard"`` |
| `/session/:sessionId/appium/device/hide_keyboard.POST.payloadParams` | { `optional`: readonly [``"strategy"``, ``"key"``, ``"keyCode"``, ``"keyName"``]  } |
| `/session/:sessionId/appium/device/hide_keyboard.POST.payloadParams.optional` | readonly [``"strategy"``, ``"key"``, ``"keyCode"``, ``"keyName"``] |
| `/session/:sessionId/appium/device/install_app` | { `POST`: { `command`: ``"installApp"`` = 'installApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [``"appPath"``]  }  }  } |
| `/session/:sessionId/appium/device/install_app.POST` | { `command`: ``"installApp"`` = 'installApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [``"appPath"``]  }  } |
| `/session/:sessionId/appium/device/install_app.POST.command` | ``"installApp"`` |
| `/session/:sessionId/appium/device/install_app.POST.payloadParams` | { `optional`: readonly [``"options"``] ; `required`: readonly [``"appPath"``]  } |
| `/session/:sessionId/appium/device/install_app.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/appium/device/install_app.POST.payloadParams.required` | readonly [``"appPath"``] |
| `/session/:sessionId/appium/device/is_keyboard_shown` | { `GET`: { `command`: ``"isKeyboardShown"`` = 'isKeyboardShown' }  } |
| `/session/:sessionId/appium/device/is_keyboard_shown.GET` | { `command`: ``"isKeyboardShown"`` = 'isKeyboardShown' } |
| `/session/:sessionId/appium/device/is_keyboard_shown.GET.command` | ``"isKeyboardShown"`` |
| `/session/:sessionId/appium/device/is_locked` | { `POST`: { `command`: ``"isLocked"`` = 'isLocked'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/is_locked.POST` | { `command`: ``"isLocked"`` = 'isLocked'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/is_locked.POST.command` | ``"isLocked"`` |
| `/session/:sessionId/appium/device/is_locked.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/keyevent` | { `POST`: { `command`: ``"keyevent"`` = 'keyevent'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``] ; `required`: readonly [``"keycode"``]  }  }  } |
| `/session/:sessionId/appium/device/keyevent.POST` | { `command`: ``"keyevent"`` = 'keyevent'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``] ; `required`: readonly [``"keycode"``]  }  } |
| `/session/:sessionId/appium/device/keyevent.POST.command` | ``"keyevent"`` |
| `/session/:sessionId/appium/device/keyevent.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/keyevent.POST.payloadParams` | { `optional`: readonly [``"metastate"``] ; `required`: readonly [``"keycode"``]  } |
| `/session/:sessionId/appium/device/keyevent.POST.payloadParams.optional` | readonly [``"metastate"``] |
| `/session/:sessionId/appium/device/keyevent.POST.payloadParams.required` | readonly [``"keycode"``] |
| `/session/:sessionId/appium/device/lock` | { `POST`: { `command`: ``"lock"`` = 'lock'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"seconds"``]  }  }  } |
| `/session/:sessionId/appium/device/lock.POST` | { `command`: ``"lock"`` = 'lock'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"seconds"``]  }  } |
| `/session/:sessionId/appium/device/lock.POST.command` | ``"lock"`` |
| `/session/:sessionId/appium/device/lock.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/lock.POST.payloadParams` | { `optional`: readonly [``"seconds"``]  } |
| `/session/:sessionId/appium/device/lock.POST.payloadParams.optional` | readonly [``"seconds"``] |
| `/session/:sessionId/appium/device/long_press_keycode` | { `POST`: { `command`: ``"longPressKeyCode"`` = 'longPressKeyCode'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  }  }  } |
| `/session/:sessionId/appium/device/long_press_keycode.POST` | { `command`: ``"longPressKeyCode"`` = 'longPressKeyCode'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  }  } |
| `/session/:sessionId/appium/device/long_press_keycode.POST.command` | ``"longPressKeyCode"`` |
| `/session/:sessionId/appium/device/long_press_keycode.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/long_press_keycode.POST.payloadParams` | { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  } |
| `/session/:sessionId/appium/device/long_press_keycode.POST.payloadParams.optional` | readonly [``"metastate"``, ``"flags"``] |
| `/session/:sessionId/appium/device/long_press_keycode.POST.payloadParams.required` | readonly [``"keycode"``] |
| `/session/:sessionId/appium/device/network_speed` | { `POST`: { `command`: ``"networkSpeed"`` = 'networkSpeed'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"netspeed"``]  }  }  } |
| `/session/:sessionId/appium/device/network_speed.POST` | { `command`: ``"networkSpeed"`` = 'networkSpeed'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"netspeed"``]  }  } |
| `/session/:sessionId/appium/device/network_speed.POST.command` | ``"networkSpeed"`` |
| `/session/:sessionId/appium/device/network_speed.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/network_speed.POST.payloadParams` | { `required`: readonly [``"netspeed"``]  } |
| `/session/:sessionId/appium/device/network_speed.POST.payloadParams.required` | readonly [``"netspeed"``] |
| `/session/:sessionId/appium/device/open_notifications` | { `POST`: { `command`: ``"openNotifications"`` = 'openNotifications'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/open_notifications.POST` | { `command`: ``"openNotifications"`` = 'openNotifications'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/open_notifications.POST.command` | ``"openNotifications"`` |
| `/session/:sessionId/appium/device/open_notifications.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/power_ac` | { `POST`: { `command`: ``"powerAC"`` = 'powerAC'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"state"``]  }  }  } |
| `/session/:sessionId/appium/device/power_ac.POST` | { `command`: ``"powerAC"`` = 'powerAC'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"state"``]  }  } |
| `/session/:sessionId/appium/device/power_ac.POST.command` | ``"powerAC"`` |
| `/session/:sessionId/appium/device/power_ac.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/power_ac.POST.payloadParams` | { `required`: readonly [``"state"``]  } |
| `/session/:sessionId/appium/device/power_ac.POST.payloadParams.required` | readonly [``"state"``] |
| `/session/:sessionId/appium/device/power_capacity` | { `POST`: { `command`: ``"powerCapacity"`` = 'powerCapacity'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"percent"``]  }  }  } |
| `/session/:sessionId/appium/device/power_capacity.POST` | { `command`: ``"powerCapacity"`` = 'powerCapacity'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"percent"``]  }  } |
| `/session/:sessionId/appium/device/power_capacity.POST.command` | ``"powerCapacity"`` |
| `/session/:sessionId/appium/device/power_capacity.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/power_capacity.POST.payloadParams` | { `required`: readonly [``"percent"``]  } |
| `/session/:sessionId/appium/device/power_capacity.POST.payloadParams.required` | readonly [``"percent"``] |
| `/session/:sessionId/appium/device/press_keycode` | { `POST`: { `command`: ``"pressKeyCode"`` = 'pressKeyCode'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  }  }  } |
| `/session/:sessionId/appium/device/press_keycode.POST` | { `command`: ``"pressKeyCode"`` = 'pressKeyCode'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  }  } |
| `/session/:sessionId/appium/device/press_keycode.POST.command` | ``"pressKeyCode"`` |
| `/session/:sessionId/appium/device/press_keycode.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/press_keycode.POST.payloadParams` | { `optional`: readonly [``"metastate"``, ``"flags"``] ; `required`: readonly [``"keycode"``]  } |
| `/session/:sessionId/appium/device/press_keycode.POST.payloadParams.optional` | readonly [``"metastate"``, ``"flags"``] |
| `/session/:sessionId/appium/device/press_keycode.POST.payloadParams.required` | readonly [``"keycode"``] |
| `/session/:sessionId/appium/device/pull_file` | { `POST`: { `command`: ``"pullFile"`` = 'pullFile'; `payloadParams`: { `required`: readonly [``"path"``]  }  }  } |
| `/session/:sessionId/appium/device/pull_file.POST` | { `command`: ``"pullFile"`` = 'pullFile'; `payloadParams`: { `required`: readonly [``"path"``]  }  } |
| `/session/:sessionId/appium/device/pull_file.POST.command` | ``"pullFile"`` |
| `/session/:sessionId/appium/device/pull_file.POST.payloadParams` | { `required`: readonly [``"path"``]  } |
| `/session/:sessionId/appium/device/pull_file.POST.payloadParams.required` | readonly [``"path"``] |
| `/session/:sessionId/appium/device/pull_folder` | { `POST`: { `command`: ``"pullFolder"`` = 'pullFolder'; `payloadParams`: { `required`: readonly [``"path"``]  }  }  } |
| `/session/:sessionId/appium/device/pull_folder.POST` | { `command`: ``"pullFolder"`` = 'pullFolder'; `payloadParams`: { `required`: readonly [``"path"``]  }  } |
| `/session/:sessionId/appium/device/pull_folder.POST.command` | ``"pullFolder"`` |
| `/session/:sessionId/appium/device/pull_folder.POST.payloadParams` | { `required`: readonly [``"path"``]  } |
| `/session/:sessionId/appium/device/pull_folder.POST.payloadParams.required` | readonly [``"path"``] |
| `/session/:sessionId/appium/device/push_file` | { `POST`: { `command`: ``"pushFile"`` = 'pushFile'; `payloadParams`: { `required`: readonly [``"path"``, ``"data"``]  }  }  } |
| `/session/:sessionId/appium/device/push_file.POST` | { `command`: ``"pushFile"`` = 'pushFile'; `payloadParams`: { `required`: readonly [``"path"``, ``"data"``]  }  } |
| `/session/:sessionId/appium/device/push_file.POST.command` | ``"pushFile"`` |
| `/session/:sessionId/appium/device/push_file.POST.payloadParams` | { `required`: readonly [``"path"``, ``"data"``]  } |
| `/session/:sessionId/appium/device/push_file.POST.payloadParams.required` | readonly [``"path"``, ``"data"``] |
| `/session/:sessionId/appium/device/remove_app` | { `POST`: { `command`: ``"removeApp"`` = 'removeApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  }  } |
| `/session/:sessionId/appium/device/remove_app.POST` | { `command`: ``"removeApp"`` = 'removeApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/remove_app.POST.command` | ``"removeApp"`` |
| `/session/:sessionId/appium/device/remove_app.POST.payloadParams` | { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/remove_app.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/appium/device/remove_app.POST.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/rotate` | { `POST`: { `command`: ``"mobileRotation"`` = 'mobileRotation'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``] ; `required`: readonly [``"x"``, ``"y"``, ``"radius"``, ``"rotation"``, ``"touchCount"``, ``"duration"``]  }  }  } |
| `/session/:sessionId/appium/device/rotate.POST` | { `command`: ``"mobileRotation"`` = 'mobileRotation'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``] ; `required`: readonly [``"x"``, ``"y"``, ``"radius"``, ``"rotation"``, ``"touchCount"``, ``"duration"``]  }  } |
| `/session/:sessionId/appium/device/rotate.POST.command` | ``"mobileRotation"`` |
| `/session/:sessionId/appium/device/rotate.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/rotate.POST.payloadParams` | { `optional`: readonly [``"element"``] ; `required`: readonly [``"x"``, ``"y"``, ``"radius"``, ``"rotation"``, ``"touchCount"``, ``"duration"``]  } |
| `/session/:sessionId/appium/device/rotate.POST.payloadParams.optional` | readonly [``"element"``] |
| `/session/:sessionId/appium/device/rotate.POST.payloadParams.required` | readonly [``"x"``, ``"y"``, ``"radius"``, ``"rotation"``, ``"touchCount"``, ``"duration"``] |
| `/session/:sessionId/appium/device/send_sms` | { `POST`: { `command`: ``"sendSMS"`` = 'sendSMS'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"phoneNumber"``, ``"message"``]  }  }  } |
| `/session/:sessionId/appium/device/send_sms.POST` | { `command`: ``"sendSMS"`` = 'sendSMS'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"phoneNumber"``, ``"message"``]  }  } |
| `/session/:sessionId/appium/device/send_sms.POST.command` | ``"sendSMS"`` |
| `/session/:sessionId/appium/device/send_sms.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/send_sms.POST.payloadParams` | { `required`: readonly [``"phoneNumber"``, ``"message"``]  } |
| `/session/:sessionId/appium/device/send_sms.POST.payloadParams.required` | readonly [``"phoneNumber"``, ``"message"``] |
| `/session/:sessionId/appium/device/set_clipboard` | { `POST`: { `command`: ``"setClipboard"`` = 'setClipboard'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"contentType"``, ``"label"``] ; `required`: readonly [``"content"``]  }  }  } |
| `/session/:sessionId/appium/device/set_clipboard.POST` | { `command`: ``"setClipboard"`` = 'setClipboard'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"contentType"``, ``"label"``] ; `required`: readonly [``"content"``]  }  } |
| `/session/:sessionId/appium/device/set_clipboard.POST.command` | ``"setClipboard"`` |
| `/session/:sessionId/appium/device/set_clipboard.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/set_clipboard.POST.payloadParams` | { `optional`: readonly [``"contentType"``, ``"label"``] ; `required`: readonly [``"content"``]  } |
| `/session/:sessionId/appium/device/set_clipboard.POST.payloadParams.optional` | readonly [``"contentType"``, ``"label"``] |
| `/session/:sessionId/appium/device/set_clipboard.POST.payloadParams.required` | readonly [``"content"``] |
| `/session/:sessionId/appium/device/shake` | { `POST`: { `command`: ``"mobileShake"`` = 'mobileShake'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/shake.POST` | { `command`: ``"mobileShake"`` = 'mobileShake'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/shake.POST.command` | ``"mobileShake"`` |
| `/session/:sessionId/appium/device/shake.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/start_activity` | { `POST`: { `command`: ``"startActivity"`` = 'startActivity'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"appWaitPackage"``, ``"appWaitActivity"``, ``"intentAction"``, ``"intentCategory"``, ``"intentFlags"``, ``"optionalIntentArguments"``, ``"dontStopAppOnReset"``] ; `required`: readonly [``"appPackage"``, ``"appActivity"``]  }  }  } |
| `/session/:sessionId/appium/device/start_activity.POST` | { `command`: ``"startActivity"`` = 'startActivity'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"appWaitPackage"``, ``"appWaitActivity"``, ``"intentAction"``, ``"intentCategory"``, ``"intentFlags"``, ``"optionalIntentArguments"``, ``"dontStopAppOnReset"``] ; `required`: readonly [``"appPackage"``, ``"appActivity"``]  }  } |
| `/session/:sessionId/appium/device/start_activity.POST.command` | ``"startActivity"`` |
| `/session/:sessionId/appium/device/start_activity.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/start_activity.POST.payloadParams` | { `optional`: readonly [``"appWaitPackage"``, ``"appWaitActivity"``, ``"intentAction"``, ``"intentCategory"``, ``"intentFlags"``, ``"optionalIntentArguments"``, ``"dontStopAppOnReset"``] ; `required`: readonly [``"appPackage"``, ``"appActivity"``]  } |
| `/session/:sessionId/appium/device/start_activity.POST.payloadParams.optional` | readonly [``"appWaitPackage"``, ``"appWaitActivity"``, ``"intentAction"``, ``"intentCategory"``, ``"intentFlags"``, ``"optionalIntentArguments"``, ``"dontStopAppOnReset"``] |
| `/session/:sessionId/appium/device/start_activity.POST.payloadParams.required` | readonly [``"appPackage"``, ``"appActivity"``] |
| `/session/:sessionId/appium/device/system_bars` | { `GET`: { `command`: ``"getSystemBars"`` = 'getSystemBars'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/system_bars.GET` | { `command`: ``"getSystemBars"`` = 'getSystemBars'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/system_bars.GET.command` | ``"getSystemBars"`` |
| `/session/:sessionId/appium/device/system_bars.GET.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/system_time` | { `GET`: { `command`: ``"getDeviceTime"`` = 'getDeviceTime'; `payloadParams`: { `optional`: readonly [``"format"``]  }  } ; `POST`: { `command`: ``"getDeviceTime"`` = 'getDeviceTime'; `payloadParams`: { `optional`: readonly [``"format"``]  }  }  } |
| `/session/:sessionId/appium/device/system_time.GET` | { `command`: ``"getDeviceTime"`` = 'getDeviceTime'; `payloadParams`: { `optional`: readonly [``"format"``]  }  } |
| `/session/:sessionId/appium/device/system_time.GET.command` | ``"getDeviceTime"`` |
| `/session/:sessionId/appium/device/system_time.GET.payloadParams` | { `optional`: readonly [``"format"``]  } |
| `/session/:sessionId/appium/device/system_time.GET.payloadParams.optional` | readonly [``"format"``] |
| `/session/:sessionId/appium/device/system_time.POST` | { `command`: ``"getDeviceTime"`` = 'getDeviceTime'; `payloadParams`: { `optional`: readonly [``"format"``]  }  } |
| `/session/:sessionId/appium/device/system_time.POST.command` | ``"getDeviceTime"`` |
| `/session/:sessionId/appium/device/system_time.POST.payloadParams` | { `optional`: readonly [``"format"``]  } |
| `/session/:sessionId/appium/device/system_time.POST.payloadParams.optional` | readonly [``"format"``] |
| `/session/:sessionId/appium/device/terminate_app` | { `POST`: { `command`: ``"terminateApp"`` = 'terminateApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  }  } |
| `/session/:sessionId/appium/device/terminate_app.POST` | { `command`: ``"terminateApp"`` = 'terminateApp'; `payloadParams`: { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  }  } |
| `/session/:sessionId/appium/device/terminate_app.POST.command` | ``"terminateApp"`` |
| `/session/:sessionId/appium/device/terminate_app.POST.payloadParams` | { `optional`: readonly [``"options"``] ; `required`: readonly [readonly [``"appId"``], readonly [``"bundleId"``]]  } |
| `/session/:sessionId/appium/device/terminate_app.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/appium/device/terminate_app.POST.payloadParams.required` | readonly [readonly [``"appId"``], readonly [``"bundleId"``]] |
| `/session/:sessionId/appium/device/toggle_airplane_mode` | { `POST`: { `command`: ``"toggleFlightMode"`` = 'toggleFlightMode'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/toggle_airplane_mode.POST` | { `command`: ``"toggleFlightMode"`` = 'toggleFlightMode'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/toggle_airplane_mode.POST.command` | ``"toggleFlightMode"`` |
| `/session/:sessionId/appium/device/toggle_airplane_mode.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/toggle_data` | { `POST`: { `command`: ``"toggleData"`` = 'toggleData'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/toggle_data.POST` | { `command`: ``"toggleData"`` = 'toggleData'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/toggle_data.POST.command` | ``"toggleData"`` |
| `/session/:sessionId/appium/device/toggle_data.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/toggle_location_services` | { `POST`: { `command`: ``"toggleLocationServices"`` = 'toggleLocationServices'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/toggle_location_services.POST` | { `command`: ``"toggleLocationServices"`` = 'toggleLocationServices'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/toggle_location_services.POST.command` | ``"toggleLocationServices"`` |
| `/session/:sessionId/appium/device/toggle_location_services.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/toggle_wifi` | { `POST`: { `command`: ``"toggleWiFi"`` = 'toggleWiFi'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/toggle_wifi.POST` | { `command`: ``"toggleWiFi"`` = 'toggleWiFi'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/toggle_wifi.POST.command` | ``"toggleWiFi"`` |
| `/session/:sessionId/appium/device/toggle_wifi.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/device/unlock` | { `POST`: { `command`: ``"unlock"`` = 'unlock'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/device/unlock.POST` | { `command`: ``"unlock"`` = 'unlock'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/device/unlock.POST.command` | ``"unlock"`` |
| `/session/:sessionId/appium/device/unlock.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/element/:elementId/replace_value` | { `POST`: { `command`: ``"replaceValue"`` = 'replaceValue'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"text"``]  }  }  } |
| `/session/:sessionId/appium/element/:elementId/replace_value.POST` | { `command`: ``"replaceValue"`` = 'replaceValue'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"text"``]  }  } |
| `/session/:sessionId/appium/element/:elementId/replace_value.POST.command` | ``"replaceValue"`` |
| `/session/:sessionId/appium/element/:elementId/replace_value.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/element/:elementId/replace_value.POST.payloadParams` | { `required`: readonly [``"text"``]  } |
| `/session/:sessionId/appium/element/:elementId/replace_value.POST.payloadParams.required` | readonly [``"text"``] |
| `/session/:sessionId/appium/element/:elementId/value` | { `POST`: { `command`: ``"setValueImmediate"`` = 'setValueImmediate'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"text"``]  }  }  } |
| `/session/:sessionId/appium/element/:elementId/value.POST` | { `command`: ``"setValueImmediate"`` = 'setValueImmediate'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"text"``]  }  } |
| `/session/:sessionId/appium/element/:elementId/value.POST.command` | ``"setValueImmediate"`` |
| `/session/:sessionId/appium/element/:elementId/value.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/element/:elementId/value.POST.payloadParams` | { `required`: readonly [``"text"``]  } |
| `/session/:sessionId/appium/element/:elementId/value.POST.payloadParams.required` | readonly [``"text"``] |
| `/session/:sessionId/appium/events` | { `POST`: { `command`: ``"getLogEvents"`` = 'getLogEvents'; `payloadParams`: { `optional`: readonly [``"type"``]  }  }  } |
| `/session/:sessionId/appium/events.POST` | { `command`: ``"getLogEvents"`` = 'getLogEvents'; `payloadParams`: { `optional`: readonly [``"type"``]  }  } |
| `/session/:sessionId/appium/events.POST.command` | ``"getLogEvents"`` |
| `/session/:sessionId/appium/events.POST.payloadParams` | { `optional`: readonly [``"type"``]  } |
| `/session/:sessionId/appium/events.POST.payloadParams.optional` | readonly [``"type"``] |
| `/session/:sessionId/appium/getPerformanceData` | { `POST`: { `command`: ``"getPerformanceData"`` = 'getPerformanceData'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"dataReadTimeout"``] ; `required`: readonly [``"packageName"``, ``"dataType"``]  }  }  } |
| `/session/:sessionId/appium/getPerformanceData.POST` | { `command`: ``"getPerformanceData"`` = 'getPerformanceData'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"dataReadTimeout"``] ; `required`: readonly [``"packageName"``, ``"dataType"``]  }  } |
| `/session/:sessionId/appium/getPerformanceData.POST.command` | ``"getPerformanceData"`` |
| `/session/:sessionId/appium/getPerformanceData.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/getPerformanceData.POST.payloadParams` | { `optional`: readonly [``"dataReadTimeout"``] ; `required`: readonly [``"packageName"``, ``"dataType"``]  } |
| `/session/:sessionId/appium/getPerformanceData.POST.payloadParams.optional` | readonly [``"dataReadTimeout"``] |
| `/session/:sessionId/appium/getPerformanceData.POST.payloadParams.required` | readonly [``"packageName"``, ``"dataType"``] |
| `/session/:sessionId/appium/log_event` | { `POST`: { `command`: ``"logCustomEvent"`` = 'logCustomEvent'; `payloadParams`: { `required`: readonly [``"vendor"``, ``"event"``]  }  }  } |
| `/session/:sessionId/appium/log_event.POST` | { `command`: ``"logCustomEvent"`` = 'logCustomEvent'; `payloadParams`: { `required`: readonly [``"vendor"``, ``"event"``]  }  } |
| `/session/:sessionId/appium/log_event.POST.command` | ``"logCustomEvent"`` |
| `/session/:sessionId/appium/log_event.POST.payloadParams` | { `required`: readonly [``"vendor"``, ``"event"``]  } |
| `/session/:sessionId/appium/log_event.POST.payloadParams.required` | readonly [``"vendor"``, ``"event"``] |
| `/session/:sessionId/appium/performanceData/types` | { `POST`: { `command`: ``"getPerformanceDataTypes"`` = 'getPerformanceDataTypes'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/appium/performanceData/types.POST` | { `command`: ``"getPerformanceDataTypes"`` = 'getPerformanceDataTypes'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/appium/performanceData/types.POST.command` | ``"getPerformanceDataTypes"`` |
| `/session/:sessionId/appium/performanceData/types.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/receive_async_response` | { `POST`: { `command`: ``"receiveAsyncResponse"`` = 'receiveAsyncResponse'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"response"``]  }  }  } |
| `/session/:sessionId/appium/receive_async_response.POST` | { `command`: ``"receiveAsyncResponse"`` = 'receiveAsyncResponse'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"response"``]  }  } |
| `/session/:sessionId/appium/receive_async_response.POST.command` | ``"receiveAsyncResponse"`` |
| `/session/:sessionId/appium/receive_async_response.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/receive_async_response.POST.payloadParams` | { `required`: readonly [``"response"``]  } |
| `/session/:sessionId/appium/receive_async_response.POST.payloadParams.required` | readonly [``"response"``] |
| `/session/:sessionId/appium/settings` | { `GET`: { `command`: ``"getSettings"`` = 'getSettings' } ; `POST`: { `command`: ``"updateSettings"`` = 'updateSettings'; `payloadParams`: { `required`: readonly [``"settings"``]  }  }  } |
| `/session/:sessionId/appium/settings.GET` | { `command`: ``"getSettings"`` = 'getSettings' } |
| `/session/:sessionId/appium/settings.GET.command` | ``"getSettings"`` |
| `/session/:sessionId/appium/settings.POST` | { `command`: ``"updateSettings"`` = 'updateSettings'; `payloadParams`: { `required`: readonly [``"settings"``]  }  } |
| `/session/:sessionId/appium/settings.POST.command` | ``"updateSettings"`` |
| `/session/:sessionId/appium/settings.POST.payloadParams` | { `required`: readonly [``"settings"``]  } |
| `/session/:sessionId/appium/settings.POST.payloadParams.required` | readonly [``"settings"``] |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment` | { `POST`: { `command`: ``"toggleEnrollTouchId"`` = 'toggleEnrollTouchId'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"enabled"``]  }  }  } |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment.POST` | { `command`: ``"toggleEnrollTouchId"`` = 'toggleEnrollTouchId'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"enabled"``]  }  } |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment.POST.command` | ``"toggleEnrollTouchId"`` |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment.POST.payloadParams` | { `optional`: readonly [``"enabled"``]  } |
| `/session/:sessionId/appium/simulator/toggle_touch_id_enrollment.POST.payloadParams.optional` | readonly [``"enabled"``] |
| `/session/:sessionId/appium/simulator/touch_id` | { `POST`: { `command`: ``"touchId"`` = 'touchId'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"match"``]  }  }  } |
| `/session/:sessionId/appium/simulator/touch_id.POST` | { `command`: ``"touchId"`` = 'touchId'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"match"``]  }  } |
| `/session/:sessionId/appium/simulator/touch_id.POST.command` | ``"touchId"`` |
| `/session/:sessionId/appium/simulator/touch_id.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/simulator/touch_id.POST.payloadParams` | { `required`: readonly [``"match"``]  } |
| `/session/:sessionId/appium/simulator/touch_id.POST.payloadParams.required` | readonly [``"match"``] |
| `/session/:sessionId/appium/start_recording_screen` | { `POST`: { `command`: ``"startRecordingScreen"`` = 'startRecordingScreen'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"options"``]  }  }  } |
| `/session/:sessionId/appium/start_recording_screen.POST` | { `command`: ``"startRecordingScreen"`` = 'startRecordingScreen'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"options"``]  }  } |
| `/session/:sessionId/appium/start_recording_screen.POST.command` | ``"startRecordingScreen"`` |
| `/session/:sessionId/appium/start_recording_screen.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/start_recording_screen.POST.payloadParams` | { `optional`: readonly [``"options"``]  } |
| `/session/:sessionId/appium/start_recording_screen.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/appium/stop_recording_screen` | { `POST`: { `command`: ``"stopRecordingScreen"`` = 'stopRecordingScreen'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"options"``]  }  }  } |
| `/session/:sessionId/appium/stop_recording_screen.POST` | { `command`: ``"stopRecordingScreen"`` = 'stopRecordingScreen'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"options"``]  }  } |
| `/session/:sessionId/appium/stop_recording_screen.POST.command` | ``"stopRecordingScreen"`` |
| `/session/:sessionId/appium/stop_recording_screen.POST.deprecated` | ``true`` |
| `/session/:sessionId/appium/stop_recording_screen.POST.payloadParams` | { `optional`: readonly [``"options"``]  } |
| `/session/:sessionId/appium/stop_recording_screen.POST.payloadParams.optional` | readonly [``"options"``] |
| `/session/:sessionId/application_cache/status` | { `GET`: {} = {} } |
| `/session/:sessionId/application_cache/status.GET` | {} |
| `/session/:sessionId/back` | { `POST`: { `command`: ``"back"`` = 'back' }  } |
| `/session/:sessionId/back.POST` | { `command`: ``"back"`` = 'back' } |
| `/session/:sessionId/back.POST.command` | ``"back"`` |
| `/session/:sessionId/buttondown` | { `POST`: { `command`: ``"buttonDown"`` = 'buttonDown'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  }  } |
| `/session/:sessionId/buttondown.POST` | { `command`: ``"buttonDown"`` = 'buttonDown'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  } |
| `/session/:sessionId/buttondown.POST.command` | ``"buttonDown"`` |
| `/session/:sessionId/buttondown.POST.deprecated` | ``true`` |
| `/session/:sessionId/buttondown.POST.payloadParams` | { `optional`: readonly [``"button"``]  } |
| `/session/:sessionId/buttondown.POST.payloadParams.optional` | readonly [``"button"``] |
| `/session/:sessionId/buttonup` | { `POST`: { `command`: ``"buttonUp"`` = 'buttonUp'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  }  } |
| `/session/:sessionId/buttonup.POST` | { `command`: ``"buttonUp"`` = 'buttonUp'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  } |
| `/session/:sessionId/buttonup.POST.command` | ``"buttonUp"`` |
| `/session/:sessionId/buttonup.POST.deprecated` | ``true`` |
| `/session/:sessionId/buttonup.POST.payloadParams` | { `optional`: readonly [``"button"``]  } |
| `/session/:sessionId/buttonup.POST.payloadParams.optional` | readonly [``"button"``] |
| `/session/:sessionId/click` | { `POST`: { `command`: ``"clickCurrent"`` = 'clickCurrent'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  }  } |
| `/session/:sessionId/click.POST` | { `command`: ``"clickCurrent"`` = 'clickCurrent'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"button"``]  }  } |
| `/session/:sessionId/click.POST.command` | ``"clickCurrent"`` |
| `/session/:sessionId/click.POST.deprecated` | ``true`` |
| `/session/:sessionId/click.POST.payloadParams` | { `optional`: readonly [``"button"``]  } |
| `/session/:sessionId/click.POST.payloadParams.optional` | readonly [``"button"``] |
| `/session/:sessionId/context` | { `GET`: { `command`: ``"getCurrentContext"`` = 'getCurrentContext' } ; `POST`: { `command`: ``"setContext"`` = 'setContext'; `payloadParams`: { `required`: readonly [``"name"``]  }  }  } |
| `/session/:sessionId/context.GET` | { `command`: ``"getCurrentContext"`` = 'getCurrentContext' } |
| `/session/:sessionId/context.GET.command` | ``"getCurrentContext"`` |
| `/session/:sessionId/context.POST` | { `command`: ``"setContext"`` = 'setContext'; `payloadParams`: { `required`: readonly [``"name"``]  }  } |
| `/session/:sessionId/context.POST.command` | ``"setContext"`` |
| `/session/:sessionId/context.POST.payloadParams` | { `required`: readonly [``"name"``]  } |
| `/session/:sessionId/context.POST.payloadParams.required` | readonly [``"name"``] |
| `/session/:sessionId/contexts` | { `GET`: { `command`: ``"getContexts"`` = 'getContexts' }  } |
| `/session/:sessionId/contexts.GET` | { `command`: ``"getContexts"`` = 'getContexts' } |
| `/session/:sessionId/contexts.GET.command` | ``"getContexts"`` |
| `/session/:sessionId/cookie` | { `DELETE`: { `command`: ``"deleteCookies"`` = 'deleteCookies' } ; `GET`: { `command`: ``"getCookies"`` = 'getCookies' } ; `POST`: { `command`: ``"setCookie"`` = 'setCookie'; `payloadParams`: { `required`: readonly [``"cookie"``]  }  }  } |
| `/session/:sessionId/cookie.DELETE` | { `command`: ``"deleteCookies"`` = 'deleteCookies' } |
| `/session/:sessionId/cookie.DELETE.command` | ``"deleteCookies"`` |
| `/session/:sessionId/cookie.GET` | { `command`: ``"getCookies"`` = 'getCookies' } |
| `/session/:sessionId/cookie.GET.command` | ``"getCookies"`` |
| `/session/:sessionId/cookie.POST` | { `command`: ``"setCookie"`` = 'setCookie'; `payloadParams`: { `required`: readonly [``"cookie"``]  }  } |
| `/session/:sessionId/cookie.POST.command` | ``"setCookie"`` |
| `/session/:sessionId/cookie.POST.payloadParams` | { `required`: readonly [``"cookie"``]  } |
| `/session/:sessionId/cookie.POST.payloadParams.required` | readonly [``"cookie"``] |
| `/session/:sessionId/cookie/:name` | { `DELETE`: { `command`: ``"deleteCookie"`` = 'deleteCookie' } ; `GET`: { `command`: ``"getCookie"`` = 'getCookie' }  } |
| `/session/:sessionId/cookie/:name.DELETE` | { `command`: ``"deleteCookie"`` = 'deleteCookie' } |
| `/session/:sessionId/cookie/:name.DELETE.command` | ``"deleteCookie"`` |
| `/session/:sessionId/cookie/:name.GET` | { `command`: ``"getCookie"`` = 'getCookie' } |
| `/session/:sessionId/cookie/:name.GET.command` | ``"getCookie"`` |
| `/session/:sessionId/dismiss_alert` | { `POST`: { `command`: ``"postDismissAlert"`` = 'postDismissAlert' }  } |
| `/session/:sessionId/dismiss_alert.POST` | { `command`: ``"postDismissAlert"`` = 'postDismissAlert' } |
| `/session/:sessionId/dismiss_alert.POST.command` | ``"postDismissAlert"`` |
| `/session/:sessionId/doubleclick` | { `POST`: { `command`: ``"doubleClick"`` = 'doubleClick'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/doubleclick.POST` | { `command`: ``"doubleClick"`` = 'doubleClick'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/doubleclick.POST.command` | ``"doubleClick"`` |
| `/session/:sessionId/doubleclick.POST.deprecated` | ``true`` |
| `/session/:sessionId/element` | { `POST`: { `command`: ``"findElement"`` = 'findElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/element.POST` | { `command`: ``"findElement"`` = 'findElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/element.POST.command` | ``"findElement"`` |
| `/session/:sessionId/element.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/element.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/element/:elementId` | { `GET`: {} = {} } |
| `/session/:sessionId/element/:elementId.GET` | {} |
| `/session/:sessionId/element/:elementId/attribute/:name` | { `GET`: { `command`: ``"getAttribute"`` = 'getAttribute' }  } |
| `/session/:sessionId/element/:elementId/attribute/:name.GET` | { `command`: ``"getAttribute"`` = 'getAttribute' } |
| `/session/:sessionId/element/:elementId/attribute/:name.GET.command` | ``"getAttribute"`` |
| `/session/:sessionId/element/:elementId/clear` | { `POST`: { `command`: ``"clear"`` = 'clear' }  } |
| `/session/:sessionId/element/:elementId/clear.POST` | { `command`: ``"clear"`` = 'clear' } |
| `/session/:sessionId/element/:elementId/clear.POST.command` | ``"clear"`` |
| `/session/:sessionId/element/:elementId/click` | { `POST`: { `command`: ``"click"`` = 'click' }  } |
| `/session/:sessionId/element/:elementId/click.POST` | { `command`: ``"click"`` = 'click' } |
| `/session/:sessionId/element/:elementId/click.POST.command` | ``"click"`` |
| `/session/:sessionId/element/:elementId/css/:propertyName` | { `GET`: { `command`: ``"getCssProperty"`` = 'getCssProperty' }  } |
| `/session/:sessionId/element/:elementId/css/:propertyName.GET` | { `command`: ``"getCssProperty"`` = 'getCssProperty' } |
| `/session/:sessionId/element/:elementId/css/:propertyName.GET.command` | ``"getCssProperty"`` |
| `/session/:sessionId/element/:elementId/displayed` | { `GET`: { `command`: ``"elementDisplayed"`` = 'elementDisplayed' }  } |
| `/session/:sessionId/element/:elementId/displayed.GET` | { `command`: ``"elementDisplayed"`` = 'elementDisplayed' } |
| `/session/:sessionId/element/:elementId/displayed.GET.command` | ``"elementDisplayed"`` |
| `/session/:sessionId/element/:elementId/element` | { `POST`: { `command`: ``"findElementFromElement"`` = 'findElementFromElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/element/:elementId/element.POST` | { `command`: ``"findElementFromElement"`` = 'findElementFromElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/element/:elementId/element.POST.command` | ``"findElementFromElement"`` |
| `/session/:sessionId/element/:elementId/element.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/element/:elementId/element.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/element/:elementId/elements` | { `POST`: { `command`: ``"findElementsFromElement"`` = 'findElementsFromElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/element/:elementId/elements.POST` | { `command`: ``"findElementsFromElement"`` = 'findElementsFromElement'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/element/:elementId/elements.POST.command` | ``"findElementsFromElement"`` |
| `/session/:sessionId/element/:elementId/elements.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/element/:elementId/elements.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/element/:elementId/enabled` | { `GET`: { `command`: ``"elementEnabled"`` = 'elementEnabled' }  } |
| `/session/:sessionId/element/:elementId/enabled.GET` | { `command`: ``"elementEnabled"`` = 'elementEnabled' } |
| `/session/:sessionId/element/:elementId/enabled.GET.command` | ``"elementEnabled"`` |
| `/session/:sessionId/element/:elementId/equals/:otherId` | { `GET`: { `command`: ``"equalsElement"`` = 'equalsElement'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/equals/:otherId.GET` | { `command`: ``"equalsElement"`` = 'equalsElement'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/equals/:otherId.GET.command` | ``"equalsElement"`` |
| `/session/:sessionId/element/:elementId/equals/:otherId.GET.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/location` | { `GET`: { `command`: ``"getLocation"`` = 'getLocation'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/location.GET` | { `command`: ``"getLocation"`` = 'getLocation'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/location.GET.command` | ``"getLocation"`` |
| `/session/:sessionId/element/:elementId/location.GET.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/location_in_view` | { `GET`: { `command`: ``"getLocationInView"`` = 'getLocationInView'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/location_in_view.GET` | { `command`: ``"getLocationInView"`` = 'getLocationInView'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/location_in_view.GET.command` | ``"getLocationInView"`` |
| `/session/:sessionId/element/:elementId/location_in_view.GET.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/name` | { `GET`: { `command`: ``"getName"`` = 'getName' }  } |
| `/session/:sessionId/element/:elementId/name.GET` | { `command`: ``"getName"`` = 'getName' } |
| `/session/:sessionId/element/:elementId/name.GET.command` | ``"getName"`` |
| `/session/:sessionId/element/:elementId/pageIndex` | { `GET`: { `command`: ``"getPageIndex"`` = 'getPageIndex'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/pageIndex.GET` | { `command`: ``"getPageIndex"`` = 'getPageIndex'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/pageIndex.GET.command` | ``"getPageIndex"`` |
| `/session/:sessionId/element/:elementId/pageIndex.GET.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/property/:name` | { `GET`: { `command`: ``"getProperty"`` = 'getProperty' }  } |
| `/session/:sessionId/element/:elementId/property/:name.GET` | { `command`: ``"getProperty"`` = 'getProperty' } |
| `/session/:sessionId/element/:elementId/property/:name.GET.command` | ``"getProperty"`` |
| `/session/:sessionId/element/:elementId/rect` | { `GET`: { `command`: ``"getElementRect"`` = 'getElementRect' }  } |
| `/session/:sessionId/element/:elementId/rect.GET` | { `command`: ``"getElementRect"`` = 'getElementRect' } |
| `/session/:sessionId/element/:elementId/rect.GET.command` | ``"getElementRect"`` |
| `/session/:sessionId/element/:elementId/screenshot` | { `GET`: { `command`: ``"getElementScreenshot"`` = 'getElementScreenshot' }  } |
| `/session/:sessionId/element/:elementId/screenshot.GET` | { `command`: ``"getElementScreenshot"`` = 'getElementScreenshot' } |
| `/session/:sessionId/element/:elementId/screenshot.GET.command` | ``"getElementScreenshot"`` |
| `/session/:sessionId/element/:elementId/selected` | { `GET`: { `command`: ``"elementSelected"`` = 'elementSelected' }  } |
| `/session/:sessionId/element/:elementId/selected.GET` | { `command`: ``"elementSelected"`` = 'elementSelected' } |
| `/session/:sessionId/element/:elementId/selected.GET.command` | ``"elementSelected"`` |
| `/session/:sessionId/element/:elementId/shadow` | { `GET`: { `command`: ``"elementShadowRoot"`` = 'elementShadowRoot' }  } |
| `/session/:sessionId/element/:elementId/shadow.GET` | { `command`: ``"elementShadowRoot"`` = 'elementShadowRoot' } |
| `/session/:sessionId/element/:elementId/shadow.GET.command` | ``"elementShadowRoot"`` |
| `/session/:sessionId/element/:elementId/size` | { `GET`: { `command`: ``"getSize"`` = 'getSize'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/size.GET` | { `command`: ``"getSize"`` = 'getSize'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/size.GET.command` | ``"getSize"`` |
| `/session/:sessionId/element/:elementId/size.GET.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/submit` | { `POST`: { `command`: ``"submit"`` = 'submit'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/element/:elementId/submit.POST` | { `command`: ``"submit"`` = 'submit'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/element/:elementId/submit.POST.command` | ``"submit"`` |
| `/session/:sessionId/element/:elementId/submit.POST.deprecated` | ``true`` |
| `/session/:sessionId/element/:elementId/text` | { `GET`: { `command`: ``"getText"`` = 'getText' }  } |
| `/session/:sessionId/element/:elementId/text.GET` | { `command`: ``"getText"`` = 'getText' } |
| `/session/:sessionId/element/:elementId/text.GET.command` | ``"getText"`` |
| `/session/:sessionId/element/:elementId/value` | { `POST`: { `command`: ``"setValue"`` = 'setValue'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"value"``, ``"text"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"text\" or \"value\" params"``  }  }  } |
| `/session/:sessionId/element/:elementId/value.POST` | { `command`: ``"setValue"`` = 'setValue'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"value"``, ``"text"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"text\" or \"value\" params"``  }  } |
| `/session/:sessionId/element/:elementId/value.POST.command` | ``"setValue"`` |
| `/session/:sessionId/element/:elementId/value.POST.payloadParams` | { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"value"``, ``"text"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"text\" or \"value\" params"``  } |
| `/session/:sessionId/element/:elementId/value.POST.payloadParams.makeArgs` | (`jsonObj`: `any`) => `any`[] |
| `/session/:sessionId/element/:elementId/value.POST.payloadParams.optional` | readonly [``"value"``, ``"text"``] |
| `/session/:sessionId/element/:elementId/value.POST.payloadParams.validate` | (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"text\" or \"value\" params"`` |
| `/session/:sessionId/element/active` | { `GET`: { `command`: ``"active"`` = 'active' } ; `POST`: { `command`: ``"active"`` = 'active' }  } |
| `/session/:sessionId/element/active.GET` | { `command`: ``"active"`` = 'active' } |
| `/session/:sessionId/element/active.GET.command` | ``"active"`` |
| `/session/:sessionId/element/active.POST` | { `command`: ``"active"`` = 'active' } |
| `/session/:sessionId/element/active.POST.command` | ``"active"`` |
| `/session/:sessionId/elements` | { `POST`: { `command`: ``"findElements"`` = 'findElements'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/elements.POST` | { `command`: ``"findElements"`` = 'findElements'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/elements.POST.command` | ``"findElements"`` |
| `/session/:sessionId/elements.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/elements.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/execute` | { `POST`: { `command`: ``"execute"`` = 'execute'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  }  } |
| `/session/:sessionId/execute.POST` | { `command`: ``"execute"`` = 'execute'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  } |
| `/session/:sessionId/execute.POST.command` | ``"execute"`` |
| `/session/:sessionId/execute.POST.payloadParams` | { `required`: readonly [``"script"``, ``"args"``]  } |
| `/session/:sessionId/execute.POST.payloadParams.required` | readonly [``"script"``, ``"args"``] |
| `/session/:sessionId/execute/async` | { `POST`: { `command`: ``"executeAsync"`` = 'executeAsync'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  }  } |
| `/session/:sessionId/execute/async.POST` | { `command`: ``"executeAsync"`` = 'executeAsync'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  } |
| `/session/:sessionId/execute/async.POST.command` | ``"executeAsync"`` |
| `/session/:sessionId/execute/async.POST.payloadParams` | { `required`: readonly [``"script"``, ``"args"``]  } |
| `/session/:sessionId/execute/async.POST.payloadParams.required` | readonly [``"script"``, ``"args"``] |
| `/session/:sessionId/execute/sync` | { `POST`: { `command`: ``"execute"`` = 'execute'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  }  } |
| `/session/:sessionId/execute/sync.POST` | { `command`: ``"execute"`` = 'execute'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  } |
| `/session/:sessionId/execute/sync.POST.command` | ``"execute"`` |
| `/session/:sessionId/execute/sync.POST.payloadParams` | { `required`: readonly [``"script"``, ``"args"``]  } |
| `/session/:sessionId/execute/sync.POST.payloadParams.required` | readonly [``"script"``, ``"args"``] |
| `/session/:sessionId/execute_async` | { `POST`: { `command`: ``"executeAsync"`` = 'executeAsync'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  }  } |
| `/session/:sessionId/execute_async.POST` | { `command`: ``"executeAsync"`` = 'executeAsync'; `payloadParams`: { `required`: readonly [``"script"``, ``"args"``]  }  } |
| `/session/:sessionId/execute_async.POST.command` | ``"executeAsync"`` |
| `/session/:sessionId/execute_async.POST.payloadParams` | { `required`: readonly [``"script"``, ``"args"``]  } |
| `/session/:sessionId/execute_async.POST.payloadParams.required` | readonly [``"script"``, ``"args"``] |
| `/session/:sessionId/forward` | { `POST`: { `command`: ``"forward"`` = 'forward' }  } |
| `/session/:sessionId/forward.POST` | { `command`: ``"forward"`` = 'forward' } |
| `/session/:sessionId/forward.POST.command` | ``"forward"`` |
| `/session/:sessionId/frame` | { `POST`: { `command`: ``"setFrame"`` = 'setFrame'; `payloadParams`: { `required`: readonly [``"id"``]  }  }  } |
| `/session/:sessionId/frame.POST` | { `command`: ``"setFrame"`` = 'setFrame'; `payloadParams`: { `required`: readonly [``"id"``]  }  } |
| `/session/:sessionId/frame.POST.command` | ``"setFrame"`` |
| `/session/:sessionId/frame.POST.payloadParams` | { `required`: readonly [``"id"``]  } |
| `/session/:sessionId/frame.POST.payloadParams.required` | readonly [``"id"``] |
| `/session/:sessionId/frame/parent` | { `POST`: { `command`: ``"switchToParentFrame"`` = 'switchToParentFrame' }  } |
| `/session/:sessionId/frame/parent.POST` | { `command`: ``"switchToParentFrame"`` = 'switchToParentFrame' } |
| `/session/:sessionId/frame/parent.POST.command` | ``"switchToParentFrame"`` |
| `/session/:sessionId/ime/activate` | { `POST`: { `command`: ``"activateIMEEngine"`` = 'activateIMEEngine'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"engine"``]  }  }  } |
| `/session/:sessionId/ime/activate.POST` | { `command`: ``"activateIMEEngine"`` = 'activateIMEEngine'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"engine"``]  }  } |
| `/session/:sessionId/ime/activate.POST.command` | ``"activateIMEEngine"`` |
| `/session/:sessionId/ime/activate.POST.deprecated` | ``true`` |
| `/session/:sessionId/ime/activate.POST.payloadParams` | { `required`: readonly [``"engine"``]  } |
| `/session/:sessionId/ime/activate.POST.payloadParams.required` | readonly [``"engine"``] |
| `/session/:sessionId/ime/activated` | { `GET`: { `command`: ``"isIMEActivated"`` = 'isIMEActivated'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/ime/activated.GET` | { `command`: ``"isIMEActivated"`` = 'isIMEActivated'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/ime/activated.GET.command` | ``"isIMEActivated"`` |
| `/session/:sessionId/ime/activated.GET.deprecated` | ``true`` |
| `/session/:sessionId/ime/active_engine` | { `GET`: { `command`: ``"getActiveIMEEngine"`` = 'getActiveIMEEngine'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/ime/active_engine.GET` | { `command`: ``"getActiveIMEEngine"`` = 'getActiveIMEEngine'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/ime/active_engine.GET.command` | ``"getActiveIMEEngine"`` |
| `/session/:sessionId/ime/active_engine.GET.deprecated` | ``true`` |
| `/session/:sessionId/ime/available_engines` | { `GET`: { `command`: ``"availableIMEEngines"`` = 'availableIMEEngines'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/ime/available_engines.GET` | { `command`: ``"availableIMEEngines"`` = 'availableIMEEngines'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/ime/available_engines.GET.command` | ``"availableIMEEngines"`` |
| `/session/:sessionId/ime/available_engines.GET.deprecated` | ``true`` |
| `/session/:sessionId/ime/deactivate` | { `POST`: { `command`: ``"deactivateIMEEngine"`` = 'deactivateIMEEngine'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/ime/deactivate.POST` | { `command`: ``"deactivateIMEEngine"`` = 'deactivateIMEEngine'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/ime/deactivate.POST.command` | ``"deactivateIMEEngine"`` |
| `/session/:sessionId/ime/deactivate.POST.deprecated` | ``true`` |
| `/session/:sessionId/keys` | { `POST`: { `command`: ``"keys"`` = 'keys'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"value"``]  }  }  } |
| `/session/:sessionId/keys.POST` | { `command`: ``"keys"`` = 'keys'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"value"``]  }  } |
| `/session/:sessionId/keys.POST.command` | ``"keys"`` |
| `/session/:sessionId/keys.POST.deprecated` | ``true`` |
| `/session/:sessionId/keys.POST.payloadParams` | { `required`: readonly [``"value"``]  } |
| `/session/:sessionId/keys.POST.payloadParams.required` | readonly [``"value"``] |
| `/session/:sessionId/local_storage` | { `DELETE`: { `deprecated`: ``true`` = true } ; `GET`: { `deprecated`: ``true`` = true } ; `POST`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/local_storage.DELETE` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage.DELETE.deprecated` | ``true`` |
| `/session/:sessionId/local_storage.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage.GET.deprecated` | ``true`` |
| `/session/:sessionId/local_storage.POST` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage.POST.deprecated` | ``true`` |
| `/session/:sessionId/local_storage/key/:key` | { `DELETE`: { `deprecated`: ``true`` = true } ; `GET`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/local_storage/key/:key.DELETE` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage/key/:key.DELETE.deprecated` | ``true`` |
| `/session/:sessionId/local_storage/key/:key.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage/key/:key.GET.deprecated` | ``true`` |
| `/session/:sessionId/local_storage/size` | { `GET`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/local_storage/size.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/local_storage/size.GET.deprecated` | ``true`` |
| `/session/:sessionId/location` | { `GET`: { `command`: ``"getGeoLocation"`` = 'getGeoLocation' } ; `POST`: { `command`: ``"setGeoLocation"`` = 'setGeoLocation'; `payloadParams`: { `required`: readonly [``"location"``]  }  }  } |
| `/session/:sessionId/location.GET` | { `command`: ``"getGeoLocation"`` = 'getGeoLocation' } |
| `/session/:sessionId/location.GET.command` | ``"getGeoLocation"`` |
| `/session/:sessionId/location.POST` | { `command`: ``"setGeoLocation"`` = 'setGeoLocation'; `payloadParams`: { `required`: readonly [``"location"``]  }  } |
| `/session/:sessionId/location.POST.command` | ``"setGeoLocation"`` |
| `/session/:sessionId/location.POST.payloadParams` | { `required`: readonly [``"location"``]  } |
| `/session/:sessionId/location.POST.payloadParams.required` | readonly [``"location"``] |
| `/session/:sessionId/log` | { `POST`: { `command`: ``"getLog"`` = 'getLog'; `payloadParams`: { `required`: readonly [``"type"``]  }  }  } |
| `/session/:sessionId/log.POST` | { `command`: ``"getLog"`` = 'getLog'; `payloadParams`: { `required`: readonly [``"type"``]  }  } |
| `/session/:sessionId/log.POST.command` | ``"getLog"`` |
| `/session/:sessionId/log.POST.payloadParams` | { `required`: readonly [``"type"``]  } |
| `/session/:sessionId/log.POST.payloadParams.required` | readonly [``"type"``] |
| `/session/:sessionId/log/types` | { `GET`: { `command`: ``"getLogTypes"`` = 'getLogTypes' }  } |
| `/session/:sessionId/log/types.GET` | { `command`: ``"getLogTypes"`` = 'getLogTypes' } |
| `/session/:sessionId/log/types.GET.command` | ``"getLogTypes"`` |
| `/session/:sessionId/moveto` | { `POST`: { `command`: ``"moveTo"`` = 'moveTo'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``, ``"xoffset"``, ``"yoffset"``]  }  }  } |
| `/session/:sessionId/moveto.POST` | { `command`: ``"moveTo"`` = 'moveTo'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``, ``"xoffset"``, ``"yoffset"``]  }  } |
| `/session/:sessionId/moveto.POST.command` | ``"moveTo"`` |
| `/session/:sessionId/moveto.POST.deprecated` | ``true`` |
| `/session/:sessionId/moveto.POST.payloadParams` | { `optional`: readonly [``"element"``, ``"xoffset"``, ``"yoffset"``]  } |
| `/session/:sessionId/moveto.POST.payloadParams.optional` | readonly [``"element"``, ``"xoffset"``, ``"yoffset"``] |
| `/session/:sessionId/network_connection` | { `GET`: { `command`: ``"getNetworkConnection"`` = 'getNetworkConnection' } ; `POST`: { `command`: ``"setNetworkConnection"`` = 'setNetworkConnection'; `payloadParams`: { `required`: readonly [``"type"``] ; `unwrap`: ``"parameters"`` = 'parameters' }  }  } |
| `/session/:sessionId/network_connection.GET` | { `command`: ``"getNetworkConnection"`` = 'getNetworkConnection' } |
| `/session/:sessionId/network_connection.GET.command` | ``"getNetworkConnection"`` |
| `/session/:sessionId/network_connection.POST` | { `command`: ``"setNetworkConnection"`` = 'setNetworkConnection'; `payloadParams`: { `required`: readonly [``"type"``] ; `unwrap`: ``"parameters"`` = 'parameters' }  } |
| `/session/:sessionId/network_connection.POST.command` | ``"setNetworkConnection"`` |
| `/session/:sessionId/network_connection.POST.payloadParams` | { `required`: readonly [``"type"``] ; `unwrap`: ``"parameters"`` = 'parameters' } |
| `/session/:sessionId/network_connection.POST.payloadParams.required` | readonly [``"type"``] |
| `/session/:sessionId/network_connection.POST.payloadParams.unwrap` | ``"parameters"`` |
| `/session/:sessionId/orientation` | { `GET`: { `command`: ``"getOrientation"`` = 'getOrientation' } ; `POST`: { `command`: ``"setOrientation"`` = 'setOrientation'; `payloadParams`: { `required`: readonly [``"orientation"``]  }  }  } |
| `/session/:sessionId/orientation.GET` | { `command`: ``"getOrientation"`` = 'getOrientation' } |
| `/session/:sessionId/orientation.GET.command` | ``"getOrientation"`` |
| `/session/:sessionId/orientation.POST` | { `command`: ``"setOrientation"`` = 'setOrientation'; `payloadParams`: { `required`: readonly [``"orientation"``]  }  } |
| `/session/:sessionId/orientation.POST.command` | ``"setOrientation"`` |
| `/session/:sessionId/orientation.POST.payloadParams` | { `required`: readonly [``"orientation"``]  } |
| `/session/:sessionId/orientation.POST.payloadParams.required` | readonly [``"orientation"``] |
| `/session/:sessionId/receive_async_response` | { `POST`: { `command`: ``"receiveAsyncResponse"`` = 'receiveAsyncResponse'; `payloadParams`: { `required`: readonly [``"status"``, ``"value"``]  }  }  } |
| `/session/:sessionId/receive_async_response.POST` | { `command`: ``"receiveAsyncResponse"`` = 'receiveAsyncResponse'; `payloadParams`: { `required`: readonly [``"status"``, ``"value"``]  }  } |
| `/session/:sessionId/receive_async_response.POST.command` | ``"receiveAsyncResponse"`` |
| `/session/:sessionId/receive_async_response.POST.payloadParams` | { `required`: readonly [``"status"``, ``"value"``]  } |
| `/session/:sessionId/receive_async_response.POST.payloadParams.required` | readonly [``"status"``, ``"value"``] |
| `/session/:sessionId/refresh` | { `POST`: { `command`: ``"refresh"`` = 'refresh' }  } |
| `/session/:sessionId/refresh.POST` | { `command`: ``"refresh"`` = 'refresh' } |
| `/session/:sessionId/refresh.POST.command` | ``"refresh"`` |
| `/session/:sessionId/rotation` | { `GET`: { `command`: ``"getRotation"`` = 'getRotation' } ; `POST`: { `command`: ``"setRotation"`` = 'setRotation'; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``, ``"z"``]  }  }  } |
| `/session/:sessionId/rotation.GET` | { `command`: ``"getRotation"`` = 'getRotation' } |
| `/session/:sessionId/rotation.GET.command` | ``"getRotation"`` |
| `/session/:sessionId/rotation.POST` | { `command`: ``"setRotation"`` = 'setRotation'; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``, ``"z"``]  }  } |
| `/session/:sessionId/rotation.POST.command` | ``"setRotation"`` |
| `/session/:sessionId/rotation.POST.payloadParams` | { `required`: readonly [``"x"``, ``"y"``, ``"z"``]  } |
| `/session/:sessionId/rotation.POST.payloadParams.required` | readonly [``"x"``, ``"y"``, ``"z"``] |
| `/session/:sessionId/screenshot` | { `GET`: { `command`: ``"getScreenshot"`` = 'getScreenshot' }  } |
| `/session/:sessionId/screenshot.GET` | { `command`: ``"getScreenshot"`` = 'getScreenshot' } |
| `/session/:sessionId/screenshot.GET.command` | ``"getScreenshot"`` |
| `/session/:sessionId/screenshot/:elementId` | { `GET`: { `command`: ``"getElementScreenshot"`` = 'getElementScreenshot' }  } |
| `/session/:sessionId/screenshot/:elementId.GET` | { `command`: ``"getElementScreenshot"`` = 'getElementScreenshot' } |
| `/session/:sessionId/screenshot/:elementId.GET.command` | ``"getElementScreenshot"`` |
| `/session/:sessionId/se/log` | { `POST`: { `command`: ``"getLog"`` = 'getLog'; `payloadParams`: { `required`: readonly [``"type"``]  }  }  } |
| `/session/:sessionId/se/log.POST` | { `command`: ``"getLog"`` = 'getLog'; `payloadParams`: { `required`: readonly [``"type"``]  }  } |
| `/session/:sessionId/se/log.POST.command` | ``"getLog"`` |
| `/session/:sessionId/se/log.POST.payloadParams` | { `required`: readonly [``"type"``]  } |
| `/session/:sessionId/se/log.POST.payloadParams.required` | readonly [``"type"``] |
| `/session/:sessionId/se/log/types` | { `GET`: { `command`: ``"getLogTypes"`` = 'getLogTypes' }  } |
| `/session/:sessionId/se/log/types.GET` | { `command`: ``"getLogTypes"`` = 'getLogTypes' } |
| `/session/:sessionId/se/log/types.GET.command` | ``"getLogTypes"`` |
| `/session/:sessionId/session_storage` | { `DELETE`: { `deprecated`: ``true`` = true } ; `GET`: { `deprecated`: ``true`` = true } ; `POST`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/session_storage.DELETE` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage.DELETE.deprecated` | ``true`` |
| `/session/:sessionId/session_storage.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage.GET.deprecated` | ``true`` |
| `/session/:sessionId/session_storage.POST` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage.POST.deprecated` | ``true`` |
| `/session/:sessionId/session_storage/key/:key` | { `DELETE`: { `deprecated`: ``true`` = true } ; `GET`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/session_storage/key/:key.DELETE` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage/key/:key.DELETE.deprecated` | ``true`` |
| `/session/:sessionId/session_storage/key/:key.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage/key/:key.GET.deprecated` | ``true`` |
| `/session/:sessionId/session_storage/size` | { `GET`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/session_storage/size.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/session_storage/size.GET.deprecated` | ``true`` |
| `/session/:sessionId/shadow/:shadowId/element` | { `POST`: { `command`: ``"findElementFromShadowRoot"`` = 'findElementFromShadowRoot'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/shadow/:shadowId/element.POST` | { `command`: ``"findElementFromShadowRoot"`` = 'findElementFromShadowRoot'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/shadow/:shadowId/element.POST.command` | ``"findElementFromShadowRoot"`` |
| `/session/:sessionId/shadow/:shadowId/element.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/shadow/:shadowId/element.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/shadow/:shadowId/elements` | { `POST`: { `command`: ``"findElementsFromShadowRoot"`` = 'findElementsFromShadowRoot'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  }  } |
| `/session/:sessionId/shadow/:shadowId/elements.POST` | { `command`: ``"findElementsFromShadowRoot"`` = 'findElementsFromShadowRoot'; `payloadParams`: { `required`: readonly [``"using"``, ``"value"``]  }  } |
| `/session/:sessionId/shadow/:shadowId/elements.POST.command` | ``"findElementsFromShadowRoot"`` |
| `/session/:sessionId/shadow/:shadowId/elements.POST.payloadParams` | { `required`: readonly [``"using"``, ``"value"``]  } |
| `/session/:sessionId/shadow/:shadowId/elements.POST.payloadParams.required` | readonly [``"using"``, ``"value"``] |
| `/session/:sessionId/source` | { `GET`: { `command`: ``"getPageSource"`` = 'getPageSource' }  } |
| `/session/:sessionId/source.GET` | { `command`: ``"getPageSource"`` = 'getPageSource' } |
| `/session/:sessionId/source.GET.command` | ``"getPageSource"`` |
| `/session/:sessionId/timeouts` | { `GET`: { `command`: ``"getTimeouts"`` = 'getTimeouts' } ; `POST`: { `command`: ``"timeouts"`` = 'timeouts'; `payloadParams`: { `optional`: readonly [``"type"``, ``"ms"``, ``"script"``, ``"pageLoad"``, ``"implicit"``] ; `validate`: (`jsonObj`: `any`, `protocolName`: `string`) => `undefined` \| ``"W3C protocol expects any of script, pageLoad or implicit to be set"`` \| ``"MJSONWP protocol requires type and ms"``  }  }  } |
| `/session/:sessionId/timeouts.GET` | { `command`: ``"getTimeouts"`` = 'getTimeouts' } |
| `/session/:sessionId/timeouts.GET.command` | ``"getTimeouts"`` |
| `/session/:sessionId/timeouts.POST` | { `command`: ``"timeouts"`` = 'timeouts'; `payloadParams`: { `optional`: readonly [``"type"``, ``"ms"``, ``"script"``, ``"pageLoad"``, ``"implicit"``] ; `validate`: (`jsonObj`: `any`, `protocolName`: `string`) => `undefined` \| ``"W3C protocol expects any of script, pageLoad or implicit to be set"`` \| ``"MJSONWP protocol requires type and ms"``  }  } |
| `/session/:sessionId/timeouts.POST.command` | ``"timeouts"`` |
| `/session/:sessionId/timeouts.POST.payloadParams` | { `optional`: readonly [``"type"``, ``"ms"``, ``"script"``, ``"pageLoad"``, ``"implicit"``] ; `validate`: (`jsonObj`: `any`, `protocolName`: `string`) => `undefined` \| ``"W3C protocol expects any of script, pageLoad or implicit to be set"`` \| ``"MJSONWP protocol requires type and ms"``  } |
| `/session/:sessionId/timeouts.POST.payloadParams.optional` | readonly [``"type"``, ``"ms"``, ``"script"``, ``"pageLoad"``, ``"implicit"``] |
| `/session/:sessionId/timeouts.POST.payloadParams.validate` | (`jsonObj`: `any`, `protocolName`: `string`) => `undefined` \| ``"W3C protocol expects any of script, pageLoad or implicit to be set"`` \| ``"MJSONWP protocol requires type and ms"`` |
| `/session/:sessionId/timeouts/async_script` | { `POST`: { `command`: ``"asyncScriptTimeout"`` = 'asyncScriptTimeout'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"ms"``]  }  }  } |
| `/session/:sessionId/timeouts/async_script.POST` | { `command`: ``"asyncScriptTimeout"`` = 'asyncScriptTimeout'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"ms"``]  }  } |
| `/session/:sessionId/timeouts/async_script.POST.command` | ``"asyncScriptTimeout"`` |
| `/session/:sessionId/timeouts/async_script.POST.deprecated` | ``true`` |
| `/session/:sessionId/timeouts/async_script.POST.payloadParams` | { `required`: readonly [``"ms"``]  } |
| `/session/:sessionId/timeouts/async_script.POST.payloadParams.required` | readonly [``"ms"``] |
| `/session/:sessionId/timeouts/implicit_wait` | { `POST`: { `command`: ``"implicitWait"`` = 'implicitWait'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"ms"``]  }  }  } |
| `/session/:sessionId/timeouts/implicit_wait.POST` | { `command`: ``"implicitWait"`` = 'implicitWait'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"ms"``]  }  } |
| `/session/:sessionId/timeouts/implicit_wait.POST.command` | ``"implicitWait"`` |
| `/session/:sessionId/timeouts/implicit_wait.POST.deprecated` | ``true`` |
| `/session/:sessionId/timeouts/implicit_wait.POST.payloadParams` | { `required`: readonly [``"ms"``]  } |
| `/session/:sessionId/timeouts/implicit_wait.POST.payloadParams.required` | readonly [``"ms"``] |
| `/session/:sessionId/title` | { `GET`: { `command`: ``"title"`` = 'title' }  } |
| `/session/:sessionId/title.GET` | { `command`: ``"title"`` = 'title' } |
| `/session/:sessionId/title.GET.command` | ``"title"`` |
| `/session/:sessionId/touch/click` | { `POST`: { `command`: ``"click"`` = 'click'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"element"``]  }  }  } |
| `/session/:sessionId/touch/click.POST` | { `command`: ``"click"`` = 'click'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"element"``]  }  } |
| `/session/:sessionId/touch/click.POST.command` | ``"click"`` |
| `/session/:sessionId/touch/click.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/click.POST.payloadParams` | { `required`: readonly [``"element"``]  } |
| `/session/:sessionId/touch/click.POST.payloadParams.required` | readonly [``"element"``] |
| `/session/:sessionId/touch/doubleclick` | { `POST`: {} = {} } |
| `/session/:sessionId/touch/doubleclick.POST` | {} |
| `/session/:sessionId/touch/down` | { `POST`: { `command`: ``"touchDown"`` = 'touchDown'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  }  } |
| `/session/:sessionId/touch/down.POST` | { `command`: ``"touchDown"`` = 'touchDown'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  } |
| `/session/:sessionId/touch/down.POST.command` | ``"touchDown"`` |
| `/session/:sessionId/touch/down.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/down.POST.payloadParams` | { `required`: readonly [``"x"``, ``"y"``]  } |
| `/session/:sessionId/touch/down.POST.payloadParams.required` | readonly [``"x"``, ``"y"``] |
| `/session/:sessionId/touch/flick` | { `POST`: { `command`: ``"flick"`` = 'flick'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``, ``"xspeed"``, ``"yspeed"``, ``"xoffset"``, ``"yoffset"``, ``"speed"``]  }  }  } |
| `/session/:sessionId/touch/flick.POST` | { `command`: ``"flick"`` = 'flick'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"element"``, ``"xspeed"``, ``"yspeed"``, ``"xoffset"``, ``"yoffset"``, ``"speed"``]  }  } |
| `/session/:sessionId/touch/flick.POST.command` | ``"flick"`` |
| `/session/:sessionId/touch/flick.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/flick.POST.payloadParams` | { `optional`: readonly [``"element"``, ``"xspeed"``, ``"yspeed"``, ``"xoffset"``, ``"yoffset"``, ``"speed"``]  } |
| `/session/:sessionId/touch/flick.POST.payloadParams.optional` | readonly [``"element"``, ``"xspeed"``, ``"yspeed"``, ``"xoffset"``, ``"yoffset"``, ``"speed"``] |
| `/session/:sessionId/touch/longclick` | { `POST`: { `command`: ``"touchLongClick"`` = 'touchLongClick'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"elements"``]  }  }  } |
| `/session/:sessionId/touch/longclick.POST` | { `command`: ``"touchLongClick"`` = 'touchLongClick'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"elements"``]  }  } |
| `/session/:sessionId/touch/longclick.POST.command` | ``"touchLongClick"`` |
| `/session/:sessionId/touch/longclick.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/longclick.POST.payloadParams` | { `required`: readonly [``"elements"``]  } |
| `/session/:sessionId/touch/longclick.POST.payloadParams.required` | readonly [``"elements"``] |
| `/session/:sessionId/touch/move` | { `POST`: { `command`: ``"touchMove"`` = 'touchMove'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  }  } |
| `/session/:sessionId/touch/move.POST` | { `command`: ``"touchMove"`` = 'touchMove'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  } |
| `/session/:sessionId/touch/move.POST.command` | ``"touchMove"`` |
| `/session/:sessionId/touch/move.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/move.POST.payloadParams` | { `required`: readonly [``"x"``, ``"y"``]  } |
| `/session/:sessionId/touch/move.POST.payloadParams.required` | readonly [``"x"``, ``"y"``] |
| `/session/:sessionId/touch/multi/perform` | { `POST`: { `command`: ``"performMultiAction"`` = 'performMultiAction'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"elementId"``] ; `required`: readonly [``"actions"``]  }  }  } |
| `/session/:sessionId/touch/multi/perform.POST` | { `command`: ``"performMultiAction"`` = 'performMultiAction'; `deprecated`: ``true`` = true; `payloadParams`: { `optional`: readonly [``"elementId"``] ; `required`: readonly [``"actions"``]  }  } |
| `/session/:sessionId/touch/multi/perform.POST.command` | ``"performMultiAction"`` |
| `/session/:sessionId/touch/multi/perform.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/multi/perform.POST.payloadParams` | { `optional`: readonly [``"elementId"``] ; `required`: readonly [``"actions"``]  } |
| `/session/:sessionId/touch/multi/perform.POST.payloadParams.optional` | readonly [``"elementId"``] |
| `/session/:sessionId/touch/multi/perform.POST.payloadParams.required` | readonly [``"actions"``] |
| `/session/:sessionId/touch/perform` | { `POST`: { `command`: ``"performTouch"`` = 'performTouch'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"actions"``] ; `wrap`: ``"actions"`` = 'actions' }  }  } |
| `/session/:sessionId/touch/perform.POST` | { `command`: ``"performTouch"`` = 'performTouch'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"actions"``] ; `wrap`: ``"actions"`` = 'actions' }  } |
| `/session/:sessionId/touch/perform.POST.command` | ``"performTouch"`` |
| `/session/:sessionId/touch/perform.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/perform.POST.payloadParams` | { `required`: readonly [``"actions"``] ; `wrap`: ``"actions"`` = 'actions' } |
| `/session/:sessionId/touch/perform.POST.payloadParams.required` | readonly [``"actions"``] |
| `/session/:sessionId/touch/perform.POST.payloadParams.wrap` | ``"actions"`` |
| `/session/:sessionId/touch/scroll` | { `POST`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/touch/scroll.POST` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/touch/scroll.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/up` | { `POST`: { `command`: ``"touchUp"`` = 'touchUp'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  }  } |
| `/session/:sessionId/touch/up.POST` | { `command`: ``"touchUp"`` = 'touchUp'; `deprecated`: ``true`` = true; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``]  }  } |
| `/session/:sessionId/touch/up.POST.command` | ``"touchUp"`` |
| `/session/:sessionId/touch/up.POST.deprecated` | ``true`` |
| `/session/:sessionId/touch/up.POST.payloadParams` | { `required`: readonly [``"x"``, ``"y"``]  } |
| `/session/:sessionId/touch/up.POST.payloadParams.required` | readonly [``"x"``, ``"y"``] |
| `/session/:sessionId/url` | { `GET`: { `command`: ``"getUrl"`` = 'getUrl' } ; `POST`: { `command`: ``"setUrl"`` = 'setUrl'; `payloadParams`: { `required`: readonly [``"url"``]  }  }  } |
| `/session/:sessionId/url.GET` | { `command`: ``"getUrl"`` = 'getUrl' } |
| `/session/:sessionId/url.GET.command` | ``"getUrl"`` |
| `/session/:sessionId/url.POST` | { `command`: ``"setUrl"`` = 'setUrl'; `payloadParams`: { `required`: readonly [``"url"``]  }  } |
| `/session/:sessionId/url.POST.command` | ``"setUrl"`` |
| `/session/:sessionId/url.POST.payloadParams` | { `required`: readonly [``"url"``]  } |
| `/session/:sessionId/url.POST.payloadParams.required` | readonly [``"url"``] |
| `/session/:sessionId/webauthn/authenticator` | { `POST`: { `command`: ``"addVirtualAuthenticator"`` = 'addVirtualAuthenticator'; `payloadParams`: { `optional`: readonly [``"hasResidentKey"``, ``"hasUserVerification"``, ``"isUserConsenting"``, ``"isUserVerified"``] ; `required`: readonly [``"protocol"``, ``"transport"``]  }  }  } |
| `/session/:sessionId/webauthn/authenticator.POST` | { `command`: ``"addVirtualAuthenticator"`` = 'addVirtualAuthenticator'; `payloadParams`: { `optional`: readonly [``"hasResidentKey"``, ``"hasUserVerification"``, ``"isUserConsenting"``, ``"isUserVerified"``] ; `required`: readonly [``"protocol"``, ``"transport"``]  }  } |
| `/session/:sessionId/webauthn/authenticator.POST.command` | ``"addVirtualAuthenticator"`` |
| `/session/:sessionId/webauthn/authenticator.POST.payloadParams` | { `optional`: readonly [``"hasResidentKey"``, ``"hasUserVerification"``, ``"isUserConsenting"``, ``"isUserVerified"``] ; `required`: readonly [``"protocol"``, ``"transport"``]  } |
| `/session/:sessionId/webauthn/authenticator.POST.payloadParams.optional` | readonly [``"hasResidentKey"``, ``"hasUserVerification"``, ``"isUserConsenting"``, ``"isUserVerified"``] |
| `/session/:sessionId/webauthn/authenticator.POST.payloadParams.required` | readonly [``"protocol"``, ``"transport"``] |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId` | { `DELETE`: { `command`: ``"removeVirtualAuthenticator"`` = 'removeVirtualAuthenticator' }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId.DELETE` | { `command`: ``"removeVirtualAuthenticator"`` = 'removeVirtualAuthenticator' } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId.DELETE.command` | ``"removeVirtualAuthenticator"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential` | { `POST`: { `command`: ``"addAuthCredential"`` = 'addAuthCredential'; `payloadParams`: { `optional`: readonly [``"userHandle"``, ``"signCount"``] ; `required`: readonly [``"credentialId"``, ``"isResidentCredential"``, ``"rpId"``, ``"privateKey"``]  }  }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential.POST` | { `command`: ``"addAuthCredential"`` = 'addAuthCredential'; `payloadParams`: { `optional`: readonly [``"userHandle"``, ``"signCount"``] ; `required`: readonly [``"credentialId"``, ``"isResidentCredential"``, ``"rpId"``, ``"privateKey"``]  }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential.POST.command` | ``"addAuthCredential"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential.POST.payloadParams` | { `optional`: readonly [``"userHandle"``, ``"signCount"``] ; `required`: readonly [``"credentialId"``, ``"isResidentCredential"``, ``"rpId"``, ``"privateKey"``]  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential.POST.payloadParams.optional` | readonly [``"userHandle"``, ``"signCount"``] |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credential.POST.payloadParams.required` | readonly [``"credentialId"``, ``"isResidentCredential"``, ``"rpId"``, ``"privateKey"``] |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials` | { `DELETE`: { `command`: ``"removeAllAuthCredentials"`` = 'removeAllAuthCredentials' } ; `GET`: { `command`: ``"getAuthCredential"`` = 'getAuthCredential' }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials.DELETE` | { `command`: ``"removeAllAuthCredentials"`` = 'removeAllAuthCredentials' } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials.DELETE.command` | ``"removeAllAuthCredentials"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials.GET` | { `command`: ``"getAuthCredential"`` = 'getAuthCredential' } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials.GET.command` | ``"getAuthCredential"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials/:credentialId` | { `DELETE`: { `command`: ``"removeAuthCredential"`` = 'removeAuthCredential' }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials/:credentialId.DELETE` | { `command`: ``"removeAuthCredential"`` = 'removeAuthCredential' } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/credentials/:credentialId.DELETE.command` | ``"removeAuthCredential"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/uv` | { `POST`: { `command`: ``"setUserAuthVerified"`` = 'setUserAuthVerified'; `payloadParams`: { `required`: readonly [``"isUserVerified"``]  }  }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/uv.POST` | { `command`: ``"setUserAuthVerified"`` = 'setUserAuthVerified'; `payloadParams`: { `required`: readonly [``"isUserVerified"``]  }  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/uv.POST.command` | ``"setUserAuthVerified"`` |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/uv.POST.payloadParams` | { `required`: readonly [``"isUserVerified"``]  } |
| `/session/:sessionId/webauthn/authenticator/:authenticatorId/uv.POST.payloadParams.required` | readonly [``"isUserVerified"``] |
| `/session/:sessionId/window` | { `DELETE`: { `command`: ``"closeWindow"`` = 'closeWindow' } ; `GET`: { `command`: ``"getWindowHandle"`` = 'getWindowHandle' } ; `POST`: { `command`: ``"setWindow"`` = 'setWindow'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"name"``, ``"handle"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"name\" or \"handle\" to be set"``  }  }  } |
| `/session/:sessionId/window.DELETE` | { `command`: ``"closeWindow"`` = 'closeWindow' } |
| `/session/:sessionId/window.DELETE.command` | ``"closeWindow"`` |
| `/session/:sessionId/window.GET` | { `command`: ``"getWindowHandle"`` = 'getWindowHandle' } |
| `/session/:sessionId/window.GET.command` | ``"getWindowHandle"`` |
| `/session/:sessionId/window.POST` | { `command`: ``"setWindow"`` = 'setWindow'; `payloadParams`: { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"name"``, ``"handle"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"name\" or \"handle\" to be set"``  }  } |
| `/session/:sessionId/window.POST.command` | ``"setWindow"`` |
| `/session/:sessionId/window.POST.payloadParams` | { `makeArgs`: (`jsonObj`: `any`) => `any`[] ; `optional`: readonly [``"name"``, ``"handle"``] ; `validate`: (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"name\" or \"handle\" to be set"``  } |
| `/session/:sessionId/window.POST.payloadParams.makeArgs` | (`jsonObj`: `any`) => `any`[] |
| `/session/:sessionId/window.POST.payloadParams.optional` | readonly [``"name"``, ``"handle"``] |
| `/session/:sessionId/window.POST.payloadParams.validate` | (`jsonObj`: `any`) => ``false`` \| ``"we require one of \"name\" or \"handle\" to be set"`` |
| `/session/:sessionId/window/:windowhandle/maximize` | { `POST`: { `command`: ``"maximizeWindow"`` = 'maximizeWindow' }  } |
| `/session/:sessionId/window/:windowhandle/maximize.POST` | { `command`: ``"maximizeWindow"`` = 'maximizeWindow' } |
| `/session/:sessionId/window/:windowhandle/maximize.POST.command` | ``"maximizeWindow"`` |
| `/session/:sessionId/window/:windowhandle/position` | { `GET`: { `deprecated`: ``true`` = true } ; `POST`: { `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/window/:windowhandle/position.GET` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/window/:windowhandle/position.GET.deprecated` | ``true`` |
| `/session/:sessionId/window/:windowhandle/position.POST` | { `deprecated`: ``true`` = true } |
| `/session/:sessionId/window/:windowhandle/position.POST.deprecated` | ``true`` |
| `/session/:sessionId/window/:windowhandle/size` | { `GET`: { `command`: ``"getWindowSize"`` = 'getWindowSize'; `deprecated`: ``true`` = true }  } |
| `/session/:sessionId/window/:windowhandle/size.GET` | { `command`: ``"getWindowSize"`` = 'getWindowSize'; `deprecated`: ``true`` = true } |
| `/session/:sessionId/window/:windowhandle/size.GET.command` | ``"getWindowSize"`` |
| `/session/:sessionId/window/:windowhandle/size.GET.deprecated` | ``true`` |
| `/session/:sessionId/window/fullscreen` | { `POST`: { `command`: ``"fullScreenWindow"`` = 'fullScreenWindow' }  } |
| `/session/:sessionId/window/fullscreen.POST` | { `command`: ``"fullScreenWindow"`` = 'fullScreenWindow' } |
| `/session/:sessionId/window/fullscreen.POST.command` | ``"fullScreenWindow"`` |
| `/session/:sessionId/window/handle` | { `GET`: { `command`: ``"getWindowHandle"`` = 'getWindowHandle' }  } |
| `/session/:sessionId/window/handle.GET` | { `command`: ``"getWindowHandle"`` = 'getWindowHandle' } |
| `/session/:sessionId/window/handle.GET.command` | ``"getWindowHandle"`` |
| `/session/:sessionId/window/handles` | { `GET`: { `command`: ``"getWindowHandles"`` = 'getWindowHandles' }  } |
| `/session/:sessionId/window/handles.GET` | { `command`: ``"getWindowHandles"`` = 'getWindowHandles' } |
| `/session/:sessionId/window/handles.GET.command` | ``"getWindowHandles"`` |
| `/session/:sessionId/window/maximize` | { `POST`: { `command`: ``"maximizeWindow"`` = 'maximizeWindow' }  } |
| `/session/:sessionId/window/maximize.POST` | { `command`: ``"maximizeWindow"`` = 'maximizeWindow' } |
| `/session/:sessionId/window/maximize.POST.command` | ``"maximizeWindow"`` |
| `/session/:sessionId/window/minimize` | { `POST`: { `command`: ``"minimizeWindow"`` = 'minimizeWindow' }  } |
| `/session/:sessionId/window/minimize.POST` | { `command`: ``"minimizeWindow"`` = 'minimizeWindow' } |
| `/session/:sessionId/window/minimize.POST.command` | ``"minimizeWindow"`` |
| `/session/:sessionId/window/new` | { `POST`: { `command`: ``"createNewWindow"`` = 'createNewWindow'; `payloadParams`: { `optional`: readonly [``"type"``]  }  }  } |
| `/session/:sessionId/window/new.POST` | { `command`: ``"createNewWindow"`` = 'createNewWindow'; `payloadParams`: { `optional`: readonly [``"type"``]  }  } |
| `/session/:sessionId/window/new.POST.command` | ``"createNewWindow"`` |
| `/session/:sessionId/window/new.POST.payloadParams` | { `optional`: readonly [``"type"``]  } |
| `/session/:sessionId/window/new.POST.payloadParams.optional` | readonly [``"type"``] |
| `/session/:sessionId/window/rect` | { `GET`: { `command`: ``"getWindowRect"`` = 'getWindowRect' } ; `POST`: { `command`: ``"setWindowRect"`` = 'setWindowRect'; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``, ``"width"``, ``"height"``]  }  }  } |
| `/session/:sessionId/window/rect.GET` | { `command`: ``"getWindowRect"`` = 'getWindowRect' } |
| `/session/:sessionId/window/rect.GET.command` | ``"getWindowRect"`` |
| `/session/:sessionId/window/rect.POST` | { `command`: ``"setWindowRect"`` = 'setWindowRect'; `payloadParams`: { `required`: readonly [``"x"``, ``"y"``, ``"width"``, ``"height"``]  }  } |
| `/session/:sessionId/window/rect.POST.command` | ``"setWindowRect"`` |
| `/session/:sessionId/window/rect.POST.payloadParams` | { `required`: readonly [``"x"``, ``"y"``, ``"width"``, ``"height"``]  } |
| `/session/:sessionId/window/rect.POST.payloadParams.required` | readonly [``"x"``, ``"y"``, ``"width"``, ``"height"``] |
| `/session/:sessionId/window_handle` | { `GET`: { `command`: ``"getWindowHandle"`` = 'getWindowHandle' }  } |
| `/session/:sessionId/window_handle.GET` | { `command`: ``"getWindowHandle"`` = 'getWindowHandle' } |
| `/session/:sessionId/window_handle.GET.command` | ``"getWindowHandle"`` |
| `/session/:sessionId/window_handles` | { `GET`: { `command`: ``"getWindowHandles"`` = 'getWindowHandles' }  } |
| `/session/:sessionId/window_handles.GET` | { `command`: ``"getWindowHandles"`` = 'getWindowHandles' } |
| `/session/:sessionId/window_handles.GET.command` | ``"getWindowHandles"`` |
| `/sessions` | { `GET`: { `command`: ``"getSessions"`` = 'getSessions' }  } |
| `/sessions.GET` | { `command`: ``"getSessions"`` = 'getSessions' } |
| `/sessions.GET.command` | ``"getSessions"`` |
| `/status` | { `GET`: { `command`: ``"getStatus"`` = 'getStatus' }  } |
| `/status.GET` | { `command`: ``"getStatus"`` = 'getStatus' } |
| `/status.GET.command` | ``"getStatus"`` |
| `session/:sessionId/element/:elementId/computedlabel` | { `GET`: { `command`: ``"getComputedLabel"`` = 'getComputedLabel' }  } |
| `session/:sessionId/element/:elementId/computedlabel.GET` | { `command`: ``"getComputedLabel"`` = 'getComputedLabel' } |
| `session/:sessionId/element/:elementId/computedlabel.GET.command` | ``"getComputedLabel"`` |
| `session/:sessionId/element/:elementId/computedrole` | { `GET`: { `command`: ``"getComputedRole"`` = 'getComputedRole' }  } |
| `session/:sessionId/element/:elementId/computedrole.GET` | { `command`: ``"getComputedRole"`` = 'getComputedRole' } |
| `session/:sessionId/element/:elementId/computedrole.GET.command` | ``"getComputedRole"`` |

#### Defined in

@appium/base-driver/lib/protocol/routes.js:23

___

### NO\_SESSION\_ID\_COMMANDS

• `Const` **NO\_SESSION\_ID\_COMMANDS**: `string`[]

#### Defined in

@appium/base-driver/lib/protocol/routes.js:1015

___

### PREFIXED\_APPIUM\_OPTS\_CAP

• `Const` **PREFIXED\_APPIUM\_OPTS\_CAP**: `string`

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:10

___

### PROTOCOLS

• `Const` **PROTOCOLS**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `MJSONWP` | ``"MJSONWP"`` |
| `W3C` | ``"W3C"`` |

#### Defined in

@appium/base-driver/lib/constants.ts:13

___

### STANDARD\_CAPS

• `Const` **STANDARD\_CAPS**: `Readonly`<`Set`<keyof [`StandardCapabilities`](../interfaces/appium_types.StandardCapabilities.md)\>\>

Standard, non-prefixed capabilities

**`See`**

https://www.w3.org/TR/webdriver/#dfn-table-of-standard-capabilities)

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:100

___

### STATIC\_DIR

• **STATIC\_DIR**: `string`

#### Defined in

@appium/base-driver/lib/express/static.js:7

___

### W3C\_ELEMENT\_KEY

• `Const` **W3C\_ELEMENT\_KEY**: ``"element-6066-11e4-a52e-4f735466cecf"``

#### Defined in

@appium/base-driver/lib/constants.ts:12

___

### errors

• `Const` **errors**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `BadParametersError` | typeof `BadParametersError` |
| `ElementClickInterceptedError` | typeof `ElementClickInterceptedError` |
| `ElementIsNotSelectableError` | typeof `ElementIsNotSelectableError` |
| `ElementNotInteractableError` | typeof `ElementNotInteractableError` |
| `ElementNotVisibleError` | typeof `ElementNotVisibleError` |
| `IMEEngineActivationFailedError` | typeof `IMEEngineActivationFailedError` |
| `IMENotAvailableError` | typeof `IMENotAvailableError` |
| `InsecureCertificateError` | typeof `InsecureCertificateError` |
| `InvalidArgumentError` | typeof `InvalidArgumentError` |
| `InvalidContextError` | typeof `InvalidContextError` |
| `InvalidCookieDomainError` | typeof `InvalidCookieDomainError` |
| `InvalidCoordinatesError` | typeof `InvalidCoordinatesError` |
| `InvalidElementCoordinatesError` | typeof `InvalidElementCoordinatesError` |
| `InvalidElementStateError` | typeof `InvalidElementStateError` |
| `InvalidSelectorError` | typeof `InvalidSelectorError` |
| `JavaScriptError` | typeof `JavaScriptError` |
| `MoveTargetOutOfBoundsError` | typeof `MoveTargetOutOfBoundsError` |
| `NoAlertOpenError` | typeof `NoAlertOpenError` |
| `NoSuchAlertError` | typeof `NoSuchAlertError` |
| `NoSuchContextError` | typeof `NoSuchContextError` |
| `NoSuchCookieError` | typeof `NoSuchCookieError` |
| `NoSuchDriverError` | typeof `NoSuchDriverError` |
| `NoSuchElementError` | typeof `NoSuchElementError` |
| `NoSuchFrameError` | typeof `NoSuchFrameError` |
| `NoSuchWindowError` | typeof `NoSuchWindowError` |
| `NotImplementedError` | typeof `NotImplementedError` |
| `NotYetImplementedError` | typeof `NotYetImplementedError` |
| `ProxyRequestError` | typeof `ProxyRequestError` |
| `ScriptTimeoutError` | typeof `ScriptTimeoutError` |
| `SessionNotCreatedError` | typeof `SessionNotCreatedError` |
| `StaleElementReferenceError` | typeof `StaleElementReferenceError` |
| `TimeoutError` | typeof `TimeoutError` |
| `UnableToCaptureScreen` | typeof `UnableToCaptureScreen` |
| `UnableToSetCookieError` | typeof `UnableToSetCookieError` |
| `UnexpectedAlertOpenError` | typeof `UnexpectedAlertOpenError` |
| `UnknownCommandError` | typeof `UnknownCommandError` |
| `UnknownError` | typeof `UnknownError` |
| `UnknownMethodError` | typeof `UnknownMethodError` |
| `UnsupportedOperationError` | typeof `UnsupportedOperationError` |
| `XPathLookupError` | typeof `XPathLookupError` |

#### Defined in

@appium/base-driver/lib/protocol/errors.js:938

___

### statusCodes

• `Const` **statusCodes**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `ElementIsNotSelectable` | { `code`: `number` = 15; `summary`: `string` = 'An attempt was made to select an element that cannot be selected.' } |
| `ElementIsNotSelectable.code` | `number` |
| `ElementIsNotSelectable.summary` | `string` |
| `ElementNotVisible` | { `code`: `number` = 11; `summary`: `string` = 'An element command could not be completed because the element is not visible on the page.' } |
| `ElementNotVisible.code` | `number` |
| `ElementNotVisible.summary` | `string` |
| `IMEEngineActivationFailed` | { `code`: `number` = 31; `summary`: `string` = 'An IME engine could not be started.' } |
| `IMEEngineActivationFailed.code` | `number` |
| `IMEEngineActivationFailed.summary` | `string` |
| `IMENotAvailable` | { `code`: `number` = 30; `summary`: `string` = 'IME was not available.' } |
| `IMENotAvailable.code` | `number` |
| `IMENotAvailable.summary` | `string` |
| `InvalidCookieDomain` | { `code`: `number` = 24; `summary`: `string` = 'An illegal attempt was made to set a cookie under a different domain than the current page.' } |
| `InvalidCookieDomain.code` | `number` |
| `InvalidCookieDomain.summary` | `string` |
| `InvalidElementCoordinates` | { `code`: `number` = 29; `summary`: `string` = 'The coordinates provided to an interactions operation are invalid.' } |
| `InvalidElementCoordinates.code` | `number` |
| `InvalidElementCoordinates.summary` | `string` |
| `InvalidElementState` | { `code`: `number` = 12; `summary`: `string` = 'An element command could not be completed because the element is in an invalid state (e.g. attempting to click a disabled element).' } |
| `InvalidElementState.code` | `number` |
| `InvalidElementState.summary` | `string` |
| `InvalidSelector` | { `code`: `number` = 32; `summary`: `string` = 'Argument was an invalid selector (e.g. XPath/CSS).' } |
| `InvalidSelector.code` | `number` |
| `InvalidSelector.summary` | `string` |
| `JavaScriptError` | { `code`: `number` = 17; `summary`: `string` = 'An error occurred while executing user supplied JavaScript.' } |
| `JavaScriptError.code` | `number` |
| `JavaScriptError.summary` | `string` |
| `MoveTargetOutOfBounds` | { `code`: `number` = 34; `summary`: `string` = 'Target provided for a move action is out of bounds.' } |
| `MoveTargetOutOfBounds.code` | `number` |
| `MoveTargetOutOfBounds.summary` | `string` |
| `NoAlertOpenError` | { `code`: `number` = 27; `summary`: `string` = 'An attempt was made to operate on a modal dialog when one was not open.' } |
| `NoAlertOpenError.code` | `number` |
| `NoAlertOpenError.summary` | `string` |
| `NoSuchContext` | { `code`: `number` = 35; `summary`: `string` = 'No such context found.' } |
| `NoSuchContext.code` | `number` |
| `NoSuchContext.summary` | `string` |
| `NoSuchDriver` | { `code`: `number` = 6; `summary`: `string` = 'A session is either terminated or not started' } |
| `NoSuchDriver.code` | `number` |
| `NoSuchDriver.summary` | `string` |
| `NoSuchElement` | { `code`: `number` = 7; `summary`: `string` = 'An element could not be located on the page using the given search parameters.' } |
| `NoSuchElement.code` | `number` |
| `NoSuchElement.summary` | `string` |
| `NoSuchFrame` | { `code`: `number` = 8; `summary`: `string` = 'A request to switch to a frame could not be satisfied because the frame could not be found.' } |
| `NoSuchFrame.code` | `number` |
| `NoSuchFrame.summary` | `string` |
| `NoSuchWindow` | { `code`: `number` = 23; `summary`: `string` = 'A request to switch to a different window could not be satisfied because the window could not be found.' } |
| `NoSuchWindow.code` | `number` |
| `NoSuchWindow.summary` | `string` |
| `ScriptTimeout` | { `code`: `number` = 28; `summary`: `string` = 'A script did not complete before its timeout expired.' } |
| `ScriptTimeout.code` | `number` |
| `ScriptTimeout.summary` | `string` |
| `SessionNotCreatedException` | { `code`: `number` = 33; `summary`: `string` = 'A new session could not be created.' } |
| `SessionNotCreatedException.code` | `number` |
| `SessionNotCreatedException.summary` | `string` |
| `StaleElementReference` | { `code`: `number` = 10; `summary`: `string` = 'An element command failed because the referenced element is no longer attached to the DOM.' } |
| `StaleElementReference.code` | `number` |
| `StaleElementReference.summary` | `string` |
| `Success` | { `code`: `number` = 0; `summary`: `string` = 'The command executed successfully.' } |
| `Success.code` | `number` |
| `Success.summary` | `string` |
| `Timeout` | { `code`: `number` = 21; `summary`: `string` = 'An operation did not complete before its timeout expired.' } |
| `Timeout.code` | `number` |
| `Timeout.summary` | `string` |
| `UnableToSetCookie` | { `code`: `number` = 25; `summary`: `string` = "A request to set a cookie's value could not be satisfied." } |
| `UnableToSetCookie.code` | `number` |
| `UnableToSetCookie.summary` | `string` |
| `UnexpectedAlertOpen` | { `code`: `number` = 26; `summary`: `string` = 'A modal dialog was open, blocking this operation' } |
| `UnexpectedAlertOpen.code` | `number` |
| `UnexpectedAlertOpen.summary` | `string` |
| `UnknownCommand` | { `code`: `number` = 9; `summary`: `string` = 'The requested resource could not be found, or a request was received using an HTTP method that is not supported by the mapped resource.' } |
| `UnknownCommand.code` | `number` |
| `UnknownCommand.summary` | `string` |
| `UnknownError` | { `code`: `number` = 13; `summary`: `string` = 'An unknown server-side error occurred while processing the command.' } |
| `UnknownError.code` | `number` |
| `UnknownError.summary` | `string` |
| `XPathLookupError` | { `code`: `number` = 19; `summary`: `string` = 'An error occurred while searching for an element by XPath.' } |
| `XPathLookupError.code` | `number` |
| `XPathLookupError.summary` | `string` |

#### Defined in

@appium/base-driver/lib/jsonwp-status/status.js:3

## Functions

### checkParams

▸ **checkParams**(`paramSets`, `jsonObj`, `protocol`): `void`

#### Parameters

| Name | Type |
| :------ | :------ |
| `paramSets` | `any` |
| `jsonObj` | `any` |
| `protocol` | `any` |

#### Returns

`void`

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:101

___

### determineProtocol

▸ **determineProtocol**(`createSessionArgs`): ``"MJSONWP"`` \| ``"W3C"``

#### Parameters

| Name | Type |
| :------ | :------ |
| `createSessionArgs` | `any` |

#### Returns

``"MJSONWP"`` \| ``"W3C"``

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:25

___

### errorFromCode

▸ **errorFromCode**(`code`, `value?`): `ProtocolError`

Retrieve an error derived from MJSONWP status

#### Parameters

| Name | Type | Default value | Description |
| :------ | :------ | :------ | :------ |
| `code` | `number` | `undefined` | JSONWP status code |
| `value` | `any` | `''` | The error message, or an object with a `message` property |

#### Returns

`ProtocolError`

The error that is associated with provided JSONWP status code

#### Defined in

@appium/base-driver/lib/protocol/errors.js:1037

___

### errorFromW3CJsonCode

▸ **errorFromW3CJsonCode**(`code`, `message`, `stacktrace?`): `ProtocolError`

Retrieve an error derived from W3C JSON Code

#### Parameters

| Name | Type | Default value | Description |
| :------ | :------ | :------ | :------ |
| `code` | `string` | `undefined` | W3C error string (see https://www.w3.org/TR/webdriver/#handling-errors `JSON Error Code` column) |
| `message` | `string` | `undefined` | the error message |
| `stacktrace` | ``null`` \| `string` | `null` | an optional error stacktrace |

#### Returns

`ProtocolError`

The error that is associated with the W3C error string

#### Defined in

@appium/base-driver/lib/protocol/errors.js:1056

___

### getSummaryByCode

▸ **getSummaryByCode**(`code`): `string`

#### Parameters

| Name | Type |
| :------ | :------ |
| `code` | `any` |

#### Returns

`string`

#### Defined in

@appium/base-driver/lib/jsonwp-status/status.js:117

___

### isErrorType

▸ **isErrorType**<`T`\>(`err`, `type`): err is T

Type guard to check if an Error is of a specific type

#### Type parameters

| Name | Type |
| :------ | :------ |
| `T` | extends `Error` |

#### Parameters

| Name | Type |
| :------ | :------ |
| `err` | `any` |
| `type` | [`Class`](appium_types.md#class)<`T`, `object`, `any`[]\> |

#### Returns

err is T

#### Defined in

@appium/base-driver/lib/protocol/errors.js:1011

___

### isSessionCommand

▸ **isSessionCommand**(`command`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `command` | `any` |

#### Returns

`boolean`

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:44

___

### isStandardCap

▸ **isStandardCap**(`cap`): `boolean`

#### Parameters

| Name | Type |
| :------ | :------ |
| `cap` | `string` |

#### Returns

`boolean`

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:123

___

### makeArgs

▸ **makeArgs**(`requestParams`, `jsonObj`, `payloadParams`, `protocol`): `any`

#### Parameters

| Name | Type |
| :------ | :------ |
| `requestParams` | `any` |
| `jsonObj` | `any` |
| `payloadParams` | `any` |
| `protocol` | `any` |

#### Returns

`any`

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:169

___

### normalizeBasePath

▸ **normalizeBasePath**(`basePath`): `string`

Normalize base path string

#### Parameters

| Name | Type |
| :------ | :------ |
| `basePath` | `string` |

#### Returns

`string`

#### Defined in

@appium/base-driver/lib/express/server.js:290

___

### processCapabilities

▸ **processCapabilities**<`C`, `W3CCaps`\>(`w3cCaps`, `constraints?`, `shouldValidateCaps?`): [`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>

Calls parseCaps and just returns the matchedCaps variable

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](appium_types.md#constraints) |
| `W3CCaps` | extends [`W3CCapabilities`](../interfaces/appium_types.W3CCapabilities.md)<`C`, `W3CCaps`\> |

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `w3cCaps` | `W3CCaps` | `undefined` |
| `constraints?` | `C` | `undefined` |
| `shouldValidateCaps?` | `boolean` | `true` |

#### Returns

[`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:327

___

### promoteAppiumOptions

▸ **promoteAppiumOptions**<`C`\>(`originalCaps`): [`W3CCapabilities`](../interfaces/appium_types.W3CCapabilities.md)<`C`\>

Return a copy of a capabilities object which has taken everything within the 'options'
capability and promoted it to the top level.

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](appium_types.md#constraints) |

#### Parameters

| Name | Type |
| :------ | :------ |
| `originalCaps` | [`W3CCapabilities`](../interfaces/appium_types.W3CCapabilities.md)<`C`\> |

#### Returns

[`W3CCapabilities`](../interfaces/appium_types.W3CCapabilities.md)<`C`\>

the capabilities with 'options' promoted if necessary

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:422

___

### promoteAppiumOptionsForObject

▸ **promoteAppiumOptionsForObject**<`C`\>(`obj`): `Partial`<[`CapsToNSCaps`](appium_types.md#capstonscaps)<[`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>, ``"appium"``\>\>

Return a copy of a "bare" (single-level, non-W3C) capabilities object which has taken everything
within the 'appium:options' capability and promoted it to the top level.

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](appium_types.md#constraints) |

#### Parameters

| Name | Type |
| :------ | :------ |
| `obj` | `Partial`<[`CapsToNSCaps`](appium_types.md#capstonscaps)<[`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>, ``"appium"``\>\> |

#### Returns

`Partial`<[`CapsToNSCaps`](appium_types.md#capstonscaps)<[`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>, ``"appium"``\>\>

the capabilities with 'options' promoted if necessary

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:360

___

### routeConfiguringFunction

▸ **routeConfiguringFunction**(`driver`): `RouteConfiguringFunction`

#### Parameters

| Name | Type |
| :------ | :------ |
| `driver` | [`Core`](../interfaces/appium_types.Core.md)<`any`, [`StringRecord`](appium_types.md#stringrecord)\> |

#### Returns

`RouteConfiguringFunction`

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:257

___

### routeToCommandName

▸ **routeToCommandName**(`endpoint`, `method`, `basePath?`): `undefined` \| `string`

#### Parameters

| Name | Type | Default value |
| :------ | :------ | :------ |
| `endpoint` | `string` | `undefined` |
| `method` | [`HTTPMethod`](appium_types.md#httpmethod) | `undefined` |
| `basePath?` | `string` | `DEFAULT_BASE_PATH` |

#### Returns

`undefined` \| `string`

#### Defined in

@appium/base-driver/lib/protocol/routes.js:978

___

### server

▸ **server**(`opts`): `Promise`<[`AppiumServer`](appium_types.md#appiumserver)\>

#### Parameters

| Name | Type |
| :------ | :------ |
| `opts` | [`ServerOpts`](../interfaces/appium_base_driver.ServerOpts.md) |

#### Returns

`Promise`<[`AppiumServer`](appium_types.md#appiumserver)\>

#### Defined in

@appium/base-driver/lib/express/server.js:78

___

### validateCaps

▸ **validateCaps**<`C`\>(`caps`, `constraints?`, `opts?`): [`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>

Validates caps against a set of constraints

#### Type parameters

| Name | Type |
| :------ | :------ |
| `C` | extends [`Constraints`](appium_types.md#constraints) |

#### Parameters

| Name | Type |
| :------ | :------ |
| `caps` | [`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\> |
| `constraints?` | `C` |
| `opts?` | `ValidateCapsOpts` |

#### Returns

[`ConstraintsToCaps`](appium_types.md#constraintstocaps)<`C`\>

#### Defined in

@appium/base-driver/lib/basedriver/capabilities.js:54

___

### validateExecuteMethodParams

▸ **validateExecuteMethodParams**(`params`, `paramSpec`): `any`

#### Parameters

| Name | Type |
| :------ | :------ |
| `params` | `any` |
| `paramSpec` | `any` |

#### Returns

`any`

#### Defined in

@appium/base-driver/lib/protocol/protocol.js:219
