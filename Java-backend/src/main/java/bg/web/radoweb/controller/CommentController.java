package bg.web.radoweb.controller;

import bg.web.radoweb.exceptions.CommentIsNotCreate;
import bg.web.radoweb.model.Dto.CommentDto;
import bg.web.radoweb.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
      try {
          commentService.createComment(commentDto);
          return ResponseEntity.ok("Comment is create.");
      }catch (CommentIsNotCreate e) {
          return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
      }
    }
    @GetMapping ("/allComment")
    public ResponseEntity<List<CommentDto>> getAllComment() {
        List<CommentDto> comments = commentService.allComment();
        return   ResponseEntity.ok(comments);

    }
    @DeleteMapping ("/allComment{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<String> deleteComment(@PathVariable  Long id) {
        boolean delete= commentService.deleteComment(id);
        if (delete){
            return ResponseEntity.ok("Comment is delete");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment is not delete!");
        }
        }
}
