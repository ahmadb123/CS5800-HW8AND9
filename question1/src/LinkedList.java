public class LinkedList{
  private Node head;

  public LinkedList(){
    this.head = null;
  }


  // find word
  public Node findNode(String key){
    if(head == null || key == null) {
      return null;
    }
    String k = head.getKey();
    if(k.equals(key)){
      return head;
    }
    Node current = head.getNextNode();
    while(current != null){
      if(current.getKey().equals(key)){
        return current;
      }else{
        current = current.getNextNode();
      }
    }
    return null;
  }

  // add new word

  public void addNode(String key, int value){
    head = new Node(key, value, head);
  }

  // remove word

  public void removeWord(String key){
    Node prev = null;
    Node current = head;
    while(current != null && !key.equals(current.getKey())){
      prev = current;
      current = current.getNextNode();
    }
    if(current == null){
      System.out.println("Absent");
      return;
    }
    if(prev == null){
      head = current.getNextNode();
    }else{
      prev.setNextNode(current.getNextNode());
    }
  }

  public int getListLength(){
    int length = 0;
    Node current = head;
    while(current != null){
      length ++;
      current = current.getNextNode();
    }
    return length;
  }

  public Node getHeader(){
    return head;
  }
}