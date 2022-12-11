package eleven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

public class Eleven {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/eleven/input.txt"));
        //System.out.println(part_two("./src/eleven/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        Map<Integer, Monkey> monkeys = new HashMap<>();
        Map<Monkey, Integer> trueMonkeys = new HashMap<>();
        Map<Monkey, Integer> falseMonkeys = new HashMap<>();
        while (input.hasNext()) {
            String firstLine = input.nextLine();
            if (firstLine.length() == 0) continue;
            int monkeyNumber = Integer.parseInt(firstLine.substring(7, 8));
            String[] itemStrings = input.nextLine().substring(18).split(", ");
            List<Integer> items = Arrays.stream(itemStrings).map(Integer::parseInt).toList();
            Function<Integer, Integer> operator = getOperator(input.nextLine().substring(19));
            int test = Integer.parseInt(input.nextLine().substring(21));
            int trueMonkey = Integer.parseInt(input.nextLine().substring(29));
            int falseMonkey = Integer.parseInt(input.nextLine().substring(30));
            Monkey monkey = new Monkey(monkeyNumber, items, operator, test, null, null);
            monkeys.put(monkeyNumber, monkey);
            trueMonkeys.put(monkey, trueMonkey);
            falseMonkeys.put(monkey, falseMonkey);
        }
        monkeys.values().forEach(monkey -> {
            monkey.setTrueMonkey(monkeys.get(trueMonkeys.get(monkey)));
            monkey.setFalseMonkey(monkeys.get(falseMonkeys.get(monkey)));
        });

        for (int i = 1; i <= 20; i++) {
            for (int j = 0; j < monkeys.size(); j++) monkeys.get(j).tick();
            System.out.println("After round " + i + ", the monkeys are holding items with these worry levels:");
            for (int j = 0; j < monkeys.size(); j++) {
                System.out.println("Monkey " + j + ": " + monkeys.get(j).items);
            }
        }
        for (int j = 0; j < monkeys.size(); j++) {
            System.out.println("Monkey " + j + " inspected items " + monkeys.get(j).getInspected() + " times.");
        }
        List<Integer> monkeyBusiness = monkeys.values().stream().map(Monkey::getInspected)
                .map(i -> i*-1).sorted().map(i -> i * -1).toList();

        return monkeyBusiness.get(0) * monkeyBusiness.get(1);
    }

    private static Function<Integer, Integer> getOperator(String op) {
        String[] ops = op.split(" ");
        return i -> {
            int op1 = ops[0].equals("old") ? i : Integer.parseInt(ops[0]);
            int op2 = ops[2].equals("old") ? i : Integer.parseInt(ops[2]);
            return ops[1].equals("+") ? op1 + op2 : op1 * op2;
        };
    }

    private static class Monkey {
        private int number;
        private List<Integer> items;
        Function<Integer, Integer> operation;
        int test;
        Monkey trueMonkey;
        Monkey falseMonkey;

        int inspected;

        public Monkey(int number, List<Integer> items, Function<Integer, Integer> operation,
                      int test, Monkey trueMonkey, Monkey falseMonkey) {
            this.number = number;
            this.items = new ArrayList<>(items);
            this.operation = operation;
            this.test = test;
            this.trueMonkey = trueMonkey;
            this.falseMonkey = falseMonkey;
            this.inspected = 0;
        }

        public void tick() {
            for (int item : items) {
                inspected++;
                int newWorry = operation.apply(item) / 3;
                if (newWorry % test == 0) trueMonkey.addItem(newWorry);
                else falseMonkey.addItem(newWorry);
            }
            items.clear();
        }

        public void setTrueMonkey(Monkey trueMonkey) {
            this.trueMonkey = trueMonkey;
        }

        public void setFalseMonkey(Monkey falseMonkey) {
            this.falseMonkey = falseMonkey;
        }

        public void addItem(int item) {
            items.add(item);
        }

        public int getInspected() {
            return inspected;
        }

    }


}