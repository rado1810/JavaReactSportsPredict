package bg.web.radoweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "video_url")
public class VideoUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String url;
    @Column(name = "is_confirmed_by_admin")
    private boolean isConfirmedByAdmin;
    public VideoUrl(){

    }

    public Long getId() {
        return id;
    }

    public VideoUrl setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public VideoUrl setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isConfirmedByAdmin() {
        return isConfirmedByAdmin;
    }

    public VideoUrl setConfirmedByAdmin(boolean confirmedByAdmin) {
        isConfirmedByAdmin = confirmedByAdmin;
        return this;
    }
}
