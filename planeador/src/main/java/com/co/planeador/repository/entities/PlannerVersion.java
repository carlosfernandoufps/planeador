package com.co.planeador.repository.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "planner_version")
public class PlannerVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "is_default_version")
    private boolean defaultVersion;

    @ElementCollection
    @CollectionTable(
            name = "column_description_map",
            joinColumns = @JoinColumn(name = "planner_version_id")
    )
    @OrderColumn(name = "column_order")
    private List<ColumnDefinition> columnDefinitions = new ArrayList<>();

    @Embeddable
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ColumnDefinition {
        @Column(name = "column_name", nullable = false)
        private String name;

        @Column(name = "column_description", length = 2000)
        private String description;
    }

    public int getNumberOfColumns(){
        return this.columnDefinitions.size();
    }

}
