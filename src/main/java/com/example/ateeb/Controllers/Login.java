package com.example.ateeb.Controllers;

import com.example.ateeb.UserRepository;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;

@RestController
public class Login
{
        @GetMapping("/login/{id}/{name}/{pass}/{sem}")
        public String login(@PathVariable("id") String id, @PathVariable("name") String name, @PathVariable("pass") String pass, @PathVariable String sem) throws SQLException{
            System.out.println("excuting login");
            Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
            Statement stmt = db.createStatement( );
            Statement stmt1 = db.createStatement( );
            String sqlquery = "select * from users where uniid = '"+id+"';";
            ResultSet rs = stmt.executeQuery(sqlquery);
            if(!rs.next())
            {
                String sqlQuery = "insert into Users (name,password,uniid,semester) values ('"+name+"','"+pass+"','"+id+"','"+sem+"');\n";
                stmt1.execute(sqlQuery);
                return "ok";
            }
            else
            {
                return "notok";
            }
        }

        @GetMapping("/editlogin/{name}/{pass}/{id}")
        public String editacc(@PathVariable("name") String name,@PathVariable("pass") String pass,@PathVariable("id") String id) throws SQLException
        {
            Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
            Statement stmt = db.createStatement( );

            int id1 = Integer.parseInt(id);

            String sqlQuery = "update Users " +
                    "SET name = '"+name+"' , password = '"+pass+"'" +
                    "where ID = "+id1+";";

            stmt.execute(sqlQuery);

            return "ok";
        }

        @GetMapping("/getconnection")
        public String getconnection()
        {
            return "ok";
        }
    private UserRepository userRepository;

        @GetMapping("/getsemester/{id}")
        public String getsemester(@PathVariable("id") String id) throws SQLException {
            Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
            Statement stmt = db.createStatement( );
            int idd = Integer.parseInt(id);
            String sqlQuery = "select * from users where ID = "+idd+";";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.next();
            String sem = "1";
            if(!rs.next())
            {
                return "3";
            }
            if(rs.getString("semester")!=null)
            {
                 sem = rs.getString("semester");
            }
            else
            {
                sem = "3";
            }

            return sem;
        }

    @GetMapping("/login/{name}/{password}")
    public String getlogin(@PathVariable("name") String name,@PathVariable("password") String pass) throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        String sqlQuery = "select * from users where uniid = '"+name+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        if(rs.next())
        {
            String password = rs.getString("password");
            String id = rs.getString("uniid");
            String idd = rs.getString("id");
            if(pass.equals(password))
            {
                return idd;
            }
            else
            {
                return "notok";
            }
        }
        return "notok";
    }
    @GetMapping("/forgetpassword/{id}")
    public String forgerpass(@PathVariable("id") String id) throws IOException, MessagingException, SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        String sqlQuery = "select * from users where uniid = '"+id+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        rs.next();
        String pass = rs.getString("password");


        Properties props = System.getProperties();
        props.put("mail.smtps.host","smtp.gmail.com");
        props.put("mail.smtps.auth","true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mail@tovare.com"));;
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(id+"@nu.edu.pk", false));
        msg.setSubject("Forgot Password ");
        msg.setText("The Password you have set is: "+pass);
        msg.setHeader("Email Verfication", "Tov Are's program");
        msg.setSentDate(new Date());
        SMTPTransport t =
                (SMTPTransport)session.getTransport("smtps");
        t.connect("smtp.gmail.com", "personalorganiserproject@gmail.com", "lotus@123");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();


        return "ok";
    }
}
