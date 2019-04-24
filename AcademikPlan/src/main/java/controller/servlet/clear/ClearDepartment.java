package controller.servlet.clear;

import connection.pooling.ConnectionPool;
import data.dao.mariaDB.DepartmentMariaDb;
import data.dao.mariaDB.FactoryMariaDb;
import data.model.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/clearDepartment")
public class ClearDepartment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idDepartmentClear"));
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            FactoryMariaDb fb = new FactoryMariaDb();
            //UserMariaDb userDao = fb.getUserMariaDB(connection);
            DepartmentMariaDb depDao = fb.getDepartmentMariaDB(connection);
            Department department = depDao.getDepartmentById(id);
            /*List<User> users = new ArrayList<>();
            users.addAll(userDao.getUsersByDepartment(id, true));
            users.addAll(userDao.getUsersByDepartment(id, false));
            for(User user : users)
                userDao.deleteUser(user);*/
            depDao.deleteDepartment(department);

            HttpSession session = req.getSession();
            session.setAttribute("message", "Кафедра полностью удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("/plans/admin/department-managment");
    }
}