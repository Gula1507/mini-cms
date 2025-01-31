package de.aygul.minicms.repository;

import de.aygul.minicms.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository <BlogPost, Long>{
}
