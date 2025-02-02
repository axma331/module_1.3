package CRUD_app.model;

public record Label(Integer id, String name, Status status) {

    public Label(String name) {
        this(null, name, null);
    }

    public Label(Integer id, String name) {
        this(id, name, Status.ACTIVE);
    }
}

