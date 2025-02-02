package CRUD_app.view;

import CRUD_app.controller.LabelController;
import CRUD_app.model.Label;

import java.io.IOException;
import java.util.List;

public class LabelView extends BasicView {
    private final LabelController controller = new LabelController();

    @Override
    void create() throws IOException {
        out.println(MassageUtils.ENTER_LABEL_NAME);
        String nameName = in.readLine();
        Label createdObj = controller.create(nameName);
        System.out.println(createdObj == null ? MassageUtils.ACTION_FAILED : MassageUtils.ACTION_SUCCESS);
    }

    @Override
    void get() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        int id = Integer.parseInt(in.readLine());
        Label gettingObj = controller.getById(id);
        out.println(gettingObj != null
                ? gettingObj + "\n" + MassageUtils.ACTION_SUCCESS
                : MassageUtils.ACTION_FAILED
        );
    }

    @Override
    void update() throws IOException {
        out.println(MassageUtils.ENTER_ID);
        Integer id = Integer.parseInt(in.readLine());
        out.println(MassageUtils.ENTER_LABEL_NAME);
        String name = in.readLine();
        Label gettingObj = controller.update(id, name);
        out.println(gettingObj != null && id.equals(gettingObj.id()) && name.equals(gettingObj.name())
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
        getAll().forEach(l -> out.println(l.id() + ": " + l.name()));
    }

    List<Label> getAll() {
        return controller.getAll();
    }
}
