/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public class RandomFourDigitNumber {
    private Integer number;
    private int numberID;
    private static final int HIDDEN_NUMBER = -1;
    
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
    
    public RandomFourDigitNumber(Integer number) {
        this.number = number;
    }   
    
    public static RandomFourDigitNumber createHiddenNumber() {
        return new RandomFourDigitNumber(HIDDEN_NUMBER);
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public void setID(Integer id) {
        this.numberID = id;
    }
    
    public int getID() {
        return this.numberID;
    }
    
    public String compareNumbers(Integer number) {
        Map<Integer, Integer> hiddenDigitAndPlace = new HashMap(); 
        int numberOfExactMatches = 0;
        int numberOfPartialMatches = 0;
        
        int digit;
        int temp = this.number;
        for (int i = 1; i < 5; i++) {
            digit = temp % 10;
            temp /= 10;     
            hiddenDigitAndPlace.put(digit, i);
        }

        temp = number;
        for (int i = 1; i < 5; i++) {
            digit = temp % 10;
            temp /= 10;     
            
            if (hiddenDigitAndPlace.containsKey(digit)) {
                if (hiddenDigitAndPlace.get(digit) == i) {
                    numberOfExactMatches++;
                } else {
                    numberOfPartialMatches++;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("e:")
                .append(numberOfExactMatches)
                .append(":p:")
                .append(numberOfPartialMatches);

        return sb.toString();
    }
        
}
