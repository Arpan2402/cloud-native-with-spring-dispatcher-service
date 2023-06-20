package com.arpanm.dispatcherservice;

import com.arpanm.dispatcherservice.dto.OrderAcceptedDto;
import com.arpanm.dispatcherservice.dto.OrderDispatchedDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinder;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class DispatcherServiceApplicationTests {

	@Autowired
	InputDestination input;

	@Autowired
	OutputDestination output;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testProcessingUsingTestBinder() throws IOException {
		long orderId = 12345l;
		Message<OrderAcceptedDto> in = MessageBuilder.withPayload(new OrderAcceptedDto(orderId)).build();
		Message<OrderDispatchedDto> out = MessageBuilder.withPayload(new OrderDispatchedDto(orderId)).build();

		this.input.send(in);
		Assertions.assertThat(objectMapper.readValue(this.output.receive().getPayload(), OrderDispatchedDto.class))
				.isEqualTo(out.getPayload());
	}
}
