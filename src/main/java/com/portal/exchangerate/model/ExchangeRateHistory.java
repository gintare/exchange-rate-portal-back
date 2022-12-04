package com.portal.exchangerate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
//@EqualsAndHashCode
public class ExchangeRateHistory {
    @Id
    @SequenceGenerator(name="er_history_id_sequence", sequenceName = "er_history_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "er_history_id_sequence")
    private int id;
    private int currencyId;
    private Date currencyDate;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "currency_fk_id")
    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateHistory history = (ExchangeRateHistory) o;
        return Objects.equals(currencyDate, history.currencyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyDate);
    }
}
