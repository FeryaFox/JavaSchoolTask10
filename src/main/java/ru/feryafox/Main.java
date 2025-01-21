package ru.feryafox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        String filePath = "numbers.txt";

        List<Integer> numbers = readNumbersFromFile(filePath);

        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        for (int number : numbers) {
            executor.execute(new FactorialTask(number));
        }

        executor.shutdown();
    }

    public static List<Integer> readNumbersFromFile(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    numbers.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Некорректное число в файле: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    static class FactorialTask implements Runnable {
        private final int number;

        public FactorialTask(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            BigInteger factorial = calculateFactorial(number);
            System.out.println("Факториал числа " + number + " = " + factorial);
        }

        private BigInteger calculateFactorial(int number) {
            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= number; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        }
    }
}
