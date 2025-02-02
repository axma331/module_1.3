package CRUD_app.model;

import java.util.List;

public record Writer(Integer id, String firstName, String lastName, List<Post> posts, Status status) {

    public Writer(String firstName, String lastName, List<Post> posts) {
        this(null, firstName, lastName, posts, null);
    }

    public Writer(Integer id, String firstName, String lastName, List<Post> posts) {
        this(id, firstName, lastName, posts, Status.ACTIVE);
    }
}
