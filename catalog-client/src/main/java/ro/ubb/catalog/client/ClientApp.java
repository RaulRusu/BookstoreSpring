package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.client.ui.ConsoleUI;


/**
 * Created by radu.
 */
public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.catalog.client"
                );


        ConsoleUI console = context.getBean(ConsoleUI.class);
        console.runConsole();
    }
}
