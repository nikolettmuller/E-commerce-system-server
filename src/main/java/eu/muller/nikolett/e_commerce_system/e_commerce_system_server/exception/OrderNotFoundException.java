package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
