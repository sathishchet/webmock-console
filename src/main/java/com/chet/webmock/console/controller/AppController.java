package com.chet.webmock.console.controller;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chet.webmock.console.db.DataSourceListener;
import com.chet.webmock.console.db.DataSourceResponse;
import com.chet.webmock.console.relay.RelayAsync;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(path = "/datasource")
@EnableScheduling
public class AppController {
	@Autowired
	DataSourceListener dataSourceListener;
	@Autowired
	RelayAsync relayAsync;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(path = "/fetch", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ResponseEntity<DataSourceResponse> getDatasourceDetails() {
		return new ResponseEntity<DataSourceResponse>(dataSourceListener.getResponse(), HttpStatus.ACCEPTED);
	}

	@RequestMapping(path = "/request/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ResponseEntity asyncRespose(@PathVariable String id) {
		IntStream.range(0, Integer.valueOf(id)).forEach(relayAsync::getintes);

		System.out.println(id + " T starter " + Thread.currentThread().getName());

		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	// @Scheduled(fixedDelay = 10000)
	public void scheduled() {
		System.out.println("fixedDelay 10000 scheduled");
	}

}
