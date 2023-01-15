import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersListClient extends Client{
    private static final String ORDERS_LIST_PATH = "/api/v1/orders";

    public ValidatableResponse getOrdersList(){
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS_LIST_PATH)
                .then();
    }
}
