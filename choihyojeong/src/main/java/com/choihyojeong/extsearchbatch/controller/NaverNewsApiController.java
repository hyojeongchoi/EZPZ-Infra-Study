package com.choihyojeong.extsearchbatch.controller;

import com.choihyojeong.extsearchbatch.service.NaverNewsService;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/api")
public class NaverNewsApiController {
    private final NaverNewsService naverNewsService;

    @Scheduled(cron="0 0 3 * * *",zone="Asia/Seoul")
    @GetMapping("/naver")
    public String naver()  {
        return naverNewsService.naverNewsApi();
    }

}
