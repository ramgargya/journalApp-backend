package com.engineeringdigest.journalApp.repository;

import com.engineeringdigest.journalApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl {


    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne("")); or we can do below

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        List<User> users = mongoTemplate.find(query, User.class);

        return users;
    }
}
