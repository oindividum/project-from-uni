package hibernate.using.annotation;

import hibernate.using.CommonMethods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.annotation/hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1.  Добавить сотрудника");
                System.out.println("2. Найти по ID");
                System.out.println("3. Найти по имени");
                System.out.println("4. Найти по дате рождения");
                System.out.println("5. Обновить сотрудника");
                System.out.println("6. Удалить сотрудника");
                System.out.println("7. Показать всех");
                System.out.println("8. Общая сумма зарплат");
                System.out.println("9. Удалить всех");
                System.out.println("0. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        CommonMethods.addEmployee(session, scanner);
                        break;
                    case 2:
                        CommonMethods.searchEmployeeById(session, scanner);
                        break;
                    case 3:
                        CommonMethods.searchEmployeeByName(session, scanner);
                        break;
                    case 4:
                        CommonMethods.searchEmployeeByDateOfBirth(session, scanner);
                        break;
                    case 5:
                        CommonMethods.updateEmployee(session, scanner);
                        break;
                    case 6:
                        CommonMethods.deleteEmployee(session, scanner);
                        break;
                    case 7:
                        CommonMethods.showAllEmployees(session);
                        break;
                    case 8:
                        CommonMethods.calculateTotalSalaries(session);
                        break;
                    case 9:
                        CommonMethods.clearTable(session);
                        break;
                    case 0:
                        System.out.println("Выход прогарммы...");
                        return;
                    default:
                        System.out.println("ошибка ищи проблему и запускай занятия.");
                }
            }
        }
    }
}
