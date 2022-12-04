package ru.agentche.controller;

import com.google.gson.Gson;
import ru.agentche.model.Post;
import org.springframework.stereotype.Controller;
import ru.agentche.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 04.12.2022
 */
@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final ResponseHandler<List<Post>> handler = service::all;
        makeResponse(handler, response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        final ResponseHandler<Post> handler = () -> service.getById(id);
        makeResponse(handler, response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final ResponseHandler<Post> handler = () -> service.save(post);
        makeResponse(handler, response);
    }

    public void removeById(long id, HttpServletResponse response) {
        service.removeById(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private <T> void makeResponse(ResponseHandler<T> handler, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = handler.makeResponseData();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    @FunctionalInterface
    interface ResponseHandler<T> {
        T makeResponseData();
    }
}