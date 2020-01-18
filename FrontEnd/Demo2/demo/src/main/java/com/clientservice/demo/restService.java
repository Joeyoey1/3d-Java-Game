package com.clientservice.demo;

import com.clientservice.demo.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class restService {

    static final String serverURL = "http://coms-309-jr-4.misc.iastate.edu:8080/players";
    RestTemplate restTemplate = new RestTemplate();

    public String updateList(String id, String health){
        User u = new User("test");
        u.setHealth(Integer.parseInt(health));
        String userURL = serverURL + "/" + id;
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> request = new HttpEntity<>(u, headers);
            restTemplate.put(userURL, request, new Object[]{});
        }
        catch (RestClientException e){
            System.out.println(e);

        }
        return restTemplate.getForObject(serverURL, String.class);
    }

    public int sendLogin(String name){
        User u = new User(name);
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> request = new HttpEntity<>(u, headers);
            restTemplate.postForObject(serverURL, request, User.class);
        }
        catch (RestClientException e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }



}
