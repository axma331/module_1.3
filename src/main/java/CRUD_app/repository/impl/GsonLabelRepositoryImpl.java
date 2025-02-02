package CRUD_app.repository.impl;

import CRUD_app.model.Label;
import CRUD_app.model.Status;
import CRUD_app.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private static final String DATABASE_FILE_NAME = "src/main/resources/label.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Label findById(Integer id) {
        return readDataFromFile().stream()
                .filter(l -> l.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Label> findAll() {
        return readDataFromFile().stream()
                .filter(l -> l.status().equals(Status.ACTIVE)).toList();
    }

    @Override
    public Label save(Label entity) {
        List<Label> loadedData = readDataFromFile();
        int id = getMaxId(loadedData);
        Label createdObj = new Label(id, entity.name(), Status.ACTIVE);
        loadedData.add(createdObj);
        boolean success = writeDataToFile(loadedData);
        return success ? createdObj : null;
    }

    @Override
    public Label update(Label entity) {
        List<Label> updatedData = readDataFromFile().stream().map(l -> !l.id().equals(entity.id()) ? l
                        : new Label(l.id(), entity.name(), l.status()))
                .toList();
        if (writeDataToFile(updatedData)) {
            return updatedData.stream().filter(l -> l.id().equals(entity.id())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Label> updatedData = readDataFromFile().stream().map(l -> !l.id().equals(id) ? l
                        : new Label(l.id(), l.name(), Status.DELETED))
                .toList();
        return updatedData.stream().anyMatch(l -> l.id().equals(id) && l.status().equals(Status.DELETED))
                && writeDataToFile(updatedData);
    }


    private boolean writeDataToFile(List<Label> savedData) {
        try (var writer = new FileWriter(DATABASE_FILE_NAME)) {
            gson.toJson(savedData, writer);
        } catch (IOException e) {
            System.out.println("File saving error!\n" + e.getMessage());
            return false;
        }
        System.out.println("The data is saved to the file!");
        return true;
    }

    private List<Label> readDataFromFile() {
        Type type = new TypeToken<List<Label>>() {
        }.getType();
        try (FileReader reader = new FileReader(DATABASE_FILE_NAME)) {
            List<Label> labels = gson.fromJson(reader, type);
            System.out.println("Data loaded from the file!");
            return labels != null ? labels : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("File reading error:" + e.getMessage());
            return new ArrayList<>(); //todo Collections.emptyList()?
        }
    }

    private int getMaxId(List<Label> labels) {
        return labels.stream().mapToInt(Label::id).max().orElse(0) + 1;
    }
}
