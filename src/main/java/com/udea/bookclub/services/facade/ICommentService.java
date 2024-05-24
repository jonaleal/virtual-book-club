package com.udea.bookclub.services.facade;

import com.udea.bookclub.dtos.CommentDTO;
import com.udea.bookclub.exceptions.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {
    CommentDTO save(CommentDTO commentDTO) throws RepositoryException;

    List<CommentDTO> findAll(Pageable pageable) throws RepositoryException;

    CommentDTO findById(Long id) throws RepositoryException;

    CommentDTO update(CommentDTO commentDTO) throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
}
