package Server;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class TileBag {
	
	private char[] alpabetArray = new char[26];
	private int min = 0;
	private int max = 25;
	private Map<Character, Integer> tileBag = new HashMap<Character, Integer>();
	
	public TileBag(Map<Character, Integer> tb){
		this.tileBag = tb;
		alpabetArray = mapAlphabets();
	}
		
	public synchronized char[] getInitialTiles(){
		char[] rack = new char[7];
		for (int i = 1; i <= 7; i++){
			System.out.println(i);
			int num = randomWithRange(min, max);
			if (tileBag.containsKey(alpabetArray[num])){
				rack[i] = alpabetArray[num];
				if (tileBag.get(alpabetArray[num]) > 1){
					System.out.println(alpabetArray[num] +" inital amount");
					tileBag.put(alpabetArray[num], tileBag.get(alpabetArray[num]) - 1);
					System.out.println(alpabetArray[num] +" after amount");
				}
				else{
					System.out.println(alpabetArray[num] +" got removed.");
					tileBag.remove(alpabetArray[num]);
					max--;
				}
			}			
		}
		return rack;
	}
	
	public synchronized char getTiles(){
		if (!tileBag.isEmpty()){
			int num = randomWithRange(min, max);
			if (tileBag.containsKey(alpabetArray[num])){
				if (tileBag.get(alpabetArray[num]) > 1){
					System.out.println(alpabetArray[num] +" inital amount");
					tileBag.put(alpabetArray[num], tileBag.get(alpabetArray[num]) - 1);
					System.out.println(alpabetArray[num] +" after amount");
				}
				else{
					System.out.println(alpabetArray[num] +" got removed.");
					tileBag.remove(alpabetArray[num]);
					max--;
				}
				return alpabetArray[num];
			}
		}
		return '\0';
	}
	
	
	private int randomWithRange(int min, int max)
	{
	   int range = Math.abs(max - min) + 1;     
	   return (int)(Math.random() * range) + (min <= max ? min : max);
	}
	
	private char[] mapAlphabets(){
		char[] arr = new char[26];
	    int j = 0;
	    for (char i = 'a' ; i <= 'z' ; i++) {
		        arr[j] = i;
		        j++;
		    }
	    return arr;
	}
}
