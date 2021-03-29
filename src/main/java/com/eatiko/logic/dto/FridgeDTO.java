package com.eatiko.logic.dto;

import com.eatiko.logic.model.FridgeProduct;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
public class FridgeDTO {

    private String fridgeId;
    @NotEmpty(message = "Please, enter fridge name")
    private String fridgeName;
    private Set<Long> productIds = new HashSet<>();
    private List<FridgeProduct> fridgeProducts = new ArrayList<>();
    private List<RecipeDTO> recipeDTOList;

}
