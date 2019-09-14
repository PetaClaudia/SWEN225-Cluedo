
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;

public class Tests extends TestCase {
	
	//Go to room
	//Go to corridoor
	//Go from room to corridoor
	//Go from room to room
	//Try go to invalid square
	//Try go to somewhere without enough moves
	//Try not moving
	//Try accusing someone
	//try winning
	//try losing
	//try suggesting when noone has stuff
	//suggest invalid card 1 for each
	//showing card 1 for each
	//printing hand correct
	//roll dice
	//play with 3 4 5 6 
	//check invalid if 2 or 7
	
	public @Test void testRoom() {
		Game g=new Game();
		g.setup();

		  StringWriter output = new StringWriter();
		  String input = "3\n"      
		               + "1\n"
		               + "2\n"
		               + "3\n";

		/**
		String[][] tests = { 
				// Test 1
				
				{"3 1 2 3",
					 
					"25 |X|X|X|X|X|X|X|X|X|W|X|X|X|X|X|X|X|X|X|X|X|X|X|X| 25\n"+
					"24 |*|*|*|*|*|*|X|_|_|_|*|*|*|*|_|_|_|X|*|*|*|*|*|*| 24\n"+
					"23 |*|_|_|_|_|*|_|_|*|*|*|_|_|*|*|*|_|_|*|_|_|_|_|*| 23\n"+
					"22 |*|_|k|i|_|*|_|_|*|_|_|_|_|_|_|*|_|_|*|_|c|o|_|*| 22\n"+
					"21 |*|_|_|_|_|*|_|_|*|_|b|a|l|l|_|*|_|_|B|_|_|_|*|*| 21\n"+
					"20 |*|*|_|_|_|*|_|_|L|_|_|_|_|_|_|R|_|_|_|*|*|*|*|X| 20\n"+
					"19 |X|*|*|*|B|*|_|_|*|_|_|_|_|_|_|*|_|_|_|_|_|_|_|_| 19\n"+
					"18 |_|_|_|_|_|_|_|_|*|B|*|*|*|*|B|*|_|_|_|_|_|_|_|X| 18\n"+
					"17 |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|*|*|*|*|*|*| 17\n"+
					"16 |*|*|*|*|*|_|_|_|_|_|_|_|_|_|_|_|_|_|L|_|_|_|_|*| 16\n"+
					"15 |*|_|_|_|*|*|*|*|_|_|*|*|*|*|*|_|_|_|*|_|b|i|l|*| 15\n"+
					"14 |*|_|_|_|_|_|_|*|_|_|*|_|_|_|*|_|_|_|*|_|_|_|_|*| 14\n"+
					"13 |*|_|d|i|n|_|_|R|_|_|*|_|_|_|*|_|_|_|*|*|*|*|B|*| 13\n"+
					"12 |*|_|_|_|_|_|_|*|_|_|*|_|_|_|*|_|_|_|_|_|_|_|_|X| 12\n"+
					"10 |*|*|*|*|*|*|B|*|_|_|*|_|_|_|*|_|_|*|*|_|_|_|*|*| 10\n"+
					" 9 |x|_|_|_|_|_|_|_|_|_|*|*|*|*|*|_|_|L|_|l|i|b|_|*| 9\n"+
					" 8 |M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|*|*|_|_|_|*|*| 8\n"+
					" 7 |x|_|_|_|_|_|_|_|_|*|*|T|T|*|*|_|_|_|*|*|*|*|*|X| 7\n"+
					" 6 |*|*|*|*|*|*|T|_|_|*|_|_|_|_|*|_|_|_|_|_|_|_|_|_| 6\n"+
					" 5 |*|_|_|_|_|_|*|_|_|*|_|_|_|_|R|_|_|_|_|_|_|_|_|X| 5\n"+
					" 4 |*|_|l|o|u|_|*|_|_|*|_|h|a|_|*|_|_|T|*|*|*|*|*|*| 4\n"+
					" 3 |*|_|_|_|_|_|*|_|_|*|_|_|_|_|*|_|_|*|_|s|t|u|_|*| 3\n"+
					" 2 |*|_|_|_|_|*|*|_|_|*|_|_|_|_|*|_|_|*|*|_|_|_|_|*| 2\n"+
					" 1 |*|*|*|*|*|*|X|S|X|*|*|*|*|*|*|X|_|X|*|*|*|*|*|*| 1\n"+
					 "\n"+
					    "A B C D E F G H I J K L M N O P Q R S T U V W X"};
		}
		}
				}**/
	}
		
