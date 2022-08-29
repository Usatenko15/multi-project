import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredTests {


    @Test
    public void createProduct () {

        JSONObject jsonObj = new JSONObject()
                .put("name","createTest");

        given()
                .contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("http://localhost:8080/api/v1/product")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", equalTo("createTest"))
                .body("uniqId", notNullValue());
    }

    @Test
    public void getAllProducts () {
        given()
                .get("http://localhost:8080/api/v1/product")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", notNullValue())
                .body("uniqId", notNullValue());
    }

    @Test
    public void getProduct () {
        given()
                .get("http://localhost:8080/api/v1/product/1")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", notNullValue())
                .body("uniqId", notNullValue());
    }

    @Test
    public void createCustomer () {

        JSONObject jsonObj = new JSONObject()
                .put("name","createTest");

        given()
                .contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("http://localhost:8080/api/v1/customer")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", equalTo("createTest"))
                .body("customerId", notNullValue());
    }

    @Test
    public void getAllCustomers () {
        given()
                .get("http://localhost:8080/api/v1/customer")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", notNullValue())
                .body("customerId", notNullValue());
    }

    @Test
    public void getCustomer () {
        given()
                .get("http://localhost:8080/api/v1/customer/1")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("name", notNullValue())
                .body("customerId", notNullValue());
    }
}
