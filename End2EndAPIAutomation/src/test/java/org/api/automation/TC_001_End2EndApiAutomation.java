package org.api.automation;

import org.api.library.CreatPost;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;


import com.jayway.restassured.http.ContentType;

import com.jayway.restassured.response.ValidatableResponse;



public class TC_001_End2EndApiAutomation {
	
	@Test
	public void tc_001_test1(){
		int input = 18;
		
		//Step 1:Created a resource by using post method
		CreatPost cpost = new CreatPost();
		cpost.setId(input);
		cpost.setTitle("Hero5");
		cpost.setAuthor("Lakshmi");
		
		ValidatableResponse response = given().contentType(ContentType.JSON)
		.body(cpost)
		
		.when()
		.post("http://localhost:3000/posts")
		.then()
		.contentType(ContentType.JSON);
		
	int responseId =	response.extract().path("id");
	System.out.println(responseId);
	int actualStatusCode =response.extract().response().getStatusCode();
	
	Assert.assertEquals(actualStatusCode, 201);
	
	//Step2: Validating the resource by using get method
	
	ValidatableResponse response1 = when()
	.get( "http://localhost:3000/posts/"+responseId)
	.then()
	.contentType(ContentType.JSON);
	System.out.println(response1.toString());
	
	String actualTitle = response1.extract().path("title");
	System.out.println(actualTitle);
	String actualAuthor = response1.extract().path("author");
	System.out.println(actualAuthor);
	
	Assert.assertEquals(actualTitle,"Hero5");
    Assert.assertEquals(actualAuthor,"Lakshmi");
	
    //Step 3: Update  the resource by using put method
		CreatPost cpost1 = new CreatPost();
		cpost1.setId(input);
		cpost1.setTitle("Update Title");
		cpost1.setAuthor("Update Author");
		
		ValidatableResponse response2 = given()
		.contentType(ContentType.JSON)
		.body(cpost1)
		.when()
		.put("http://localhost:3000/posts/"+input)
		.then()
		.contentType(ContentType.JSON);
		// Validating the updated resource by using get method
		ValidatableResponse response3 = when()
				.get( "http://localhost:3000/posts/"+responseId)
				.then()
				.contentType(ContentType.JSON);
				System.out.println(response1.toString());
				
				String actualTitle1 = response3.extract().path("title");
				System.out.println(actualTitle);
				String actualAuthor1 = response3.extract().path("author");
				System.out.println(actualAuthor);
				
				Assert.assertEquals(actualTitle1,"Update Title");
			    Assert.assertEquals(actualAuthor1,"Update Author");
		
	
	//Step4: delete the resource by using delete method		    
       when()
       .delete("http://localhost:3000/posts/"+input);
       
       //Validating the resource to check if the content is deleted and also checking the status code
       
       ValidatableResponse response4 = when()
				.get( "http://localhost:3000/posts/"+responseId)
				.then()
				.contentType(ContentType.JSON);
       
       Assert.assertNotEquals(response4.extract().response().statusCode(), 201);
       
       
	}

}
