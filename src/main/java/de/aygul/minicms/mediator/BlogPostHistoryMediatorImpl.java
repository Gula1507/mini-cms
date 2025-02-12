package de.aygul.minicms.mediator;

import de.aygul.minicms.model.BlogPostHistory;
import de.aygul.minicms.repository.BlogPostHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostHistoryMediatorImpl implements BlogPostHistoryMediator{
    private final BlogPostHistoryRepository blogPostHistoryRepository;

    @Override
    public void save(BlogPostHistory blogPostHistory) {
        blogPostHistoryRepository.save(blogPostHistory);
    }
}
