import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class QuestionTwo {
  public static void main(String[] args) throws IOException {
    String filename = (args.length > 0) ? args[0] : "input.txt";
    RBTree tree = new RBTree();

    String text = Files.readString(Path.of(filename));
    for (String token : text.trim().split("[\\s,]+")) {
      if (!token.isEmpty()) {
        tree.insert(Integer.parseInt(token));
      }
    }
    System.out.println("Built tree from " + filename + "   height: " + tree.height());
    System.out.println("Commands: insert x, delete x, search x, min, max, "
        + "successor x, predecessor x, sort, quit");

    Scanner in = new Scanner(System.in);
    while (true) {
      System.out.print("> ");
      if (!in.hasNextLine()) break;
      String[] parts = in.nextLine().trim().split("\\s+");
      String cmd = parts[0].toLowerCase();
      if (cmd.isEmpty()) continue;
      if (cmd.equals("quit")) break;

      try {
        switch (cmd) {
          case "insert" -> tree.insert(Integer.parseInt(parts[1]));
          case "delete" -> tree.delete(Integer.parseInt(parts[1]));
          case "search" -> {
            int k = Integer.parseInt(parts[1]);
            System.out.println(tree.isNil(tree.search(k)) ? k + " not found" : k + " found");
          }
          case "min" -> System.out.println(tree.isEmpty() ? "tree is empty" : "min: " + tree.min().getKey());
          case "max" -> System.out.println(tree.isEmpty() ? "tree is empty" : "max: " + tree.max().getKey());
          case "successor", "predecessor" -> {
            int k = Integer.parseInt(parts[1]);
            RBNode x = tree.search(k);
            if (tree.isNil(x)) {
              System.out.println(k + " not found");
            } else {
              RBNode r = cmd.equals("successor") ? tree.successor(x) : tree.predecessor(x);
              System.out.println(tree.isNil(r) ? "no " + cmd + " for " + k : cmd + " of " + k + ": " + r.getKey());
            }
          }
          case "sort" -> tree.outputInSorted();
          default -> {
            System.out.println("unknown command");
            continue;
          }
        }
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("usage: " + cmd + " <number>");
        continue;
      }
      System.out.println("height: " + tree.height());
    }
  }
}