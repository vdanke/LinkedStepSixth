package org.step.linked.step.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.step.linked.step.exception.NotFoundException;
import org.step.linked.step.model.PromotionCard;
import org.step.linked.step.repository.PromotionCardRepository;
import org.step.linked.step.service.PromotionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionCardServiceImpl implements PromotionService {

    private final PromotionCardRepository promotionCardRepository;

    @Override
    public PromotionCard findById(String id) {
        return promotionCardRepository.findById(id)
                .filter(this::validatePromotion)
                .orElseThrow(
                () -> new NotFoundException(String.format("Promotion card with ID %s not found", id))
        );
    }

    @Override
    public List<PromotionCard> findNotExpiredPromotion() {
        return promotionCardRepository.findAll()
                .stream()
                .filter(this::validatePromotion)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExpiredPromotion() {
        List<PromotionCard> promotionCards = promotionCardRepository.findAll()
                .stream()
                .filter(pc -> !this.validatePromotion(pc))
                .collect(Collectors.toList());

        promotionCardRepository.deleteAll(promotionCards);
    }

    @Override
    public PromotionCard save(PromotionCard promotionCard) {
        promotionCard.setId(UUID.randomUUID().toString());
        promotionCard.setExpiredDate(LocalDateTime.now().plusDays(7).toString());
        return promotionCardRepository.save(promotionCard);
    }

    private boolean validatePromotion(PromotionCard promotionCard) {
        LocalDateTime expiredDate = LocalDateTime.parse(promotionCard.getExpiredDate());
        return LocalDateTime.now().isBefore(expiredDate);
    }
}
