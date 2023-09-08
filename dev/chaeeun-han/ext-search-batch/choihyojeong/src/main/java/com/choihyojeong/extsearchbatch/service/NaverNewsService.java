package com.choihyojeong.extsearchbatch.service;

import com.choihyojeong.extsearchbatch.VO.NaverResultVO;
import com.choihyojeong.extsearchbatch.VO.NewsVO;
import com.choihyojeong.extsearchbatch.domain.NaverNews;
import com.choihyojeong.extsearchbatch.dto.NaverNewsItem;
import com.choihyojeong.extsearchbatch.repository.NaverNewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.CharacterCodingException;
import java.util.List;


@RequiredArgsConstructor
@Service
@Component
public class NaverNewsService {
    private final NaverNewsRepository NaverNewsRepo;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public String naverNewsApi ()  {
        System.out.println("clientId " + " / clientSecret ");
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/news.json")
                .queryParam("query","주식")
                .queryParam("display",15)
                .queryParam("start",1)
                .queryParam("sort","sim")
                .encode()
                .build()
                .toUri();


        //Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id",clientId)
                .header("X-Naver-Client-Secret",clientSecret)
                .build();

        //Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        //JSON 파싱 ( JSON 형태의 문자열을 객체로 만드는 과정)
        ObjectMapper om = new ObjectMapper();
        NaverResultVO resultVO = null;

        try{
            resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        List<NaverNews> newsItem = resultVO.getItems();
        for (NaverNews news : newsItem){ // NaverNews news는 지금 새로 리스트에 들어온거
            NaverNews findNews = NaverNewsRepo.findByTitle((news.getTitle())); //DB에서 찾아온 거
            if (findNews == null) { //없으면 저장해주면 됨
                NaverNewsRepo.save(news);
                System.out.println("뉴스 데이터 적재 완료");
            }
            else { //그냥 교체해주기 --> 비교안하고 교체해줘도 되나? 덮어쓰는 형식
                findNews.setDescription(news.getDescription());
                findNews.setLink(news.getLink());
                findNews.setPubDate(news.getPubDate());
                NaverNewsRepo.save(findNews);
                System.out.println("뉴스 데이터 업데이트 완료");
            }
        }
        return resp.getBody();
    }
    //news의 아이템을 디비에 저장해주는 코드
        //1. 중복확인코드 - 어떻게 할까? 검색해보고 진행?
}
