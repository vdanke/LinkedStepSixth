package org.step.linked.step.service;

import org.step.linked.step.model.PromotionCard;

import java.util.List;

public interface PromotionService {

    PromotionCard findById(String id);

    List<PromotionCard> findNotExpiredPromotion();

    void deleteExpiredPromotion();

    PromotionCard save(PromotionCard promotionCard);
}
