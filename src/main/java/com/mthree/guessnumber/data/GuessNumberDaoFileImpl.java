/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.data;

import com.mthree.guessnumber.models.RandomFourDigitNumber;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chuck
 */

@Repository
public class GuessNumberDaoFileImpl implements GuessNumberDao {
    private Map<Integer, RandomFourDigitNumber> randomFourDigitNumberList;
        
    public GuessNumberDaoFileImpl() {
        randomFourDigitNumberList = new HashMap();
    }
    
    @Override
    public Integer createRandomFourDigitNumber() {
        RandomFourDigitNumber number;
        while (true) {
            number = new RandomFourDigitNumber();
            if (!randomFourDigitNumberList.containsKey(number.getNumber())) {
                randomFourDigitNumberList.put(number.getNumber(), number);
                break;
            }
        }
        return number.getNumber();
    }   

    @Override
    public List<RandomFourDigitNumber> getNumbers() {
        List<RandomFourDigitNumber> list = new ArrayList();
        list.addAll(randomFourDigitNumberList.values());
        return list;
    } 
}
