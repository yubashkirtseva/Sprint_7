import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String DELETE_ORDER_PATH = "/api/v1/orders/cancel";

    public ValidatableResponse create(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    public ValidatableResponse delete(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .put(DELETE_ORDER_PATH)
                .then();
    }
}
