package org.sma8learning.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sma8learning.util.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
public class Test_IOS_Calculator extends BaseTest {


	@FindBy(name="=")
	public static WebElement equalTo; //TO CHECK and work-around:: working only for static elements in this design

	@Parameters({ "input1", "input2", "operator" })
	@Test(dataProvider = "inputs")
	public void testIOS_Calulator(String tcId,String input1, String input2, String operator) {
		this.tcId = tcId;
		load();
		calculate(input1, input2, operator);
		validate(input1, input2, operator);
		
		System.out.println("-------------- IOS Calculator TEST PASSED -----------");

	}
	
	
	private void calculate(String input1, String input2, String operator){
		click("name", input1);
		click("name", operator);
		click("name", input2);
		equalTo.click();
	}
	
	private void validate(String input1, String input2, String operator){
		System.out.println("perforing operation");
		double actualResult = Double.parseDouble( driver.findElementsByClassName("UIATextField").get(0).getText());
		double expectedResult = getExpectedResult(input1, input2, operator);

		System.out.println("actual result="+actualResult);
		System.out.println("expected result="+expectedResult);
		Assert.assertEquals(actualResult,expectedResult);
	}
	private void reset(){
		
	}


	@BeforeTest
	public void load(){
		PageFactory.initElements(driver, Test_IOS_Calculator.class);
	}

	// can add more test functions to test for multiplication etc. 


	@DataProvider(name = "inputs")
	public static Object[][] primeNumbers() {
		return new Object[][] {
				{"tc1_addition","3", "7","+"},
				{"tc2_sutraction", "5","1","-"}};
	}
}
