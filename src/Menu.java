import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    //=================================== COSTRUTTORE DI DEFAULT ===================================//
    public  Menu() throws IOException, SQLException
    {
        PrintUsers();

    }

    public void PrintUsers () throws SQLException
    {
        System.out.println("Elenco utenti: ");
        ArrayList<User> listaUtentiMenu = new ArrayList<User>();
        listaUtentiMenu = UserDao.Viewusers();

        for (int i = 0; i < listaUtentiMenu.size(); i++)
        {
            System.out.println( i + " nome: " + listaUtentiMenu.get(i).getName() + " cognome: " + listaUtentiMenu.get(i).getSurname() + " email: " + listaUtentiMenu.get(i).getEmail() );
        }


        System.out.println("Quale utente vuoi scegliere?");
        Scanner input = new Scanner(System.in);
        int sceltaUtente = input.nextInt();

        //una volta scelto l utente, mi restituisce il suo nome nella posizione presa da input
        System.out.println(listaUtentiMenu.get(sceltaUtente).getName());
        PrintBooks(listaUtentiMenu.get(sceltaUtente).getName());

    }

    public  void PrintBooks (String name) throws SQLException
    {
        System.out.println("Elenco libri: ");
        ArrayList<Book> listaLibriMenu = new ArrayList<Book>();
        listaLibriMenu = BookDao.ViewBooks(name);

        for (int i = 0; i < listaLibriMenu.size(); i++)
        {
            System.out.println( i + " nome: " + listaLibriMenu.get(i).getName() + " titolo: " + listaLibriMenu.get(i).getTitle());
        }


    }
}

