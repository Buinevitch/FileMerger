package helloworld;
import java.io.*; 
  
	// Validation of input arguments
class Validator {

	private static int numberOfInputs;
	private static char order = 'a';
	private static char type;

// checking number and correctness of input arguments
public static void checkArgs(String[] args) {    	
	if ((args.length < 3) || ((args.length == 3) && (args[0].equals("-a") || (args[0].equals("-d")))))
		closeCauseArgs();
	
	// check first argument (here can be ascending/descending or string/integer)
	if (!args[0].equals("-a") && !args[0].equals("-d")) {
		if (!args[0].equals("-s") && !args[0].equals("-i")) {
			closeCauseArgs();
		}
		
	// exclude possible repeating of s/i as 2nd arg (if its already on 1st arg i.e a/d not setted - ascending by default)
		else if (args[1].equals("-s") || args[1].equals("-i"))
			closeCauseArgs();
	}
	
	// checking 2nd argument
	else if (!args[1].equals("-s") && !args[1].equals("-i")) {
		closeCauseArgs();
	}
	
	// counting number of input files in cases with a/d and without it
	if ((args[0].equals("-a"))||(args[0].equals("-d"))) {
			numberOfInputs = args.length-3;
			order = args[0].equals("-a") ? 'a' : 'd'; 
			type = args[1].equals("-i") ? 'i' : 's';
	}
	else {
			numberOfInputs = args.length-2;
			type = args[0].equals("-i") ? 'i' : 's';
	}
}


// getter for number of inputs
public static int getNumberOfInputs() {
	return numberOfInputs;
}

// getter for order
public static int getOrder() {
	return order;
}

// getter for type
public static int getType() {
	return type;
}

// Close app and give feedback if validation went wrong
private static void closeCauseArgs() {
	System.out.println("Input arguments are wrong, closing program. Please follow instruction and retry to run");
    System.exit(0);
}
}  


public class FileMerge { 
	
    public static void main(String[] args) { 
    	
    	// Checking arguments and number of them      	
    	Validator.checkArgs(args);
    	int numberOfInputs = Validator.getNumberOfInputs();
    	int outputIndex = args.length-numberOfInputs-1;
    	
    	// creating output file and reading inputs (this version is only for 2 inputs)
        try {
        	PrintWriter output = new PrintWriter(args[outputIndex]); 
            BufferedReader input1 = new BufferedReader(new FileReader(args[args.length-2]));
            BufferedReader input2 = new BufferedReader(new FileReader(args[args.length-1]));

        // checking command line arguments and choosing sort method
            try {
                if ((Validator.getOrder() == 'a') && (Validator.getType() == 'i'))
                	ascendingInt(input1, input2, output);
                if ((Validator.getOrder() == 'd') && (Validator.getType() == 'i'))
                	descendingInt(input1, input2, output);
			} catch (NumberFormatException e) {
				 System.out.println("Found a string, but argument was numeric. Check source files or change command line argument from -i to -s");
			}

        if ((Validator.getOrder() == 'a') && (Validator.getType() == 's'))
        	ascendingStr(input1, input2, output);
        if ((Validator.getOrder() == 'd') && (Validator.getType() == 's')) 
        	descendingStr(input1, input2, output);
    	    
    } catch (IOException e) {
		System.out.println("Input files not found. Check command line arguments");
	}
        }
    
    
    // merging integers in ascending order 
    static void ascendingInt(BufferedReader input1, BufferedReader input2, PrintWriter output) throws IOException {
		
    	// reading first lines at both files
		String lineOutInput1 = input1.readLine(); 
        String lineOutInput2 = input2.readLine();
              
        // comparing lines then reading next line for the file from which we copied line. repeat until one of files end 
        while ((lineOutInput1 != null) && (lineOutInput2 !=null)) {
        	if (Integer.parseInt(lineOutInput1) <= Integer.parseInt(lineOutInput2)) {
        		output.println(lineOutInput1);
        		lineOutInput1 = input1.readLine();
        	}
        	else {
        		output.println(lineOutInput2);
        		lineOutInput2 = input2.readLine();
        	}

        }
        
        // if 1st file still got lines, copy them to output
        while (lineOutInput1 != null) {
        	output.println(Integer.parseInt(lineOutInput1));
        	lineOutInput1 = input1.readLine();
        }
        
        // same here for 2nd file
        while (lineOutInput2 !=null) {
        	output.println(Integer.parseInt(lineOutInput2));
        	lineOutInput2 = input1.readLine();
        }
        
        // close stream
        output.flush();
        output.close(); 
        
        System.out.println("Files were merged"); 
    }
    
    
    static void descendingInt(BufferedReader input1, BufferedReader input2, PrintWriter output) throws IOException {
		
  		String lineOutInput1 = input1.readLine(); 
          String lineOutInput2 = input2.readLine();
                
          while ((lineOutInput1 != null) && (lineOutInput2 !=null)) {
          	if (Integer.parseInt(lineOutInput1) >= Integer.parseInt(lineOutInput2)) {
          		output.println(lineOutInput1);
          		lineOutInput1 = input1.readLine();
          	}
          	else {
          		output.println(lineOutInput2);
          		lineOutInput2 = input2.readLine();
          	}

          }
          while (lineOutInput1 != null) {
          	output.println(Integer.parseInt(lineOutInput1));
          	lineOutInput1 = input1.readLine();
          }
          while (lineOutInput2 !=null) {
          	output.println(Integer.parseInt(lineOutInput2));
          	lineOutInput2 = input1.readLine();
          }
          output.flush();
          output.close(); 
          
          System.out.println("Files were merged"); 
      }
    
    
    static void ascendingStr(BufferedReader input1, BufferedReader input2, PrintWriter output) throws IOException {
		
		String lineOutInput1 = input1.readLine(); 
        String lineOutInput2 = input2.readLine();
              
        while ((lineOutInput1 != null) && (lineOutInput2 !=null)) {
        	if (lineOutInput1.compareToIgnoreCase(lineOutInput2) <=0) {
        		output.println(lineOutInput1);
        		lineOutInput1 = input1.readLine();
        	}
        	else {
        		output.println(lineOutInput2);
        		lineOutInput2 = input2.readLine();
        	}

        }
        while (lineOutInput1 != null) {
        	output.println(lineOutInput1);
        	lineOutInput1 = input1.readLine();
        }
        while (lineOutInput2 !=null) {
        	output.println(lineOutInput2);
        	lineOutInput2 = input1.readLine();
        }
        output.flush();
        output.close(); 
        
        System.out.println("Files were merged"); 
    }
    
    static void descendingStr(BufferedReader input1, BufferedReader input2, PrintWriter output) throws IOException {
		
		String lineOutInput1 = input1.readLine(); 
        String lineOutInput2 = input2.readLine();
              
        while ((lineOutInput1 != null) && (lineOutInput2 !=null)) {
        	if (lineOutInput1.compareToIgnoreCase(lineOutInput2) >= 0) {
        		output.println(lineOutInput1);
        		lineOutInput1 = input1.readLine();
        	}
        	else {
        		output.println(lineOutInput2);
        		lineOutInput2 = input2.readLine();
        	}

        }
        while (lineOutInput1 != null) {
        	output.println(lineOutInput1);
        	lineOutInput1 = input1.readLine();
        }
        while (lineOutInput2 !=null) {
        	output.println(lineOutInput2);
        	lineOutInput2 = input1.readLine();
        }
        output.flush();
        output.close(); 
        
        System.out.println("Files were merged"); 
    }
} 
   
    
  
