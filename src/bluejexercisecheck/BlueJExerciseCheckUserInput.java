package bluejexercisecheck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;


/**
 * Gui for input of user answers
 * @author david
 */
public class BlueJExerciseCheckUserInput extends BlueJExerciseCheckView
{
	private static final long serialVersionUID = 1L;
	
	private JTextArea textAreaQuestion;
	private JTextArea textAreaAnswer;
	
	//private JLabel labelQuestion;
	private JLabel labelAnswer;
	
	private JPanel panelQuestion;
	private JPanel panelAnswer;
	
	private JScrollPane jspQuestion;
	private JScrollPane jspAnswer;
	
	Border emptyBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
	Border blueBorder = BorderFactory.createLineBorder(new Color(80,80,190), 2);
	Border greenBorder = BorderFactory.createLineBorder(new Color(105,170,105), 2);
	Border cmpBlue = BorderFactory.createCompoundBorder(blueBorder, emptyBorder);
	Border cmpGreen = BorderFactory.createCompoundBorder(greenBorder, emptyBorder);
	
	
	
	public BlueJExerciseCheckUserInput()
	{
		super();
		setGUI();
	}
	
	@Override
	public void setGUI()
	{

		this.setTitle("BlueJ Exercise Check  -  User input");
		this.setResizable(true);
		this.setSize(480, 640);
		this.setMinimumSize(new Dimension(240, 300));
		
		Font textAreaFont = new Font("", Font.BOLD, 13);

		textAreaQuestion = new JTextArea();
		textAreaQuestion.setPreferredSize(new Dimension(200,140));
		textAreaQuestion.setBorder(blueBorder);
		textAreaQuestion.setLineWrap(true);
		textAreaQuestion.setWrapStyleWord(true);
		textAreaQuestion.setText("Vraag");
		textAreaQuestion.setBorder(cmpBlue);
		textAreaQuestion.setFont(textAreaFont);
		
		textAreaAnswer = new JTextArea();
		textAreaAnswer.setLineWrap(true);
		textAreaAnswer.setPreferredSize(new Dimension(200,240));
		textAreaAnswer.setWrapStyleWord(true);
		textAreaAnswer.setBorder(greenBorder);
		textAreaAnswer.setText("Antwoord");
		textAreaAnswer.setBorder(cmpGreen);
		textAreaAnswer.setFont(textAreaFont);
				
		
		labelAnswer = new JLabel("Antwoord:");
		
		labelQuestion.setBorder(emptyBorder);
		panelQuestion = new JPanel();
		labelAnswer.setBorder(emptyBorder);
		panelAnswer = new JPanel();
		
	
		jspQuestion = new JScrollPane(textAreaQuestion, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspAnswer = new JScrollPane(textAreaAnswer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panelQuestion.setLayout(new BoxLayout(panelQuestion, BoxLayout.PAGE_AXIS));
		panelAnswer.setLayout(new BoxLayout(panelAnswer, BoxLayout.PAGE_AXIS));
		
		
		panelQuestion.add(labelQuestion);
		panelQuestion.add(jspQuestion);
		panelAnswer.add(labelAnswer);
		panelAnswer.add(jspAnswer);
		
		panel.setBorder(emptyBorder);

		panelQuestion.setBorder(emptyBorder);
		panelAnswer.setBorder(emptyBorder);
		
		//panelQuestion.setBorder(testBorder);
				
		panel.add(panelQuestion);
		
		panel.add(panelAnswer);
		
		panel.add(panelBottom);
		
		//panel.add(textAreaAnswer);
				
		
		this.setVisible(true);		
	}
	
	
	
	class WindowClosingListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			System.out.println("Closing userinput window" );
			BlueJExerciseCheckUserInput.this.dispose();			
		}
		
	}
	
	
	/**
	 * Adds the given window closing listener 
	 * @param windowAdapter 
	 */
	@Override
	public void addWindowClosingListener(WindowAdapter windowAdapter)
	{
		// TODO Auto-generated method stub
		super.addWindowClosingListener(windowAdapter);
	}

	/**
	 * Test userinput gui 
	 */
	public static void main(String[] args)
	{
		BlueJExerciseCheckUserInput userInput  =  new BlueJExerciseCheckUserInput();
		userInput.addWindowClosingListener(userInput.new WindowClosingListener());
		userInput.setVisible(true);
		
		GuiUtils.printHierarchy(userInput);

	}

}
