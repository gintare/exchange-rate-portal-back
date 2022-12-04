package com.portal.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ExchangeRateCurrent {
    @Id
    @SequenceGenerator(name="er_current_id_sequence", sequenceName = "er_current_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "er_current_id_sequence")
    private int id;
    private int CurrencyId;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "currency_fk_id")
    private Currency currency;

}
