import javax.xml.namespace.QName;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
public class Menu
{
    // =================================== COSTRUTTORE DI DEFAULT ===================================//
    public Menu() throws IOException, SQLException {
        PrintUsers();
    }

    public void PrintUsers() throws SQLException {
        System.out.println("Elenco utenti: ");
        ArrayList<User> listaUtentiMenu = UserDao.Viewusers();


        for (int i = 0; i < listaUtentiMenu.size(); i++)
        {
            System.out.println(i +1 + " nome: " + listaUtentiMenu.get(i).getName() + " cognome: " + listaUtentiMenu.get(i).getSurname() + " email: " + listaUtentiMenu.get(i).getEmail());
        }

        // faccio scegliere all'utente con quale nome loggarsi
        System.out.println("Quale utente vuoi scegliere?");
        Scanner input = new Scanner(System.in);
        int sceltaUtente = input.nextInt()-1;

        // una volta scelto l'utente, mi restituisce il suo nome nella posizione presa da input
        String selectedUserName = listaUtentiMenu.get(sceltaUtente).getName();
        System.out.println(selectedUserName);

        PrintMenu(selectedUserName);
        //PrintBooks(selectedUserName);

    }

    public void PrintMenu(String name) throws SQLException
    {
        Scanner inputMenu = new Scanner(System.in);

        System.out.println("Cosa vuoi fare?");
        System.out.println("A: Aggiungi libro");
        System.out.println("B: Modifica libro");
        System.out.println("C: Elimina libro");
        System.out.println("D: Visualizza info su libro");
        System.out.println("E: Esci");

        String inputChoice = inputMenu.nextLine();


        if(inputChoice.equalsIgnoreCase("a"))
        {
            AddBook(name);
        }

        //=================================== FUN MODIFCIA LIBRO ===================================//
        if(inputChoice.equalsIgnoreCase("B"))
        {
            ModifyBook(name);
        }


        if (inputChoice.equalsIgnoreCase("C"))
        {
            DeleteBook(name);
        }

        if(inputChoice.equalsIgnoreCase("D"))
        {
            PrintBooks(name);
        }

    }

    //=================================== FUN AGGIUNTA LIBRO ===================================//
    public void AddBook(String email) throws SQLException {
        Scanner addBook = new Scanner(System.in);

        System.out.println("Inserisci titolo");
        String addTitle = addBook.nextLine();

        System.out.println("Inserisci autore");
        String addAuthor = addBook.nextLine();

        System.out.println("Inserisci codice ISBN");
        String addISBNCode = addBook.nextLine();

        System.out.println("Inserisci data di inserimento");
        String addDate = addBook.nextLine();

        System.out.println("Inserisci trama");
        String addPlot = addBook.nextLine();

        System.out.println("Inserisci numero di letture");
        int addReadCounter = addBook.nextInt();

        // Chiamata al metodo InsertBook di BookDao e gli passo la lista degli argomenti passati al metodo InsertBook.
        BookDao.InsertBook(email, addTitle, addAuthor, addISBNCode, addDate, addPlot ,addReadCounter );
    }

