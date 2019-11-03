package com.custom.keyboard;

import java.util.*;

class TrieNode implements Comparable<TrieNode> {
    char c;
    TrieNode parent;
    HashMap<Character,TrieNode> children = new HashMap<Character,TrieNode>();
    boolean isLeaf;
    int value;
 
    public TrieNode() {}
    public TrieNode(char c) {
        this.c = c;
    }
    
    public int compareTo(TrieNode that) {
        return this.value - that.value;
    }
}
   
public class Trie {
    private TrieNode root;
    ArrayList<String> words; 
    TrieNode prefixRoot;
    String curPrefix;
 
    public Trie() {
        root = new TrieNode();
        words  = new ArrayList<String>();
    }
 
    // Inserts a word into the trie.
    public void insert(String word, int value) {
        HashMap<Character,TrieNode> children = root.children;
        
        TrieNode crntparent;
        
        crntparent = root;
        
        // cur children parent = root
        int i;
        char c;
        for (i = 0; i < word.length(); i++) {
            c = word.charAt(i);
 
            TrieNode t;
            
            if (children.containsKey(c)) { 
                t = children.get(c);
            }
            else {
                t = new TrieNode(c);
                t.value = value;
                t.parent = crntparent;
                children.put(c, t);
            }
         
            children = t.children;
            crntparent = t;
 
            // set leaf node
            if (i == word.length()-1) {
                t.isLeaf = true;
            }
        }
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);
        return (t != null && t.isLeaf);
    }
 
    // Returns if there is any word in the trie that starts with the given prefix.
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }
 
    public TrieNode searchNode(String str) {
        Map<Character,TrieNode> children = root.children; 
        TrieNode t = null;
        int i;
        char c;
        for(i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            }
            else {
                return null;
            }
        }
 
        prefixRoot = t;
        curPrefix = str;
        words.clear();
        return t;
    }
    
    ArrayList<String> getWords() {
        return words;
    }
    
    void wordsFinderTraversal(TrieNode node, int offset) {
        // System.out.print(node, offset);
        
        if (node.isLeaf == true) {
            // System.out.println("leaf node found");
          
            TrieNode altair;
            altair = node;
          
            Stack<String> hstack = new Stack<String>(); 
          
            while(altair != prefixRoot) {
                // System.out.println(altair.c);
                hstack.push(Character.toString(altair.c));
                altair = altair.parent;
            }
          
            String wrd = curPrefix;
          
            while(hstack.empty() == false) {
                wrd = wrd + hstack.pop();
            }
          
            // System.out.println(wrd);
            words.add(wrd);
          
        }
     
        Set<Character> kset = node.children.keySet();
        // System.out.println(node.c); println(node.isLeaf);println(kset);
        Iterator itr = kset.iterator();
        ArrayList<Character> aloc = new ArrayList<Character>();
     
        
        while(itr.hasNext()) {
            Character ch = (Character)itr.next();  
            aloc.add(ch);
            // System.out.println(ch);
        } 
     
        // here you can play with the order of the children
        int i;
        for(i = 0; i < aloc.size(); i++) {
            wordsFinderTraversal(node.children.get(aloc.get(i)), offset + 2);
        }
    }


    void displayFoundWords() {
        System.out.println("_______________");
        for(int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i));
        } 
        System.out.println("________________");
    }
}

/*
Trie prefixTree;

prefixTree = new Trie();  
  
prefixTree.insert("GOING");
prefixTree.insert("GONG");
prefixTree.insert("PAKISTAN");
prefixTree.insert("SHANGHAI");
prefixTree.insert("GONDAL");
prefixTree.insert("GODAY");
prefixTree.insert("GODZILLA");
  
if (prefixTree.startsWith("GO") == true) {
    TrieNode tn = prefixTree.searchNode("GO");
    prefixTree.wordsFinderTraversal(tn, 0);
    prefixTree.displayFoundWords(); 
}
  
if (prefixTree.startsWith("GOD") == true) {
    TrieNode tn = prefixTree.searchNode("GOD");
    prefixTree.wordsFinderTraversal(tn, 0);
    prefixTree.displayFoundWords(); 
}
*/
