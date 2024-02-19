package org.jrotero.coches.repositories;

import org.jrotero.coches.models.CocheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocheRepository extends JpaRepository<CocheEntity, Long> {

}
