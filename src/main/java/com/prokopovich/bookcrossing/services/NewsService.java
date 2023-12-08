package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.News;

import java.util.List;

public interface NewsService {
    void addNews(News news);
    List<News> getAllNews();
    String getLastNewsTitle();
}
