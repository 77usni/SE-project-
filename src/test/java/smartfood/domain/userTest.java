
package smartfood.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class userTest {

    @Test
    public void testUserCreation() {
        user u = new user(1, "Laith", "laith@gmail.com", "1234", "CUSTOMER");

        assertEquals(1, u.getId());
        assertEquals("Laith", u.getName());
        assertEquals("laith@gmail.com", u.getEmail());
        assertEquals("CUSTOMER", u.getRole());
    }

    @Test
    public void testSetName() {
        user u = new user(1, "Laith", "laith@gmail.com", "1234", "CUSTOMER");

        u.setName("Adham");
        u.setEmail("adham@gmail.com");

        assertEquals("Adham", u.getName());
        assertEquals("adham@gmail.com", u.getEmail());
    }

    @Test
    public void testHusniUserCreation() {
        user u = new user(2, "Husni", "husni@gmail.com", "5678", "CUSTOMER");

        assertEquals(2, u.getId());
        assertEquals("Husni", u.getName());
        assertEquals("husni@gmail.com", u.getEmail());
        assertEquals("CUSTOMER", u.getRole());
    }
}
