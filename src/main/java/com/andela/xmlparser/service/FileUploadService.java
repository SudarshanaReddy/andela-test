package com.andela.xmlparser.service;

import com.andela.xmlparser.model.XmlFileCatalog;
import com.andela.xmlparser.model.XmlFileCatalogResponse;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.List;

public interface FileUploadService {

    boolean validateXmlFile(File xmlFile);

    XmlFileCatalogResponse parseAndStore(File xmlFile);

    XmlFileCatalogResponse paging(String filterField, List<String> sortList, String sortOrder, int page, int size);
}
