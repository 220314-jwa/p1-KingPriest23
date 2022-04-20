package Gluecode;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import Pages.TRMSHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepImpl {
	static WebDriver driver;
	static TRMSHomePage trmsAppHome;
	
	@BeforeAll 
	public static void setUp() {
		// first create a file for your web driver in this case an edge driver
		File file = new File("src/main/resources/msedgedriver.exe");
		// Set the driver to the file so we can make connections to edge websites
		System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
		
		// create WebDriver object (using EdgeDriver or whatever internet services you use)
		driver = new EdgeDriver();
		trmsAppHome = new TRMSHomePage(driver);
		// send the driver to the webpage of your choice
		// In this case the main page to my Login page
		//driver.get("https://http://127.0.0.1:5500/LoginPage.html");
	}
	@AfterAll
	public static void closeDriver() {
		driver.quit();
	}
	
	@Given("the user is on the homepage")
	public void the_user_is_on_the_homepage() {
	    trmsAppHome.navigateTo();
	}

	@When("the user enters the correct username")
	public void the_user_enters_the_correct_username() {
		trmsAppHome.inputUsername("snicholes");
	}

	@When("the user enters the correct password")
	public void the_user_enters_the_correct_password() {
	    trmsAppHome.inputPassword("pass");
	}

	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
	    trmsAppHome.submitLogin();
	}

	@Then("the nav will show the user's first name")
	public void the_nav_will_show_the_user_s_first_name() {
		assertTrue(trmsAppHome.getNavText().contains("Sierra"));
		trmsAppHome.logOut();
	}

	@When("the user enters an incorrect username")
	public void the_user_enters_an_incorrect_username() {
	    trmsAppHome.inputUsername("asdfghjkl");
	}

	@Then("an incorrect credentials message will be displayed")
	public void an_incorrect_credentials_message_will_be_displayed() {
		String message = trmsAppHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("incorrect credentials"));
	}

	@When("the user enters the incorrect password")
	public void the_user_enters_the_incorrect_password() {
	    trmsAppHome.inputPassword("12345678987654321");
	}
	
	@When("the user enters the username {string}")
	public void the_user_enters_the_username(String username) {
	    trmsAppHome.inputUsername(username);
	}

	@When("the user enters the password {string}")
	public void the_user_enters_the_password(String password) {
	    trmsAppHome.inputPassword(password);
	}

	@Then("an invalid input message will be displayed")
	public void an_invalid_input_message_will_be_displayed() {
		String message = trmsAppHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("invalid input"));
	}


}
