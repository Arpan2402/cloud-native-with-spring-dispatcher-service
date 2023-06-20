package com.arpanm.dispatcherservice.function;

import com.arpanm.dispatcherservice.dto.OrderAcceptedDto;
import com.arpanm.dispatcherservice.dto.OrderDispatchedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
@Slf4j
public class DispatcherServiceFunctions {

    @Bean
    public Function<OrderAcceptedDto, Long> pack() {
        return orderAcceptedDto -> {
            log.info("Packing order - {}", orderAcceptedDto);
            return orderAcceptedDto.getOrderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedDto>> label() {
        return orderFlux -> orderFlux.map(orderId -> {
            log.info("Labelling order with id - {}", orderId);
            return new OrderDispatchedDto(orderId);
        });
    }
}
