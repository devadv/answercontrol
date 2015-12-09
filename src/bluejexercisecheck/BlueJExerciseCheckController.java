package bluejexercisecheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BlueJExerciseCheckController {

    private final BlueJExerciseCheckMainView theView;
    private final BlueJExerciseCheckModel theModel;
    private final BlueJExerciseCheckViewInputQuestion theViewInputQuestion;
    private final BlueJExerciseCheckViewInputCorrectAnswer theViewInputCorrectAnswer;

    private BlueJExerciseCheckUserInput userInputWindow;

    
    /**
     * The Contoller brings the Model and the View together
     *
     * @param theView Main View
     * @param theModel Connection to database
     *
     * @throws SQLException SQL exceptions are catched
     */
    public BlueJExerciseCheckController(BlueJExerciseCheckMainView theView,
            BlueJExerciseCheckModel theModel) throws SQLException {
        this.theView = theView;
        this.theModel = theModel;
        // add listeners
        this.theView.addInputQuestionListener(new InputQuestionListener());
        this.theView.addInputCorrectAnswerListener(new InputAnswerListener());
        // get values out of db and set in the view
        this.theModel.setConnectionDatabase();
        theViewInputQuestion = new BlueJExerciseCheckViewInputQuestion();
        theViewInputCorrectAnswer = new BlueJExerciseCheckViewInputCorrectAnswer();
        
        // Listener for button that selects the user input window
        this.theView.addUserInputButtonListener(new userInputButttonLister());
        
    }// end BlueJExerciseCheckController

    /**
     * checks question exsist in database and set the view
     */
    public void setQuestionFromDBToView() {
        try {
            if (theModel.exerciseExist(theViewInputQuestion.getSelectedExercise())) {
                theViewInputQuestion.setQuestion(theModel.
                        getQuestion(theViewInputQuestion.getSelectedExercise()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlueJExerciseCheckController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// end method setQuestionFromDBToView

    /**
     * create a question if not exsist then update question
     */
    public void addQuestionFromViewToDB() {
        try {
            if (!theModel.exerciseExist(theViewInputQuestion.getSelectedExercise())) {
                theModel.addQuestion(
                        theViewInputQuestion.getSelectedExercise(),
                        theViewInputQuestion.getQuestion(),
                        theViewInputQuestion.getSelectedBlockIndex());
            } else {
                theModel.updateQuestion(theViewInputQuestion.getSelectedExercise(),
                        theViewInputQuestion.getQuestion());
            }// end if else
        } catch (SQLException ex) {

        }// end try catch
    }// end method addQuestionFromViewToDB

    /*
     * windowclosing event control of data loss
     */
    private class WindowClosingAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent we) {
            System.out.println("Closing event");
            if (theViewInputQuestion.questionChanged()) // question changed
            {
                int dialogResult = JOptionPane.showConfirmDialog(theView,
                        "Gegevens zijn gewijzigd, opslaan? ", null, JOptionPane.YES_NO_OPTION);

                if (dialogResult == 0)// Yes button clicked
                {
                    System.out.println("Yes option");
                    addQuestionFromViewToDB();
                    System.exit(1);
                } else // No button clicked
                {
                    System.out.println("No Option");
                    System.exit(1);
                }// end if else
            }

        }
    }
    
    
       /**
        * Listener for inputQuestion button in MainView. 
        *  <ul><li>  Adds the listeners for InputQuestion View.
        *      <li>  Sets the size, and makes InputQuestion view visable.
        *   </ul> 
        */
        class InputQuestionListener implements ActionListener {
            
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                theViewInputQuestion.addWindowClosingListener(new WindowClosingAdapter());

                theViewInputQuestion.addSaveActionListener(new SaveBtnListener());
                theViewInputQuestion.addNextActionListener(new NextBtnListener());
                theViewInputQuestion.addPreviousActionListener(new PreviousBtnListener());
                theViewInputQuestion.addListExercisesListener(new listenForListExercisesComb());
                theViewInputQuestion.setBlocks(theModel.getBlockList());
                setQuestionFromDBToView();
                theViewInputQuestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                theViewInputQuestion.setSize(400, 600);
                theViewInputQuestion.setVisible(true);
                theView.dispose();
                System.out.println("Question Clicked!");
            }
            
            // List Exercise combobox Listener
            class listenForListExercisesComb implements ActionListener {//***************************************************

                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    System.out.println(theViewInputQuestion.getSelectedExerciseIndex());
                    
                    theViewInputQuestion.getQuestion();
                    theViewInputQuestion.clearQuestionTextArea();
                    setQuestionFromDBToView();
                    
                }
            }
            
            

            // Save button Listener
            class SaveBtnListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    System.out.println(theViewInputQuestion.getSelectedBlock());
                    System.out.println(theViewInputQuestion.getSelectedExercise());
                    System.out.println(theViewInputQuestion.getSelectedBlockIndex());
                    System.out.println(theViewInputQuestion.getSelectedExerciseIndex());
                    System.out.println(theViewInputQuestion.getQuestion());
                    addQuestionFromViewToDB();
                    setQuestionFromDBToView();
                }
            }// end method SaveBtnListener

            class NextBtnListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (theViewInputQuestion.questionChanged()) // question changed
                    {
                        int dialogResult = JOptionPane.showConfirmDialog(theView,
                                "Gegevens zijn gewijzigd, opslaan? ", null, JOptionPane.YES_NO_OPTION);

                        if (dialogResult == 0)// Yes button clicked
                        {
                            System.out.println("Yes option");
                            addQuestionFromViewToDB();
                            theViewInputQuestion.setNextExercise();
                            theViewInputQuestion.clearQuestionTextArea();
                            setQuestionFromDBToView();
                        } else // No button clicked
                        {
                            System.out.println("No Option");
                            theViewInputQuestion.setNextExercise();
                            theViewInputQuestion.clearQuestionTextArea();
                            setQuestionFromDBToView();
                        }// end if else
                    } else // question not changed
                    {
                        theViewInputQuestion.setNextExercise();
                        theViewInputQuestion.clearQuestionTextArea();
                        setQuestionFromDBToView();
                    }// end outer if else
                }
            }// end method  NextBtnListener

            class PreviousBtnListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                     if (theViewInputQuestion.questionChanged()) // question changed
                    {
                        int dialogResult = JOptionPane.showConfirmDialog(theView,
                                "Gegevens zijn gewijzigd, opslaan? ", null, JOptionPane.YES_NO_OPTION);

                        if (dialogResult == 0)// Yes button clicked
                        {
                            System.out.println("Yes option");
                            addQuestionFromViewToDB();
                            theViewInputQuestion.setPreviousExercise();
                            theViewInputQuestion.clearQuestionTextArea();
                            setQuestionFromDBToView();
                        } else // No button clicked
                        {
                            System.out.println("No Option");
                            theViewInputQuestion.setPreviousExercise();
                            theViewInputQuestion.clearQuestionTextArea();
                            setQuestionFromDBToView();
                        }// end if else
                    } else // question not changed
                    {
                        theViewInputQuestion.setPreviousExercise();
                        theViewInputQuestion.clearQuestionTextArea();
                        setQuestionFromDBToView();
                    }// end outer if else
                }
                
            }// end method  PreviousBtnListener
        }// end class InputQuestionListener

        // Next button Listener
        class InputAnswerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                theViewInputCorrectAnswer.addSaveActionListener(new SaveBtnListener());
                theViewInputCorrectAnswer.addNextActionListener(new NextBtnListener());
                theViewInputCorrectAnswer.addPreviousActionListener(new PreviousBtnListener());

                theViewInputCorrectAnswer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                theViewInputCorrectAnswer.setSize(400, 600);
                theViewInputCorrectAnswer.setVisible(true);
                theView.dispose();
                System.out.println("Correct Answer Clicked");
            }



        // Save button Listener
        class SaveBtnListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println(theViewInputCorrectAnswer.getSelectedBlock());
                System.out.println(theViewInputCorrectAnswer.getSelectedExercise());
                System.out.println(theViewInputCorrectAnswer.getAnswer());
                System.out.println("save Clicked");
            }
        }// end inner class SaveBtnListener

        // Next button Listener
        class NextBtnListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("next  Clicked");
            }
        }// end inner class NextBtnListener

        class PreviousBtnListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("previous Clicked");
            }
        }
        
        }// end inner class  PreviousBtnListener
        
        
        /**
         * Listener for userInput button in MainView
         * Creates the window
         * Adds listeners via the parent class methods
         * Get from datase and set in view: exercise blocknumbers
         *  
         *  TODO:  listeners as inner classes for the
         *  functionality of, the save, next and previous button.
         *  
         * @author dvogel
         */
        class userInputButttonLister implements ActionListener
        {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Display userinput window");
				
				userInputWindow = new BlueJExerciseCheckUserInput();
				userInputWindow.setBlocks(theModel.getBlockList());
				
				userInputWindow.addSaveActionListener(new saveButtonListener());
				userInputWindow.addPreviousActionListener(new previousButtonListener());
				userInputWindow.addNextActionListener(new nextButtonListener());
				userInputWindow.addListExercisesListener(new comboSelectExerciseListener());
				
				
				
				// Adds the window closing listener contained in userInputWindow
				//   note: Listener should be moved to the controller
				userInputWindow.addWindowClosingListener(userInputWindow.new WindowClosingListener());
				
				checkExist();
				setQuestion();
				
				// close mainView
				theView.dispose();
			}

			

			/**
			 * This listener should save the given answer 
			 */
			class saveButtonListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// TODO Auto-generated method stub
					System.out.println("Saving answer.. not implemented");
				}
				
			}
			
			
			class nextButtonListener implements ActionListener
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Save (not implemented), then go to next");
					userInputWindow.setNextExercise();
					// clear
					setQuestion();
				}
		
			}
			
			
			class previousButtonListener implements ActionListener
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// TODO Auto-generated method stub
					System.out.println("Save (not implemented), then go to previous .");
					userInputWindow.setPreviousExercise();
					//clear
					//set from db to view
					setQuestion();

				}

			}
			
			
			
			class comboSelectExerciseListener implements ActionListener
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					setQuestion();
					
				}
				
			}
			
			
			/// Check and set question methods,
			///    used by nextbuton, previousbutton , window setup, combobox
			
			/**
			 * Check if currently selected exercise exists in database
			 * @return 
			 */
			private boolean checkExist()
			{
				try
				{
					if (theModel.exerciseExist(userInputWindow.getSelectedExercise()))
					{
						return true;
					}
				} catch (SQLException e)
				{
					System.out.println("SQL exception");
					e.printStackTrace();
				}
				return false;
			}
			
			/**
			 * Check if currently selected exercise exists in database,
			 *   then gets question from db and set in view 
			 */
			private void setQuestion()
			{
				if (checkExist())
				{
					String question = theModel.getQuestion(userInputWindow.getSelectedExercise());
					userInputWindow.setQuestion(question);
				}
				else
				{
					System.out.println("Error: Selected exercise does"
							+ "not exist in database");
				}
				
			}
			
			
			//// END Methods used by ..

	
        	
        }
        
        
        
        
        
        
        
    }// end class BlueJExerciseCheckController

