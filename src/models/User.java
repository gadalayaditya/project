package models;

//import database.ConnectionFactory;
import database.ConnectionFactory;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User {
    Connection connection = ConnectionFactory.getConnection();
    private static String username;

    public int isValid(String username, String pswd) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select privelege from users where username = ? and password = ?");
        stmt.setString(1, username);
        stmt.setString(2, pswd);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User.username = username;
            int pvlg = rs.getInt(1);
            String sql = "insert into logs(username, time, description) values(?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String dd = dateFormat.format(cal.getTime());

            st.setString(1, username);
            st.setString(2, dd);
            st.setString(3, "login");

            st.executeUpdate();

            return pvlg;
        } else {
            return -1;
        }

    }

    public String getUsername() {
        return username;
    }

    public void logoutUpdate() {
        username = null;
    }

    //= isValid(String username, String pswd);

}