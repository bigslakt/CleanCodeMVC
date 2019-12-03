package com.iths.robinhansson.cleancode.lab2.config;

import com.iths.robinhansson.cleancode.lab2.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;


@Configuration
public class Config {

    @Bean
    public IProductStorage getProductStorage() {
        return new Inventory(mongoTemplate());
    }

    @Bean
    public MongoTemplate mongoTemplate(){

        return new MongoTemplate(new SimpleMongoClientDbFactory("mongodb://localhost:27017/accountDatabase"));
    }
}
