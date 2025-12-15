package PROG3;

import PROG3.DB.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        try {
            db.getDBConnection();
            System.out.println("Connexion PostgreSQL réussie !");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
