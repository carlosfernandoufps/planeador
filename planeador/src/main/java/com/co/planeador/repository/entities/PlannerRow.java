package com.co.planeador.repository.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner_row")
public class PlannerRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "planner_id")
    private Planner planner;

    @ElementCollection
    @CollectionTable(name = "planner_row_cell", joinColumns = @JoinColumn(name = "planner_row_id"))
    @Column(name = "cell_value")
    @OrderColumn(name = "cell_order")
    private List<String> cells = new ArrayList<>();


    @PrePersist
    @PreUpdate
    private void validateNumberOfCells(){
        if (cells.size() != planner.getPlannerVersion().getNumberOfColumns()) {
            throw new IllegalStateException("Número de celdas inválido");
        }
    }

}
