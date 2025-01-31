package CRUD_app.model;

import java.util.List;

public record Writer(int id, String firstName, String lastName, List<Post> posts, Status status) {

    public Writer(int id, String firstName, String lastName, List<Post> posts) {
        this(id, firstName, lastName, posts, Status.ACTIVE);
    }

    public Writer(String firstName, String lastName, List<Post> posts) {
        this(-1, firstName, lastName, posts, Status.ACTIVE);
    }
}
