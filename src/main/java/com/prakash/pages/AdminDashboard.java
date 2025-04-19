package com.prakash.pages;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.prakash.controller.QuestionController;
import com.prakash.controller.StudentController;
import com.prakash.entity.Question;
import com.prakash.entity.Student;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.jdesktop.swingx.JXRadioGroup;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class AdminDashboard {

    Font f = new Font("SansSerif", Font.BOLD, 14);
    //Panels
    JPanel welcomePanel,mainPanel,sidebarPanel,showAllStudents,cutoffPanel,resultPanel,uploadPanel,uploadQuestionsPanel;
    CardLayout cardLayout;

    public AdminDashboard(JFrame frame){

        JPanel mainPanel1 = new JPanel(new BorderLayout());

        FlatMacLightLaf.setup();
        frame.setTitle("Admin DashBoard");

        // Theme Toggle Button
        GridBagConstraints gbcTheme = new GridBagConstraints();
        gbcTheme.gridx = 1;
        gbcTheme.anchor = GridBagConstraints.NORTHEAST;
        gbcTheme.insets = new Insets(10, 10, 0, 50);

        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        themePanel.add(LoginPage.modeButton(frame));
        mainPanel1.add(themePanel,BorderLayout.NORTH);


        //sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS)); //align all button in vertical
        sidebarPanel.setSize(new Dimension(200,850));
        sidebarPanel.setBackground(UIManager.getColor("frame.background"));


        JButton uploadStudentsCredentials = new JButton("Upload Students Credentials ");
        JButton uploadQuestions = new JButton("Upload Questions");
        JButton deleteQuestions = new JButton("Delete Questions");
        JButton manageStudents = new JButton("ManageStudents");
        JButton seeAllStudents = new JButton("All Students");
        JButton setCutoff = new JButton("Set Cutoff");
        JButton result = new JButton("Result");


        Dimension buttonSize = new Dimension(100,60);
        uploadQuestions.setSize(buttonSize);
        deleteQuestions.setSize(buttonSize);
        manageStudents.setSize(buttonSize);
        uploadStudentsCredentials.setSize(buttonSize);
        seeAllStudents.setSize(buttonSize);
        setCutoff.setSize(buttonSize);
        result.setSize(buttonSize);

        sidebarPanel.add(Box.createVerticalStrut(20)); //spacing
        sidebarPanel.add(uploadStudentsCredentials);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(uploadQuestions);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(deleteQuestions);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(manageStudents);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(seeAllStudents);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(setCutoff);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(result);
        sidebarPanel.add(Box.createHorizontalGlue());//push all buttons to top


        mainPanel1.add(sidebarPanel,BorderLayout.WEST);



        //Main Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        welcomePanel = new JPanel();
        welcomePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        JLabel welcomeLabel = new JLabel("<html><h1>Welcome, Admin...!</h1><p>Manage exams efficiently.</p></html>");
        welcomePanel.add(welcomeLabel);
        mainPanel.add(welcomePanel);

        cardLayout.show(mainPanel,"WelcomePanel"); // 2nd parameter is an identifier for card layout,card layout show the panel in main panel which have welcomePanel name
        mainPanel1.add(mainPanel,BorderLayout.CENTER);

        frame.add(mainPanel1);
        frame.setVisible(true);



        //manually Add Students form
        //form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //  Form Fields
        JLabel nameLabel = new JLabel("Name : ");
        JTextField nameText = new JTextField(15);
        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailText = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password : ");
        JPasswordField passwordField = new JPasswordField(15);

        nameLabel.setFont(f);
        emailLabel.setFont(f);
        passwordLabel.setFont(f);

        // Adding components to form panel
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(nameText, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; formPanel.add(emailText, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; formPanel.add(passwordField, gbc);


        JButton addmore = new JButton(" Add More ");
        JButton save = new JButton(" Save All Students");
        addmore.setFont(f);
        save.setFont(f);

        //bottompanel
        JPanel addStudentBottomPanel = new JPanel(new FlowLayout());
        addStudentBottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Top, Left, Bottom, Right padding
        addStudentBottomPanel.add(addmore);
        addStudentBottomPanel.add(save);

        JPanel allStudentsTopPanel = new JPanel(new FlowLayout());

        // Create JTextArea without column width, but set preferred size
        JTextArea studentsData = new JTextArea(5, 30);
        studentsData.setEditable(false); // Optional: Make it read-only
        studentsData.setLineWrap(true); // Enable word wrapping
        studentsData.setWrapStyleWord(true);
        JScrollPane studentsScrollPane = new JScrollPane(studentsData);
        studentsScrollPane.setPreferredSize(new Dimension(500, 100)); // Set fixed size
        JLabel allstudentsData = new JLabel("Added Students Data");
        allStudentsTopPanel.add(allstudentsData);
        allStudentsTopPanel.add(studentsScrollPane);

        CardLayout cardLayoutformanage = new CardLayout();

        List<Student> studentsForAdd = new ArrayList<>();

        JPanel manageStudentsPanel = new JPanel(cardLayoutformanage);
        JPanel manageStudentTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JMenuItem addStudents = new JMenuItem("ADD");
        JMenuItem deleteStudents = new JMenuItem("DELETE");
        JMenuItem deleteAllStudents = new JMenuItem("DELETE ALL STUDENTS");
        JMenuItem updateStudent = new JMenuItem("UPDATE");

        JPanel mainPanelOfStudentAdd = new JPanel(new BorderLayout());
        mainPanelOfStudentAdd.add(allStudentsTopPanel,BorderLayout.NORTH);
        mainPanelOfStudentAdd.add(formPanel,BorderLayout.CENTER);
        mainPanelOfStudentAdd.add(addStudentBottomPanel,BorderLayout.SOUTH);
        JPanel deletePanel = new JPanel(new FlowLayout());
        JPanel updateMainPanel = new JPanel(new BorderLayout());

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(addStudents);
        popupMenu.add(deleteStudents);
        popupMenu.add(updateStudent);
        popupMenu.add(deleteAllStudents);

        //show students
        seeAllStudents.addActionListener(e -> {

            cardLayout.removeLayoutComponent(mainPanel);

            showAllStudents = new JPanel(new BorderLayout());
            showAllStudents.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Top, Left, Bottom, Right padding
            JLabel description = new JLabel("All Students",SwingConstants.CENTER);
            description.setFont(f);
            showAllStudents.add(description,BorderLayout.NORTH);

            //student table
            String[] columns = {" Id "," Name "," E-mail "," Password"};

            List<Student> studentList = new StudentController().getAllStudents();
            if(studentList.isEmpty())
            {
                JOptionPane.showMessageDialog(frame,"Students List is empty","Error",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String[][]  data = new String[studentList.size()][4];

                for (int i = 0; i < studentList.size(); i++)
                {
                    for (int j = 0 ;j < 4; j++)
                    {
                        Student student = studentList.get(i);

                        data[i][j] = switch (j)
                        {
                            case 0 -> String.valueOf(student.getId());
                            case 1 -> student.getName();
                            case 2 -> student.getEmail();
                            case 3 -> student.getPassword();
                            default -> "0";
                        };
                    }
                }

                JXTable table1 = new JXTable(data,columns);
                table1.setEditable(false);
                table1.setColumnControlVisible(true);
                table1.setSortable(true);

                // Styling Enhancements
                table1.setHighlighters(HighlighterFactory.createSimpleStriping());
                table1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                table1.setRowHeight(25);
                table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
                table1.getTableHeader().setForeground(Color.WHITE);
                table1.getTableHeader().setBackground(new Color(50, 50, 50));
                table1.setSelectionBackground(new Color(184, 207, 229));
                table1.setSelectionForeground(Color.BLACK);
                table1.packAll();

                JScrollPane scrollPane = new JScrollPane(table1);

                // Center-align table content
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table1.getColumnCount(); i++) {
                    table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                showAllStudents.add(scrollPane,BorderLayout.CENTER);

                mainPanel.add(showAllStudents,"Students");
                cardLayout.show(mainPanel,"Students");

            }


        });

        //Manage Students
        manageStudents.addActionListener(e -> {
             popupMenu.show(manageStudents, 0, manageStudents.getHeight());
            mainPanel.add(manageStudentsPanel,"ManageStudents");
            cardLayout.show(mainPanel,"ManageStudents");

        });

        //Add Students Manuaaly
        addStudents.addActionListener(e -> {

            mainPanel.add(mainPanelOfStudentAdd,"AddStudents");
            cardLayout.show(mainPanel,"AddStudents");
        });

        //Delete Students
        deleteStudents.addActionListener(e -> {

            JLabel label = new JLabel("Enter student email ");
            JTextField emailDelete = new JTextField(20);
            JButton deleteIt = new JButton("Delete");
            deletePanel.add(label);
            deletePanel.add(emailDelete);
            deletePanel.add(deleteIt);

//            manageStudentsPanel.add(deletePanel,"DeleteStudents");
//            cardLayoutformanage.show(manageStudentsPanel,"DeleteStudents");

            mainPanel.add(deletePanel,"DeleteStudents");
            cardLayout.show(mainPanel,"DeleteStudents");



            deleteIt.addActionListener(e1 -> {
                if( ! new StudentController().deleteStudentByEmail(emailDelete.getText()))
                {
                    JOptionPane.showMessageDialog(frame,"Student is not present","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frame,"Student deleted successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            });

        });

        //update students
        updateStudent.addActionListener(e ->{

            manageStudentsPanel.add(manageStudentTopPanel);
            manageStudentsPanel.remove(mainPanelOfStudentAdd);

            JPanel updateTopPanel = new JPanel(new FlowLayout());
            JLabel label = new JLabel("Enter student email ");
            JTextField emailForUpdate = new JTextField(20);
            JButton showStudent = new JButton("Show Details");
            updateTopPanel.add(label);
            updateTopPanel.add(emailForUpdate);
            updateTopPanel.add(showStudent);

            updateMainPanel.add(updateTopPanel,BorderLayout.NORTH);

            JPanel studentDetails = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
            JLabel namelabel = new JLabel("Name ");
            JTextField name = new JTextField(20);
            JLabel emaillabel = new JLabel(" Email ");
            JTextField email = new JTextField(20);
            JLabel passwordlabel = new JLabel(" Password ");
            JTextField password = new JTextField(20);
            JLabel Verifiedlabel = new JLabel(" Verified ");
            JTextField verified = new JTextField(20);
            JButton update = new JButton("Update Details");
            studentDetails.add(namelabel);
            studentDetails.add(name);
            studentDetails.add(emaillabel);
            studentDetails.add(email);
            studentDetails.add(passwordlabel);
            studentDetails.add(password);
            studentDetails.add(Verifiedlabel);
            studentDetails.add(verified);
            studentDetails.add(update);

            updateMainPanel.add(studentDetails,BorderLayout.CENTER);

//            manageStudentsPanel.add(updateMainPanel,"UpdateStudents");
//            cardLayoutformanage.show(manageStudentsPanel,"UpdateStudents");

            mainPanel.add(updateMainPanel,"UpdateStudents");
            cardLayout.show(mainPanel,"UpdateStudents");


            update.addActionListener(e2 -> {

                String updatedName = name.getText();
                String updatedEmail = email.getText();
                String updatedPassword = String.valueOf(password.getText());
                String updatedVerified = verified.getText();

                if(updatedName.length() < 3){JOptionPane.showMessageDialog(frame,"Name length must be greater than 3 ","Error",JOptionPane.ERROR_MESSAGE);return;}
                else if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",updatedEmail)){JOptionPane.showMessageDialog(frame,"Invalid mail","Error",JOptionPane.ERROR_MESSAGE);return;}
                else if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",updatedPassword)){JOptionPane.showMessageDialog(frame,"Password must be Strong","Error",JOptionPane.ERROR_MESSAGE);return;}
                else if( !updatedVerified.equalsIgnoreCase("YES") && !updatedVerified.equalsIgnoreCase("NO") ){JOptionPane.showMessageDialog(frame,"Verified must be Yes or No","Error",JOptionPane.ERROR_MESSAGE);return;}

                Student student = new Student();
                student.setName(name.getText());
                student.setEmail(email.getText());
                student.setPassword(String.valueOf(password.getText()));
                student.setVerified(updatedVerified.equalsIgnoreCase("YES") ? true : false);

                if(! new StudentController().updateStudentByEmail(emailForUpdate.getText(),student))
                {
                    JOptionPane.showMessageDialog(frame,"Student is not present","Error",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,"Student Details Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            });



            showStudent.addActionListener(e1 -> {

                Student student = new StudentController().getStudentByEmail(emailForUpdate.getText());
                if(student == null )
                {
                    JOptionPane.showMessageDialog(frame,"Student is not present","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                name.setText(student.getName());
                email.setText(student.getEmail());
                password.setText(student.getPassword());
                verified.setText(!student.isVerified() ? "Yes" : "No");

            });


        });

        //delete all students
        deleteAllStudents.addActionListener(e ->{

            if(! new StudentController().deleteAllStudent()){
                JOptionPane.showMessageDialog(frame,"No Student Data are present","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame,"Students data deleted successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //Manage Buttons for new Student Entry
        addmore.addActionListener(e1 -> {

            String name = nameText.getText();
            String email = emailText.getText();
            String password = String.valueOf(passwordField.getPassword());

            if(name.length() < 3)
                JOptionPane.showMessageDialog(frame,"Name length must be greater than 3 ","Error",JOptionPane.ERROR_MESSAGE);

            else if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",email))
                JOptionPane.showMessageDialog(frame,"Invalid mail","Error",JOptionPane.ERROR_MESSAGE);

            else if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",password))
                JOptionPane.showMessageDialog(frame,"Password must be Strong","Error",JOptionPane.ERROR_MESSAGE);
            else {

                studentsForAdd.add(new Student(name,email,password));
                studentsData.append(studentsForAdd.size()+". "+name+",  "+email+",  "+password+" \n");
                studentsData.revalidate();
                studentsData.repaint();
                nameText.setText("");
                emailText.setText("");
                passwordField.setText("");

            }
        });

        //Save Added Students
        save.addActionListener(e1 -> {
            if(studentsForAdd.isEmpty())
                JOptionPane.showMessageDialog(frame,"No Student Data Is present","Error",JOptionPane.ERROR_MESSAGE);
            else
            {
                if(! new StudentController().saveAllStudentsManually(studentsForAdd))
                {
                    JOptionPane.showMessageDialog(frame,"Email must be unique for all students","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(frame,"Student Data Saved","Success",JOptionPane.INFORMATION_MESSAGE);

            }

        });

        //upload Questions
        uploadQuestions.addActionListener(e -> {

            uploadQuestionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton chooseFile = new JButton("Choose Excel File");
            JButton upload = new JButton("upload file");
            JTextField filePath = new JTextField(15);
            filePath.setEditable(false);


            uploadQuestionsPanel.add(chooseFile);
            uploadQuestionsPanel.add(upload);
            uploadQuestionsPanel.add(filePath);

            mainPanel.add(uploadQuestionsPanel,"uploadQuestions");
            cardLayout.show(mainPanel,"uploadQuestions");


            //File Choose
            chooseFile.addActionListener(e1 -> {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    filePath.setText(file.getAbsolutePath());
                }
            });


            //File Upload
            upload.addActionListener(e2 -> {

                String path = filePath.getText();

                //add file data to database
                if(!path.isEmpty())
                {
                    try {
                        FileInputStream fis = new FileInputStream(path);
                        Workbook workBook = WorkbookFactory.create(fis);

                        Sheet sheet = workBook.getSheetAt(0);

                        List<Question> questions = new ArrayList<>();

                        for (Row row : sheet)
                        {
                            if(row.getRowNum() == 0) continue; //skip header

                            Question question = new Question();

                            question.setQuestion(row.getCell(0).getStringCellValue());//Question
                            question.setOption_A(row.getCell(1).getStringCellValue());
                            question.setOption_B(row.getCell(2).getStringCellValue());
                            question.setOption_C(row.getCell(3).getStringCellValue());
                            question.setOption_D(row.getCell(4).getStringCellValue());
                            question.setCorrect_answer(row.getCell(5).getStringCellValue());
                            question.setSection(row.getCell(6).getStringCellValue());

                            questions.add(question);

                        }

                        String[] columns = {" Question "," Option_A "," Option_B "," Option_C "," Option_D "," CorrectAnswer "," Section "};
                        String[][] data = new String[questions.size()][7];

                        if(questions.isEmpty())
                        {
                            data =  new String[][]{{"0","0","0","0","0","0","0"}};
                        }
                        else {

                            if( ! new QuestionController().saveAllQuestions(questions))
                            {
                                JOptionPane.showMessageDialog(frame,"All Questions must be unique","Error",JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            for (int i = 0; i < questions.size() ; i++) {

                                for (int j = 0; j < 7; j++) {

                                    Question question = questions.get(i);

                                    data[i][j] = switch (j){
                                        case 0 -> question.getQuestion();
                                        case 1 -> question.getOption_A();
                                        case 2 -> question.getOption_B();
                                        case 3 -> question.getOption_C();
                                        case 4 -> question.getOption_D();
                                        case 5 -> question.getCorrect_answer();
                                        case 6 -> question.getSection();
                                        default-> "0";
                                    };
                                }

                            }
                        }

                        // Remove Buttons Panel after Upload
                        mainPanel.remove(uploadQuestionsPanel);

                        // Refresh UI
                        mainPanel.revalidate();
                        mainPanel.repaint();

                        JXTable table1 = new JXTable(data,columns);
                        table1.setEditable(false);
                        table1.setColumnControlVisible(true);
                        table1.setSortable(true);

                        // Styling Enhancements
                        table1.setHighlighters(HighlighterFactory.createSimpleStriping());
                        table1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        table1.setRowHeight(25);
                        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
                        table1.getTableHeader().setForeground(Color.WHITE);
                        table1.getTableHeader().setBackground(new Color(50, 50, 50));
                        table1.setSelectionBackground(new Color(184, 207, 229));
                        table1.setSelectionForeground(Color.BLACK);
                        table1.packAll();
                        JScrollPane scrollPane = new JScrollPane(table1);

                        //Make All the data in center column vice
                        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
                        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                        for (int i = 0; i < table1.getColumnCount(); i++) {
                            table1.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
                        }

                        JPanel tablePanel = new JPanel(new BorderLayout());
                        JLabel uploadedQuestions = new JLabel("Uploaded Questions",SwingConstants.CENTER);
                        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Top, Left, Bottom, Right padding
                        uploadedQuestions.setFont(f);
                        tablePanel.add(uploadedQuestions,BorderLayout.NORTH);
                        tablePanel.add(scrollPane,BorderLayout.CENTER);

                        mainPanel.add(tablePanel,"UploadedStudentData");
                        cardLayout.show(mainPanel,"UploadedStudentData");


                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        });

        //upload excel file
        uploadStudentsCredentials.addActionListener(e -> {

            uploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            JButton chooseFile = new JButton("Choose Excel File");
            JButton upload = new JButton("upload file");
            JTextField filePath = new JTextField(15);
            filePath.setEditable(false);


            uploadPanel.add(chooseFile);
            uploadPanel.add(upload);
            uploadPanel.add(filePath);

            mainPanel.add(uploadPanel,"UploadStudentData");
            cardLayout.show(mainPanel,"UploadStudentData");


            //File Choose
            chooseFile.addActionListener(e1 -> {
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    filePath.setText(file.getAbsolutePath());
                }
            });


            //File Upload
            upload.addActionListener(e2 -> {

                String path = filePath.getText();

                //add file data to database
                if(!path.isEmpty())
                {
                    try {
                        FileInputStream fis = new FileInputStream(path);
                        Workbook workBook = WorkbookFactory.create(fis);

                        Sheet sheet = workBook.getSheetAt(0);

                        List<Student> students = new ArrayList<>();

                        for (Row row : sheet)
                        {
                            if(row.getRowNum() == 0) continue; //skip header

                            Student student = new Student();

                            student.setId((int)row.getCell(0).getNumericCellValue()); //ID
                            student.setName(row.getCell(1).getStringCellValue());//Name
                            student.setEmail(row.getCell(2).getStringCellValue());//Email
                            student.setPassword(row.getCell(3).getStringCellValue());//Password
                            student.setVerified(true);
                            students.add(student);

                        }

                        String[] columns = {" ID "," Name "," Email "," Password "};
                        String[][] data = new String[students.size()][4];

                        if(students.isEmpty())
                        {
                            data =  new String[][]{{"0","0","0","0"}};
                        }
                        else {

                            if( ! new StudentController().saveAllStudents(students))
                            {
                                JOptionPane.showMessageDialog(frame,"Email must be unique for all students","Error",JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            for (int i = 0; i < students.size() ; i++) {

                                for (int j = 0; j < 4; j++) {

                                    Student student = students.get(i);

                                    data[i][j] = switch (j){
                                        case 0 -> String.valueOf(student.getId());
                                        case 1 -> student.getName();
                                        case 2 -> student.getEmail();
                                        case 3 -> student.getPassword();
                                        default-> "0";
                                    };
                                }

                            }
                        }

                        // Remove Buttons Panel after Upload
                        mainPanel.remove(uploadPanel);

                        // Refresh UI
                        mainPanel.revalidate();
                        mainPanel.repaint();

                        JXTable table1 = new JXTable(data,columns);
                        table1.setEditable(false);
                        table1.setColumnControlVisible(true);
                        table1.setSortable(true);

                        // Styling Enhancements
                        table1.setHighlighters(HighlighterFactory.createSimpleStriping());
                        table1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        table1.setRowHeight(25);
                        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
                        table1.getTableHeader().setForeground(Color.WHITE);
                        table1.getTableHeader().setBackground(new Color(50, 50, 50));
                        table1.setSelectionBackground(new Color(184, 207, 229));
                        table1.setSelectionForeground(Color.BLACK);
                        table1.packAll();
                        JScrollPane scrollPane = new JScrollPane(table1);

                        //Make All the data in center column vice
                        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
                        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                        for (int i = 0; i < table1.getColumnCount(); i++) {
                         table1.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
                        }

                        JPanel tablePanel = new JPanel(new BorderLayout());
                        JLabel uploadedStudentData = new JLabel("Uploaded Student Data",SwingConstants.CENTER);
                        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Top, Left, Bottom, Right padding
                        uploadedStudentData.setFont(f);
                        tablePanel.add(uploadedStudentData,BorderLayout.NORTH);
                        tablePanel.add(scrollPane,BorderLayout.CENTER);

                        mainPanel.add(tablePanel,"UploadedStudentData");
                        cardLayout.show(mainPanel,"UploadedStudentData");


                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });



        });

        //set cutoff
        setCutoff.addActionListener(e ->{

            cutoffPanel = new JPanel();
            cutoffPanel.setLayout(new BoxLayout(cutoffPanel, BoxLayout.Y_AXIS));// Vertical alignment
            cutoffPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel defaultCutoff = new JLabel("Current Cutoff :");
            JTextField currCutofff = new JTextField(10);
            currCutofff.setText(String.valueOf(Student.getExamCutoff()));
            JLabel label = new JLabel("Update Cutoff :");
            JTextField cutoff = new JTextField(5);
            JButton set = new JButton("Set");

            defaultCutoff.setFont(f);
            currCutofff.setFont(f);
            label.setFont(f);
            cutoff.setFont(f);
            set.setFont(f);

            defaultCutoff.setMaximumSize(new Dimension(300,20));
            currCutofff.setMaximumSize(new Dimension(100,100));
            label.setMaximumSize(new Dimension(300,20));
            cutoff.setMaximumSize(new Dimension(50,50));
            set.setMaximumSize(new Dimension(100,100));

            // Center align components
            defaultCutoff.setAlignmentX(Component.CENTER_ALIGNMENT);
            currCutofff.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            cutoff.setAlignmentX(Component.CENTER_ALIGNMENT);
            set.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add spacing between components
            cutoffPanel.add(Box.createVerticalGlue()); // Push content to the center
            cutoffPanel.add(defaultCutoff);
            cutoffPanel.add(Box.createRigidArea(new Dimension(0,20)));
            cutoffPanel.add(currCutofff);
            cutoffPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
            cutoffPanel.add(label);
            cutoffPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
            cutoffPanel.add(cutoff);
            cutoffPanel.add(Box.createRigidArea(new Dimension(0,20)));
            cutoffPanel.add(set);
            cutoffPanel.add(Box.createVerticalGlue()); // Push content to the center

            mainPanel.add(cutoffPanel,"CutoffPanel");
            cardLayout.show(mainPanel,"CutoffPanel");

            //setcutoff
            set.addActionListener(e3 ->{

                String s = cutoff.getText();
                boolean flag = true;
                for (int i = 0; i < s.length() ; i++) {
                    if( !Character.isDigit(s.charAt(i)) && s.charAt(i) != '.' || (s.charAt(i) == '.' && i == 0 ) ){
                        flag = false;
                        break;
                    }
                }
                if(flag)
                {
                    Student.setExamCutoff(Double.parseDouble(cutoff.getText()) );
                    currCutofff.setText(String.valueOf(Double.parseDouble(cutoff.getText())));
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,"Cutoff must be in Numeric");
                }
            });



        });

        //result
        result.addActionListener(e -> {

            resultPanel = new JPanel(new BorderLayout());
            resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Top, Left, Bottom, Right padding

            // Create a panel for the button and description
            JPanel topPanel = new JPanel(new FlowLayout());
            JButton downloadButton = new JButton("Download Results");
            downloadButton.setFont(f);
            topPanel.add(downloadButton);
            resultPanel.add(topPanel,BorderLayout.NORTH);

            //get all results from database
            List<Student> studentList = new StudentController().getAllStudentsResult();

            //student table
            if(studentList.isEmpty())
            {
                JOptionPane.showMessageDialog(frame,"Result list is Empty","Error",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String[] columns = {" Id "," Name "," Marks "," Result "};
                String[][] data = new String[studentList.size()][4];

                for (int i = 0; i < studentList.size(); i++)
                {
                    for (int j = 0 ;j < 4; j++)
                    {
                        Student student = studentList.get(i);

                        data[i][j] = switch (j)
                        {
                            case 0 -> String.valueOf(student.getId());
                            case 1 -> student.getName();
                            case 2 -> String.valueOf(student.getResult());
                            case 3 -> student.getResult() >= Student.examCutoff ? " PASS " : " FAIL ";
                            default -> "0";
                        };
                    }//inner for loop
                }//otuer for loop

                JXTable table1 = new JXTable(data,columns);
                table1.setEditable(false);
                table1.setColumnControlVisible(true);
                table1.setSortable(true);

                // Styling Enhancements
                table1.setHighlighters(HighlighterFactory.createSimpleStriping());
                table1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                table1.setRowHeight(25);
                table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
                table1.getTableHeader().setForeground(Color.WHITE);
                table1.getTableHeader().setBackground(new Color(50, 50, 50));
                table1.setSelectionBackground(new Color(184, 207, 229));
                table1.setSelectionForeground(Color.BLACK);
                table1.packAll();
                JScrollPane scrollPane = new JScrollPane(table1);


                // Center-align table content
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < table1.getColumnCount(); i++) {
                    table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                resultPanel.add(scrollPane,BorderLayout.CENTER);
                mainPanel.add(resultPanel,"Result");
                cardLayout.show(mainPanel,"Result");

            }//else


            //Download Button choose a save path and create an Excel file for download
            downloadButton.addActionListener(e1 -> {

                if(studentList.isEmpty())
                {
                    JOptionPane.showMessageDialog(frame,"Result list is Empty","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save File");

                    if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
                    {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        if (!filePath.endsWith(".xlsx")) {
                            filePath += ".xlsx";
                        }


                        Workbook workbook = new XSSFWorkbook();
                        Sheet sheet = workbook.createSheet("Student Results");
                        Row header = sheet.createRow(0);
                        String[] columns = {" Id "," Name "," Marks "," Result "};
                        for (int i = 0; i < columns.length; i++) {
                            header.createCell(i).setCellValue(columns[i]);
                        }

                        //Read from Students list
                        int row = 1;
                        for (Student student : studentList)
                        {
                            Row row1 = sheet.createRow(row++);
                            row1.createCell(0).setCellValue(student.getId());
                            row1.createCell(1).setCellValue(student.getName());
                            row1.createCell(2).setCellValue(student.getResult());
                            row1.createCell(3).setCellValue(student.getResult() >= Student.examCutoff ? " PASS " : " FAIL ");
                        }
                        // Auto-size columns
                        for (int i = 0; i < columns.length; i++) {
                            sheet.autoSizeColumn(i);
                        }

                        try(FileOutputStream fos = new FileOutputStream(filePath)) {
                            workbook.write(fos);
                            workbook.close();
                        }  catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    } //file chooser
                }//else

            });


        });

        //delete
        deleteQuestions.addActionListener(e -> {

            JPanel deleteQuestionsPanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new FlowLayout());
            JButton deleteAll = new JButton("Delete All");
            JButton deleteSelected = new JButton("Delete Selected");
            topPanel.add(deleteAll);
            topPanel.add(deleteSelected);

            //Center
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            JLabel gk = new JLabel("GK Questions");
            JLabel maths = new JLabel("Maths Questions");
            JLabel english = new JLabel("English Questions");
            JLabel aptitude = new JLabel("Aptitude Questions");
            JLabel reasoning = new JLabel("Reasoning Questions");

            gk.setHorizontalAlignment(SwingConstants.CENTER);
            maths.setHorizontalAlignment(SwingConstants.CENTER);
            english.setHorizontalAlignment(SwingConstants.CENTER);
            aptitude.setHorizontalAlignment(SwingConstants.CENTER);
            reasoning.setHorizontalAlignment(SwingConstants.CENTER);


//            JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
            List<Question> gkQuestions = new QuestionController().getAllQuestionsBySection("GK");
            List<Question> mathsQuestions = new QuestionController().getAllQuestionsBySection("Maths");
            List<Question> englishQuestions = new QuestionController().getAllQuestionsBySection("English");
            List<Question> aptitudeQuestions = new QuestionController().getAllQuestionsBySection("Aptitude");
            List<Question> reasoningQuestions = new QuestionController().getAllQuestionsBySection("Reasoning");

            List<JCheckBox> checkboxList = new ArrayList<>();

            centerPanel.add(new JLabel("    "));
            centerPanel.add(gk);
            for (Question q : gkQuestions)
            {
                JCheckBox cb = new JCheckBox(q.getQuestion());
                checkboxList.add(cb);
                centerPanel.add(cb);
            }
            centerPanel.add(new JLabel("    "));
            centerPanel.add(maths);
            for (Question q : mathsQuestions)
            {
                JCheckBox cb = new JCheckBox(q.getQuestion());
                checkboxList.add(cb);
                centerPanel.add(cb);
            }
            centerPanel.add(new JLabel("    "));
            centerPanel.add(english);
            for (Question q : englishQuestions)
            {
                JCheckBox cb = new JCheckBox(q.getQuestion());
                checkboxList.add(cb);
                centerPanel.add(cb);
            }
            centerPanel.add(new JLabel("    "));
            centerPanel.add(aptitude);
            for (Question q : aptitudeQuestions)
            {
                JCheckBox cb = new JCheckBox(q.getQuestion());
                checkboxList.add(cb);
                centerPanel.add(cb);
            }
            centerPanel.add(new JLabel("    "));
            centerPanel.add(reasoning);
            for (Question q : reasoningQuestions)
            {
                JCheckBox cb = new JCheckBox(q.getQuestion());
                checkboxList.add(cb);
                centerPanel.add(cb);
            }

            centerPanel.revalidate();
            centerPanel.repaint();
            JScrollPane scrollPane = new JScrollPane(centerPanel);
            scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Increase scroll speed

            deleteQuestionsPanel.add(topPanel,BorderLayout.NORTH);

            if(gkQuestions.isEmpty() &&mathsQuestions.isEmpty()&&englishQuestions.isEmpty()&&aptitudeQuestions.isEmpty()&&reasoningQuestions.isEmpty())
            {
                JOptionPane.showMessageDialog(frame,"Their is no questions present","Error",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                deleteQuestionsPanel.add(scrollPane,BorderLayout.CENTER);
                mainPanel.add(deleteQuestionsPanel,"DeleteQuestions");
                cardLayout.show(mainPanel,"DeleteQuestions");
            }


            deleteSelected.addActionListener(e1 -> {
                List<String> questionsForDeletion = new ArrayList<>();
                for (JCheckBox cb : checkboxList){
                    if(cb.isSelected()){
                        questionsForDeletion.add(cb.getText());
                    }
                }

                 new QuestionController().deleteSelectedQuestions(questionsForDeletion);
                JOptionPane.showMessageDialog(frame,"Selected Questions Deleted Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            });

            deleteAll.addActionListener( e2 -> {
                if (! new QuestionController().deleteAllQuestions())
                {
                    JOptionPane.showMessageDialog(frame,"Some Error Occured","Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frame,"All Questions Deleted Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            });
        });


    }
    }
