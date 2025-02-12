package de.aygul.minicms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BlogPostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long blogPostId;
    private String title;
    private String body;
    private String author;

    @Column
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    private BlogPostStatus blogPostStatus;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Category> categories;

    private int version;

}
