public class RBNode{
  private int key;
  private Color color;
  private RBNode left;
  private RBNode right;
  private RBNode parent;

  public RBNode(int key, Color color, RBNode left, RBNode right, RBNode parent){
    this.key = key;
    this.color = color;
    this.left = left;
    this.right = right;
    this.parent = parent;
  }


  public int getKey(){
    return key;
  }

  public Color getColor(){
    return color;
  }

  public RBNode getLeft(){
    return left;
  }

  public RBNode getRight(){
    return right;
  }

  public RBNode getParent() {
    return parent;
  }

  public void setColor(Color c){
    this.color = c;
  }

  public void setLeft(RBNode left) {
    this.left = left;
  }

  public void setParent(RBNode parent) {
    this.parent = parent;
  }

  public void setRight(RBNode right) {
    this.right = right;
  }

  enum Color{
    RED, BLACK
  }
}