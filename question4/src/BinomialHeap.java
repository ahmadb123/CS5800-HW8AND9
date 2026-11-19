public class BinomialHeap {
  private BinomialNode head;

  public BinomialHeap() {
    head = null;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public BinomialNode minimum() {
    if (head == null) {
      return null;
    }
    BinomialNode y = head;
    BinomialNode x = head.getSibling();
    while (x != null) {
      if (x.getKey() < y.getKey()) {
        y = x;
      }
      x = x.getSibling();
    }
    return y;
  }

  private static void link(BinomialNode y, BinomialNode z) {
    y.setParent(z);
    y.setSibling(z.getChild());
    z.setChild(y);
    z.setDegree(z.getDegree() + 1);
  }

  private static BinomialNode merge(BinomialNode a, BinomialNode b) {
    BinomialNode dummy = new BinomialNode(0);
    BinomialNode tail = dummy;
    while (a != null && b != null) {
      if (a.getDegree() <= b.getDegree()) {
        tail.setSibling(a);
        a = a.getSibling();
      } else {
        tail.setSibling(b);
        b = b.getSibling();
      }
      tail = tail.getSibling();
    }
    tail.setSibling(a != null ? a : b);
    return dummy.getSibling();
  }

  public void union(BinomialHeap other) {
    BinomialNode newHead = merge(this.head, other.head);
    other.head = null;
    if (newHead == null) {
      head = null;
      return;
    }
    BinomialNode prev = null;
    BinomialNode x = newHead;
    BinomialNode next = x.getSibling();
    while (next != null) {
      if (x.getDegree() != next.getDegree()
          || (next.getSibling() != null && next.getSibling().getDegree() == x.getDegree())) {
        prev = x;
        x = next;
      } else if (x.getKey() <= next.getKey()) {
        x.setSibling(next.getSibling());
        link(next, x);
      } else {
        if (prev == null) {
          newHead = next;
        } else {
          prev.setSibling(next);
        }
        link(x, next);
        x = next;
      }
      next = x.getSibling();
    }
    head = newHead;
  }

  public BinomialNode insert(int key) {
    BinomialNode x = new BinomialNode(key);
    BinomialHeap single = new BinomialHeap();
    single.head = x;
    union(single);
    return x;
  }

  public BinomialNode extractMin() {
    if (head == null) {
      return null;
    }
    BinomialNode min = head;
    BinomialNode minPrev = null;
    BinomialNode prev = head;
    BinomialNode cur = head.getSibling();
    while (cur != null) {
      if (cur.getKey() < min.getKey()) {
        min = cur;
        minPrev = prev;
      }
      prev = cur;
      cur = cur.getSibling();
    }
    removeRoot(min, minPrev);
    return min;
  }

  private void removeRoot(BinomialNode root, BinomialNode prev) {
    if (prev == null) {
      head = root.getSibling();
    } else {
      prev.setSibling(root.getSibling());
    }

    BinomialNode reversed = null;
    BinomialNode child = root.getChild();
    while (child != null) {
      BinomialNode nextChild = child.getSibling();
      child.setSibling(reversed);
      child.setParent(null);
      reversed = child;
      child = nextChild;
    }

    BinomialHeap children = new BinomialHeap();
    children.head = reversed;
    union(children);

    root.setChild(null);
    root.setSibling(null);
    root.setDegree(0);
  }

  public void decreaseKey(BinomialNode x, int newKey) {
    if (newKey > x.getKey()) {
      throw new IllegalArgumentException("new key " + newKey + " is greater than current key " + x.getKey());
    }
    x.setKey(newKey);
    BinomialNode y = x;
    BinomialNode z = y.getParent();
    while (z != null && y.getKey() < z.getKey()) {
      int temp = y.getKey();
      y.setKey(z.getKey());
      z.setKey(temp);
      y = z;
      z = y.getParent();
    }
  }

  public void delete(BinomialNode x) {
    BinomialNode y = x;
    while (y.getParent() != null) {
      BinomialNode z = y.getParent();
      int temp = y.getKey();
      y.setKey(z.getKey());
      z.setKey(temp);
      y = z;
    }
    BinomialNode prev = null;
    for (BinomialNode r = head; r != y; r = r.getSibling()) {
      prev = r;
    }
    removeRoot(y, prev);
  }

  public BinomialNode find(int key) {
    return findIn(head, key);
  }

  private BinomialNode findIn(BinomialNode list, int key) {
    for (BinomialNode x = list; x != null; x = x.getSibling()) {
      if (x.getKey() == key) {
        return x;
      }
      if (x.getKey() < key) {
        BinomialNode found = findIn(x.getChild(), key);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }

  public boolean isValid() {
    int prevDegree = -1;
    for (BinomialNode t = head; t != null; t = t.getSibling()) {
      if (t.getDegree() <= prevDegree || t.getParent() != null) {
        return false;
      }
      if (checkTree(t) != (1 << t.getDegree())) {
        return false;
      }
      prevDegree = t.getDegree();
    }
    return true;
  }

  private int checkTree(BinomialNode x) {
    int size = 1;
    int expectedDegree = x.getDegree() - 1;
    int childCount = 0;
    for (BinomialNode c = x.getChild(); c != null; c = c.getSibling()) {
      if (c.getKey() < x.getKey() || c.getParent() != x || c.getDegree() != expectedDegree) {
        return -1;
      }
      int s = checkTree(c);
      if (s < 0) {
        return -1;
      }
      size += s;
      expectedDegree--;
      childCount++;
    }
    return childCount == x.getDegree() ? size : -1;
  }

  public void print() {
    if (head == null) {
      System.out.println("  (empty heap)");
      return;
    }
    for (BinomialNode t = head; t != null; t = t.getSibling()) {
      System.out.println("  B" + t.getDegree() + ":");
      printTree(t, 2);
    }
  }

  private void printTree(BinomialNode x, int indent) {
    System.out.println(" ".repeat(indent * 2) + x.getKey());
    for (BinomialNode c = x.getChild(); c != null; c = c.getSibling()) {
      printTree(c, indent + 1);
    }
  }
}