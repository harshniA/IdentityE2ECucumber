Feature: Test the vehicle details

  @Test
  Scenario: Verify the vehicle details
  	Given I am in the home page of free car check site
	When I enter vehicle registration numbers from the input file and assert the details from the output file
	Then I close the browser
   

