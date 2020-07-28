package br.sp.psantos.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://192.168.0.16:8001/tasks-backend";
	}
	
	@Test
	public void shouldReturnTasks() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void shouldAddTaskSuccess() {
		RestAssured.given()
			.body("{ \"task\" : \"Testing via API\",\"dueDate\" : \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void shouldNotAddInvalidTask() {
		RestAssured.given()
			.body("{ \"task\" : \"Testing via API\",\"dueDate\" : \"2010-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}

