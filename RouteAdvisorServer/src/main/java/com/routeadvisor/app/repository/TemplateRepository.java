package com.routeadvisor.app.repository;

import com.routeadvisor.app.domain.Template;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Template entity.
 */
@SuppressWarnings("unused")
public interface TemplateRepository extends MongoRepository<Template,String> {

}
