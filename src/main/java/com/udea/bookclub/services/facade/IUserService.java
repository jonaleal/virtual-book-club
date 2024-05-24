package com.udea.bookclub.services.facade;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    UserDTO save(UserDTO userDTO) throws RepositoryException;

    List<UserDTO> findAll(Pageable pageable) throws RepositoryException;
    
    UserDTO findById(Long id) throws RepositoryException;

    UserDTO update(UserDTO userDTO) throws RepositoryException;

    UserDTO findByUsername(String username);

    List<BookClubDTO> findBookClubsJoinedByUserId(Long userId);
}
