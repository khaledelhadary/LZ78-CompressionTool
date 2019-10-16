import java.util.ArrayList;

public class DeCompression {
	
    private ArrayList<String> dictionary = new ArrayList<String>();
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	String output = new String();
	public DeCompression (ArrayList<Tag> tags) {
		this.tags = tags;
    }
	
	public void setTags (ArrayList<Tag> tags) {
		this.tags = tags;
    }
	
	public String deCompress(){
		dictionary.add("");
		
		for (int i=0 ; i<tags.size(); i++) {
			output += (dictionary.get(tags.get(i).getIndex())+ tags.get(i).getCharachter());
			dictionary.add(dictionary.get(tags.get(i).getIndex())+ tags.get(i).getCharachter());
		}
		return output;
	}
	@Override
	public String toString() {
		return output;
	}
}
