package com.udea.bookclub.services;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.dtos.UserBookClubDTO;
import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.dtos.mappers.IBookClubMapper;
import com.udea.bookclub.dtos.mappers.IDiscussionMapper;
import com.udea.bookclub.dtos.mappers.IUserBookClubMapper;
import com.udea.bookclub.dtos.mappers.IUserMapper;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.BookClub;
import com.udea.bookclub.models.User;
import com.udea.bookclub.repositories.IBookClubRepository;
import com.udea.bookclub.repositories.IDiscussionRepository;
import com.udea.bookclub.repositories.IUserBookClubRepository;
import com.udea.bookclub.repositories.IUserRepository;
import com.udea.bookclub.services.facade.IBookClubService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookClubService implements IBookClubService {

    private final IBookClubRepository bookClubRepository;
    private final IBookClubMapper bookClubMapper;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final IDiscussionRepository discussionRepository;
    private final IDiscussionMapper discussionMapper;
    private final IUserBookClubRepository userBookClubRepository;
    private final IUserBookClubMapper userBookClubMapper;

    public BookClubService(IBookClubRepository bookClubRepository, IBookClubMapper bookClubMapper, IUserRepository userRepository, IUserMapper userMapper, IDiscussionRepository discussionRepository, IDiscussionMapper discussionMapper, IUserBookClubRepository userBookClubRepository, IUserBookClubMapper userBookClubMapper) {
        this.bookClubRepository = bookClubRepository;
        this.bookClubMapper = bookClubMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.discussionRepository = discussionRepository;
        this.discussionMapper = discussionMapper;
        this.userBookClubRepository = userBookClubRepository;
        this.userBookClubMapper = userBookClubMapper;
    }

    @Override
    public BookClubDTO save(BookClubDTO bookClubDTO) {
        Optional<User> user = userRepository.findById(bookClubDTO.userId());
        if (user.isEmpty()) {
            throw new RepositoryException("User not found");
        }
        BookClub bookClub = bookClubMapper.toBookClub(bookClubDTO);
        return bookClubMapper.toBookClubDTO(bookClubRepository.save(bookClub));
    }

    @Override
    public List<BookClubDTO> findAll(Pageable pageable) {
        return bookClubMapper.toBookClubsDTO(bookClubRepository.findAll(pageable).toList());
    }

    @Override
    public BookClubDTO findById(Long id) {
        Optional<BookClub> bookClub = bookClubRepository.findById(id);
        if (bookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        return bookClubMapper.toBookClubDTO(bookClub.get());
    }

    @Override
    public BookClubDTO update(BookClubDTO bookClubDTO) {
        Optional<BookClub> existingBookClub = bookClubRepository.findById(bookClubDTO.bookClubId());
        if (existingBookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        BookClub bookClub = bookClubMapper.toBookClub(bookClubDTO);
        return bookClubMapper.toBookClubDTO(bookClubRepository.save(bookClub));
    }

    @Override
    public void deleteById(Long id) {
        Optional<BookClub> bookClub = bookClubRepository.findById(id);
        if (bookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        bookClubRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findUsersByBookClubId(Long id) {
        Optional<BookClub> bookClub = bookClubRepository.findById(id);
        if (bookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        return userMapper.toUsersDTO(userRepository.findUsersByBookClubId(id));
    }

    @Override
    public List<DiscussionDTO> findDiscussionsByBookClubId(Long id) {
        Optional<BookClub> bookClub = bookClubRepository.findById(id);
        if (bookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        return discussionMapper.toDiscussionsDTO(discussionRepository.findDiscussionsByBookClubId(id));
    }

    @Override
    public UserBookClubDTO joinToBookClub(UserBookClubDTO userBookClubDTO) throws RepositoryException {
        Optional<BookClub> bookClub = bookClubRepository.findById(userBookClubDTO.bookClubId());
        if (bookClub.isEmpty()) {
            throw new RepositoryException("BookClub not found");
        }
        Optional<User> user = userRepository.findById(userBookClubDTO.userId());
        if (user.isEmpty()) {
            throw new RepositoryException("User not found");
        }
        if (userBookClubRepository.findByBookClubIdAndUserId(userBookClubDTO.bookClubId(), userBookClubDTO.userId()).isPresent()) {
            throw new RepositoryException("User already joined");
        }

        return userBookClubMapper.toUserBookClubDTO(userBookClubRepository.save(userBookClubMapper.toUserBookClub(userBookClubDTO)));
    }
}
