import java.util.*;

public class task2 
{
    public static void main(String[] args) 
{
        Scanner sc = new Scanner(System.in);
        int[] marks = new int[5];

        // taking input of marks
        for (int i = 0; i < marks.length; i++) 
        {
            System.out.print("Enter marks of subject "+ (i + 1)+ ":");
            marks[i] = sc.nextInt();
        }

        // for total marks and average percentage
        int Total_Marks = cal_TotalMarks(marks);
        double AvgPercentage = cal_AvgPercentage(Total_Marks, marks.length);

        // for grade
        char grade = Fix_Grade(AvgPercentage);

        // Declare the results
        declare_Results(Total_Marks, AvgPercentage, grade);
    }

    private static int cal_TotalMarks(int[] marks) 
   {
        int total = 0;
        for (int mark : marks) 
        {
            total += mark;
        }
        return total;
    }

    private static double cal_AvgPercentage(int totalMarks, int numberOfSubjects) 
    {
        return totalMarks / (double) numberOfSubjects;
    }

    private static char Fix_Grade(double avgPercentage) 
{
        if (avgPercentage >= 90) {
            return 'A';
        } else if (avgPercentage >= 80) {
            return 'B';
        } else if (avgPercentage >= 70) {
            return 'C';
        } else if (avgPercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    private static void declare_Results(int total_Marks, double avgPercentage, char grade) 
{
        System.out.println("Your total Marks: " + total_Marks);
        System.out.println("Your average percentage becomes : " + avgPercentage + "%");
        System.out.println("Grade: " + grade);
    }
}
