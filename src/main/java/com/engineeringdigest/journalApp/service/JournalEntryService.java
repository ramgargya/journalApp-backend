package com.engineeringdigest.journalApp.service;

import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // logging
    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    // Save an Entry in the database
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            User user = userService.findByUsername(userName); // first it will find the user using the userName
            JournalEntry saved = journalEntryRepository.save(journalEntry); // then save the journalEntry in the journaldb

            user.getJournalEntries().add(saved); // then save the reference of the saved journalEntry in the user's journalEntries List
            userService.saveUser(user); // then we will save the user using userService
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry: ", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        JournalEntry saved = journalEntryRepository.save(journalEntry); // then save the journalEntry in the journaldb
    }


    // Get all the Entries from the database
    public List<JournalEntry> getAll() {
        List<JournalEntry> allEntries = journalEntryRepository.findAll();
        return allEntries;
    }

    // Find Entry by Id
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // Delete Entry by Id
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(userName);// finding the user using userName
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));//then removing the reference from the user collection

            if(removed) {
                userService.saveUser(user); // updated user will save
                journalEntryRepository.deleteById(id); // it will delete the general entry from the journal_entries collection

            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured wile deleting the entry");
        }
        return removed;
    }


    // find by username

}


//controller -> service -> repository --extends--> MongoRepository<>
