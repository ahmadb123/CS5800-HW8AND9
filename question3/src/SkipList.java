import java.util.Random;

public class SkipList {
  private static final int MAX_LEVEL = 16;
  private SkipNode head;
  private int level = 1;
  private static final Random RAND = new Random(16);


  public SkipList(){
    this.head = new SkipNode(Integer.MIN_VALUE, MAX_LEVEL);
  }


  public void insertNode(int key){
    SkipNode[] update = findPredecessors(key);
    SkipNode target = update[0].getNext(0);
    if (target != null && target.getKey() == key) {
      return;
    }

    int h = randomHeight();
    if(h > level){
      for(int j = level; j < h; j++) {
        update[j] = head;
      }
      level = h;
    }
    SkipNode newNode = new SkipNode(key, h);
    for(int i = 0; i < h; i++){
      newNode.setNext(i, update[i].getNext(i));
      update[i].setNext(i, newNode);
    }
  }

  public int randomHeight(){
    int h = 1;
    while(h < MAX_LEVEL && RAND.nextBoolean()){
      h++;
    }
    return h;
  }

  public boolean search(int key){
    SkipNode x = head;
    for(int i = level - 1; i >= 0; i--){
      while(x.getNext(i) != null && x.getNext(i).getKey() < key){
        x = x.getNext(i);
      }
    }
    SkipNode candidate = x.getNext(0);
    return candidate != null && candidate.getKey() == key;
  }

  public void delete(int key){
    SkipNode[] update = findPredecessors(key);
    SkipNode target = update[0].getNext(0);
    if(target == null || target.getKey() != key){
      System.out.println("KEY NOT FOUND");
      return;
    }
    for(int i = 0; i < target.getHeight(); i++){
      update[i].setNext(i, target.getNext(i));
    }
    while(level > 1 && head.getNext(level - 1) == null){
      level --;
    }
  }

  private SkipNode[] findPredecessors(int key) {
    SkipNode[] update = new SkipNode[MAX_LEVEL];
    SkipNode x = head;
    for (int i = level - 1; i >= 0; i--) {
      while (x.getNext(i) != null && x.getNext(i).getKey() < key) {
        x = x.getNext(i);
      }
      update[i] = x;
    }
    return update;
  }


  public void print() {
    for (int i = level - 1; i >= 0; i--) {
      StringBuilder sb = new StringBuilder(String.format("L%-2d head", i));
      SkipNode x = head.getNext(0);
      while (x != null) {
        if (x.getHeight() > i) {
          sb.append(String.format(" -> %4d", x.getKey()));
        } else {
          sb.append("--------");
        }
        x = x.getNext(0);
      }
      sb.append(" -> nil");
      System.out.println(sb);
    }
  }
}