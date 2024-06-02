import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BookGUI {
    private BookManager bookManager;
    private ObservableList<Book> bookObservableList;

    public BookGUI(Stage primaryStage) {
        List<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book("1984", "George Orwell", "123456789", 1949));
        initialBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", "987654321", 1960));
        initialBooks.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "111111111", 1925));
        initialBooks.add(new Book("Moby Dick", "Herman Melville", "222222222", 1851));
        initialBooks.add(new Book("War and Peace", "Leo Tolstoy", "333333333", 1869));

        bookManager = new BookManager(initialBooks);
        bookObservableList = FXCollections.observableArrayList(bookManager.getBooks());

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        TableView<Book> tableView = new TableView<>(bookObservableList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getYear()).asObject());

        tableView.getColumns().addAll(titleColumn, authorColumn, isbnColumn, yearColumn);

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> showAddBookDialog());

        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookManager.removeBook(selectedBook);
                bookObservableList.setAll(bookManager.getBooks());
            }
        });

        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                showUpdateBookDialog(selectedBook);
            }
        });

        vbox.getChildren().addAll(tableView, addButton, removeButton, updateButton);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Manager");
        primaryStage.show();
    }

    private void showAddBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        TextField yearField = new TextField();

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbnField, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int year = Integer.parseInt(yearField.getText());

            Book newBook = new Book(title, author, isbn, year);
            bookManager.addBook(newBook);
            bookObservableList.setAll(bookManager.getBooks());
            dialog.close();
        });

        grid.add(addButton, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void showUpdateBookDialog(Book oldBook) {
        Stage dialog = new Stage();
        dialog.setTitle("Update Book");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField titleField = new TextField(oldBook.getTitle());
        TextField authorField = new TextField(oldBook.getAuthor());
        TextField isbnField = new TextField(oldBook.getIsbn());
        TextField yearField = new TextField(String.valueOf(oldBook.getYear()));

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbnField, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int year = Integer.parseInt(yearField.getText());

            Book newBook = new Book(title, author, isbn, year);
            bookManager.updateBook(oldBook, newBook);
            bookObservableList.setAll(bookManager.getBooks());
            dialog.close();
        });

        grid.add(updateButton, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
