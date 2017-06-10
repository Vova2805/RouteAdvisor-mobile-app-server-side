package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Note entity.
 */
@SuppressWarnings("unused")
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByDayId(String dayId);
}
