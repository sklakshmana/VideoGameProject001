package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPI {
	
	@Test(priority=1)
	public void getAllVideoGames()
	{
		
		given()
		
		.when()
			.get("http://localhost:9000/app/videogames")
		.then()
			.statusCode(200);
	}
	
	@Test(priority=2)
	public void test_addNewVideoGame()
	{
		HashMap data=new HashMap();
		data.put("id", "100");
		data.put("name", "Spider Man");
		data.put("releaseDate", "2020-04-15T09:47:20.320Z");
		data.put("reviewScore", "5");
		data.put("category", "Advanture");
		data.put("rating", "Universal");
		
		Response res=
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("http://localhost:9000/app/videogames")
			
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
	}
	
	@Test(priority=3)
	public void test_getVideoGame()
	{
		given()
		.when()
			.get("http://localhost:9000/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body();

	}
	@Test(priority=4)
	public void test_UpdateVideoGame()
	{
		HashMap data= new HashMap();
		//data.put(key, value)
		data.put("id", "100");
		data.put("name", "Pacman");
		data.put("releaseDate", "2020-04-15T09:47:20.320Z");
		data.put("reviewScore", "4");
		data.put("category", "Advanture");
		data.put("rating", "Universal");	
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("http://localhost:9000/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("VideoGame.id", equalTo("100"))
			.body("VideoGame.name", equalTo("Pacman"));		
	}
	
	@Test(priority=5)
	public void test_DeleteVideoGame() throws InterruptedException
	{
		Response res=
		given()
		
		.when()
			.delete("http://localhost:9000/app/videogames/100")
		
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		Thread.sleep(3000);
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}
	

}
