package restTemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import restTemplate.config.MyConfig;
import restTemplate.model.User;

import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        //Список пользователей
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);

        //Добавление пользователя
        User newUser = new User(3L, "James", "Brown", (byte) 92);
        communication.saveUser(newUser);

        //Изменение пользователя
        User updUser = new User(3L, "Thomas", "Shelby", (byte) 92);
        communication.editUser(updUser);

        //Удаление пользователя
        communication.deleteUser(3L);
    }
}
