package exercise.controller;

import java.util.List;

import exercise.dto.AuthorDTO;
import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    List<BookDTO> index(){
        return bookService.getAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDTO show(@PathVariable Long id){
        return bookService.getById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    BookDTO create (@RequestBody BookCreateDTO bookCreateDTO){
        return bookService.create(bookCreateDTO);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookDTO update (@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO){
        return bookService.update(id, bookUpdateDTO);
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Long id){
        bookService.deleteById(id);
    }
}
