package com.example.ateeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
@RestController
public class Login
{
        @GetMapping("/login/{id}/{name}/{pass}")
        public void login(@PathVariable("id") String id,@PathVariable("name") String name,@PathVariable("pass") String pass) throws SQLException{

            Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
            Statement stmt = db.createStatement( );
            System.out.println(name);
            UserData d = new UserData();
            d.setID("12");
            d.setName("hello");
            d.setPassword("12347");
            d.setUniid("k163847");
            String sqlQuery = "insert into Users (name,password,uniid) values ('"+name+"','"+pass+"','"+id+"');\n";
//            ResultSet rs = stmt.executeQuery(sqlQuery);
//            rs.next();
//            String first_name = rs.getString("uniid");
            //userRepository.save(d);
            stmt.executeQuery(sqlQuery);
            //return first_name;

        }

    private UserRepository userRepository;

    @GetMapping("/login/{name}")
    public String getAllEmployees(@PathVariable("name") String name) throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        System.out.println(name);
        UserData d = new UserData();
        d.setID("12");
        d.setName("hello");
        d.setPassword("12347");
        d.setUniid("k163847");
        String sqlQuery = "select * from Users";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        rs.next();
        String first_name = rs.getString("uniid");
        //userRepository.save(d);
        return first_name;

    }
}
