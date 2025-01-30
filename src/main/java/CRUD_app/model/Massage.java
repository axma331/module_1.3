package CRUD_app.model;

public enum Massage {
    WELCOME("""
            Введите желаемую команду:
            create | edit | get | delete | exit.
            """),
    ENTER_ID("Введите ID:"),
//    ENTER_NAME("Введите имя:"),
//    ENTER_LAST_NAME("Введите фамилию:"),
//    ENTER_TITLE("Введите заголовок поста:"),
//    ENTER_CONTENT("Введите содержимое поста:"),
    ENTER_LABEL_NAME("Введите название метки:"),
    ACTION_SUCCESS("Операция выполнена успешно!"),
    ACTION_FAILED("Ошибка выполнения операции!"),
    REPEAT("Команда не распознана, повторите ввод.");

    private final String description;

    Massage(String _description) {
        description = _description;
    }

    @Override
    public String toString() {
        return description;
    }
}
