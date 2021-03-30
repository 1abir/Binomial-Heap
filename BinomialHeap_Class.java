
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Math.pow;

class Node {
    int key;
    int order;
    Node parent;
    Node child;
    Node sibling;
    Node left;

    public Node() {
    }

    public Node(int key) {
        this.key = key;
        this.order = 0;
        this.parent = null;
        this.child = null;
        this.sibling = null;
        this.left=null;
    }

    public Node(int key, int order) {
        this.key = key;
        this.order = order;
        this.parent = null;
        this.child = null;
        this.sibling = null;
        this.left=null;
    }
}

public class BinomialHeap_Class {

    private Node head;

    public BinomialHeap_Class(Node head) {
        this.head = head;
    }

    private static void linkBinomialTrees(Node x, Node y) {

        y.parent = x;
        y.sibling = x.child;
        x.child = y;


        x.order += 1;
    }

    BinomialHeap_Class() {
        head = null;
    }


    Node Find_Min() {

        int min = 999999;
        Node currPtr = head;
        Node minPtr = null;

        while (currPtr != null) {
            if (currPtr.key < min) {
                min = currPtr.key;
                minPtr = currPtr;
            }
            currPtr = currPtr.sibling;
        }

        return minPtr;

    }


    void Insert(int data) {
        if(getHead()==null)
        {
            head=new Node(data);
            return;
        }
        Node node = new Node(data);
        BinomialHeap_Class h = new BinomialHeap_Class(node);
        Union(h);
    }


    void printHeap() {
        Node current = head;
        while (current != null) {
            System.out.println();
            System.out.println("B" + current.order);
            System.out.println("There are " + (int) pow(2, current.order) + " nodes in this tree");
            System.out.println("The level order traversal is");
            Queue<Node> q = new LinkedList<>();
            q.add(current);
            while (!q.isEmpty()) {
                Node p = q.peek();
                q.remove();
                System.out.println(p.key + " ");

                if (p.child != null) {
                    Node temp = p.child;
                    while (temp != null) {
                        q.add(temp);
                        temp = temp.sibling;
                    }
                }
            }
            current = current.sibling;
            System.out.println();
        }
    }

    Node getHead() {
        return head;
    }


    void setHead(Node head) {
        this.head = head;
    }


    void Union(BinomialHeap_Class h1) {
        if(getHead()==null)
        {
            head=h1.head;
            return;
        }
        Node curr1 = getHead();
        Node curr2 = h1.getHead();
        Node curr3 = null;
        Node temp = null;

        if (curr1.order <= curr2.order) {
            curr3 = curr1;
            curr1 = curr1.sibling;
        } else {
            curr3 = curr2;
            curr2 = curr2.sibling;
        }

        temp = curr3;


        while (curr1 != null && curr2 != null) {
            if (curr1.order <= curr2.order) {
                curr3.sibling = curr1;
                curr1.left=curr3;
                curr1 = curr1.sibling;
            } else {
                curr3.sibling = curr2;
                curr2.left=curr3;
                curr2 = curr2.sibling;
            }

            curr3 = curr3.sibling;
        }

        if (curr1 != null) {

            while (curr1 != null) {
                curr3.sibling = curr1;
                curr1.left=curr3;
                curr1 = curr1.sibling;
                curr3 = curr3.sibling;
            }
        }

        if (curr2 != null) {

            while (curr2 != null) {
                curr3.sibling = curr2;
                curr2.left=curr3;
                curr2 = curr2.sibling;
                curr3 = curr3.sibling;
            }
        }


        curr3 = temp;
        Node prev = null;
        Node next = curr3.sibling;

        while (next != null) {
            if ((curr3.order != next.order) || (next.sibling != null && curr3.order == next.sibling.order)) {
                prev = curr3;
                curr3 = next;
            } else {

                if (curr3.key <= next.key) {
                    curr3.sibling = next.sibling;
                    if(next.sibling!=null)
                        next.sibling.left=curr3;
                    linkBinomialTrees(curr3, next);
                } else {
                    if (prev == null) {
                        temp = next;
                    } else {
                        prev.sibling = next;
                        next.left=prev;
                    }

                    linkBinomialTrees(next, curr3);
                    curr3 = next;
                }
            }

            next = curr3.sibling;
        }

        setHead(temp);
    }


    Node Extract_Min() {
        Node curr = head;
        Node prevMin = null;
        Node minPtr = null;
        Node prevPtr = null;
        int min = 999999;

        while (curr != null) {
            if (curr.key <= min) {
                min = curr.key;
                prevMin = prevPtr;
                minPtr = curr;
            }
            prevPtr = curr;
            curr = curr.sibling;
        }


        if (minPtr == null) throw new AssertionError();

        if (prevMin != null && minPtr.sibling != null) {
            prevMin.sibling = minPtr.sibling;
        } else if (prevMin != null) {
            prevMin.sibling = null;
        }
        Node childPtr = minPtr.child;
        while (childPtr != null) {
            childPtr.parent = null;
            childPtr = childPtr.sibling;
        }


        Stack<Node> s = new Stack<>();
        childPtr = minPtr.child;
        while (childPtr != null) {
            s.push(childPtr);
            childPtr = childPtr.sibling;
        }

        curr = s.peek();
        Node temp = s.pop();
        while (!s.isEmpty()) {
            curr.sibling = s.pop();
            curr = curr.sibling;
        }

        curr.sibling = null;

        BinomialHeap_Class h = new BinomialHeap_Class(temp);

        Union(h);

        return minPtr;
    }

    public static void main(String[] args) {
        int n, m, l, i;
        BinomialHeap_Class bh = new BinomialHeap_Class();
        Node parent;
        Node H = new Node(0);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------------------------");
            System.out.println("1)Insert Element in the heap");
            System.out.println("2)Find Minimum key Node ");
            System.out.println("3)Extract Minimum key Node ");
            System.out.println("4)Print Heap");
            System.out.println("5)Exit");
            System.out.println("----------------------------");
            System.out.println("Enter Your Choice: ");
            l = scanner.nextInt();
            switch (l) {
                case 1:
                    System.out.println("Enter the element to be inserted: ");
                    m=scanner.nextInt();
                    if(bh.getHead()==null)
                        bh.setHead(new Node(m));
                    else
                        bh.Insert(m);
                    break;
                case 2:
                    parent = bh.Find_Min();
                    if (parent != null)
                        System.out.println("The Node  with minimum key: " + parent.key);
                    else
                        System.out.println("Heap is empty");
                    break;
                case 3:
                    parent = bh.Extract_Min();
                    if (parent != null)
                        System.out.println("The Node  with minimum key: " + parent.key);
                    else
                        System.out.println("Heap is empty");
                    break;
                case 4:
                    bh.printHeap();
                    System.out.println();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }
}
