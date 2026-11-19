import java.util.Arrays;
import java.util.Random;

public class QuestionFour {
  private static final Random RAND = new Random(42);

  public static void main(String[] args) {
    demo();
    System.out.println();
    for (int n : new int[]{10, 50, 200, 1000}) {
      randomTest(n);
    }
  }

  private static void demo() {
    System.out.println("===== demo =====");
    BinomialHeap h = new BinomialHeap();
    int[] keys = randomArray(10);
    System.out.println("insert " + Arrays.toString(keys));
    for (int k : keys) {
      h.insert(k);
    }
    show(h);

    System.out.println("minimum: " + h.minimum().getKey());

    System.out.println("extract-min: " + h.extractMin().getKey());
    show(h);

    int target = keys[1];
    BinomialNode x = h.find(target);
    if (x != null) {
      int newKey = h.minimum().getKey() - 1;
      System.out.println("decrease-key " + target + " -> " + newKey);
      h.decreaseKey(x, newKey);
      show(h);
    }

    int toDelete = keys[2];
    BinomialNode d = h.find(toDelete);
    if (d != null) {
      System.out.println("delete " + toDelete);
      h.delete(d);
      show(h);
    }

    BinomialHeap other = new BinomialHeap();
    int[] more = randomArray(5);
    for (int k : more) {
      other.insert(k);
    }
    System.out.println("union with a heap of " + Arrays.toString(more));
    h.union(other);
    show(h);
  }

  private static void randomTest(int n) {
    BinomialHeap h = new BinomialHeap();
    int[] keys = randomArray(n);
    boolean ok = true;
    for (int k : keys) {
      h.insert(k);
      ok &= h.isValid();
    }

    for (int i = 0; i < n / 5; i++) {
      BinomialNode x = h.find(keys[RAND.nextInt(n)]);
      if (x != null) {
        h.decreaseKey(x, x.getKey() - RAND.nextInt(100));
        ok &= h.isValid();
      }
    }

    int deletes = 0;
    for (int i = 0; i < n / 5; i++) {
      BinomialNode x = h.find(keys[RAND.nextInt(n)]);
      if (x != null) {
        h.delete(x);
        deletes++;
        ok &= h.isValid();
      }
    }

    BinomialHeap other = new BinomialHeap();
    int[] extra = randomArray(n / 2);
    for (int k : extra) {
      other.insert(k);
    }
    h.union(other);
    ok &= h.isValid();

    int expected = n - deletes + extra.length;
    int count = 0;
    int last = Integer.MIN_VALUE;
    boolean sorted = true;
    while (!h.isEmpty()) {
      int k = h.extractMin().getKey();
      if (k < last) {
        sorted = false;
      }
      last = k;
      count++;
      ok &= h.isValid();
    }

    System.out.println("random test n=" + n
        + ": properties held after every operation = " + ok
        + ", extract-min output sorted = " + sorted
        + ", sizes match = " + (count == expected));
  }

  private static int[] randomArray(int n) {
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = RAND.nextInt(1000);
    }
    return a;
  }

  private static void show(BinomialHeap h) {
    h.print();
    System.out.println("  valid binomial heap: " + h.isValid());
  }
}