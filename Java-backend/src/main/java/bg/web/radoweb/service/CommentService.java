package bg.web.radoweb.service;


import bg.web.radoweb.model.Dto.CommentDto;

import java.util.List;

public interface CommentService {

    void createComment(CommentDto commentDto);

    List<CommentDto> allComment();

    boolean deleteComment(Long id);
}
