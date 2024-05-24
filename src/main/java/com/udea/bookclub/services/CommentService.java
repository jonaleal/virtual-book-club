package com.udea.bookclub.services;

import com.udea.bookclub.dtos.CommentDTO;
import com.udea.bookclub.dtos.mappers.ICommentMapper;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.Comment;
import com.udea.bookclub.repositories.ICommentRepository;
import com.udea.bookclub.services.facade.ICommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    private final ICommentRepository commentRepository;
    private final ICommentMapper commentMapper;

    public CommentService(ICommentRepository commentRepository, ICommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) throws RepositoryException {
        Comment comment = commentMapper.toComment(commentDTO);
        return commentMapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> findAll(Pageable pageable) throws RepositoryException {
        return commentMapper.toCommentsDTO(commentRepository.findAll(pageable).toList());
    }

    @Override
    public CommentDTO findById(Long id) throws RepositoryException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new RepositoryException("Comment not found");
        }
        return commentMapper.toCommentDTO(comment.get());
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) throws RepositoryException {
        Optional<Comment> existingComment = commentRepository.findById(commentDTO.commentId());
        if (existingComment.isEmpty()) {
            throw new RepositoryException("Comment not found");
        }
        Comment comment = commentMapper.toComment(commentDTO);
        return commentMapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new RepositoryException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}
