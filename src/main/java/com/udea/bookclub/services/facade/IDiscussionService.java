package com.udea.bookclub.services.facade;

import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDiscussionService {
    DiscussionDTO save(DiscussionDTO discussionDTO) throws RepositoryException;

    List<DiscussionDTO> findAll(Pageable pageable) throws RepositoryException;

    DiscussionDTO findById(Long id) throws RepositoryException;

    DiscussionDTO update(DiscussionDTO discussionDTO) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;
}
