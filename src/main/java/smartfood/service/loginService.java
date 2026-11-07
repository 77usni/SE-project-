
package smartfood.service;

import smartfood.domain.user;

public class loginService {

    public String login(user u, String email, String password) {

        if (u == null) {
            return "Error: User not found.";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Error: Email is required.";
        }

        if (password == null || password.isEmpty()) {
            return "Error: Password is required.";
        }

        if (u.getEmail() == null || !u.getEmail().equals(email.trim())) {
            return "Error: Incorrect email.";
        }

        if (u.getPassword() == null || !u.getPassword().equals(password)) {
            return "Error: Incorrect password.";
        }

        return "Login successful.";
    }
}