    //=================================== FUN MODIFICA LIBRO ===================================//
    public void ModifyBook(String name) throws SQLException
    {
        ArrayList<Book> listaLibriMenu = BookDao.ViewBooks(name);
        String specificBook;

        // Stampa la lista dei libri dell'utente
        System.out.println("Elenco libri per l'utente " + name + ": ");
        for (int i = 0; i < listaLibriMenu.size(); i++)
        {
            System.out.println(i + 1 +
                    " Title: "+ listaLibriMenu.get(i).getTitle() + "|" +
                    " Author: "+ listaLibriMenu.get(i).getAuthor() + "|" +
                    " ISBNCode; "+ listaLibriMenu.get(i).getISBNcode() + "|" +
                    " addDate: " + listaLibriMenu.get(i).getAddDate() + "|" +
                    " delDate: " + listaLibriMenu.get(i).getDelDate() + "|" +
                    " Plot: " + listaLibriMenu.get(i).getPlot() + "|" +
                    "readCounter: " + listaLibriMenu.get(i).getReadCounter()
            );
        }

        // Chiedi all'utente quale libro vuole modificare
        System.out.println("Quale libro vuoi modificare? Inserisci il numero corrispondente.");
        Scanner inputBook = new Scanner(System.in);
        int sceltaLibro = inputBook.nextInt();

        //segno ad una variabile temporanea il titolo del libro da modificare
        specificBook = listaLibriMenu.get(sceltaLibro-1).getTitle();


        Scanner inputModify = new Scanner(System.in);

        System.out.println("Inserisci titolo");
        String addTitle = inputModify.nextLine();

        System.out.println("Inserisci autore");
        String addAuthor = inputModify.nextLine();

        System.out.println("Inserisci codice ISBN");
        String addISBNCode = inputModify.nextLine();

        System.out.println("Inserisci data di inserimento");
        String addDate = inputModify.nextLine();

        System.out.println("Inserisci trama");
        String addPlot = inputModify.nextLine();

        System.out.println("Inserisci numero di letture");
        int addReadCounter = inputModify.nextInt();

        // Chiamata al metodo InsertBook di BookDao e gli passo la lista degli argomenti passati al metodo InsertBook.
        BookDao.UpdateBook(name,addTitle, addAuthor, addISBNCode,addDate,addPlot,addReadCounter, specificBook );

    }
    //=================================== FUN ELIMINA LIBRO ===================================//
    public void DeleteBook(String name) throws SQLException
    {
        // mostro i libri nella libreria
        System.out.println("Elenco libri per l'utente " + name + ": ");

        ArrayList<Book> listaLibriMenu = new ArrayList<Book>();
        listaLibriMenu = BookDao.ViewBooks(name);

        // inizializzo oggetto per ottenera la data corente
        LocalDate today = LocalDate.now();

        //restituisce tuti i libri d iun determinato utente
        for (int i = 0; i < listaLibriMenu.size(); i++)
        {
            System.out.println(i + 1 + " nome: " + listaLibriMenu.get(i).getName() + " titolo: " + listaLibriMenu.get(i).getTitle());
        }

        //scelta libro da eliminare
        System.out.println("Quale libro vuoi scegliere?");
        Scanner inputBook = new Scanner(System.in);
        int sceltaLibro = inputBook.nextInt();

        // ottieni il titolo del libro selezionato
        String selectedTitle = listaLibriMenu.get(sceltaLibro-1).getTitle();

        // Chiamata al metodo DeleteBook di BookDao passando il nome e il titolo del libro selezionato
        BookDao.DeleteBook(name, selectedTitle, today.toString());
    }


    public void PrintBooks(String name) throws SQLException
    {
        ArrayList<Book> listaLibriMenu = new ArrayList<Book>();
        listaLibriMenu = BookDao.ViewBooks(name);


        for (int i = 0; i < listaLibriMenu.size(); i++)
        {
            System.out.println(i +1 + " titolo: " + listaLibriMenu.get(i).getTitle());
        }

        //qui sto stampando la lista di libri
        System.out.println("Quale libro vuoi scegliere?");
        Scanner inputBook = new Scanner(System.in);
        int sceltaLibro = inputBook.nextInt()-1;

        // una volta scelto il libro, mi restituisce il suo titolo nella posizione presa da input
        System.out.println(
                "Title: "+ listaLibriMenu.get(sceltaLibro).getTitle() + "\n" +
                        "Author: "+ listaLibriMenu.get(sceltaLibro).getAuthor() + "\n" +
                        "ISBNCode; "+ listaLibriMenu.get(sceltaLibro).getISBNcode() + "\n" +
                        "addDate: " + listaLibriMenu.get(sceltaLibro).getAddDate() + "\n" +
                        "delDate: " + listaLibriMenu.get(sceltaLibro).getDelDate() + "\n" +
                        "Plot: " + listaLibriMenu.get(sceltaLibro).getPlot() + "\n" +
                        "readCounter: " + listaLibriMenu.get(sceltaLibro).getReadCounter()
        );
    }
}


