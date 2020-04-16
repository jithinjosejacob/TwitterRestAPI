package apitests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.RestUtilities;
import constants.Endpoints;
import constants.Path;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TwitterWorkFlowTest {
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId = "";
	String tweetName = "";
	String tweetText = "";

	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		//System.out.println(reqSpec.p;
		
		resSpec = RestUtilities.getResponseSpecification();
		//System.out.println(resSpec.log());
	}
	
	@Test
	public void postTweet() {
		Response response =
				given()
					.spec(RestUtilities.createQueryParam(reqSpec, "status", "Ghost Protocol 2020"))
					//.log().all()
				.when()
					.post(Endpoints.STATUSES_TWEET_POST)
				.then()
					.spec(resSpec)
					//.log().all()
					.extract()
					.response();
		JsonPath jsPath = RestUtilities.getJsonPath(response);
		tweetId = jsPath.get("id_str");
		tweetName = jsPath.get("user.name");
		tweetText = jsPath.get("text");
		System.out.println("The tweet posted by: " + tweetName);
		System.out.println("The tweet id is: " + tweetId);
		System.out.println("The tweet text is: " + tweetText);
	
	}
	
	@Test(dependsOnMethods={"postTweet"})
	public void readTweet() {
		RestUtilities.setEndPoint(Endpoints.STATUSES_TWEET_READ_SINGLE);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "id", tweetId), "get");
		String text = res.path("text");
		System.out.println("ReadTweet tweet text is: " + text);
	}
	
	@Test(dependsOnMethods={"readTweet"})
	public void reTweet() {
		RestUtilities.setEndPoint(Endpoints.STATUS_RETWEET);
		Response res = RestUtilities.getResponse(
				RestUtilities.createPathParam(reqSpec, "id", tweetId), "post");
		String text = res.path("text");
		System.out.println("ReTweet is: " + text);
		
	/*given()
		.spec(RestUtilities.createPathParam(reqSpec, "id", tweetId))
		.when()
			.post(Endpoints.STATUS_RETWEET)
		.then()
			.spec(resSpec);*/
	
	}
	
	@Test(dependsOnMethods={"reTweet"})
	public void deleteTweet() {
		RestUtilities.setEndPoint(Endpoints.STATUSES_TWEET_DESTROY);
		Response res = RestUtilities.getResponse(
				RestUtilities.createPathParam(reqSpec, "id", tweetId), "post");
		int statusCode = res.getStatusCode();
		//String statusMessage = res.getStatusLine();
		System.out.println(statusCode);
		//System.out.println(statusMessage);
		//System.out.println(res);
		/*given()
			.spec(RestUtilities.createPathParam(reqSpec, "id", tweetId))
		.when()
			.post(Endpoints.STATUSES_TWEET_DESTROY)
		.then()
			.spec(resSpec);*/
	}
}