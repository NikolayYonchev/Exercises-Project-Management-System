package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class AppFrame extends JFrame {

    private JPanel contentPanel;
    private JList<Exercise> listExercises;
    private JList<Project> listProjects;
    private JList<Exercise> listProjectExercises;
    private JTextField textFieldTitle;
    private JTextField textFieldDescExercise;
    private JTextField textFieldStatus;
    private JTextField textFieldPriority;
    private JButton buttonCreateNewExercise;
    private JButton buttonDeleteExercise;
    private JButton buttonUpdateExercise;
    private JButton buttonDisplayExercise;
    private JTextField textFieldName;
    private JTextField textFieldDescProject;
    private JTextField textFieldExpiryDate;
    private JButton buttonDisplayProject;
    private JButton buttonAddExerciseToProject;
    private JButton buttonDeleteProject;
    private JButton buttonUpdateProject;
    private JButton buttonCreateNewProject;

    DefaultListModel<Exercise> model = new DefaultListModel<>();
    DefaultListModel<Project> modelProjects = new DefaultListModel<>();
    DefaultListModel<Exercise> modelProjectExercises = new DefaultListModel<>();

    public AppFrame() {
        initializeUI();

        this.setContentPane(contentPanel);
        this.pack();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeUI() {


        listExercises.setModel(model);
        listExercises.addListSelectionListener(new ExerciseSelectionListener());

        listProjects.setModel(modelProjects);
        listProjects.addListSelectionListener(new ProjectSelectionListener());

        listProjectExercises.setModel(modelProjectExercises);

        model.addElement(new Exercise("exercise 1", "1", "processing", "low"));
        model.addElement(new Exercise("exercise 2", "1", "done", "high"));

        modelProjects.addElement(new Project("project 1", "1", "21st"));


        buttonCreateNewExercise.addActionListener(new AddNewExerciseButton());
        buttonUpdateExercise.addActionListener(new UpdateExerciseButton());
        buttonDeleteExercise.addActionListener(new DeleteExerciseButton());
        buttonDisplayExercise.addActionListener(new DisplayExerciseButton());

        buttonCreateNewProject.addActionListener(new AddNewProjectButton());
        buttonUpdateProject.addActionListener(new UpdateProjectButton());
        buttonDeleteProject.addActionListener(new DeleteProjectButton());
        buttonAddExerciseToProject.addActionListener(new AddExerciseToProjectButton());
        buttonDisplayProject.addActionListener(new DisplayProjectButton());

    }

    public class ExerciseSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Exercise selectedExercise = listExercises.getSelectedValue();
            if (selectedExercise != null) {
                textFieldTitle.setText("");
                textFieldDescExercise.setText("");
                textFieldStatus.setText("");
                textFieldPriority.setText("");
                buttonUpdateExercise.setEnabled(true);
                buttonDeleteExercise.setEnabled(true);
                buttonDisplayExercise.setEnabled(true);
            } else {
                buttonUpdateExercise.setEnabled(false);
                buttonDeleteExercise.setEnabled(false);
                buttonDisplayExercise.setEnabled(false);
            }

        }
    }

    public class ProjectSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Project selectedProject = listProjects.getSelectedValue();
            if (selectedProject != null) {
                textFieldName.setText("");
                textFieldDescProject.setText("");
                textFieldExpiryDate.setText("");

                modelProjectExercises.clear();

                buttonUpdateProject.setEnabled(true);
                buttonDeleteProject.setEnabled(true);
                buttonDisplayProject.setEnabled(true);
                buttonAddExerciseToProject.setEnabled(true);
            } else {
                buttonUpdateProject.setEnabled(false);
                buttonDeleteProject.setEnabled(false);
                buttonDisplayProject.setEnabled(false);
                buttonAddExerciseToProject.setEnabled(false);
            }
        }
    }

    public class AddNewExerciseButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Exercise exercise = new Exercise(
                    textFieldTitle.getText(),
                    textFieldDescExercise.getText(),
                    textFieldStatus.getText(),
                    textFieldPriority.getText()
            );
            model.addElement(exercise);
            textFieldTitle.setText("");
            textFieldDescExercise.setText("");
            textFieldStatus.setText("");
            textFieldPriority.setText("");

        }
    }

    public class UpdateExerciseButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listExercises.getSelectedIndex();

            Exercise selectedExercise = model.get(selectedIndex);

            selectedExercise.setTitle(textFieldTitle.getText());
            selectedExercise.setDescription(textFieldDescExercise.getText());
            selectedExercise.setStatus(textFieldStatus.getText());
            selectedExercise.setPriority(textFieldPriority.getText());

            model.setElementAt(selectedExercise, selectedIndex);
        }
    }

    public class DeleteExerciseButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listExercises.getSelectedIndex();

            if (!listExercises.isSelectionEmpty()) {
                int choice = showConfirmationDialog();
                if (choice == JOptionPane.YES_OPTION) {
                    Exercise selectedExercise = model.get(selectedIndex);

                    model.removeElement(selectedExercise);

                }
            }
        }

        public int showConfirmationDialog() {
            int result = JOptionPane.showConfirmDialog(
                    AppFrame.this,
                    "Are you sure?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            return result;
        }

    }

    public class DisplayExerciseButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Exercise selectedExercise = listExercises.getSelectedValue();
            textFieldTitle.setText(selectedExercise.getTitle());
            textFieldDescExercise.setText(selectedExercise.getDescription());
            textFieldStatus.setText(selectedExercise.getStatus());
            textFieldPriority.setText(selectedExercise.getPriority());

        }
    }

    public class AddNewProjectButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonCreateNewProject) {
                Project project = new Project(
                        textFieldName.getText(),
                        textFieldDescProject.getText(),
                        (textFieldExpiryDate.getText())
                );

                modelProjects.addElement(project);

                textFieldName.setText("");
                textFieldDescProject.setText("");
                textFieldExpiryDate.setText("");

            }
        }
    }

    public class UpdateProjectButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listProjects.getSelectedIndex();

            Project selectedProject = modelProjects.get(selectedIndex);

            selectedProject.setName(textFieldName.getText());
            selectedProject.setDescription(textFieldDescProject.getText());
            selectedProject.setExpiryDate(textFieldExpiryDate.getText());

            modelProjects.setElementAt(selectedProject, selectedIndex);
        }
    }

    public class DeleteProjectButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listProjects.getSelectedIndex();
            if (!listProjects.isSelectionEmpty()) {

                int choice = showConfirmationDialog();

                if (choice == JOptionPane.YES_OPTION) {

                    Project selectedProject = modelProjects.get(selectedIndex);
                    modelProjects.removeElement(selectedProject);
                }
            }

        }

        public int showConfirmationDialog() {
            int result = JOptionPane.showConfirmDialog(
                    AppFrame.this,
                    "Are you sure?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            return result;
        }
    }

    public class AddExerciseToProjectButton implements ActionListener {
        @Override

        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listProjects.getSelectedIndex();

            Project selectedProject = modelProjects.get(selectedIndex);

            Exercise exercise = new Exercise(
                    textFieldTitle.getText(),
                    textFieldDescExercise.getText(),
                    textFieldStatus.getText(),
                    textFieldPriority.getText()
            );
            selectedProject.addElementToList(exercise);
            textFieldTitle.setText("");
            textFieldDescExercise.setText("");
            textFieldStatus.setText("");
            textFieldPriority.setText("");

        }

    }

    public class DisplayProjectButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = listProjects.getSelectedIndex();

            Project selectedProject = modelProjects.get(selectedIndex);

            selectedProject.displayElementsInList();
        }
    }

    public class Exercise {
        public Exercise(String title, String description, String status, String priority) {
            setTitle(title);
            setDescription(description);
            setStatus(status);
            setPriority(priority);
        }

        String title;
        String description;
        String status;
        String priority;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public String getPriority() {
            return priority;
        }


        public void setTitle(String title) throws IllegalArgumentException {
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Title cannot be an empty string",
                        "Invalid Title",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Title cannot be an empty string");
            }
            this.title = title;
        }

        public void setDescription(String description) throws IllegalArgumentException {
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Description cannot be an empty string",
                        "Invalid Description",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Description cannot be an empty string");
            }
            this.description = description;
        }

        public void setStatus(String status) throws IllegalArgumentException {
            List<String> allowedValues = Arrays.asList("undone", "processing", "done");

            if (!allowedValues.contains(status)) {
                JOptionPane.showMessageDialog(null,
                        "Status must be one of: undone, processing, done",
                        "Invalid Status",
                        JOptionPane.ERROR_MESSAGE);

                throw new IllegalArgumentException("Invalid Status: " + priority);
            }
            this.status = status;
        }

       public void setPriority(String priority) {
           List<String> allowedValues = Arrays.asList("low", "medium", "high");

           if (!allowedValues.contains(priority)) {
               JOptionPane.showMessageDialog(null,
                       "Priority must be one of: low, medium, high",
                       "Invalid Priority",
                       JOptionPane.ERROR_MESSAGE);
               throw new IllegalArgumentException("Invalid Priority: " + priority);
           }

           this.priority = priority;
       }

        @Override
        public String toString() {
            return this.title;
        }
    }

    public class Project {
        public Project(String name, String description, String expiryDate) {
            setName(name);
            setDescription(description);
            setExpiryDate(expiryDate);
            this.list = new DefaultListModel<>();
        }
        String name;
        String description;
        String expiryDate;
        DefaultListModel<Exercise> list;


        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public DefaultListModel<Exercise> getList() {
            return list;
        }

        public void setName(String name) throws IllegalArgumentException {
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Name cannot be an empty string",
                        "Invalid Name",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Name cannot be an empty string");
            }
            this.name = name;
        }

        public void setDescription(String description) throws IllegalArgumentException {
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Description cannot be an empty string",
                        "Invalid Description",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Description cannot be an empty string");
            }
            this.description = description;
        }

        public void setExpiryDate(String expiryDate) throws IllegalArgumentException {
            if (expiryDate.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Expiry Date cannot be an empty string",
                        "Invalid Expiry Date",
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Expiry Date cannot be an empty string");
            }
            this.expiryDate = expiryDate;
        }

        @Override
        public String toString() {
            return name;
        }

        public void addElementToList(Exercise exercise) {
            list.addElement(exercise);
        }

        public void displayElementsInList() {

            for (int i = 0; i < list.getSize(); i++) {
                modelProjectExercises.addElement(list.getElementAt(i));
            }
        }
    }
}

class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->
        {
            AppFrame myFrame = new AppFrame();
        });

    }
}

