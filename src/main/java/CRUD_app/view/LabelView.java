package CRUD_app.view;

import CRUD_app.controller.LabelController;
import CRUD_app.model.Label;
import CRUD_app.model.Massage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LabelView extends BasicView {

    LabelController controller = new LabelController();

    @Override
    void create(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_LABEL_NAME);
        String answer = in.readLine();
        int assignedId = controller.create(answer);
        out.println(Massage.ACTION_SUCCESS);
        out.println("Присвоен id = " + assignedId);
    }

    @Override
    void get(BufferedReader in, PrintWriter out) throws IOException {
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

    @Override
    void update(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        out.println(Massage.ENTER_LABEL_NAME);
        String name = in.readLine();
        boolean result = controller.update(id, name);
        out.println(result ? Massage.ACTION_SUCCESS : Massage.ACTION_FAILED);
    }

    @Override
    void delete(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        controller.deleteById(id);
        out.println(Massage.ACTION_SUCCESS);
    }

    @Override
    void showAll() {
        getAll().forEach(l -> out.println( l.id() + ": "+l.name()));
    }

    public List<Label> getAll() {
        return controller.getAll();
    }


}
