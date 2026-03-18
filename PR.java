import java.io.*;
import java.util.Scanner;

public class PR {

    static final String FILE_NAME = "text.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            showMenu();

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        writeMultipleLines(scanner);
                        break;
                    case 2:
                        readAll();
                        break;
                    case 3:
                        readRange(scanner);
                        break;
                    case 4:
                        insertAtLine(scanner);
                        break;
                    case 5:
                        System.out.println("Вихід...");
                        break;
                    default:
                        System.out.println("Невірний вибір!");
                }
            } catch (Exception e) {
                System.out.println("Помилка введення!");
            }
        }

        scanner.close();
    }

    public static void showMenu() {
        System.out.println("\n=== Редактор Про ===");
        System.out.println("1. Додати рядки");
        System.out.println("2. Прочитати весь файл");
        System.out.println("3. Прочитати діапазон рядків");
        System.out.println("4. Вставити рядок у позицію");
        System.out.println("5. Вийти");
        System.out.print("Ваш вибір: ");
    }

    public static void writeMultipleLines(Scanner scanner) {
        System.out.println("Введіть рядки (порожній рядок — завершення):");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            int lineNumber = countLines();

            while (true) {
                String line = scanner.nextLine();
                if (line.equals("")) break;

                lineNumber++;
                writer.write(line);
                writer.newLine();

                System.out.println("Додано рядок № " + lineNumber);
            }

        } catch (IOException e) {
            System.out.println("Помилка запису!");
        }
    }

    public static void readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            int lineNumber = 1;

            System.out.println("\n=== Вміст файлу ===");

            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено!");
        } catch (IOException e) {
            System.out.println("Помилка читання!");
        }
    }

    public static void readRange(Scanner scanner) {
        System.out.print("Початковий рядок: ");
        int start = Integer.parseInt(scanner.nextLine());

        System.out.print("Кінцевий рядок: ");
        int end = Integer.parseInt(scanner.nextLine());

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                if (lineNumber >= start && lineNumber <= end) {
                    System.out.println(lineNumber + ": " + line);
                }
                lineNumber++;
            }

        } catch (IOException e) {
            System.out.println("Помилка!");
        }
    }

    public static void insertAtLine(Scanner scanner) {
        System.out.print("Номер рядка для вставки: ");
        int position = Integer.parseInt(scanner.nextLine());

        System.out.print("Введіть текст: ");
        String newLine = scanner.nextLine();

        try {
            int total = countLines();
            String[] lines = new String[total + 1];

            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                lines[i] = line;
                i++;
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));

            for (int j = 0; j < total; j++) {
                if (j == position - 1) {
                    writer.write(newLine);
                    writer.newLine();
                }
                writer.write(lines[j]);
                writer.newLine();
            }

            if (position > total) {
                writer.write(newLine);
                writer.newLine();
            }

            writer.close();

            System.out.println("Вставка виконана!");

        } catch (IOException e) {
            System.out.println("Помилка вставки!");
        }
    }

    public static int countLines() {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
        }

        return count;
    }
}