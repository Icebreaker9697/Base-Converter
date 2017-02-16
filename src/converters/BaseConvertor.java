package converters;

import java.io.FileNotFoundException;
import java.math.BigInteger;

public class BaseConvertor
{
	private static String digitsNorm = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static String digitsSixtyFour = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	
	public static void main(String[] args) throws FileNotFoundException
	{
		//some examples
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
	
	/**
	 * This method converts a given number in a specified starting base to a specified ending base 
	 * after first doing some basic validation.
	 * @param startBase The base in which the given number
	 * @param num The number to be converted
	 * @param endBase The base to which convert the number
	 * @return a string representing the converted number
	 */
	private static String convertTool(int startBase, String num, int endBase)
	{
		//check if the specified bases are valid (base cannot be 63, or be greater than 64, or be less than 2)
		if(startBase > 64 || startBase == 63)
		{
			return "Starting base cannot be greater than 64 or be 63";
		}
		if(startBase < 2)
		{
			return "Starting base cannot be less than 2";
		}
		if(endBase > 64 || endBase == 63)
		{
			return "Ending base cannot be greate than 64 or be 63";
		}
		if(endBase < 2)
		{
			return "Ending base cannot be less than 2";
		}
		
		//data was validated, so the arguments are passed on to a helper method
		System.out.println("Converting : '" + num + "' from base" + startBase + " to base"+ endBase);
		
		if(startBase == 64)
		{
			return convertFrom(startBase, num, endBase, digitsSixtyFour);
		}
		else
		{
			return convertFrom(startBase, num, endBase, digitsNorm);
		}
	}
	
	/**
	 * Helper method for convertTool, which first converts num to base10, then passes
	 * that converted number to another helper method
	 * @param startBase The base in which the given number
	 * @param num The number to be converted
	 * @param endBase The base to which convert the number
	 * @param digits specifies the sequence of digits which num is based off of (base 64 is based off of a
	 * different sequence than normal bases)
	 * @return a string representing the converted number
	 */
	private static String convertFrom(int startBase, String num, int endBase, String digits)
	{
		//num must first be converted to base10, which no doubt could be a huge number
		//therefore it will be stored as a BigInteger
		BigInteger number = new BigInteger("0");
		
		//iterate through each index of the given number
		for(int i = 0; i < num.length(); i++)
		{
			//for each index, initiate the starting base as a BigInteger
			BigInteger toMultiply = new BigInteger("" + startBase);
			
			//then find the power to which to raise the starting base, based on the current index
			int power = num.length() - 1 - i;
			
			//then raise toMultiply to the respective power
			for(int in = 1; in < power; in++)
			{
				toMultiply = toMultiply.multiply(new BigInteger("" + startBase));
			}
			
			//then initiate a BigInteger which finds the index of num's current digit within digits
			//this is so that toMultiply can be converted to base10 by multiplying it by that index
			BigInteger toAdd = new BigInteger("" + digits.indexOf(num.charAt(i)));
			
			//if the power is zero, then toMultiply will be 1, because anything raised to the
			//power of zero equals 1
			if(power == 0)
			{
				toMultiply = new BigInteger("1");
			}
			
			//get the number to add on to the "end result"
			toAdd = toAdd.multiply(toMultiply);
			
			//add it to the "end result", which is in base 10
			number = number.add(toAdd);
		}
		
		//at this point, the given num has been converted from its respective base, into base 10
		//this number is then passed into another helper method
		return convert(number, endBase);
		
	}
	
	/**
	 * Helper method for converFrom which does some setup for a recursive helper method
	 * @param num The number (in base10) to be converted
	 * @param toBase The base to which it will be converted
	 * @return a string representing the converted number
	 */
	private static String convert(BigInteger num, int toBase)
	{
		//some validation (in case this method was being used by it'sself)
		if(toBase > 64 || toBase == 63)
		{
			return "Base cannot be greater than 64 or be 63";
		}
		if(toBase < 2)
		{
			return "Base cannot be less than 2";
		}
		
		//if the number is being converted to base64, use the special digit sequence specifically
		//for base64
		else if(toBase == 64)
		{
			return convertThreeRec(num, toBase,digitsSixtyFour);
		}
		
		//otherwise convert it using the normal digit sequence
		return convertThreeRec(num, toBase, digitsNorm);
	}
	
	/**
	 * Helper method for convert which recursively converts a number from base10 to a given base
	 * @param num number to be converted
	 * @param toBase base to which it will be converted
	 * @param digits String representing the sequence of digits for the converted number to use
	 * (base64 uses a different sequence than normal bases). Can also be used to pass a custom digit sequence.
	 * @return a string representing the converted number
	 */
	private static String convertThreeRec(BigInteger num, int toBase, String digits)
	{
		String res = "";
		int base = toBase;
		int power = toBase;
		
		//if num is smaller than the base (ie it can be represented in one digit), then return the "digit"
		//which represents num (ex: if you were converting 3 to base four, it would return 3)
		for(int i = 0; i < base; i++)
		{
			BigInteger temp3 = new BigInteger("" + i);
			if(num.equals(temp3))
			{
				return "" + digits.charAt(i);
			}
		}
		
		//intialize a number to run calculations on, and compare to the value we are seeking
		BigInteger bigBase = new BigInteger("" + base);
		
		//initialize the endBase as a BigInteger
		BigInteger bigPower = new BigInteger ("" + power);
		
		//if num equals the base, it will always return 10. (ex: 4 base 4 returns 10)
		if(num.equals(base))
		{
			return "10";
		}
		
		
		//at this point, num is for sure bigger than bigBase
		//while num is bigger than bigBase, keep increasing bigBase
		while(num.compareTo(bigBase) == 1)
		{
			//raise bigBase one power
			bigBase = bigBase.multiply(bigPower);
			
			//if num is still greater than or equal to bigBase, then add the first digit to the final result
			if(num.compareTo(bigBase) == 0 || num.compareTo(bigBase) == 1)
			{
				res = res + digits.charAt(0);
			}
		}
		
		//if num is now less than bigBase, then we overshot, so bring bigBase back down one power
		if(bigBase.compareTo(num) == 1)
		{
			bigBase = bigBase.divide(bigPower);
		}
		
		//initialize temp as a variable at this scope, with a dumb value (value will be overwritten)
		BigInteger temp = new BigInteger ("" + -1);
		
		//for each index of the end result, the number of possible digits is equal to the endBase
		//(which is represented by power)
		//so we need to multiply bigBase by each number leading up to power, to find which one
		//exactly overshoots num
		for(int i = 0; i <= power; i++)
		{
			//initialize a big Integer to represent i
			BigInteger bigI = new BigInteger("" + i);
			
			//if num is now less than bigBase*bigI, than we overshot, and thus our digit is the last index we tried.
			if(num.compareTo(bigBase.multiply(bigI)) == -1)
			{
				//store the last index into temp (because the last index was the one we were looking for)
				temp = new BigInteger("" + (i - 1));
				
				//update bigBase by multiplying it by the correct digit
				//bigBase now represents how much of the original number we have stored into the end result
				bigBase = bigBase.multiply(temp);
				
				//update result by appending the the correct digit (the digit at index i-1 of digits)
				res = "" + digits.charAt(i-1) + res;
				break;
			}
		}
		
		//so now subtract bigBase from num
		num = num.subtract(bigBase);
		
		//and recursively run this helper method again, with the now smaller num
		String back = convertThreeRec(num, toBase, digits);
		
		//append each resulting string to the final result;
		int backLength = back.length();
		res = res.substring(0,res.length()-backLength + 1) + back;
		
		return res;	
	}
}
