package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(id);
        bookList.add(book);
        this.id++;
        return new ResponseEntity(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    @GetMapping("/get-book-by-id/{id}")
     public ResponseEntity<Book> getBookById(@PathVariable ("id") String id){
        for(Book b: bookList){
            if(b.getId() == Integer.parseInt(id)){
                return new ResponseEntity(b,HttpStatus.FOUND);
            }
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
     }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    @DeleteMapping("/delete-book-by-id/{id}")
     public ResponseEntity<String> deleteBookById(@PathVariable ("id") String id){
        int size = bookList.size();
         for(int i=0; i<size; i++){
             if(bookList.get(i).getId() == Integer.parseInt(id)){
                 bookList.remove(bookList.get(i));
                 return new ResponseEntity("Deleted Successfully..", HttpStatus.ACCEPTED);
             }
         }
         return new ResponseEntity("Book not present", HttpStatus.BAD_REQUEST);
     }

    // get request /get-all-books
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity(bookList, HttpStatus.ACCEPTED);
     }

    // delete request /delete-all-books
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity("All books deleted...", HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    @GetMapping("/get-books-by-author")
     public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam ("author") String name){
        List <Book> list = new ArrayList<>();
        for(Book b: bookList){
            if(b.getAuthor().equals(name)){
                list.add(b);
            }
        }
        return new ResponseEntity(list, HttpStatus.FOUND);
     }

    // get request /get-books-by-genre
    // pass genre name as request param
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam ("genre") String name){
        List <Book> list = new ArrayList<>();
        for(Book b: bookList){
            if(b.getGenre().equals(name)){
                list.add(b);
            }
        }
        return new ResponseEntity(list, HttpStatus.FOUND);
    }
}
