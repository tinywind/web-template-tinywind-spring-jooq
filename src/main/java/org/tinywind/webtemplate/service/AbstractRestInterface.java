package org.tinywind.webtemplate.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tinywind.webtemplate.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

public abstract class AbstractRestInterface {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRestInterface.class);
    private static final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

    private static HttpComponentsClientHttpRequestFactory getFactory() {
        factory.setConnectTimeout(5 * 1000);
        factory.setReadTimeout(5 * 1000);
        return factory;
    }

    @Value("${application.devel:true}")
    private Boolean devel;

    protected <BODY, RESPONSE> RESPONSE call(String url, BODY o, Class<RESPONSE> klass, HttpMethod method) throws Exception {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            if (!devel) objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            final RestTemplate template = new RestTemplate();
            // final RestTemplate template = new RestTemplate(getFactory());

            @SuppressWarnings("unchecked") final Map<String, Object> params = (Map<String, Object>) objectMapper.convertValue(o, Map.class);
            // template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (method.equals(HttpMethod.GET)) params.forEach(builder::queryParam);
            final URI uri = builder.build().encode().toUri();

            if (logger.isInfoEnabled()) logger.info("[" + method + "] " + uri.toString());
            final ResponseEntity<String> response = method.equals(HttpMethod.GET)
                    ? template.exchange(uri, method, HttpEntity.EMPTY, String.class)
                    : template.exchange(uri, method, new HttpEntity<>(objectMapper.writeValueAsString(params)), String.class);

            return objectMapper.readValue(response.getBody(), klass);
        } catch (HttpStatusCodeException e) {
            logger.info(e.getMessage(), e);

            final int status = e.getStatusCode().value();
            if (status >= 500) {
                throw new Exception("Server error");
            } else if (status >= 400) {
                throw new Exception("Client error");
            } else if (status >= 300) {
                throw new Exception("Url redirect");
            }
            throw new Exception(e.getStatusCode() + ": " + e.getMessage());
        } catch (RestClientException e) {
            logger.info(e.getMessage(), e);
            throw new Exception("검색 시간이 초과되었습니다.");
        }
    }

    public class EmptyResultException extends Exception {
        public EmptyResultException() {
            super("검색 결과가 존재하지 않습니다.");
        }

        public EmptyResultException(String s) {
            super(s);
        }
    }
}
