package four;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Four {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/four/input.txt"));
        System.out.println(part_two("./src/four/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int overlappingPairs = 0;
        while (input.hasNext()) {
            String currentLine = input.nextLine();
            int[] pairs = Arrays.stream(currentLine.split(","))
                    .flatMap(s -> Arrays.stream(s.split("-")))
                    .mapToInt(Integer::parseInt).toArray();
            if (pairs[0] >= pairs[2] && pairs[1] <= pairs[3]
             || pairs[2] >= pairs[0] && pairs[3] <= pairs[1])
                overlappingPairs++;
        }
        return overlappingPairs;
    }

    public static int part_two(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int overlappingPairs = 0;
        while (input.hasNext()) {
            String currentLine = input.nextLine();
            int[] pairs = Arrays.stream(currentLine.split(","))
                    .flatMap(s -> Arrays.stream(s.split("-")))
                    .mapToInt(Integer::parseInt).toArray();
            if (pairs[0] <= pairs[2] && pairs[1] >= pairs[2]
             || pairs[2] <= pairs[0] && pairs[3] >= pairs[0])
                overlappingPairs++;
        }
        return overlappingPairs;
    }
}