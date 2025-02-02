package CRUD_app;

import CRUD_app.view.BasicView;
import CRUD_app.view.LabelView;
import CRUD_app.view.WriterView;

public class AppRunner {
    public static void main(String[] args) {
        BasicView view = new LabelView();
        view.startDialog();
    }
}

