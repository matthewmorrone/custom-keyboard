// package com.custom.keyboard;

class TrieNode {

    TrieNode[] arr;
    String value;
    boolean isEnd;
    
    public TrieNode() {
        this.arr = new TrieNode[52];
    }
    public TrieNode(String value) {
        this.arr = new TrieNode[52];
        this.value = value;
    }
 
}
 
public class Trie {
    private TrieNode root;
 
    public Trie() {
        root = new TrieNode();
    }
 
    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int index = -1;
            if (c >= 65 && c <= 91)  index = c - 'A';
            if (c >= 97 && c <= 122) index = c - 'a';
            if (p.arr[index] == null) {
                TrieNode temp = new TrieNode(word.substring(0, i+1));
                p.arr[index] = temp;
                p = temp;
            }
            else {
                p = p.arr[index];
            }
        }
        p.isEnd = true;
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode p = searchNode(word);
        if (p == null) {
            return false;
        }
        else {
            if (p.isEnd) {
                return true;
            }
        }
        return false;
    }
 
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode p = searchNode(prefix);
        if (p == null) {
            return false;
        }
        else {
            return true;
        }
    }
 
    public TrieNode searchNode(String s) {
        TrieNode p = root;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = -1;
            if (c >= 65 && c <=  91) index = c - 'A';
            if (c >= 97 && c <= 122) index = c - 'a';
            if (index < 0 || index > p.arr.length) return null;
            
            if (p.arr[index] != null) {
                p = p.arr[index];
            }
            else {
                return null;
            }
        }
        if (p == root) {
            return null;
        }
        return p;
    }
}