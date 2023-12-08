package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.News;
import com.prokopovich.bookcrossing.parsing.Parser;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

@Service
@EnableAsync
@AllArgsConstructor
public class ScheduledTaskService {
    private NewsServiceImpl  newsService;
    @Async
    @Scheduled(cron = "0 0 6 * * ?",zone = "Europe/Minsk")
//    @Scheduled(fixedDelay = 60000)
    public void scheduleTask() throws InterruptedException {
        try {
            News freshNews = Parser.getNews();
            String lastNews= newsService.getLastNewsTitle();
            if(!freshNews.getTitle().trim().equals(lastNews.trim())){
                newsService.addNews(freshNews);
            }else {
                System.out.println("Такая новость уже есть");
            }
        } catch (SocketTimeoutException e) {
                Thread.sleep(10000);
                scheduleTask(); // Рекурсивный вызов метода
        }
    }
}
