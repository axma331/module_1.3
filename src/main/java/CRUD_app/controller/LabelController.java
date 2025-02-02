package CRUD_app.controller;

import CRUD_app.model.Label;
import CRUD_app.model.Status;
import CRUD_app.repository.LabelRepository;
import CRUD_app.repository.impl.GsonLabelRepositoryImpl;

import java.util.List;

public class LabelController {

    private final LabelRepository repository = new GsonLabelRepositoryImpl();

    public Label create(String labelName) {
        return repository.save(new Label(labelName));
    }

    public Label getById(int id) {
        return repository.findById(id);
    }

    public Label update(int id, String newName) {
        return repository.update(new Label(id, newName));
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public List<Label> getAll() {
        return repository.findAll();
    }
}