		/*
		public @Test void testBoard() {
			Game g = new Game();
			Board b = new Board();
			List<Player> allSuspects = new ArrayList<>();
			g.players;
			String actual = b.printBoard(allSuspects);
			String expected = "    A B C D E F G H I J K L M N O P Q R S T U V W X\r\n" + 
					" \r\n" + 
					"25 |X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X|X| 25\r\n" + 
					"24 |*|*|*|*|*|*|X|_|_|_|*|*|*|*|_|_|_|X|*|*|*|*|*|*| 24\r\n" + 
					"23 |*|_|_|_|_|*|_|_|*|*|*|_|_|*|*|*|_|_|*|_|_|_|_|*| 23\r\n" + 
					"22 |*|_|k|i|_|*|_|_|*|_|_|_|_|_|_|*|_|_|*|_|c|o|_|*| 22\r\n" + 
					"21 |*|_|_|_|_|*|_|_|*|_|b|a|l|l|_|*|_|_|B|_|_|_|*|*| 21\r\n" + 
					"20 |*|*|_|_|_|*|_|_|L|_|_|_|_|_|_|R|_|_|_|*|*|*|*|X| 20\r\n" + 
					"19 |X|*|*|*|B|*|_|_|*|_|_|_|_|_|_|*|_|_|_|_|_|_|_|C| 19\r\n" + 
					"18 |_|_|_|_|_|_|_|_|*|B|*|*|*|*|B|*|_|_|_|_|_|_|_|X| 18\r\n" + 
					"17 |X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|*|*|*|*|*|*| 17\r\n" + 
					"16 |*|*|*|*|*|_|_|_|_|_|_|_|_|_|_|_|_|_|L|_|_|_|_|*| 16\r\n" + 
					"15 |*|_|_|_|*|*|*|*|_|_|*|*|*|*|*|_|_|_|*|_|b|i|l|*| 15\r\n" + 
					"14 |*|_|_|_|_|_|_|*|_|_|*|_|_|_|*|_|_|_|*|_|_|_|_|*| 14\r\n" + 
					"13 |*|_|d|i|n|_|_|R|_|_|*|_|_|_|*|_|_|_|*|*|*|*|B|*| 13\r\n" + 
					"12 |*|_|_|_|_|_|_|*|_|_|*|_|_|_|*|_|_|_|_|_|_|_|_|X| 12\r\n" + 
					"11 |*|_|_|_|_|_|_|*|_|_|*|_|_|_|*|_|_|_|*|*|T|*|*|X| 11\r\n" + 
					"10 |*|*|*|*|*|*|B|*|_|_|*|_|_|_|*|_|_|*|*|_|_|_|*|*| 10\r\n" + 
					" 9 |x|_|_|_|_|_|_|_|_|_|*|*|*|*|*|_|_|L|_|l|i|b|_|*| 9\r\n" + 
					" 8 |M|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|*|*|_|_|_|*|*| 8\r\n" + 
					" 7 |x|_|_|_|_|_|_|_|_|*|*|T|T|*|*|_|_|_|*|*|*|*|*|X| 7\r\n" + 
					" 6 |*|*|*|*|*|*|T|_|_|*|_|_|_|_|*|_|_|_|_|_|_|_|_|P| 6\r\n" + 
					" 5 |*|_|_|_|_|_|*|_|_|*|_|_|_|_|R|_|_|_|_|_|_|_|_|X| 5\r\n" + 
					" 4 |*|_|l|o|u|_|*|_|_|*|_|h|a|_|*|_|_|T|*|*|*|*|*|*| 4\r\n" + 
					" 3 |*|_|_|_|_|_|*|_|_|*|_|_|_|_|*|_|_|*|_|s|t|u|_|*| 3\r\n" + 
					" 2 |*|_|_|_|_|*|*|_|_|*|_|_|_|_|*|_|_|*|*|_|_|_|_|*| 2\r\n" + 
					" 1 |*|*|*|*|*|*|X|S|X|*|*|*|*|*|*|X|_|X|*|*|*|*|*|*| 1\r\n" + 
					" \r\n" + 
					"    A B C D E F G H I J K L M N O P Q R S T U V W X\r\n" + 
					"";
			
			Assert.assertEquals(expected, b.printBoard(allSuspects));
		}
		*/
		public @Test void testDice() {
			Game g = new Game();
			int num = g.rollDice();
			
			assertTrue(num<=12 && num>=2);
			
		}
		/*
		public static void checkValid(String input, String expectedOutput, List<Player> players) {
			try {
				Game game = new Game();
				Board board = new Board();
				List<Board> boards = board.board(players);
				if (boards.isEmpty()) {
					fail("test failed with insufficient boards on input: " + input);
				}
				String actualOutput = boards.get(boards.size() - 1).toString();
				assertEquals(expectedOutput, actualOutput);
			} catch (Exception e) {
				fail("test failed because of exception on input: " + input);
			}
		}
		*/
}
