import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LZ78 {

	public static void main(String[] args) throws IOException{
		
	/*
	 * ABAABABAABABBBBBBBBBBA
	 * 0A 0B 1A 2A 4A 4B 2B 7B 8B 0A
	 */
		Scanner input = new Scanner(System.in);		
		
		while(true) {
			
			System.out.println("Please select operation:");
			System.out.println("1- Compress");
			System.out.println("2- Decompress");
			System.out.println("3- Close");
			System.out.print("Input: ");
			int operation = input.nextInt();
			if (operation == 3) {
				System.out.println("BYE!");
				break;
			}
			else if (operation != 1 && operation != 2) {
				clearScreen();
				System.out.println("Please Enter Correct Input.");
				continue;
			}
			
			clearScreen();
			
			System.out.println("Please select input source:");
			System.out.println("1- Console");
			System.out.println("2- File");
			System.out.print("Input: ");
			int source = input.nextInt();
			
			clearScreen();

			
			if (operation == 1 && source == 1){
				System.out.println("Please enter required data to compress:");
				String word = input.next();
				Compression compressor = new Compression(word);
				ArrayList<Tag> tags = compressor.compress();
				System.out.println("Compressed: ");
				System.out.println(compressor);

				System.in.read();
				clearScreen();
			}
			
			else if (operation == 1 && source == 2){
				System.out.println("Enter file name: ");
				String fileName = input.next();
				File file = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\" + fileName + ".txt"); 
				Scanner sc = new Scanner(file); 
								
				File fout = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\"+ fileName + "C.txt");
				if (!fout.exists()) {
					fout.createNewFile();
				}
				
				FileOutputStream fos = new FileOutputStream(fout);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				
				while (sc.hasNextLine()) {
					String word = sc.nextLine();
					Compression compressor = new Compression(word);
					ArrayList<Tag> tags = compressor.compress();
					for (int i=0 ; i<tags.size() ; i++)
					{
                        osw.write(String.valueOf(tags.get(i).getIndex()) + String.valueOf(tags.get(i).getCharachter() + "|")) ;
					}
				}
				osw.close();
				sc.close();
				System.out.println("Compressed Successfully");
			}
			
			else if (operation == 2 && source == 1){
				clearScreen();
				System.out.println("Enter Tags in <Index,Character> format, then press #");
		        Scanner inputTagsScanner = new Scanner(System.in);
		        inputTagsScanner.useDelimiter("#");
		        String inputTags = inputTagsScanner.next();
		        inputTags = inputTags.replace("\r", "");
		        ArrayList<Tag> tags = new ArrayList<Tag>();
		        
		        int len = inputTags.length();
				for (int i=0 ; i<len;i++) {
					int indexOfSpace = inputTags.indexOf("\n");
					if (indexOfSpace == -1)
						break;
					String tag = inputTags.substring(0,indexOfSpace);
					inputTags = inputTags.substring(indexOfSpace+1);
					tags.add(new Tag(Integer.valueOf(tag.substring(0,tag.length()-1)),Character.valueOf(tag.charAt(tag.length()-1))) );
				}
				DeCompression d = new DeCompression(tags);
				d.deCompress();
				System.out.println("Decompression: " + d);
				System.in.read();
				clearScreen();
			}
			
			else if (operation == 2 && source == 2){
				System.out.println("Enter file name: ");
				String fileName = input.next();
				File file = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\" + fileName + ".txt"); 
				Scanner sc = new Scanner(file); 
								
				File fout = new File("C:\\Users\\Khaled-Predator\\Desktop\\compression\\"+ fileName + " Decompressed.txt");
				if (!fout.exists()) {
					fout.createNewFile();
				}
				
				FileOutputStream fos = new FileOutputStream(fout);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				
				while (sc.hasNextLine()) {
					String word = sc.nextLine();
					ArrayList<Tag> tags = new ArrayList<Tag>();
					int len = word.length();
					for (int i=0 ; i<len;i++) {
						int indexOfSpace = word.indexOf("|");
						if (indexOfSpace == -1)
						{
							if (word.length()!=0)
							{
								tags.add(new Tag(Integer.valueOf(word.substring(0,word.length()-1)),Character.valueOf(word.charAt(word.length()-1))) );
							}
							break;
						}
						String tag = word.substring(0,indexOfSpace);
						word = word.substring(indexOfSpace+1);
						//if (tag)
						tags.add(new Tag(Integer.valueOf(tag.substring(0,tag.length()-1)),Character.valueOf(tag.charAt(tag.length()-1))) );
					}

					DeCompression d = new DeCompression(tags);
					osw.write(d.deCompress());
					System.out.println("Decompressed Successfully");
					System.in.read();
					clearScreen();
				}
				osw.close();
				sc.close();
			}
			
			else {
				System.out.println("Please Enter Correct Input.");
				System.in.read();
				clearScreen();

			}
		}
	}
	
	
	
	public final static void clearScreen() {
		for (int i=0 ; i<40 ; i++)
			System.out.println("");
	}
	

}