import java.util.ArrayList;

public class Compression {
	
	private String input = "";
    private ArrayList<String> dictionary = new ArrayList<String>();
	ArrayList<Tag> tags = new ArrayList<Tag>();
	
	public Compression (String input) {
		this.input = input;
    }
	
	public void setInput (String input) {
		this.input = input;
    }
	
	public ArrayList<Tag> compress(){
		int inputLength = input.length();
		int prevIndex = 0;
	    dictionary.add(""); //null value at beginning of dictionary
	    String c = "";
	    
		for (int i=0; i< inputLength; i++)
		{
			c = c + Character.toString(input.charAt(i));
			int index = dictionary.indexOf(c);
			if (index == -1) {
				dictionary.add(c);
				tags.add(new Tag(prevIndex,c.charAt(c.length()-1)));
				c = "";
				prevIndex = 0;
			}
			else {
				prevIndex = index;
				if (i == inputLength-1) {
					tags.add(new Tag(0,c.charAt(c.length()-1)));
				}
				continue;
			}
		}			
		return tags;
	}

	@Override
	public String toString() {
		String outString = new String();
		outString += "{ ";
		for(int i=0 ; i<tags.size(); i++) {
			outString = outString + "<" + tags.get(i).getIndex() +","+ tags.get(i).getCharachter()+"> ";
		}
		outString += "}";
		return outString;
		
	}
}
