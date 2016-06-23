package org.sma8learning.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class BaseTest extends UiUtils{
	private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);
	public static String PATH_DRIVER_CHROME ="src/main/resources/drivers/chromedriver_2.22"; //works with  chrome 51.0.2704.103 
	protected final String WEB_SERVER = System.getProperty("WEB_SERVER", "http://127.0.0.1:8000/");
	protected final String IOS_SERVER = System.getProperty("IOS_SERVER", "http://0.0.0.0:4723/wd/hub");
	protected final String ANDROID_SERVER = System.getProperty("ANDROID_SERVER", "http://0.0.0.0:4724/wd/hub");
	protected final String client = System.getProperty("client", "firefox");
	private static ChromeDriverService service;
	
	
	@Parameters({"client"})
	@BeforeSuite
	public WebDriver getDriver(String client) throws MalformedURLException {
		capabilities =  new DesiredCapabilities();
		LOG.info("****************** Launching"+client +" ******************");
		switch(client.toLowerCase()){
		case "firefox":
		case "ff":
			driver = new FirefoxDriver();
			maximise();
			break;
		case "chrome":
			//System.setProperty("webdriver.chrome.driver", PATH_DRIVER_CHROME);
			//driver = new ChromeDriver();
			try {
				service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(PATH_DRIVER_CHROME))
				.usingAnyFreePort()
				.build();
				service.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver = new RemoteWebDriver(service.getUrl(),
					DesiredCapabilities.chrome());
			Dimension dimension = new Dimension(1224,800);  //Best Resolution works for firefox with 1400*1200*16 xvfb
			driver.manage().window().setSize(dimension);
			break;
		case "android":
			capabilities.setCapability("platformVersion", "4.4");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "Android");
			capabilities.setCapability("app", "/Users/pooja/Documents/conf/apps/Calculator.apk");
			driver = new AndroidDriver(new URL(ANDROID_SERVER), capabilities);
			break;
		case "ios":
			/*** For better management, Giving from Flags  while running from source**/
			//			capabilities.setCapability("platformName", "iOS"); works with 1.5.3
			//			capabilities.setCapability("deviceName", "iPhone 6s");
			//			capabilities.setCapability("platformVersion", "9.2");
			capabilities.setCapability("app", "/Users/pooja/Documents/conf/apps/Calculator.app");
			driver = new IOSDriver(new URL(IOS_SERVER), capabilities);
			break;
		}
		implicitWait(30);
		LOG.info("Session established with"+SEPARATOR+client);
		return driver;
	}





	/**
	 * get calendar calculation expected result
	 * @param in1
	 * @param in2
	 * @param operator
	 * @return
	 */
	public double getExpectedResult(String in1, String in2, String operator) {
		double input1 = Double.parseDouble(in1);
		double input2 = Double.parseDouble(in2);
		switch (operator) {
		case "+":
			return input1 + input2;

		case "-":
		case "−":
			return input1 - input2;

		case "*":
		case "×":
			return input1 * input2;

		case "/":
		case "÷":
			return input1 / input2;

		case "%":
			return input1 % input2;

		default:
			LOG.debug("Given Operator case didn't match any, check test case again, operator="+operator);
		}
		return -111111; // return weird
	}



	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if(ITestResult.FAILURE==result.getStatus()){
			captureScreenshot(driver, result.getMethod().getMethodName());
		}
	}


	@AfterClass(alwaysRun = true)
	public void suiteTearDown() {
		driver.quit();
	}
}
