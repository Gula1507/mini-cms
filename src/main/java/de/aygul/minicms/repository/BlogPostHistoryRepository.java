package de.aygul.minicms.repository;

import de.aygul.minicms.model.BlogPostHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostHistoryRepository extends JpaRepository<BlogPostHistory,Long> {
}
