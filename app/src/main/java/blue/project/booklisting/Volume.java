package blue.project.booklisting;

public class Volume {
    private String title;
    private String authors;

    public Volume(String title, String authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }
}
