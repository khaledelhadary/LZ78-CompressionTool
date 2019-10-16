
public class Tag{

    private int index;
    private char character;

    public Tag(int index, char character) {
        this.index = index;
        this.character = character;
    }
    
    public void setIndex(int index) {
    	this.index = index;
    }
    
    public void setCharacter( char character) {
    	this.character = character;
    }
    
    public int getIndex() {
        return index;
    }

    public char getCharachter() {
        return character;
    }

}
