package CRUD_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Post {
    private int id;
    private String title;
    private String content;
    private final List<Label> labels = new ArrayList<>();
    private Status status= Status.ACTIVE;

    public Post addLabel(Label label) {
        this.labels.add(label);
        return this;
    }
}
