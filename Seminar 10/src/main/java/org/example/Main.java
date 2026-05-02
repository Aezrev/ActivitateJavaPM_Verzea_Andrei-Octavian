package org.example;

import org.h2.tools.Server;

import java.sql.*;

public class Main {
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Server webServer = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
            System.out.println("H2 Console started at: http://localhost:8082");

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("Connected to H2 in-memory database.\n");

                createTable(connection);

                insertMovie(connection, "Avatar", "Director1", 2010, 8.8);
                insertMovie(connection, "The Matrix", "Director2", 1999, 8.7);

                System.out.println("===PrintAllMovies");
                printAllMovies(connection);

                updateMovieRating(connection, 1, 9.0);

                System.out.println("===PrintAllMovies UPDATE");
                printAllMovies(connection);

                deleteMovie(connection, 2);

                System.out.println("===PrintAllMovies DELETE");
                printAllMovies(connection);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String sql = """
        CREATE TABLE movies (
            id INT AUTO_INCREMENT PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            director VARCHAR(255),
            release_year INT,
            rating DOUBLE
        )
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table created.");
        }
    }

    private static void insertMovie(Connection connection, String title, String director, int year, double rating) throws SQLException {
        String sql = "INSERT INTO movies (title, director, release_year, rating) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, director);
            ps.setInt(3, year);
            ps.setDouble(4, rating);

            ps.executeUpdate();
            System.out.println("Inserted: " + title);
        }
    }

    private static void printAllMovies(Connection connection) throws SQLException {
        String sql = "SELECT * FROM movies ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("director") + " | " +
                                rs.getInt("release_year") + " | " +
                                rs.getDouble("rating")
                );
            }
        }
    }

    private static void updateMovieRating(Connection connection, int id, double newRating) throws SQLException {
        String sql = "UPDATE movies SET rating = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, newRating);
            ps.setInt(2, id);

            ps.executeUpdate();
            System.out.println("Updated movie id " + id);
        }
    }

    private static void deleteMovie(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Deleted movie id " + id);
        }
    }
}