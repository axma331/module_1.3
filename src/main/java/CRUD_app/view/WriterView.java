package CRUD_app.view;

import CRUD_app.controller.WriterController;
import CRUD_app.model.Massage;
import CRUD_app.model.Post;
import CRUD_app.model.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class WriterView extends BasicView {

    WriterController controller = new WriterController();
    PostView postView = new PostView();

    @Override
    void create(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_NAME);
        String firstName = in.readLine();
        out.println(Massage.ENTER_LAST_NAME);
        String lastName = in.readLine();
        List<Post> selectedPosts = showAndSelectPosts(in, out);
        int assignedId = controller.create(firstName, lastName, selectedPosts);
        out.println(Massage.ACTION_SUCCESS);
        out.println("Присвоен id = " + assignedId);
    }

    @Override
    void get(BufferedReader in, PrintWriter out) throws IOException {
        out.println(Massage.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Writer objById = controller.getById(id);
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
        out.println(Massage.ENTER_NAME);
        String newName = in.readLine();
        out.println(Massage.ENTER_LAST_NAME);
        String newLastName = in.readLine();
        List<Post> newSelectedPosts = showAndSelectPosts(in, out);
        boolean result = controller.update(id, newName, newLastName, newSelectedPosts);
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
        getAll().forEach(w -> out.println(
                String.format("%2d", w.id()) + " first name: " + w.firstName() + ", last name: " + w.lastName()
                        + ",\n" + w.posts() + "\n" + w.status()));
    }

    public List<Writer> getAll() {
        return controller.getAll();
    }

    private List<Post> showAndSelectPosts(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Введите id поста для добавления");
        List<Post> allPosts = postView.getAll();
        allPosts.forEach(p -> out.println(p.id() + ": " + p.title() + ", " + p.content()));
        String inputIdSet = in.readLine();
        int[] ids = Arrays.stream(inputIdSet.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        Arrays.stream(ids).forEach(out::println);
        List<Post> selectedPosts = allPosts.stream()
                .filter(l -> Arrays.stream(ids).anyMatch(id -> id == l.id())).toList();
        return selectedPosts;
    }
}
