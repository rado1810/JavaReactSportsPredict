package bg.web.radoweb.exceptions;

public class UrlIsNotCorrect extends RuntimeException {
    public UrlIsNotCorrect(String messages){
        super(messages);
    }
}
