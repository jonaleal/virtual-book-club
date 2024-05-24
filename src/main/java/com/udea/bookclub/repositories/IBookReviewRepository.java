package com.udea.bookclub.repositories;

import com.udea.bookclub.models.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookReviewRepository extends JpaRepository<BookReview, Long> {
}
