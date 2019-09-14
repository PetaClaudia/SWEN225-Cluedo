import java.util.ArrayList;
import java.util.List;

public class Token {
	
	int posX;
	int posY;
	List<Card> hand = new ArrayList<>();
	String name;
	String token;
	boolean hasLost = false;
	public Token(String name) {
		this.name = name;
	}
	public Token(String name, String token,int posX, int posY) {
		this.name = name;
		this.posX=posX;
		this.posY=posY;
		this.token=token;
	}
	
	public List<Card> getHand(){
		return hand;
	}
	
	public String printHand() {
		StringBuilder string = new StringBuilder();
		for (Card c : hand) {
			string.append(c.getName());
			string.append(" ");
		}
		return string.toString();
	}
}
