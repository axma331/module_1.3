package CRUD_app.model;

import java.util.List;

public record Post(int id, String title, String content, List<Label> labels, Status status) {

    public Post(String title, String content, List<Label> labels) {
        this(-1, title, content, labels, Status.ACTIVE);
    }

    public Post(int id, String title, String content, List<Label> labels) {
        this(id, title, content, labels, Status.ACTIVE);

    }
}
