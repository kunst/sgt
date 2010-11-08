package cn.shengguantu;
import java.util.Vector;

/**
 * Rule.java   
 * 
 */

/**
 * @author rwf
 *
 */
public class Rule {
	/**
	 * vector of cube value, it has the same length as vector of Destination.
	 */
	private Vector<String> cubeValues;
	/**
	 * vector of Destinations, it has the same length as vector of cubeValues.
	 */
	private Vector<String> destinations;
	
	/**
	 * constructor to create empty rules
	 */
	public Rule()  
	{
		cubeValues = new Vector<String>();
		destinations = new Vector<String>();
	}
	public Rule(String s)
	{
		cubeValues = new Vector<String>();
		destinations = new Vector<String>();
		
		String[] allRules = s.split(";");
		for(int i = 0; i < allRules.length; i++)
		{
			addRule(allRules[i]);
		}
	}
	private void addRule(String cubeValueEquaDest)
	{
		String[] s = cubeValueEquaDest.split("=");
		if(s.length == 2 && s[0] != "" && s[1] != "")
			addRule(s[0], s[1]);
	}
	private void addRule(String cubeValue, String dest)
	{
		cubeValues.add(cubeValue);
		destinations.add(dest);
	}
	public String toString()
	{
		String string = "";
		for(int i = 0; i < cubeValues.size(); i++)
		{
			string += cubeValues.get(i);
			string += "=";
			string += destinations.get(i);
			if(i!=cubeValues.size()-1)
				string += ",";
		}
		return string;
	}
	
	public Vector<String> getPosibleCubeValues()
	{
		return getCubeValues();
	}
	public boolean isCubeValuePosible(String cube)
	{
		return cubeValues.contains(cube);
	}
	
	public Vector<String> getCubeValues() {
		return cubeValues;
	}
	public Vector<String> getDestinations() {
		return destinations;
	}
	public String useRule(String cubeValue)
	{
		if(this.isCubeValuePosible(cubeValue))
		{
			return destinations.elementAt(cubeValues.indexOf(cubeValue));
		}
		else
		{
			return "";
		}
	}
	public static void main(String[] arg)
	{
		String rules = "de=a;cai=b;gong=c;liang=d;you=e;zang=f";
		Rule r = new Rule(rules);
		System.out.println(r.toString());
		System.out.println(r.isCubeValuePosible("de"));
		System.out.println(r.isCubeValuePosible("chuanhua"));

	}
	
}
