package com.sma8learning.tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sma8learning.util.BaseTest;
public class Test_IOS_Calculator extends BaseTest {


	@FindBy(name="=")
	public static WebElement equalTo; //pagefactory with iosdriver somehow working only for static elements

	@Parameters({ "input1", "input2", "operator" })
	@Test(dataProvider = "inputs")
	public void testIOS_Calulator_One_digitNumbers(String input1, String input2, String operator) {
		PageFactory.initElements(driver, Test_IOS_Calculator.class);
		click("name", input1);
		click("name", operator);
		click("name", input2);
		equalTo.click();

		System.out.println("perforing operation");
		double actualResult = Integer.parseInt( driver.findElementsByClassName("UIATextField").get(0).getText());
		double expectedResult = getExpectedResult(input1, input2, operator);

		System.out.println("actual result="+actualResult);
		System.out.println("expected result="+expectedResult);
		Assert.assertEquals(actualResult,expectedResult);
		System.out.println("-------------- IOS Calculator TEST PASSED -----------");

	}



	// can add more test functions to test for multiplication etc. 


	@DataProvider(name = "inputs")
	public static Object[][] primeNumbers() {
		return new Object[][] {{"3", "7","+"},
				{"6", "5","-"}};
	}
}
