package com.andela.xmlparser.util;

import com.andela.xmlparser.model.XmlFileCatalog;

import java.io.File;

public interface XmlParser {

    XmlFileCatalog parseXML(File xmlFile);
}
