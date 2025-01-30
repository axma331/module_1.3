package CRUD_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Writer {
    private static int idCounter = 0;

    private int id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;

    public Writer() {
        id = ++idCounter;
        status = Status.ACTIVE;;
        posts = new ArrayList<>();
    }

    public Writer addPost(Post post) {
        posts.add(post);
        return this;
    }
}
