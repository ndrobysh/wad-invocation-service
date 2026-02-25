package com.wad.invocation.repository;

import com.wad.invocation.model.InvocationLog;
import com.wad.invocation.model.InvocationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvocationLogRepository extends MongoRepository<InvocationLog, String> {
    List<InvocationLog> findByStatus(InvocationStatus status);
    List<InvocationLog> findByUsername(String username);
}
