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
                            "screenShots": ""
                        }
                    ]
                },
                {
                    "testCase": "TestCase2",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_3",
                            "screenShots": ""
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
                            "screenShots": ""
                        }
                    ]
                },
                ]
        }]
        let result = parse(testData)
        expect(result[0].name).toBe(testData[0].testCases[0].testCase)

    })

    it('should return testmethods with method name, device info and screenShot', () => {
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
                            "screenShots": "something"
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].methodName).toBeTruthy()
        expect(result[0].testMethods[0].info).toBeTruthy()
        expect(result[0].testMethods[0].screenShot).toBeTruthy()
    })

    it('should return device uuid in device info', () => {
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
                            "screenShots": ""
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].info.deviceUDID).toBeTruthy()
        expect(result[0].testMethods[0].info.deviceUDID).toBe('192_168_56_101_5555')
    })

    it('should return device name in device info', () => {
        let testData = [{
            "deviceName": "16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": ""
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].info.deviceName).toBeTruthy()
        expect(result[0].testMethods[0].info.deviceName).toBe('16PREVIEWGoogleNexus6P700API241440x2560')
    })

    it('should return device os in device info', () => {
        let testData = [{
            "deviceName": "16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "deviceOS": "6.0",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": ""
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].info.deviceOS).toBeTruthy()
        expect(result[0].testMethods[0].info.deviceOS).toBe('6.0')
    })

    it('should return device model in device info', () => {
        let testData = [{
            "deviceName": "16PREVIEWGoogleNexus6P700API241440x2560",
            "deviceModel": "Android",
            "deviceUDID": "192_168_56_101_5555",
            "deviceOS": "6.0",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": ""
                        }
                    ]
                }]
        }]
        let result = parse(testData)
        expect(result[0].testMethods[0].info.deviceModel).toBeTruthy()
        expect(result[0].testMethods[0].info.deviceModel).toBe('Android')
    })

    it('should return all the test methods under a test case', () => {
        let testData = [{
            "deviceName": "2017-04-26T14: 59: 11GoogleNexus5",
            "deviceModel": "generic",
            "deviceUDID": "192_168_56_102_5555",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": ""
                        }
                    ]
                },
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "com.test.site.HomePageTest3",
                            "screenShots": 
                                "/Users/saikrisv/git/new_appium_parallel/AppiumTestDistribution/target/screenshot/android/192_168_56_102_5555/LoginPage/com.test.site.HomePageTest3/2017-04-26T14: 58: 56GoogleNexus5_generic_com.test.site.HomePageTest3_results.png"
                            
                        }
                    ]
                }
            ]
        }]
        let result = parse(testData)
        expect(result[0].testMethods.length).toBe(2)
        expect(result[0].testMethods[0].methodName).toBe("testMethodOne_2")
        expect(result[0].testMethods[1].methodName).toBe("com.test.site.HomePageTest3")
    })

    it('should return all test cases with same name run on different deivces as single block', () => {
        let testData = [{
            "deviceName": "2017-04-26T14: 59: 11GoogleNexus5",
            "deviceModel": "generic",
            "deviceUDID": "192_168_56_102_5555",
            "deviceOS": "6.0",
            "testCases": [
                {
                    "testCase": "TestCase1",
                    "testMethod": [
                        {
                            "methodName": "testMethodOne_2",
                            "screenShots": ""
                        }
                    ]
                }
            ]
        }, {
            "deviceName": "GoogleNexus5",
            "deviceModel": "generic",
            "deviceUDID": "192_168_56_102_5555",
            "deviceOS": "5.0",
            "testCases": [{
                "testCase": "TestCase1",
                "testMethod": [
                    {
                        "methodName": "com.test.site.HomePageTest3",
                        "screenShots": 
                            "/Users/saikrisv/git/new_appium_parallel/AppiumTestDistribution/target/screenshot/android/192_168_56_102_5555/LoginPage/com.test.site.HomePageTest3/2017-04-26T14: 58: 56GoogleNexus5_generic_com.test.site.HomePageTest3_results.png"
                        
                    }
                ]
            }]
        }]
        let result = parse(testData)
        let block=result.find(r=>r.name==='TestCase1')
        expect(block).toBeTruthy()
        expect(block.testMethods.length).toBe(2)
    })
})