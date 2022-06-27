package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;



@RunWith(Cucumber.class)
public class StepDefinition extends Utils 
{

	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
  	TestDataBuild data=new TestDataBuild();
  	static String placeId;
  	@Given("add place payload with {string} {string} {string}")
  	public void add_place_payload_with(String name, String language, String address) throws IOException 
  	{
    	res=given().spec(requestSpesification())
		.body(data.addPlacePayload(name,language,address));
        

    }

  	@When("user calls {string} with {string} http request")
  	public void user_calls_with_http_request(String resource, String method) {
    	APIResources resourceAPI=  APIResources.valueOf(resource);
    	System.out.println(resourceAPI.getResource());
    	
    	
    	resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    	if(method.equalsIgnoreCase("POST"))
    	{
        response =res.when().post(resourceAPI.getResource()).
				then().spec(resspec).extract().response();
    	}
    	else if (method.equalsIgnoreCase("GET")) {
    		response =res.when().get(resourceAPI.getResource()).
    				then().spec(resspec).extract().response();
			
		}
    	
    	
    }

    @Then("^the api call got success with status code 200$")
    public void the_api_call_got_success_with_status_code_200() throws Throwable
    {
    		assertEquals(response.getStatusCode(),200);

    }

    @Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String keyValue, String expectedValue) throws Throwable
    {
    	
		assertEquals(getJsonPath(response,keyValue).toString(),expectedValue);
		
    }

    @And("^verify place_Id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
    public void verify_placeid_created_maps_to_something_using_something(String expectedName, String resource) throws IOException
    {
    // 1) PREPARE requrestSpec
    	placeId=getJsonPath(response,"place_id");
    res=given().spec(requestSpesification()).queryParam("place_id",placeId);
    user_calls_with_http_request(resource,"GET");
    String actualName= getJsonPath(response,"name");
    assertEquals(actualName,expectedName);
    }
    
    

@Given("Delete_place payload")
public void delete_place_payload() throws IOException
{
   res= given().spec(requestSpesification()).body(data.deletePlacePayload(placeId));
}


	
}
