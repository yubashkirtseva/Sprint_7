import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierCreationTest {
    private Courier courier;
    private Courier other_courier;
    private CourierClient courierClient;
    private String id;

    @Before
    public void setUp(){
        courier = CourierGeneration.getDefault();
        other_courier = CourierGeneration.getIncompleteDate();
        courierClient = new CourierClient();
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }



    @Test
    @DisplayName("Проверка создания курьера с корректными параметрами")
    public void courierCanBeCreated(){
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int idInt = loginResponse.extract().path("id");
        id = String.valueOf(idInt);
        int statusCode = response.extract().statusCode();
        Boolean ok = response.extract().path("ok");
        assertEquals(SC_CREATED, statusCode);
        assertEquals(true, ok);
    }

    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров")
    public void cannotCreateTwoIdenticalCouriers(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int idInt = loginResponse.extract().path("id");
        id = String.valueOf(idInt);
        ValidatableResponse responseSecondCreation = courierClient.create(courier);
        int statusCode = responseSecondCreation.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера без необходимых параметров")
    public void cannotCreateCourierWithIncompleteData(){
        ValidatableResponse response = courierClient.create(other_courier);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

    }


}
