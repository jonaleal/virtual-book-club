package com.udea.bookclub.services.facade;

import com.udea.bookclub.dtos.BookReviewDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookReviewService {

    BookReviewDTO save(BookReviewDTO bookReviewDTO) throws RepositoryException;

    List<BookReviewDTO> findAll(Pageable pageable) throws RepositoryException;

    BookReviewDTO findById(Long id) throws RepositoryException;

    BookReviewDTO update(BookReviewDTO bookReviewDTO) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;
}
