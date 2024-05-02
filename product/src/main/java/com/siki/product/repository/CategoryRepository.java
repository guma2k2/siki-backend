package com.siki.product.repository;

import com.siki.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
            select c
            from Category c
            left join fetch c.parent
            where c.id = :id
            """)
    Optional<Category> findByIdCustom(@Param("id") Integer id);

    @Query("""
            select c
            from Category c
            where c.name = :name and (id != null or c.id != :id)
            """)
    Optional<Category> findByIdAndName(@Param("id") Integer id, @Param("name") String name);

    @Query("""
            select c
            from Category c
            left join fetch c.parent
            where c.parent = null
            """)
    List<Category> findCategoryParents();

    @Query("""
            select c
            from Category c
            left join fetch c.parent
            where c.parent.id = :id
            """)
    List<Category> findByParentId(@Param("id") Integer id);

    @Query("""
            select c
            from Category c
            left join fetch c.parent
            left join fetch c.childrenList
            where c.name = :name
            """)
    Optional<Category> findByName(String name);

    @Query("""
            select c
            from Category c
            left join fetch c.parent
            """)
    List<Category> findAll();
}
