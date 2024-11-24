package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        var authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::map).toList();
    }

    public AuthorDTO getById(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        return authorMapper.map(author);
    }

    public AuthorDTO create(AuthorCreateDTO authorCreateDTO) {
        if (authorCreateDTO.getLastName().isBlank() || authorCreateDTO.getLastName().isEmpty())
            throw new ConstraintViolationException("Lastname is blank or does not exist", null);
        if (authorCreateDTO.getFirstName().isBlank() || authorCreateDTO.getFirstName().isEmpty())
            throw new ConstraintViolationException("Lastname is blank or does not exist", null);
        var author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public AuthorDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        authorMapper.update(authorUpdateDTO, author);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
