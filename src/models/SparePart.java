package models;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.ConnectionFactory;
import models.users.Admin;

/**
 * Created by gadalaya on 7/23/2017.
 */
@WebServlet(name = "Servlet")
public class SparePart extends HttpServlet {
    Connection connection = ConnectionFactory.getConnection();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session= request.getSession(false);
        String s =(String) session.getAttribute("mode");
       try{

           if(s.equals("add_sparepart")) {
               add_sparepart(request,response);
           }else

           if(s.equals("remove_sparepart")) {
               remove_sparepart(request,response);
           }
           else
           if(s.equals("add_sparePartsStocks")) {
               add_sparePartsStocks(request,response);
           }
           else
           if(s.equals("sell_spareParts")) {
               sell_spareParts(request,response);
           }

       }
       catch(Exception e) {
           out.println("Exception");
       }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request, response);
    }
    public void add_sparepart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model_no, name,  brand; double price;int units, vehicle_id; double tax;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        model_no=request.getParameter("model_no");
        name = request.getParameter("name");
        brand = request.getParameter("brand");
        price = Double.parseDouble(request.getParameter("price"));
        units = Integer.parseInt(request.getParameter("units"));
        vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
        tax = Double.parseDouble(request.getParameter("tax"));

        PreparedStatement stmt = null;
        try {
            String sql;
            stmt = connection.prepareStatement("insert into spareparts(model_no, name, brand, price, units,vehicle_id, updated_at, tax) values(?,?,?,?,?,?,?,?)");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Calendar now = Calendar.getInstance();
            String time = df.format(now.getTime());
            stmt.setString(1, model_no);
            stmt.setString(2, name);
            stmt.setString(3, brand);
            stmt.setDouble(4, price);
            stmt.setInt(5, units);
            stmt.setInt(6, vehicle_id);
            stmt.setString(7, time);
            stmt.setDouble(8, tax);

            int cnt = stmt.executeUpdate();
            if (cnt > 0) {
                out.println("Successfully inserted...");
            } else {
                out.println("Error inserting...");
            }


            //STEP 6: Clean-up environment
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
        }//end try
        out.println("Done...");
    }//end main

    public void remove_sparepart(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException, InterruptedException {
        String model_no;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        model_no  = request.getParameter("model_no");
        String sql = "delete from spareparts where model_no = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,model_no);

        int rows_affected = stmt.executeUpdate();
        if (rows_affected > 0) {
            out.println("Successfully Removed the sparepart having model_no: "+model_no);
            Admin a = new Admin();
            a.item();
        } else {
            out.println("Not Removed");
        }

    }

    public void add_sparePartsStocks( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String model; int n;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        model = request.getParameter("model");
        n = Integer.parseInt(request.getParameter("n"));
        try {
            String isql = "select units from spareparts where model_no = ?";
            PreparedStatement stmt = connection.prepareStatement(isql);
            stmt.setString(1, model);
            ResultSet rs = stmt.executeQuery();
            int t = 0;
            if(rs.next()) {
                t=rs.getInt(1) +n;
            }
            String sql = "update spareparts set units=? where model_no = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, t);
            stmt.setString(2, model);
            int rows_affected = stmt.executeUpdate();
            if(rows_affected > 0) {
                out.println("Successfully Updated");
            } else {
                out.println("Not Updated");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public void sell_spareParts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model; int n;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        model = request.getParameter("model");
        n = Integer.parseInt(request.getParameter("n"));
        try {
            String isql = "select units from spareparts where model_no = ?";
            PreparedStatement stmt = connection.prepareStatement(isql);
            stmt.setString(1, model);
            ResultSet rs = stmt.executeQuery();
            int t = 0;
            if(rs.next()) {
                t=rs.getInt(1) -n;
            }
            String sql = "update spareparts set units=? where model_no = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, t);
            stmt.setString(2, model);
            int rows_affected = stmt.executeUpdate();
            if(rows_affected > 0) {
                out.println("Successfully Updated");
            } else {
                out.println("Not Updated");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
