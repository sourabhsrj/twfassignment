package com.twfassignment.translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.twfassignment.translator.model.Sentance;
import com.twfassignment.translator.model.Tranlation;
import com.twfassignment.translator.service.TranslateService;

@RestController
public class Translate {
	
	@Autowired
	TranslateService translateService;

	 @PostMapping("/translate")
	    public ResponseEntity<Tranlation> addeStaff( @RequestBody Sentance sentance ) {
	        ResponseEntity<Tranlation> response = null;
	        Tranlation details = null;
	        try {
	         
	            details = translateService.translateToFrench(sentance);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "<DFAPO003> : Internal Server Error");
	        }
	        response = new ResponseEntity<>(details, HttpStatus.OK);
	        return response;
	    }

}
