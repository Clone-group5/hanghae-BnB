package com.hanghae.hanghaebnb.room.entity;

import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long price;

    @Column
    private Long extraPrice;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer headDefault;

    @Column
    private Integer headMax;

    @Column(nullable = false)
    private Integer likeCount;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private Users users;

    @OneToMany(mappedBy = "room")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Book> books = new ArrayList<>();

    @OneToMany
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Room(String title
                ,String contents
                ,Long price
                ,Long extraPrice
                ,String location
                ,Integer headDefault
                ,Integer headMax
                ,String img
                ,Integer likeCount
                ,Users users
                ) {

        this.title = title;
        this.contents = contents;
        this. price = price;
        this.extraPrice = extraPrice;
        this.location = location;
        this.headDefault = headDefault;
        this.headMax = headMax;
        this.likeCount = likeCount;
        this.img = img;
        this.users = users;

    }

    public void addBook(Book book){
        this.books.add(book);
    }


    public void imgUpdate(String img){
        this.img = img;
    }

    public void like(){
        this.likeCount++;
    }

    public void unLike(){
        this.likeCount--;
    }


}
