import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Application {
	private double result = 0;
	private String output = "";
	private List<String> numbers = new ArrayList<>();
	private final List<String> operations = Collections.unmodifiableList(new ArrayList<>(Arrays.asList("+", "-", "*", "/")));

	public static void main(String[] args) {
		Application calculator = new Application();
		calculator.inputHandler();
	}
	
	public void inputHandler() {
		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a number: " + "\t");
		
		while (input.hasNext()) {	// hasNextLine();
			
			String line = input.next(); // nextLine();
			
			if ( isDouble(line) || operations.contains(line) || line.equals("=") ) {	// check if the input is valid
				
				if ( line.equals("=") ) {	// check if user wants to calculate the total
					
					if( !previousInputIsOperation() ) {
						
						DecimalFormat formatter = new DecimalFormat("#.####");
						formatter.setRoundingMode(RoundingMode.CEILING);
						System.out.println("Result: " + "\t\t" + formatter.format(result));
						System.out.println();
						
						if(result == (long) result) {
							
							System.out.println(output + "= " + (long)result);
						} else {
							
							System.out.println(output + "= " + result);
						}
						
					} else {
						
						System.out.println("Only numbers are allowed as a last input before equals sign, please do not enter any operation that precedes an equals sign!");
						System.out.print("Enter a number: " + "\t");
						continue;
					}
					
				//	calculator.output = calculator.output.replace("=", "");
					break;
				}
				
				if ( !(operations.contains(line)) ) {	// if it's a number
					
					if( !numbers.isEmpty() && !previousInputIsOperation() ) { 
						// check if the very first input was a number and the previous input was a number, throw an error, if so
						
						System.out.println("You have already entered a number, please instead enter an operation now!");
						System.out.print("Enter an operation:" + "\t");
						continue;
					}
					
					setOutput(line); 	// build an output
					numbers.add(line); 	// add the number to the numbers list
					
					if ( numbers.size() < 2) {
						
						result = Double.parseDouble(previousInput());
						
					} else {
						
						switch(numbers.get(numbers.size() - 2)) {
						
							case "+": 
								Addition(previousInput());
								break;
							case "-":
								Subtraction(previousInput());
								break;
							case "*":
								Multiplication(previousInput());
								break;
							case "/":
								Division(previousInput());
								break;
							default:
								System.out.println("Unexpected error, something went wrong!...");
						}
					}
				}
				
				if( operations.contains(line) )	{	// if the operation is +
					
					if( numbers.size() == 0 ) {		// check if the very 1st input was an operation, throw an error
						
						firstInputError();
						continue;
					}
					
					if( !numbers.isEmpty() && previousInputIsOperation() ) { 
						// check if the 1st input was a number and the previous input was an operation, throw an error
						operationError();
						continue;
					}
					
					setOutput(line); 	// build an output
					numbers.add(line); 	// add the operation to the numbers list
				}
				
				if( !numbers.isEmpty() && previousInputIsOperation() ) {	// if the previous input was an operation
					
					System.out.print("Enter a number: " + "\t");
					
				} else if( !numbers.isEmpty() ) {
					// if the previous input was a number and it's not a very first input
					System.out.print("Enter an operation:" + "\t");
				}
		
			} else {	// if the input is not valid, throw an error
				
				System.out.println("Please either enter a single number, or or a single operation!");
				continue;
			}
		}
		
		input.close();
	}
	
	private boolean isDouble(String str) {
        try {
        	
        	Double.parseDouble(str);
            return true;
            
        } catch (NumberFormatException e) {
        	
            return false;
        }
    }
	
	private boolean previousInputIsOperation() {
		
		if ( operations.contains(previousInput()) ) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	private String previousInput() {
		
		return numbers.get(numbers.size() - 1);
	}
	
	private void Addition(String number) {
		
		result += Double.parseDouble(number);
	}
	
	private void Subtraction(String number) {
		
		result -= Double.parseDouble(number);
	}
	
	private void Multiplication(String number) {
		
		result *= Double.parseDouble(number);
	}
	
	private void Division(String number) {
		
		result /= Double.parseDouble(number);
	}
	
	private void setOutput(String text) {
		
		output += text + " ";
	}
	
	private void firstInputError() {
		
		System.out.println("First input must be a number!");
		System.out.print("Enter a number: " + "\t");
	}
	
	private void operationError() {
		
		System.out.println("You have already entered an operation, please instead enter a number now!");
		System.out.print("Enter a number: " + "\t");
	}
}