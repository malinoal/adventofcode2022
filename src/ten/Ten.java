package ten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ten {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/ten/input.txt"));
        //System.out.println(part_two("./src/ten/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        Machine machine = new Machine();
        while (input.hasNext()) {
            String[] ops = input.nextLine().split(" ");
            switch (ops[0]) {
                case "addx" -> {
                    machine.addX(Integer.parseInt(ops[1]));
                }
                case "noop" -> {
                    machine.tick();
                }
                default -> {
                    System.out.println("Unknown Instruction " + ops[0]);
                }
            }
        }
        machine.printScreen();
        return machine.getResult();
    }

    private static class Machine {
        int X;
        int cycle;
        int result;
        List<Integer> interestingCycles;

        boolean[] frameBuffer;
        public Machine() {
            X = 1;
            cycle = 1;
            result = 0;
            interestingCycles = Arrays.asList(20, 60, 100, 140, 180, 220);
            frameBuffer = new boolean[240];
            for (int i = 0; i < 240; i++) frameBuffer[i] = false;
        }

        public void addX(int V) {
            tick();
            tick();
            System.out.println("Adding " + V + " to X");
            X += V;
        }

        public void tick() {
            int printingPosition = (cycle % 40) - 1;
            if (printingPosition == -1) printingPosition = 39;
            System.out.println("Cycle: " + cycle + " X: " + X + " Printing position " + printingPosition);
            if (Math.abs(printingPosition - X) <= 1) {
                System.out.println("Rendering Sprite...");
                frameBuffer[cycle-1] = true;
            }
            if (interestingCycles.contains(cycle)) {
                System.out.println("Interesting Cycle: " + cycle + " X: " + X);
                System.out.println("Signal Strength: " + cycle * X);
                result += cycle * X;
                System.out.println("Result is now " + result);
            }
            cycle++;
        }

        public int getResult() {
            return result;
        }

        public void printScreen() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 40; j++) {
                    System.out.print(frameBuffer[j+i*40] ? '#' : '.');
                }
                System.out.println();
            }
        }
    }

}