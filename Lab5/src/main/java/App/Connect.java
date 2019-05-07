package App;

import Data.Animal;
import Data.Person;

import java.sql.*;
import java.util.ArrayList;

public class Connect {
    static Connection connection = null;

    public boolean dbConnect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Sterowniki zaladowane");

            // jdbc:oracle:thin:username/password@localhost:port:SID
            connection = DriverManager.getConnection("jdbc:oracle:thin:system/admin@localhost:1521:orcl");

            System.out.println("Polaczenie nawiazane");
            // odczytanie danych z bazy dla sprawdzenia poprawnosci polaczenia,
            // sql developer musi byc wylaczony
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Blad polaczenia z baza danych");
            return false;
        }
    }

    public boolean dbCreatePerson(int id, String firstName, String lastName, int salary, String someText) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO Osoba VALUES (?, ?, ?, ?, ?)");
            prepStmt.setInt(1, id);
            prepStmt.setString(2, firstName);
            prepStmt.setString(3, lastName);
            prepStmt.setInt(4, salary);
            prepStmt.setString(5, someText);
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean dbUpdatePerson(int id, String firstName, String lastName, int salary, String someText) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("UPDATE Osoba SET Imie = ?, Nazwisko = ?, Pensja = ?, Jakiespole = ? WHERE id = ?");
            prepStmt.setString(1, firstName);
            prepStmt.setString(2, lastName);
            prepStmt.setInt(3, salary);
            prepStmt.setString(4, someText);
            prepStmt.setInt(5, id);
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Person> dbQueryPeople() {
        ArrayList<Person> arrayListPeople = new ArrayList<Person>();

        try {
            String queryPeople = "SELECT id, imie, nazwisko, pensja, jakiespole FROM Osoba";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryPeople);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("imie");
                String lastName = rs.getString("nazwisko");
                int salary = rs.getInt("pensja");
                String someText = rs.getString("jakiespole");

                arrayListPeople.add(new Person(id, firstName, lastName, salary, someText));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("query osoba");
        }

        return arrayListPeople;
    }

    public boolean dbCreateAnimal(int id, String name, int ownerID){
        try {
            PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO Zwierze VALUES (?, ?, ?)");
            prepStmt.setInt(1, id);
            prepStmt.setString(2, name);
            prepStmt.setInt(3, ownerID);
            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean dbUpdateAnimal(int id, String name, int ownerID){
        try {
            PreparedStatement prepStmt = connection.prepareStatement("UPDATE Zwierze SET Imie = ?, IDWlasciciela = ? WHERE id = ?");
            prepStmt.setString(1, name);
            prepStmt.setInt(2, ownerID);
            prepStmt.setInt(3, id);
            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Animal> dbQueryAnimals() {
        ArrayList<Animal> arrayListAnimals = new ArrayList<Animal>();

        try {
            String queryAnimals = "SELECT id, imie, idwlasciciela FROM Zwierze";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryAnimals);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("imie");
                int ownerID = rs.getInt("idwlasciciela");

                arrayListAnimals.add(new Animal(id, firstName, ownerID));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("query osoba");
        }

        return arrayListAnimals;
    }
}
