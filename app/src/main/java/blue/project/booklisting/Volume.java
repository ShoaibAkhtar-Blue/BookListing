package blue.project.booklisting;

import java.io.Serializable;

public class Volume implements Serializable {
    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String canonicalVolumeLink;

    public Volume(String title, String authors, String publisher, String publishedDate, String description, int pageCount, String canonicalVolumeLink) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }
}
