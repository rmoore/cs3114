/**
 * Store the output messages from the Database.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 */
public class OutputMessages {
	public static String InsertBadX = "Insert rejected, bad X coordinate";
	public static String InsertBadY = "Insert rejected, bad Y coordinate";
	public static String InsertSuccess = "Insert operation successful";
	public static String InsertDup = "Insert rejected, coordinates duplicate an existing city record";
	public static String MakeNullSuccess = "Makenull operation successful";
	public static String RemoveNoFound = "No such City Record found";
	public static String RemoveBadX = "Remove operation failed: Bad X coordinate";
	public static String RemoveBadY = "Remove operation failed: Bad Y coordinate";
	public static String SearchBadX = "Search operation failed: Bad X value";
	public static String SearchBadY = "Search operation failed: Bad Y value";
	public static String SearchBadRadius = "Search operation failed: Bad radius value";
	public static String findCRF = "City Record(s) Found:";
	public static String findNoRecords = "No records.";
	
	public static String formatFindRecord(City city)
	{
		return city.getX() + ", " + city.getY() + ", " + city.getName();
	}
	
	public static String formatRemoveCity(City city)
	{
		return "Removed " + city.getX() + ", " + city.getY() + ", " + city.getName();
	}
}
