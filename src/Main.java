import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import database.ConnectionFactory;
import models.User;
import models.users.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadalaya on 7/22/2017.
 */
@WebServlet(name = "ServletMain")
public class Main extends HttpServlet {
    static String username;
    Connection conn = ConnectionFactory.getConnection();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            mainMenu(request, response);

        } catch (Exception e) {
            out.println("exception ooccured");
        }
    }
    public void mainMenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("\n1. Login\n2. View\n\tSelect an input : ");
        try {
            int i = Integer.parseInt(br.readLine());
            switch (i) {
                case 1:
                    loginMenu(request, response);
                    break;
                case 2:
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("\n---------------Welcome User--------------\n");
                    TimeUnit.MILLISECONDS.sleep(500);
                    viewMenu();
                    break;
                default:
                    System.out.println("Incorrect Input!  Try Again!! \n\n");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    mainMenu(request, response);
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
            mainMenu(request, response);
        } catch (IOException ioe) {
            System.err.println("Invalid Input");
            mainMenu(request, response);
        } catch (InterruptedException ie) {
            System.err.println("Something went wrong!");
            mainMenu(request, response);
        }
    }

    public void loginMenu(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        User u = new User();
        try {
            System.out.print("<br>Username : <br>");
            String username = br.readLine();
            //System.out.print(username);
            out.print("Password : ");
            String password = br.readLine();
            //System.out.print(password);
            int privilege = u.isValid(username, password); //isValid(username, password);// TODO: 7/12/2017 enter check login Details function name

            switch(privilege) {
                case 2 :
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("Logged in as Admin succeeded!!");
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("\n---------------------Welcome Admin------------------\n");
                    adminMenu();
                    break;
                case 1 :
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("Logged in as Operator succeeded!!");
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("\n---------------------Welcome Operator------------------\n");
                    operatorMenu();
                    break;
                case -1 :
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("Incorrect Credentials. Login Again......");
                    TimeUnit.MILLISECONDS.sleep(500);
                    loginMenu();
                    break;
                default :
                    System.out.println("Oops! Try Again!");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    mainMenu();
                    break;
            }
        } catch (IOException ioe) {
            System.err.println("Invalid Input");
            loginMenu();
        } catch (InterruptedException ie) {
            System.err.println("Something went wrong!");
            loginMenu();
        } catch (SQLException sqle) {
            System.err.println("Something went wrong!");
            loginMenu();
        }
    }

    public void viewMenu() throws SQLException{
        System.out.print("\n1. View Vehicles\n2. View Spare Parts\n3. Back to main menu\tSelect any option : ");
        Statement stmt = conn.createStatement();
        ResultSet rs1;
        try {
            int i = Integer.parseInt(br.readLine());
            switch(i) {
                case 1 :
                    rs1 = stmt.executeQuery("select * from vehicles");
                    while (rs1.next()) {
                        System.out.println("Vehicle ID : " + rs1.getInt(1) + ",  Name : " + rs1.getString(3) + ",  Units : " + rs1.getInt(6));
                    }
                    // TODO: 7/13/2017  enter view vehicles function name
                    TimeUnit.MILLISECONDS.sleep(500);
                    mainMenu();
                    break;
                case 2 :
                    rs1 = stmt.executeQuery("select * from spareparts");
                    while(rs1.next()) {
                        System.out.println("SparePart ID : " + rs1.getInt(1) + ",  Name : " + rs1.getString(3) + ",  Units : " + rs1.getInt(6));
                    }
                    // TODO: 7/13/2017  enter view spare parts function name
                    TimeUnit.MILLISECONDS.sleep(500);
                    mainMenu();
                    break;
                case 3 :
                    mainMenu();
                    break;
                default :
                    System.out.println("Oops! Try Again!!");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    viewMenu();
                    break;
            }
        } catch(IOException ioe) {
            System.err.println("Invalid Input");
            viewMenu();
        }
        catch (InterruptedException ie) {
            System.err.println("Something went wrong!");
            viewMenu();
        }
    }

    public void adminMenu() {
        System.out.print("1. Register Operator\n2. Terminate Operator\n3. Stock\n4. Logout\n\tChoose an option : ");
        Admin ad = new Admin();
        try {
            int i = Integer.parseInt(br.readLine());
            switch(i) {
                case 1 :
                    ad.add_operator();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    adminMenu();
                    break;
                case 2 :
                    ad.remove_operator();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    adminMenu();
                    break;
                case 3 :
                    ad.item();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    adminMenu();
                    break;
                case 4 :
                    ad.logout();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    mainMenu();
                    break;
                default :
                    System.out.println("Oops! Try Again!!\n");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    adminMenu();
                    break;
            }
        } catch(IOException ioe) {
            System.err.println("Invalid Input");
            adminMenu();
        } catch (InterruptedException ie) {
            System.err.println("Something went wrong");
            adminMenu();
        } catch(SQLException sqle) {
            System.err.println("Something went wrong!");
            adminMenu();
        }
    }

    public void operatorMenu() throws SQLException{
        System.out.print("1. Add Stock\n2. Sell\n3. Display Stock\n4. Logout\n\tChoose an option : ");
        try {
            models.users.Operator op = new models.users.Operator();
            int i = Integer.parseInt(br.readLine());
            switch(i) {
                case 1 :
                    op.add_stock();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    operatorMenu();
                    break;
                case 2 :
                    op.sell();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    operatorMenu();
                    break;
                case 3 :
                    op.display();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    operatorMenu();
                case 4:
                    op.logout();
                    TimeUnit.MILLISECONDS.sleep(1000);
                    mainMenu();
            }
        } catch(IOException ioe) {
            System.err.println("Invalid Input");
            viewMenu();
        }
        catch (InterruptedException ie) {
            System.err.println("Something went wrong!");
            operatorMenu();
        } catch(SQLException sqle) {
            System.err.println("Something went wrong!");
            operatorMenu();
        }
    }

    public static void main(String[] args) throws SQLException{
        Main m = new Main();
        m.mainMenu();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
doPost(request,response);
    }
}
