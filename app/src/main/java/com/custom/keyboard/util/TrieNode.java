package com.custom.keyboard.util;

import java.util.HashMap;

public class TrieNode implements Comparable<TrieNode> {
    public char c;
    public String word;
    public TrieNode parent;
    public HashMap<Character,TrieNode> children = new HashMap<>();
    public boolean isLeaf;
    public long value;

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