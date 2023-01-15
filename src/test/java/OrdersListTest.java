import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class OrdersListTest {
    private OrdersListClient ordersListClient;
    @Test
    @DisplayName("Проверка получения списка заказов")
    public void  getOrdersList(){
        ordersListClient = new OrdersListClient();
        ValidatableResponse response = ordersListClient.getOrdersList();
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        List<String> list = response.extract().path("orders");
        boolean EmptyList = list.isEmpty();
        assertEquals(false, EmptyList);
    }

}
