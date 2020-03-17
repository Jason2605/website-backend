package dev.codesupport.web.api.data.repository;

import dev.codesupport.web.api.data.entity.ContributorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorRepository extends JpaRepository<ContributorEntity, Long> {

    //S00100 - Underscores are spring syntax, and are required
    @SuppressWarnings("squid:S00100")
    List<ContributorEntity> findAllByContributorList_Id(Long id);

}
