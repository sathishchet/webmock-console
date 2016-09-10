package com.chet.webmock.console.relay;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAsync
@Component
public class RelayAsync {
	static int count;
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private RelayRepository relayRepository;

	@Async
	public synchronized Future<Integer> getintes(int x) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Relay relay = new Relay();
		relay.setCount(count);
		relay.setName(Thread.currentThread().getName());
		relay.setRand(ThreadLocalRandom.current().nextInt(100));
		try {
			relayRepository.save(relay);
			template.convertAndSend("/topic/relay", mapper.writeValueAsString(relay));
			System.out.println(Thread.currentThread().getName() + " count " + ++count);

		} catch (MessagingException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AsyncResult<Integer>(count);

	}
}
