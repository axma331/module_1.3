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
    private static final String DATABASE_FILE_NAME = "post.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public Post findById(Integer id) {
        return findAll().stream().filter(l -> l.id() == id).findFirst().orElse(null);
    }

    @Override
    public List<Post> findAll() {
        return readDataFromFile();
    }

    @Override
    public Integer save(Post postDto) {
        List<Post> loadedPosts = findAll();
        int id = getMaxId(loadedPosts);
        loadedPosts.add(new Post(id, postDto.title(), postDto.content(), postDto.labels(), postDto.status()));
        writeDataToFile(loadedPosts);
        return id;
    }

    @Override
    public boolean update(Post post) {
        List<Post> loadedPosts = findAll();
        if (loadedPosts.stream().anyMatch(p -> p.id() == post.id())) {
            writeDataToFile(loadedPosts.stream().map(p -> p.id() == post.id()
                    ? new Post(
                    p.id(),
                    post.title(),
                    post.content(),
                    Stream.concat(p.labels().stream(), post.labels().stream()).toList(),
                    post.status())
                    : p).toList()
            );
            return true;
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        writeDataToFile(findAll().stream().map(p -> p.id() == id
                ? new Post(p.id(), p.title(), p.content(), p.labels(), Status.DELETED)
                : p).toList()
        );
    }

    private void writeDataToFile(List<Post> savedData) {
        try (var writer = new FileWriter(DATABASE_FILE_NAME)) {
            gson.toJson(savedData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved");
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
