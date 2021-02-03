package org.step.linked.step.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PromotionCardSaveRequest {

    @NotBlank
    @Size(min = 15, max = 120)
    private String promo;
    @NotBlank
    @Size(min = 15)
    private String text;
    @NotNull
    private Double discount;
}
