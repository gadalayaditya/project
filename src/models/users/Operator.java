package models.users;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.ConnectionFactory;
import models.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import java.sql.*;

public class Operator extends User {
    Connection conn = ConnectionFactory.getConnection();

    Scanner sc = new Scanner(System.in);
    String str,str2;
    int n;
    String model;

    public void add_stock()
    {
        System.out.println("Enter the type(Vehicle/Sparepart) : ");
        str = sc.nextLine();


        switch(str.toLowerCase()) {
            case "vehicle" : System.out.println("Enter the Vehicle Model Number : ");
                model = sc.nextLine();
                System.out.println("Enter the no. of units to be added: ");
                n = sc.nextInt();
                Vehicle v = new Vehicle();
                v.add_vehicle_stock(model, n);
                System.out.println("Vehicle Added");
                break;
            case "sparepart"   : out.println("Enter the SparePart Model Number : ");
            out.println("<form id='myform' action='SparePart'> <input id = 'model' name = 'model' type='text' <br>");// html here
               // model = sc.nextLine();
                HttpSession ssn1=request.getSession();
                sssn1.setAttribute("mode","add_sparePartsStocks");
                out.println("Enter the no. of units : ");
              out.println("<input id = 'n' name = 'n' type='text' <br>");
                out.println("<input type ='submit' >");
              out.println("<form>");
                //  n = sc.nextInt();
               // SparePart sp = new SparePart();
                //sp.add_sparePartsStocks(model, n);
                //out.println("Spare Part Added");
                break;
            default:
                System.out.println("You have entered wrong option");
        }
    }
    public void sell() {
        System.out.println("Enter the type(Vehicle/Sparepart) : ");
        str = sc.nextLine();
        switch(str.toLowerCase()) {
            case "vehicle" : System.out.println("Enter the Vehicle Model Number : ");
                model = sc.nextLine();
                System.out.println("Enter the no. of units to be sold : ");
                n = sc.nextInt();
                Vehicle v = new Vehicle();
                v.sell_vehicle_stock(model, n);
                System.out.println("Vehicle(s) Sold");
                break;
            case "sparepart"   : out.println("Enter the SparePart Model Number : ");//html here
                out.println("<form id='myform' action='SparePart'> <input id = 'model' name = 'model' type='text' <br>");// html here
               // model = sc.nextLine();
                HttpSession ssn1=request.getSession();
                sssn1.setAttribute("mode","sell_spareParts");
                out.println("Enter the no. of units : ");
                // System.out.println("Enter the no. of units : ");
                out.println("<input id = 'n' name = 'n' type='text' <br>");
                out.println("<input type ='submit' >");
                out.println("<form>");
                /*


                n = sc.nextInt();
                SparePart sp = new SparePart();
                sp.sell_spareParts(model, n);
                System.out.println("Spare Part(s) Sold");*/
                break;
            default:
                System.out.println("You have entered wrong option");
        }
    }

    public void display() throws SQLException {
        Statement stmt = conn.createStatement();
        System.out.println("Enter the type(Vehicle/Sparepart) : ");
        str2 = sc.nextLine();
        ResultSet rs1;
        switch(str2.toLowerCase()) {
            case "vehicle" :
                rs1 = stmt.executeQuery("select * from vehicles");
                while (rs1.next()) {
                    System.out.println("Vehicle ID : " + rs1.getInt(1) + ",  Name : " + rs1.getString(3) + ",  Units : " + rs1.getInt(6));
                }
                break;
            case "sparepart" :
                rs1 = stmt.executeQuery("select * from spareparts");
                while(rs1.next()) {
                    System.out.println("SparePart ID : " + rs1.getInt(1) + ",  Name : " + rs1.getString(3) + ",  Units : " + rs1.getInt(6));
                }
                break;
        }

    }

    public void logout() throws SQLException {
        String sql = "insert into logs(username, time, description) values(?, ?, ?)";
        PreparedStatement st = conn.prepareStatement(sql);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String dd = dateFormat.format(cal.getTime());
        User u = new User();
        String user_name = u.getUsername();

        st.setString(1, user_name);
        st.setString(2, dd);
        st.setString(3, "logout");

        int i = st.executeUpdate();

        if(i == 1) {
            System.out.println("Logged out successfully!");
            u.logoutUpdate();
        }
        else
            System.out.println("Try Again");
    }
}