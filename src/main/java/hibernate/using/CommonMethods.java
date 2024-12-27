package hibernate.using;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernate.using.annotation.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommonMethods {

    public static <T> List<T> getAllEntriesUsingCriteriaApi(Session session, Class<T> myClass) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(myClass);
        Root<T> rootEntry = cq.from(myClass);
        CriteriaQuery<T> all = cq.select(rootEntry);

        TypedQuery<T> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    public static <T> List<T> getAllEntriesUsingHQL(Session session, Class<T> myClass) {
        return session.createQuery("FROM Person", myClass).getResultList();
    }

    public static void addEmployee(Session session, Scanner scanner) {
        // Request employee data from the user
        System.out.println("Имя:");
        String firstName = scanner.nextLine();

        System.out.println("Фамилия:");
        String surname = scanner.nextLine();

        System.out.println("Адрес:");
        String address = scanner.nextLine();

        System.out.println("Дата рождения:");
        String dateOfBirth = scanner.nextLine();

        System.out.println("Место рождения:");
        String placeOfBirth = scanner.nextLine();

        System.out.println("Зарплата:");
        int salary = scanner.nextInt();
        scanner.nextLine(); // Clear buffer after integer input

        // Create a Person object with the entered data
        Person person = new Person(firstName, surname, address, dateOfBirth, placeOfBirth, salary);

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        try {
            // Save the Person object to the database
            session.persist(person);

            // Commit the transaction
            transaction.commit();

            // Output success message
            System.out.println("Добавлени Emplyee: " + person);
        } catch (Exception e) {
            // Rollback the transaction in case of error
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Ошибка. Employee не добавлен: " + e.getMessage());
        }
    }

    public static void searchEmployeeByName(Session session, Scanner scanner) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        // CriteriaBuilder is a Hibernate object that allows you to create queries programmatically.
        System.out.println("Имя: ");
        String firstName = scanner.nextLine();

        CriteriaQuery<Person> query = cb.createQuery(Person.class);
        // CriteriaQuery<Person> is an object that will represent our SQL query.
        // It returns records of the Person entity (rows of the people table in the database).

        Root<Person> root = query.from(Person.class); // Root of the query
        List<Predicate> predicates = new ArrayList<>(); // List of conditions
        // Predicate is an object representing a single condition for the query (e.g., firstName = 'John')
        if (firstName != null) {
            predicates.add(cb.equal(cb.lower(root.get("Фамилия")), firstName.toLowerCase()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        List<Person> results = session.createQuery(query).getResultList();
        if (results.isEmpty()) {
            System.out.println("Employee не найден");
        } else {
            for (Person person : results) {
                System.out.println(person);
            }
        }
    }

    public static void searchEmployeeById(Session session, Scanner scanner) {
        System.out.println("ввеидет employee ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Person person = session.get(Person.class, id);
        if (person != null) {
            System.out.println("Employee не найден: " + person);
        } else {
            System.out.println("Employee с ID " + id + " не найден");
        }
    }

    public static void searchEmployeeByDateOfBirth(Session session, Scanner scanner) {
        System.out.println("Enter employee's date of birth in the format dd.mm.yyyy:");
        String dateOfBirth = scanner.nextLine();

        // Create query using Criteria API:
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> query = cb.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        // Condition for searching by date of birth
        query.select(root).where(cb.equal(root.get("дата рождения"), dateOfBirth));
        // Execute the query
        List<Person> results = session.createQuery(query).getResultList();

        if (results.isEmpty()) {
            System.out.println("Employee с этой датой " + dateOfBirth + " не найден.");
        } else {
            System.out.println("найден emplyees:");
            for (Person person : results) {
                System.out.println(person);
            }
        }
    }

    public static void showAllEmployees(Session session) {
        System.out.println("Getting all people using HQL");

        List<Person> entriesHQL = getAllEntriesUsingHQL(session, Person.class);

        if (entriesHQL == null || entriesHQL.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Person entry : entriesHQL) {
                System.out.println(entry);
            }
        }
    }

    public static void updateEmployee(Session session, Scanner scanner) {
        System.out.println("Enter the ID of the employee you want to update: ");
        int id = scanner.nextInt();
        Person person = session.get(Person.class, id);
        if (person != null) {
            System.out.println("Employee found: " + person);

            Transaction transaction = null;
            try {
                while (true) {
                    transaction = session.beginTransaction();
                    System.out.println("Select what you want to update: \n" +
                            "1. Обновленное иям\n" +
                            "2.  Обновленное фамилия\n" +
                            "3.  Обновленное адресс\n" +
                            "4.  Обновленная дата\n" +
                            "5.  Обновленная место рождения\n" +
                            "6.  Обновленная зарплшата\n" +
                            "7. вызод");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer after nextInt()

                    switch (choice) {
                        case 1:
                            System.out.println("Enter new name:");
                            String newFirstName = scanner.nextLine();
                            person.setFirstName(newFirstName);
                            System.out.println("Name updated");
                            break;

                        case 2:
                            System.out.println("Enter new surname:");
                            String newSurname = scanner.nextLine();
                            person.setSurname(newSurname);
                            System.out.println("Surname updated");
                            break;

                        case 3:
                            System.out.println("Enter new address:");
                            String newAddress = scanner.nextLine();
                            person.setAddress(newAddress);
                            System.out.println("Address updated");
                            break;

                        case 4:
                            System.out.println("Enter new date of birth in the format dd.mm.yyyy:");
                            String newDateOfBirth = scanner.nextLine();
                            person.setBirthDate(newDateOfBirth);
                            System.out.println("Date of birth updated");
                            break;

                        case 5:
                            System.out.println("Enter new place of birth: ");
                            String newPlaceOfBirth = scanner.nextLine();
                            person.setBirth_place(newPlaceOfBirth);
                            System.out.println("Place of birth updated");
                            break;

                        case 6:
                            System.out.println("Enter new salary:");
                            int newSalary = scanner.nextInt();
                            person.setSalary(newSalary);
                            System.out.println("Salary updated");
                            break;

                        case 7:
                            System.out.println("Employee with updated data: " + person);
                            System.out.println("Exiting...");
                            transaction.commit();
                            return;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    // Commit the transaction after each modification
                    transaction.commit();
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback(); // Rollback changes in case of error
                }
                e.printStackTrace();
            }
        } else {
            System.out.println("Employee with ID " + id + " not found");
        }
    }

    public static void deleteEmployee(Session session, Scanner scanner) {
        Transaction transaction = null;
        while (true) {
            System.out.println("Enter the ID of the employee you want to delete or write 'exit' to exit: ");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                transaction = session.beginTransaction();
                Person person = session.get(Person.class, id);
                if (person != null) {
                    session.delete(person);
                    System.out.println("Employee " + person + " deleted");
                } else {
                    System.out.println("Employee with ID " + id + " not found");
                }
                transaction.commit();
            } else {
                String str = scanner.nextLine();
                if (str.equals("exit")) {
                    System.out.println("Exiting...");
                    return;
                }
                System.out.println("Invalid input");
            }
        }
    }

    public static void calculateTotalSalaries(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        // CriteriaBuilder is used to create queries based on the Criteria API
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        // Here you create the query skeleton, which is empty at this point. It knows that the result will be of type Integer.
        // But at this stage, the query does not know:
        // which table to take data from and what exactly needs to be calculated.
        Root<Person> root = query.from(Person.class);
        // Creates a Root object representing the table (or entity) Person in the query.
        // The method query.from(Person.class) tells the query: "Work with the Person entity
        // The root object is the "entry point" for working with the table fields. With it, you specify which columns you want to access:
        // root.get("salary") — access to the salary column.
        // root.get("name") — access to the name column.
        query.select(cb.sum(root.get("salary")));
        Integer totalSalary = session.createQuery(query).getSingleResult();
        System.out.println("Total salary: " + totalSalary);
    }

    public static void clearTable(Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Person";
            session.createQuery(hql).executeUpdate();
            transaction.commit();
            System.out.println("All records deleted from the table.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Error deleting records: " + e.getMessage());
        }
    }
}
