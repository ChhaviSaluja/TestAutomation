/*The aim of this test is to visit the Met Office and get the difference between
humidity values for a set time of today and tomorrow for a particular city.
*/
package TestAutomationPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.util.List;

public class WeatherForecast {

	public static void main(String[] args) {
		
		//Variable Declarations
		String myUrl = "https://www.metoffice.gov.uk/";
		String myLocationInputId = "location-search-input";
		String myFavCity = "Basingstoke"; // Enter a valid city name, like Basingstoke, London, Bristol, etc.
		String myTodaysTabId = "tabDay0";
		String myTmrwsTabId = "tabDay1";
		String myHumidityDataClassName = "step-humidity";
		String myHumidityValueClassName = "humidity";
		String myCookieAcceptClassName = "as-oil__btn-optin";
		    	
		//Set the Chrome WebDriver
		System.setProperty("webdriver.chrome.driver", 
				"C:\\Drivers\\Browsers\\chromedriver_win32\\chromedriver.exe");
				
		WebDriver browser = new ChromeDriver();
		        browser.get(myUrl);
		        WebElement inputDemo = browser.findElement(By.id(myLocationInputId));
		        
		if(inputDemo.isDisplayed()) {
        	//Maximise the window
        	browser.manage().window().maximize();
		        	
        	//Accept the cookie policy.
        	browser.findElement(By.className(myCookieAcceptClassName)).click();
		        	
        	int myTimeOfDay, myTodaysHumidity, myTmrwsHumidity;
		        	
        	inputDemo.sendKeys(myFavCity);
        	inputDemo.submit();
		        	
        	//Access the weather forecast by either
        	browser.findElement(By.xpath("//a[contains(.,'" + myFavCity + " weather')]")).click();
        	//or by clicking the first link in results
        	//browser.findElements(By.className("result")).get(0).findElement(By.tagName("a")).click();
		        	
        	//Get the day-id of today's forecast
        	String myDayId = browser.findElement(By.id(myTodaysTabId))
        					 	    .getAttribute("data-tab-id");
		        	
        	List<WebElement> myHumidityData = browser.findElement(By.id(myDayId))
                    								 .findElement(By.className(myHumidityDataClassName))
                    								 .findElements(By.tagName("td"));
        	
        	//Get humidity value at current time
        	myTodaysHumidity = Integer.parseInt(myHumidityData.get(0)
					  				  .findElement(By.className(myHumidityValueClassName))
					  				  .getAttribute("data-value"));
        	
        	//Get the current hour
        	myTimeOfDay = 24 - myHumidityData.size();
        	
        	//Get the day-id of tomorrow's forecast
        	myDayId = browser.findElement(By.id(myTmrwsTabId))
			 	             .getAttribute("data-tab-id");
        	
        	myHumidityData = browser.findElement(By.id(myDayId))
                    				.findElement(By.className(myHumidityDataClassName))
                    				.findElements(By.tagName("td"));
       	
        	//Get humidity value at the same time tomorrow
        	myTmrwsHumidity = Integer.parseInt(myHumidityData.get(myTimeOfDay)
        			                                         .findElement(By.className(myHumidityValueClassName))
        			                                         .getAttribute("data-value"));
        	
        	//Calculate the difference
        	int myDiffInHumidity = Math.abs(myTodaysHumidity - myTmrwsHumidity);
        	
        	//Print results
        	System.out.println("In " + myFavCity + ", the humidity at " + 
        					   myTimeOfDay + ":00 today is " + myTodaysHumidity + "% " +
        					   "and at " + myTimeOfDay + ":00 tomorrow is " + myTmrwsHumidity + "%." +
        					   "The difference in humidity values is " + myDiffInHumidity + ".");
        }
        else {
        	System.out.println("Test Failed");
        }
        browser.close();
	}
}