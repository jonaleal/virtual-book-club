package com.udea.bookclub.repositories;

import com.udea.bookclub.models.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiscussionRepository extends JpaRepository<Discussion, Long>{

    @Query("SELECT d FROM Discussion d JOIN d.bookClub b WHERE b.bookClubId = :bookClubId")
    List<Discussion> findDiscussionsByBookClubId(Long bookClubId);
}
