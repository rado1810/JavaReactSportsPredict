package bg.web.radoweb.model.Dto;

public class VideoUrlDto {
    private Long id;
    private String url;

    private boolean isConfirmedByAdmin;

    public VideoUrlDto(){

    }

    public Long getId() {
        return id;
    }

    public VideoUrlDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public VideoUrlDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isConfirmedByAdmin() {
        return isConfirmedByAdmin;
    }

    public VideoUrlDto setConfirmedByAdmin(boolean confirmedByAdmin) {
        isConfirmedByAdmin = confirmedByAdmin;
        return this;
    }
}
