package temp;

import java.util.Date;

public class GenerateEmailDemo {

	public static void main(String[] args) {
		
		Date date = new Date();
		String dateString = date.toString();
		String noSpaceDateString = dateString.replaceAll(" ","");

		String noSpaceAndnoColonDateString = noSpaceDateString.replaceAll("\\:", "");
		System.out.println(noSpaceAndnoColonDateString);
		
		String emailWithTimeStamp = noSpaceAndnoColonDateString+"@gmail.com";
		System.out.println(emailWithTimeStamp);
	}

}
