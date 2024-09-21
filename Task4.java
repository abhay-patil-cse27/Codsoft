import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class task4 {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeLimit = 10; // Time limit in seconds for each question
    private JLabel timerLabel;
    private JLabel questionLabel;
    private JButton[] optionButtons;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new task4().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Initialize questions
        questions = new ArrayList<>();
        questions.add(new Question("Which of the following is a primitive data type in Java?",
                new String[]{"String", "Integer", "Float", "boolean"}, 3));
        questions.add(new Question("What is the default value of a boolean variable in Java?",
                new String[]{"true", "false", "0", "null"}, 1));
        questions.add(new Question("Which keyword is used to create an object in Java?",
                new String[]{"class", "object", "new", "create"}, 2));
        questions.add(new Question("What is the parent class of all classes in Java?",
                new String[]{"Object", "Class", "Base", "Super"}, 0));
        questions.add(new Question("What is the range of the byte data type in Java?",
                new String[]{"-128 to 127", "0 to 255", "-32768 to 32767", "-2147483648 to 2147483647"}, 0));

        JFrame frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        questionLabel = new JLabel("", SwingConstants.CENTER);
        northPanel.add(questionLabel);
        frame.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 5, 5)); // Adjust padding as needed
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new OptionButtonListener(i));
            centerPanel.add(optionButtons[i]);
        }
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        timerLabel = new JLabel("Time left: " + timeLimit + " seconds");
        southPanel.add(timerLabel);
        frame.add(southPanel, BorderLayout.SOUTH);

        showQuestion();
        frame.setVisible(true);
    }

    private void showQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showResult();
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestionText());

        String[] options = question.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[i]);
        }

        startTimer();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            private int timeLeft = timeLimit;

            @Override
            public void run() {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.cancel();
                    // Move to the next question when time is up
                    currentQuestionIndex++;
                    showQuestion();
                }
            }
        }, 0, 1000);
    }

    private void showResult() {
        JOptionPane.showMessageDialog(null, "Quiz finished! Your score: " + score);
        System.exit(0);
    }

    private class OptionButtonListener implements ActionListener {
        private int optionIndex;

        public OptionButtonListener(int optionIndex) {
            this.optionIndex = optionIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Question question = questions.get(currentQuestionIndex);
            if (question.isAnswerCorrect(optionIndex)) {
                score++;
            }
            currentQuestionIndex++;
            showQuestion();
        }
    }
}

class Question {
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean isAnswerCorrect(int index) {
        return index == correctAnswerIndex;
    }
}
