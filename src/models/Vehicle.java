package models;

/**
 * Created by ghoshri on 7/13/2017.
 */

import database.ConnectionFactory;
import com.sun.org.apache.regexp.internal.RE;
import models.users.Admin;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Vehicle {
    Connection connection = ConnectionFactory.getConnection();


    public void add_vehicle(String model_no, String name, String brand, double price, int units, double tax, double profit) {

        PreparedStatement stmt=null;
        try{
            String sql;
            stmt = connection.prepareStatement( "insert into vehicles(model_no, name, brand, price, units, updated_at, tax, profit) values(?,?,?,?,?,?,?,?)");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Calendar now = Calendar.getInstance();
            String time = df.format(now.getTime());
            stmt.setString(1, model_no);
            stmt.setString(2,name);
            stmt.setString(3,brand);
            stmt.setDouble(4,price);
            stmt.setInt(5,units);
            stmt.setString(6,time);
            stmt.setDouble(7,tax);
            stmt.setDouble(8,profit);
            int cnt = stmt.executeUpdate();
            if(cnt>0)
            {
                System.out.println("Successfully inserted...");
                Admin a = new Admin();
                a.item();
            }
            else
            {
                System.out.println("Error inserting...");
            }


            //STEP 6: Clean-up environment
            stmt.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();

        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
        }//end try
        System.out.println("Done...");
    }//end main

    public void remove_vehicle(String model_no) throws SQLException, IOException, InterruptedException {

        String sql = "delete from vehicles where model_no = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,model_no);

        int rows_affected = stmt.executeUpdate();
        if (rows_affected > 0) {
            System.out.println("Successfully Removed the vehicle having model_no: "+model_no);
            Admin a = new Admin();
            a.item();
        } else {
            System.out.println("Not Removed");
        }

    }
    public void add_vehicle_stock(String model_no, int n){
        try {
            String isql = "select units from vehicles where model_no = ?";
            PreparedStatement stmt = connection.prepareStatement(isql);
            stmt.setString(1, model_no);
            ResultSet rs = stmt.executeQuery();
            int t = 0;
            if(rs.next()) {
                t=rs.getInt(1) +n;
            }
            String sql = "update vehicles set units=? where model_no = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, t);
            stmt.setString(2, model_no);
            int rows_affected = stmt.executeUpdate();
            if(rows_affected > 0) {
                System.out.println("Successfully Updated");
            } else {
                System.out.println("Not Updated");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public void sell_vehicle_stock(String model_no,int n){
        try {
            String isql = "select units from vehicles where model_no = ?";
            PreparedStatement stmt = connection.prepareStatement(isql);
            stmt.setString(1, model_no);
            ResultSet rs = stmt.executeQuery();
            int t = 0;
            if(rs.next()) {
                t=rs.getInt(1) -n;
            }
            String sql = "update vehicles set units=? where model_no = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, t);
            stmt.setString(2, model_no);
            int rows_affected = stmt.executeUpdate();
            if(rows_affected > 0) {
                System.out.println("Successfully Updated");
            } else {
                System.out.println("Not Updated");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}