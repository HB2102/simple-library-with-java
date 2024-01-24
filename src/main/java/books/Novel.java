package books;

import java.util.Arrays;

public class Novel extends AbstractBook {

    int i = 0;
    private String[] genres = new String[10];



       public Novel(String title, String author , String genres ){
        super(title , author);

        this.genres = genres.split(",");
    }

    public String[] getGenres(){
        return genres;
    }

    public String getAllGenres() {
           return genres[0]+","+genres[1]+","+genres[2];
    }

    @Override
    public String toString() {
        return "<[ " +title +
                " /"+genres[0]+","+genres[1]+","+genres[2]+" ]>"
                +" from: " + author;
    }
}
