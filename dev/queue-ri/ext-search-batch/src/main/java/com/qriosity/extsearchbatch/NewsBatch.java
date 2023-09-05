package com.qriosity.extsearchbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsBatch {
    private final NaverNewsItemRepository newsRepo;
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${secrets.naver.id}")
    private String CLIENT_ID;

    @Value("${secrets.naver.secret}")
    private String CLIENT_SECRET;

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul") // test: */20 * * * * *
    public void fetch() {
        NaverNewsResponse response = restTemplate.exchange(
                "https://openapi.naver.com/v1/search/news.json?query=%EC%A3%BC%EC%8B%9D&display=10&start=1&sort=sim",
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders()),
                NaverNewsResponse.class
        ).getBody();

        List<NaverNewsItem> items = response.getItems();
        for (NaverNewsItem item : items) {
            NaverNewsItem data = newsRepo.findByTitle(item.getTitle());
            if (data == null) newsRepo.save(item);
            else checkUpdate(item, data);
        }
        System.out.println("뉴스 데이터 적재 완료");
    }

    private void checkUpdate(NaverNewsItem newItem, NaverNewsItem oldItem) {
//        // legacy code
//        if (newItem.getDescription().compareTo(oldItem.getDescription()) != 0)
//            oldItem.setDescription(newItem.getDescription());
//        if (newItem.getLink().compareTo(oldItem.getLink()) != 0)
//            oldItem.setLink(newItem.getLink());
//        if (newItem.getOriginallink().compareTo(oldItem.getOriginallink()) != 0)
//            oldItem.setOriginallink(newItem.getOriginallink());
//        if (newItem.getPubDate().compareTo(oldItem.getPubDate()) != 0)
//            oldItem.setPubDate(newItem.getPubDate());
        
        // 비교 로직 생략하여 최적화
        oldItem.setDescription(newItem.getDescription());
        oldItem.setLink(newItem.getLink());
        oldItem.setOriginallink(newItem.getOriginallink());
        oldItem.setPubDate(newItem.getPubDate());
        // oldItem.setUpdatedAt(LocalDateTime.now()); // 엔티티 어노테이션으로 대체
        newsRepo.save(oldItem);
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", CLIENT_ID);
        headers.add("X-Naver-Client-Secret", CLIENT_SECRET);
        return headers;
    }
}