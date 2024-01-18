import java.sql.*;
import java.util.ArrayList;

public class BookDao
{
    //=================================== FUN CONNESSIONE ===================================//
    /*
     * creazione di una funzione di connessione al db per non rimettere la connessione nel CRUD
     */

    public static Connection ConnectionDb()
    {
        Connection dbConnectionOperation = null;

        try
        {
            //driver di connessione al db
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Creiamo la stringa di connessione
            String url = "jdbc:mysql://localhost:3306/libreria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Otteniamo una connessione con username e password
            dbConnectionOperation = DriverManager.getConnection(url, "root", "1234");

        }
        catch (Exception e)
        {
            System.out.println("ERRORE CONNESSIONE");
        }
        return dbConnectionOperation;
    }

    //=================================== FUN INSERIMENTO LIBRI ===================================//
    public static boolean InsertBook(String name, String titolo, String autore, String codiceISBN, String dataAggiunta, String dataEliminazione, String trama, int numeroLetture) throws SQLException
    {
        PreparedStatement cmd = null;

        //inserimento effettivo di una nuovo record nel db
        try {
            String updateTableSQL = "INSERT INTO libreria(name, titolo, autore, codiceISBN, dataAggiunta, dataEliminazione, trama, numeroLetture) VALUES(?,?, ?,?,?,?,?, ?)";
            cmd = ConnectionDb().prepareStatement(updateTableSQL);

            cmd.setString(1, name);
            cmd.setString(2, titolo);
            cmd.setString(3, autore);
            cmd.setString(4, codiceISBN);
            cmd.setString(5, dataAggiunta);
            cmd.setString(6, dataEliminazione);
            cmd.setString(7, trama);
            cmd.setInt(8, numeroLetture);

            //esercuzione query
            cmd.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println("ERRORE INSERT");
        }
        return true;
    }

    //=================================== FUN VISUALIZZAZIONE ELENCO LIBRI ===================================//

    //gli passo sola la mail perche per il momento decido se sia la primaryKey
    public static ArrayList<Book> ViewBooks(String name) throws SQLException
    {
        PreparedStatement cmd = null;
        ArrayList<Book> listBooks = new ArrayList<Book>();

        try
        {

            String qry = "SELECT * FROM books";
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
                Book bookTmp = new Book(
                    res.getString("NAME"),
                    res.getString("title"),
                    res.getString("author"),
                    res.getString("ISBN_code"),
                    res.getString("addition_date"),
                    res.getString("deletion_date"),
                    res.getString("plot"),
                    res.getInt("number_of_lessions")
                );

                //System.out.println(res.getString("NAME"));
                //System.out.println(res.getString("title"));

                /*System.out.println(res.getString("autore"));
                System.out.println(res.getString("codice_isbn"));
                System.out.println(res.getString("data_aggiunta"));
                System.out.println(res.getString("data_eliminazione"));
                System.out.println(res.getString("trama"));
                System.out.println(res.getString("numero_letture"));*/

                listBooks.add(bookTmp);
                esiste = res.next();
            }
        }
        catch (Exception e)
        {
            System.out.println("ERRORE BOOK OPERATION");
        }
        return listBooks;
    }
}
