package CRUD_app;

import CRUD_app.view.BasicView;
import CRUD_app.view.PostView;
import CRUD_app.view.WriterView;

public class Main {
    public static void main(String[] args) {

//        BasicView view = new LabelView();
//        BasicView view = new PostView();
        BasicView view = new WriterView();

        view.startDialog();

    }
}

