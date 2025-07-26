package com.kkindia.app;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (!password.equals(confirm)) {
            out.println("<h3>Passwords do not match!</h3>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db", "your_user", "your_pass");

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(username, password) VALUES (?, ?)");

            ps.setString(1, username);
            ps.setString(2, password);

            int result = ps.executeUpdate();
            if (result > 0)
                out.println("<h3>User registered successfully!</h3>");
            else
                out.println("<h3>Failed to register user!</h3>");

            conn.close();

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
