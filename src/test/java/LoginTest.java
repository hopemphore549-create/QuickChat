/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hopem
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    // =========================
    // USERNAME TESTS
    // =========================

    @Test
    public void testCheckUserNameCorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "Kyle!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testUserNameCorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkUserName());
    }

    @Test
    public void testUserNameIncorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "Kyle!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(false, user.checkUserName());
    }


    // =========================
    // PASSWORD TESTS
    // =========================

    @Test
    public void testCheckPasswordCorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordIncorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordCorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordIncorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertEquals(false, user.checkPasswordComplexity());
    }


    // =========================
    // CELLPHONE TESTS
    // =========================

    @Test
    public void testCellPhoneCorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrect() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneCorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(true, user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectWithAssertEquals() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertEquals(false, user.checkCellPhoneNumber());
    }


    // =========================
    // LOGIN TESTS
    // =========================

    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.loginUser("wrong_user", "wrong_password"));
    }


    // =========================
    // LOGIN STATUS TESTS
    // =========================

    @Test
    public void testReturnLoginStatusSuccessful() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Welcome Hope Mphore, it is great to see you again.",
                user.returnLoginStatus(true)
        );
    }

    @Test
    public void testReturnLoginStatusFailed() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus(false)
        );
    }


    // =========================
    // REGISTRATION TESTS
    // =========================

    @Test
    public void testRegistrationSuccessful() {

        Login user = new Login(
                "Hope",
                "Mphore",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals("Registration successful.", user.registerUser());
    }
}
