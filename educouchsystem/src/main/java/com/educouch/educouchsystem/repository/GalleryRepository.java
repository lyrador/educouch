package com.educouch.educouchsystem.repository;

import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.util.exception.ItemNotFoundException;
import com.educouch.educouchsystem.util.exception.LocationOccupiedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {


}
