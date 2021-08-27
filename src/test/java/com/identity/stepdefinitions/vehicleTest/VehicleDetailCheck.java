package com.identity.stepdefinitions.vehicleTest;

import java.util.HashMap;
import java.util.List;

import com.identity.VO.VehicleDetailsVO;
import com.identity.pages.HomePage;
import com.identity.pages.VehicleResultsPage;
import com.identity.service.FileService;
import com.identity.testUtils.BaseTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;


public class VehicleDetailCheck extends BaseTest {
	private static String browser;
	HomePage homePage = null;
	List<String>  registrationNumberList = null;
	HashMap<String, VehicleDetailsVO>  vehilceDetailsVOMap = null;
	

	@Given("^I am in the home page of free car check site$")
	public void gotoHomePage() {
		try {
			loadDriverAndProperties();
			String inputFolderPath = getProp().getProperty("input_folder_path");
			String outputFolderPath = getProp().getProperty("output_folder_path");
			//Load the input and output details from all the files present in the given folders
			registrationNumberList = FileService.loadInputData(inputFolderPath);
			vehilceDetailsVOMap = FileService.loadOutputData(outputFolderPath);
			// navigate to the Home Page in the UI
			homePage = navigateToHomePage();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@When("^I enter vehicle registration numbers from the input file and assert the details from the output file$")
	public void enterRegistrationDetails() throws Exception {
		//pick up each of the Registration numbers, click Free car check button, and assert the details
		for(String registrationNumber :registrationNumberList ) {
			VehicleDetailsVO vehicleDetailsVOFromOutputFile = vehilceDetailsVOMap.get(registrationNumber.replaceAll("\\s+",""));
			VehicleResultsPage vehicleResultsPage = homePage.enterRegistrationAndClickFreeCarCheckButton(registrationNumber);
			VehicleDetailsVO vehicleDetailsVOFromUIPage = vehicleResultsPage.returnVehicleDetails();
			if(StringUtils.isNotBlank(vehicleDetailsVOFromUIPage.getModel())) {
				System.out.println("values are found in the UI for the registration number -" + registrationNumber);
				System.out.println("values from the UI are : make = " + vehicleDetailsVOFromUIPage.getMake() + ", color = "
						+ vehicleDetailsVOFromUIPage.getColor() + "Model = " + vehicleDetailsVOFromUIPage.getModel() +
						"Year = " + vehicleDetailsVOFromUIPage.getYear());
				//asserting the vehicle make
				Assert.assertEquals("values from UI and outputFile for make are not equal", vehicleDetailsVOFromUIPage.getMake(), vehicleDetailsVOFromOutputFile.getMake());
				//asserting the vehicle colour
				Assert.assertEquals("values from UI and outputFile for color are not equal", vehicleDetailsVOFromUIPage.getColor(), vehicleDetailsVOFromOutputFile.getColor());
				//asserting the vehicle Model
				Assert.assertEquals("values from UI and outputFile for Model are not equal", vehicleDetailsVOFromUIPage.getModel(), vehicleDetailsVOFromOutputFile.getModel());
				//asserting the vehicle colour
				Assert.assertEquals("values from UI and outputFile for Year are not equal", vehicleDetailsVOFromUIPage.getYear(), vehicleDetailsVOFromOutputFile.getYear());
				homePage = vehicleResultsPage.clickCheckAnotherVehicleButton();
			} else {
				System.out.println("***** values are not found in the UI for the registration number *** -" + registrationNumber);
				homePage = vehicleResultsPage.clickTryAgainPopUpButton();
			}

			}
	}

	 @Then("^I close the browser$")
	public void tearDown() {
		driver.quit();
	}

}
