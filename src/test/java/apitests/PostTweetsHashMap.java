package apitests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.RestUtilities;
import constants.Endpoints;
import constants.Path;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostTweetsHashMap {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId = "";
	String tweetName = "";
	String tweetText = "";
	String tweetPlace = "";
	HashMap<String,String> tweetDetails = new HashMap<String,String>();
	

	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		//System.out.println(reqSpec.log().all());
		
		resSpec = RestUtilities.getResponseSpecification();
		//System.out.println(resSpec.log());
	}
	
	@Test
	public void postTweetandAttachments() {
		
		tweetDetails.put("status","Attachment Tweet");
		tweetDetails.put("place_id","122");
		Response response =
				given()
					.spec(RestUtilities.createQueryParam(reqSpec, tweetDetails))
					.log().all()
				.when()
					.post(Endpoints.STATUSES_TWEET_POST)
				.then()
					.spec(resSpec)
					.log().all()
					.extract()
					.response();
		JsonPath jsPath = RestUtilities.getJsonPath(response);
		tweetId = jsPath.get("id_str");
		tweetName = jsPath.get("user.name");
		tweetText = jsPath.get("text");
		tweetPlace = jsPath.get("place");
		System.out.println("The tweet posted by: " + tweetName);
		System.out.println("The tweet id is: " + tweetId);
		System.out.println("The tweet text is: " + tweetText);
		System.out.println("The tweet text is: " + tweetPlace);
	
	}

}
