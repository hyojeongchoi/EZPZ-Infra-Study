package com.choihyojeong.extsearchbatch.VO;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsVO {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private LocalDateTime pubDate;
}
