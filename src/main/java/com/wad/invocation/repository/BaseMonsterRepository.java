package com.wad.invocation.repository;

import com.wad.invocation.model.BaseMonster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMonsterRepository extends MongoRepository<BaseMonster, String> {
}
