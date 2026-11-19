import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class QuestionOne {

  static String[] tokenize(String text) {
    return text.toLowerCase().split("[^a-z]+");
  }

  public static void main(String[] args) throws IOException {
    String filename = (args.length > 0) ? args[0] : "alice_in_wonderland.txt";
    String text = Files.readString(Path.of(filename), StandardCharsets.ISO_8859_1);
    String[] words = tokenize(text);

    int[] sizes = {30, 300, 1000};
    for (int m : sizes) {
      HashTable table = new HashTable(m);
      for (String w : words) {
        if (!w.isEmpty()) {
          table.increase(w);
        }
      }

      table.listAllLists("counts_" + m + ".txt");

      double alpha = table.computeAlpha();
      System.out.println("===== m = " + m + " =====");
      System.out.println("n (distinct words) = " + table.getDistinctN());
      System.out.println("alpha = n/m        = " + alpha);
      System.out.println("variance           = " + table.computeVariance());
      System.out.println("expected if random = " + alpha * (1 - 1.0 / m));

      System.out.println("histogram (length: number of slots)");
      int[] hist = table.histogram();
      for (int len = 0; len < hist.length; len++) {
        if (hist[len] > 0) {
          System.out.println("  " + len + ": " + hist[len]);
        }
      }

      System.out.println("longest 10% of lists: " + Arrays.toString(table.topTenPercent()));
      System.out.println();
    }
  }
}