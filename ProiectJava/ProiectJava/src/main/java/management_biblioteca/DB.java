package management_biblioteca;

import org.h2.tools.Server;

import java.lang.reflect.Field;
import java.sql.*;

public class DB {

    static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASSWORD = "";

    public static void main(String[] args) {

        try {
            Server webServer = Server.createWebServer(
                    "-webPort", "8082",
                    "-tcpAllowOthers"
            ).start();


            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                createTableClienti(connection);
                createTableCarti(connection);




            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void createTableClienti(Connection connection) throws SQLException {

        String sql = """
                CREATE TABLE clienti (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table 'clienti' created.");
        }
    }


    private static void createTableCarti(Connection connection) throws SQLException {

        String sql = """
                CREATE TABLE carti (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    pagini INT NOT NULL,
                    client_id INT,
                    
                    FOREIGN KEY (client_id)
                    REFERENCES clienti(id)
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table 'carti' created.");
        }
    }


    static void insertClient(Connection connection,
                             String nume,
                             String email) throws SQLException {

        String sql = "INSERT INTO clienti (nume, email) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nume);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Inserted client: " + nume);
        }
    }



    static void insertCarte(Connection connection,
                            String nume,
                            int pagini,
                            int clientId) throws SQLException {

        String sql = """
                INSERT INTO carti (nume, pagini, client_id)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nume);
            ps.setInt(2, pagini);
            ps.setInt(3, clientId);
            ps.executeUpdate();
            System.out.println("Inserted carte: " + nume);
        }
    }

    static void printAllClienti(Connection connection) throws SQLException {

        String sql = """
                SELECT id, nume, email
                FROM clienti
                ORDER BY id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("nume") + " | " +
                                rs.getString("email")
                );
            }
        }
    }



    static void printAllCarti(Connection connection) throws SQLException {

        String sql = """
                SELECT id, nume, pagini, client_id
                FROM carti
                ORDER BY id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("nume") + " | " +
                                rs.getInt("pagini") + " | client_id = " +
                                rs.getInt("client_id")
                );
            }
        }
    }

    static void updateCarte(Connection connection, int id, String nume) throws SQLException {

        String sql = "UPDATE carti SET nume = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nume);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            System.out.println("Updated rows: " + rows);
        }
    }

    static void printCartiCuClient(Connection connection) throws SQLException {

        String sql = """
                SELECT
                    c.id,
                    c.nume AS carte,
                    c.pagini,
                    cl.nume AS client,
                    cl.email
                FROM carti c
                JOIN clienti cl
                    ON c.client_id = cl.id
                ORDER BY c.id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("carte") + " | " +
                                rs.getInt("pagini") + " pagini | " +
                                rs.getString("client") + " | " +
                                rs.getString("email")
                );
            }
        }
    }
}