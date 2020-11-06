package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class CardDAO {
    String url;

    private Connection connection;
    private Statement statement;
    SQLiteDataSource dataSource;

    /**
     * Connects with database and if it doesn't contain table "card", creates it
     * @param fileName name and path to the file with database
     */
    public void init(String fileName) {
        url = "jdbc:sqlite:" + fileName;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try {
            connection = dataSource.getConnection();
            if (connection.isValid(5)) {
                System.out.println("Connection is valid.");
            }
            try {
                statement = connection.createStatement();
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Adds new card to "card" table
     * @param number card number
     * @param pin pin to card
     * @param balance balance on an account with specified card number
     */
    public void addCard(String number, String pin, int balance){
        try {
            statement.executeUpdate("INSERT INTO card (number, pin, balance) VALUES " +
                    "(" + number + ",'" + pin + "'," + balance + ");");
        }catch(SQLException e) {
            System.out.println("Failed to write to database: operation ADD NEW CARD");
        }
    }


    /**
     * Adds new card to "card" table
     * @param number card number
     * @param pin pin to card
     */
    public void addCard(String number, String pin){
        try {
            statement.executeUpdate("INSERT INTO card (number, pin) VALUES " +
                    "(" + number + ",'" + pin + "');");
        }catch(SQLException e) {
            System.out.println("Failed to write to database: operation ADD NEW CARD");
        }
    }


    /**
     * Finds card with specified number and pin
     * (it means you need to know either number and pin to get access to it)
     * @param number card number
     * @param pin pin to card
     * @return card if found, null otherwise
     */
    public Card findCardByNumberAndPin(String number, String pin) {
        //String pin2 = "%" + pin + "%";
        try (ResultSet cards = statement.executeQuery("SELECT balance FROM card WHERE number="+number+" AND pin='"+pin+"';")) {
            if (cards.next()) {
                int balance = cards.getInt("balance");
                return new Card(this, number, pin, balance);
            }
        }catch(SQLException e) {
            System.out.println("Failed to read from database: operation FIND CARD");
        }
        return null;
    }


    /**
     * Finds card only by its number
     * @param number card number
     * @return card if found, null otherwise
     */
    public Card findCardByNumber(String number) {
        try (ResultSet cards = statement.executeQuery("SELECT balance, pin FROM card WHERE number="+number+";")) {
            if (cards.next()) {
                int balance = cards.getInt("balance");
                String pin = cards.getString("pin");
                return new Card(this, number, pin, balance);
            }
        }catch(SQLException e) {
            System.out.println("Failed to read from database: operation FIND CARD");
        }
        return null;
    }


    /**
     * Chesks if there isn't already a card with specified card number
     * @param number card number
     * @return true if there is not, false otherwise
     */
    public boolean checkIfUnique(String number) {
        try (ResultSet cards = statement.executeQuery("SELECT number FROM card;")) {
            while (cards.next()) {
                String num = cards.getString("number");
                if (num.substring(6, 15).equals(number)) return false;
            }
        }catch(SQLException e) {
            System.out.println("Failed to read from database.");
        }
        return true;
    }


    /**
     * Adds income to balance to card with specified card number
     * @param income income to add
     * @param number card number
     */
    public void addIncome(int income, String number) {
        try {
            statement.executeUpdate("UPDATE card SET balance=balance+" + income + " WHERE number=" + number+ ";");
        }catch(SQLException e) {
            System.out.println("Failed to write to database: operation ADD INCOME");
        }
    }


    /**
     * Gets balance on a card with specified card number
     * @param number card number
     * @return balance if card was found, null otherwise
     */
    public Integer getBalance(String number) {
        try (ResultSet cards = statement.executeQuery("SELECT balance FROM card WHERE number="+number+";")) {
            if (cards.next()) {
                int balance = cards.getInt("balance");
                return balance;
            }
        }catch(SQLException e) {
            System.out.println("Failed to read from database: operation GET BALANCE");
        }
        return null;
    }


    /**
     * deletes card with specified card number
     * @param number card number
     */
    public void deleteCard(String number) {
        try {
            statement.executeUpdate("DELETE FROM card WHERE number=" + number + ";");
        }catch(SQLException e) {
            System.out.println("Failed to write to database: operation DELETE CARD");
        }
    }
}
