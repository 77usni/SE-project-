
package smartfood.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import smartfood.domain.user;

public class loginServiceTest {

    @Test
    public void testValidLogin() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Login successful.",
                service.login(u, "husni@gmail.com", "1234"));
    }

    @Test
    public void testWrongPassword() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Error: Incorrect password.",
                service.login(u, "husni@gmail.com", "9999"));
    }

    @Test
    public void testWrongEmail() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Error: Incorrect email.",
                service.login(u, "wrong@gmail.com", "1234"));
    }

    @Test
    public void testEmptyEmail() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Error: Email is required.",
                service.login(u, "", "1234"));
    }

    @Test
    public void testEmptyPassword() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Error: Password is required.",
                service.login(u, "husni@gmail.com", ""));
    }

    @Test
    public void testEmptyEmailAndPassword() {
        user u = new user(1, "Husni", "husni@gmail.com", "1234", "CUSTOMER");
        loginService service = new loginService();

        assertEquals("Error: Email is required.",
                service.login(u, "", ""));
    }
}
