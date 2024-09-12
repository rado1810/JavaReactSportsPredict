package bg.web.radoweb.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User author;
   // private String author;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "text_content", columnDefinition = "TEXT", nullable = false)
    private String textContent;

    public Comment(){

    }

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Comment setAuthor(User author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Comment setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public Comment setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }
}
