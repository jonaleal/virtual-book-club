package com.udea.bookclub.services;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.dtos.mappers.IBookClubMapper;
import com.udea.bookclub.dtos.mappers.IUserMapper;
import com.udea.bookclub.exceptions.RepositoryException;
import com.udea.bookclub.models.User;
import com.udea.bookclub.repositories.IBookClubRepository;
import com.udea.bookclub.repositories.IUserRepository;
import com.udea.bookclub.services.facade.IUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final IBookClubRepository bookClubRepository;
    private final IBookClubMapper bookClubMapper;

    public UserService(IUserRepository userRepository, IUserMapper userMapper, PasswordEncoder passwordEncoder, IBookClubRepository bookClubRepository, IBookClubMapper bookClubMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.bookClubRepository = bookClubRepository;
        this.bookClubMapper = bookClubMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()).isPresent()) {
            throw new RepositoryException("Username already exist");
        }
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        return userMapper.toUsersDTO(userRepository.findAll(pageable).toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RepositoryException("User not found");
        }
        return userMapper.toUserDTO(user.get());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        if (userRepository.findById(userDTO.userId()).isEmpty()) {
            throw new RepositoryException("User not found");
        }
        Optional<User> user = userRepository.findByUsernameOrEmail(userDTO.username(), userDTO.email());
        if (user.isPresent() && !user.get().getUserId().equals(userDTO.userId())) {
            throw new RepositoryException("Username or email already exist");
        }
        return userMapper.toUserDTO(userRepository.save(userMapper.toUser(userDTO)));
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RepositoryException("User not found");
        }
        return userMapper.toUserDTO(user.get());
    }

    @Override
    public List<BookClubDTO> findBookClubsJoinedByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RepositoryException("User not found");
        }
        return bookClubMapper.toBookClubsDTO(bookClubRepository.findBookClubsJoinedByUserId(userId));
    }

}
