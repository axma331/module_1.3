package CRUD_app.model;

import java.util.List;

public record Post(Integer id, String title, String content, List<Label> labels, Status status) {

    public Post(String title, String content, List<Label> labels) {
        this(null, title, content, labels, null);
    }

    public Post(Integer id, String title, String content, List<Label> labels) {
        this(id, title, content, labels, Status.ACTIVE);

    }
}
