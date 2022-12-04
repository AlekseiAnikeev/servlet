package ru.agentche.model;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 04.12.2022
 */
public class Post {
    private long id;
    private String content;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}