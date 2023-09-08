package com.choihyojeong.extsearchbatch.VO;
import java.util.List;

import com.choihyojeong.extsearchbatch.domain.NaverNews;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NaverResultVO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverNews> items;
}
