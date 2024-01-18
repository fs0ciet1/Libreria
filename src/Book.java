public class Book
{
    //=================================== ATTRIBUTI ===================================//
    private String name;
    private String title;
    private String author;
    private String ISBNcode;
    private String addDate;
    private String delDate;
    private String plot;
    private int readCounter;

    //=================================== COSTRUTTORE ===================================//
    public Book(String name, String title, String author, String ISBNcode, String addDate, String delDate, String plot, int readCounter)
    {
        this.name = name;
        this.title = title;
        this.author = author;
        this.ISBNcode = ISBNcode;
        this.addDate = addDate;
        this.delDate = delDate;
        this.plot = plot;
        this.readCounter = readCounter;
    }

    //=================================== GETTER & SETTER ===================================//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBNcode() {
        return ISBNcode;
    }

    public void setISBNcode(String ISBNcode) {
        this.ISBNcode = ISBNcode;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getReadCounter() {
        return readCounter;
    }

    public void setReadCounter(int readCounter) {
        this.readCounter = readCounter;
    }
}
