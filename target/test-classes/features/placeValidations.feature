Feature: Validating place API's

@AddPlace
Scenario Outline:: Vefify if place is being successfully added using AddPlaceAPI
Given add place payload with "<name>" "<language>" "<address>"
When user calls "addPlaceAPI" with "post" http request
Then the api call got success with status code 200
And "status" in response body is "OK"
And verify place_Id created maps to "<name>" using "getPlaceAPI"



Examples: 
|name  			|language  			|address    |
|priti			|Marathi			|aaaa		|
#|prerana		|hindi				|bbbb		|
#|kittu			|english			|cccc		|
#|mayur			|french				|dddd		|
#|adesh			|urdu				|eeee		|

@DeletePlace
Scenario: verify if Delete Place functionality is working
Given Delete_place payload
When user calls "deletePlaceAPI" with "post" http request
Then the api call got success with status code 200
And "status" in response body is "OK"
	
