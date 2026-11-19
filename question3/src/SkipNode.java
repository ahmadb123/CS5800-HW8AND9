public class SkipNode{
  private final int key;
  private final SkipNode[] nextNode;

  public SkipNode(int key, int height){
    this.key = key;
    this.nextNode = new SkipNode[height];
  }

  public SkipNode getNext(int level){
    return nextNode[level];
  }

  public void setNext(int level, SkipNode node){
    this.nextNode[level] = node;
  }

  public int getHeight(){
    return nextNode.length;
  }

  public int getKey(){
    return key;
  }
}