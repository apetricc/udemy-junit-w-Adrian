package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

class BMICalculatorTest {
	
	//there is also @BeforeAll which is used to run operations that are too expensive to be run before each unit test case, 
	// for example: setting up connection to a DB, or starting servers etc.; this code is way to simple to need it, 
	// so we're just going to print something to the console
	
	@BeforeAll    
	static void beforeAll() {
		System.out.println("Before all unit tests."); 
	}
	
	//@AfterAll is the opposite of @BeforeAll, so we could use it to close a DB connection or shut down a server, 
	// we would use these to run things only once 
	@AfterAll
	static void afterAll() {
		System.out.println("After all unit tests.");
	}

	@Test
	void should_ReturnTrue_When_DietRecommended() {
		//given   aka 'arrange'
		double weight = 89.0;
		double height = 1.72;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		
		// then   aka 'assert'
		assertTrue(recommended);  // using  a boolean like this makes things more clear and easier to read for later/someone else reading the code;
//		assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));  // our old version, less readable; 
	}
	
	/** 
	 * our above test only tests one specific test case; if we wanted to test more scenarios, we could write more 
	 * tests with slightly different parameters, but this would not be a good practice or efficient. 
	 * instead, we can use the @ParameterizedTest annotation like below:
	 * 
	 * to auto-magically insert the values to use, we can use the @ValueSource annotation and provide some values to use,
	 * we also seem to have to pass in a value in the method signature.
	 */
	
	@ParameterizedTest
	@ValueSource(doubles = {89.0, 95.0, 110.0})      
	void should_ReturnTrue_When_DietRecommended_Parameterized_SingleValue(Double coderWeight) {
		//given   aka 'arrange'
		double weight = coderWeight;
		double height = 1.72;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// then   aka 'assert'
		assertTrue(recommended); 
	}
	
	
	//we can pass multiple values with the @CsvSource, with which we have to say value = {"string values", "more values"}
	@ParameterizedTest
	@CsvSource(value = {"89.0, 1.72",  "95.0, 1.75",  "110.0, 1.78"})      
	void should_ReturnTrue_When_DietRecommended_Parameterized_MultipleValues(Double coderWeight, Double coderHeight) {
		//given   aka 'arrange'
		double weight = coderWeight;
		double height = coderHeight;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// then   aka 'assert'
		assertTrue(recommended); 
	}
	
	//we can also make it easier to read (in the junit run window for this method) by stating the names of the values in relation to each other like so: 
	
	@ParameterizedTest(name = "weight={0}, height={1}")
	@CsvSource(value = {"89.0, 1.72",  "95.0, 1.75",  "110.0, 1.78"})      
	void should_ReturnTrue_When_DietRecommended_Parameterized_MultipleValues_Named(Double coderWeight, Double coderHeight) {
		//given   aka 'arrange'
		double weight = coderWeight;
		double height = coderHeight;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// then   aka 'assert'
		assertTrue(recommended); 
	}

	//we can also import values from an actual CSV file ( CSV == 'column separated rows') 
	// we just have to change the one line where we specify the source of the values;
	// we copied the file to src/test/resources, and this is the default location junit will look, 
	// so we just have to give the "/" and the file name, we also specify to skip the first line b/c
	// that has the field names instead of values; 
	
	@ParameterizedTest(name = "weight={0}, height={1}")
	@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
	void should_ReturnTrue_When_DietRecommended_Parameterized_MultipleValues_FromFile(Double coderWeight, Double coderHeight) {
		//given   aka 'arrange'
		double weight = coderWeight;
		double height = coderHeight;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		// then   aka 'assert'
		assertTrue(recommended); 
	}	
	
	
	@Test
	void should_ReturnFalse_When_DietNotRecommended() {
		//given   aka 'arrange'
		double weight = 50.0;
		double height = 1.92;
		
		//when   aka 'act'
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		
		// then   aka 'assert'
		assertFalse(recommended);

	}

	
	@Test
	void should_ThrowArithmeticException_When_HeightZero() {
		//given   aka 'arrange'
		double weight = 50.0;
		double height = 0.0;
		
		//when   aka 'act'
		// if we try to use a boolean (or any kind of variable that calls the method and will throw an exception), 
		// it will throw the exception and never get the 'assert' section of our test, 
		// therefore we need to use an executable! 
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
		
		
		// then   aka 'assert'
		assertThrows(ArithmeticException.class, executable);

	}
	
	@Test
	void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));
		coders.add(new Coder(1.82, 64.7));
		
		
		// when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
				
		
		// then
//		assertEquals(1.85, coderWorstBMI.getHeight());  // this fails, and then we don't know if the next test got done; here we need an 'assertAll' call; 
//		assertEquals(98.5, coderWorstBMI.getWeight());
		
		//this way we can see that both tests have failed;
//		assertAll (
//			() -> assertEquals(1.85, coderWorstBMI.getHeight()),
//			() -> assertEquals(98.5, coderWorstBMI.getWeight())
//		);   
		
		assertAll (
				() -> assertEquals(1.82, coderWorstBMI.getHeight()),
				() -> assertEquals(98.0, coderWorstBMI.getWeight())
			);   
	}
	
	//if we need to test how long our test is taking, or a method is taking, we 
	// can put a test depending on the time it takes like this-->
	// this could really be useful for testing an API and making sure it 
	// does its work within a given SLA--nice!
	
	@Test
	void should_ReturnCoderWithWorstBMIin1Ms_When_CoderListHas10000Elements() {
		//given
		List<Coder> coders = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			coders.add(new Coder(1.0 + i, 10.0 + i));
		}
		
		//when
		Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
		
		//then
		assertTimeout(Duration.ofMillis(500), executable);
		
	}
	
	
	
	
	@Test
	void should_ReturnNullWorstBMICoder_When_CoderListEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();		
		
		// when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
				
		
		// then
		// we are expecting the coder with worst BMI to be null, b/c the list is null: 
		assertNull(coderWorstBMI);
	}
	
	@Test 
	void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
		// given
		List<Coder> coders = new ArrayList<>();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82, 98.0));
		coders.add(new Coder(1.82, 64.7));
		double[] expected = {18.52, 29.59, 19.53};
		
		// when
		double[] bmiScores = BMICalculator.getBMIScores(coders);
		
		// then
		//we might be tempted to write this: 
		//assertEquals(expected, bmiScores);
		//this will fail b/c these are 2 different objects in memory (despite having the same values in the arrays)
		// to compare 2 arrays (that aren't the same object in memory) we can use 'assertArrayEquals'
		assertArrayEquals(expected, bmiScores);
		
		
	}
	
	
	
	
}
