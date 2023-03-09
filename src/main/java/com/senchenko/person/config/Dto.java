package com.senchenko.person.config;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JacksonAnnotationsInside
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public @interface Dto {
}
