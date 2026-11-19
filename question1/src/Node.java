public class Node{
  private final String key;
  private int value;
  private Node nextNode;

  public Node(String key, int value, Node node){
    this.key = key;
    this.value = value;
    this.nextNode = node;
  }

  public String getKey(){
    return key;
  }

  public int getValue(){
    return value;
  }

  public void setValue(int val){
    this.value = val;
  }

  public int updateCount(int value){
    return this.value += value;
  }

  public Node getNextNode(){
    return nextNode;
  }

  public void setNextNode(Node next){
    this.nextNode = next;
  }
}