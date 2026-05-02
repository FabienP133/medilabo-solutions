package com.medilabo.patient;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/medilabo_patient?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "Jaipastropconfiance13#";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connexion JDBC réussie !");
            System.out.println("Auto-commit : " + connection.getAutoCommit());
        } catch (Exception e) {
            System.out.println("Échec connexion JDBC");
            e.printStackTrace();
        }
    }
}