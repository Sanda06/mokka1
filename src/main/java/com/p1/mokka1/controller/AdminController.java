package com.p1.mokka1.controller;

import com.p1.mokka1.model.User;
import com.p1.mokka1.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/users")
    public ModelAndView getUsers() {

        ModelAndView userview = new ModelAndView("users.html");
        String userQuery = "SELECT * FROM `mokka`.`user`;";
        List<User> userList = jdbcTemplate.query(userQuery, new UserRowMapper());

        userview.addObject("users", userList);
        return userview;

    }

    @GetMapping("/users/{id}")
    public ModelAndView getUserById(@PathVariable Integer id) {
        ModelAndView userView = new ModelAndView("userProfile.html");
        String query = "SELECT * FROM `mokka`.`user` WHERE iduser =" + id;
        User currentUser = jdbcTemplate.queryForObject(query, new UserRowMapper());
        userView.addObject("user", currentUser);
        return userView;
    }

}
