

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserDao implements UserInterface {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Set<Test> tests = new HashSet<Test>();
	static private Set<User> users = new HashSet<User>();
	public static Set<Test> getTests() {
		return tests;
	}
	public void setTests(Set<Test> tests) {
		UserDao.tests = tests;
	}
	public static Set<User> getUsers() {
		return users;
	}
	public static void addUser(User user) {
		UserDao.users.add(user);
	}
	public Test addTest(Test test)

	{
		BigInteger testId;
		String testTitle;
		LocalTime testDuration;
		BigDecimal testTotalMarks;
		Set<Question> testQuestions = new HashSet<Question>();
		LocalDateTime startTime,endTime;
		BigDecimal testMarksScored = new BigDecimal(0);
		boolean flag = true;
		try {
			
			System.out.println("Enter Test ID");
			testId=new BigInteger(br.readLine());
			if(testId.toString().isEmpty())
				throw new NullException("Test Id you Entered doesn't contain even a single character");
			System.out.println("Enter Test Title");
			testTitle= br.readLine();
			System.out.println("Enter Maximum Test Marks");
			testTotalMarks = new BigDecimal(br.readLine());
			System.out.println("Enter Test Duration");
			testDuration=LocalTime.parse(br.readLine());
			System.out.println("Enter Start Time");
			startTime=LocalDateTime.parse(br.readLine());
			endTime=startTime.plusHours(testDuration.getHour());
			test.setTestId(testId);
			test.setTestTitle(testTitle);
			test.setTestDuration(testDuration);
			test.setStartTime(startTime);
			test.setEndTime(endTime);
			test.setTestTotalMarks(testTotalMarks);
			test.setTestMarksScored(testMarksScored);
			while(flag==true)
			{
				Question question = new Question();
				UserDao dao = new UserDao();
			testQuestions.add(dao.addQuestions(test.getTestId(), question));
			System.out.println("Do you want to add more question ");
			String ans = br.readLine();
			if(ans.equalsIgnoreCase("yes")) {
				continue;
			}
			else
				flag = false;
			}
			
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullException e) {
			// TODO Auto-generated catch block
			System.out.println("Enter a test id that is not null");
		}
		return test;
	}
	public Question addQuestions(BigInteger testId,Question question)
	{
		System.out.println("Enter questionId");
		try {
			Iterator<Test>it = tests.iterator();
			while(it.hasNext()) {
				Test t = it.next();
				if(t.getTestId()==testId) {
			BigInteger questionId = new BigInteger(br.readLine());
			System.out.println("Enter question title");
			String questionTitle = br.readLine();
			System.out.println("Enter correct answer as integer(1-4)");
			int questionOption = Integer.parseInt(br.readLine());
			System.out.println("Enter Maximum marks for this question");
			BigDecimal questionMarks = new BigDecimal(br.readLine());
			System.out.println("Enter 4 question options");
			String options[] = new String[4];
			for(int i = 0; i < 4;i++)
				options[i] = br.readLine();
			question.setQuestionId(questionId);
			question.setQuestionTitle(questionTitle);
			question.setQuestionAnswer(questionOption);
			question.setQuestionMarks(questionMarks);
			question.setQuestionOptions(options);
			question.setChosenAnswer(0);
			question.setMarksScored(new BigDecimal(0));
			t.getTestQuestions().add(question);
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question;
	}
	public Question deleteQuestions(BigInteger testId,Question question)
	{
			Iterator<Test>it = tests.iterator();
			while(it.hasNext())
			{
				Test t = it.next();
				if(t.getTestId()==testId) 
				{
					Iterator<Question> itq = t.getTestQuestions().iterator();
					while(itq.hasNext())
					{
						Question q = itq.next();
						if(q.getQuestionId()==question.getQuestionId())
						{
							t.getTestQuestions().remove(question);
							break;
						}
					}
					break;
				}
			}
			return question;	
	}
	public boolean assignTest(Long userId, BigInteger TestId)
	{
		Iterator<Test> itt = UserDao.getTests().iterator();
		Iterator<User> itu = UserDao.getUsers().iterator();
		boolean testFlag = false;
		boolean userFlag = false;
		boolean flag = false;
		while(itt.hasNext())
		{
			Test t = itt.next();
			if(t.getTestId()==TestId)
			{
				testFlag = true;
				while(itu.hasNext()) {
					User user = itu.next();
					if(user.getUserId()==userId)
					{
						userFlag = true;
						user.setUserTest(t);
						break;
					}
				}
				break;
			}
		}
		if(testFlag==true && userFlag==true)
			flag = true;
		return flag;
	}
	public Test deleteTest(BigInteger testId)
	{
		Iterator<Test> testIterator=tests.iterator();
		Test test = null;
		while(testIterator.hasNext())
		{
			 test=testIterator.next();
			if(test.getTestId()==testId)
			{
				tests.remove(test);
				break;
			}
		}
		return test;
	}
	public Question updateQuestions(BigInteger testId,Question question)
	{
		try {
			Iterator<Test>it = tests.iterator();
			while(it.hasNext())
			{
				Test t = it.next();
				if(t.getTestId()==testId) 
				{
					Iterator<Question> itq = t.getTestQuestions().iterator();
					while(itq.hasNext())
					{
						Question q = itq.next();
						if(q.getQuestionId()==question.getQuestionId())
						{
							System.out.println("Enter new Question title");
							String title = br.readLine();
							question.setQuestionTitle(title);
							System.out.println("Enter correct answer as integer(1-4)");
							int questionOption = Integer.parseInt(br.readLine());
							System.out.println("Enter Maximum marks for this question");
							BigDecimal questionMarks = new BigDecimal(br.readLine());
							System.out.println("Enter 4 question options");
							String options[] = new String[4];
							for(int i = 0; i < 4;i++)
								options[i] = br.readLine();
							question.setQuestionAnswer(questionOption);
							question.setQuestionMarks(questionMarks);
							question.setQuestionOptions(options);
							question.setChosenAnswer(0);
							question.setMarksScored(new BigDecimal(0));
							break;
						}
					}
					break;
				}
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
			return question;	
	}
	public Test updateTest(BigInteger testId, Test test)
	{
		//UserDao us = new UserDao();
		Iterator<Test> tit = tests.iterator();
		while(tit.hasNext())
		{
			Test t = tit.next();
			if(t.getTestId().equals(testId))
			{
				
			}
		}
		return test;
	}
	public BigDecimal getResult(Test test)
	{
		BigDecimal totalMarks = new BigDecimal(0);
		HashSet<Question> questions = (HashSet<Question>) test.getTestQuestions();
		Iterator<Question> it = questions.iterator();
		while(it.hasNext())
		{
			Question question = it.next();
			totalMarks = totalMarks.add(question.getMarksScored());
		}
		return totalMarks;
	}
}

