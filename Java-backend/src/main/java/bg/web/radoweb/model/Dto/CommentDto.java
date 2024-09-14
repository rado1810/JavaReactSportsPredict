package bg.web.radoweb.model.Dto;



import java.time.LocalDateTime;

public class CommentDto {
    private String author;

    private LocalDateTime created;

    private String textContent;



    public CommentDto(){

    }

    public String getAuthor() {
        return author;
    }

    public CommentDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentDto setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }
}
