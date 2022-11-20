package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOwnedRepository extends JpaRepository<ItemOwned, Long> {


}
