public class forTree {
    public forTree() {}
    public void generateTree(BinaryTree[] arrive) {
        int arrIndex = 0;
        // Проход по каждому элементу массива
        for (int i = 1; i < arrive.length; i += 1) {
            boolean checkFill = false;
            // Определение места для нового элемента в дереве
            while (!checkFill) {
                // Спуск по левой ветке
                if (arrive[i].value < arrive[arrIndex].value) {
                    if (arrive[arrIndex].left == -1) {
                        // Добавление элемента
                        arrive[arrIndex].left = i;
                        arrive[i].parent = arrIndex;
                        arrIndex = 0;
                        checkFill = true;
                    } else {
                        // Продолжение спуска
                        arrIndex = arrive[arrIndex].left;
                    }
                // Спуск по правой ветке
                } else {
                    if (arrive[arrIndex].right == -1) {
                        // Добавление элемента
                        arrive[arrIndex].right = i;
                        arrive[i].parent = arrIndex;
                        arrIndex = 0;
                        checkFill = true;
                    } else {
                        // Продолжение спуска
                        arrIndex = arrive[arrIndex].right;
                    }
                }
            }
        }
    }
    public void showTree(BinaryTree[] arrive, int valueSearch) {
        boolean stop = false;
        int arrIndex = 0;
        // Идем по дереву до элемента
        while (stop == false) {
            if (valueSearch == arrive[arrIndex].value) {
                System.out.print(valueSearch);
                stop = true;
                break;
            }
            if (valueSearch < arrive[arrIndex].value) {
                System.out.print(arrive[arrIndex].value + " -> ");
                arrIndex = arrive[arrIndex].left;
            } else {
                System.out.print(arrive[arrIndex].value + " -> ");
                arrIndex = arrive[arrIndex].right;
            }
        }
        System.out.println();
    }
    public boolean findElement(BinaryTree[] arrive, int itemToSearch) {
        long time = System.currentTimeMillis();
        boolean find = false;
        int arrIndex = 0;
        while (!find) {
            // проверка на элемент
            if (arrive[arrIndex].value == itemToSearch) {
                System.out.println("Элемент найден. \n\nBinaryTree: Индекс элемента: " + arrIndex);
                find = true;
                long time2 = System.currentTimeMillis();
                System.out.println("BinaryTree: Время поиска: " + (time2 - time));
                return true;
            } else if ((itemToSearch < arrive[arrIndex].value) && (arrive[arrIndex].left != -1)) { // спуск на левую часть ветки
                arrIndex = arrive[arrIndex].left;
            } else if ((itemToSearch > arrive[arrIndex].value) && (arrive[arrIndex].right != -1)) { // спуск на правую часть ветки
                arrIndex = arrive[arrIndex].right;
            } else { // ветка закончилась
                System.out.println("Элемент не найден");
                find = true;
            }
        }
        return false;
    }
}
