public class RBTree{
  private RBNode root;
  private RBNode sentinel;

  public RBTree(){
    this.sentinel = new RBNode(0, RBNode.Color.BLACK, null, null, null);
    this.root = sentinel;
  }


  public void outputInSorted(){
    inorder(root);
  }

  private void inorder(RBNode x){
    if(x != sentinel){
      inorder(x.getLeft());
      System.out.println(x.getKey());
      inorder(x.getRight());
    }
  }

  public RBNode search(int k){
    return searchTree(root, k);
  }
  private RBNode searchTree(RBNode x, int k){
    if(x == sentinel || k == x.getKey()){
      return x;
    }
    if(k < x.getKey()){
      return searchTree(x.getLeft(), k);
    }else {
      return searchTree(x.getRight(), k);
    }
  }

  public RBNode min(RBNode x){
    while(x.getLeft() != sentinel){
      x = x.getLeft();
    }
    return x;
  }

  public RBNode max(RBNode x){
    while(x.getRight() != sentinel){
      x = x.getRight();
    }
    return x;
  }

  public RBNode successor(RBNode x){
    if(x.getRight() != sentinel){
      return min(x.getRight());
    }else{
      RBNode y = x.getParent();
      while(y != sentinel && x == y.getRight()){
        x = y;
        y = y.getParent();
      }
      return y;
    }
  }

  public RBNode predecessor(RBNode x){
    if(x.getLeft() != sentinel){
      return max(x.getLeft());
    }else{
      RBNode y = x.getParent();
      while(y != sentinel && x == y.getLeft()){
        x = y;
        y = y.getParent();
      }
      return y;
    }
  }

  public void insert(int key) {
    RBNode z = new RBNode(key, RBNode.Color.RED, sentinel, sentinel, sentinel);

    RBNode x = root;
    RBNode y = sentinel;
    while (x != sentinel) {
      y = x;
      if (z.getKey() < x.getKey()) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }

    z.setParent(y);
    if (y == sentinel) {
      root = z;
    } else if (z.getKey() < y.getKey()) {
      y.setLeft(z);
    } else {
      y.setRight(z);
    }

    insertFixUp(z);
  }
  private void insertFixUp(RBNode z) {
    while (z.getParent().getColor() == RBNode.Color.RED) {
      if (z.getParent() == z.getParent().getParent().getLeft()) {
        RBNode y = z.getParent().getParent().getRight();          // uncle
        if (y.getColor() == RBNode.Color.RED) {                   // case 1
          z.getParent().setColor(RBNode.Color.BLACK);
          y.setColor(RBNode.Color.BLACK);
          z.getParent().getParent().setColor(RBNode.Color.RED);
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getRight()) {                  // case 2
            z = z.getParent();
            leftRotate(z);
          }
          z.getParent().setColor(RBNode.Color.BLACK);           // case 3
          z.getParent().getParent().setColor(RBNode.Color.RED);
          rightRotate(z.getParent().getParent());
        }
      } else {                                                      // mirror image
        RBNode y = z.getParent().getParent().getLeft();           // uncle
        if (y.getColor() == RBNode.Color.RED) {                   // case 1
          z.getParent().setColor(RBNode.Color.BLACK);
          y.setColor(RBNode.Color.BLACK);
          z.getParent().getParent().setColor(RBNode.Color.RED);
          z = z.getParent().getParent();
        } else {
          if (z == z.getParent().getLeft()) {                   // case 2
            z = z.getParent();
            rightRotate(z);
          }
          z.getParent().setColor(RBNode.Color.BLACK);           // case 3
          z.getParent().getParent().setColor(RBNode.Color.RED);
          leftRotate(z.getParent().getParent());
        }
      }
    }
    root.setColor(RBNode.Color.BLACK);
  }

  private void leftRotate(RBNode x) {
    RBNode y = x.getRight();

    x.setRight(y.getLeft());
    if (y.getLeft() != sentinel) {
      y.getLeft().setParent(x);
    }

    y.setParent(x.getParent());
    if (x.getParent() == sentinel) {
      root = y;
    } else if (x == x.getParent().getLeft()) {
      x.getParent().setLeft(y);
    } else {
      x.getParent().setRight(y);
    }

    y.setLeft(x);
    x.setParent(y);
  }

  private void rightRotate(RBNode y) {
    RBNode x = y.getLeft();

    y.setLeft(x.getRight());
    if (x.getRight() != sentinel) {
      x.getRight().setParent(y);
    }

    x.setParent(y.getParent());
    if (y.getParent() == sentinel) {
      root = x;
    } else if (y == y.getParent().getRight()) {
      y.getParent().setRight(x);
    } else {
      y.getParent().setLeft(x);
    }

    x.setRight(y);
    y.setParent(x);
  }

  private void transplant(RBNode u, RBNode v){
    if(u.getParent() == sentinel){
      root = v;
    }else if(u == u.getParent().getLeft()){
      u.getParent().setLeft(v);
    }else{
      u.getParent().setRight(v);
    }
    if(v != sentinel){
      v.setParent(u.getParent());
    }
  }

  private void delete(RBNode z){
    if(z.getLeft() == sentinel){
      transplant(z, z.getRight());
    }else if(z.getRight() == sentinel){
      transplant(z, z.getLeft());
    }else{
      RBNode y = min(z.getRight());
      if(y != z.getRight()){
        transplant(y, y.getRight());
        y.setRight(z.getRight());
        y.getRight().setParent(y);
      }
      transplant(z, y);
      y.setLeft(z.getLeft());
      y.getLeft().setParent(y);
      y.setColor(z.getColor());
    }
  }

  public void delete(int key) {
    RBNode z = searchTree(root, key);
    if (z == sentinel) {
      System.out.println(key + " not found");
      return;
    }
    delete(z);
  }

  public int height() {
    return height(root);
  }

  private int height(RBNode x) {
    if (x == sentinel) {
      return -1;
    }
    return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
  }

  public boolean isEmpty() {
    return root == sentinel;
  }

  public boolean isNil(RBNode x) {
    return x == sentinel;
  }

  public RBNode min() {
    return min(root);
  }

  public RBNode max() {
    return max(root);
  }
}