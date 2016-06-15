package com.sma8learning.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sma8learning.util.BaseTest;

public class Test_Web_Calculator extends BaseTest{
	private static final Logger LOG = LoggerFactory.getLogger(Test_Web_Calculator.class);


	@FindBy(name="=")
	public static WebElement equalTo; //pagefactory with iosdriver somehow working only for static elements

	@FindBy(id="result")
	public static WebElement resultTextField; //TO CHECK::pagefactory with RemoteWebDriver somehow working only for static elements
	
	@FindBy(name="clear")
	public static WebElement clearField;

	@Parameters({ "input1", "input2", "operator" })
	@Test(dataProvider = "web_input")
	public void testWeb_Calulator_One_digitNumbers(String input1, String input2, String operator) {
		PageFactory.initElements(driver, Test_Web_Calculator.class);
		click("id", input1);   //can write logic to support more input like more than 1 digits as per test case
		click("id", operator);
		click("id", input2);
		equalTo.click();

		LOG.info("performing operation"+SEPARATOR+input1+operator+input2);
		sleep(500);  //work around for chrome -> viewport is not ready, so throws staleException
		double actualResult = Float.parseFloat(resultTextField.getAttribute("value"));
		double expectedResult = getExpectedResult(input1, input2, operator);

		LOG.debug("actual result="+SEPARATOR+actualResult);
		LOG.debug("expected result="+SEPARATOR+expectedResult);
		Assert.assertEquals(actualResult,expectedResult);
		LOG.info("-------------- Web Calculator TEST PASSED for "+client+" ----------------------");
		clearField.click();

	}


	@BeforeTest
	public void openPage(){
		driver.get(WEB_SERVER);
	}

	@DataProvider(name = "web_input")
	public static Object[][] primeNumbers() {
		return new Object[][] {
				{"3", "7","+"}, 
				{"6", "5","−"},
				{"3", "9","×"},
				{"8","2","÷"}
		};
	}

}
