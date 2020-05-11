import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.print("Введите текст: ");
        String text = input.nextLine();
        System.out.print("Введите подстроку для поиска: ");
        String str = input.nextLine();
        System.out.print("Чувствительно к регистру?(0 - нет. 1 - да): ");
        int caseOfLetters = input.nextInt();

        // чувствительность к регистру
        if (caseOfLetters == 0) {
            text = text.toUpperCase();
            str = str.toUpperCase();
        }

        long time1 = System.currentTimeMillis();
        // создаем префикс функцию
        int piArray[] = new int[str.length()];
        KMP findStr = new KMP(text, str);
        findStr.prefixFunc(piArray);

        // осуществляем поиск
        int result = findStr.KMPFunc(piArray);
        long timeMy = System.currentTimeMillis() - time1;

        // выводим результат на экран
        if (result == -1) {
            System.out.println("Данная строка не содержится в тексте");
        } else {
            // Выводим на экран
            System.out.println("Найденная подстрока выделена на отдельной строке восклицательными знаками:");
            findStr.showResultKMP(result);
        }

        

        input.close();
    }
}
