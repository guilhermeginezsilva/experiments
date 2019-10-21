package com.baeldung.spring.rsocket.client;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.rsocket.model.MarketData;
import com.baeldung.spring.rsocket.model.MarketDataRequest;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MarketDataRestController {

	private final Random random = new Random();
    private RSocketRequester rSocketRequester;
    private RSocketRequester rSocketRequester2;
	private final RSocketStrategies rSocketStrategies;

	public MarketDataRestController(// RSocketRequester rSocketRequester) {
			RSocketStrategies rSocketStrategies) {
//        this.rSocketRequester = rSocketRequester;
		this.rSocketStrategies = rSocketStrategies;
	}

	@PostConstruct
	public void post() {
		try {
			RSocket block = RSocketFactory.connect()
					.mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
					.frameDecoder(PayloadDecoder.ZERO_COPY).transport(TcpClientTransport.create("127.0.0.1", 7000)).start()
					.block();
			this.rSocketRequester = RSocketRequester.wrap(block, MimeTypeUtils.APPLICATION_JSON, this.rSocketStrategies);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
//		RSocket block2 = RSocketFactory.connect()
//				.mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
//				.frameDecoder(PayloadDecoder.ZERO_COPY).transport(TcpClientTransport.create("127.0.0.1", 7001)).start()
//				.block();
//		this.rSocketRequester2 = RSocketRequester.wrap(block2, MimeTypeUtils.APPLICATION_JSON, this.rSocketStrategies);
	}

	@GetMapping(value = "/current/{stock}")
	public ResponseEntity<MarketData> current(@PathVariable("stock") String stock) throws InterruptedException, ExecutionException {

		System.out.println();
		Mono<MarketData> retrieveMono = rSocketRequester.route("currentMarketData").data(new MarketDataRequest(stock)).retrieveMono(MarketData.class);
		return ResponseEntity.ok(retrieveMono.toFuture().get());
	}
	
	@GetMapping(value = "/current")
	public ResponseEntity<Flux<MarketData>> all() throws InterruptedException, ExecutionException {

		System.out.println();
		Flux<MarketData> retrieveMono = rSocketRequester.route("feedMarketData").data(new MarketDataRequest("")).retrieveFlux(MarketData.class);
		 ResponseEntity<Flux<MarketData>> ok = ResponseEntity.ok(retrieveMono);
		 return ok;
	}

	@GetMapping(value = "/feed/{stock}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<MarketData> feed(@PathVariable("stock") String stock) {

		return rSocketRequester.route("feedMarketData").data(new MarketDataRequest(stock)).retrieveFlux(MarketData.class);
	}

	@GetMapping(value = "/collect")
	public Publisher<Void> collect() {
		RSocketRequester client = RSocketRequester.wrap(
				RSocketFactory.connect().transport(TcpClientTransport.create("127.0.0.1", 7001)).start().block(),
				MimeTypeUtils.APPLICATION_JSON, this.rSocketStrategies);

		return client.route("collectMarketData").data(getMarketData()).send();
	}

	private MarketData getMarketData() {
		return new MarketData("X", random.nextInt(10));
	}
}
