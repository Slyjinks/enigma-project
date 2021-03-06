package main.java.enigma;

/**
 * Contains 3-4 instances of the Rotor class. Also acts as the reflector in an
 * Enigma machine. Fully encrypts a character (except for Plugboard
 * Substitutions) and handles the proper cycling of the individual Rotor
 * instances.
 * 
 * The rotor options are represented as an integer numbered 0-9. The rotors
 * are mapped as follows: 
 *            	0  - Rotor I;
 *            	1  - Rotor II;
 *            	2  - Rotor III;
 *            	3  - Rotor IV;
 *            	4  - Rotor V;
 *            	5  - Rotor VI;
 *            	6  - Rotor VII;
 *            	7  - Rotor VIII;
 *            	9  - Rotor Beta;
 *            	10 - Rotor Gamma 
 *            
 * Reflector options are also represented as an integer. The options are
 * numbered 0-3, and are mapped as follows:
 *  			0 - Reflector B;
 *            	1 - Reflector C;
 *            	2 - Reflector B thin;
 *            	3 - Reflector C thin
 *            
 * Ring and rotor settings are represented using characters. 
 * 
 * @author Bryan Matthew Winstead
 * @author Team Enigma
 * @version 0.9
 * Nov 22, 2013
 */
public class Rotors {
	public final static String[] rotorWirings = { 
		 "EKMFLGDQVZNTOWYHXUSPAIBRCJ", //I     1-3 Only 0
		 "AJDKSIRUXBLHWTMCQGZNPYFVOE", //II    1-3 Only 1
		 "BDFHJLCPRTXVZNYEIWGAKMUSQO", //III   1-3 Only 2
		 "ESOVPZJAYQUIRHXLNFTGKDCMWB", //IV    1-3 Only 3
		 "VZBRGITYUPSDNHLXAWMJQOFECK", //V     1-3 Only 4
		 "JPGVOUMFYQBENHZRDKASXLICTW", //VI    1-3 Only 5
		 "NZJHGRCXMYSWBOUFAIVLPEKQDT", //VII   1-3 Only 6
		 "FKQHTLXOCBJSPDZRAMEWNIUYGV", //VIII  1-3 Only 7
		 "LEYJVCNIXWPBQMDRTAKZGFUHOS", //BETA  4 Only   8
		 "FSOKANUERHMBTIYCWLQPZXVGJD"  //GAMMA 4 Only   9
	}; 
	
	public final static char[][] rotorNotches = {
		 {'R','!'}, //I
		 {'F','!'}, //II
		 {'W','!'}, //III
		 {'K','!'}, //IV
		 {'A','!'}, //V
		 {'A','N'}, //VI
		 {'A','N'}, //VII
		 {'A','N'}, //VIII
		 {'!','!'}, //BETA, no step
		 {'!','!'}  //GAMMA, no step
	};
	
	public final static String[] reflectors = {
		"YRUHQSLDPXNGOKMIEBFZCWVJAT", //B      0
		"FVPJIAOYEDRZXWGCTKUQSBNMHL", //C      1
		"ENKQAUYWJICOPBLMDXZVFTHRGS", //B Thin 2
		"RDOBJNTKVEHMLFCWZAXGYIPSUQ", //C Thin 3
	};
	
	private Rotor left;
	private Rotor middle;
	private Rotor right;
	private Rotor fourth;  // Used only by the Navy
	private Rotor reflector;
	private int reflectorChoice;

	/**
	 * Constructor. Accepts an array representing 3-4 Enigma rotor choices, plus
	 * an integer indicating the reflector. Ring settings and initial position
	 * have to be set separately. The Reflector itself is actually a Rotor that
	 * is never rotated. 
	 * 
	 * @param rotorChoices
	 *            An array of 3-4 integers numbered 0-9 corresponding to the
	 *            different rotors in use by the German military
	 * @param reflectorChoice
	 *            An integer indicating the reflector to be used. Valid options
	 *            are 0-3.
	 * 
	 */
	public Rotors(int[] rotorChoices, int reflectorChoice){
		this.reflectorChoice = reflectorChoice;
		if(rotorChoices.length == 3 || rotorChoices[0] == -1){
		left = new Rotor(rotorWirings[rotorChoices[0]],rotorNotches[rotorChoices[0]]);
		middle = new Rotor(rotorWirings[rotorChoices[1]],rotorNotches[rotorChoices[1]]);
		right = new Rotor(rotorWirings[rotorChoices[2]],rotorNotches[rotorChoices[2]]);
		}else{
			if (rotorChoices[0] != -1) {
				fourth = new Rotor(rotorWirings[rotorChoices[0]], rotorNotches[rotorChoices[0]]);
			}
			
			left = new Rotor(rotorWirings[rotorChoices[1]],rotorNotches[rotorChoices[1]]);
			middle = new Rotor(rotorWirings[rotorChoices[2]],rotorNotches[rotorChoices[2]]);
			right = new Rotor(rotorWirings[rotorChoices[3]],rotorNotches[rotorChoices[3]]);
		}
		reflector = new Rotor(reflectors[reflectorChoice],new char[]{'!','!'});
	} // end Constructor
	
