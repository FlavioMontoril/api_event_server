package com.events.api.domain.dto.coupon;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CouponRequestDTO(
    @NotBlank(message = "O código do cupom é obrigatório")
    @Size(max = 50, message = "O código deve ter no máximo 7 caracteres")
    String code, 
    @Min(value = 1, message = "O desconto mínimo é 1%")
    @Max(value = 20, message = "O desconto máximo é 20%")
    Integer discount,
    @NotNull(message = "A data do evento é obrigatória") 
    LocalDateTime valid
) {

}
