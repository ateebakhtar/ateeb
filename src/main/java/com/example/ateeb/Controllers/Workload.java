package com.example.ateeb.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
public class Workload
{
    ResultSet rs;
    ArrayList<com.example.ateeb.Models.Workload> wk;
    com.example.ateeb.Models.Workload ob;
    @GetMapping("/editworkload/{id}")
    public String edit(@PathVariable("id") String id) throws SQLException
    {
        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement( );

        int id1 = Integer.parseInt(id);

        String sqlQuery = "update reminder " +
                "SET status = 'complete' " +
                "where ID = "+id1+";";

        stmt.execute(sqlQuery);

        return "ok";
    }



    @GetMapping("/workload/{id}")
    public ArrayList<com.example.ateeb.Models.Workload> reminder(@PathVariable("id") String id12) throws SQLException {

        String[] qw = new String[7];

        Connection db = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=MunazamDB","Munazam","lotus123");
        Statement stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY );
        int idtt = Integer.parseInt(id12);
        String sqlQuery = "select * from reminder ;";
        rs = stmt.executeQuery(sqlQuery);
        if(!rs.next())
        {
            return null;
        }
        ob = new com.example.ateeb.Models.Workload();
        wk = new ArrayList<com.example.ateeb.Models.Workload>();


        LocalDate current = LocalDate.now();

        int sizer = 0;
        while (rs.next())
        {
            sizer++;
        }
        rs.first();
        String[] x = new String[15];
        int[] prio = new int[15];
        long[] currentdate = new long[sizer];
        for(int i=0;i<6;i++)
        {
            x[i] = ""+current.getDayOfWeek();
            current = current.plusDays(1);
        }
        current = LocalDate.now();

        double[] ratios = new double[sizer];
        int counter = 0;
        long noOfDaysBetween = 0;
        rs.getFetchSize();
        while(rs.next())
        {
            if(rs.getString("status").equals("incomplete"))
            {
                String date = rs.getString("date");
                int p= Integer.parseInt(rs.getString("priority")) ;

                String[] DM = date.split(" ", 2);
                int day = Integer.parseInt(DM[0]);
                int month = Integer.parseInt(DM[1]);

                LocalDate inputDate = LocalDate.of(2019,month,day);
                noOfDaysBetween = ChronoUnit.DAYS.between(current , inputDate);

                currentdate[counter] = noOfDaysBetween;
                if(currentdate[counter]>0)
                {
                    ratios[counter] = currentdate[counter] / p;
                }
                else
                {
                    ratios[counter] = -1;
                }
                counter++;

            }
        }
        rs.first();
        for(int i=0;i<7;i++) {
            rs.first();
            x[i] = "" + current.getDayOfWeek();
            current = current.plusDays(1);
            int trackeddays = 0;
            double[] temp = ratios;
            for (int j = 0; j < sizer; j++)
            {
                if (currentdate[j] <= 4 && currentdate[j] > 0)
                {
                    rs.absolute(j + 1);
                    com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();
                    ob.insert("" + (i), rs.getString("type"), rs.getString("course"), "" + currentdate[j],""+rs.getString("ID"));
                    //input in the list
                    wk.add(ob);
                    trackeddays++;
                }
            }

            if(trackeddays < 3 && !x[i].equals("SATURDAY") && !x[i].equals("SUNDAY"))// current day is not saturday or sunday
            {
                switch (trackeddays)
                {
                    case 0:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<3;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,3,i);
                        break;
                        //max 3 ratios
                    }
                    case 1:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<2;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,2,i);
                        break;
                        //max 2 ratios
                    }
                    case 2:
                    {
                        //setArray(ratios,currentdate,1,i);
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<1;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();
                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        break;
                    }
                }
            }
            else if(trackeddays < 6 && x[i].equals("SATURDAY") || x[i].equals("SUNDAY"))
            {
                switch (trackeddays)
                {
                    case 0:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<6;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,6,i);
                        break;
                        //max 6 ratios
                    }
                    case 1:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<5;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,5,i);
                        break;
                        //max 5 ratios
                    }
                    case 2:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<4;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,4,i);
                        break;
                        //max 4 ratios
                    }
                    case 3:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<3;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);
                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();

                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,3,i);
                        break;
                        //max 3 ratios
                    }
                    case 4:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<2;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }
                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);

                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();
                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));

                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,2,i);
                        break;
                        //max 2 ratios
                    }
                    case 5:
                    {
                        double max = 1200;
                        int maxindex = 0;
                        for(int n=0;n<1;n++)
                        {
                            for(int m=0;m<temp.length;m++)
                            {
                                if(temp[m]<max && currentdate[m] > 0 && temp[m]>0)
                                {
                                    max = temp[m];
                                    maxindex = m;
                                }
                            }

                            temp[maxindex] = -1;
                            rs.absolute(maxindex+1);

                            com.example.ateeb.Models.Workload ob = new com.example.ateeb.Models.Workload();
                            ob.insert(""+i,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));

                            maxindex = 0;
                            max = 1200;
                            wk.add(ob);
                        }
                        //setArray(ratios,currentdate,1,i);
                        break;
                        //max 1 ratios
                    }

                }
            }

            trackeddays = 0;
            for(int j=0;j<currentdate.length;j++)
            {
                currentdate[j] = currentdate[j] - 1;
            }
            for(int i1=0;i1<ratios.length;i1++)
            {
                if(currentdate[i1]>0)
                {
                    ratios[i1] =  currentdate[i1] / 5;
                }
                else
                {
                    ratios[i1] = -1;
                }
            }
        }

        return wk;
    }

    public void setArray(double[] temp,long[] currentdate,int x,int y) throws SQLException
    {
        double max = 0;
        int maxindex = 0;
        for(int n=0;n<x;n++)
        {
            for(int m=0;m<temp.length;m++)
            {
                if(temp[m]>max && currentdate[m] > 0)
                {
                    max = temp[m];
                    maxindex = m;
                }
            }
            temp[maxindex] = -1;
            rs.absolute(maxindex+1);

            ob.insert(""+y,rs.getString("type"),rs.getString("course"),""+currentdate[maxindex],""+rs.getString("ID"));
            max = 0;
            wk.add(ob);
        }

    }


}
