package CRUD_app.view;

public enum MassageUtils {
    WELCOME("""
            Введите желаемую команду:
            create | edit | get | delete | show all | exit.
            """),
    ENTER_ID("Введите ID:"),
    ENTER_NAME("Введите имя:"),
    ENTER_LAST_NAME("Введите фамилию:"),
    ENTER_POST_TITLE("Введите заголовок поста:"),
    ENTER_POST_CONTENT("Введите содержимое поста:"),
    ENTER_LABEL_NAME("Введите название ярлыка:"),
    ACTION_SUCCESS("Операция выполнена успешно!"),
    ACTION_FAILED("Ошибка выполнения операции!"),
    REPEAT("Команда не распознана, повторите ввод.");

    private final String description;

    MassageUtils(String _description) {
        description = _description;
    }

    @Override
    public String toString() {
        return description;
    }
}
