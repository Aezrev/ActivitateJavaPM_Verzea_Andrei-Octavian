package exercitiu;

import java.sql.*;

import org.h2.tools.Server;

public class MovieJdbc {

    private static final String URL = "jdbc:h2:mem:moviesdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        try {
            Server webServer = Server.createWebServer(
                    "-webPort", "8082",
                    "-tcpAllowOthers"
            ).start();

            System.out.println("H2 running here: http://localhost:8082");

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connected to database.\n");

            createTable(connection);

            // adaug cateva filme
            insertMovie(connection, "Inception", "Sci-Fi", 2010);
            insertMovie(connection, "The Godfather", "Crime", 1972);
            insertMovie(connection, "Interstellar", "Sci-Fi", 2014);

            System.out.println("\nLista dupa insert:");
            printAllMovies(connection);

            // update la un film
            updateMovieGenre(connection, 2, "Drama / Crime");

            System.out.println("\nLista dupa update:");
            printAllMovies(connection);

            // sterg primul film
            deleteMovie(connection, 1);

            System.out.println("\nLista dupa delete:");
            printAllMovies(connection);

            System.out.println("\nJDBC URL: " + URL);
            System.out.println("Press ENTER to stop...");
            System.in.read();

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // creare tabela
    private static void createTable(Connection connection) throws SQLException {

        String sql =
                "CREATE TABLE movies (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(150) NOT NULL, " +
                        "genre VARCHAR(100) NOT NULL, " +
                        "release_year INT NOT NULL" +
                        ")";

        Statement stmt = connection.createStatement();

        stmt.execute(sql);

        System.out.println("Table movies created.");
    }

    // insert
    private static void insertMovie(Connection connection,
                                    String title,
                                    String genre,
                                    int releaseYear) throws SQLException {

        String sql =
                "INSERT INTO movies (title, genre, release_year) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, title);
        statement.setString(2, genre);
        statement.setInt(3, releaseYear);

        statement.executeUpdate();

        System.out.println("Added: " + title);
    }

    // afisare filme
    private static void printAllMovies(Connection connection) throws SQLException {

        String sql =
                "SELECT id, title, genre, release_year FROM movies ORDER BY id";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            String title = rs.getString("title");
            String genre = rs.getString("genre");
            int year = rs.getInt("release_year");

            System.out.println(
                    id + " | " +
                            title + " | " +
                            genre + " | " +
                            year
            );
        }
    }

    // update
    private static void updateMovieGenre(Connection connection,
                                         int id,
                                         String newGenre) throws SQLException {

        String sql =
                "UPDATE movies SET genre = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, newGenre);
        statement.setInt(2, id);

        statement.executeUpdate();

        System.out.println("Movie updated: " + id);
    }

    // delete
    private static void deleteMovie(Connection connection,
                                    int id) throws SQLException {

        String sql =
                "DELETE FROM movies WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.executeUpdate();

        System.out.println("Deleted movie id " + id);
    }
}