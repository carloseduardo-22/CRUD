import java.sql.SQLException;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {
    private String databaseName;
    private JdbcConnectionSource connection;

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }

    public JdbcConnectionSource getConnection()
            throws SQLException {

        if (databaseName == null) {
            throw new SQLException("Nome do banco nao informado");
        }

        if (connection == null) {
            connection = new JdbcConnectionSource(
                "jdbc:sqlite:" + databaseName
            );

            System.out.println("Banco conectado");
        }

        return connection;
    }

    
public void close() {
    if (connection != null) {
        try {
            connection.close();
            connection = null;
            System.out.println("Banco desconectado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

}