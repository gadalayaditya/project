package models.users;

import database.ConnectionFactory;
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

/**
 * Created by gadalaya on 7/24/2017.
 */
@WebServlet(name = "Servlet3")
public class Servlet3 extends HttpServlet {
    Connection conn = ConnectionFactory.getConnection();
    String str,str2;
    int n;
    String model;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(request.getParameter("str")!=null)
        {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void add_stock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("Enter the type(Vehicle/Sparepart) : ");
        str = sc.nextLine();


        switch(str.toLowerCase()) {
            case "vehicle" : System.out.println("Enter the Vehicle Model Number : ");
//                model = sc.nextLine();
//                System.out.println("Enter the no. of units to be added: ");
//                n = sc.nextInt();
//                Vehicle v = new Vehicle();
//                v.add_vehicle_stock(model, n);
//                System.out.println("Vehicle Added");
                break;
            case "sparepart"   : out.println("Enter the SparePart Model Number : ");
                out.println("<form id='myform' action='SparePart'> <input id = 'model' name = 'model' type='text' <br>");// html here
                // model = sc.nextLine();
                HttpSession ssn1=request.getSession();
                ssn1.setAttribute("mode","add_sparePartsStocks");
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
    public void sell(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("Enter the type(Vehicle/Sparepart) : ");
        str = sc.nextLine();
        switch(str.toLowerCase()) {
            case "vehicle" : System.out.println("Enter the Vehicle Model Number : ");
//                model = sc.nextLine();
//                System.out.println("Enter the no. of units to be sold : ");
//                n = sc.nextInt();
//                Vehicle v = new Vehicle();
//                v.sell_vehicle_stock(model, n);
//                System.out.println("Vehicle(s) Sold");
                break;
            case "sparepart"   : out.println("Enter the SparePart Model Number : ");//html here
                out.println("<form id='myform' action='SparePart'> <input id = 'model' name = 'model' type='text' <br>");// html here
                // model = sc.nextLine();
                HttpSession ssn1=request.getSession();
                ssn1.setAttribute("mode","sell_spareParts");
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
                out.println("You have entered wrong option");
        }
    }
}
