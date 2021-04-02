package com.eatiko.logic.dto;

import com.eatiko.logic.model.FridgeProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FridgeDTO {

    private Long fridgeId;
    @NotEmpty(message = "Please, enter fridge name")
    private String fridgeName;
    private Set<Long> productIds = new HashSet<>();
    private List<FridgeProductDTO> fridgeProducts = new ArrayList<>();
    private List<RecipeDTO> recipeDTOList  = new ArrayList<>();
    private ACLUserDTO aclUserDTO;

}
