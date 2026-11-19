import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class HashTable{
  private final int a;
  private final int b;
  private final int m;
  private final static long P = 1_000_000_007L;
  private static final Random RAND = new Random(42);
  private static final int BIT = 31;
  private LinkedList[] lists;

  public HashTable(int m){
    this.a = RAND.nextInt((int) P - 1) + 1;
    this.b = RAND.nextInt((int) P);
    this.m = m;
    this.lists = new LinkedList[m];
    for(int i = 0; i < m; i++){
      this.lists[i] = new LinkedList();
    }
  }

  // turn word into k
  public long convertStringToK(String key){
    long k;
    long z = 0;
    for(int i = 0; i < key.length(); i++) {
      char c = key.charAt(i);
      int ascii = c;
      // compute z left to right, last z = k
     z = ((z * BIT + ascii) % P);
    }
    k = z;
    return k;
  }


  // turn k into slot in m array.
  public int slot(String key) {
    // algorithm with a,b p mod m.
    long k = convertStringToK(key);
    int slot = (int) ((a * k + b) % P) % m;
    return slot;
  }

  public void listAllLists(String filename){
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
      for(int i = 0; i < m; i++){
        Node n = lists[i].getHeader();
        while(n != null){
            writer.write(n.getKey() + " ");
            writer.write((String.valueOf(n.getValue())) + " ");
            writer.write("\n");
          n = n.getNextNode();
        }
      }
    } catch (IOException e) {
      System.err.println("Failed to write out to file: " + e.getMessage());
    }
  }

  public Node findNode(String key){
    if(key == null) return null;
    int s = slot(key);
    return lists[s].findNode(key);
  }

  public void put(String key, int value){
    if(key == null || value < 0){
      System.out.println("Must enter value");
      return;
    }
    int s = slot(key);
    Node n = lists[s].findNode(key);
    if(n != null) {
      n.setValue(value);
    }
    else{
      lists[s].addNode(key, value);
    }
  }

  public void increase(String key){
   int s = slot(key);
   Node n = lists[s].findNode(key);
   if(n != null){
     n.updateCount(1);
   }else{
     lists[s].addNode(key, 1);
   }
  }

  public void delete(String key){
    int s = slot(key);
    lists[s].removeWord(key);
  }

  public int[] getListLengths(){
    int[] lengthOfEach = new int[m];
    for(int i = 0; i < m; i++){
      lengthOfEach[i] = lists[i].getListLength();
    }
    return lengthOfEach;
  }

  public int getDistinctN(){
    int sum = 0;
    int[] lengthM = getListLengths();
    for (int j : lengthM) {
      sum += j;
    }
    return sum;
  }

  public double computeAlpha(){
    double alpha;
    int n = getDistinctN();
    alpha = (double) n / (double) m;
    return alpha;
  }

  public double computeVariance(){
    double variance = 0.0;
    double alpha = computeAlpha();
    int[] lengthArrays = getListLengths();
    for(int i : lengthArrays){
      variance += Math.pow(i - alpha, 2);
    }
    variance = variance / m;
    return variance;
  }


  public int[] histogram(){
    int[] lengthM = getListLengths();
    int max = 0;
    for(int j : lengthM){
      max = Math.max(max, j);
    }
    int[] results = new int[max + 1];
    for(int i : lengthM){
      results[i]++;
    }
    return results;
  }

  public int[] topTenPercent(){
    int[] histogramData = histogram();
    int[] saveTopTen = new int[m/10];
    int position = 0;
    for(int i = histogramData.length - 1; i >= 0; i--){
      for(int j = 0; j < histogramData[i]; j++){
        if(position < m/10){
          saveTopTen[position] = i;
          position++;
        }
      }
    }
    return saveTopTen;
  }
}