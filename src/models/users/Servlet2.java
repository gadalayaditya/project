package models.users;

import database.ConnectionFactory;
import models.User;
import models.Vehicle;

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
import java.util.Date;
import java.util.Scanner;

/**
 * Created by gadalaya on 7/23/2017.
 */
@WebServlet(name = "Servlet2")
public class Servlet2 extends HttpServlet {
    int id,units,sunits,vehicle_id;
    String model,smodel,name,sname,brand,sbrand;
    double price,sprice,tax,stax,profit;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    try{
        if(request.getParameter("n2")!= null)
            sparepart(request,response);
    }
    catch(Exception e) {
        out.println("exception");
    }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
    }
    public void add_operator() throws SQLException {
        System.out.println("Enter user name");
        Scanner sc =new Scanner(System.in);
        String user_name = sc.next();
        Connection con = ConnectionFactory.getConnection();
        //Statement st0 = con.createStatement();
        PreparedStatement st1 = con.prepareStatement("select * from users where username = ?");
        st1.setString(1,user_name);
        ResultSet res =st1.executeQuery();
        //ResultSet rs = st0.executeQuery("select * from users where username = "+user_name);
        //System.out.println(rs);
        if(!res.next()) {
            System.out.println("Enter Your Password");
            String password = sc.next();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date now = Calendar.getInstance().getTime();
            String reportDate = df.format(now);


            PreparedStatement st = con.prepareStatement("insert into users(username,password,privelege,updated_at) values(?,?,?,?)");
            //st.setInt(1,1);
            st.setString(1,user_name);
            st.setString(2,password);
            st.setInt(3,1);
            st.setString(4,reportDate);
            int result=st.executeUpdate();
            if(result>0) {
                System.out.println("Operator:"+user_name+" created successfully");
            }

        } else {
            System.out.println("username already exists!!");

            add_operator();
        }
    }
    public  void remove_operator() throws SQLException {
        System.out.print("Enter user name you want to delete:  ");
        Scanner sc =new Scanner(System.in);
        String user_name = sc.next();
        Connection con = ConnectionFactory.getConnection();
        //Statement st0 = con.createStatement();
        PreparedStatement st1 = con.prepareStatement("select * from users where username = ?");
        st1.setString(1,user_name);
        ResultSet res =st1.executeQuery();
        //ResultSet rs = st0.executeQuery("select * from users where username = "+user_name);
        //System.out.println(rs);
        if(res.next()) {
            PreparedStatement st = con.prepareStatement("delete from users where username = ?");
            //st.setInt(1,1);
            st.setString(1,user_name);
            int result=st.executeUpdate();
            if(result>0) {
                System.out.println("Operator:"+user_name+" deleted successfully");
            }

        } else {
            System.out.println("username not found");
        }
    }
    public  void item(HttpServletRequest request, HttpServletResponse response) throws ServletException,  SQLException, IOException, InterruptedException {
        System.out.println("1. Vehicle: \n2. Sparepart: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 1) {
            vehicle(request,response);

        } else if (n == 2) {
            sparepart(request, response);
        } else {
            System.out.println("Wrong input entered");
            final  String ESC = "\033[";
            System.out.print(ESC + "2J");
            item(request,response);

        }
    }
    public  void vehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException, InterruptedException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("1. Add Vehicle: \n2. Remove Vehicle: \n3. Back: 4. Logout: ");
        Scanner sc = new Scanner(System.in);
        int n1= sc.nextInt();
        Vehicle v = new Vehicle();
        if(n1==1) {
            System.out.println("Enter Vehicle name");
            name=sc.next();
            System.out.println("Enter model number");
            model=sc.next();
            System.out.println("Enter brand name");
            brand=sc.next();
            System.out.println("Enter price of vehicle");
            price=sc.nextDouble();
            System.out.println("Enter units of vehicle");
            units=sc.nextInt();
            System.out.println("Enter tax incurred ");
            tax=sc.nextDouble();
            System.out.println("Enter profit incurred");
            profit=sc.nextDouble();
            v.add_vehicle(model,name,brand,price,units,tax,profit);
        }
        else if(n1 ==2) {
            System.out.println("Enter model number of vehicle you want to delete");
            String dvmodel=sc.next();
            v.remove_vehicle(dvmodel);
        }
        else if(n1 ==3) {
            item(request,response);
        }
        else if(n1 ==4) {
            logout();
        }
        else {
            System.out.println("Wrong input entered");
            vehicle(request,response);
        }

    }
    public void sparepart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException,  InterruptedException {// html
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       // out.println("1. Add Sparepart: <br>2. Remove Sparepart: <br>3. Back: 4.Logout<br>");
       // Scanner sc = new Scanner(System.in);
        int n2=Integer.parseInt(request.getParameter("n2"));
        if(n2==1) {

            out.println("Enter sparpart name");
            out.println("<form id='myform' action='SparePart'> <input id = 'sname' name = 'sname' type='text' <br>");
            // sname=sc.next();
            HttpSession ssn1=request.getSession();
            ssn1.setAttribute("mode","add_sparepart");
            out.println("Enter model number");
            out.println("<input id = 'smodel' name = 'smodel' type='text' <br>");
            // smodel=sc.next();
            out.println("Enter brand name");
            out.println("<input id = 'sbrand' name = 'sbrand' type='text' <br>");
            //sbrand=sc.next();
            System.out.println("Enter price of sparepart");
            out.println("<input id = 'sprice' name = 'sprice' type='text' <br>");
            // sprice=sc.nextDouble();
            out.println("Enter units of sparepart");
            out.println("<input id = 'sunits' name = 'sunits' type='text' <br>");
            // sunits=sc.nextInt();
            System.out.println("Enter sparepart's vehicle ID");
            out.println("<input id = 'vehicle_id' name = 'vehicle_id' type='text' <br>");
            //vehicle_id=sc.nextInt();
            System.out.println("Enter tax incurred ");
            out.println("<input id = 'stax' name = 'stax' type='text' <br>");
            out.println("<input type ='submit' >");
            out.println("<form>");
            // stax=sc.nextDouble();
            // SparePart sp = new SparePart();
            //  sp.add_sparepart(smodel,sname,sbrand,sprice,sunits,vehicle_id,stax);
        }
        else if(n2 ==2) {
            out.println("Enter model number of sparepart you want to delete!");
            out.println("<form id='myform' action='SparePart'> <input id = 'dsmodel' name = 'dsmodel' type='text' <br>");
            HttpSession ssn1=request.getSession();
            ssn1.setAttribute("mode","add_sparepart");
            out.println("<input type ='submit' >");
            out.println("<form>");
            //String dsmodel=sc.next();
            //SparePart sp = new SparePart();
            //sp.remove_sparepart(dsmodel);
        }
        else if(n2 ==3) {

            item(request,response);
        }
        else if(n2 ==4) {

            logout();
        }
        else {
            out.println("Wrong input entered");
            sparepart(request,response);
        }

    }
    public void logout() throws SQLException {
        String sql = "insert into logs(username, time, description) values(?, ?, ?)";
        Connection con =ConnectionFactory.getConnection();
        PreparedStatement st = con.prepareStatement(sql);

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
