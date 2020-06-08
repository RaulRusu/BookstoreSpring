package ro.ubb.catalog.client.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.web.dto.AcquisitionDto;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.PairDto;
import ro.ubb.catalog.web.dto.listDto.AcquisitionListDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;
import ro.ubb.catalog.web.dto.listDto.BookListDto;
import ro.ubb.catalog.web.dto.listDto.ClientListDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;


/**
 * Created by radu.
 */
@Component
public class ConsoleUI {
    @Autowired
    RestTemplate restTemplate;
    private Map<String, Consumer<String>> commands;
    private BufferedReader bufferedReader;
    private boolean runing;
    public static final String BooksURL = "http://localhost:8080/api/books";
    public static final String ClientsURL = "http://localhost:8080/api/clients";
    public static final String AcquisitionsURL = "http://localhost:8080/api/acquisitions";
    public static final String ReportsURL = "http://localhost:8080/api/reports";

    public ConsoleUI() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.commands = new HashMap<>();
        this.initialiseCommands();
        this.runing = true;
    }

    private void initialiseCommands(){
        this.commands.put("help",(args)-> printHelp());
        this.commands.put("exit",(args)-> exit());

        this.commands.put("printBooks", (args) -> printBooks());
        this.commands.put("addBook", (args) -> addBook(args));
        this.commands.put("updateBook", (args) -> updateBook(args));
        this.commands.put("deleteBook", (args) -> deleteBook(args));
        this.commands.put("filterBooksByDate", (args) -> filterBooksByDate(args));

        this.commands.put("printClients", (args) -> printClients());
        this.commands.put("addClient", (args) -> addClient(args));
        this.commands.put("updateClient", (args) -> updateClient(args));
        this.commands.put("deleteClient", (args) -> deleteClient(args));
        this.commands.put("filterClientsByAge", (args) ->filterClientsByAge(args));

        this.commands.put("printAcquisitions", (args) -> printAcquisitions());
        this.commands.put("addAcquisition", (args) -> addAcquisition(args));
        this.commands.put("updateAcquisition", (args) -> updateAcquisition(args));
        this.commands.put("deleteAcquisition", (args) -> deleteAcquisition(args));



        this.commands.put("reportClientAcquisitions", (args) ->reportClientAcquisitions());
        this.commands.put("reportClientsSpentMoney", (args) ->reportClientsSpentMoney());
        this.commands.put("reportBooksBought", (args) ->reportBooksBought());

        this.commands.put("printBooksSorted", (args) -> printBooksSorted(args));
        this.commands.put("printClientsSorted", (args) -> printClientsSorted(args));
        this.commands.put("printAcquisitionsSorted", (args) -> printAcquisitionsSorted(args));
    }

    private void exit() {
        this.runing = false;
    }

    private void printHelp() {
        System.out.println();

        System.out.println("help - display all usable console functions");
        System.out.println("exit - exit the app");

        System.out.println("printBooks - display all books");
        System.out.println("printClients - display all clients");
        System.out.println("printAcquisitions - display all acquisitions");

        System.out.println("addBook author, name, releaseDate(dd/mm/yyyy) - adds a book");
        System.out.println("addClient name, age - adds a client");
        System.out.println("addAcquisition clientID, bookID, purchaseDate(dd/mm/yyyy), price - adds an acquisition");

        System.out.println("updateBook id, newAuthor, newName, newReleaseDate(dd/mm/yyyy) - updates a book");
        System.out.println("updateClient id, newName, newAge - updates a client");
        System.out.println("updateAcquisition id, newBookID, newClientID, newPurchaseDate(dd/mm/yyyy), newPrice - updates an acquisition");

        System.out.println("deleteBook id - deletes a book");
        System.out.println("deleteClient id - deletes a client");
        System.out.println("deleteAcquisition id - deletes an acquisition");

        System.out.println("filterBooksByDate releaseDate(dd/mm/yyyy) - filters books by date");
        System.out.println("filterClientsByAge age - filters books by age");

        System.out.println("reportClientAcquisitions - reports what books every client bought");
        System.out.println("reportClientsSpentMoney - reports how much every client has spent");
        System.out.println("reportBooksBought - reports how many copies of books have been bought");

        System.out.println("printBooksSorted");
        System.out.println("printClientsSorted");
        System.out.println("printAcquisitionsSorted");
    }

    public void runConsole() {
        System.out.println("App started. Write help for help");


        while(this.runing){
            String input = null;
            String commandInput = null;
            String argsInput = null;
            try {
                input = this.bufferedReader.readLine();
            } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            String[] splitInput=input.split(" ",2);
            try {
                commandInput = splitInput[0];
            }
            catch (IndexOutOfBoundsException e) {
                commandInput="help";
            }
            try {
                argsInput = splitInput[1];
            }catch (IndexOutOfBoundsException e) {
                argsInput="";
            }
            try {
                commands.getOrDefault(commandInput,args->{System.out.println("Unknown Command. Executing help");printHelp();}).accept(argsInput);
                System.out.println("Executed Successfully");
            }catch (IllegalArgumentException | IndexOutOfBoundsException | HttpClientErrorException | HttpServerErrorException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBooks() {
        Iterable<BookDto> booksToPrint = this.restTemplate.getForObject(BooksURL, BookListDto.class).getDtoList();

        if(StreamSupport.stream(booksToPrint.spliterator(), false).count() == 0)
            System.out.println("Book list is empty");
        else
            booksToPrint.forEach(book -> System.out.println(book.toString()));
    }

    private void addBook(String args){
        String[] splitArgs=args.split(", ",3);
        try{
            String author = splitArgs[0];
            String name = splitArgs[1];
            String releaseDate = splitArgs[2];
            BookDto book = new BookDto(author, name, releaseDate);
            this.restTemplate.postForEntity(BooksURL, book, BookDto.class);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(String args) {
        String[] splitArgs=args.split(", ",1);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            this.restTemplate.delete(AcquisitionsURL + "/cascadeBook/{id}", id);
            this.restTemplate.delete(BooksURL + "/{id}", id);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void updateBook(String args) {
        String[] splitArgs=args.split(", ",4);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            String author = splitArgs[1];
            String name = splitArgs[2];
            String releaseDate = splitArgs[3];
            BookDto book = new BookDto(author, name, releaseDate);
            book.setId(id);
            this.restTemplate.put(BooksURL + "/{id}", book, book.getId());
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void filterBooksByDate(String args) {
        String[] splitArgs=args.split(", ",1);
        try {
            String date = splitArgs[0];
            Iterable<BookDto> filteredBooks = this.restTemplate.getForObject(BooksURL + "/filter/{date}", BookListDto.class, date).getDtoList();
            if(StreamSupport.stream(filteredBooks.spliterator(), false).count() == 0)
                System.out.println("Filtered book list is empty");
            else
                filteredBooks.forEach(book -> System.out.println(book.toString()));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void printClients() {
        Iterable<ClientDto> clientsToPrint = this.restTemplate.getForObject(ClientsURL, ClientListDto.class).getDtoList();
        if(StreamSupport.stream(clientsToPrint.spliterator(), false).count() == 0)
            System.out.println("Clients list is empty");
        else
            clientsToPrint.forEach(client -> System.out.println(client.toString()));
    }

    private void addClient(String args){
        String[] splitArgs=args.split(", ",2);
        try{
            String name = splitArgs[0];
            int age = Integer.parseInt(splitArgs[1]);
            ClientDto client = new ClientDto(name, age);
            this.restTemplate.postForEntity(ClientsURL, client, ClientDto.class);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient(String args) {
        String[] splitArgs=args.split(", ",1);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            this.restTemplate.delete(AcquisitionsURL + "/cascadeClient/{id}", id);
            this.restTemplate.delete(ClientsURL + "/{id}", id);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void updateClient(String args) {
        String[] splitArgs=args.split(", ",3);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            String name = splitArgs[1];
            int age = Integer.parseInt(splitArgs[2]);
            ClientDto client = new ClientDto(name, age);
            client.setId(id);
            this.restTemplate.put(ClientsURL + "/{id}", client, client.getId());
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void filterClientsByAge(String args) {
        String[] splitArgs=args.split(", ",1);
        try {
            int age = Integer.parseInt(splitArgs[0]);
            Iterable<ClientDto> filteredClients = this.restTemplate.getForObject(ClientsURL + "/filter/{age}", ClientListDto.class, age).getDtoList();;
            if(StreamSupport.stream(filteredClients.spliterator(), false).count() == 0)
                System.out.println("Filtered client list is empty");
            else
                filteredClients.forEach(client -> System.out.println(client.toString()));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void printAcquisitions() {
        List<AcquisitionDto> acquisitionsToPrint = this.restTemplate.getForObject(AcquisitionsURL, AcquisitionListDto.class).getDtoList();
        if(StreamSupport.stream(acquisitionsToPrint.spliterator(), false).count() == 0)
            System.out.println("Acquisitions list is empty");
        else
            acquisitionsToPrint.forEach(acquisition -> System.out.println(acquisition.toString()));
    }

    private void addAcquisition(String args){
        String[] splitArgs=args.split(", ",4);
        try{
            int clientID = Integer.parseInt(splitArgs[0]);
            int bookID = Integer.parseInt(splitArgs[1]);
            String purchaseDate = splitArgs[2];
            int price = Integer.parseInt(splitArgs[3]);
            AcquisitionDto acquisition = new AcquisitionDto(clientID, bookID, purchaseDate,price);
            this.restTemplate.postForEntity(AcquisitionsURL, acquisition, AcquisitionDto.class);
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void deleteAcquisition(String args) {
        String[] splitArgs=args.split(", ",1);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            this.restTemplate.delete(AcquisitionsURL + "/{id}", id);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void updateAcquisition(String args) {
        String[] splitArgs=args.split(", ",5);
        try{
            int id = Integer.parseInt(splitArgs[0]);
            int clientID = Integer.parseInt(splitArgs[1]);
            int bookID = Integer.parseInt(splitArgs[2]);
            String purchaseDate = splitArgs[3];
            int price = Integer.parseInt(splitArgs[4]);
            AcquisitionDto acquisition = new AcquisitionDto(clientID, bookID, purchaseDate,price);
            acquisition.setId(id);
            this.restTemplate.put(AcquisitionsURL + "/{id}", acquisition, acquisition.getId());
        }
        catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void reportClientAcquisitions() {
        List<LinkedHashMap<ClientDto, BaseListDto<BookDto>>> itemsToPrint = this.restTemplate.getForObject(ReportsURL + "/reportClientAcquisitions", new BaseListDto<PairDto<ClientDto, BaseListDto<BookDto>>>().getClass()).getDtoList();

        if(StreamSupport.stream(itemsToPrint.spliterator(), false).count() == 0)
            System.out.println("Clients list is empty");
        else
            StreamSupport.stream(itemsToPrint.spliterator(), false)
                    .forEach(entry -> {
                        System.out.println("Client: " + entry.get("first"));
                        System.out.println(entry.get("second"));
                        System.out.println();
                    });
    }

    private void reportClientsSpentMoney() {
        List<LinkedHashMap<ClientDto, Integer>> itemsToPrint = restTemplate.getForObject(ReportsURL + "/reportClientsSpentMoney", BaseListDto.class).getDtoList();;
        if(StreamSupport.stream(itemsToPrint.spliterator(), false).count() == 0)
            System.out.println("Clients list is empty");
        else
            StreamSupport.stream(itemsToPrint.spliterator(), false)
                    .forEach(entry -> System.out.println("Client: " + entry.get("first") + " " + entry.get("second")));
    }

    private void reportBooksBought() {
        List<LinkedHashMap<BookDto, Integer>> itemsToPrint = restTemplate.getForObject(ReportsURL + "/reportBooksBought", BaseListDto.class).getDtoList();
        if(StreamSupport.stream(itemsToPrint.spliterator(), false).count() == 0)
            System.out.println("Clients list is empty");
        else
            StreamSupport.stream(itemsToPrint.spliterator(), false)
                    .forEach(entry -> System.out.println("Book: " + entry.get("first") + " "  + entry.get("second")));
    }

    private void printAcquisitionsSorted(String args) {
        Iterable<BookDto> booksToPrint = this.restTemplate.getForObject(BooksURL, BookListDto.class).getDtoList();

        if(StreamSupport.stream(booksToPrint.spliterator(), false).count() == 0)
            System.out.println("Book list is empty");
        else
            booksToPrint.forEach(book -> System.out.println(book.toString()));
    }

    private void printClientsSorted(String args) {
        Iterable<ClientDto> clientsToPrint = this.restTemplate.getForObject(ClientsURL, ClientListDto.class).getDtoList();
        if(StreamSupport.stream(clientsToPrint.spliterator(), false).count() == 0)
            System.out.println("Clients list is empty");
        else
            clientsToPrint.forEach(client -> System.out.println(client.toString()));
    }

    private void printBooksSorted(String args) {
        List<AcquisitionDto> acquisitionsToPrint = this.restTemplate.getForObject(AcquisitionsURL, AcquisitionListDto.class).getDtoList();
        if(StreamSupport.stream(acquisitionsToPrint.spliterator(), false).count() == 0)
            System.out.println("Acquisitions list is empty");
        else
            acquisitionsToPrint.forEach(acquisition -> System.out.println(acquisition.toString()));
    }
}
