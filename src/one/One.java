package one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;
public class One {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/one/input.txt"));
        System.out.println(part_two("./src/one/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int maxCalories = 0;
        while (input.hasNext()) {
            int currentCalories = 0;
            while (input.hasNext()) {
                String currentLine = input.nextLine();
                if (currentLine.isEmpty())
                    break;
                currentCalories += Integer.parseInt(currentLine);
            }
            maxCalories = maxCalories > currentCalories ? maxCalories : currentCalories;
        }
        return maxCalories;
    }

    public static int part_two(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        PriorityQueue<Integer> caloriesums = new PriorityQueue<>( (x, y) -> y - x);
        while (input.hasNext()) {
            int currentCalories = 0;
            while (input.hasNext()) {
                String currentLine = input.nextLine();
                if (currentLine.isEmpty())
                    break;
                currentCalories += Integer.parseInt(currentLine);
            }
            caloriesums.add(currentCalories);
        }
        int maxThreeCalories = caloriesums.poll() + caloriesums.poll() + caloriesums.poll();
        return maxThreeCalories;
    }
}