package com.choihyojeong.extsearchbatch.repository;
import com.choihyojeong.extsearchbatch.domain.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverNewsRepository extends JpaRepository<NaverNews, Long> {
    NaverNews findByTitle(String title);
}
