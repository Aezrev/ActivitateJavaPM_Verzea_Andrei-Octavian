package management_biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static management_biblioteca.DB.*;

public class TCPClient {
    public static void main(String[] args) throws IOException, SQLException {
        Socket socket = new Socket("localhost", 5001);

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        insertClient(connection, "A", "andrei@gmail.com");
        insertClient(connection, "Maria", "maria@gmail.com");

        insertCarte(connection, "Harry Potter", 500, 1);
        insertCarte(connection, "Lotr", 1000, 1);
        insertCarte(connection, "Design Patterns", 450, 2);

        printAllClienti(connection);
        printAllCarti(connection);
        printCartiCuClient(connection);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
        );
        out.println("Entries Added");

        socket.close();
    }
}
