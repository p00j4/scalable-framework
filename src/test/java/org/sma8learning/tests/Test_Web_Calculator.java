package org.sma8learning.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sma8learning.util.BaseTest;
import org.sma8learning.util.UiUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test_Web_Calculator extends BaseTest{
	private static final Logger LOG = LoggerFactory.getLogger(Test_Web_Calculator.class);


	@FindBy(name="=")
	public static WebElement equalTo; //pagefactory with iosdriver somehow working only for static elements

	@FindBy(id="result")
	public static WebElement resultTextField; //TO CHECK::pagefactory with RemoteWebDriver somehow working only for static elements
    
	public static By resultTextFieldBy = By.id("result");
	
	
	@FindBy(name="clear")
	public static WebElement clearField;

	@Parameters({ "input1", "input2", "operator" })
	@Test(dataProvider = "web_input")
	public void testWeb_Calulator(String tcId,String input1, String input2, String operator) {
		this.tcId = tcId;
		reset();
		calculate(input1, input2, operator);
		validate(input1, input2, operator);
		LOG.info("-------------- Web Calculator TEST PASSED for "+client+" ----------------------");
	}
	
	
	@BeforeTest
	public void openPage(){
		PageFactory.initElements(driver, Test_Web_Calculator.class);
		driver.get(WEB_SERVER);
	}


	public void calculate(String input1, String input2, String operator){
		click("id", input1);   //can write logic to support more input like more than 1 digits as per test case
		click("id", operator);
		click("id", input2);
		equalTo.click();
	}

	public void validate(String input1, String input2, String operator){
		LOG.info("performing operation"+SEPARATOR+input1+operator+input2);
		waitForElementPresent(resultTextFieldBy, 1000);//work around for chrome -> viewport is not ready, so throws staleException
		double actualResult = Double.parseDouble(resultTextField.getAttribute("value"));
		double expectedResult = getExpectedResult(input1, input2, operator);

		LOG.debug("actual result="+SEPARATOR+actualResult);
		LOG.debug("expected result="+SEPARATOR+expectedResult);
		Assert.assertEquals(actualResult,expectedResult);
		if(client.equalTo("chrome")){
			LOG.debug("chrome browser with ::::");
			Assert.assertEquals(actualResult,expectedResult+0.00000);
		}
	}



	@DataProvider(name = "web_input")
	public static Object[][] primeNumbers() {
		return new Object[][] {
				{"tc1_addition","3", "7","+"}, 
				{"tc2_sutraction","6", "5","−"},
				{"tc3_multiplication","3", "9","×"},
				{"tc4_division","8","2","÷"}
		};
	}

	void reset(){
		clearField.click();
	}

}
