package com.choihyojeong.extsearchbatch.domain;

import com.choihyojeong.extsearchbatch.dto.NaverNewsItem;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.cglib.core.Local;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;

@Setter
@Entity
@Getter
@NoArgsConstructor
@Table(name="naver_news_item")

public class NaverNews {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 하나 씩 증가
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="title",nullable = false)//, length = 200)
    private String title;

    @Column(name = "link",nullable = false)
    private String link;

    @Column(name="description")//length = 500)
    private String description;

    private LocalDateTime pubDate;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;

    @Builder
    public NaverNews(Long id, String title, String link, String description,
                                LocalDateTime pubDate, LocalDateTime created_at, LocalDateTime updated_at){
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public void update (NaverNewsItem itemDto){
        this.title = itemDto.getTitle();
        this.description = itemDto.getDescription();
        this.link = itemDto.getLink();
        this.pubDate = itemDto.getPubDate();

    }
}
