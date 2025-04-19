package com.prakash.pages;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.prakash.controller.QuestionController;
import com.prakash.controller.StudentController;
import com.prakash.entity.Question;
import com.prakash.entity.Student;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StudentDashboard {

    JFrame frame;
    JPanel topPanel,mainPanel,bottomPanel,instructionPanel,questionPanel,centerPanel,wrapperPanel;
    JButton gk,maths,aptitude,reasoning,english,startExam,submitExam;


    JScrollPane scrollPane;


    //Button group map for storing the bottongroup corresponding to each question, by this i can get the result by subject
    HashMap<Integer,ButtonGroup> gkButtonGroupMap = new HashMap<>();
    HashMap<Integer,ButtonGroup> englishButtonGroupMap = new HashMap<>();
    HashMap<Integer,ButtonGroup> mathsButtonGroupMap = new HashMap<>();
    HashMap<Integer,ButtonGroup> aptitudeButtonGroupMap = new HashMap<>();
    HashMap<Integer,ButtonGroup> reasoningButtonGroupMap = new HashMap<>();



    //This list
    List<Question> gkQuestionList;
    List<Question> mathsQuestionList;
    List<Question> englishQuestionList;
    List<Question> aptitudeQuestionList;
    List<Question> reasoningQuestionList;


    Timer examTimer;
    private boolean allowforexam = false; //Student only allow to see questions subject vice after starting the exam
    Font f = new Font("SansSerif", Font.BOLD, 14);



    public StudentDashboard(Student student) {
        FlatMacLightLaf.setup();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Student DashBoard");
        frame.setSize(new Dimension(1600,820));
        frame.setLocationRelativeTo(null); // Center window

        // Add Theme Button to Top-Right
        GridBagConstraints gbcTheme = new GridBagConstraints();
        gbcTheme.gridx = 1;
        gbcTheme.anchor = GridBagConstraints.NORTHEAST;
        gbcTheme.insets = new Insets(10, 10, 0, 10);
       JPanel themeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       themeButtonPanel.add(LoginPage.modeButton(frame),gbcTheme);
       frame.add(themeButtonPanel,BorderLayout.NORTH);


       //Main Panel
        mainPanel = new JPanel(new BorderLayout());

        //Top Panel
        topPanel = new JPanel(new GridLayout(1,1));

        JLabel welcomeLabel = new JLabel("Welcome "+student.getName());
        welcomeLabel.setFont(f);
        welcomeLabel.setMaximumSize(new Dimension(100,topPanel.getHeight()));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(welcomeLabel);
        mainPanel.add(topPanel,BorderLayout.NORTH);

      //Subject Buttons -> When Student click on Start button it will appear
        gk = new JButton(" GK ");
        maths = new JButton(" Maths ");
        aptitude = new JButton(" Apti ");
        reasoning = new JButton(" Resoning ");
        english = new JButton(" English ");
        gk.setFont(f);
        maths.setFont(f);
        aptitude.setFont(f);
        reasoning.setFont(f);
        english.setFont(f);


        //Center Dynamic CardLayout
        centerPanel = new JPanel(new CardLayout());
        instructionPanel = new JPanel(new BorderLayout());

        // Create JEditorPane for instructions
        JEditorPane instructionstText = new JEditorPane("text/html",
                "<html><body style='font-family: Arial, sans-serif; font-size: 12px;'> " +
                        "<br><br><br><h1 style='color:red;'>📌 Exam Instructions</h1>" +
                        "<ul>" +
                        "<li><b>🔹 Exam Start->   </b>     Click on any subject to begin.</li>" +
                        "<li><b>🔹 Question Display:   </b> All questions of the selected subject will appear.</li>" +
                        "<li><b>🔹 Switching Subjects:   </b> You can switch between subjects anytime.</li>" +
                        "<li><b>🔹 Answering Questions:   </b> Click on one option per question, it is auto-saved.</li>" +
                        "<li><b>🔹 Timer:   </b> The exam duration is <b>1 hour</b>. It starts when you select your first subject.</li>" +
                        "<li><b>🔹 Auto-Submission:   </b> The exam will submit automatically when the time ends.</li>" +
                        "<li><b>🔹 Manual Submission:   </b> If you finish early, click the <b>'Submit Exam'</b> button.</li>" +
                        "</ul>" +

                        "<br><h2>📜 General Exam Rules</h2>" +
                        "<ul>" +
                        "<li><b>✅ Read all questions carefully before answering.</b></li>" +
                        "<li><b>✅ Do not refresh or close the window during the exam.</b></li>" +
                        "<li><b>✅ Any attempt to cheat may result in disqualification.</b></li>" +
                        "</ul>" +

                        "<br><br><h2>⚠ Important Notes:</h2>" +
                        "<p style='color:red;'><b>❌ Do not close the window! The exam will be auto-submitted after 1 hour.</b></p>" +
                        "<p>For any technical issues, contact the exam administrator.</p>" +
                        "<br><br><br><h1 style='color:green;'>BEST OF LUCK</h1>" +
                        "</body></html>");

        instructionstText.setEditable(false); // Make text non-editable

        // Wrap JEditorPane inside JScrollPane for scrolling support and add it into instructionPanel
        instructionPanel.add(new JScrollPane(instructionstText),BorderLayout.CENTER);
        centerPanel.add(instructionPanel,"Instructions");

        mainPanel.add(centerPanel,BorderLayout.CENTER);


        //Bottom Panel for Start & Submit Button & TImer
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(frame.getWidth(),60));

        startExam = new JButton("Start Exam");
        JTextField timer = new JTextField("Time Left");
        submitExam = new JButton("Submit Exam");

        startExam.setFont(f);
        timer.setFont(f);
        timer.setEditable(false);
        submitExam.setFont(f);

        bottomPanel.add(startExam);
        bottomPanel.add(timer);
        bottomPanel.add(submitExam);

        mainPanel.add(bottomPanel,BorderLayout.SOUTH);






         //Exam Started
        startExam.addActionListener(e ->{

            mainPanel.remove(topPanel);

            topPanel = new JPanel(new GridLayout(1,5,50,10));

            //add Subject buttons in top panel
            topPanel.add(gk);
            topPanel.add(maths);
            topPanel.add(aptitude);
            topPanel.add(reasoning);
            topPanel.add(english);

            mainPanel.add(topPanel,BorderLayout.NORTH);

            //Please select a Subject
            centerPanel.removeAll();
            JLabel selectSubject = new JLabel("Please Select Any Subject",SwingConstants.CENTER);
            selectSubject.setPreferredSize(new Dimension(300,centerPanel.getHeight()));
            selectSubject.setFont(new Font("SansSerif", Font.BOLD, 20));
            centerPanel.add(selectSubject);
            frame.revalidate();
            frame.repaint();

            SwingUtilities.invokeLater(() -> {
                allowforexam = true;
            });

            bottomPanel.remove(startExam);
            submitExam.setEnabled(false);

            final int[] remainingTime = {60}; // in seconds
            examTimer = new Timer(1000, e1 -> {  //It runs in every second

                remainingTime[0] = remainingTime[0] - 1;//decrement time in every second
                int minute = remainingTime[0] /60;
                int second = remainingTime[0] %60;
                timer.setText(String.format("%02d : %02d",minute,second));

                //Student allow to submit exam after 30 minute
                if(second <= 30)
                {
                    submitExam.setEnabled(true);
                    submitExam.addActionListener(e2 -> {
                        examTimer.stop();

                        //Open Result Panel
                        result(student);
                    });
                }
                //If one hour completed then automatically exam is submitted
                if(remainingTime[0] == 0)
                {
                    examTimer.stop();
                    result(student);
                }
            });
            examTimer.start(); //start the timer
        });



//**********Question Selection
        JScrollPane gkQuestionScrollPane = addQuestions("GK");
        JScrollPane englishQuestionScrollPane = addQuestions("English");
        JScrollPane mathsQuestionScrollPane = addQuestions("Maths");
        JScrollPane aptitudeQuestionScrollPane = addQuestions("Aptitude");
        JScrollPane resoningQuestionScrollPane = addQuestions("Reasoning");


        //Choose or Switch Subject
            gk.addActionListener(e1 -> {

                if(allowforexam) {showScrollPanel(gkQuestionScrollPane);}
                else showError("GK",frame);
            });

            maths.addActionListener(e1 -> {
                if(allowforexam) {showScrollPanel(mathsQuestionScrollPane);}
                else showError("Maths",frame);
            });

            english.addActionListener(e1 -> {
                if(allowforexam){showScrollPanel(englishQuestionScrollPane);}
                else showError("English",frame);
            });

            aptitude.addActionListener(e1 -> {
                if(allowforexam){showScrollPanel(aptitudeQuestionScrollPane);}
                else showError("Aptitude",frame);
            });

            reasoning.addActionListener(e1 -> {
                if(allowforexam){showScrollPanel(resoningQuestionScrollPane);}
                else showError("Reasoning",frame);
            });



        frame.revalidate();
        frame.repaint();
        frame.add(mainPanel);
        frame.setVisible(true);


    }






    private void showError(String subject,JFrame frame) {
        JOptionPane.showMessageDialog(frame,"Sorry, you can not see "+subject+" Questions before starting the exam...!");
    }


