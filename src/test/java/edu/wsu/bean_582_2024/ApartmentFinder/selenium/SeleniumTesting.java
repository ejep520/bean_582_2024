package edu.wsu.bean_582_2024.ApartmentFinder.selenium;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.NavigationResult;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.vaadin.flow.server.PWA;


public class SeleniumTesting {
	
	WebDriver driver;
	
	
	@BeforeEach
	void setUp() {
		//driver = new ChromeDriver();
		ChromeOptions chromeOptions = new ChromeOptions();
	    driver = new ChromeDriver(chromeOptions);
		/*Headless - no windows open
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		 */
	}
	
	void openPageInitial() {
		driver.get("http://localhost:8080/");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
	}
	
	void openPage() {
		driver.get("http://localhost:8080/");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));					
	}
	
	void registerWrong(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-17']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-18']"));
		WebElement confirmPassword = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-19']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		confirmPassword.sendKeys("nope");
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Send')]"));
		loginButton.click();
	}
	
	void register2(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-17']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-18']"));
		WebElement confirmPassword = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-19']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		confirmPassword.sendKeys(pass);
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Send')]"));
		loginButton.click();
	}
	
	void addUnit(String addy, int bed, double bath, String kitch, String lRoom) {
		WebElement address = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		WebElement bedrooms = driver.findElement(By.cssSelector("input[id='input-vaadin-integer-field-23']"));
		WebElement bathrooms = driver.findElement(By.cssSelector("input[id='input-vaadin-number-field-27']"));
		WebElement kitchen = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-31']"));
		WebElement livingRoom = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-35']"));
		address.sendKeys(addy);
		bedrooms.sendKeys(String.valueOf(bed));
		bathrooms.sendKeys(String.valueOf(bath));
		kitchen.sendKeys(kitch);
		livingRoom.sendKeys(lRoom);		
		WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Save')]"));
		saveButton.click();
	}
	
	void login(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[name='username']"));
		WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log in')]"));
		loginButton.click();
	}
	
	
	@Test
	void test_page_load() {
		openPageInitial();	
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	/*@Test
	void test_register() {		
		openPageInitial();
		register("admin", "password");
		WebElement titleElement = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Log in";
		assertEquals(expectedTitle, titleElement.getText());
	}
	*/
	
	@Test
	void add_new_user() {		
		openPageInitial();
		driver.findElement(By.linkText("Add a user")).click();
		register2("user", "upass");			
		WebElement titleElement = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Register";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	@Test
	void add_new_user_pass_dont_match() {		
		openPageInitial();
		driver.findElement(By.linkText("Add a user")).click();
		registerWrong("user22", "nopass");			
		WebElement titleElement = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Register";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	@Test
	void test_login_happy_path_admin() {
		openPage();
		login("admin", "password");		
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		String expectedTitle = "Log out";
		assertEquals(expectedTitle, logOutButton.getText());
	}
	
	@Test
	void test_logOut_happy_path_admin() {
		openPage();
		login("admin", "password");		
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		logOutButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	@Test
	void test_login_happy_path_user() {
		openPage();
		login("user", "upass");		
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		String expectedTitle = "Log out";
		assertEquals(expectedTitle, logOutButton.getText());
	}
	
	@Test
	void test_logOut_happy_path_user() {
		openPage();
		login("user", "upass");		
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		logOutButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
    @Test
    void test_incorrect_username(){
        openPage();
        login("wrong", "upass");
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
    }

    @Test
    void test_incorrect_password(){
        openPage();
        login("admin", "wrong");
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
    }
    
    @Test
	void test_login_admin_grid_access() {
		openPage();
		login("admin", "password");		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'04093 Hansons Road')]"));
		String expectedTitle = "04093 Hansons Road";
		assertEquals(expectedTitle, titleElement.getText());
	}
    
    @Test
 	void test_login_user_grid_access() {
 		openPage();
 		login("user", "upass");		
 		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
 		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'04093 Hansons Road')]"));
 		String expectedTitle = "04093 Hansons Road";
 		assertEquals(expectedTitle, titleElement.getText());
 	}
 	
    @Test
	void test_login_admin_add_unit() {
		openPage();
		login("admin", "password");
		driver.findElement(By.linkText("Unit Editing")).click();	
		WebElement addUnitButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add Unit')]"));
		addUnitButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		addUnit("123 Bean st",4,3.5,"Gas Stove, Microwave, Deep Freezer", "Couch and Loveseat");
		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'123 Bean st')]"));
 		String expectedTitle = "123 Bean st";
 		assertEquals(expectedTitle, titleElement.getText());	
	}
    
    @Test
	void test_login_admin_edit_unit() {
		openPage();
		login("admin", "password");
		driver.findElement(By.linkText("Unit Editing")).click();	
		WebElement addUnitButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add Unit')]"));
		addUnitButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		addUnit("1234 Bean st",4,3.5,"Gas Stove, Microwave, Deep Freezer", "Couch and Loveseat");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));	
		WebElement titleElement = driver.findElement(By.cssSelector("vaadin-grid"));
		List<WebElement> rows = driver.findElements(By.cssSelector("vaadin-grid-cell-content"));
		for (WebElement row : rows) {
		    System.out.println(row.getText());  // Print cell text or perform other actions
		}
		//driver.findElement(By.cssSelector("vaadin-grid-cell-content"));
		//WebElement titleElement = driver.findElement(By.cssSelector("vaadin-grid-cell-content"));
		//Select select = new Select(titleElement);
		//select.selectByVisibleText("1234 Bean st");
		WebElement address = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		address.clear();
		address.sendKeys("321 Beans St");
		WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Save')]"));
		saveButton.click();
		titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'321 Beans St')]"));
		String expectedTitle = "321 Beans St";
 		assertEquals(expectedTitle, titleElement.getText());
		
	}

    
	@AfterEach
	void tearDown() {
		//driver.quit();
	}
	
}
