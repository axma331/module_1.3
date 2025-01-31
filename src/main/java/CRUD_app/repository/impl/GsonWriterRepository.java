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

    private static final String DATABASE_FILE_NAME = "writer.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public Writer findById(Integer id) {
        return findAll().stream().filter(l -> l.id() == id).findFirst().orElse(null);
    }

    @Override
    public List<Writer> findAll() {
        return readDataFromFile();
    }

    @Override
    public Integer save(Writer writer) {
        List<Writer> loadedWriters = findAll();
        int id = getMaxId(loadedWriters);
        loadedWriters.add(new Writer(id, writer.firstName(), writer.lastName(), writer.posts()));
        writeDataToFile(loadedWriters);
        return id;
    }

    @Override
    public boolean update(Writer writer) {
        List<Writer> loadedWriters = findAll();
        if (loadedWriters.stream().anyMatch(p -> p.id() == writer.id())) {
            writeDataToFile(loadedWriters.stream().map(p -> p.id() == writer.id()
                    ? new Writer(
                    p.id(),
                    writer.firstName(),
                    writer.lastName(),
                    Stream.concat(p.posts().stream(), writer.posts().stream()).toList(),
                    writer.status())
                    : p).toList()
            );
            return true;
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        writeDataToFile(findAll().stream().map(w -> w.id() == id
                ? new Writer(w.id(), w.firstName(), w.lastName(), w.posts(), Status.DELETED)
                : w).toList()
        );

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

    private void writeDataToFile(List<Writer> savedData) {
        try (var writer = new FileWriter(DATABASE_FILE_NAME)) {
            gson.toJson(savedData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved");
    }

    //utils

    private int getMaxId(List<Writer> writers) {
        return writers.stream().mapToInt(Writer::id).max().orElse(0) + 1;
    }
}
