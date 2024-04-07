package org.goosenvi.exercises;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

public class MicroClient {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(MicroClient.class);

    /**
     * The web client to use.
     */
    private WebClient webClient = null;

    /**
     * The web client builder.
     */
    protected Builder webClientBuilder = null;

    /**
     * Initializes the web client.
     */
    public void init() {

        this.webClientBuilder = WebClient.builder();
        webClientBuilder.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(200 * 1024 * 1024));

        HttpClient httpClient = HttpClient.create();
        httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
        webClient = this.webClientBuilder.baseUrl("").build();
    }

    /**
     * Retrieves data from the specified endpoint.
     *
     * @param endpoint the endpoint to call
     * @return the content that the endpoint sends back
     */
    public String getDataFromEndpoint(String endpoint) {
        log.info("Retrieving GET data from endpoint: " + endpoint);
        return this.webClient.get().uri(endpoint).retrieve().bodyToMono(String.class).block();
    }
}