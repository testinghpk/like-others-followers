package com.testing;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriver_Shiba {
	
	public static synchronized boolean isElementVisible(By by,int timeout,WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
    public static synchronized void clickUsingJS(String xpath, WebDriver driver) {
		WebElement element = driver.findElement(By.xpath(xpath));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click()", element);
	}
	
	public static long getMin() {
		int min = 150000;
		return min;
	}
	
	public static long getMax() {
		int max = 235000;
		return max;
	}
	
	public static void delay(long delaySec) {
		try {
			Thread.sleep(delaySec * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	public static long resetTime() {
		long randomNo = (long)(Math.random()*(getMax()-getMin()+1)+getMin());
		return randomNo;
	}
	
	public static void watchYoutube(String url, WebDriver driver) throws InterruptedException {
		driver.get(url);
		driver.findElement(By.xpath("//button[@title='Play (k)']")).click();
		System.out.println("Now sleep...");
	}
	
	public static void endWatchingYouTube(WebDriver driver) {
		driver.quit();
	}
	
	public static void login(String url, String userID, String pw, WebDriver driver) throws InterruptedException {
		driver.get(url);
		delay(5);
		driver.findElement(By.xpath("//input[@name='username']")).click();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(userID);
		driver.findElement(By.xpath("//input[@name='password']")).click();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(pw);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		if (isElementVisible(By.xpath("//button[contains(.,'Not now')]"),3,driver)) {
			driver.findElement(By.xpath("//button[contains(.,'Not now')]")).click();
		}
	}
	
	public static void goToShibaKOL(String url,WebDriver driver) {
		driver.get(url);
		delay(5);
	}
	

	public static void goToProfileAndLike() {
		// TODO Auto-generated method stub
		
	}
	
	public static void goToLatestWindow(WebDriver driver) {
		for(String winHandle : driver.getWindowHandles()){
			System.out.println(driver.getTitle());
			driver.switchTo().window(winHandle);
		}
	}
	
	public static void goToFollowerList(String KOLusername, WebDriver driver) {
		//Open followers prompt
		driver.findElement(By.xpath("//a[@href='/" + KOLusername + "/followers/']")).click();
		delay(5);
		
		//Get on the list
		WebElement followerList = driver.findElement(By.xpath("//div[@class='isgrP']"));
		
		//Find list and scroll
		int j=1;
		int k=10;
		while (j<k) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", followerList);
			delay(3);
			System.out.println("Scrolling - "+j);
			j++;
		}
        
		//	Next tasks
		//	1. Get the followers number
		//	2. Scroll to end to get full list
		
        //Loop over all Followers and click like if user's profile is not private 
        for (int i=1; i<100; i++) {
			driver.findElement(By.xpath("//li[@class='wo9IH'][" + i + "]/child::*/child::*/child::*[2]/child::*/child::*/child::*")).click();
			delay(3);
			if (isElementVisible(By.xpath("//div[@class='Nnq7C weEfm']/parent::*/child::*[1]/child::*[1]/child::*"),5,driver)) {		
				//Click on first photo
				driver.findElement(By.xpath("//div[@class='Nnq7C weEfm']/parent::*/child::*[1]/child::*[1]/child::*")).click();
				delay(3);
				//Click on Like
				driver.findElement(By.xpath("//span[@class='fr66n']/child::*")).click();
				System.out.println("Liked the " + i + "th follower....");
				delay(3);
				//Return to profile and wait for 6 minutes
				driver.navigate().back();
				delay(360);
			}
			//Return to Followers list
			driver.navigate().back();
			delay(3);

        }
        
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		
		//Chrome Setting
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		
		//Set YouTube URL
//		String FRBot_YT_url = "https://youtu.be/huKL4ItDeRc";
	
		//Set Instagram URL, Username and password
		String url = "https://instagram.com/";
		String userID = "theshibainunation";
		String pw = "Shiba@2022@";
		String KOLusername = "marutaro";

		//Start Following
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		login(url,userID,pw,driver);
		goToShibaKOL(url+KOLusername,driver);
		goToFollowerList(KOLusername, driver);
		goToProfileAndLike();
		
//		for (int i = 0; i < 100; i++) {
//			for (int j = 0; j < 100; j++) {
//				WebDriver driver = new ChromeDriver(options);
//				watchYoutube(FRBot_YT_url,driver);
//				driver.quit();
//				resetTime();
//			}
//		}
		
		
		
	}


}

