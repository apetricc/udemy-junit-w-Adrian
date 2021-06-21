package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BMICalculatorTest {

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
