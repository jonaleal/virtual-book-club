package com.udea.bookclub.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_book_clubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserBookClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_book_club_id")
    private Long userBookClubId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_club_id", referencedColumnName = "book_club_id")
    private BookClub bookClub;

}
