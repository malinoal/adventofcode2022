package two;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Two {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/two/input.txt"));
        System.out.println(part_two("./src/two/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int score = 0;
        while (input.hasNext()) {
            String currentLine = input.nextLine();
            String[] round = currentLine.split(" ");
            score += calculateScoreOne(round[0], round[1]);
        }
        return score;
    }

    private static int calculateScoreOne(String a, String b) {
        int score = 0;
        switch (b) {
            case "X":
                score += 1;
                score += a.equals("A") ? 3 : a.equals("B") ? 0 : 6;
                break;
            case "Y":
                score += 2;
                score += a.equals("A") ? 6 : a.equals("B") ? 3 : 0;
                break;
            case "Z":
                score += 3;
                score += a.equals("A") ? 0 : a.equals("B") ? 6 : 3;
                break;
        }
        return score;
    }

    public static int part_two(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int score = 0;
        while (input.hasNext()) {
            String currentLine = input.nextLine();
            String[] round = currentLine.split(" ");
            score += calculateScoreTwo(round[0], round[1]);
        }
        return score;
    }

    private static int calculateScoreTwo(String a, String b) {
        int score = 0;
        switch (a) {
            case "A":
                score += b.equals("X") ? 3 : b.equals("Y") ? 4 : 8;
                break;
            case "B":
                score += b.equals("X") ? 1 : b.equals("Y") ? 5 : 9;
                break;
            case "C":
                score += b.equals("X") ? 2 : b.equals("Y") ? 6 : 7;
                break;
        }
        return score;
    }

}