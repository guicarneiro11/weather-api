package com.guicarneirodev.weatherapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "favorite_cities")
@Getter
@ToString(exclude = "weatherData")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Construtor protegido para JPA
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Construtor privado para Builder
@Builder
public class FavoriteCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default // Marca o campo com valor default para o Builder
    @OneToMany(mappedBy = "favoriteCity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeatherData> weatherData = new ArrayList<>();

    // Construtor público para uso normal
    public FavoriteCity(String cityName, String userId) {
        this.cityName = Objects.requireNonNull(cityName, "City name cannot be null");
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.weatherData = new ArrayList<>();
    }

    // Métodos de modificação
    public void setCityName(String cityName) {
        this.cityName = Objects.requireNonNull(cityName, "City name cannot be null");
    }

    public void addWeatherData(WeatherData data) {
        weatherData.add(data);
    }
}