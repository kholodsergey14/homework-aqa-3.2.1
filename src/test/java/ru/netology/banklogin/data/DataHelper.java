package ru.netology.banklogin.data;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWithTestDataLoginAndWrongPassword() {
        return new AuthInfo("vasya", "qwerty");
    }

    public static AuthInfo getRandomAuthInfoWithoutAddingToBase() {
        val faker = new Faker();
        return new AuthInfo(faker.name().firstName(), faker.internet().password());
    }

    public static AuthInfo getRandomAuthInfo(String status) throws SQLException {
        val faker = new Faker();
        String login = faker.name().firstName();
        val runner = new QueryRunner();
        val countSQL = "SELECT COUNT(*) FROM users;";
        val dataSQL = "INSERT INTO users (id, login, password, status) VALUES (?, ?, ?, ?);";
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {
            long count = runner.query(conn, countSQL, new ScalarHandler<>());
            runner.update(conn, dataSQL, Long.toString(count + 1), login, "$2a$10$3oIaaE/XKaHyjqzevEmN9e.JZFD0I678RzFAAEpPpI/JpA/yibKuu", status);
        }
        return new AuthInfo(login, "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() throws SQLException {
        String verificationCode = "";
        val codesSQL = "SELECT * FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {
            val verificationCodeTable = runner.query(conn, codesSQL, new BeanHandler<>(VerificationCodeTable.class));
            verificationCode = verificationCodeTable.getCode();
        }
        return new VerificationCode(verificationCode);
    }

    public static VerificationCode getRandomVerificationCode() {
        val faker = new Faker();
        return new VerificationCode(faker.number().digits(6));
    }
}