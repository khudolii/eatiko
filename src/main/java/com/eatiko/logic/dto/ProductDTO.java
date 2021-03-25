package com.eatiko.logic.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductDTO {
    private Long productId;
    private Long name;
}
