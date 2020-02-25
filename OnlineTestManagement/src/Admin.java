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

public class Admin {
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static Set<Test> testCollection=new HashSet<Test>();
	
	public Set<Question> addQuestion()
{	
		Set<Question> testQuestion=new HashSet<Question>();
		try {
			int j=0,i,n;
			System.out.println("Enter no. of question");
			n=Integer.parseInt(br.readLine());
			while(j<=n)
			{	
				Question sample=new Question();
				System.out.println("Enter question ID");
				BigInteger questionId=new BigInteger(br.readLine());
				sample.setQuestionId(questionId);
				System.out.println("Enter Question Title");
				String questionTitle=br.readLine();
				sample.setQuestionTitle(questionTitle);
				System.out.println("Enter Question marks");
				BigDecimal questionMarks=new BigDecimal(br.readLine());
				sample.setQuestionMarks(questionMarks);
				System.out.println("Enter Question options");
				for(i=0;i<4;i++)
				{
					//System.out.print(i+1);
					sample.getQuestionOptions()[i]=br.readLine();
				}
				System.out.println("Enter Correct answer");
				int questionAnswer=Integer.parseInt(br.readLine());
				sample.setQuestionAnswer(questionAnswer);
				testQuestion.add(sample);
				j++;
			}
		}
	 catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return testQuestion;
}

	
	public Test addTest(Test test)

{
	BigInteger testId;
	String testTitle;
	LocalTime testDuration;
	//Set<Question> testQuestion=new HashSet<Question>();
	BigDecimal testTotalMarks=new BigDecimal(0);
	//BigDecimal testMarksScored;
	//Question currentQuestion;
	LocalDateTime startTime,endTime;
	Admin admin=new Admin();
	try {
		
		System.out.println("Enter Test ID");
		testId=new BigInteger(br.readLine());
		System.out.println("Enter Test Title");
		testTitle= br.readLine();
		System.out.println("Enter Maximum Test Marks");
		//testTotalMarks= BigDecimal)(br.readLine());
		System.out.println("Enter Test Duration");
		testDuration=LocalTime.parse(br.readLine());
		System.out.println("Enter Start Time");
		startTime=LocalDateTime.parse(br.readLine());
		//endTime=LocalDateTime.parse();
		endTime=startTime.plusHours(testDuration.getHour());
		test.setTestId(testId);
		test.setTestTitle(testTitle);
		test.setTestDuration(testDuration);
		test.setStartTime(startTime);
		test.setEndTime(endTime);
		test.setTestTotalMarks(testTotalMarks);
		//test.getTestQuestions()=admin.addQuestion();
				
		testCollection.add(test);
	} catch (IOException e) {

		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return test;
}
	
	
public Set<Test> deleteTest(BigInteger testId)
{
	Iterator<Test> testIterator=testCollection.iterator();
	while(testIterator.hasNext())
	{
		Test test=testIterator.next();
		if(test.getTestId()==testId)
		{
			testCollection.remove(test);
			break;
		}
	}
	return testCollection;
	
}


public Set<Test> deleteQuestion(BigInteger testId, Question question)
{
	Iterator<Test> testIterator =testCollection.iterator();
	Iterator<Question> questionIterator =new Test().getTestQuestions().iterator();
	while(testIterator.hasNext())
	{
		Test testnext=testIterator.next();
		if(testnext.getTestId()==testId)
		{
			while(questionIterator.hasNext())
			{
				Question nextQuestion=questionIterator.next();
				if(nextQuestion.getQuestionId()==question.getQuestionId())
				{
					Test test = new Test();
					test.getTestQuestions().remove(nextQuestion);
					return testCollection;
				}
			}
		}
	}
	
	return null;
	
}



}
