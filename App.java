import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class App 
{
	List<User> userList = new ArrayList<User>();
	List<Anniversary> anniversaryList = new ArrayList<Anniversary>();
	List<Birthday> birthdayList = new ArrayList<Birthday>();
	List<Meeting> meetingList = new ArrayList<Meeting>();
	List<Appointment> appointmentList = new ArrayList<Appointment>();
	List<Concert> concertList = new ArrayList<Concert>();
	List<Theater> theaterList = new ArrayList<Theater>();
	List<Sport> sportList = new ArrayList<Sport>();
	List<Course> courseList = new ArrayList<Course>();

	//This function reads input.txt file
	public String[] readFile(String path)
	{
		try{
			int i=0;
			int length = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String [length];
			for (String line : Files.readAllLines(Paths.get(path)))
				results[i++]=line;
			return results;
		}catch (IOException e)	{
			e.printStackTrace();
			return null;
		}
	}

	public boolean canAddID (int ID)
	{
		for (int i=0 ; i < userList.size() ; i++)
		{
			if (userList.get(i).userID == ID)
				return false;
		}
		for (int i=0 ; i < anniversaryList.size() ; i++)
		{
			if (anniversaryList.get(i).anniversaryID == ID)
				return false;
		}
		for (int i=0 ; i < birthdayList.size() ; i++)
		{
			if (birthdayList.get(i).birthdayID == ID)
				return false;
		}
		for (int i=0 ; i < meetingList.size() ; i++)
		{
			if (meetingList.get(i).meetingID == ID)
				return false;
		}
		for (int i=0 ; i < appointmentList.size() ; i++)
		{
			if (appointmentList.get(i).appointmentID == ID)
				return false;
		}
		for (int i=0 ; i < concertList.size() ; i++)
		{
			if (concertList.get(i).eventID == ID)
				return false;
		}
		for (int i=0 ; i < theaterList.size() ; i++)
		{
			if (theaterList.get(i).eventID == ID)
				return false;
		}
		for (int i=0 ; i < sportList.size() ; i++)
		{
			if (sportList.get(i).eventID == ID)
				return false;
		}
		for (int i=0 ; i < courseList.size() ; i++)
		{
			if (courseList.get(i).courseID == ID)
				return false;
		}
		return true;
	}

	public boolean isUserExist (int userID)
	{
		for (int i=0 ; i < userList.size() ; i++)
		{
			if (userList.get(i).userID == userID)
				return true;
		}
		return false;
	}
	
	

	public String getUserName (int userID)
	{
		for (int i=0 ; i < userList.size() ; i++)
		{
			if (userList.get(i).userID == userID)
			{
				return userList.get(i).userName;
			}
		}
		return "";
	}
	
	public void runApp(String inputFile) throws DuplicatedIDException, NotFoundException
	{
		FileWriter fw;
		try 
		{
			fw = new FileWriter("output.txt");
		
			String[] lines = readFile(inputFile);
			for (String line : lines)
			{
				String[] parts;
				parts=line.split(" ");
			
				//SAVE COMMANDS
				if(parts[0].equals("SAVE"))
				{
					if(parts[1].equals("USER"))
					{
						User userTemp = new User();
						userTemp.userID = Integer.parseInt(parts[2]);
						userTemp.userName = "";
						for (int i=3 ; i < parts.length ; i++)
						{
							userTemp.userName += parts[i] + " ";
						}
						fw.write("COMMAND:SAVE USER " + userTemp.userID + " " + userTemp.userName);
						fw.write(String.format("%n"));
						if(canAddID(userTemp.userID) == true)
						{
							userList.add(userTemp);	
							fw.write(userTemp.userName + "SAVED");
							fw.write(String.format("%n"));
						}
						else
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + parts[1] + " ID: " + userTemp.userID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
					}
					else if(parts[1].equals("ANNIVERSARY"))
					{
						Anniversary anniversaryTemp = new Anniversary();
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
						try 
						{
							anniversaryTemp.anniversaryDate = format.parse(parts[2]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						anniversaryTemp.userID = Integer.parseInt(parts[3]);
						anniversaryTemp.anniversaryID = Integer.parseInt(parts[4]);
						anniversaryTemp.description = "";
						for (int i=5 ; i < parts.length ; i++)
						{
							anniversaryTemp.description += parts[i] + " ";
						}
						fw.write("COMMAND:SAVE ANNIVERSARY " + anniversaryTemp.anniversaryDate + " " + anniversaryTemp.userID + " " + anniversaryTemp.anniversaryID + " " + anniversaryTemp.description);
						fw.write(String.format("%n"));
						if (canAddID(anniversaryTemp.anniversaryID) == false)
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + parts[1] + " ID: " + anniversaryTemp.anniversaryID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
						if (isUserExist(anniversaryTemp.userID) == false)
						{
							try
							{
								throw new NotFoundException ();
							}
							catch (NotFoundException e)
							{
								fw.write("USER NOT FOUND: " + anniversaryTemp.userID + "");
								fw.write(String.format("%n"));
							}
						}
						if (canAddID(anniversaryTemp.anniversaryID) == true && isUserExist(anniversaryTemp.userID) == true)
						{
							anniversaryList.add(anniversaryTemp);
							fw.write(anniversaryTemp.description + "ADDED TO " + getUserName(anniversaryTemp.userID) + "'S AGENDA");
							fw.write(String.format("%n"));
						}
					}
					else if(parts[1].equals("BIRTHDAY"))
					{
						Birthday birthdayTemp = new Birthday();
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
						try 
						{
							birthdayTemp.birthdayDate = format.parse(parts[2]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						birthdayTemp.userID = Integer.parseInt(parts[3]);
						birthdayTemp.birthdayID = Integer.parseInt(parts[4]);
						birthdayTemp.description = "";
						for (int i=5 ; i < parts.length ; i++)
						{
							birthdayTemp.description += parts[i] + " ";
						}		
						fw.write("COMMAND:SAVE BIRTHDAY " + birthdayTemp.birthdayDate + " " + birthdayTemp.userID + " " + birthdayTemp.birthdayID + " " + birthdayTemp.description);
						fw.write(String.format("%n"));
						if (canAddID(birthdayTemp.birthdayID) == false)
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + parts[1] + " ID: " + birthdayTemp.birthdayID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
						if (isUserExist(birthdayTemp.userID) == false)
						{
							try
							{
								throw new NotFoundException ();
							}
							catch (NotFoundException e)
							{
								fw.write("USER NOT FOUND: " + birthdayTemp.userID + "");
								fw.write(String.format("%n"));
							}
						}
						if (canAddID(birthdayTemp.birthdayID) == true && isUserExist(birthdayTemp.userID) == true)
						{
							birthdayList.add(birthdayTemp);
							fw.write(birthdayTemp.description + "ADDED TO " + getUserName(birthdayTemp.userID) + "'S AGENDA");
							fw.write(String.format("%n"));
						}
					}
				}
					
				//ARRANGE COMMANDS
				if(parts[0].equals("ARRANGE"))
				{
					if(parts[1].equals("MEETING"))
					{
						Meeting meetingTemp = new Meeting();
					
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							meetingTemp.meetingDate = format.parse(parts[2] + " " + parts[3]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						meetingTemp.duration = Integer.parseInt(parts[4]);
						String[] idList;
						idList = parts[5].split(",");
						meetingTemp.userIDs = new ArrayList();
						for (int i=0 ; i < idList.length ; i++)
						{
							meetingTemp.userIDs.add(Integer.parseInt(idList[i]));
						}
						meetingTemp.meetingID = Integer.parseInt(parts[6]);
						meetingList.add(meetingTemp);
					}
					else if(parts[1].equals("APPOINTMENT"))
					{
						Appointment appointmentTemp = new Appointment();
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							appointmentTemp.appointmentDate = format.parse(parts[2] + " " + parts[3]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						appointmentTemp.duration = Integer.parseInt(parts[4]);
						String[] idList;
						idList = parts[5].split(",");
						appointmentTemp.userID1 = Integer.parseInt(idList[0]);
						appointmentTemp.userID2 = Integer.parseInt(idList[1]);
						appointmentTemp.appointmentID = Integer.parseInt(parts[6]);
						appointmentList.add(appointmentTemp);
					}
					else if(parts[1].equals("CONCERT"))
					{
						Concert concertTemp = new Concert();
						concertTemp.quota = Integer.parseInt(parts[2]);
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							concertTemp.concertDate = format.parse(parts[3] + " " + parts[4]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						concertTemp.eventID = Integer.parseInt(parts[5]);
						concertTemp.eventName = "";
						for (int i=6 ; i < parts.length ; i++)
						{
							concertTemp.eventName += parts[i] + " ";
						}	
						fw.write("COMMAND:ARRANGE CONCERT " + concertTemp.quota + " " + concertTemp.concertDate + " " + concertTemp.eventID + " " + concertTemp.eventName);
						fw.write(String.format("%n"));						
						if(canAddID(concertTemp.eventID) == true)
						{
							concertList.add(concertTemp);	
							fw.write(concertTemp.eventName + "ARRANGED AT " + concertTemp.concertDate);
							fw.write(String.format("%n"));
						}
						else
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + "EVENT" + " ID: " + concertTemp.eventID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
					}
					else if(parts[1].equals("THEATER"))
					{
						Theater theaterTemp = new Theater();
						theaterTemp.quota = Integer.parseInt(parts[2]);
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							theaterTemp.theaterDate = format.parse(parts[3] + " " + parts[4]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						theaterTemp.eventID = Integer.parseInt(parts[5]);
						theaterTemp.eventName = "";
						for (int i=6 ; i < parts.length ; i++)
						{
							theaterTemp.eventName += parts[i] + " ";
						}
						fw.write("COMMAND:ARRANGE THEATER " + theaterTemp.quota + " " + theaterTemp.theaterDate + " " + theaterTemp.eventID + " " + theaterTemp.eventName);
						fw.write(String.format("%n"));						
						if(canAddID(theaterTemp.eventID) == true)
						{
							theaterList.add(theaterTemp);	
							fw.write(theaterTemp.eventName + "ARRANGED AT " + theaterTemp.theaterDate);
							fw.write(String.format("%n"));
						}
						else
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + "EVENT" + " ID: " + theaterTemp.eventID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
					}
					else if(parts[1].equals("SPORT"))
					{
						Sport sportTemp = new Sport();
						sportTemp.quota = Integer.parseInt(parts[2]);
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							sportTemp.sportDate = format.parse(parts[3] + " " + parts[4]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sportTemp.eventID = Integer.parseInt(parts[5]);
						sportTemp.eventName = "";
						for (int i=6 ; i < parts.length ; i++)
						{
							sportTemp.eventName += parts[i] + " ";
						}	
						fw.write("COMMAND:ARRANGE SPORT " + sportTemp.quota + " " + sportTemp.sportDate + " " + sportTemp.eventID + " " + sportTemp.eventName);
						fw.write(String.format("%n"));						
						if(canAddID(sportTemp.eventID) == true)
						{
							sportList.add(sportTemp);	
							fw.write(sportTemp.eventName + "ARRANGED AT " + sportTemp.sportDate);
							fw.write(String.format("%n"));
						}
						else
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + "EVENT" + " ID: " + sportTemp.eventID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
					}
					else if(parts[1].equals("COURSE"))
					{
						Course courseTemp = new Course();
						courseTemp.quota = Integer.parseInt(parts[2]);
						DateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm", Locale.ENGLISH);
						try 
						{
							courseTemp.courseDate = format.parse(parts[3] + " " + parts[4]);
						} 
						catch (ParseException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						courseTemp.courseID = Integer.parseInt(parts[5]);
						courseTemp.courseName = parts[6] + " " + parts[7];
						fw.write("COMMAND:ARRANGE COURSE " + courseTemp.quota + " " + courseTemp.courseDate + " " + courseTemp.courseID + " " + courseTemp.courseName);
						fw.write(String.format("%n"));						
						if(canAddID(courseTemp.courseID) == true)
						{
							courseList.add(courseTemp);	
							fw.write(courseTemp.courseName + " ARRANGED AT " + courseTemp.courseDate);
							fw.write(String.format("%n"));
						}
						else
						{
							try
							{
								throw new DuplicatedIDException();	
							}
							catch (DuplicatedIDException e)
							{
								fw.write("DUPLICATED " + "COURSE" + " ID: " + courseTemp.courseID + " ALREADY EXIST");
								fw.write(String.format("%n"));
							}
						}
					}
				}
				
				//ATTEND COMMANDS
				if(parts[0].equals("ATTEND"))
				{
					if(parts[1].equals("EVENT"))
					{
					
					}
					else if(parts[1].equals("COURSE"))
					{
						
					}
				}
				
				//CANCEL COMMANDS
				if(parts[0].equals("CANCEL"))
				{
					if(parts[1].equals("MEETING"))
					{
					
					}
					else if(parts[1].equals("APPOINTMENT"))
					{
					
					}
					else if(parts[1].equals("EVENT"))
					{
					
					}
					else if(parts[1].equals("COURSE"))
					{
						
					}
				}
				
				//LIST COMMANDS
				if(parts[0].equals("LIST"))
				{
					if(parts[1].equals("DAILY"))
					{
					
					}
					else if(parts[1].equals("WEEKLY"))
					{
					
					}
					else if(parts[1].equals("MONTHLY"))
					{
					
					}
					else if(parts[1].equals("ATTENDANCE"))
					{
							
					}
				}
			}			
			fw.close();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
