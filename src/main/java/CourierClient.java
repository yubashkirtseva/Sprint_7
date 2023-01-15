import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client{
    private static final String CREATE_PATH = "api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";



    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then();

    }


    public ValidatableResponse delete(String id){
        return given()
                .spec(getSpec())
                .when()
                .delete("/api/v1/courier/"+id)
                .then();


    }
}
