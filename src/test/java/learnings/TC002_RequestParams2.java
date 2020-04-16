package learnings;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//URI https://reqres.in/api/users/
//RestAssured Identifying Form Parameter for user creation from body


public class TC002_RequestParams2 {
	
	@BeforeClass
	public void setBaseURI() {
		// here we setup the default URL and API base path to use throughout the tests
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "api/";
	}
	
	@Test
	public void testgetemployee() {		
		Response re = RestAssured
		.given()
			.body("{\"name\":\"Mathu Mani\",\"job\":\"Manager\"}")
			//.formParam("name", "Joby")
			.log().all()
		.when()
			.post("users")		
		.then()
		    .statusCode(201)
		    .contentType(ContentType.JSON)
		    .extract().response();
		
		System.out.println(re.asString());
		
	}

}
