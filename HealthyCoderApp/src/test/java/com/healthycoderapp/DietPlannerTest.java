package com.healthycoderapp;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class DietPlannerTest {

	private DietPlanner dietPlanner; 
	
	
	//using the "@BeforeEach" annotation will instantiate an object each time we run the test
	// this code will make us a new DietPlanner object with these inital values each time we run this test class
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50); 
	}
	//we've used '@BeforeEach' before, but there is also '@AfterEach' (which is used less often) to do something after each test: 	
	@AfterEach
	void afterEach() {
		System.out.println("A unit test was finished!");
	}
	
	@Test
	void should_ReturnCorrectDietPlan_When_CorrectCoder() {
		// given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275); 
		
		
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		
		// then
		// we might be tempted to just put an 'assertEquals', but this won't work 
		// b/c these are 2 separate objects in memory
//		assertEquals(expected, actual);

		//instead, we need to compare what's in the objects using 'assertAll':
		assertAll(
				() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getProtein(), actual.getProtein()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())				
		);
		
	}

	//if we had a method that generated random values, 
	// or we used multiple threads or something, we might want to run the test
	// multiple times, to do that we can use the @RepeatedTest annotation: 
	
	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	void should_ReturnCorrectDietPlan_When_CorrectCoder_RepeatedTest() {
		// given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275); 
		
		
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		
		// then
		// we might be tempted to just put an 'assertEquals', but this won't work 
		// b/c these are 2 separate objects in memory
//		assertEquals(expected, actual);

		//instead, we need to compare what's in the objects using 'assertAll':
		assertAll(
				() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getProtein(), actual.getProtein()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())				
		);
		
	}
	
}
