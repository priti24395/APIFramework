package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		// WRITE CODE THAT WILL GIVE YOU PLACE ID
		//EXECUTE THIS CODE ONLY WHEN PLACE IS IS NULL
		
		StepDefinition m=new StepDefinition();
		if(m.placeId==null)
		{
		m.add_place_payload_with("Aditya", "Hindi", "Pune");
		m.user_calls_with_http_request("AddPlaceAPI","getPlaceAPI");
		m.verify_placeid_created_maps_to_something_using_something("aditya","post");

		
		}
	}
		
}
