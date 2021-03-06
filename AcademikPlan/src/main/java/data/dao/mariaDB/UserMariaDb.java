package data.dao.mariaDB;

import connection.pooling.ConnectionPool;
import data.ConnectionService;
import data.dao.UserDao;
import data.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMariaDb extends ConnectionService implements UserDao {
    private Connection connection;
    private String sortParameter = "idUser";

    UserMariaDb(Connection connection){
        this.connection = connection;
    }

    @Override
    public void setOrder(User.sortParameter s) {
        sortParameter = s.toString();
    }

    @Override
    public User getUserById(int pk) {
        String query = "SELECT * FROM users WHERE idUser = "+pk;
        return getUsers(query).get(0);
    }

    @Override
    public List<User> getAllUser() {
        return getUserByVisible(-1);
    }

    @Override
    public List<User> getUsersByVisible(boolean visible) {
        return getUserByVisible(visible ? 1 : 0);
    }

    @Override
    public List<User> getUsersByDepartment(int idDepartment, boolean visible) {
        String query = "SELECT * FROM users WHERE idDepartment = "+idDepartment+" AND visible = "+ (visible ? 1:0) +" ORDER BY "+sortParameter;
        return getUsers(query);
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE login = \""+login+"\" AND password = \""+password+"\"");
            rs = statement.executeQuery();
            rs.next();
            user = new User();
            user.setIdUser(rs.getInt("idUser"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setIdDepartment(rs.getInt("idDepartment"));
            user.setIdRole(rs.getInt("idRole"));
            user.setVisible(rs.getInt("visible") == 1);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return user;
    }

    @Override
    public boolean isUniqueLogin(String login) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE login = \""+login+"\"");
            rs = statement.executeQuery();
            int i = 0;
            while(rs.next())
                i++;
            return i<1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return false;
    }

    @Override
    public boolean isExist(String login, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement("SELECT EXISTS(SELECT login, password FROM users WHERE login = \""+login+"\" AND password = \""+password+"\")");
            rs = statement.executeQuery();
            rs.next();
            int result = rs.getInt(1);
            if(result==1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return false;
    }

    @Override
    public boolean insertUser(User user) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query="INSERT INTO users SET login = '"+user.getLogin()+"',"
                + " password = '"+user.getPassword()+"', idDepartment = "+user.getIdDepartment()+", "
                + " idRole = " + user.getIdRole() + ", visible = " + (user.isVisible() ? 1 : 0);
        try{
            statement = connection.prepareStatement(query);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query="UPDATE users SET login = '"+user.getLogin()+"',"
                + " password = '"+user.getPassword()+"', idDepartment = "+user.getIdDepartment()+", "
                + " idRole = " + user.getIdRole() + ", visible = " + (user.isVisible() ? 1 : 0)
                + " WHERE idUser = "+user.getIdUser();
        try{
            statement = connection.prepareStatement(query);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query="DELETE FROM users WHERE idUser = "+user.getIdUser();
        try{
            statement = connection.prepareStatement(query);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return false;
    }

    private List<User> getUserByVisible(int visible) {
        String query = "SELECT * FROM users";
        if(visible != -1)
            query += " WHERE visible = "+visible;
        query += " ORDER BY "+sortParameter;
        return getUsers(query);
    }

    private List<User> getUsers(String query) {
        List<User> userList = new ArrayList<User>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setIdDepartment(rs.getInt("idDepartment"));
                user.setIdRole(rs.getInt("idRole"));
                user.setVisible(rs.getInt("visible") == 1);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResurse(statement, rs);
        }
        return userList;
    }

    public static void main(String[] args){
        try {
            UserMariaDb u = new UserMariaDb(ConnectionPool.getConnection());
            List<User> users = u.getAllUser();
            for(User el : users)
                System.out.println(el.toString());
            System.out.println(u.isUniqueLogin("3"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
