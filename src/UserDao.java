import java.sql.*;
import java.util.ArrayList;

public class UserDao
{
    //=================================== FUN CONNESSIONE ===================================//
    /*
     * creazione di una funzione di connessione al db per non rimetere la connessione nel CRUD
     */

    public static Connection ConnectionDb()
    {
        Connection dbConnection = null;

        try
        {
            //driver di connessione al db
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Creiamo la stringa di connessione
            String url = "jdbc:mysql://localhost:3306/libreria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Otteniamo una connessione con username e password
            dbConnection = DriverManager.getConnection(url, "root", "1234");

        }
        catch (Exception e)
        {
            System.out.println("ERRORE CONNESSIONE");
        }
        return dbConnection;
    }


    //=================================== FUN RICERCA ===================================//
    /*
     * ricerca nel db l'esistenza di un utente e la sua password
     * restituisce TRUE se lo trova, FALSE se non lo trova
     */

    public static boolean Search() throws SQLException
    {
        PreparedStatement cmd = null;

        try
        {
            String qry = "SELECT * FROM users WHERE name= ? and suername= ? and email = ?";

            cmd = ConnectionDb().prepareStatement(qry);

            // Imposta i parametri nella query (commentati perché non sono utilizzati nel codice)
            /*cmd.setString(1, name);
            cmd.setString(2, surname);
            cmd.setString(3, email);*/

            try (ResultSet resultSet = cmd.executeQuery())
            {
                return resultSet.next(); // Restituisce true se esiste almeno un record
            }
        } catch (SQLException e)
        {
            System.out.println("ERRORE RICERCA");
            return false; // Gestione degli errori
        }

    }

    //=================================== FUN VISUALIZZAZIONE ELENCO UTENTI ===================================//
    public static ArrayList<User> Viewusers() throws SQLException
    {
        PreparedStatement cmd = null;
        ArrayList<User> listUsers = new ArrayList<User>();

        try
        {
            String qry = "SELECT * FROM users";
            cmd = ConnectionDb().prepareStatement(qry);

            //cmd.setString(1, name);

            //Eseguiamo una query e immagazziniamone i risultati in un oggetto ResultSet
            ResultSet res = cmd.executeQuery();

            boolean esiste = res.next();// ---> la prima riga
            // --> seconda riga

            // Stampiamo i risultati riga per riga
            while (esiste)
            {
                // Creazione di una nuova istanza ad ogni iterazione
                User userTmp = new User();

                userTmp.setName(res.getString("name"));
                userTmp.setSurname(res.getString("surname"));
                userTmp.setEmail(res.getString("email"));

                listUsers.add(userTmp);
                esiste = res.next();
            }
        }
        catch (Exception e)
        {
            System.out.println("ERRORE VIEW OPERATION");
        }


        return listUsers;
    }
}
