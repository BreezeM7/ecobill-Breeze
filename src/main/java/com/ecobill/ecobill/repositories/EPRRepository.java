package com.ecobill.ecobill.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecobill.ecobill.domain.entities.EPREntity;
import java.util.List;
public interface EPRRepository extends JpaRepository<EPREntity, Long> {
      List<EPREntity> findAllByNameContainingIgnoreCase(String name);
      EPREntity findByName(String name);
      List<EPREntity> findAllByCategoryIgnoreCase(String category);

      Optional<EPREntity> findByCommercialRegister(Long commercialRegister);

}
