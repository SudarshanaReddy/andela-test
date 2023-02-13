package com.andela.xmlparser.util;

import com.andela.xmlparser.exception.SchemaValidatorException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public interface SchemaValidator {

    default boolean validateUploadedFile(File xsdFile, File xmlFile) throws Exception {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (IOException | SAXException ex) {
            throw new SchemaValidatorException(ex.getMessage());
        }
        return true;
    }
}
