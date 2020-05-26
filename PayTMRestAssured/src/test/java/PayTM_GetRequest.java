import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PayTM_GetRequest {

	Response response;
	String responseBody;
	String URI = "https://apiproxy.paytm.com/v2/movies/upcoming";

	@BeforeMethod
	public void Setup() {

		System.out.println("Inside before method");

		RestAssured.baseURI = "https://apiproxy.paytm.com/v2/movies/upcoming";

		RequestSpecification httpReq = RestAssured.given();

		response = httpReq.request(Method.GET);

		responseBody = response.getBody().asString();

		System.out.println("Response body is : " + responseBody);

	}

	@Test(priority = 1, enabled = false)
	public void getStatusCodeTest1() {

		int statusCode = response.getStatusCode();
		System.out.println("Status code is : " + statusCode);

		Assert.assertEquals(statusCode, 200, "Success status code not received");

	}

	@Test(priority = 3)
	public void moviePosterURLTest3() {

		JsonPath jsonResponse = response.jsonPath();
		
		String moviePosterValue = null;

		for (int i = 1; i <= 41; i++)
			
			//System.out.println(jsonResponse.get("moviePosterUrl[" + i + "]"));

			moviePosterValue = jsonResponse.get("upcomingMovieData.moviePosterUrl[" + i + "]");
		
		Assert.assertEquals(moviePosterValue.contains(".jpg"), true);

	}

	/*
	 * get("https://apiproxy.paytm.com/v2/movies/upcoming").then().assertThat().body
	 * ("upcomingMovieData.moviePosterUrl[i]", contains(".jpg"));
	 * Assert.assertEquals(responseBody.contains(), expected);
	 * 
	 * get(URI).then().assertThat().body("upcomingMovieData.moviePosterUrl[i]",
	 * IS.is(".jpg"));
	 * 
	 */

	@AfterMethod()
	public void TearDown() {

		System.out.println("Inside after method");

	}

}
