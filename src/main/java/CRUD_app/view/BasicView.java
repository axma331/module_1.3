package CRUD_app.view;

import CRUD_app.model.Massage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public abstract class BasicView {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    PrintWriter out = new PrintWriter(System.out, true);

    public void startDialog() {
        try {
            do {
                out.println(Massage.WELCOME);
                switch (in.readLine().toLowerCase()) {
                    case "create" -> {
                        create(in, out);
                    }
                    case "edit" -> {
                        update(in, out);
                    }
                    case "get" -> {
                        get(in, out);
                    }
                    case "delete" -> {
                        delete(in, out);
                    }
                    case "show all" -> {
                        showAll();
                    }
                    case "exit" -> {
                        return;
                    }
                    default -> {
                        out.println(Massage.REPEAT);
                    }
                }
            } while (true);
        } catch (IOException e) {
            out.println(Massage.ACTION_FAILED);
            e.printStackTrace();
        }
    }

    abstract void create(BufferedReader in, PrintWriter out) throws IOException;

    abstract void get(BufferedReader in, PrintWriter out) throws IOException;

    abstract void update(BufferedReader in, PrintWriter out) throws IOException;

    abstract void delete(BufferedReader in, PrintWriter out) throws IOException;

    abstract void showAll();
}
