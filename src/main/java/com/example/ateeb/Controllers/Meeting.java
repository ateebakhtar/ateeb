package com.example.ateeb.Controllers;

import com.example.ateeb.Models.AttendeesModel;
import com.example.ateeb.Models.MeetingModel;
import com.example.ateeb.Models.ReminderModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

@RestController
public class Meeting
{
    @GetMapping("/meeting/{course}/{name}/{id}/{venue}/{time}/{date}")
    public String addmeeting(@PathVariable("course") String course,@PathVariable("name") String name,@PathVariable("id") String id,@PathVariable("venue") String venue,@PathVariable("time") String time,@PathVariable("date") String date) throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        String sqlQuery = "insert into meetinglist (course,name,uniid,venue,time ,date) values ('"+course+"','"+name+"','"+id+"','"+venue+"','"+time+"','"+date+"');\n";
        stmt.execute(sqlQuery);
        return "ok";
    }

    @GetMapping("/meetinglist")
    public ArrayList<MeetingModel> getmeeting() throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        String sqlQuery = "select * from meetinglist;";

        ArrayList<MeetingModel> rem = new ArrayList<MeetingModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String course= rs.getString("course");
            String uniid= rs.getString("uniid");
            String venue= rs.getString("venue");
            String time= rs.getString("time");
            String date= rs.getString("date");
            int idd = Integer.parseInt(id);
            MeetingModel temp = new MeetingModel(idd,course,name,uniid,time,date,venue);
            rem.add(temp);
        }
        return rem;
    }
    @GetMapping("/joinmeeting/{id}/{name}/{uniid}")
    public String joinmeeting(@PathVariable("id") String id,@PathVariable("name") String name,@PathVariable("uniid") String uniid)  throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        int idd = Integer.parseInt(id);
        String sqlQuery = "insert into meetingattendee (name ,student_id,meetingid) values ('"+name+"','"+uniid+"',"+idd+");\n";
        stmt.execute(sqlQuery);
        return "ok";
    }
    @GetMapping("/getattendees/{id}")
    public ArrayList<AttendeesModel> getattendees(@PathVariable("id") String idq) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        int x = Integer.parseInt(idq);

        String sqlQuery = "select * from meetingattendee where ID = "+x+";";



        ArrayList<AttendeesModel> rem = new ArrayList<AttendeesModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String uniid= rs.getString("student_id");
            String meetingid= rs.getString("meetingid");
            int idd = Integer.parseInt(id);
            int imd = Integer.parseInt(meetingid);
            AttendeesModel temp = new AttendeesModel(idd,imd,name,uniid);
            rem.add(temp);
        }
        return rem;
    }
}