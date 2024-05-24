package com.udea.bookclub.services;

import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.dtos.mappers.IDiscussionMapper;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.Discussion;
import com.udea.bookclub.repositories.IDiscussionRepository;
import com.udea.bookclub.services.facade.IDiscussionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService implements IDiscussionService {

    private final IDiscussionRepository discussionRepository;
    private final IDiscussionMapper discussionMapper;

    public DiscussionService(IDiscussionRepository discussionRepository, IDiscussionMapper discussionMapper) {
        this.discussionRepository = discussionRepository;
        this.discussionMapper = discussionMapper;
    }

    @Override
    public DiscussionDTO save(DiscussionDTO discussionDTO) throws RepositoryException {
        Discussion discussion = discussionMapper.toDiscussion(discussionDTO);
        return discussionMapper.toDiscussionDTO(discussionRepository.save(discussion));
    }

    @Override
    public List<DiscussionDTO> findAll(Pageable pageable) throws RepositoryException {
        return discussionMapper.toDiscussionsDTO(discussionRepository.findAll(pageable).toList());
    }

    @Override
    public DiscussionDTO findById(Long id) throws RepositoryException {
        Optional<Discussion> discussion = discussionRepository.findById(id);
        if (discussion.isEmpty()) {
            throw new RepositoryException("Discussion not found");
        }
        return discussionMapper.toDiscussionDTO(discussion.get());
    }

    @Override
    public DiscussionDTO update(DiscussionDTO discussionDTO) throws RepositoryException {
        Optional<Discussion> existingDiscussion = discussionRepository.findById(discussionDTO.discussionId());
        if (existingDiscussion.isEmpty()) {
            throw new RepositoryException("Discussion not found");
        }
        Discussion discussion = discussionMapper.toDiscussion(discussionDTO);
        return discussionMapper.toDiscussionDTO(discussionRepository.save(discussion));
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Optional<Discussion> discussion = discussionRepository.findById(id);
        if (discussion.isEmpty()) {
            throw new RepositoryException("Discussion not found");
        }
        discussionRepository.deleteById(id);
    }
}
