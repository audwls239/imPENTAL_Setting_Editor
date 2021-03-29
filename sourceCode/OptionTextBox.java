import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class OptionTextBox extends JFrame implements ActionListener{
    /* 필드 */
    JLabel current_label        = new JLabel("Current");
    JLabel change_label         = new JLabel("Change to");
    JTextField current_value    = new JTextField("", 20);
    JTextField change_value     = new JTextField("", 20);
    JButton option_save         = new JButton("SAVE & CLOSE");

    /* 생성자 */
    OptionTextBox(String get_option){
        super("OPTION BOX");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(300, 125);

        current_value.setText(get_option);
        current_value.setEditable(false);

        add(current_label);
        add(current_value);
        add(change_label);
        add(change_value);
        add(option_save);

        current_label.setAlignmentX(LEFT_ALIGNMENT);

        option_save.addActionListener(this);

        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    /* 메소드 */
    public String returnText(){
        return change_value.getText();
    }

    /* 이벤트 리스너 */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "SAVE & CLOSE"){
            returnText();
            dispose();
        }
    }
}