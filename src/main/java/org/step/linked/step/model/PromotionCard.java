package org.step.linked.step.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@Document(value = "promotion_card")
public class PromotionCard {

    @MongoId
    private String id;
    private String promo;
    private String text;
    private Double discount;
    private String expiredDate;
}
