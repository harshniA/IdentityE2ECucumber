
package com.identity.testUtils;

import com.identity.pages.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest extends SeleniumBaseTest {

	public static HomePage navigateToHomePage() throws Exception {
		HomePage homepage = PageFactory.initElements(getDriver(), HomePage.class);
		homepage.navigateTo(getProp().getProperty("site_url"));
		TimeUnit.SECONDS.sleep(getVeryShortTime());
		return homepage;
	}

	@BeforeSuite
	public void setPropertiesForAll() {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		System.setProperty("org.uncommons.reportng.title", "Identity Regression Report");

	}

	public static void cleanUp() {
		if (getDriver() != null) {
			getDriver().close();
			getDriver().quit();
		}
	}

}
