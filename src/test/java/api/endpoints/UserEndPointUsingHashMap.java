package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class UserEndPointUsingHashMap {
    public static Response createUser(HashMap payload) {

        Response response =  given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);
        return response;
    }

    public static Response readUser(String userName) {
        Response response =  given()
                .pathParam("username", userName)
                .when()
                .get(Routes.get_url);
        return response;
    }

    public static Response updateUser(String userName, HashMap payload) {

        Response response =  given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", userName)
                .body(payload)
                .when()
                .put(Routes.update_url);
        return response;
    }

    public static Response deleteUser(String userName) {
        Response response =  given()
                .pathParam("username", userName)
                .when()
                .delete(Routes.delete_url);
        return response;
    }
}