package com.example.ateeb.Controllers;


import com.example.ateeb.Models.CourseModel;
import com.example.ateeb.Models.NotificationData;
import com.example.ateeb.Models.NotificationRequestModel;
import com.example.ateeb.Models.ReminderModel;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.lang.reflect.Type;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import com.google.firebase.messaging.FirebaseMessagingException;


import java.sql.*;
import java.util.ArrayList;

@RestController
public class Reminder
{
    @GetMapping("/reminder/{usrid}/{type}/{date}/{time}/{coursename}/{prio}")
    public String reminder(@PathVariable("usrid") String id, @PathVariable("type") String type, @PathVariable("date") String date, @PathVariable("time") String time, @PathVariable("coursename") String CN,@PathVariable String prio) throws SQLException {

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        int i = Integer.parseInt(id);
        String sqlQuery = "insert into reminder (date,time,type,course,usrid,status,priority) values ('"+date+"','"+time+"','"+type+"','"+CN+"',"+i+",'incomplete','"+prio+"');";
        //stmt.executeQuery(sqlQuery);
        System.out.println(i);
        stmt.execute(sqlQuery);
        return "ok";
    }
    @GetMapping("/deletereminder/{id}")
    public String deletereminder(@PathVariable("id") String id) throws SQLException
    {
        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        int id1 = Integer.parseInt(id);

        String sqlQuery = "DELETE from reminder where ID = "+id1+";";

        stmt.execute(sqlQuery);

        return "ok";
    }


    public final static String AUTH_KEY_FCM = "AAAAG0KgAEE:APA91bE1fNjUe72HBuhfsE-daal19VJeudG8QZeJMP93nW1ql-uYCyjen3p_C1B-rskwJgtEnBM1r8fr8uW18rtPJLlLYTlP9U2aJCtZVVbDaggynQuY36GrV-DO8E3g8C8bADTEtkyA";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public final static String DEVICE_ID = "dPSN1EJgjW0:APA91bE8dsmSLbfziQDHWpvbbJ0TiKilj1_tS0VwAu_Y9qu5ksr174JrHt9ti9i5nC5JUkcdfyjyPQSrMNnY3w6NTDsrg1FheN4Mpozf53eH9cvLi-uwEk_hLkY9RI0v6c2BmctG3NMR";

    @GetMapping("/sendd")
    public String sendd() throws FirebaseMessagingException , IOException
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

            info.put("title", "Reminder Suggestion"); // Notification title
            info.put("body", "You have a Reminder Suggestion for Apllied Physics on 12 - 10"); // Notification body
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

       return "repose";
    }
    @GetMapping("/sendnotification")
    public String sendnotificaiton() throws FirebaseMessagingException, IOException {
        //String registrationToken = " AIzaSyAu2WSOiAaUltrwS6OYg_Su2-GdrmCv1Yg ";
        System.out.println("Welcome to Developine");


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(
                "https://fcm.googleapis.com/fcm/send");

        // we already created this model class.
        // we will convert this model class to json object using google gson library.

        NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
        NotificationData notificationData = new NotificationData();

        notificationData.setDetail("this is firebase push notification from java client (server)");
        notificationData.setTitle("Hello Firebase Push Notification");
        notificationRequestModel.setData(notificationData);
        notificationRequestModel.setTo("117081899073");


        Gson gson = new Gson();
        Type type = new TypeToken<NotificationRequestModel>() {
        }.getType();

        String json = gson.toJson(notificationRequestModel, type);

        StringEntity input = new StringEntity(json);
        input.setContentType("application/json");

        // server key of your firebase project goes here in header field.
        // You can get it from firebase console.

        postRequest.addHeader("Authorization", "key=AAAAG0KgAEE:APA91bE1fNjUe72HBuhfsE-daal19VJeudG8QZeJMP93nW1ql-uYCyjen3p_C1B-rskwJgtEnBM1r8fr8uW18rtPJLlLYTlP9U2aJCtZVVbDaggynQuY36GrV-DO8E3g8C8bADTEtkyA");
        postRequest.setEntity(input);

        System.out.println("reques:" + json);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        } else if (response.getStatusLine().getStatusCode() == 200) {

            System.out.println("response:" + EntityUtils.toString(response.getEntity()));

        }
        return "response";
    }

    @GetMapping("/reminder/{id}")
    public ArrayList<ReminderModel> getreminder(@PathVariable("id") String id) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        int idtt = Integer.parseInt(id);
        String sqlQuery = "select * from reminder where usrid = "+idtt+";";

        ArrayList<ReminderModel> rem = new ArrayList<ReminderModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String course = rs.getString("course");
            String date = rs.getString("date");
            String type = rs.getString("type");
            String idd = rs.getString("ID");
            ReminderModel temp = new ReminderModel(course,date,type,idd);
            rem.add(temp);
        }
        return rem;
    }

    @GetMapping("/setcourses/{name}/{section}/{usrid}")
    public String setcourse(@PathVariable("name") String name, @PathVariable("section") String section, @PathVariable("usrid") String usrid) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        int idtt = Integer.parseInt(usrid);
        String sqlQuery = "insert into courselist(name, section, usrid) values('"+name+"','"+section+"',"+idtt+")";
        stmt.execute(sqlQuery);
        return "ok";
    }
    @GetMapping("/getcourses/{id}")
    public ArrayList<CourseModel> getcourses(@PathVariable("id") String id) throws SQLException{

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );
        int idtt = Integer.parseInt(id);
        String sqlQuery = "select * from courselist where usrid = "+idtt+";";

        ArrayList<CourseModel> rem = new ArrayList<CourseModel>();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        //rs.next();
        while(rs.next())
        {
            String name = rs.getString("name");
            String section = rs.getString("section");
            String usrid = rs.getString("usrid");

            CourseModel temp = new CourseModel(name,section,usrid);
            rem.add(temp);
        }
        return rem;
    }
}
