package com.p1.mokka1.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeRowMapper implements RowMapper<Type> {
    @Override
    public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
        com.p1.mokka1.model.Type type= new Type();
        type.setId(rs.getInt("id"));
        type.setName(rs.getString("name"));
        type.setDescription(rs.getString("description"));
        type.setPrice(rs.getFloat("price"));
        type.setImage(rs.getString("image"));

        return type;
    }


}
