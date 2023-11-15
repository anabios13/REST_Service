package by.anabios13.servlets;

import by.anabios13.db.DataSource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductServlet extends HttpServlet {
    @Override
    public void init() {
        try (Connection con = DataSource.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

