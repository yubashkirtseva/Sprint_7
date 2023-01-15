import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private Courier courier;

    private CourierClient courierClient;
    private CourierCredentials courierCredentials;
    private String id;

    @Before
    public void setUp(){
        courier = CourierGeneration.getDefault();
        courierClient = new CourierClient();

    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с корректными параметрами")
    public void courierCanLogin(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int idInt = loginResponse.extract().path("id");
        id = String.valueOf(idInt);
        int statusCode = loginResponse.extract().statusCode();
        boolean id_ok = idInt > 0;
        assertEquals(SC_OK, statusCode);
        assertEquals(true, id_ok);
    }

    @Test
    @DisplayName("Проверка невозможности авторизации курьера с некорректными параметрами")
    public void cannotLoginWithIncorrectData(){
        courierClient.create(courier);
        courierCredentials = new CourierCredentials("incorrect", "1703");
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }

    @Test
    @DisplayName("Проверка невозможности авторизации курьера с неполными параметрами")
    public void cannotLoginWithIncompleteData(){
        courierClient.create(courier);
        courierCredentials = new CourierCredentials(null, "1703");
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }


}
