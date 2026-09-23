package com.laboratorio.springboot24.unit;

import com.laboratorio.springboot24.dto.ProductoResponse;

import java.time.LocalDate;

public class DataProvider {

    public static ProductoResponse productoResponse() {
        return new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.now()
        );
    }
}
