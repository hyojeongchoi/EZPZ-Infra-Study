package com.choihyojeong.extsearchbatch.dto;

import com.choihyojeong.extsearchbatch.domain.NaverNews;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor// 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class NaverNewsItem {
    private String title;
    private String link;
    private String description;
    private LocalDateTime pubDate;

    public NaverNews toEntity(){ //toEntity : 빌더 패턴을 사용해 DTO를 Entity로 만들어주는 메서드
        return NaverNews.builder()
                .title(title)
                .link(link)
                .description(description)
                .pubDate(pubDate)
                .build();
    }
}
