package com.udea.bookclub.repositories;

import com.udea.bookclub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.userBookClubs ubc WHERE ubc.bookClub.bookClubId = :bookClubId")
    List<User> findUsersByBookClubId(Long bookClubId);
}
