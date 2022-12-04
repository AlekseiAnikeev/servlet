package ru.agentche.repository;

import ru.agentche.exception.NotFoundException;
import ru.agentche.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 04.12.2022
 */
@Repository
public class PostRepository {
    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicLong nextPostId = new AtomicLong(1L);

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == 0L) {
            post.setId(nextPostId.getAndIncrement());
            posts.add(post);
            return post;
        } else {
            final Post oldPost = getById(post.getId()).orElseThrow(NotFoundException::new);
            oldPost.setContent(post.getContent());
            return oldPost;
        }
    }

    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}