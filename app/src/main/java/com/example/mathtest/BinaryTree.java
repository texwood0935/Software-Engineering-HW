package com.example.mathtest;

import java.util.ArrayList;

public class BinaryTree
{
    private Node root;
    private int num;
    private ArrayList<Node> opeList = new ArrayList<Node>();

    public BinaryTree(int num){
        this.num = num;
    }
    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num = num;
    }
    public void setTreeNode(Node root){
        this.root = root;
    }
    public String toString(){
        String str = root.toString();
        str = str.substring(1, str.length()-1);
        return str;
    }
    public String CalAndVal(){
        return root.getResult();
    }
    public int getDeep(){
        int i = this.num;
        int deep = 2;
        while(i/2 > 0){
            deep++;
            i /= 2;
        }
        return deep;
    }
    public void createBTree(){
        Node lchild, rchild, lnode, rnode;

        if(num == 1){
            lchild = new Node(String.valueOf(Ran.getNumber(10)), null, null);
            rchild = new Node(String.valueOf(Ran.getNumber(10)), null, null);
            root = new Node(String.valueOf(Ran.getOperator()), lchild, rchild);
        }
        else{
            int num1 = 0;
            int n = getDeep() - 3;
            boolean[] place = Ran.getChildPlace(num);
            root = new Node(String.valueOf(Ran.getOperator()), null, null);
            opeList.add(root);

            for(int i = 0; i < n; i++){
                for(int j = 0; j < (int)Math.pow(2, i); j++, num1++){
                    lchild = new Node(String.valueOf(Ran.getOperator()), null, null);
                    rchild = new Node(String.valueOf(Ran.getOperator()), null, null);
                    opeList.get(j + num1).setChild(lchild, rchild);
                    opeList.add(lchild);
                    opeList.add(rchild);
                }
            }

            for(int i = 0; i < place.length; i++){
                if(place[i]){
                    lnode  = new Node(String.valueOf(Ran.getNumber(10)), null, null);
                    rnode  = new Node(String.valueOf(Ran.getNumber(10)), null, null);
                    if(i%2 == 0){
                        lchild = new Node(String.valueOf(Ran.getOperator()), lnode, rnode);
                        opeList.add(lchild);
                        opeList.get(num1).setLchild(lchild);
                    }
                    else{
                        rchild = new Node(String.valueOf(Ran.getOperator()), lnode, rnode);
                        opeList.add(rchild);
                        opeList.get(num1).setRchild(rchild);
                    }
                }
                else{
                    if(i%2 == 0){
                        lchild = new Node(String.valueOf(Ran.getNumber(10)), null, null);
                        opeList.get(num1).setLchild(lchild);
                    }
                    else{

                        rchild = new Node(String.valueOf(Ran.getNumber(10)), null, null);
                        opeList.get(num1).setRchild(rchild);
                    }
                }
                num1 = num1 + i%2;
            }
        }
    }
}