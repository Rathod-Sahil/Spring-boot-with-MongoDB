package com.first.repository;

import com.first.model.AdminConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminConfigRepository extends MongoRepository<AdminConfig,String> {
}
