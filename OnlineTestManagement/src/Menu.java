

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) {
		int choice;
		User admin = new User();
		admin.setUserId((long)111);
		admin.setAdmin(true);
		admin.setUserName("Vartika");
		admin.setUserPassword("Vartika26@");
		UserDao.addUser(admin);
		Menu menu = new Menu();
		
		try {
			while(true) {
				System.out.println("1.Login\t2.Signup");
				System.out.println("Enter Your Choice");
			choice = Integer.parseInt(br.readLine());
			switch(choice)
			{
			case 1 : menu.login();
				break;
			case 2 : if(menu.signUp()) {
						System.out.println("Do you want to login now?");
						if(br.readLine().equalsIgnoreCase("yes"))
						menu.login();
					}
					 break;
			default : System.out.println("try Again.Please Enter Valid Choice.");
			}
			System.out.println("Do you want to continue....");
			if(br.readLine().equalsIgnoreCase("yes"))
				continue;
			else
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NumberFormatException e1)
		{
			System.out.println("Please only enter in digits");
		}
	}

	private boolean signUp() {
		User user = new User();
		/*userPassword should have at least one upper case character,
			 *  one lower case character, one numeric character 
			 * and one special character and the length of password should 
			 * be minimum 8 characters.
			 */
		Set<User> users = UserDao.getUsers();
		Iterator<User> usit = users.iterator();
		
		Pattern charCapital = Pattern.compile("^[A-Z]+");
		Pattern pwd = Pattern.compile("(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\\d])(?=.*?[#\\?!@\\$%\\^&\\*-]).{8,}");
		Long userId = null;
		String userName="";
		String userPassword = "";
		System.out.println("Enter User Id");
		boolean flag = false;
		try {
			String Id = br.readLine();
			if(Id.isEmpty()) {
				throw new NullException("You haven't entered anything in id");
			}
			userId = Long.parseLong(Id);
			while(usit.hasNext())
			{
				User nextUser = usit.next();
				if(nextUser.getUserId()==userId)
				{
					System.out.println("This User Id already taken. Try something else");
					System.exit(0);
				}
			}
			System.out.println("Enter Your Name with first character Capital");
			Matcher m = charCapital.matcher(userName = br.readLine());
			if(m.find()!=true)
			{
				System.out.println("You have not enterd first char as capital.Try Again");
				System.exit(0);
			}
			System.out.println("Enter Your Password.It must contain atleast 1 Uppercase char, 1 Lowercase,1 digit and a special character");
			Matcher pm  = pwd.matcher(userPassword = br.readLine());
			if(pm.find()!=true)
			{
				System.out.println("You have violated password conditions.Try Again");
				System.exit(0);
			}
			else {
				user.setUserId(userId);
				user.setUserName(userName);
				user.setUserPassword(userPassword);
				user.setAdmin(false);
				user.setUserTest(null);
				UserDao.addUser(user);
				System.out.println("Successfully Signed Up. You Can now Log In");
				flag = true;
			}
		}catch(NullException n)
		{
			n.printStackTrace();
			System.out.println("Please Enter something in Id");
			System.exit(0);
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	private void login() {
		boolean userExists = false;
		UserDao us = new UserDao();
		System.out.println("Enter User Id");
		try {
			Long id = Long.parseLong(br.readLine());
			System.out.println("Enter Your Password");
			String pwd = br.readLine();
			Set<User> users = UserDao.getUsers();
			System.out.println(users);
			Iterator<User> it = users.iterator();
			while(it.hasNext())
			{
				User nextUser = it.next();
				if(nextUser.getUserId()==id)
				{
					userExists = true;
					if(nextUser.getUserPassword().equals(pwd))
					{
						System.out.print("Welcome "+nextUser.getUserName()+" ");
						if(nextUser.isAdmin())
						{
							BigInteger testId=null;
							Question question = null;
							boolean adminFlag = true;
							System.out.println("You are an admin");
							while(adminFlag) {
							System.out.println("1.Add Test               2.Update an existing test  3.Delete an existing test");
							System.out.println("4.Assign test to a user  5.Add questions to a test  6.Update a question of a test");
							System.out.println("7.Delete a question from a test");
							int choice = Integer.parseInt(br.readLine());
							switch(choice)
							{
							case 1 : us.addTest(new Test());
								     break;
							case 2 : System.out.println("Enter Test Id of the Test you want to update");
									 testId = new BigInteger(br.readLine());
									 us.updateTest(testId, new Test());
									 break;
							case 3 : System.out.println("Enter Test Id of the Test you want to delete");
							 		 testId = new BigInteger(br.readLine());
							 		 us.deleteTest(testId);
							 		 break;
							case 4 : System.out.println("Enter userId of the user to whome you want to assign the test");
									 Long userId = Long.parseLong(br.readLine());
									 System.out.println("Enter the test id of the test you want to assign");
									 BigInteger testId2 = new BigInteger(br.readLine());
									 if(us.assignTest(userId, testId2))
										 System.out.println("Assigned");
									 else
										 System.out.println("Either User or test doesn't exist");
									 break;
							case 5 : System.out.println("Enter the test id of the test you want to add questions to");
							         testId = new BigInteger(br.readLine());
								     us.addQuestions(testId, new Question());
								     break;
							case 6 : System.out.println("Enter the test id of the test you want to update question in");
					         		 testId = new BigInteger(br.readLine());
					         		 System.out.println("Enter question Id of the question you want to update");
					         		 break;
							case 7 : System.out.println("");
							         us.deleteQuestions(testId, question);
							default :
									  System.out.println("Wrong Choice.Try Again");
							
							}
							System.out.println("Do you want to do something else?");
							if((br.readLine()).equalsIgnoreCase("yes"))
								continue;
							else
								adminFlag = false;
						}
						}
						else
						{
							System.out.println("\nDo you want to see your result? say yes or no");
							if((br.readLine()).equalsIgnoreCase("yes"))
							{
								System.out.println("You have scored "+nextUser.getUserTest().getTestMarksScored());
							}
							
						}
						
					}
					else
						System.out.println("Please Enter Correct Password;");
				}
			//	break;
			}
			if(userExists == false)
				System.out.println("This user does not exist");
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
