package CRUD_app.view;

import CRUD_app.controller.PostController;
import CRUD_app.model.Label;
import CRUD_app.model.Massage;
import CRUD_app.model.Post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class PostView extends BasicView {

    PostController controller = new PostController();
    LabelView labelView = new LabelView();


    @Override
    void create(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_POST_TITLE);
        String title = in.readLine();
        out.println(Massage.ENTER_POST_CONTENT);
        String content = in.readLine();
        List<Label> selectedLabels = showAndSelectLabel(in, out);
        int assignedId = controller.create(title, content, selectedLabels);
        out.println(Massage.ACTION_SUCCESS);
        out.println("Присвоен id = " + assignedId);
    }

    @Override
    void get(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Post objById = controller.getById(id);
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
        out.println(Massage.ENTER_POST_TITLE);
        String newTitle = in.readLine();
        out.println(Massage.ENTER_POST_CONTENT);
        String newContent = in.readLine();
        List<Label> newSelectedLabels = showAndSelectLabel(in, out);
        boolean result = controller.update(id, newTitle, newContent, newSelectedLabels);
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
        getAll().forEach(p -> out.println(
                String.format("%2d", p.id()) + " title: " + p.title() + ", content: " + p.content()
                        + ",\n" + p.labels() + "\n" + p.status()));
    }

    public List<Post> getAll() {
        return controller.getAll();
    }

    private List<Label> showAndSelectLabel(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Введите id ярлыков для добавления");
        List<Label> allLabels = labelView.getAll();
        allLabels.forEach(e -> out.println(e.id() + ": " + e.name()));
        String inputIdSet = in.readLine();
        int[] ids = Arrays.stream(inputIdSet.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        Arrays.stream(ids).forEach(out::println);
        List<Label> selectedLabels = allLabels.stream().
                filter(l -> Arrays.stream(ids).anyMatch(id -> id == l.id())).toList();
        return selectedLabels;
    }
}
