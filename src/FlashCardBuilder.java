import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.*;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    // Build the user interface
    public FlashCardBuilder() {
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PANEL
        JPanel mainPanel = new JPanel();

        // FONT
        Font font = new Font("Arial", Font.BOLD, 21);

        // QUESTION
        question = new JTextArea(5, 16);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);
        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // ANSWER
        answer = new JTextArea(5, 16);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);
        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<FlashCard>();

        // LABELS
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        // MAIN PANEL COMPONENTS
        mainPanel.add(questionLabel);
        mainPanel.add(questionScroll);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScroll);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        // MENU BAR
        JMenuBar MenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");

        JMenuItem saveMenuItem = new JMenuItem("Save");

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);

        MenuBar.add(fileMenu);

        // ADD EVENTLISTENERS
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuListener());

        frame.setJMenuBar(MenuBar);

        // ADD TO THE FRAME
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(360, 480);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // create a flash card
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }

    }

    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New Menu Clicked");
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

    }

    class SaveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Save Menu Clicked");
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
}
