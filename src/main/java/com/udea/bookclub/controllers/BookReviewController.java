package com.udea.bookclub.controllers;

import com.udea.bookclub.dtos.BookReviewDTO;
import com.udea.bookclub.dtos.ResponseDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.services.facade.IBookReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Review")
@RestController
@RequestMapping("api/v1/book-review/")
public class BookReviewController {

    private final IBookReviewService bookReviewService;

    public BookReviewController(IBookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @PostMapping("/")
    @Operation(summary = "Create a book review")
    @ApiResponse(responseCode = "201", description = "Book review successfully created")
    public ResponseEntity<ResponseDTO<BookReviewDTO>> create(@RequestBody BookReviewDTO bookReviewDTO) {
        BookReviewDTO savedBookReview = bookReviewService.save(bookReviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Book review successfully created", savedBookReview));
    }

    @GetMapping("/")
    @Operation(summary = "Get a page of book reviews")
    @ApiResponse(responseCode = "200", description = "Book reviews successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No book reviews found")
    public ResponseEntity<ResponseDTO<List<BookReviewDTO>>> findAll(@ParameterObject Pageable pageable) {
        List<BookReviewDTO> bookReviews = bookReviewService.findAll(pageable);
        if (bookReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No book reviews found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Book reviews successfully retrieved", bookReviews));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book review by id")
    @ApiResponse(responseCode = "200", description = "Book review successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Book review not found")
    public ResponseEntity<ResponseDTO<BookReviewDTO>> findById(@PathVariable Long id) {
        try {
            BookReviewDTO bookReview = bookReviewService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Book review successfully retrieved", bookReview));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PutMapping("/")
    @Operation(summary = "Update a book review")
    @ApiResponse(responseCode = "200", description = "Book review successfully updated")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<BookReviewDTO>> update(@RequestBody BookReviewDTO bookReviewDTO) {
        try {
            BookReviewDTO updatedBookReview = bookReviewService.update(bookReviewDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Book review successfully updated", updatedBookReview));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book review by id")
    @ApiResponse(responseCode = "200", description = "Book review successfully deleted")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        try {
            bookReviewService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Book review successfully deleted", null));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
