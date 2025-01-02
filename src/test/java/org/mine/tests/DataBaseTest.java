package org.mine.tests;

import org.mine.mappers.UserRowMapper;
import org.mine.models.User;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class DataBaseTest extends BaseTest{
    @Test
    public void verify2ndIdUser(){

        List<User> users = executeQuery("SELECT * FROM users", new UserRowMapper());
        // Assertions to verify the results
        assertNotNull(users);
        assertFalse(users.isEmpty());
        logger.info("users.isEmpty() "+users.isEmpty());
        logger.info("users.get(0) -0th row ="+users.get(0));
        logger.info("users.get(0) -0th rows email id ="+users.get(0).getEmail());
        logger.info("users.get(0) -0th rows first name ="+users.get(0).getFirstname());

        //Retrieve all data
        logger.info("table =");
        for(User singleUser:users){
            logger.info(singleUser.getId());
            logger.info(singleUser.getEmail());
            logger.info(singleUser.getFirstname());
            logger.info(singleUser.getLastName());
            logger.info(singleUser.getAvatarUrl());
        }

        // Additional assertions as needed...
    }
}
