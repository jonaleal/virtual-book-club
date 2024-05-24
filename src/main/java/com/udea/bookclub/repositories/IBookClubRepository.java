package com.udea.bookclub.repositories;

import com.udea.bookclub.models.BookClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookClubRepository extends JpaRepository<BookClub, Long> {

    @Query("SELECT bc FROM BookClub bc JOIN bc.bookClubUsers bcu WHERE bcu.user.userId = :userId")
    List<BookClub> findBookClubsJoinedByUserId(Long userId);
}
