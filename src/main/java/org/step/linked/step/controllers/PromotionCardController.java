package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.linked.step.configuration.security.assets.AllowAdmin;
import org.step.linked.step.dto.request.PromotionCardSaveRequest;
import org.step.linked.step.dto.response.PromotionCardSaveResponse;
import org.step.linked.step.model.PromotionCard;
import org.step.linked.step.service.PromotionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionCardController {

    private final PromotionService promotionService;

    @AllowAdmin
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PromotionCardSaveResponse> savePromotionCard(@Valid @RequestBody PromotionCardSaveRequest request) {
        PromotionCard card = PromotionCard.builder()
                .text(request.getText())
                .promo(request.getPromo())
                .discount(request.getDiscount())
                .build();

        PromotionCard promotionCard = promotionService.save(card);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new PromotionCardSaveResponse(promotionCard.getId()));
    }
}
