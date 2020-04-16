package learnings;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//URI https://reqres.in/api/users?page=2
//RestAssured Identifying Request Parameter as Query Parameter


public class TC001_RequestParams {
	
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
			.param("page", "2")
			.log().all()
		.when()
			.get("users")		
		.then()
		    .statusCode(200)
		    .contentType(ContentType.JSON)
		    .extract().response();
		
		System.out.println(re.asString());
		
	}

}