	/**
	 * Sets the initial starting positions of the rotors.
	 * 
	 * @param choices
	 *            Array of 3-4 characters indicating the starting positions of
	 *            the rotors. This corresponds to the letters indicated on the
	 *            top of the Enigma Machine before the operator begins encrypt
	 *            or decrypt his message.
	 */
	public void setPositions(char[] choices) {
		if(choices.length == 3 || choices[0] == '!'){
		left.setStartPosition(choices[0]);
		middle.setStartPosition(choices[1]);
		right.setStartPosition(choices[2]);
		} else{
			if (choices[0] != '!') {
				fourth.setStartPosition(choices[0]);
			}
			
			left.setStartPosition(choices[1]);
			middle.setStartPosition(choices[2]);
			right.setStartPosition(choices[3]);
		}
	} // end setPositions method
	
	/**
	 * Sets the initial ring settings of the rotors. This rotates the
	 * substitution mapping of the letters.
	 * 
	 * @param choices
	 *            An array of 3-4 letters indicating the ring settings. On the
	 *            Enigma Machine, these would be adjusted by aligning the
	 *            indicated letter with a mark on the rotor before the rotor is
	 *            installed in the Enigma.
	 */
	public void setRingSettings(char[] choices) {
		if(choices.length == 3 || choices[0] == '!'){
		left.setRingPosition(choices[0]);
		middle.setRingPosition(choices[1]);
		right.setRingPosition(choices[2]);
		} else{
			if (choices[0] != '!') {
				fourth.setRingPosition(choices[0]);
			}
			
			left.setRingPosition(choices[1]);
			middle.setRingPosition(choices[2]);
			right.setRingPosition(choices[3]);
		}
	} // end setRingSettings method
	
	/**
	 * Encrypts an individual character. A character is first forwardEncrypted
	 * through each rotor in the Enigma, then sent through the reflector (a
	 * one-way rotor that does not cycle). Then the character is
	 * reverseEncrypted through each rotor before the final character is
	 * returned.
	 * 
	 * @param letter
	 *            Character to be encrypted.
	 * @return Final character after processing.
	 */
	public char encrypt(char letter){
		// Walter Adolph - doubleStep was init. to false; this assumed that there is no chance of a double step on initial position. Fixed 11/21/2013.
		boolean notch1Test = middle.getPosition() == middle.getNotchPosition()[0];
		boolean notch2Test = middle.getPosition() == middle.getNotchPosition()[1];
		boolean doubleStep = (notch1Test || notch2Test);
		
		if (Character.isAlphabetic(letter)) {
			if (right.cycleRotor() || doubleStep) {
				if (middle.cycleRotor()) {
					if (doubleStep) {
						left.cycleRotor();
						doubleStep = false;
					}
				} else if (middle.getPosition() == middle.getNotchPosition()[0] || middle.getPosition() == middle.getNotchPosition()[1]) {
					doubleStep = true;
				}
			}
			
			letter = right.forwardEncrypt(letter);
			letter = middle.forwardEncrypt(letter);
			letter = left.forwardEncrypt(letter);
			if(fourth != null)
				letter = fourth.forwardEncrypt(letter);
			letter = reflector.forwardEncrypt(letter);
			if(fourth != null)
				letter = fourth.reverseEncrypt(letter);
			letter = left.reverseEncrypt(letter);
			letter = middle.reverseEncrypt(letter);
			letter = right.reverseEncrypt(letter);
		}
		return letter;
	} // end encrypt method
	
	/**
	 * Returns the Rotor Positions to EnigmaMachine, which then passes them on to
	 * the GUI
	 * 
	 * @return A 3-4 character array representing the current positions of the
	 *         Enigma rotors
	 * 
	 */
	public char[] getPositions() {
		char[] threePositions = new char[3];
		char[] fourPositions = new char[4];
		if(fourth != null){
			fourPositions[0] = fourth.getPosition();
			fourPositions[1] = left.getPosition();
			fourPositions[2] = middle.getPosition();
			fourPositions[3] = right.getPosition();
			return fourPositions;
		}
		else{
			threePositions[0] = left.getPosition();
			threePositions[1] = middle.getPosition();
			threePositions[2] = right.getPosition();
			return threePositions;
		}
	} // end getPositions method
	
	/**
	 * Returns the reflector choice to EnigmaMachine, which then passes where
	 * it's needed.
	 * 
	 * @return An integer representing the reflector choice. 
	 */
	public int getReflector() {
		return reflectorChoice;
	}
	
	/**
	 * Returns the ring settings to the EnigmaMachine, which then passes it
	 * where it's needed.
	 * 
	 * @return 3-4 character array representing the ring settings.
	 */
	public char[] getRingSettings() {
		char[] threeSettings = new char[3];
		char[] fourSettings = new char[4];
		if(fourth != null){
			fourSettings[0] = fourth.getRingSetting();
			fourSettings[1] = left.getRingSetting();
			fourSettings[2] = middle.getRingSetting();
			fourSettings[3] = right.getRingSetting();
			return fourSettings;
		}
		else{
			threeSettings[0] = left.getRingSetting();
			threeSettings[1] = middle.getRingSetting();
			threeSettings[2] = right.getRingSetting();
			return threeSettings;
		}
	}
} // end Rotors class
