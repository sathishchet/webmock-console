package com.chet.webmock.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.chet.webmock.console.db.DataSourceListener;
import com.chet.webmock.console.db.DataSourceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class PageController extends WebMvcConfigurerAdapter {
	@Autowired
	DataSourceListener dataSourceListener;

	@RequestMapping("/greeting")
	public String greeting() {
		return "index.";
	}

	@RequestMapping(path = "/request")
	public String asyncRequest() {
		return "relay.html";
	}

	@RequestMapping(value = "/msg")
	@SendTo("/topic/greetings")
	public ResponseEntity<DataSourceResponse> greetingMsg() throws MessagingException, JsonProcessingException {
		return new ResponseEntity<DataSourceResponse>(dataSourceListener.publishUpdates(), HttpStatus.ACCEPTED);
	}

}
