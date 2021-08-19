/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chuck
 */
public class RandomFourDigitNumber {
    private Integer number;
    
    public RandomFourDigitNumber() {
        ArrayList<Integer> listOfDigits = new ArrayList<Integer>();
        
        // Add most significant digit making sure it is not 0
        for (int i = 1; i < 10; i++) {
            listOfDigits.add(i);
        }      
        Collections.shuffle(listOfDigits);
        number = 0;
        number += (listOfDigits.get(0) * 1000);
        listOfDigits.remove(0);
        
        // Add the remaining digits
        listOfDigits.add(0);
        Collections.shuffle(listOfDigits);
        number += listOfDigits.get(0);
        listOfDigits.remove(0);
        number += (listOfDigits.get(0) * 10);
        listOfDigits.remove(0);
        number += (listOfDigits.get(0) * 100);  
    }   
    
    public Integer getNumber() {
        return number;
    }
}
