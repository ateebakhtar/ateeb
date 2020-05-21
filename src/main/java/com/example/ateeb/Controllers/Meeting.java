package com.example.ateeb.Controllers;

import com.example.ateeb.Models.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

@RestController
public class Meeting
{
    @GetMapping("/meeting/{course}/{name}/{id}/{venue}/{time}/{date}/{capacity}/{topic}/{description}")
    public String addmeeting(@PathVariable("course") String course,@PathVariable("name") String name,@PathVariable("id") String id,@PathVariable("venue") String venue,@PathVariable("time") String time,@PathVariable("date") String date,@PathVariable("capacity") String capacity,@PathVariable("topic") String topic,@PathVariable("description") String description) throws SQLException,FirebaseMessagingException,IOException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        String sqlQuery = "insert into meetinglist (course,name,uniid,venue,time ,date,capacity,topic,description) values ('"+course+"','"+name+"','"+id+"','"+venue+"','"+time+"','"+date+"','"+capacity+"','"+topic+"','"+description+"');\n";
        stmt.execute(sqlQuery);
        sendnotification(topic);
        return "ok MEETING";
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
            String capacity = rs.getString("capacity");
            String topic= rs.getString("topic");
            String description = rs.getString("description");
            int idd = Integer.parseInt(id);
            MeetingModel temp = new MeetingModel(idd,course,name,uniid,time,date,venue,capacity,topic,description);
            rem.add(temp);
        }
        return rem;
    }

    @GetMapping("/meetinglist/{uniid}")
    public ArrayList<MeetingModel> getmymeeting(@PathVariable("uniid")String uniid) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        String sqlQuery = "select * from meetinglist where uniid = '"+uniid+"';";

        ArrayList<MeetingModel> rem = new ArrayList<MeetingModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String course= rs.getString("course");
            String uniidd= rs.getString("uniid");
            String venue= rs.getString("venue");
            String time= rs.getString("time");
            String date= rs.getString("date");
            String capacity = rs.getString("capacity");
            String topic= rs.getString("topic");
            String description = rs.getString("description");
            int idd = Integer.parseInt(id);
            MeetingModel temp = new MeetingModel(idd,course,name,uniidd,time,date,venue,capacity,topic,description);
            rem.add(temp);
        }
        return rem;
    }
    @GetMapping("/joinedmeeting/{uniid}")
    public ArrayList<MeetingModel> joinedmeeting(@PathVariable("uniid")String uniid) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        String sqlQuery = "select * from meetinglist as x,meetingattendee as y where y.student_id = '"+uniid+"' and x.ID = y.meetingid;";

        ArrayList<MeetingModel> rem = new ArrayList<MeetingModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String course= rs.getString("course");
            String uniidd= rs.getString("uniid");
            String venue= rs.getString("venue");
            String time= rs.getString("time");
            String date= rs.getString("date");
            String capacity = rs.getString("capacity");
            String topic= rs.getString("topic");
            String description = rs.getString("description");
            int idd = Integer.parseInt(id);
            MeetingModel temp = new MeetingModel(idd,course,name,uniidd,time,date,venue,capacity,topic,description);
            rem.add(temp);
        }
        return rem;
    }

    @GetMapping("/joinmeeting/{id}/{name}/{uniid}")
    public String joinmeeting(@PathVariable("id") String id,@PathVariable("name") String name,@PathVariable("uniid") String uniid)  throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        Statement stmt1 = db.createStatement( );
        Statement stmt2 = db.createStatement( );
        int idd = Integer.parseInt(id);
        String sqlQuery = "insert into meetingattendee (name ,student_id,meetingid) values ('"+name+"','"+uniid+"',"+idd+");\n";
        stmt.execute(sqlQuery);
        String sqlQuery1 = "select capacity from meetinglist where id = "+idd+";\n";

        ResultSet rs = stmt1.executeQuery(sqlQuery1);
        rs.next();
        String count = rs.getString("capacity");

        int capacity = Integer.parseInt(count);
        capacity--;
        sqlQuery = "update meetinglist " +
                "SET capacity = '"+capacity+"'"+
                "where ID = "+idd+";";

        stmt2.execute(sqlQuery);
        return "ok";
    }

    public final static String AUTH_KEY_FCM = "AAAAG0KgAEE:APA91bE1fNjUe72HBuhfsE-daal19VJeudG8QZeJMP93nW1ql-uYCyjen3p_C1B-rskwJgtEnBM1r8fr8uW18rtPJLlLYTlP9U2aJCtZVVbDaggynQuY36GrV-DO8E3g8C8bADTEtkyA";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public final static String DEVICE_ID = "dPSN1EJgjW0:APA91bE8dsmSLbfziQDHWpvbbJ0TiKilj1_tS0VwAu_Y9qu5ksr174JrHt9ti9i5nC5JUkcdfyjyPQSrMNnY3w6NTDsrg1FheN4Mpozf53eH9cvLi-uwEk_hLkY9RI0v6c2BmctG3NMR";


    public void sendnotification(String id) throws FirebaseMessagingException, IOException
    {
        String DeviceIdKey = DEVICE_ID;
        String authKey = AUTH_KEY_FCM;
        String FMCurl = API_URL_FCM;

        try {
            URL url = new URL(FMCurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + authKey);
            conn.setRequestProperty("Content-Type", "application/json");

            System.out.println(DeviceIdKey);



            JSONObject data = new JSONObject();
            data.put("to", DeviceIdKey.trim());
            JSONObject info = new JSONObject();

            info.put("title", "New meeting scheduled"); // Notification title
            info.put("body", id); // Notification body
            data.put("notification", info);

            System.out.println(data.toString());
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data.toString());
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    @GetMapping("/getattendees/{id}")
    public ArrayList<AttendeesModel> getattendees(@PathVariable("id") String idq) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        int x = Integer.parseInt(idq);
        String sqlQuery = "select * from meetingattendee where meetingid = '"+x+"';";

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
