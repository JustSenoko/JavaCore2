package ru.geekbrains.lesson2;

public class Application {

    static final int SIZE = 4;

    public static void main(String[] args) {
        String[][][] arrays = new String[3][][];
        String[][] arr1 =  {{"1", "2", "3", "4"},
                            {"2", "3", "4", "5"},
                            {"3", "4", "5", "6"},
                            {"4", "5", "6", "7"}};

        String[][] arr2 =  {{"1", "b", "c", "d"},
                            {"2", "3", "4", "5"},
                            {"3", "4", "5", "6"},
                            {"4", "5", "6", "7"}};

        String[][] arr3 =  {{"1", "2", "3", "4", "5"},
                            {"2", "3", "4", "5", "6"},
                            {"3", "4", "5", "6"},
                            {"4", "5", "6", "7"}};
        arrays[0] = arr1;
        arrays[1] = arr2;
        arrays[2] = arr3;

        for (String[][] arr : arrays) {
            printArray(arr);
            try {
                System.out.printf("Сумма элементов массива равна %s%n%n", sumArray(arr));
            } catch (MyArraySizeException | MyArrayDataException ex) {
                System.out.println(ex);
            }
        }
    }

    private static int sumArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {

        if (arr.length != SIZE) {
            throw new MyArraySizeException(SIZE);
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4) {
                throw new MyArraySizeException(SIZE);
            }
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException ex) {
                    throw new MyArrayDataException(i, j, ex);
                }
            }
        }
        return sum;
    }

    private static void printArray(String[][] arr) {
        for (String[] strings : arr) {
            for (String string : strings) {
                System.out.printf("%4s", string);
            }
            System.out.println();
        }
    }


}
