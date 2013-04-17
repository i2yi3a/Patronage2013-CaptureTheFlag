package com.blstream.patronage.ctf.repository;

import com.blstream.patronage.ctf.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Named;



@Named("gameRepository")
public interface GameRepository extends MongoRepository<Game, String> {

}
