package com.udea.bookclub.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_review_id")
    private Long bookReviewId;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "review", nullable = false, columnDefinition = "TEXT")
    private String review;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "created_at", columnDefinition = "DATE", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_club_id", referencedColumnName = "book_club_id")
    private BookClub bookClub;

}
