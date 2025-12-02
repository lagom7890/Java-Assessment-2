/*
Name: Sun Runze
UoR ID: 33810887
NUIST ID: 202483710021

 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SortComparison {
    public static void main(String[] args) throws IOException {
        sortComparison(new String[]{"sort10.txt", "sort50.txt", "sort100.txt", "sort200.txt", "sort1000.txt",
                "sort5000.txt", "sort8000.txt", "sort10000.txt", "sort20000.txt", "sort50000.txt"});
    }
    static int cardCompare(String card1, String card2){
        String suit1 = card1.substring(card1.length() - 1);
        String suit2 = card2.substring(card2.length() - 1);
        int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
        int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
        int suitNum1 = 0;
        int suitNum2 = 0;
        int result = 100;
        switch (suit1){
            case "H":
                suitNum1 = 0;
                break;
            case "C":
                suitNum1 = 1;
                break;
            case "D":
                suitNum1 = 2;
                break;
            case "S":
                suitNum1 = 3;
                break;
        }
        switch (suit2){
            case "H":
                suitNum2 = 0;
                break;
            case "C":
                suitNum2 = 1;
                break;
            case "D":
                suitNum2 = 2;
                break;
            case "S":
                suitNum2 = 3;
                break;
        }
        if (suitNum1 > suitNum2){
            result = 1;
        }
        else if (suitNum1 < suitNum2){
            result = -1;
        }
        else {
            if (number1 > number2){
                result = 1;
            }
            else if (number1 < number2){
                result = -1;
            }
            else {
                result = 0;
            }
        }
        return result;
    }

    static ArrayList<String> bubbleSort(ArrayList<String> array){
        for (int i = 0; i < array.size() - 1; i++){
            for (int j = 0; j < array.size() - 1 - i; j++){
                if (cardCompare(array.get(j), array.get(j + 1)) == 1){
                    String temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }

        }
        return array;
    }

    static ArrayList<String> mergeSort(ArrayList<String> array){
        if (array.size() <= 1) {
            return array;
        }

        int mid = array.size() / 2;

        ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
        ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        ArrayList<String> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (cardCompare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    static void sortComparison(String[] filenames) throws IOException{
        int n = filenames.length;

        int[] sizes = new int[n];
        long[] bubbleTimes = new long[n];
        long[] mergeTimes = new long[n];

        for (int i = 0; i < n; i++) {
            String fname = filenames[i];

            ArrayList<String> cards = new ArrayList<>();
            try {
                Scanner sc = new Scanner(new File(fname));
                while (sc.hasNextLine()) {
                    cards.add(sc.nextLine().trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            sizes[i] = cards.size();

            ArrayList<String> bubbleList = new ArrayList<>(cards);
            ArrayList<String> mergeList = new ArrayList<>(cards);

            long start = System.currentTimeMillis();
            bubbleSort(bubbleList);
            long end = System.currentTimeMillis();
            bubbleTimes[i] = end - start;

            start = System.currentTimeMillis();
            mergeList = mergeSort(mergeList);
            end = System.currentTimeMillis();
            mergeTimes[i] = end - start;
        }

        PrintWriter writer = new PrintWriter("sortComparison.csv");

        writer.print(",");
        for (int s : sizes) {
            writer.print(" " + s + ",");
        }
        writer.println();

        writer.print("bubbleSort,");
        for (long t : bubbleTimes) {
            writer.print(" " + t + ",");
        }
        writer.println();

        writer.print("mergeSort ,");
        for (long t : mergeTimes) {
            writer.print(" " + t + ",");
        }
        writer.println();

        writer.close();
    }
}