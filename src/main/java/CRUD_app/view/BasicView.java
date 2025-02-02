package CRUD_app.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public abstract class BasicView {
    protected final BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    protected final PrintWriter out = new PrintWriter(System.out, true);

    public void startDialog() {
        try {
            do {
                out.println(MassageUtils.WELCOME);
                switch (in.readLine().toLowerCase()) {
                    case "create" -> create();
                    case "edit" -> update();
                    case "get" -> get();
                    case "delete" -> delete();
                    case "show all" -> showAll();
                    case "exit" -> {
                        return;
                    }
                    default -> out.println(MassageUtils.REPEAT);
                }
            } while (true);
        } catch (IOException e) {
            out.println(MassageUtils.ACTION_FAILED);
            e.printStackTrace();
        }
    }

    abstract void create() throws IOException;

    abstract void get() throws IOException;

    abstract void update() throws IOException;

    abstract void delete() throws IOException;

    abstract void showAll();
}
