/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.data;

import com.mthree.guessnumber.models.Game;
import com.mthree.guessnumber.models.RandomFourDigitNumber;
import com.mthree.guessnumber.models.Round;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public interface GuessNumberDao {
    Integer createRandomFourDigitNumber();
    List<RandomFourDigitNumber> getNumbers();
    Round guessNumber(Integer number, int gameID);
    Map<Integer, Game> getGames();
    Integer retrieveHiddenNumber(int gameID);
    Integer getNumber(int gameID);
    Game getGame(int id);
    List<Round> getRounds(int gameID);
}
