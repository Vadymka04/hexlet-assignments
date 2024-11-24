package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAll() {
        var books = bookRepository.findAll();
        return books.stream().map(bookMapper::map).toList();
    }

    public BookDTO getById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        return bookMapper.map(book);
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO update(Long id, BookUpdateDTO bookUpdateDTO) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
