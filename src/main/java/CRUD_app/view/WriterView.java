package CRUD_app.view;

import CRUD_app.controller.WriterController;
import CRUD_app.model.Post;
import CRUD_app.model.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class WriterView extends BasicView {

    private final WriterController controller = new WriterController();
    private final PostView postView = new PostView();

    @Override
    void create() throws IOException {
        out.println(MassageUtils.ENTER_NAME);
        String firstName = in.readLine();
        out.println(MassageUtils.ENTER_LAST_NAME);
        String lastName = in.readLine();
        List<Post> selectedPosts = showAndSelectPosts(in, out);
        Writer createdObj = controller.create(firstName, lastName, selectedPosts);
        System.out.println(createdObj == null ? MassageUtils.ACTION_FAILED : MassageUtils.ACTION_SUCCESS);

    }

    @Override
    void get() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Writer gettingObj = controller.getById(id);
        out.println(gettingObj != null
                ? gettingObj + "\n" + MassageUtils.ACTION_SUCCESS
                : MassageUtils.ACTION_FAILED
        );
    }

    @Override
    void update() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        Integer id = Integer.parseInt(in.readLine());
        out.println(MassageUtils.ENTER_NAME);
        String newName = in.readLine();
        out.println(MassageUtils.ENTER_LAST_NAME);
        String newLastName = in.readLine();
        List<Post> newSelectedPosts = showAndSelectPosts(in, out);
        Writer gettingObj= controller.update(id, newName, newLastName, newSelectedPosts);
        out.println(gettingObj != null
                && id.equals(gettingObj.id())
                && newName.equals(gettingObj.firstName())
                && newLastName.equals(gettingObj.lastName())
                && gettingObj.posts().containsAll(newSelectedPosts)
                ? MassageUtils.ACTION_SUCCESS
                : MassageUtils.ACTION_FAILED
        );
    }

    @Override
    void delete() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        boolean success = controller.deleteById(id);
        out.println(success ? MassageUtils.ACTION_SUCCESS : MassageUtils.ACTION_FAILED);
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
        return allPosts.stream()
                .filter(l -> Arrays.stream(ids).anyMatch(id -> id == l.id())).toList();
    }
}
