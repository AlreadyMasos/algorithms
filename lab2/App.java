import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {

    public int n = 0;
    public boolean checkRandom = true;
    Scanner in = new Scanner(System.in);

    public void App() {
        // Генерация массива
        interview();
        BinaryTree[] arrive = new BinaryTree [n];
        for (int i = 0; i < n; i += 1) {
            arrive[i] = new BinaryTree();
        }
        generateArrive(checkRandom, arrive);
        showArrive(arrive);

        //Создание бинарного дерева и показ его
        forTree tree = new forTree();
        tree.generateTree(arrive);

        // Поиск элемента
        System.out.print("\nВведите число для поиска: ");
        int itemToSearch = in.nextInt();
        boolean checkFind = tree.findElement(arrive, itemToSearch);
        if (checkFind) {
            System.out.print("BinaryTree: Путь до элемента: ");
            tree.showTree(arrive, itemToSearch);

            // Поиск элемента стандартными методами java
            findElementWithJava(arrive, itemToSearch);
        }
        in.close();



    }

    public void interview() {

        System.out.println("Генерация массива");
        System.out.print("Генерировать автоматически?(0 - нет, 1 - да) ");
        int check = in.nextInt();
        if (check == 0) {
            checkRandom = false;
        } else {
            checkRandom = true;
        }

        System.out.print("Введите размер массива(не вводите большие размеры массива): ");
        n = in.nextInt();

    }

    public void generateArrive(boolean checkRandom, BinaryTree[] arrive) {
        // Проверка на необходимость автоматической генерации
        if (checkRandom)  {
            Random rnd = new Random();
            // Генерация с проверкой на одинаковые элементы
            for (int i = 0; i < n; i += 1) {
                int num = rnd.nextInt(1000000);
                for (int j = 0; j < i; j += 1) {
                    if (num == arrive[j].value) {
                        num += 1; // Изменение на рандомное число
                        j = -1; // Проверка этого числа
                    }
                }
                arrive[i].value = num;
            }
        } else {
            // Ввод значений массива вручную
            for (int i = 0; i < n; i += 1) {
                System.out.print("Введите значение " + i + "-ого элемента: ");
                int number = in.nextInt();
                arrive[i].value = number;
            }
        }
    }

    public void showArrive(BinaryTree[] arrive) {
        // Вывод массива
        System.out.println("\nМассив: ");
        for (int i = 0; i < n; i += 1) {
            if (i % 1000 == 0) {
                System.out.print("\n" + arrive[i].value + "(" + i + ")" + " ");
            } else {
                System.out.print(arrive[i].value + "(" + i + ")" + " ");
            }
        }
        System.out.print("\n");
    }

    public void findElementWithJava(BinaryTree[] arrive, int itemToSearch) {
        int copyArrive [] = new int [arrive.length];
        for (int i = 0; i < arrive.length; i += 1) {
            copyArrive[i] = arrive[i].value;
        }
        System.out.println("\nСортировка массива для поиска при помощи Java: ");
        long time = System.currentTimeMillis();
        Arrays.sort(copyArrive);
        int find = Arrays.binarySearch(copyArrive, itemToSearch);
        long time2 = System.currentTimeMillis();
        for (int i = 0; i < arrive.length; i += 1) {
            if (i % 100 == 0) {
                System.out.print("\n" + copyArrive[i] + "(" + i + ")" + " ");
            } else {
                System.out.print(copyArrive[i] + "(" + i + ") ");
            }
        }
        System.out.println("\nJavaSearch: Индекс искомового элемента: " + find);
        System.out.println("JavaSearch: Время поиска: " + (time2 - time));
    }

}
