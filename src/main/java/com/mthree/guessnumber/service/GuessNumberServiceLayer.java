/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.service;

import com.mthree.guessnumber.models.RandomFourDigitNumber;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public interface GuessNumberServiceLayer {
    Integer createRandomFourDigitNumber();
    List<RandomFourDigitNumber> getNumbers();
}
