package com.engineeringdigest.journalApp.cache;

import com.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import com.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {


    public enum keys {
        WEATHER_API;
    }


    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    @PostConstruct   //using this annotation when the bean is created of this class this method will be invoked
    public void init() {
        appCache = new HashMap<>();
        // first we load all the data from the mongodb database then stored in a list
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();

        // now we're storing key and values of this data into the Map which will work as
        // the cache of the application it will load only when the application will start
        // using this we don't need to get data again and again from database so it will become faster

        for(ConfigJournalAppEntity configJournalAppEntity:all) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
