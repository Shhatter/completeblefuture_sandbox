package com.example.completeblefuture.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "ID", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(nullable=false)
    private String manufacturer;
    @NonNull
    @Column(nullable=false)
    private String model;
    @NonNull
    @Column(nullable=false)
    private String type;
}