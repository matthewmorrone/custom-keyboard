package com.custom.keyboard;

import java.util.HashMap;

class TrieNode implements Comparable<TrieNode> {
    char c;
    String word;
    TrieNode parent;
    HashMap<Character,TrieNode> children = new HashMap<>();
    boolean isLeaf;
    long value;

    public TrieNode() {}
    public TrieNode(char c) {
        this.c = c;
    }

    public String getWord() {
        return word;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.word+"("+this.value+")";
    }

    @Override
    public int compareTo(TrieNode that) {
        if (that.word.length() == this.word.length()) {
            return (int)(that.value - this.value);
        }
        return this.word.length() - that.word.length();
    }
}