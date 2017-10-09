
package mvc.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import mvc.controller.iCRUD;
import mvc.model.Model;

public class ViewUserAnswerCorrectAnswer extends View
{
    private JTextArea userAnswerField;
    private iCRUD controller;
    String exerciseNr = "";
    private JTextArea correctAnswerField;
	private int blockid;
	private JButton btnBack;
	private View viewParent;
	private String parentExerciseSelected;

    public ViewUserAnswerCorrectAnswer(Model model, iCRUD controller, View viewParent)
    {
        super(model, controller);
        this.viewParent = viewParent;
        this.model = model;
        this.controller = controller;
        this.blockid = viewParent.getBlockID();
        //exerciseNr = getExcercise();

        this.parentExerciseSelected = viewParent.getExcercise();
        exerciseNr = parentExerciseSelected;

        setGUI();

        questionField.setText(model.retrieveQuestion(getExcercise()));
        userAnswerField.setText(model.retrieveAnswerUser(getExcercise(), controller.getUserName()));

    }

    public  void setSelectedValue(JComboBox comboBox, String value)
    {
        String item;
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            item = (String)comboBox.getItemAt(i);
            if (item.equals(value))
            {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }


    @Override
    public void setGUI()
    {
        super.setGUI();
        btnBack = new JButton("Back to input questions");

        btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewParent.setVisible(true);
				ViewUserAnswerCorrectAnswer.this.setVisible(false);

			}
		});
        labelBlock.setVisible(false);
        btnSave.setVisible(false);
        btnCheckAnswer.setVisible(false);
        blocks_id.setVisible(false);
        exercise_id.setModel(new DefaultComboBoxModel<>(model.getExerciseList(blockid)));
        exerciseNr = getExcercise();

        // selecting the exercise_nr form the parent view
        setSelectedValue(exercise_id, parentExerciseSelected);


        questionField.setRows(10);
        questionField.setColumns(78);
        questionField.setFont(textAreaFont);
        questionField.setEditable(false);
        questionField.setText(model.retrieveQuestion(exerciseNr));

        userAnswerField = new JTextArea(23, 38);
        userAnswerField.setFont(textAreaFont);
        userAnswerField.setLineWrap(true);
        userAnswerField.setBackground(new Color(219, 205, 197));

        JScrollPane jspUserAnswer = new JScrollPane(userAnswerField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel panelAnswer = new JPanel();
        panelAnswer.add(jspUserAnswer);

        correctAnswerField = new JTextArea(23, 38);
        correctAnswerField.setFont(textAreaFont);
        correctAnswerField.setLineWrap(true);
        correctAnswerField.setBackground(new Color(219, 205, 197));
        JScrollPane jspCorrectAnswer = new JScrollPane(correctAnswerField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        correctAnswerField.setText(model.retrieveAnswer(exerciseNr));
        panelBottom.add(btnBack);
        panelAnswer.add(jspCorrectAnswer);

        panel.add(panelQuestion);
        panel.add(panelAnswer);

        panel.add(panelBottom);

        this.addWindowListener(new windowClosingAdapter());
        this.setTitle("Check user answer with correct answer.");
        this.setSize(1200, 800);
        this.setLocation(500, 200);
        this.getContentPane().add(panel);
        this.setVisible(true);

    }


    @Override
    public void btnNext()
    {
    	exercise_id.setSelectedIndex(exercise_id.getSelectedIndex() + 1);
    }

    public void btnPrevious()
    {
    	exercise_id.setSelectedIndex(exercise_id.getSelectedIndex() - 1);
    }


    public String getUserAnswer()
    {
        return userAnswerField.getText();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        questionField.setText(model.retrieveQuestion(exerciseNr));
        userAnswerField.getText();
        userAnswerField.setText(model.retrieveAnswerUser(exerciseNr, controller.getUserName()));
    }


    public class windowClosingAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent we)
        {
            System.exit(1);
        }
    }// end class windowClosingAdaptor


    @Override
    public void exerciseId()
    {
        exerciseNr = viewParent.getExcercise();
        questionField.setText(model.retrieveQuestion(exerciseNr));
        //userAnswerField.setText(model.retrieveAnswerUser(exerciseNr, controller.getUserName()));
        //correctAnswerField.setText(model.retrieveAnswer(exerciseNr));
    }

    @Override
    public void btnSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void blocksId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void btnCheckAnswer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}// end class ViewInputAnswerUser
