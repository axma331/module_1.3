package CRUD_app.controller;

import CRUD_app.model.Label;
import CRUD_app.repository.impl.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    GsonLabelRepositoryImpl repository = new GsonLabelRepositoryImpl();


    public int create(String labelName) {
        return repository.save(new Label(labelName));
    }

    public Label getById(int id) {
        return repository.findById(id);
    }

    public boolean update(int id, String newName) {
        return repository.update(new Label(id, newName));
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Label> getAll() {
        return repository.findAll();
    }
}
