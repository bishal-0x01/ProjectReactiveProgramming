package com.reactive.pro;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class ProApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test(){
		System.out.println("Started Testing for Reactive Programming");

		Mono<String> monoString = Mono.just("Hello, Reactive Programming with Mono!");
		Flux<String> fluxString = Flux.just("Hello", "Reactive", "Programming", "with", "Flux!");

		fluxString.subscribe(new CoreSubscriber<String>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("subscribe the flux publisher");
				subscription.request(2);
			}

			@Override
			public void onNext(String s) {
				System.out.println("data recieved from publisher: " + s);
				System.out.println("data processing done");
				System.out.println("requesting for more data");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("error occured: " + throwable.getMessage());
				System.out.println("data processing failed");
			}

			@Override
			public void onComplete() {
				System.out.println("data processing completed");
			}
		});

		monoString.subscribe(new CoreSubscriber<String>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("subscribe the mono publisher");
				subscription.request(2);
			}

			@Override
			public void onNext(String s) {
				System.out.println("data recieved from publisher: " + s);
				System.out.println("data processing done");
				System.out.println("requesting for more data");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("error occured: " + throwable.getMessage());
				System.out.println("data processing failed");
			}

			@Override
			public void onComplete() {
				System.out.println("data processing completed");
			}
		});
	}

}
