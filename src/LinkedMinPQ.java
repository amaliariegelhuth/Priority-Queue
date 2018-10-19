/*
Amalia Riegelhuth
amaliariegelhuth

CSCI 1102 Computer Science 2

*/
import java.util.NoSuchElementException;

public class LinkedMinPQ<T extends Comparable<T>> implements MinPQ<T> {

  ////////////////////////
  // Instance variables //
  ////////////////////////
  Node top;
  int n;


  /////////////////////////////////
  // Node inner class definition //
  /////////////////////////////////

  private class Node {
    T info;
    Node rchild;
    Node lchild;
    Node parent;

  }

  //////////////////////////////////////////////////////
  //     Methods you *might* want to implement.       //
  // There could be others, but these are some ideas. //
  //////////////////////////////////////////////////////

  public void swap(Node n1, Node n2) {
    T temp = n1.info;
    n1.info = n2.info;
    n2.info = temp;
  }

  // 1. A method that figures out where to insert a new node, i.e.,
  //    a method that tells you which rights and lefts to follow in
  //    in order to find the next available place for a node.
  public String findPath(int i) {
    String s = "";
    if (i > 1) {
      if (i % 2 == 0) {
        s = findPath(i / 2) + "L";
      } else {
        s = findPath(i / 2) + "R";
      }
    }
    return s;
  }


  //    a method that will sink new info down to a node where it
  //    is smaller than its children but bigger than its parent
  public void sink(Node d) {
    while (d.lchild != null) {
      if (d.rchild != null && d.lchild.info.compareTo(d.rchild.info) > 0) {
        if (d.info.compareTo(d.rchild.info) < 0) {
          break;
        } else {
          swap(d, d.rchild);
          d = d.rchild;
        }
      } else {
        if (d.info.compareTo(d.lchild.info) < 0) {
          break;
        } else {
          swap(d, d.lchild);
          d = d.lchild;
        }
      }
    }

  }

  //    a method that will swim info up from the bottom to a node
  //    where it's bigger than its parent and smaller than its children
  public void swim(Node d) {
    while (d.parent != null && d.parent.info.compareTo(d.info) > 0) {
      swap(d, d.parent);
      d = d.parent;
    }
  }


  // Freebie helper method you can call in toString().
  // You do not need to use this method, but you can if you like.
  // This method will return a String listing the info in all the nodes
  // in a level in a binary tree, from left to right.
  // It is recursive: it calls itself in order to get the next level.
  String printThisLevel(Node root ,int level) {
    StringBuilder s = new StringBuilder();
    if (root == null) {
      return s.toString();
    }
    if (level == 1) {
      s.append( root.info.toString());
    } else if (level > 1) {
      s.append( printThisLevel(root.lchild, level-1));
      s.append( printThisLevel(root.rchild, level-1));
    }
    return s.toString();
  }


  /////////////////////////////////////////////////////////
  // Methods you must implement from the PQ interface //
  /////////////////////////////////////////////////////////

  // Remove and return the min (top) element
  public T delMin() {
    if (isEmpty()) {
      throw new NoSuchElementException("Stack underflow");
    }
    T min = top.info;
    Node k = top;
    String path = findPath(n);
    for (int i = 0; i < path.length(); i++) {
      String s = path.substring(i, i + 1);
      if (s.equals("L")) {
        k = k.lchild;
      } else {
        k = k.rchild;
      }
    }
    top.info = k.info;
    if (n == 1) {
      top = null;
    } else {
      if (path.substring(path.length() - 1).equals("L")) {
        k.parent.lchild = null;
      } else {
        k.parent.rchild = null;
      }
      sink(top);
    }
    n--;
    return min;

  }

  // Insert a new element.
  public void insert(T key) {
    n++;
    Node n1 = new Node();
    if (n == 1) {
      top = n1;
      n1.info = key;
      swim(n1);
    } else {
      n1 = top;
      String path = findPath(n);
      for (int i = 0; i < path.length() - 1; i++) {
        String s = path.substring(i, i + 1);
        if (s.equals("L")) {
          n1 = n1.lchild;
        } else {
          n1 = n1.rchild;
        }
      }
      if (path.substring(path.length() - 1).equals("L")) {
        Node n2 = new Node();
        n2.parent = n1;
        n1.lchild = n2;
        n2.info = key;
        swim(n2);
      } else {
        Node n2 = new Node();
        n2.parent = n1;
        n1.rchild = n2;
        n2.info = key;
        swim(n2);
      }
    }
  }

  // Return true if the PQ is empty
  public boolean isEmpty() {
    return n == 0;
  }

  // Return the size of the PQ.
  public int size() {
    return n;
  }

  // Return a string showing the PQ in level order, i.e.,
  // containing the info at each node, L to R, from top level to bottom.
  public String toString() {
    int height = (findPath(n).length()) + 1;
    String s = "";
    for (int i = 1; i <= height; i++) {
      s = s + printThisLevel(top, i) + "\n";
    }
    return s;
  }


  ////////////////////////////////////////////////////////////
  // Main method you must write to test out your code above //
  ////////////////////////////////////////////////////////////

  public static void main (String[] args) {
    LinkedMinPQ<String> lm = new LinkedMinPQ<String>();
    lm.insert("B");
    lm.insert("R");
    lm.insert("A");
    lm.insert("L");
    lm.insert("Z");
    lm.insert("F");
    lm.insert("K");
    lm.insert("E");
    System.out.println(lm);
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());
    System.out.println(lm.delMin());

  }

}
