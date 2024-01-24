package books;

public abstract class AbstractBook {

    protected String title;
    protected String author;
    protected boolean borrowed = false;
    protected boolean returned = true;

    public AbstractBook(String title , String author){
        this.title = title;
        this.author = author;
    }


    public boolean borrowed(){
        borrowed = true;
        returned = false;
        return borrowed;
    }

    public boolean returned(){
        borrowed = false;
        returned = true;
        return returned;
    }

    public boolean isBorrowed(){
        return borrowed;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }

    @Override
    public String toString() {
        return "AbstractBook{" +
                "title='" + title + '\'' +
                ", author='" + author + //'\'' +
//                ", borrowed=" + borrowed +
//                ", returned=" + returned +
                '}';
    }
}