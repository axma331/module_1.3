package CRUD_app.repository.impl;

import CRUD_app.model.Post;
import CRUD_app.model.Status;
import CRUD_app.repository.PostRepository;
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

public class GsonPostRepositoryImpl implements PostRepository {
    private static final String DATABASE_FILE_NAME = "src/main/resources/post.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public Post findById(Integer id) {
        return readDataFromFile().stream()
                .filter(l -> l.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Post> findAll() {
        return readDataFromFile();
    }

    @Override
    public Post save(Post entity) {
        List<Post> loadedData = readDataFromFile();
        int id = getMaxId(loadedData);
        Post createdObj = new Post(id, entity.title(), entity.content(), entity.labels(), Status.ACTIVE);
        loadedData.add(createdObj);
        boolean success = writeDataToFile(loadedData);
        return success ? createdObj : null;
    }

    @Override
    public Post update(Post entity) {
        List<Post> updatedData = readDataFromFile().stream().map(l -> !l.id().equals(entity.id()) ? l
                        : new Post(
                        entity.id(),
                        entity.title(),
                        entity.content(),
                        Stream.concat(l.labels().stream(), entity.labels().stream()).toList(),
                        entity.status()))
                .toList();
        if (writeDataToFile(updatedData)) {
            return updatedData.stream().filter(l -> l.id().equals(entity.id())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Post> updatedData = readDataFromFile().stream().map(p -> p.id().equals(id)
                ? new Post(p.id(), p.title(), p.content(), p.labels(), Status.DELETED)
                : p).toList();
        return updatedData.stream().anyMatch(l -> l.id().equals(id) && l.status().equals(Status.DELETED))
                && writeDataToFile(updatedData);
    }

    private boolean writeDataToFile(List<Post> savedData) {
        try (var writer = new FileWriter(DATABASE_FILE_NAME)) {
            gson.toJson(savedData, writer);
        } catch (IOException e) {
            System.out.println("File saving error!\n" + e.getMessage());
            return false;
        }
        System.out.println("The data is saved to the file!");
        return true;
    }

    private List<Post> readDataFromFile() {
        List<Post> loadedPosts;
        Type type = new TypeToken<List<Post>>() {
        }.getType();
        try (var reader = new FileReader(DATABASE_FILE_NAME)) {
            loadedPosts = gson.fromJson(reader, type);
            System.out.println("Loaded");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedPosts == null ? new ArrayList<>() : loadedPosts;
    }

    //utils

    private int getMaxId(List<Post> posts) {
        return posts.stream().mapToInt(Post::id).max().orElse(0) + 1;
    }
}
