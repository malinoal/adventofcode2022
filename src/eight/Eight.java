package eight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Eight {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/eight/input.txt"));
        //System.out.println(part_two("./src/eight/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        List<List<Tree>> trees = new ArrayList<>();
        while (input.hasNext()) {
            String currentLine = input.nextLine();
            List<Tree> currentTrees = new ArrayList<>(currentLine.length());
            for (char c : currentLine.toCharArray()) {
                currentTrees.add(new Tree(c - '0'));
            }
            trees.add(currentTrees);
        }
        int[] left = new int[trees.size()];
        int[] up = new int[trees.size()];
        for (int i = 0; i < trees.size(); i++) {
            for (int j = 0; j < trees.get(0).size(); j++) {
                Tree currentTree = trees.get(i).get(j);
                if (i == 0 || j == 0 || i == trees.size() - 1 || j == trees.get(0).size() -1)
                    currentTree.atEdge();
                currentTree.left(left[i]);
                currentTree.up(up[j]);
                left[i] = currentTree.getHeight() > left[i] ? currentTree.getHeight() : left[i];
                up[j] = currentTree.getHeight() > up[j] ? currentTree.getHeight() : up[j];
                for (int k = 0; k < i; k++) trees.get(k).get(j).down(currentTree.getHeight());
                for (int k = 0; k < j; k++) trees.get(i).get(k).right(currentTree.getHeight());
            }
        }
        for (List<Tree> treeline : trees) {
            for (Tree tree : treeline) System.out.print("" + tree.getHeight() + (tree.visible() ? "T" : " "));
            System.out.println();
        }
        int result = 0;
        for (List<Tree> treeline : trees) for (Tree tree : treeline)
            if (tree.visible()) result++;
        return result;
    }

    private static class Tree {
        private final int height;
        private int left;
        private int right;
        private int up;
        private int down;

        private boolean atEdge;

        public Tree(int height) {
            this.height = height;
            left = 0;
            right = 0;
            up = 0;
            down = 0;
            atEdge = false;
        }

        public boolean visible() {
            return atEdge || left < height || right < height || up < height || down < height;
        }

        public void atEdge() {
            atEdge = true;
        }

        public int getHeight() {
            return height;
        }

        public void left(int left) {
            this.left = left > this.left ? left : this.left;
        }
        public void right(int right) {
            this.right = right > this.right ? right : this.right;
        }
        public void up(int up) {
            this.up = up > this.up ? up : this.up;
        }
        public void down(int down) {
            this.down = down > this.down ? down : this.down;
        }
    }
}