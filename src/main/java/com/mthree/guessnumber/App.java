package com.mthree.guessnumber;

import com.mthree.guessnumber.controller.GuessNumberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String[] args) {
            SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        GuessNumberController controller = 
                ctx.getBean("controller", GuessNumberController.class);
        controller.run();
    }
}
