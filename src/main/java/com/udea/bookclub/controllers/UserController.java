package com.udea.bookclub.controllers;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.dtos.LoginAndSignUpDTO;
import com.udea.bookclub.dtos.ResponseDTO;
import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.Role;
import com.udea.bookclub.services.facade.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @Operation(summary = "Register an user")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<UserDTO>> create(@RequestBody LoginAndSignUpDTO signupRequest) {
        UserDTO user = UserDTO.builder().username(signupRequest.username()).password(signupRequest.password()).role(Role.USER).build();
        try {
            UserDTO savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("User successfully created", savedUser));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get a page of users")
    @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
    @ApiResponse(responseCode = "204", description = "No users found")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> findAll(@ParameterObject Pageable pageable) {
        List<UserDTO> users = userService.findAll(pageable);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>("No users found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Users successfully retrieved", users));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get an user by id ")
    @ApiResponse(responseCode = "200", description = "User successfully retrieved")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<ResponseDTO<UserDTO>> findById(@PathVariable Long userId) {
        try {
            UserDTO user = userService.findById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("User successfully retrieved", user));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @PutMapping("/")
    @Operation(summary = "Update an user")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @ApiResponse(responseCode = "409", description = "Something went wrong")
    public ResponseEntity<ResponseDTO<UserDTO>> update(@RequestBody UserDTO userDTO) {
        try {
            UserDTO updateUser = userService.update(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("User successfully updated", updateUser));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}/joined-book-clubs")
    @Operation(summary = "Get all book clubs joined by an user")
    @ApiResponse(responseCode = "200", description = "Book clubs successfully retrieved")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<ResponseDTO<List<BookClubDTO>>> findBookClubsJoinedByUserId(@PathVariable Long userId) {
        try {
            List<BookClubDTO> bookClubs = userService.findBookClubsJoinedByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Book clubs successfully retrieved", bookClubs));
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
