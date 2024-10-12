package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
