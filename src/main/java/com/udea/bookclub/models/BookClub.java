package com.udea.bookclub.models;

import com.udea.bookclub.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "book_clubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_id")
    private Long bookClubId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "tags", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @Column(name = "meet_link")
    private String meetLink;

    @Column(name = "image_link")
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookClub", cascade = CascadeType.REMOVE)
    private List<Discussion> discussions;

    @OneToMany(mappedBy = "bookClub", cascade = CascadeType.REMOVE)
    private List<UserBookClub> bookClubUsers;

    @OneToMany(mappedBy = "bookClub", cascade = CascadeType.REMOVE)
    private List<BookReview> bookReviews;

}

