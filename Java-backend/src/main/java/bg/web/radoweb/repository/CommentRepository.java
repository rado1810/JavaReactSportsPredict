package bg.web.radoweb.repository;

import bg.web.radoweb.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByAuthor_username (String string);
}
