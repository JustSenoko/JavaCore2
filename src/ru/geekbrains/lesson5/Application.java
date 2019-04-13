package ru.geekbrains.lesson5;

import java.util.Arrays;

public class Application {

    private static final int size = 10000000;
    private static final int threadCount = 2;

    public static void main(String[] args) {
        long timer;
        float[] arr = new float[size];

        Arrays.fill(arr, 1);

        timer = System.currentTimeMillis();
        countInOneThread(arr);
        printTimerResult(timer);

        Arrays.fill(arr, 1);

        timer = System.currentTimeMillis();
        countInNThreads(arr);
        printTimerResult(timer);

    }

    private static void countInOneThread(float[] arr) {
        System.out.print("1 поток: ");
        setNewValues(arr, 0);
    }
    private static void countInNThreads(float[] arr) {
        
        System.out.printf("%d потока: ", threadCount);
        
        Thread[] threads = new Thread[threadCount];

        float[][] partArr = splitArray(arr);

        int deltaI = 0; //разность индекса основного массива и его части
        for (int i = 0; i < threadCount; i++) {
            float[] arrI = partArr[i];
            int finalDeltaI = deltaI;
            threads[i] = new Thread(() -> setNewValues(arrI, finalDeltaI));
            threads[i].start();
            deltaI += partArr[i].length;
        }

        waitForThreadsJoin(threads);

        mergeArrays(partArr, arr);
    }

    private static void waitForThreadsJoin(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setNewValues(float[] arr, int deltaI) {
        for (int i = 0; i < arr.length; i++) {
            int k = i + deltaI;
            arr[i] = (float)(arr[i] * Math.sin(0.2f + k / 5) * Math.cos(0.2f + k / 5) * Math.cos(0.4f + k / 2));
        }
    }

    private static void printTimerResult(long timer) {
        System.out.println(System.currentTimeMillis() - timer);
    }

    private static float[][] splitArray(float[] arr) {

        float[][] partArrs = new float[threadCount][];
        int h = arr.length / threadCount;

        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                partArrs[i] = new float[size - h*(threadCount - 1)];
            } else {
                partArrs[i] = new float[h];
            }
            System.arraycopy(arr, i*h, partArrs[i], 0, partArrs[i].length);
        }

        return partArrs;
    }

    private static void mergeArrays(float[][] arr, float[] resultArr) {
        int lastPosition = 0;
        for (float[] floats : arr) {
            System.arraycopy(floats, 0, resultArr, lastPosition, floats.length);
            lastPosition += floats.length;
        }
    }

}
