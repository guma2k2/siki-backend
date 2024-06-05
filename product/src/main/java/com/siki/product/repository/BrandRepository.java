package com.siki.product.repository;

import com.siki.product.model.BaseProduct;
import com.siki.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("select distinct br from Brand br join fetch br.productList pds where pds in :baseProducts")
    List<Brand> findDistinctBrandInListBaseProduct(@Param("baseProducts") List<BaseProduct> baseProducts);
}
