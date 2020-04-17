/**
 * Laboratory work No3 Context Free Grammar to Chomsky Normal Form.
 *
 * Here goes the reading of cfg.txt file and breaking it up in parts
 * which will help make manipulations with data inside.
 * Here I create 5 different lists to hold up the "ready to convert data"
 * for the use in the class ChomskyNormalForm. It every list it holds different data
 * such as the left side of transitions, filters the terminals from non-terminals.
 */
package com.beatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ContextFreeGrammar {
    private Scanner in;
    private List<String> left;
    private List<List<String>> right;
    private List<String> terminals;
    private List<String> non_terminals;
    boolean contains_epsilon = false;

    ContextFreeGrammar(File input) throws FileNotFoundException {
        this.in = new Scanner(input);
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.non_terminals = new ArrayList<>();
        this.parse();
        this.merge();
    }

    List<String> epsilon_non_terminals(){
        List<String> epsilon = new ArrayList<>();
        for (int i = 0; i < this.right.size(); i++){
            if (this.right.get(i).contains("0")){ // epsilon is represented as 0
                epsilon.add(this.left.get(i));
            }
        }
        boolean added_new = true;
        while (added_new){
            added_new = false;
            for (int i = 0; i < this.right.size(); i++){
                for (String word: this.right.get(i)){
                    boolean all_epsilon = true;
                    for (char c: word.toCharArray()){
                        if (!epsilon.contains(String.valueOf(c))){
                            all_epsilon = false;
                            break;
                        }
                    }
                    if (all_epsilon && !epsilon.contains(this.left.get(i))){
                        added_new = true;
                        epsilon.add(this.left.get(i));
                        break;
                    }
                }
            }
        }
        return epsilon;
    }

    void add_rule(String left, List<String> right){
        this.left.add(left);
        this.right.add(right);
    }

    void change_rules(String left, List<String> right){
        this.right.get(this.left.indexOf(left)).clear();
        this.right.get(this.left.indexOf(left)).addAll(right);
    }

    void change_left(String old, String new_){
        this.left.set(this.left.indexOf(old), new_);
    }

    String get_free(){
        for (char c = 'A'; c < 'Z'; c++){
            if (!this.non_terminals.contains(String.valueOf(c))){
                return String.valueOf(c);
            }
        }
        return "A";
    }

    private void parse(){
        while (this.in.hasNext()){
            List<String> line = Arrays.asList(in.nextLine().split(" "));
            this.left.add(line.get(0));
            this.right.add(new ArrayList<>());
            this.right.get(this.right.size() - 1).addAll(line.subList(1, line.size()));

            // adding terminals and non_terminals
            for (String s: line){
                for (char c: s.toCharArray()){
                    if (c == '0') {
                        this.contains_epsilon = true; // grammar contains epsilon rules
                    }
                    else if (String.valueOf(c).toUpperCase().equals(String.valueOf(c))){
                        String t = new String(String.valueOf(c));
                        if (!this.non_terminals.contains(t)) {
                            this.non_terminals.add(t); // if letter is big add it to non_terminals list
                        }
                    }
                    else{
                        String t = new String(String.valueOf(c));
                        if (!this.terminals.contains(t)) {
                            this.terminals.add(String.valueOf(c)); // else add it to terminals list
                        }
                    }
                }
            }
        }
    }

    void merge(){
        List<Integer> remove = new ArrayList<>();
        for (int i = 0; i < this.left.size(); i++){
            for (int j = i + 1; j < this.left.size(); j++){
                if (this.left.get(i).equals(this.left.get(j))){
                    this.right.get(i).addAll(this.right.get(j));
                    remove.add(j);
                }
            }
        }
        int k = 0;
        for (int i: remove){
            this.left.remove(i - k);
            this.right.remove(i - k);
            k += 1;
        }
    }

    void print(){
        for (int i = 0; i < this.right.size(); i++){
            System.out.print(this.left.get(i) + " -> ");
            for (String word: this.right.get(i)){
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

    List<String> rules(String t) { return this.right.get(this.left.indexOf(t)); }

    List<String> getTerminals() { return terminals; }

    List<String> getNonterminals() { return non_terminals; }

    List<String> getLeft() { return left; }

    List<List<String>> getRight() { return right; }
}