import java.util.Scanner;

public class QuestionThree {
  public static void main(String[] args) {
    SkipList list = new SkipList();
    Scanner in = new Scanner(System.in);
    System.out.println("Commands: insert x, delete x, lookup x, print, quit (comma-separated is fine)");

    while (true) {
      System.out.print("> ");
      if (!in.hasNextLine()) break;
      for (String command : in.nextLine().split(",")) {
        String[] parts = command.trim().split("\\s+");
        String cmd = parts[0].toLowerCase();
        if (cmd.isEmpty()) continue;
        if (cmd.equals("quit")) return;
        try {
          switch (cmd) {
            case "insert" -> {
              int k = Integer.parseInt(parts[1]);
              if (list.search(k)) {
                System.out.println(k + " already present");
              } else {
                list.insertNode(k);
              }
            }
            case "delete" -> list.delete(Integer.parseInt(parts[1]));
            case "lookup", "search" -> {
              int k = Integer.parseInt(parts[1]);
              System.out.println(k + (list.search(k) ? " found" : " not found"));
            }
            case "print" -> { }
            default -> {
              System.out.println("unknown command: " + cmd);
              continue;
            }
          }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          System.out.println("usage: " + cmd + " <number>");
          continue;
        }
        System.out.println("after " + command.trim() + ":");
        list.print();
      }
    }
  }
}