import _ from 'lodash'
import parse from '../AppiumReportParser'

describe('Appium report parser', () => {

    it('should return test name as the testcase value', () => {
        let testData = [{
            "deviceName": "2017-04-26T14: 59: 16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": []
                        }
                    ]
                },
                {
                    "testCase": "TestCase2",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_3",
                            "screenShots": []
                        }
                    ]
                }
                ]
        },
        {
            "deviceName": "2017-04-26T14: 59: 16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": []
                        }
                    ]
                },
                ]
        }]
        let result = parse(testData)
        console.log(JSON.stringify(result))
        expect(result[0].name).toBe(testData[0].testCases[0].testCase)

    })

    it('should return testmethods with method name, device info and screenshots', () => {
        let testData = [{
            "deviceName": "2017-04-26T14: 59: 16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": []
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].methodName).toBeTruthy()
        expect(result[0].testMethods[0].info).toBeTruthy()
        expect(result[0].testMethods[0].screenShots).toBeTruthy()
    })
})