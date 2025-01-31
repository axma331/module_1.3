package CRUD_app.controller;

import CRUD_app.model.Label;
import CRUD_app.model.Post;
import CRUD_app.repository.impl.GsonPostRepositoryImpl;

import java.util.List;

public class PostController {

    GsonPostRepositoryImpl repository = new GsonPostRepositoryImpl();

    public int create(String title, String content, List<Label> labels) {
        return repository.save(new Post(title, content, labels));
    }

    public Post getById(int id) {
        return repository.findById(id);
    }

    public boolean update(int id, String newTitle, String newContent, List<Label> newSelectedLabels) {
        return repository.update(new Post(id, newTitle, newContent, newSelectedLabels));
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Post> getAll() {
        return repository.findAll();
    }
}
