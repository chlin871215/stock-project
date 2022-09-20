package com.example.stockproject.model.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Symbol {
    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "shortname")
    private String shortname;
    @XmlAttribute(name = "dealprice")
    private String dealprice;
    @XmlAttribute(name = "mtype")
    private String mtype;
}
