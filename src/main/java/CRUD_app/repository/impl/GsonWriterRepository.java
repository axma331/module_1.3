package CRUD_app.repository.impl;

import CRUD_app.model.Status;
import CRUD_app.model.Writer;
import CRUD_app.repository.WriterRepository;
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
import java.util.stream.Stream;

public class GsonWriterRepository implements WriterRepository {

    private static final String DATABASE_FILE_NAME = "src/main/resources/writer.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public Writer findById(Integer id) {
        return readDataFromFile().stream()
                .filter(l -> l.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Writer> findAll() {
        return readDataFromFile();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> loadedData = readDataFromFile();
        int id = getMaxId(loadedData);
        Writer createdObj = new Writer(id, writer.firstName(), writer.lastName(), writer.posts());
        loadedData.add(createdObj);
        boolean success = writeDataToFile(loadedData);
        return success ? createdObj : null;
    }

    @Override
    public Writer update(Writer entity) {
        List<Writer> updatedData = readDataFromFile().stream().map(l -> !l.id().equals(entity.id()) ? l
                        : new Writer(
                        entity.id(),
                        entity.firstName(),
                        entity.lastName(),
                        Stream.concat(l.posts().stream(), entity.posts().stream()).toList(),
                        entity.status()))
                .toList();
        if (writeDataToFile(updatedData)) {
            return updatedData.stream().filter(l -> l.id().equals(entity.id())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Writer> updatedData = readDataFromFile().stream().map(w -> !w.id().equals(id) ? w
                        : new Writer(w.id(), w.firstName(), w.lastName(), w.posts(), Status.DELETED))
                .toList();
        return updatedData.stream().anyMatch(l -> l.id().equals(id) && l.status().equals(Status.DELETED))
                && writeDataToFile(updatedData);

    }

    private List<Writer> readDataFromFile() {
        List<Writer> loadedWriters;
        Type type = new TypeToken<List<Writer>>() {
        }.getType();
        try (var reader = new FileReader(DATABASE_FILE_NAME)) {
            loadedWriters = gson.fromJson(reader, type);
            System.out.println("Loaded");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedWriters == null ? new ArrayList<>() : loadedWriters;
    }

    private boolean writeDataToFile(List<Writer> savedData) {
        try (var writer = new FileWriter(DATABASE_FILE_NAME)) {
            gson.toJson(savedData, writer);
        } catch (IOException e) {
            System.out.println("File saving error!\n" + e.getMessage());
            return false;
        }
        System.out.println("The data is saved to the file!");
        return true;
    }

    private int getMaxId(List<Writer> writers) {
        return writers.stream().mapToInt(Writer::id).max().orElse(0) + 1;
    }
}
