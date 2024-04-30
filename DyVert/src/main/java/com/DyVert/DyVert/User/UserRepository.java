package com.DyVert.DyVert.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;



    // Method to fetch all users
    
    public List<User> findAllUsers() {
        String query = "SELECT accountID, accountType, password FROM users";  // Make sure these column names exist in the 'users' table
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(User.class));
    }
    
    @Transactional
    public void deleteUser(String accountID) {

        int count = 0;
        String query = "SELECT count(*) FROM bucket WHERE accountID = :accountID";
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("accountID", accountID);
        count = jdbcTemplate.queryForObject(query, parameter, Integer.class);
        if (count > 0) {
            String bSql = "DELETE FROM bucket WHERE accountID = :accountID";
            jdbcTemplate.update(bSql, parameter);
        }
        int count3 = 0;
        String query3 = "SELECT count(*) FROM seen WHERE accountID = :accountID";
        count3 = jdbcTemplate.queryForObject(query3, parameter, Integer.class);
        if (count3 > 0) {
            String dSql = "DELETE FROM seen WHERE accountID = :accountID";
            jdbcTemplate.update(dSql, parameter);
        }
        int count4 = 0;
        String query4 = "SELECT count(*) FROM seen s JOIN card_data c ON s.contentID = c.cardid WHERE c.userID = :accountID";
        count4 = jdbcTemplate.queryForObject(query4, parameter, Integer.class);
        if (count4 > 0) {
            String eSql = "DELETE s FROM seen s JOIN card_data c ON s.contentID = c.cardid WHERE c.userID = :accountID";
            jdbcTemplate.update(eSql, parameter);
        }
        int count2 = 0;
        String query2 = "SELECT count(*) FROM card_data WHERE userID = :accountID";
        count2 = jdbcTemplate.queryForObject(query2, parameter, Integer.class);
        if (count2 > 0) {
            String cSql = "DELETE FROM card_data WHERE userID = :accountID";
            jdbcTemplate.update(cSql, parameter);
        }
       
        String sql = "DELETE FROM users WHERE accountID = :accountID";
        jdbcTemplate.update(sql, parameter);
    }
}
