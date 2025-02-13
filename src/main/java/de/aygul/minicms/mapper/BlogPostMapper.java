package de.aygul.minicms.mapper;

import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;


@Mapper(componentModel = "spring", imports = {ArrayList.class})
public interface BlogPostMapper {
    @Mapping(target = "categories", expression = "java(new ArrayList<>(blogPost.getCategories()))")
    @Mapping(source = "id", target = "blogPostId")
    @Mapping(target = "id", ignore = true)
    BlogPostHistory toBlogPostHistory(BlogPost blogPost);


}
