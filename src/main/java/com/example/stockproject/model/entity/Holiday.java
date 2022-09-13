package com.example.stockproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "holiday")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    @Id
    @Column(name = "holiday")
    private String holiday;
}
