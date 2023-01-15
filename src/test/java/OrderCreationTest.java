import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.awt.Color.BLACK;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private Order order;
    private OrderClient orderClient;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;
    private final int expected_status_code;

    public OrderCreationTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color, int expected_status_code) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.expected_status_code = expected_status_code;
    }

    @Parameterized.Parameters
    public static Object[] createOrder() {
        return new Object[][]{
                {"Ivan", "Ivanov", "Nevskiy, 122", 4, "+79990009900", 3, "2023-01-20", "-", Arrays.asList("BLACK"), 201},
                {"Petr", "Petrov", "Nevskiy, 124", 4, "+79990009901", 2, "2023-01-21", "hello", Arrays.asList("BLACK", "GRAY"), 201},
                {"Igor", "Igorev", "Nevskiy, 126", 4, "+79990009902", 2, "2023-04-20", "-", Arrays.asList(), 201},
        };
    }


    @Test
    @DisplayName("Проверка создания заказов")
    public void correctCreateOrder(){
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(expected_status_code, statusCode);
        int track = response.extract().path("track");
        boolean track_ok = track > 0;
        assertEquals(SC_CREATED, statusCode);
        assertEquals(true, track_ok);


    }

}
