package com.prokopovich.bookcrossing.parsing;

import com.prokopovich.bookcrossing.entities.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Component
public class Parser {

    public static News getNews() throws SocketTimeoutException {
        Element h1;
        Elements paragraphs;
        Elements date;
        try {
            Document document = Jsoup.connect("https://smartpress.by/tags/%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%82%D1%83%D1%80%D0%B0/").get();
            Elements div1 = document.getElementsByClass("news-line");
            Element ul = div1.select("ul").first();
            Element li = ul.selectFirst("li");
            date=li.getElementsByClass("date");
            Element p = li.selectFirst("p");
            Element link = p.selectFirst("a");
            String href = "https://smartpress.by" + link.attr("href");
            Document newPage = Jsoup.connect(href).get();
            h1 = newPage.selectFirst("h1");
            Elements div2 = newPage.getElementsByClass("art-content");
            paragraphs = div2.select("p");
        } catch (SocketTimeoutException e) {
            System.out.println("waiting...");
            throw new SocketTimeoutException("Ошибка при подключении: превышено время ожидания");
        } catch (NullPointerException e) {
            throw new RuntimeException("Ошибка при извлечении элементов, возможно таких элементов нет", e);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обработке ввода-вывода", e);
        }
        News news = new News();
        StringBuilder builder = new StringBuilder();
        news.setTitle(h1.text());
        for (Element paragraph : paragraphs) {
            // Проверка наличия изображения в параграфе
            Element imageElement = paragraph.selectFirst("img");
            if (imageElement != null) {
                // Получение URL изображения
                String imageURL = imageElement.toString();
                String first = "data-src=\"";
                String second = "\" title";
                int startIndex = imageURL.indexOf(first) + first.length();
                int endIndex = imageURL.indexOf(second);
                if (startIndex != -1 && endIndex != -1) {
                    String resultString = imageURL.substring(startIndex, endIndex).trim();
                    news.setPictureLink("https://smartpress.by" + resultString);
                } else {
                    System.out.println("Слова не найдены или порядок неверный.");
                    news.setPictureLink("Empty");
                }
            } else {
                builder.append(paragraph.text() + "\n");
            }
        }
        news.setContent(builder.toString());
        news.setDate(date.text());
        return news;
    }

    public static void main(String[] args) throws SocketTimeoutException {
        getNews();
    }
}

