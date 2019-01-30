package dao;

public class PersistException extends Exception {

        public PersistException(String message, Throwable cause) {
                super(message, cause);
        }

        public PersistException(Throwable cause) {
                super(cause);
        }

}

