# Base-Converter
A Java program which converts a given number (in a specified start base) to a specified end base.

## How it works

Although there are already much easier and simpler ways to convert between bases using the Java libraries, I wanted to tackle the challenge of writing the code myself. 

Basically the order of the helper methods is as follows: main-->converTool-->convertFrom-->convert-->convertThreeRec

This program runs the convertTool method which first validates the input, then passes the arguments to a helper method called convertFrom. convertFrom first converts the given number to base10, and then passes that new number, along with the target base to another helper method called convert. The convert method sets up the input for a recursive helper method called convertThreeRec. This is the method that does the work.

### Converting from base10 to a given base

convertThreeRec is a recursive method that works on the number one base-place at a time. (for a base-10 number, each recursion will work through a ones place, tens place, hundreds place, and so on). It first checks if the number is smaller than or equal to the given base. If so, it immediately returns a value (the base case of the recursion). Otherwise it keeps raising the base one power at a time until it exceeds or equals the number. If it exceeds the number, then it goes back down one power, and then iterates through each digit that preceeds the value representing the base, multiplying it by that amount. Once it has found the value that exceeded the target number, it then backtracks one more time selecting the highest digit that does not exceed the value of the target number. Next it stores that digit in a string which represents the end result, and then subtracts the amount represented by that digit from the target number. Lastly, it enters a recursion on the now smaller target number, and adds the result of that recursion to the end result string.

## How it runs

This program runs the static convertTool method from the main method, and prints its return value to the console. Example:

```
public static void main(String[] args)
{
    String num1 = "134710352538679645";
		String num2 = "1000000";
		String num3 = "HW80V";
		String num4 = "FF";
		System.out.println("Result is: " + convertTool(10,num1,64));
		System.out.println("");
		System.out.println("Result is: " + convertTool(10,num2,37));
		System.out.println("");
		System.out.println("Result is: " + convertTool(64,num3,10));
		System.out.println("");
		System.out.println("Result is: " + convertTool(16,num4,2));
}
```

returns

```
Converting : '134710352538679645' from base10 to base64
Result is: HelloWorld

Converting : '1000000' from base10 to base37
Result is: JRH1

Converting : 'HW80V' from base64 to base10
Result is: 123456789

Converting : 'FF' from base16 to base2
Result is: 11111111
```

    


