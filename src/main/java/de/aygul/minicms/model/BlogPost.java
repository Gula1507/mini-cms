package de.aygul.minicms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private String author;

    @Column(columnDefinition = "DATE")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    private BlogPostStatus blogPostStatus;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Category> categories = new ArrayList<>();
}
