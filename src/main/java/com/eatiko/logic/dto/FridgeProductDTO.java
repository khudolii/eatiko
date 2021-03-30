package com.eatiko.logic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FridgeProductDTO {
    private Long fridgeProductId;
    private Long productId;
    private Long fridgeId;
    private Double weight;
}
