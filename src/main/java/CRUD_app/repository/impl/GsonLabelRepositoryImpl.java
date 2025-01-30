package CRUD_app.repository.impl;

import CRUD_app.model.Label;
import CRUD_app.model.Status;
import CRUD_app.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private static final String FILE_NAME = "label.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Label findById(Integer id) {
        return findAll().stream().filter(l -> l.id() == id).findFirst().orElse(null);
    }

    @Override
    public List<Label> findAll() {
        return readAllFromFile();
    }

    @Override
    public Integer save(Label labelDto) {
        var all = findAll();
        int id = getMaxId(all);
        all.add(new Label(id, labelDto.name()));
        writeAllToFile(all);
        return id;
    }

    @Override
    public boolean update(Label label) {
        var all = findAll();
        if (all.stream().anyMatch(l -> l.id() == label.id())) {
            writeAllToFile(
                    all.stream().map(l -> l.id() == label.id()
                                    ? new Label(l.id(), label.name(), l.status())
                                    : l)
                            .collect(Collectors.toList()));
            return true;
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        writeAllToFile(findAll().stream().map(l -> l.id() == id
                        ? new Label(l.id(), l.name(), Status.DELETED)
                        : l)
                .collect(Collectors.toList())
        );
    }


    private void writeAllToFile(List<Label> obj) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            gson.toJson(obj, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved");
    }

    private List<Label> readAllFromFile() {
        List<Label> labels = null;
        Type listType = new TypeToken<List<Label>>() {
        }.getType();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            labels = gson.fromJson(reader, listType);
            System.out.println("Loaded");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return labels == null ? new ArrayList<>() : labels;
    }

    // utils

    public int getMaxId(List<Label> labels) {
        return labels.stream().mapToInt(Label::id).max().orElse(0) + 1;
    }

}
