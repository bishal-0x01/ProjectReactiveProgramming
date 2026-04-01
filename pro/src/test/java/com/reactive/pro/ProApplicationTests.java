package com.reactive.pro;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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


	@Test
	void MonoLearning(){

		Mono<Object> error = Mono.error(new RuntimeException("Error !!!"));
		Mono<Object> m1 = Mono.just("Learn with Me!!").log().then(error);
		m1.subscribe(System.out::println);
		//ignore the response of this mono and return the error


		Mono<String> m2 = Mono.just("Learn with Me!!").log().thenReturn("Mono then return");
		m2.subscribe(System.out::println);


		Mono<String> m3 = Mono.just("Learn with Me!!");
		Mono<String> m4 = Mono.just("Subscribe the channel");
		Mono<Integer> m5 = Mono.just(100);
		Mono<Boolean> m6 = Mono.just(true);

		Mono<Tuple2<String, String>> zip = Mono.zip(m3, m4);
		zip.subscribe(tuple -> {
			System.out.println("Tuple 1: " + tuple.getT1());
			System.out.println("Tuple 2: " + tuple.getT2());
		});

		Mono<Tuple2<Tuple2<String, String>, Integer>> zip1 = Mono.zip(zip, m5);

		zip1.subscribe(tuple -> {
			System.out.println("Tuple 1: " + tuple.getT1().getT1());
			System.out.println("Tuple 2: " + tuple.getT1().getT2());
			System.out.println("Tuple 3: " + tuple.getT2());
		});

		Mono<Tuple2<Tuple2<Tuple2<String, String>, Integer>, Boolean>> tuple2Mono = zip1.zipWith(m6);

		tuple2Mono.subscribe(tuple -> {
			System.out.println("Tuple 1: " + tuple.getT1().getT1().getT1());
			System.out.println("Tuple 2: " + tuple.getT1().getT1().getT2());
			System.out.println("Tuple 3: " + tuple.getT1().getT2());
			System.out.println("Tuple 4: " + tuple.getT2());
		});

		Mono<String> m7 = m3.map(String::toUpperCase).onErrorReturn("Default Value");
		m7.subscribe(System.out::println);

		Mono<String[]> mono = m3.flatMap(data -> Mono.just(data.split(" ")));
		mono.subscribe(array -> {
			for (String s : array) {
				System.out.println(s);
			}
		});

		Flux<String> stringFlux = m3.flatMapMany(data -> Flux.just(data.split(" "))).log();
		stringFlux.subscribe(System.out::println);


	}

}
