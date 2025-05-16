package it.milestone.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.milestone.ticket_platform.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
