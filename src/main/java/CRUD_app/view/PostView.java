package CRUD_app.view;

import CRUD_app.controller.PostController;
import CRUD_app.model.Label;
import CRUD_app.model.Post;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PostView extends BasicView {

    private final PostController controller = new PostController();
    private final LabelView labelView = new LabelView();

    @Override
    void create() throws IOException {
        out.println(MassageUtils.ENTER_POST_TITLE);
        String title = in.readLine();
        out.println(MassageUtils.ENTER_POST_CONTENT);
        String content = in.readLine();
        List<Label> selectedLabels = showAndSelectLabel();
        Post createdObj = controller.create(title, content, selectedLabels);
        System.out.println(createdObj == null ? MassageUtils.ACTION_FAILED : MassageUtils.ACTION_SUCCESS);
    }

    @Override
    void get() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Post gettingObj = controller.getById(id);
        out.println(gettingObj != null
                ? gettingObj + "\n" + MassageUtils.ACTION_SUCCESS
                : MassageUtils.ACTION_FAILED
        );
    }

    @Override
    void update() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        Integer id = Integer.parseInt(in.readLine());
        out.println(MassageUtils.ENTER_POST_TITLE);
        String newTitle = in.readLine();
        out.println(MassageUtils.ENTER_POST_CONTENT);
        String newContent = in.readLine();
        List<Label> newSelectedLabels = showAndSelectLabel();
        Post gettingObj = controller.update(id, newTitle, newContent, newSelectedLabels);
        out.println(gettingObj != null
                && id.equals(gettingObj.id())
                && newTitle.equals(gettingObj.title())
                && newContent.equals(gettingObj.content())
                && gettingObj.labels().containsAll(newSelectedLabels)
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
        getAll().forEach(p -> out.println(
                String.format("%2d", p.id()) + " title: " + p.title() + ", content: " + p.content()
                        + ",\n" + p.labels() + "\n" + p.status()));
    }

    public List<Post> getAll() {
        return controller.getAll();
    }

    private List<Label> showAndSelectLabel() throws IOException {
        out.println("Введите id ярлыков для добавления");
        List<Label> database = labelView.getAll();
        database.forEach(e -> out.println(e.id() + ": " + e.name()));
        String inputIdSet = in.readLine();
        int[] ids = Arrays.stream(inputIdSet.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        Arrays.stream(ids).forEach(out::println);
        return database.stream().
                filter(l -> Arrays.stream(ids).anyMatch(id -> id == l.id())).toList();
    }
}
