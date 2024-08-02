package dao;

public class DAOExecption extends Exception{


    public DAOExecption(String message) {
        super(message);
    }

    public DAOExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOExecption(Throwable cause) {
        super(cause);
    }
}
