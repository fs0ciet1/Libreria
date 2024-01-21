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
    public static boolean InsertBook(String name, String title, String author, String ISBNCode, String addition_date,String plot, int readCounter) throws SQLException
    {
        PreparedStatement cmd = null;
        //inserimento effettivo di una nuovo record nel db
        try {
            String updateTableSQL = "INSERT INTO books(NAME, title, author, isbn_code, add_date, del_date, plot, read_counter) VALUES(?,?,?,?,?,NULL,?,?)";
            cmd = ConnectionDb().prepareStatement(updateTableSQL);

            cmd.setString(1, name);
            cmd.setString(2, title);
            cmd.setString(3, author);
            cmd.setString(4, ISBNCode);
            cmd.setString(5, addition_date); //inserire dati in formato AAAA-MM-DD
            cmd.setString(6, plot);
            cmd.setInt(7, readCounter);

            //esercuzione query
            cmd.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println("ERRORE INSERT");
        }
        return true;
    }

    //=================================== FUN UPDATE LIBRI ===================================//
    public static void UpdateBook (String name, String title, String author, String ISBNCode, String addition_date,String plot, int readCounter, String specificBook) throws SQLException
    {
        PreparedStatement cmd = null;

        try
        {
            String updateTableSQL = "UPDATE books SET NAME = ?, title=?, author=?, isbn_code=?, add_date=?, del_date= NULL, plot=?, read_counter=? WHERE NAME=? AND title = ?";
            cmd = ConnectionDb().prepareStatement(updateTableSQL);

            //set
            cmd.setString(1, name);
            cmd.setString(2, title);
            cmd.setString(3, author);
            cmd.setString(4, ISBNCode);
            cmd.setString(5, addition_date); //passaggio di una variabile temporanea dove ho il titolo del libro da modificare
            cmd.setString(6, plot);
            cmd.setInt(7, readCounter);

            //where
            cmd.setString(8, name);
            cmd.setString(9, specificBook); //passaggio di una variabile temporanea dove ho il titolo del libro da modificare

            //esercuzione query
            cmd.executeUpdate();

        }catch(Exception e)
        {
            System.out.println("ERRORE UPDATE");
        }
    }

    //=================================== FUN DELETE LIBRI ===================================//
    public static void DeleteBook (String name, String title, String deletion_date) throws SQLException
    {
        PreparedStatement cmd = null;

        try {

            String updateTableSQL = "UPDATE books SET del_date= ? WHERE NAME=? AND title = ?";
            cmd = ConnectionDb().prepareStatement(updateTableSQL);

            //set
            cmd.setString(1, deletion_date);

            //where
            cmd.setString(2, name);
            cmd.setString(3, title);

            // Esecuzione query
            cmd.executeUpdate();

        } catch (Exception e)
        {
            System.out.println("ERRORE DELETE");
        }
    }


    //=================================== FUN VISUALIZZAZIONE ELENCO LIBRI ===================================//
    //gli passo solo in ome perche per il momento decido se sia la primaryKey
    public static ArrayList<Book> ViewBooks(String name) throws SQLException
    {
        PreparedStatement cmd = null;
        ArrayList<Book> listBooks = new ArrayList<Book>();

        try
        {
            String qry = "SELECT * FROM books WHERE NAME = ? AND del_date IS NULL";
            cmd = ConnectionDb().prepareStatement(qry);
            cmd.setString(1, name);

            //Eseguiamo una query e immagazziniamone i risultati in un oggetto ResultSet
            ResultSet res = cmd.executeQuery();

            boolean esiste = res.next();// ---> la prima riga
            // --> seconda riga

            // Stampiamo i risultati riga per riga
            while (esiste)
            {
                // Creazione di una nuova istanza ad ogni iterazione
                Book bookTmp = new Book(
                        res.getString("name"),
                        res.getString("title"),
                        res.getString("author"),
                        res.getString("isbn_code"),
                        res.getString("add_date"),
                        res.getString("del_date"),
                        res.getString("plot"),
                        res.getInt("read_counter")
                );

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
