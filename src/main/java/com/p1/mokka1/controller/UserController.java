package com.p1.mokka1.controller;

import com.p1.mokka1.model.LoginForm;
import com.p1.mokka1.model.RegisterForm;
import com.p1.mokka1.model.User;
import com.p1.mokka1.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("register.html");
    }


    @PostMapping(value = "/register-result", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postRegister(RegisterForm data) {
        ModelAndView registerView = new ModelAndView("register.html");
        if (!data.getPassword1().equals(data.getPassword2())) {
            registerView.addObject("errPassword",
                    "The two passwords do not match");
            return registerView;
        }


        String emailQuery = "SELECT COUNT(*)  FROM `mokka`.`user` WHERE email= '" + data.getEmail() + "'";// intoarce un nr
        Integer emailCount = jdbcTemplate.queryForObject(emailQuery, Integer.class);
        if (emailCount > 0) {//sa nu existe nici un user cu acest email
            registerView.addObject("errEmail",
                    "The provided email already exists");
            return registerView;
        }

        String sqlQuery = "INSERT INTO `mokka`.`user`( `first_name`,`last_name`,`email`,`password`,`phone`) VALUES(?,?,?,?,?);";
        int result = jdbcTemplate.update(sqlQuery, data.getFirstName(), data.getLastName(), data.getEmail(), data.getPassword1(), data.getPhoneNumber());

        return registerView;
    }


    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("login.html");
    }


    @PostMapping(value = "/login-result", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postLogin(LoginForm data) {
        ModelAndView loginWiew = new ModelAndView("login.html");
        String userQuery = "SELECT * FROM `mokka`.`user` WHERE email= '" + data.getEmail() + "' AND password = '" + data.getPassword() + "'";
        List<User> userList = jdbcTemplate.query(userQuery, new UserRowMapper());
        if (userList.size() == 0) {
            loginWiew.addObject("errLogin", "Incorect email or password");
            return loginWiew;
        } else {
            return new ModelAndView("redirect:/dashboard");
        }
    }
}
