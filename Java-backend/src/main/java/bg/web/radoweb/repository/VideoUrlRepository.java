package bg.web.radoweb.repository;

import bg.web.radoweb.model.VideoUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoUrlRepository extends JpaRepository<VideoUrl,Long> {


}
