package com.andela.xmlparser.controller;

import com.andela.xmlparser.model.XmlFileCatalog;
import com.andela.xmlparser.model.XmlFileCatalogResponse;
import com.andela.xmlparser.service.FileUploadService;
import com.andela.xmlparser.util.XmlParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/xml-parser")
@Tag(name = "Xml-Parser", description = "Xml-Parser API")
public class XmlParserController {

    private FileUploadService fileUploadService;

    @Autowired
    public XmlParserController(FileUploadService fileUploadService, XmlParser xmlParser) {
        this.fileUploadService = fileUploadService;
    }

    @Operation(summary = "validate-parse-api", description = "This api validates, parses and stores the content of the xml file into database")
    @PostMapping(path="/file-upload")
    public ResponseEntity<XmlFileCatalogResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        XmlFileCatalogResponse xmlFileCatalogResponse = new XmlFileCatalogResponse();
        try {
            //this is just to store the file in a temp location.
            File file = new File(System.getProperty("java.io.tmpdir") + multipartFile.getName());
            multipartFile.transferTo(file);
            if(this.fileUploadService.validateXmlFile(file)) {
               xmlFileCatalogResponse = this.fileUploadService.parseAndStore(file);
            }
        } catch (Exception ex) {
            xmlFileCatalogResponse.setErrorMessage(ex.getMessage());
        }
        return ResponseEntity.ok(xmlFileCatalogResponse);
    }


    @Operation(summary = "filter-sort-page-api", description = "This api works on the combination of values provided as input and performs filtering, sorting and pagination")
    @GetMapping(path = "/paging")
    public ResponseEntity<XmlFileCatalogResponse> sortAndPage(@RequestParam(defaultValue = "") String filterField,
                                                              @RequestParam(defaultValue = "") List<String> sortList,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        XmlFileCatalogResponse xmlFileCatalogResponse = new XmlFileCatalogResponse();
        try {
            xmlFileCatalogResponse = this.fileUploadService.paging(filterField, sortList, sortOrder.name(),page,size);
        } catch (Exception ex) {
            xmlFileCatalogResponse.setErrorMessage(ex.getMessage());
        }
        return ResponseEntity.ok(xmlFileCatalogResponse);
    }
}
