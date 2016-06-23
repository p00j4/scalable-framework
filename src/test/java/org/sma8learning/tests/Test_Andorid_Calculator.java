package org.sma8learning.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sma8learning.util.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
public class Test_Andorid_Calculator extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(Test_Andorid_Calculator.class);

	@FindBy(id="com.android2.calculator3:id/eq")
	public static WebElement equalTo; 
	@FindBy(xpath="//android.view.View[2]/android.widget.Button[1]") //clr, del 2 ids
	public static WebElement clearBtn; 
	@FindBy(id="com.android2.calculator3:id/formula")
	public static WebElement resultTextField; 

	String startDigitLocator = "com.android2.calculator3:id/digit_";
	String startOpLocator = "//android.widget.Button[@text='";
	String endOpLoactor = "']";


	@Parameters({ "input1", "input2", "operator" })
	@Test(dataProvider = "inputs")
	public void testAndroid_Calulator(String tcId,String input1, String input2, String operator) {
		load();
		this.tcId = tcId;

		reset(); 
		calculate(input1, input2, operator);
		validate(input1, input2, operator);
		logger.info(tcId+":: -------------- Android Calculator TEST PASSED -----------");

	}
	
	private void validate(String input1, String input2, String operator){
		logger.debug(tcId+":: performing operation");
		double actualResult = Double.parseDouble(resultTextField.getText());
		double expectedResult = getExpectedResult(input1, input2, operator);

		logger.debug(tcId+":: actual result="+actualResult);
		logger.debug(tcId+":: expected result="+expectedResult);
		Assert.assertEquals(actualResult,expectedResult);
	}




	// TO add more test functions to test for more calculations to support nth digit numbers


	@BeforeTest
	private void load(){
		PageFactory.initElements(driver, Test_Andorid_Calculator.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***************** Implementation : To Do:: separate it out more from this class*********/

	private void reset(){
		clearBtn.click();
	}

	private void calculate(String input1, String input2, String operator) {
		click("id", startDigitLocator+input1);
		click("xpath", startOpLocator+operator+endOpLoactor);
		click("id", startDigitLocator+input2);
		equalTo.click();
	}


	@DataProvider(name = "inputs")
	public static Object[][] primeNumbers() {
		return new Object[][] {
				{"tc1_addition","3", "7","+"},
				{"tc2_sutraction","6", "5","−"},
				{"tc3_multiplication","4","9","×"},
//				{"tc4_division","4","3","÷"}
		};
	}
}
