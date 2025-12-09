package ru.evgenious.mynotes;


import java.io.Serializable;

public class Note implements Serializable {
    private final String title;         // заголовок
    private final String content;       // содержание заметки
    private final String date;          // дата (срок выполнения заметки, string), может не быть
    private final Boolean isImportant;  // важная заметка?

    public Note(String title, String content, String date, Boolean isImportant) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.isImportant = isImportant;
    }


    // Геттеры
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Boolean getIsImportant() {return isImportant;}


    // Декоратор для форматирования заметки
    public String getFormattedContent(boolean withDate) {
        // если дата указана
        if (withDate) {
            return content + "\n\n📅 " + date;
        }
        return content;
    }

    // Декоратор для заголовка (заметка можт быть важной или нет)
    public String getFormattedTitle() {
        // если заметка важная, то ставим звездочку
        if (isImportant) {
            return "★ " + title;
        }
        return title;
    }
}