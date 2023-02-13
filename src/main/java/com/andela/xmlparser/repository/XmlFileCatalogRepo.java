package com.andela.xmlparser.repository;

import com.andela.xmlparser.model.XmlFileCatalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XmlFileCatalogRepo extends JpaRepository<XmlFileCatalog, Long> {

    @Query(value = "SELECT * FROM xml_file_catalog x where x.news_paper_name = ?1", nativeQuery = true)
    List<XmlFileCatalog> findByNewsPaperNameAndPage(String newsPaperName, Pageable pageable);

    @Query(value = "SELECT * FROM xml_file_catalog x where x.news_paper_name = ?1", nativeQuery = true)
    List<XmlFileCatalog> findByNewsPaperName(String newsPaperName);
}
