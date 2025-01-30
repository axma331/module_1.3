package CRUD_app.view;

import CRUD_app.controller.LabelController;
import CRUD_app.model.Label;
import CRUD_app.model.Massage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class LabelView {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    PrintWriter out = new PrintWriter(System.out, true);
    LabelController controller = new LabelController();


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

    private void create(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_LABEL_NAME);
        String answer = in.readLine();
        int assignedId = controller.create(answer);
        out.println(Massage.ACTION_SUCCESS);
        out.println("Присвоен id = " + assignedId);
    }

    private void update(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        out.println(Massage.ENTER_LABEL_NAME);
        String name = in.readLine();
        boolean result = controller.update(id, name);
        out.println(result ? Massage.ACTION_SUCCESS : Massage.ACTION_FAILED);
    }

    private void get(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Label objById = controller.getById(id);
        if (objById == null) {
            out.println(Massage.ACTION_FAILED);
            return;
        }
        out.println(objById);
        out.println(Massage.ACTION_SUCCESS);
    }

    private void delete(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        controller.deleteById(id);
        out.println(Massage.ACTION_SUCCESS);
    }

}
