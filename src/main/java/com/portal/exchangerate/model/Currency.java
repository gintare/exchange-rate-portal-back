package com.portal.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Currency {
    @Id
    @SequenceGenerator(name="currency_id_sequence", sequenceName = "currency_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_sequence")
    private int id;
    private String name;
    private String descriptionLt;
    private String descriptionEn;
    private String isoNumber;
    private int exponentUnits;
/*
    @OneToMany(mappedBy = "currencies")
    @JsonManagedReference
    List<ExchangeRateCurrent> exchangeRateCurrent;*/
}
