/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.data;

import com.mthree.guessnumber.models.Game;
import com.mthree.guessnumber.models.RandomFourDigitNumber;
import com.mthree.guessnumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chuck
 */

@Repository
@Profile("database")
public class GuessNumberDatabaseDao implements GuessNumberDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuessNumberDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer createRandomFourDigitNumber() {
        RandomFourDigitNumber number;
        Game game;
        
        while (true) {
            number = new RandomFourDigitNumber();
            List<Integer> numbers = new ArrayList<>();
            for (RandomFourDigitNumber num : getNumbers()) {
                numbers.add(num.getNumber());
            }
            if (!numbers.contains(number.getNumber())) {
                addRandomFourDigitNumber(number);
                game = new Game(number);
                addGame(game);
                break;
            }
            System.out.println("in createRandomFourDigitNumber()");
        }
        
        return game.getGameID();
    }
    
    private RandomFourDigitNumber addRandomFourDigitNumber (
            RandomFourDigitNumber number) {
        final String sql = "INSERT INTO randomFourDigitNumber(randomNumber) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, number.getNumber());
            return statement;

        }, keyHolder);

        number.setID(keyHolder.getKey().intValue());

        return number;
    }
    
    private Game addGame (Game game) {
        final String sql = "INSERT INTO game(isActive, gameCount, numberID) VALUES(?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setBoolean(1, game.isActive());
            statement.setInt(2, game.getNumberOfGames());
            statement.setInt(3, game.retrieveHiddenNumber().getID());
            return statement;

        }, keyHolder);

        game.setID(keyHolder.getKey().intValue());

        return game;
    }
    
    @Override
    public List<RandomFourDigitNumber> getNumbers() {
        final String sql = "SELECT numberID, randomNumber FROM randomFourDigitNumber;";
        return jdbcTemplate.query(sql, new RandomNumberMapper());
    }

    @Override
    public Round guessNumber(Integer number, int gameID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Game> getGames() {
        final String sql = "SELECT gameID, isActive, numberID FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }
    
    @Override
    public Integer retrieveHiddenNumber(int gameID) {
        final String sql = "SELECT gameID, isActive, numberID FROM game WHERE gameID = ?;";
        Game game = jdbcTemplate.queryForObject(sql, new GameMapper(), gameID);
        /// Working on this
        RandomFourDigitNumber number = jdbcTemplate.queryForObject("SELECT * FROM randomFourDigitNumber WHERE numberID = ?;", new RandomNumberMapper(), gameID);
        return game.retrieveHiddenNumber().getNumber();
    }

    @Override
    public Integer getNumber(int gameID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game getGame(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Round> getRounds(int gameID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static final class RandomNumberMapper 
            implements RowMapper<RandomFourDigitNumber> {
        @Override
        public RandomFourDigitNumber mapRow(ResultSet rs, int i) throws SQLException {
            RandomFourDigitNumber number = new RandomFourDigitNumber(-1);
            number.setID(rs.getInt("numberID"));
            number.setNumber(rs.getInt("randomNumber"));
            return number;
        }
    }
    
    private static final class GameMapper 
            implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game game = new Game();
            game.setID(rs.getInt("gameID"));
            game.setIsActive(rs.getBoolean("isActive"));
            game.setNumber(rs.getInt("numberID"));
            
            return game;
        }
    }
    
}
