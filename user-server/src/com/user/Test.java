package com.user;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.common.util.Jackson;

public class Test {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(Jackson.getDefaultObjectMapper().writeValueAsString("qwqewq"));
	}

}
