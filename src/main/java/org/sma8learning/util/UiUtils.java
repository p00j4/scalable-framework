package org.sma8learning.util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UiUtils {
	public RemoteWebDriver driver;
	public DesiredCapabilities capabilities ;
	public static final String SEPARATOR = " ";

    public static String tcId = "";


	public void sleep(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * type into given field
	 * @param type
	 * @param locator
	 * @param value
	 */
	public void type(String type, String locator, String value) {
		findElement(type, locator).sendKeys(value);
	}

	/***
	 * click on given field
	 * @param type
	 * @param value
	 */
	public void click(String type, String value){
		findElement(type, value).click();
	}

	/***
	 * click on given field
	 * @param type
	 * @param value
	 */
	public String getText(String type, String value){
		return findElement(type, value).getText();
	}

	/***
	 * find and return webElement based on related locating strategy
	 * @param type
	 * @param value
	 * @return webElement
	 */
	public WebElement findElement(String type, String value) {
		WebElement ele =null;
		By by = null;
		if(type.equals("id")){
			by = By.id(value);
		}else if(type.equals("name")){
			by = By.name(value);
		}else if(type.equals("class")){
			by = By.className(value);
		}else if(type.equals("xpath")){
			by = By.xpath(value);
		}else if(type.equals("linkText")){
			by = By.linkText(value);
		}else if(type.equals("partialLinkText")){
			by = By.partialLinkText(value);
		}else if(type.equals("css")){
			by = By.cssSelector(value);
		}
		waitForElementPresent(by, Constants.ELEMENT_WAIT);
		return ele = driver.findElement(by);
	}


	/**
	 * maximise browser window
	 */
	public void maximise(){
		driver.manage().window().maximize();
	}

	/***
	 * wait for given seconds
	 * @param seconds
	 */
	public void implicitWait(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}


	public void waitForElementPresent(final By by, int timeout){ 
		WebDriverWait wait = (WebDriverWait)new WebDriverWait(driver,timeout)
		.ignoring(StaleElementReferenceException.class); 
		wait.until(new ExpectedCondition<Boolean>(){ 
			@Override 
			public Boolean apply(WebDriver webDriver) { 
				WebElement element = webDriver.findElement(by); 
				return element != null && element.isDisplayed(); 
			} 
		}); 
	}

		
	public static void captureScreenshot(WebDriver driver,String screenshotName)
	{
		try 
		{
			TakesScreenshot ts=(TakesScreenshot)driver;
			File source=ts.getScreenshotAs(OutputType.FILE);
			String path = "./build/screenshots/"+tcId+"_"+screenshotName+".png";
			FileUtils.copyFile(source, new File(path));
			System.out.println("Screenshot taken under"+ path);
		} 
		catch (Exception e)
		{

			System.out.println("Exception while taking screenshot "+e.getMessage());
		} 
	}

}
