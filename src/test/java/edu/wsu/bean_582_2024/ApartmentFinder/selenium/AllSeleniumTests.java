package edu.wsu.bean_582_2024.ApartmentFinder.selenium;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;


import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AllSeleniumTests {
	
	WebDriver driver;
	
	@BeforeEach
	void setUp() {
		driver = new ChromeDriver();
		//Headless - no windows open
		/*ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);*/
		
	    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void openPageInitial() {
		driver.get("http://localhost:8080/");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
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
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void firstRegister(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-9']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-10']"));
		WebElement confirmPassword = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-11']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		confirmPassword.sendKeys(pass);
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Send')]"));
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void register(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-17']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-18']"));
		WebElement confirmPassword = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-19']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		confirmPassword.sendKeys(pass);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Send')]"));
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void addUnit(String addy, int bed, double bath, String kitch, String lRoom) {
		WebElement addUnitButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add Unit')]"));
		addUnitButton.click();
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
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void login(String uname, String pass) {
		WebElement userName = driver.findElement(By.cssSelector("input[name='username']"));
		WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
		userName.sendKeys(uname);
		password.sendKeys(pass);
		WebElement loginButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log in')]"));
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	void addUser(String uname, String pass, String conPass) {
		WebElement username = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-23']"));
		WebElement confirmPassword = driver.findElement(By.cssSelector("input[id='input-vaadin-password-field-27']"));
		username.sendKeys(uname);
		password.sendKeys(pass);
		confirmPassword.sendKeys(conPass);
		WebElement userRole = driver.findElement(By.xpath("//vaadin-list-box[contains(.,'USER')]"));
		userRole.click();
		WebElement enableUser = driver.findElement(By.xpath("//vaadin-checkbox[contains(.,'enabled')]"));
		enableUser.click();
		WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'save')]"));
		saveButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	
	@Test
	@Order(1)
	void test_page_load() {
		openPageInitial();	
		WebElement titleElement1 = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement1.getText());
	}
	
	@Test
	@Order(0)
	void test_register() {		
		openPageInitial();
		firstRegister("admin", "password");
		WebElement titleElement2 = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Register";
		assertEquals(expectedTitle, titleElement2.getText());
	}
	
	
	@Test
	@Order(1)
	void add_new_user() {		
		openPageInitial();
		driver.findElement(By.linkText("Add a user")).click();
		register("user", "upass");			
		WebElement titleElement3 = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Register";
		assertEquals(expectedTitle, titleElement3.getText());
	}
	
	@Test
	@Order(4)
	void test_login_happy_path_admin() {
		openPage();
		login("admin", "password");	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		String expectedTitle = "Log out";
		assertEquals(expectedTitle, logOutButton.getText());
	}
		
   @Test
   @Order(5)
    void test_incorrect_username(){
        openPage();
        login("wrong", "upass");
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
    }

	
	@Test
	@Order(6)
	void add_new_user_pass_dont_match() {		
		openPageInitial();
		driver.findElement(By.linkText("Add a user")).click();
		registerWrong("user22", "nopass");			
		WebElement titleElement = driver.findElement(By.cssSelector("h2"));
		String expectedTitle = "Register";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	@Test
	@Order(3)
	void test_login_happy_path_user() {
		openPage();
		login("user", "upass");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		String expectedTitle = "Log out";
		assertEquals(expectedTitle, logOutButton.getText());
	}
	

   @Test
   @Order(7)
    void test_incorrect_password(){
        openPage();
        login("admin", "wrong");
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
    }    
   
	@Test
	@Order(2)
	void test_logOut_happy_path_admin() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		openPage();
		login("admin", "password");	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		logOutButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
	@Test
	@Order(17)
	void test_logOut_happy_path_user() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		openPage();
		login("user", "upass");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		logOutButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());
	}
	
   @Test
   @Order(11)
	void test_login_admin_grid_access() {
		openPage();
		login("admin", "password");		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'5772 Victoria Way')]"));
		String expectedTitle = "5772 Victoria Way";
		assertEquals(expectedTitle, titleElement.getText());
	}
    
   @Test
   @Order(12)
 	void test_login_user_grid_access() {
 		openPage();
 		login("user", "upass");		
 		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
 		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'9468 Dakota Avenue')]"));
 		String expectedTitle = "9468 Dakota Avenue";
 		assertEquals(expectedTitle, titleElement.getText());
 	}
 	
   @Test
   @Order(13)
	void test_login_admin_add_unit() {
		openPage();
		login("admin", "password");
		driver.findElement(By.linkText("Unit Editing")).click();			
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		addUnit("123 Bean st",4,3.5,"Gas Stove, Microwave, Deep Freezer", "Couch and Loveseat");
		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'123 Bean st')]"));
 		String expectedTitle = "123 Bean st";
 		assertEquals(expectedTitle, titleElement.getText());	
	}
    
	@Test
	@Order(7)
  	void test_login_admin_edit_unit() {
		openPage();
		login("admin", "password");
		driver.findElement(By.linkText("Unit Editing")).click();	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement element = driver.findElement(By.cssSelector("vaadin-grid-cell-content[slot='vaadin-grid-cell-content-18']"));
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		element.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement address = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		address.clear();
		address.sendKeys("1999 Bean Boys Blvd");
		WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Save')]"));
		saveButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		element = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'1999 Bean Boys Blvd')]"));
		String expectedTitle = "114 Crescent Oaks Street";
 		assertNotEquals(expectedTitle, element.getText());		
	}
  	
  	@Test
  	@Order(15)
  	void test_login_admin_add_owner_users() {
  		openPage();
  		login("admin", "password");
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		driver.findElement(By.linkText("User Administration")).click();	
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		WebElement addUserButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add User')]"));
  		addUserButton.click();
  		addUserButton.click();
  		addUserButton.click();
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
  		addUser("ownerUser", "Opass", "Opass");
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'ownerUser')]"));
   		String expectedTitle = "ownerUser";
   		assertEquals(expectedTitle, titleElement.getText());	
  	}
  	

	
	@Test
	@Order(16)
	void test_login_happy_path_owner() {
		openPage();
		login("ownerUser", "Opass");		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement unitEdit = driver.findElement(By.linkText("Unit Editing"));
		String expectedTitle = "Unit Editing";
		assertEquals(expectedTitle, unitEdit.getText());		
	}
	
	@Test
	@Order(16)
	void test_logout_happy_path_owner() {
		openPage();
		login("ownerUser", "Opass");		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement logOutButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Log out')]"));
		logOutButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
		WebElement titleElement = driver.findElement(By.cssSelector("h1"));
		String expectedTitle = "Apartment Finder";
		assertEquals(expectedTitle, titleElement.getText());	
	}
	
	   @Test
	   @Order(17)
	 	void test_owner_user_grid_access() {
	 		openPage();
	 		login("ownerUser", "Opass");
	 		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
	 		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'9468 Dakota Avenue')]"));
	 		String expectedTitle = "9468 Dakota Avenue";
	 		assertEquals(expectedTitle, titleElement.getText());
	 	}
	   
	 @Test
	 @Order(19)
	 void test_login_owner_edit_unit() {
		 openPage();
		 login("ownerUser", "Opass");
		 driver.findElement(By.linkText("Unit Editing")).click();	
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		 addUnit("1234 test test",4,3.5,"none", "none");
		 WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'1234 test test')]"));
		 titleElement.click();
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		 WebElement address = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		 address.clear();
		 address.sendKeys("2024 582Boys Blvd");
		 WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Save')]"));
		 saveButton.click();
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		 titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'2024 582Boys Blvd')]"));
		 String expectedTitle = "1234 test test";
		 assertNotEquals(expectedTitle, titleElement.getText());		
	 }
		
	 @Test
	   @Order(18)
		void test_login_owner_add_unit() {
			openPage();
			login("ownerUser", "Opass");
			driver.findElement(By.linkText("Unit Editing")).click();			
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
			addUnit("543321 Own my own blvd",4,3.5,"none", "none");
			WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'543321 Own my own blvd')]"));
	 		String expectedTitle = "543321 Own my own blvd";
	 		assertEquals(expectedTitle, titleElement.getText());	
		}
	 
	 @Test
	 @Order(19)
	 void test_login_owner_delete_unit() {
		 int testCount = 0, preCount = 0;
		 openPage();
		 login("ownerUser", "Opass");
		 driver.findElement(By.linkText("Unit Editing")).click();	
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		 WebElement names = driver.findElement(By.cssSelector("vaadin-grid"));
		 List<WebElement> rows = names.findElements(By.xpath("//vaadin-grid-cell-content"));			
			for (WebElement row: rows) {
				preCount++;
			}
		 addUnit("delete",4,3.5,"none", "none");
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		 names = driver.findElement(By.cssSelector("vaadin-grid"));
			rows = names.findElements(By.xpath("//vaadin-grid-cell-content")); 			
			for (WebElement row: rows) {
				testCount++;
			}
		 WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'delete')]"));
		 titleElement.click();
		 WebElement deleteButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Delete')]"));
		 deleteButton.click();
		 driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

		 assertNotEquals(preCount, testCount);		
	 }

	@Test
	@Order(18)
  	void test_login_admin_edit_user() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		openPage();
  		login("admin", "password");
  		driver.findElement(By.linkText("User Administration")).click();	
  		WebElement addUserButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add User')]"));
  		addUserButton.click();
  		addUserButton.click();
  		addUserButton.click();
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
  		addUser("noname", "nopass", "nopass");
		WebElement userElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'noname')]"));
		userElement.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement username = driver.findElement(By.cssSelector("input[id='input-vaadin-text-field-19']"));
		username.clear();
		username.sendKeys("Shaq");
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement saveButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'save')]"));
		saveButton.click();		  		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'Shaq')]"));
   		String expectedTitle = "Shaq";
   		assertEquals(expectedTitle, titleElement.getText());
  	}
	
	//@Test
	//@Order(19)
  	void test_login_admin_delete_user() {
		int preCount =0, count = 0, testCount = 0;
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  		openPage();
  		login("admin", "password");
  		driver.findElement(By.linkText("User Administration")).click();	
  		WebElement addUserButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Add User')]"));
  		addUserButton.click();
  		addUserButton.click();
  		addUserButton.click();
  		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));		
  		
		WebElement names = driver.findElement(By.cssSelector("vaadin-grid"));
		List<WebElement> rows = names.findElements(By.xpath("//vaadin-grid-cell-content[contains(.,'')]")); 
		
		for (WebElement row: rows) {
			testCount++;			
		}
		System.out.println(testCount);
		addUser("deleteMe", "dpass", "dpass");
		names = driver.findElement(By.cssSelector("vaadin-grid"));
		rows = names.findElements(By.xpath("//vaadin-grid-cell-content[contains(.,'')]")); 
		
		for (WebElement row: rows) {
			preCount++;			
		}
		System.out.println(preCount);

		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		driver.findElement(By.linkText("Unit Editing")).click();	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		driver.findElement(By.linkText("User Administration")).click();	
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement userElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'deleteMe')]"));
		userElement.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement deleteButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'delete')]"));
		deleteButton.click();		  		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		
		names = driver.findElement(By.cssSelector("vaadin-grid"));
		rows = names.findElements(By.xpath("//vaadin-grid-cell-content[contains(.,'')]")); 
		
		for (WebElement row: rows) {
			System.out.println(row.getText());
			count++;
		}

   		assertNotEquals(count, preCount);   		
  	}
	
	@Test 
	@Order(7)
  	void test_login_admin_delete_unit() {				
			openPage();
			login("admin", "password");
			driver.findElement(By.linkText("Unit Editing")).click();	
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500)); 
			addUnit("remove",4,3.5,"none", "none");
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			WebElement addressSort = driver.findElement(By.xpath("//vaadin-grid-sorter[contains(.,'Address')]"));
			addressSort.click();
			addressSort.click();			
			WebElement titleElement = driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'remove')]"));
			titleElement.click();
			WebElement deleteButton = driver.findElement(By.xpath("//vaadin-button[contains(.,'Delete')]"));
			deleteButton.click();			
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			WebElement deletedElement = driver.findElement(By.cssSelector("vaadin-grid-cell-content[slot='vaadin-grid-cell-content-18']"));
			deletedElement.getText();
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			System.out.println(deletedElement.getText());
			assertNotEquals(deletedElement.getText(), "remove");  
	}

    
	@AfterEach
	void tearDown() {
		driver.quit();
	}
	
}