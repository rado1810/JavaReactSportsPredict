package bg.web.radoweb.exceptions;

public class CommentIsNotCreate extends RuntimeException {
    public CommentIsNotCreate(String messages){
        super(messages);
    }
}
