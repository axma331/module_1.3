package CRUD_app.controller;

import CRUD_app.model.Post;
import CRUD_app.model.Writer;
import CRUD_app.repository.impl.GsonWriterRepository;

import java.util.List;

public class WriterController {

    GsonWriterRepository repository = new GsonWriterRepository();

    public int create(String firstName, String lastName, List<Post> selectedPosts) {
        return repository.save(new Writer(firstName, lastName, selectedPosts));
    }

    public Writer getById(int id) {
        return repository.findById(id);
    }

    public boolean update(int id, String name, String lastName, List<Post> selectedPosts) {
        return repository.update(new Writer(id, name, lastName, selectedPosts));
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Writer> getAll() {
        return repository.findAll();
    }
}
