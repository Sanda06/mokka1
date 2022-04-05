package com.p1.mokka1.controller;

import com.p1.mokka1.model.Type;
import com.p1.mokka1.model.TypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TypeController {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/dashboard")
    public ModelAndView getDashboard() {
        ModelAndView dashboardView = new ModelAndView("dashboard.html");
        String typeQuery = "SELECT * FROM `mokka`.`type`";
        List<Type> typeList = jdbcTemplate.query(typeQuery, new TypeRowMapper());
        dashboardView.addObject("type", typeList);
        return dashboardView;
    }

    @GetMapping("/typeDet/{id}")
    public ModelAndView getTypeByID(@PathVariable Integer id) {
        ModelAndView typeDetView = new ModelAndView("typeDet.html");
        String query = "SELECT * FROM `mokka`.`type` WHERE id =" + id;
        Type dbTypeDetElem = jdbcTemplate.queryForObject(query, new TypeRowMapper());

        typeDetView.addObject("typeElem", dbTypeDetElem);


        return typeDetView;


    }
}
