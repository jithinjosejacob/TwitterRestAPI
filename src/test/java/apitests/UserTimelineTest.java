package apitests;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.RestUtilities;
import constants.Endpoints;
import constants.Path;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;


public class UserTimelineTest {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.queryParam("user_id", "JithinJoseJaco1");
		reqSpec.basePath(Path.STATUSES);
		
		resSpec = RestUtilities.getResponseSpecification();
	}

	@Test
	public void readTweets1() {
		given()
			.spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))
			//.log().all()
		.when()
			.get(Endpoints.STATUSES_USER_TIMELINE)
		.then()
			//.log().all()
			.spec(resSpec)
			.body("user.screen_name", hasItem("JithinJoseJaco1"));
	}
	
	@Test
	public void readTweets2() {
		RestUtilities.setEndPoint(Endpoints.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "count", "2"), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println("Read Tweets 2 Method");
		System.out.println(screenNameList);
		Assert.assertTrue(screenNameList.contains("JithinJoseJaco1"));
	}
}