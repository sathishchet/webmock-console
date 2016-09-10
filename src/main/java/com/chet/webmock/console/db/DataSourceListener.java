package com.chet.webmock.console.db;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataSourceListener {
	private static final Logger DATA_SOURCE_LISTENER = Logger.getLogger(DataSourceListener.class.toString());
	private static final Path PATH = Paths.get("");
	private static final String FILE = "db.yml";
	@Autowired
	private SimpMessagingTemplate template;
	ObjectMapper mapper = new ObjectMapper();

	public DataSourceResponse publishUpdates() throws MessagingException, JsonProcessingException {
		DataSourceResponse dataSourceResponse = getResponse();
		if (dataSourceResponse != null)
			template.convertAndSend("/topic/greeting", mapper.writeValueAsString(dataSourceResponse));
		return dataSourceResponse;
	}

	@PostConstruct
	public void init() throws IOException {
		if (exists()) {
			Runnable watchThread = new Runnable() {
				@Override
				public void run() {
					try {
						WatchService watchService = FileSystems.getDefault().newWatchService();
						PATH.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

						while (true) {
							WatchKey key = null;
							key = watchService.take();
							List<WatchEvent<?>> events = key.pollEvents();
							// getResponse();
							publishUpdates();
							if (events != null) {
								key.reset();
							}
						}
					} catch (IOException | InterruptedException e) {
						DATA_SOURCE_LISTENER.log(Level.ALL, e.getMessage());
					}
				}
			};
			new Thread(watchThread).start();
		}
	}

	private static boolean exists() {
		return Files.exists(PATH);
	}

	public DataSourceResponse getResponse() {
		DataSourceResponse datasource = null;
		if (exists()) {
			try {
				Yaml yaml = new Yaml();
				datasource = yaml.loadAs(Files.newInputStream(PATH.resolve(FILE)), DataSourceResponse.class);
				if (datasource != null) {
					//System.out.println(datasource);
				}
			} catch (Exception e) {
				DATA_SOURCE_LISTENER.log(Level.ALL, e.getMessage());
			}
		}
		return datasource;
	}
}
