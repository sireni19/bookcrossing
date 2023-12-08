package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.News;
import com.prokopovich.bookcrossing.repositories.NewsRepository;
import com.prokopovich.bookcrossing.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    @Override
    public void addNews(News news) {
        newsRepository.save(news);
    }

    @Override
    public List<News> getAllNews() {
        return (List<News>) newsRepository.findAll();
    }

    @Override
    public String getLastNewsTitle() {
        return newsRepository.findLatestNewsTitle();
    }
}
