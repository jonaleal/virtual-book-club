package com.udea.bookclub.controllers;

import com.udea.bookclub.dtos.CommentDTO;
import com.udea.bookclub.dtos.ResponseDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.services.facade.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment")
@RestController
@RequestMapping("api/v1/comment/")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    @Operation(summary = "Create a comment")
    @ApiResponse(responseCode = "201", description = "Comment successfully created")
    public ResponseEntity<ResponseDTO<CommentDTO>> create(@RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.save(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Comment successfully created", savedComment));
    }

    @GetMapping("/")
    @Operation(summary = "Get a page of comments")
    @ApiResponse(responseCode = "200", description = "Comments successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No comments found")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>>findAll(@ParameterObject Pageable pageable) {
        List<CommentDTO> comments = commentService.findAll(pageable);
        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No comments found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Comments successfully retrieved", comments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a comment by id")
    @ApiResponse(responseCode = "200", description = "Comment successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    public ResponseEntity<ResponseDTO<CommentDTO>> findById(@PathVariable Long id) {
        try {
            CommentDTO comment = commentService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Comment successfully retrieved", comment));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PutMapping("/")
    @Operation(summary = "Update a comment")
    @ApiResponse(responseCode = "200", description = "Comment successfully updated")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<CommentDTO>> update(@RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO updatedComment = commentService.update(commentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Comment successfully updated", updatedComment));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment by id")
    @ApiResponse(responseCode = "200", description = "Comment successfully deleted")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        try {
            commentService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Comment successfully deleted", null));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
