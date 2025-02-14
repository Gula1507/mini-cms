package de.aygul.minicms.mapper;

import de.aygul.minicms.dto.BlogPostRequestDTO;
import de.aygul.minicms.dto.BlogPostResponseDTO;
import de.aygul.minicms.dto.CategoryDTO;
import de.aygul.minicms.mediator.ApplicationMediator;
import de.aygul.minicms.model.BlogPost;
import de.aygul.minicms.model.BlogPostHistory;
import de.aygul.minicms.model.Category;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", imports = {LocalDate.class, ArrayList.class})
public interface BlogPostMapper {
    @Mapping(target = "categories", expression = "java(new ArrayList<>(blogPost.getCategories()))")
    @Mapping(source = "id", target = "blogPostId")
    @Mapping(target = "id", ignore = true)
    BlogPostHistory toBlogPostHistory(BlogPost blogPost);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicationDate", expression = "java(LocalDate.now())")
    @Mapping(target = "blogPostStatus", constant = "DRAFT")
    @Mapping(target = "categories", expression = "java(resolveCategories(blogPostRequestDTO, applicationMediator))")
    @Mapping(target = "version", constant = "1")
    BlogPost toEntity(BlogPostRequestDTO blogPostRequestDTO, @Context ApplicationMediator applicationMediator);

    default List<Category> resolveCategories(BlogPostRequestDTO blogPostRequestDTO,
                                             ApplicationMediator applicationMediator) {
        return blogPostRequestDTO.getCategoriesDTO().stream()
                                 .map(categoryDTO -> applicationMediator.resolveCategoryByName(categoryDTO.getCategoryName()))
                                 .toList();
    }

    @Mapping(target = "categoriesDTO", expression = "java(convertCategoriesToDto(blogPost))")
    BlogPostResponseDTO toResponseDTO(BlogPost blogPost);

    default List<CategoryDTO> convertCategoriesToDto(BlogPost blogPost) {
        return blogPost.getCategories().stream().map(this::toCategoryDTO).toList();
    }

    CategoryDTO toCategoryDTO(Category category);
}

