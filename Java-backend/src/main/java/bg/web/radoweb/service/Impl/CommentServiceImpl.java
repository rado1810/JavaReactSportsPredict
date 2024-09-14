package bg.web.radoweb.service.Impl;

import bg.web.radoweb.model.Comment;
import bg.web.radoweb.model.Dto.CommentDto;

import bg.web.radoweb.repository.CommentRepository;
import bg.web.radoweb.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public void createComment(CommentDto commentDto) {

        Comment comment =modelMapper.map(commentDto,Comment.class);
        commentRepository.save(comment);


    }

    @Override
    public List<CommentDto> allComment() {
        List<Comment> all = commentRepository.findAll();
       List<CommentDto> map = all.stream().map(this::mapToDto).toList();
        return map;
    }
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setAuthor(comment.getAuthor().getUsername());
        commentDto.setTextContent(comment.getTextContent());
        return commentDto;
    }

    @Override
    public boolean deleteComment(Long id) {
        boolean commentIsExists = commentRepository.existsById(id);
        if (commentIsExists){
            commentRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
