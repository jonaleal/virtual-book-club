package com.udea.bookclub.services.facade;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.dtos.UserBookClubDTO;
import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookClubService {

    BookClubDTO save(BookClubDTO bookClubDTO) throws RepositoryException;

    List<BookClubDTO> findAll(Pageable pageable) throws RepositoryException;

    BookClubDTO findById(Long id) throws RepositoryException;

    BookClubDTO update(BookClubDTO bookClubDTO) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;

    List<UserDTO> findUsersByBookClubId(Long id) throws RepositoryException;

    List<DiscussionDTO> findDiscussionsByBookClubId(Long id) throws RepositoryException;

    UserBookClubDTO joinToBookClub(UserBookClubDTO userBookClubDTO) throws RepositoryException;
}
