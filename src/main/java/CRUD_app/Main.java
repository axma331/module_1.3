package CRUD_app;

import CRUD_app.view.BasicView;
import CRUD_app.view.PostView;

public class Main {
    public static void main(String[] args) {

//        BasicView view = new LabelView();
        BasicView view = new PostView();
        view.startDialog();

    }
}

