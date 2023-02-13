package com.andela.xmlparser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "xml_file_catalog")
public class XmlFileCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long Id;

    @Column(name = "news_paper_name")
    private String newsPaperName;

    @Column(name= "upload_time")
    private String uploadTime;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "dpi")
    private String dpi;

    public XmlFileCatalog(){}

    public String getNewsPaperName() {
        return newsPaperName;
    }

    public void setNewsPaperName(String newsPaperName) {
        this.newsPaperName = newsPaperName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
}
