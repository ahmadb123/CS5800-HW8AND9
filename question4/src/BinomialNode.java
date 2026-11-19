public class BinomialNode {
  private int key;
  private int degree;
  private BinomialNode parent;
  private BinomialNode child;
  private BinomialNode sibling;

  public BinomialNode(int key) {
    this.key = key;
    this.degree = 0;
  }

  public int getKey() { return key; }
  public void setKey(int key) { this.key = key; }

  public int getDegree() { return degree; }
  public void setDegree(int degree) { this.degree = degree; }

  public BinomialNode getParent() { return parent; }
  public void setParent(BinomialNode parent) { this.parent = parent; }

  public BinomialNode getChild() { return child; }
  public void setChild(BinomialNode child) { this.child = child; }

  public BinomialNode getSibling() { return sibling; }
  public void setSibling(BinomialNode sibling) { this.sibling = sibling; }
}