public class KMP {
    public String text;
    public String str;
    public KMP(String textInput, String strInput) {
        text = textInput;
        str = strInput;
    }
    public void prefixFunc(int piArray[]) {
        if (text.length() < str.length()) {
            System.out.println("Ошибка. Длина подстроки должна быть меньше длины строки");
            return;
        }

        piArray[0] = 0;
        if (str.length() < 2) {
            return;
        }

        for (int i = 1; i < str.length(); i += 1) {
            int pIndex = 0;
            for (int j = 1; j <= i; j += 1) {
                String prefix = str.substring(0, j); // увеличиваем на каждой итерации значение префикса на один символ
                String suffix = str.substring(i + 1 - j, i + 1); // увеличиваем значение суффикса на 1 символ каждую итерацию
                if (prefix == suffix && pIndex < prefix.length()) {
                    pIndex = prefix.length();
                }
            }
            piArray[i] = pIndex;

        }
    }

    // Передаем префикс-функцию и возвращаем индекс с которого начинается подстрока в тексте
    public int KMPFunc(int piArray[]) {
        int index = 0;
        while (index < text.length()) {
            for (int j = 0; j < str.length(); j += 1) {
                // добавить условие если первая буква не подходит
                if (str.charAt(j) != text.charAt(index + j)) {
                    if (j == 0) { // если это первая буква
                        index += 1;
                    } else {
                        index += j - piArray[j - 1];
                    }
                    break;
                }
                if (j == str.length() - 1) {
                    return index;
                }
            }
        }
        return -1;
    }

    public void showResultKMP(int index) {
        System.out.print(text.substring(0, index));
        System.out.print("\n! -> " + text.substring(index, index + str.length()) + " <- !\n");
        System.out.print(text.substring(index + str.length()));
    }

}
