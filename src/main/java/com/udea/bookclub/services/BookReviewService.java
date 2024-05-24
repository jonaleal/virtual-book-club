package com.udea.bookclub.services;

import com.udea.bookclub.dtos.BookReviewDTO;
import com.udea.bookclub.dtos.mappers.IBookReviewMapper;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.BookReview;
import com.udea.bookclub.repositories.IBookReviewRepository;
import com.udea.bookclub.services.facade.IBookReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookReviewService implements IBookReviewService {

    private final IBookReviewRepository bookReviewRepository;

    private final IBookReviewMapper bookReviewMapper;

    public BookReviewService(IBookReviewRepository bookReviewRepository, IBookReviewMapper bookReviewMapper) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewMapper = bookReviewMapper;
    }

    @Override
    public BookReviewDTO save(BookReviewDTO bookReviewDTO) throws RepositoryException {
        BookReview bookReview = bookReviewMapper.toBookReview(bookReviewDTO);
        return bookReviewMapper.toBookReviewDTO(bookReviewRepository.save(bookReview));
    }

    @Override
    public List<BookReviewDTO> findAll(Pageable pageable) throws RepositoryException {
        return bookReviewMapper.toBookReviewsDTO(bookReviewRepository.findAll(pageable).toList());
    }

    @Override
    public BookReviewDTO findById(Long id) throws RepositoryException {
        Optional<BookReview> bookReview = bookReviewRepository.findById(id);
        if (bookReview.isEmpty()) {
            throw new RepositoryException("BookReview not found");
        }
        return bookReviewMapper.toBookReviewDTO(bookReview.get());
    }

    @Override
    public BookReviewDTO update(BookReviewDTO bookReviewDTO) throws RepositoryException {
        Optional<BookReview> existingBookReview = bookReviewRepository.findById(bookReviewDTO.bookReviewId());
        if (existingBookReview.isEmpty()) {
            throw new RepositoryException("BookReview not found");
        }
        BookReview bookReview = bookReviewMapper.toBookReview(bookReviewDTO);
        return bookReviewMapper.toBookReviewDTO(bookReviewRepository.save(bookReview));
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Optional<BookReview> bookReview = bookReviewRepository.findById(id);
        if (bookReview.isEmpty()) {
            throw new RepositoryException("BookReview not found");
        }
        bookReviewRepository.deleteById(id);
    }
}
