package com.andela.xmlparser.service;

import com.andela.xmlparser.exception.SchemaValidatorException;
import com.andela.xmlparser.model.XmlFileCatalogResponse;
import com.andela.xmlparser.repository.XmlFileCatalogRepo;
import com.andela.xmlparser.util.SchemaValidator;
import com.andela.xmlparser.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final XmlFileCatalogRepo xmlFileCatalogRepo;
    private final ResourceLoader resourceLoader;
    private final SchemaValidator schemaValidator;
    private final XmlParser xmlParser;

    @Autowired
    public FileUploadServiceImpl(final ResourceLoader resourceLoader,
                                 final XmlFileCatalogRepo xmlFileCatalogRepo,
                                 final SchemaValidator schemaValidator,
                                 final XmlParser xmlParser) {
        this.xmlFileCatalogRepo = xmlFileCatalogRepo;
        this.resourceLoader = resourceLoader;
        this.schemaValidator = schemaValidator;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean validateXmlFile(File xmlFile) {
        try {
            //final Resource resource = this.resourceLoader.getResource("classpath:/xsd/news-paper.xsd");
            ClassPathResource classPathResource = new ClassPathResource("static/xsd/news-paper.xsd");
            File xsdSchemaFile = new File("news-paper-new.xsd");
            FileUtils.copyInputStreamToFile(classPathResource.getInputStream(),xsdSchemaFile);
            return this.schemaValidator.validateUploadedFile(xsdSchemaFile, xmlFile);
        } catch (Exception ex) {
           throw new SchemaValidatorException(ex.getMessage());
        }
    }

    @Override
    public XmlFileCatalogResponse parseAndStore(File xmlFile) {
        XmlFileCatalogResponse xmlFileCatalogResponse = new XmlFileCatalogResponse();
        var xmlFileCatalog = this.xmlParser.parseXML(xmlFile);
        xmlFileCatalogResponse.setXmlFileCatalog(Arrays.asList(this.xmlFileCatalogRepo.save(xmlFileCatalog)));
        return xmlFileCatalogResponse;
    }

    @Override
    public XmlFileCatalogResponse paging(String filterFieldName, List<String> sortList, String sortOrder, int page, int size) {
        XmlFileCatalogResponse xmlFileCatalogResponse = new XmlFileCatalogResponse();
        if(filterFieldName.isEmpty() && !sortList.isEmpty()) {
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
            xmlFileCatalogResponse.setXmlFileCatalog(this.xmlFileCatalogRepo.findAll(pageRequest).getContent());
        } else if(!filterFieldName.isEmpty() && sortList.isEmpty()) {
            Sort sort = Sort.by(sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, "newsPaperName");
            Pageable pageRequest = PageRequest.of(page, size,sort);
            xmlFileCatalogResponse.setXmlFileCatalog(this.xmlFileCatalogRepo.findByNewsPaperName(filterFieldName));
        } else if(!filterFieldName.isEmpty() && !sortList.isEmpty()) {
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
            xmlFileCatalogResponse.setXmlFileCatalog(this.xmlFileCatalogRepo.findByNewsPaperNameAndPage(filterFieldName, pageRequest));
        } else {
            Sort sort = Sort.by((sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), "newsPaperName");
            Pageable pageRequest = PageRequest.of(page, size, sort);
            xmlFileCatalogResponse.setXmlFileCatalog(this.xmlFileCatalogRepo.findAll(pageRequest).getContent());
        }
        return xmlFileCatalogResponse;
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
