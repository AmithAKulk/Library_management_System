package com.example.library_management_system.service;

import com.example.library_management_system.DTO.responseDTO.ResponseAuthor;
import com.example.library_management_system.DTO.responseDTO.ResponseBook;
import com.example.library_management_system.DTO.responseDTO.ResponseBook_AuthorAndGenre;
import com.example.library_management_system.DTO.requestDTO.RequestBook;
import com.example.library_management_system.Enum.Genre;
import com.example.library_management_system.converters.AuthorConverter;
import com.example.library_management_system.converters.BookConverter;
import com.example.library_management_system.converters.StudentConverter;
import com.example.library_management_system.exception.AuthorNotFoundException;
import com.example.library_management_system.exception.BookNotFoundException;
import com.example.library_management_system.model.Author;
import com.example.library_management_system.model.Book;
import com.example.library_management_system.repository.AuthorRepository;
import com.example.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    public ResponseBook addBook(RequestBook requestBook) {
        // check if author exists or not (we will pass author Id in the requestBook DTO)
        Optional<Author> authorOptional = authorRepository.findById(requestBook.getAuthorId());
        if(!authorOptional.isPresent()){
            throw new AuthorNotFoundException("Author does not exists.");
        }
        Author author = authorOptional.get();
        Book book = Book.builder().
                author(author).
                title(requestBook.getTitle()).
                genre(requestBook.getGenre()).
                cost(requestBook.getCost()).
                noOfPages(requestBook.getNoOfPages()).
                isIssued(false).
                build();
        author.getBooks().add(book);
        authorRepository.save(author);

        ResponseBook responseBook = BookConverter.fromBookToResBook(book);

        return responseBook;

    }

    public String deleteABook(String title) {
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findByTitle(title));
        if(!bookOptional.isPresent()){
            throw new BookNotFoundException("Book does not exists.");
        }
        bookRepository.delete(bookOptional.get());
        return "Book deleted successfully.";
    }

    public List<ResponseBook_AuthorAndGenre> bookFromAGenre(Genre genre) {
        List<Book> bookList = bookRepository.findByGenre(genre);
        List<ResponseBook_AuthorAndGenre> responseList = new ArrayList<>();
        for(Book book : bookList){
            ResponseBook_AuthorAndGenre responseBookAuthorAndGenre = BookConverter.fromBookToResBook_AuthorAndGenre(book);
            responseList.add(responseBookAuthorAndGenre);
        }
        return responseList;
    }

    public List<ResponseBook_AuthorAndGenre> bookFromAGenreCosting500(Genre genre) {
        List<Book> bookList = bookRepository.findByGenre(genre);
        List<ResponseBook_AuthorAndGenre> responseList = new ArrayList<>();
        for(Book book : bookList){
            if(book.getCost() == 500) {
                ResponseBook_AuthorAndGenre responseBookAuthorAndGenre = BookConverter.fromBookToResBook_AuthorAndGenre(book);
                responseList.add(responseBookAuthorAndGenre);
            }
        }
        return responseList;
    }

    public List<ResponseBook_AuthorAndGenre> booksHavingPagesAtoB(int minPage, int maxPage) {
        List<Book> bookList = bookRepository.findBookHavingPagesBetweenAandB(minPage, maxPage);
        List<ResponseBook_AuthorAndGenre> responseList = new ArrayList<>();
        for(Book book : bookList){
            ResponseBook_AuthorAndGenre responseBookAuthorAndGenre = BookConverter.fromBookToResBook_AuthorAndGenre(book);
            responseList.add(responseBookAuthorAndGenre);
        }
        return responseList;
    }

    public List<ResponseAuthor> authorsFromAGenre(Genre genre) {
        List<Book> bookList = bookRepository.findByGenre(genre);
        List<ResponseAuthor> responseList = new ArrayList<>();
        Set<Author> set = new HashSet<>();
        for(Book book : bookList){
            set.add(book.getAuthor());
        }
        for(Author author : set){
            ResponseAuthor responseAuthor = AuthorConverter.fromAuthorToResponseAuthor(author);
            responseList.add(responseAuthor);
        }
        return responseList;
    }
}