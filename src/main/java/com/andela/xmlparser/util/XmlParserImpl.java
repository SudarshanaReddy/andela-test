package com.andela.xmlparser.util;

import com.andela.xmlparser.model.XmlFileCatalog;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    public XmlFileCatalog parseXML(File xmlFile) {
        XmlFileCatalog xmlFileCatalog = new XmlFileCatalog();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            var screenInfoNodeMap =  doc.getDocumentElement().getElementsByTagName("screenInfo").item(0).getAttributes();
            var newsPaperName = doc.getDocumentElement().getElementsByTagName("newspaperName").item(0).getTextContent();
            //map the fields
            xmlFileCatalog.setWidth(screenInfoNodeMap.getNamedItem("width").getTextContent());
            xmlFileCatalog.setHeight(screenInfoNodeMap.getNamedItem("height").getTextContent());
            xmlFileCatalog.setDpi(screenInfoNodeMap.getNamedItem("dpi").getTextContent());
            xmlFileCatalog.setNewsPaperName(newsPaperName);
            xmlFileCatalog.setUploadTime(LocalTime.now().toString());
            xmlFileCatalog.setFileName(xmlFile.getName());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return xmlFileCatalog;
    }

}
