/**
 * Laboratory work No3 Context Free Grammar to Chomsky Normal Form.
 * Student: Vizant Beatrice
 * Group FAF - 182
 * Variant 9
 *
 * The initial grammar is written inside the cfg.txt file.
 * The output is in console which will show the process of converting the grammar on every step.
 * Transition like:( C -> ϵ | AB ) will be written in .txt file as: C 0 AB
 *
 */
package com.beatrix;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            ChomskyNormalForm chomsky = new ChomskyNormalForm(new File(
                                "D:\\playground\\cfg_to_cnf\\resources\\cfg.txt"));
            chomsky.to_chomsky();
        }
        catch (FileNotFoundException e){
            System.out.print(e.toString());
        }
    }
}
