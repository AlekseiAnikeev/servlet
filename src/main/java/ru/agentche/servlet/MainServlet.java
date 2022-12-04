package ru.agentche.servlet;

import ru.agentche.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 04.12.2022
 */
public class MainServlet extends HttpServlet {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String DELETE_METHOD = "DELETE";
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("ru.agentche");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (method.equals(GET_METHOD) && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET_METHOD) && path.matches("/api/posts/\\d+")) {
                final var id = getIdFromUrl(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST_METHOD) && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE_METHOD) && path.matches("/api/posts/\\d+")) {
                final var id = getIdFromUrl(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getIdFromUrl(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}