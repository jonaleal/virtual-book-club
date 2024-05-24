package com.udea.bookclub.controllers;

import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.dtos.ResponseDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.services.facade.IDiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Discussion")
@RestController
@RequestMapping("api/v1/discussion/")
public class DiscussionController {

    private final IDiscussionService discussionService;

    public DiscussionController(IDiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/")
    @Operation(summary = "Create a discussion")
    @ApiResponse(responseCode = "201", description = "Discussion successfully created")
    public ResponseEntity<ResponseDTO<DiscussionDTO>> create(@RequestBody DiscussionDTO discussionDTO) {
        DiscussionDTO savedDiscussion = discussionService.save(discussionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Discussion successfully created", savedDiscussion));
    }

    @GetMapping("/")
    @Operation(summary = "Get a page of discussions")
    @ApiResponse(responseCode = "200", description = "Discussions successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No discussions found")
    public ResponseEntity<ResponseDTO<List<DiscussionDTO>>> findAll(@ParameterObject Pageable pageable) {
        List<DiscussionDTO> discussions = discussionService.findAll(pageable);
        if (discussions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("No discussions found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Discussions successfully retrieved", discussions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a discussion by id")
    @ApiResponse(responseCode = "200", description = "Discussion successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Discussion not found")
    public ResponseEntity<ResponseDTO<DiscussionDTO>> findById(@PathVariable Long id) {
        try {
            DiscussionDTO discussion = discussionService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Discussion successfully retrieved", discussion));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PutMapping("/")
    @Operation(summary = "Update a discussion")
    @ApiResponse(responseCode = "200", description = "Discussion successfully updated")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<DiscussionDTO>> update(@RequestBody DiscussionDTO discussionDTO) {
        try {
            DiscussionDTO updatedDiscussion = discussionService.update(discussionDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Discussion successfully updated", updatedDiscussion));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a discussion by id")
    @ApiResponse(responseCode = "200", description = "Discussion successfully deleted")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        try {
            discussionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Discussion successfully deleted", null));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