//add Questions realted to specific subject
    private JScrollPane addQuestions(String subjectName) {


        //Question Scroll Panel
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField subject = new JTextField(subjectName+" Questions ",SwingConstants.CENTER);
        subject.setEditable(false);
        subject.setMaximumSize(new Dimension(230,40));
        subject.setFont(f);
        subject.setHorizontalAlignment(JTextField.CENTER);
        questionPanel.add(subject);


        //fetching all questions related to specific subject and add into specific subject list for fetching correct answers on the time of result calculation
         List<Question> questionList = switch (subjectName)
         {
             case "GK" ->  {
                 gkQuestionList = new QuestionController().getAllQuestionsBySection(subjectName);
                 yield gkQuestionList;  //java 12+ switch case - `yield` is necessary in block cases
             }
             case "English" -> {
                   englishQuestionList = new QuestionController().getAllQuestionsBySection(subjectName);
                 yield englishQuestionList;
             }
             case "Maths" -> {
                 mathsQuestionList = new QuestionController().getAllQuestionsBySection(subjectName);
                 yield mathsQuestionList;
             }
             case "Aptitude" -> {
                 aptitudeQuestionList = new QuestionController().getAllQuestionsBySection(subjectName);
                 yield aptitudeQuestionList;
             }
             case "Reasoning" -> {
                 reasoningQuestionList = new QuestionController().getAllQuestionsBySection(subjectName);
                 yield reasoningQuestionList;
             }
             default -> throw new RuntimeException("Incorrect Subject Choosed");
         };



        for (int i = 0; i < questionList.size(); i++) {

            Question q = questionList.get(i);

            JLabel question = new JLabel(i+1+". "+q.getQuestion());
            question.setFont(f);
            JRadioButton option_a = new JRadioButton(q.getOption_A());
            JRadioButton option_b = new JRadioButton(q.getOption_B());
            JRadioButton option_c = new JRadioButton(q.getOption_C());
            JRadioButton option_d = new JRadioButton(q.getOption_D());

            ButtonGroup group = new ButtonGroup();
            group.add(option_a);
            group.add(option_b);
            group.add(option_c);
            group.add(option_d);

            //Store the <questionid,buttongroup> in map for fetching answers
            switch (subjectName){
                case "GK" -> gkButtonGroupMap.put(q.getId(),group);
                case "English" -> englishButtonGroupMap.put(q.getId(),group);
                case "Maths" -> mathsButtonGroupMap.put(q.getId(),group);
                case "Aptitude" -> aptitudeButtonGroupMap.put(q.getId(),group);
                case "Reasoning" -> reasoningButtonGroupMap.put(q.getId(),group);
                default -> throw new RuntimeException("Incorrect Subject Choosed");
            }


            JPanel singleQuestion = new JPanel();
            singleQuestion.setLayout(new BoxLayout(singleQuestion,BoxLayout.Y_AXIS));
            singleQuestion.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            singleQuestion.add(question);
            singleQuestion.add(option_a);
            singleQuestion.add(option_b);
            singleQuestion.add(option_c);
            singleQuestion.add(option_d);

            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.add(singleQuestion);

            //for fast scrolling
            questionPanel.revalidate();
            questionPanel.repaint();
        } //for loop ending



        //Put all questions into a scroll panel for better visualization
         scrollPane = new JScrollPane(questionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500,400));

        //for fast scrolling
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(20); // Adjust this value as needed

        return scrollPane;


    }



    private void showScrollPanel(JScrollPane sp) {
        centerPanel.removeAll();

        wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(sp, BorderLayout.CENTER);
        centerPanel.add(wrapperPanel);
        frame.revalidate();
        frame.repaint();
    }




    private static String getSelectedButtonText(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null; // No selection
    }



    private void result(Student student){

        int gkMarks = 0,mathsMarks = 0,englishMarks = 0,aptitudeMarks = 0,reasoningMarks = 0;

        //GK Answers Check
        for (Question q : gkQuestionList) {
            int id = q.getId();
            if (q.getCorrect_answer().equals(getSelectedButtonText(gkButtonGroupMap.get(id)))) gkMarks++;
            //gkAnswers.put(id,true);
            //else gkAnswers.put(id,false);
        }

        //Maths Answers Check
        for (Question q : mathsQuestionList) {
            int id = q.getId();
            if (q.getCorrect_answer().equals(getSelectedButtonText(mathsButtonGroupMap.get(id)))) mathsMarks++;
        }

        //English Answers Check
        for (Question q : englishQuestionList) {
            int id = q.getId();
            if (q.getCorrect_answer().equals(getSelectedButtonText(englishButtonGroupMap.get(id)))) englishMarks++;
        }

        //Aptitude Answers Check
        for (Question q : aptitudeQuestionList) {
            int id = q.getId();
            if (q.getCorrect_answer().equals(getSelectedButtonText(aptitudeButtonGroupMap.get(id)))) aptitudeMarks++;
        }

        //Reasoning Answers Check
        for (Question q : reasoningQuestionList) {
            int id = q.getId();
            if (q.getCorrect_answer().equals(getSelectedButtonText(reasoningButtonGroupMap.get(id)))) reasoningMarks++;
        }

        String[] subject = {"GK","Maths","English","Aptitude","Reasoning"};
        int[] totalQuestion = {gkQuestionList.size(),mathsQuestionList.size(),englishQuestionList.size(),aptitudeQuestionList.size(),reasoningQuestionList.size()};
        int[] rightAnswers = {gkMarks,mathsMarks,englishMarks,aptitudeMarks,reasoningMarks};
        int[] wrongAnswers = {gkQuestionList.size()-gkMarks,mathsQuestionList.size()-mathsMarks,englishQuestionList.size()-englishMarks,aptitudeQuestionList.size()-aptitudeMarks,reasoningQuestionList.size()-reasoningMarks};

        String [] columns = {" Subject "," Total Questions "," Right Answers  "," Wrong Answers " };
        String[][] data = new String[5][4];

        int totalMarks = 0;
        for (int i = 0; i < data.length; i++) {
                data[i][0] = subject[i];
                data[i][1] = String.valueOf(totalQuestion[i]);
                data[i][2] = String.valueOf(rightAnswers[i]);
                data[i][3] = String.valueOf(wrongAnswers[i]);

            totalMarks += rightAnswers[i];
        }

        String finalResult = totalMarks >= Student.getExamCutoff() ? " PASS " : " FAIL ";

        //Update Student Result
        student.setResult(totalMarks);
        if(! new StudentController().updateResult(student))
        {
            JOptionPane.showMessageDialog(frame,"Result is not updated");
            return;
        }

        JTable resultTable = new JTable(data,columns);
        resultTable.setDefaultEditor(Object.class, null);

        // Center-align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < resultTable.getColumnCount(); i++) {
            resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(resultTable);

        mainPanel.removeAll();

        JLabel label = new JLabel("Final Result : "+totalMarks+" (Total Marks) "+finalResult);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(f);
        mainPanel.add(label,BorderLayout.NORTH);
        mainPanel.add(scrollPane,BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

}
