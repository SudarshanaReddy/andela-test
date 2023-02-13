package com.andela.xmlparser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class XmlFileCatalogResponse {

    private String errorMessage;
    private List<XmlFileCatalog> xmlFileCatalog;
}
