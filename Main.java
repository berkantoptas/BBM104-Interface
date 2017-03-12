
public class Main 
{
	public static void main(String[] args) throws DuplicatedIDException, NotFoundException
	{	
		App agenda = new App();
		agenda.runApp(args[0]);
	}
}
