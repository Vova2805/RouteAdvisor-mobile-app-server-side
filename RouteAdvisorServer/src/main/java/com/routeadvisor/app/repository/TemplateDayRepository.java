package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.TemplateDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data MongoDB repository for the TemplateDay entity.
 */
@SuppressWarnings("unused")
public interface TemplateDayRepository extends MongoRepository<TemplateDay, String> {

    List<TemplateDay> findByTemplateId(String templateId);
}
