package com.udea.bookclub.controllers;

import com.udea.bookclub.dtos.*;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.services.facade.IBookClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Club")
@RestController
@RequestMapping("api/v1/book-club/")
public class BookClubController {

    private final IBookClubService bookClubService;

    public BookClubController(IBookClubService bookClubService) {
        this.bookClubService = bookClubService;
    }

    @PostMapping("/")
    @Operation(summary = "Create a bookclub")
    @ApiResponse(responseCode = "201", description = "Bookclub successfully created")
    public ResponseEntity<ResponseDTO<BookClubDTO>> create(@RequestBody BookClubDTO bookClubDTO) {
        try {
            BookClubDTO savedBookClub = bookClubService.save(bookClubDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Bookclub successfully created", savedBookClub));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get a page of bookclubs")
    @ApiResponse(responseCode = "200", description = "Bookclubs successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No bookclubs found")
    public ResponseEntity<ResponseDTO<List<BookClubDTO>>> findAll(@ParameterObject Pageable pageable) {
        List<BookClubDTO> bookClubs = bookClubService.findAll(pageable);
        if (bookClubs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No bookclubs found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Bookclubs successfully retrieved", bookClubs));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a bookclub by id")
    @ApiResponse(responseCode = "200", description = "Bookclub successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Bookclub not found")
    public ResponseEntity<ResponseDTO<BookClubDTO>> findById(@PathVariable Long id) {
        try {
            BookClubDTO bookClub = bookClubService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Bookclub successfully retrieved", bookClub));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PutMapping("/")
    @Operation(summary = "Update a bookclub")
    @ApiResponse(responseCode = "200", description = "Bookclub successfully updated")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<BookClubDTO>> update(@RequestBody BookClubDTO bookClubDTO) {
        try {
            BookClubDTO updatedBookClub = bookClubService.update(bookClubDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Bookclub successfully updated", updatedBookClub));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bookclub by id")
    @ApiResponse(responseCode = "200", description = "Bookclub successfully deleted")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        try {
            bookClubService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Bookclub successfully deleted", null));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/users")
    @Operation(summary = "Get users from a bookclub by bookclub id")
    @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No users found")
    @ApiResponse(responseCode = "404", description = "Bookclub not found")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> findUsersByBookClubId(@PathVariable Long id) {
        try {
            List<UserDTO> users = bookClubService.findUsersByBookClubId(id);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No users found", null));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Users successfully retrieved", users));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/discussions")
    @Operation(summary = "Get discussions from a bookclub by bookclub id")
    @ApiResponse(responseCode = "200", description = "Discussions successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No discussions found")
    @ApiResponse(responseCode = "404", description = "Bookclub not found")
    public ResponseEntity<ResponseDTO<List<DiscussionDTO>>> findDiscussionsByBookClubId(@PathVariable Long id) {
        try {
            List<DiscussionDTO> discussions = bookClubService.findDiscussionsByBookClubId(id);
            if (discussions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No discussions found", null));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Discussions successfully retrieved", discussions));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PostMapping("/{bookClubId}/user/{userId}")
    @Operation(summary = "Join an user to a bookclub")
    @ApiResponse(responseCode = "200", description = "User successfully joined")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<UserBookClubDTO>> joinToBookClub(@PathVariable Long bookClubId, @PathVariable Long userId) {
        UserBookClubDTO userBookClub = new UserBookClubDTO(null, userId, bookClubId);
        try {
            UserBookClubDTO userBookClubJoined = bookClubService.joinToBookClub(userBookClub);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("User successfully joined", userBookClubJoined));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
