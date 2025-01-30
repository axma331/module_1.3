package CRUD_app.model;

public record Label(int id, String name, Status status) {

    public Label(String name) {
        this(-1, name, Status.ACTIVE);
    }
    public Label(int id, String name) {
        this(id, name, Status.ACTIVE);
    }
}

