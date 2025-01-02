package org.mine.mappers;

import org.mine.models.User;
import org.springframework.jdbc.core.RowMapper;

import org.mine.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs,int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastName(rs.getString("lastName"));
         user.setAvatarUrl(rs.getString("avatarUrl"));
        // Set other fields as necessary
        return user;
    }
}

