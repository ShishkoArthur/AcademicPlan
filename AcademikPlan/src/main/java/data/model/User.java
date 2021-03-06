package data.model;
import lombok.Data;

@Data
public class User {
    private int idUser;
    private String login;
    private String password;
    private int idDepartment;
    private int idRole;
    private boolean visible;

    public enum sortParameter{
        idUser, login, password, idDepartment, idRole, visible
    }
}
