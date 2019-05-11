package controller.servlet.restore;

import connection.pooling.ConnectionPool;
import data.dao.mariaDB.FactoryMariaDb;
import data.dao.mariaDB.TitleMariaDb;
import data.model.Title;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/restoreTitle")
public class RestoreTitle extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idTitleRestore"));
        Connection connection = null;
        HttpSession session = req.getSession();
        try{
            connection = ConnectionPool.getConnection();
            FactoryMariaDb fb = new FactoryMariaDb();
            TitleMariaDb titleDao = fb.getTitleMariaDb(connection);
            Title title = titleDao.getTitleById(id);
            title.setVisible(true);

            if(!titleDao.updateTitle(title))
                session.setAttribute("erMessage", "Не удалось провести операцию");
            else session.setAttribute("message", "План восстановлен");
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
        resp.sendRedirect("/plans/title-managment");
    }
}
