package pl.sda.library.service;

import org.springframework.stereotype.Component;
import pl.sda.library.model.Book;
import pl.sda.library.model.Fine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class Menu {

    private final OrderService orderService;
    private final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(System.in));

    public Menu(OrderService orderService) {
        this.orderService = orderService;
    }

    public void printMenu() {
        System.out.println("1. Wypozycz");
        System.out.println("2. Zwroc");
        System.out.println("3. Dodaj");
        System.out.println("4. Usun");
        try {
            String line = bufferedReader.readLine();
            makeDecision(Integer.parseInt(line));
        } catch (IOException e) {
            e.printStackTrace();//fuj
        }
    }

    private void makeDecision(int decision) throws IOException {
        switch (decision) {
            case 1:
                System.out.println("Podaj tytul: ");
                String title = bufferedReader.readLine();
                Optional<Book> book = orderService.borrowBook(title);
                System.out.println(book.isPresent() ? book.get().toString() : "Sorry");
                break;
            case 2:
                System.out.println("Podaj id: ");
                long id = Long.parseLong(bufferedReader.readLine());
                Optional<Fine> fine = orderService.returnBook(id);
                System.out.println(fine.isPresent() ? fine.get().getAmount() : "Dzieki");
                break;
            case 3:
                System.out.println("Tytul: ");
                String newTitle = bufferedReader.readLine();
                System.out.println("Autor");
                String author = bufferedReader.readLine();
                Long setId = orderService.addBook(new Book(author, newTitle));
                System.out.println("Dodano z id: " + setId);
                break;
            case 4:
                System.out.println("Podaj id: ");
                long idToRemove = Long.parseLong(bufferedReader.readLine());
                boolean deleted = orderService.deleteBook(idToRemove);
                System.out.println(deleted ? "Usunieto" : "Nie mozna usunac");
                break;
        }
    }
}
