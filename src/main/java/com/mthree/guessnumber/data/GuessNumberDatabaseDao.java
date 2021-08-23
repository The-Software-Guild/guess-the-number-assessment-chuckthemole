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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    private final static String GAME_OVER = "e:4:p:0";

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
                addGame(game, number);
                break;
            }
        }
        
        return game.getGameID();
    }
    
    private RandomFourDigitNumber addRandomFourDigitNumber(
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
    
    private Game addGame(Game game, RandomFourDigitNumber number) {
        final String sql = "INSERT INTO game(isActive, numberID) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setBoolean(1, game.isActive());
            statement.setInt(2, number.getID());
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
        Game game = getGame(gameID);
        Round round = new Round(number, game);
        
        addRound(round, gameID);
        
        if (round.getResult().equals(GAME_OVER)) {
            final String sql = "UPDATE game SET isActive = false WHERE gameID = ?;";
            jdbcTemplate.update(sql, gameID);
            game.gameOver();
        }
        
        return round;    
    }
    
    private Round addRound(Round round, int gameID) {
        final String sql = "INSERT INTO round(guess, time, result, gameID) VALUES(?, ?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGuess());
            statement.setDate(2, Date.valueOf(round.getTime()));
            statement.setString(3, round.getResult());
            statement.setInt(4, gameID);
            return statement;

        }, keyHolder);

        round.setID(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Game> getGames() {
        final String sql = "SELECT * FROM game;";
        List<Game> games = jdbcTemplate.query(sql, new GameMapper());
        
        for (Game game : games) {
            int numberID = jdbcTemplate.queryForObject("SELECT numberID FROM game WHERE gameID = ?", Integer.class, game.getGameID());
            final String sqlNum = 
                "SELECT * FROM randomFourDigitNumber WHERE numberID = ?;";
            RandomFourDigitNumber number = jdbcTemplate.queryForObject(sqlNum, new RandomNumberMapper(), numberID);
            game.setNumber(number);
        }
        
        return games;
    }
    
    @Override
    public Integer retrieveHiddenNumber(int gameID) {
        Game game = getGame(gameID);
        return game.retrieveHiddenNumber().getNumber();
    }

    @Override
    public Integer getNumber(int gameID) {
        final String sqlGame = 
                "SELECT gameID, isActive, numberID FROM game WHERE gameID = ?;";
        Game game = 
                jdbcTemplate.queryForObject(sqlGame, new GameMapper(), gameID);
        final String sqlNum = 
                "SELECT * FROM randomFourDigitNumber WHERE numberID = ?;";
        RandomFourDigitNumber number = 
                jdbcTemplate.queryForObject(sqlNum, new RandomNumberMapper(), 
                        game.retrieveHiddenNumber().getNumber());
        game.setNumber(number.getNumber());
        return game.getNumber().getNumber();
    }

    @Override
    public Game getGame(int id) {
        final String sqlGame = 
                "SELECT * FROM game WHERE gameID = ?;";
        Game game = 
                jdbcTemplate.queryForObject(sqlGame, new GameMapper(), id);
        int numberID = jdbcTemplate.queryForObject("SELECT numberID FROM game WHERE gameID = ?", Integer.class, id);
        final String sqlNum = 
                "SELECT * FROM randomFourDigitNumber WHERE numberID = ?;";
        RandomFourDigitNumber number = jdbcTemplate.queryForObject(sqlNum, new RandomNumberMapper(), numberID);
        game.setNumber(number);
        return game;
    }

    @Override
    public List<Round> getRounds(int gameID) {
        final String sqlRound = 
                "SELECT * FROM round WHERE gameID = ?;";
        List<Round> list = jdbcTemplate.query(sqlRound, new RoundMapper(), gameID);
        Game game = getGame(gameID);
        
        for (Round round : list) {
            round.setGame(game);
        }
        
        return list;
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
            // game.setNumber(rs.getInt("numberID"));
            //final String sql = 
            //     "SELECT * FROM randomFourDigitNumber WHERE numberID = ?;";
            //game.setNumber(jdbcTemplate.queryForObject(sql, new RandomNumberMapper(), rs.getInt("numberID")));
            //System.out.println(game.retrieveHiddenNumber().getNumber());
            // game.setNumber(rs.getObject("CONSTRAINT", RandomFourDigitNumber.class));
            
            return game;
        }
    }
    
    private static final class RoundMapper 
            implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setGuess(rs.getInt("guess"));
            round.setResult(rs.getString("result"));
            Date date = rs.getDate("time");
            round.setTime(date.toLocalDate());
                      
            return round;
        }
    }
    
}
