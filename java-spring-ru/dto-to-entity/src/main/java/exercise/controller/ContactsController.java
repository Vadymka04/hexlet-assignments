package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO index(@RequestBody ContactCreateDTO data) {
        var contact = new Contact();
        contact.setFirstName(data.getFirstName());
        contact.setLastName(data.getLastName());
        contact.setPhone(data.getPhone());

        var savedContact = contactRepository.save(contact);
        return new ContactDTO(savedContact.getId(), savedContact.getFirstName(), savedContact.getLastName(), savedContact.getPhone(), savedContact.getCreatedAt(), savedContact.getUpdatedAt());
    }
}
