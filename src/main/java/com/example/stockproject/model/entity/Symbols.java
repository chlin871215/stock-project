package com.example.stockproject.model.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Symbols")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Symbols {
    @XmlElement(name = "Symbol")
    private List<Symbol> symbolList;

}
