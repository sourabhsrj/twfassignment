package com.twfassignment.translator.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twfassignment.translator.model.Sentance;
import com.twfassignment.translator.model.Tranlation;

@Service
public class TranslateService {
	
	


	public Tranlation translateToFrench(Sentance sentance) throws IOException, InterruptedException {
		

		Tranlation t=new Tranlation();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
				.header("content-type", "application/x-www-form-urlencoded")
				.header("Accept-Encoding", "application/gzip")
				.header("X-RapidAPI-Key", "c84d2f78a6msh2e47ae52a150810p179c7ejsn85cebac86837")
				.header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
				.method("POST", HttpRequest.BodyPublishers.ofString("q="+sentance.getText()+"%2C%20world!&target=fr&source=en"))
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
	
		String str=response.body();
		
		
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(str);
        String translatedTextInFrench=null;
        // Extract the translated text
        JsonNode translationsNode = jsonNode.path("data").path("translations");
        if (translationsNode.isArray() && translationsNode.size() > 0) {
            JsonNode translatedText = translationsNode.get(0).path("translatedText");
            if (translatedText.isTextual()) {
            	translatedTextInFrench = translatedText.asText();
                System.out.println(translatedTextInFrench);
            } else {
                System.out.println("Translated text not found in the expected format.");
            }
        } else {
            System.out.println("No translations found in the JSON.");
        }
		
		
		t.setTranslation(translatedTextInFrench);
		
		return t;
	}

	
}
