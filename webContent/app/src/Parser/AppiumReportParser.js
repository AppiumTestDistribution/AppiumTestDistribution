import _ from 'lodash'

export default function parse(testData) {

    let result = []
    _.map(testData, (data) => {
        _.forEach(data.testCases, (test) => {
            let existingTest = result.find((value) => {
                return value.name === test.testCase
            })
            if (_.isNil(existingTest)) {
                let name = test.testCase
                let testMethods = []
                _.forEach(test.testMethod, (method) => {
                    testMethods.push(
                        {
                            "methodName": method.methodName,
                            "info": {
                                "deviceName": data.deviceName,
                                "deviceModel": data.deviceModel,
                                "deviceUDID": data.deviceUDID,
                                "deviceOS":data.deviceOS
                            },
                            "screenShots": method.screenShots
                        }
                    )
                })
                result.push({ name, testMethods })
            } else {
                _.forEach(test.testMethod, (method) => {
                    existingTest.testMethods.push(
                        {
                            "methodName": method.methodName,
                            "info": {
                                "deviceName": data.deviceName,
                                "deviceModel": data.deviceModel,
                                "deviceUDID": data.deviceUDID,
                                 "deviceOS":data.deviceOS
                            },
                            "screenShots": method.screenShots
                        }
                    )
                })

            }
        })

    })

    return result
}