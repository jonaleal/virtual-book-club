package com.udea.bookclub.repositories;

import com.udea.bookclub.models.UserBookClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserBookClubRepository extends JpaRepository<UserBookClub, Long> {

    @Query("SELECT ubc FROM UserBookClub ubc WHERE ubc.bookClub.bookClubId = :bookClubId AND ubc.user.userId = :userId")
    Optional<UserBookClub> findByBookClubIdAndUserId(Long bookClubId, Long userId);
}
