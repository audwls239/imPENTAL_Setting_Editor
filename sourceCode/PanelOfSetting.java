import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class PanelOfSetting extends JPanel implements ItemListener{
    /* 필드 */
    ImageIcon unlock_icon = new ImageIcon("./src/img_unlock.png");
    ImageIcon   lock_icon = new ImageIcon("./src/img_lock.png");

    JToggleButton lock_toggle = new JToggleButton(unlock_icon);
    JLabel setting_name = new JLabel();
    String option;
    OptionTextBox newBox;

    JButton box_open;
    JComboBox choose_option;
    
    /* 생성자 */
    PanelOfSetting(String get_name, String get_option, int num){
        setLayout(new BorderLayout());

        if(num % 2 == 0)
            setBackground(new Color(230, 230, 230));
        
        setting_name.setText(get_name);

        add(lock_toggle, BorderLayout.WEST);
        add(setting_name, BorderLayout.CENTER);

        lock_toggle.setSelectedIcon(lock_icon);
        lock_toggle.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                /* 락 버튼 활성화 시 옵션 변경 불가능 */
                JToggleButton jbtn = (JToggleButton) e.getSource();
                if(jbtn.isSelected()){
                    if(choose_option == null)
                        box_open.setEnabled(false);
                    else
                        choose_option.setEnabled(false);
                }
                else{
                    if(choose_option == null)
                        box_open.setEnabled(true);
                    else
                        choose_option.setEnabled(true);
                }
            }
        });

        System.out.println("야하롱1");
        /* 옵션이 true / false 선택 일 경우 */
        if(get_option.equals("true") || get_option.equals("false")){
            this.option = get_option;
            choose_option = new JComboBox();

            choose_option.addItem(true);
            choose_option.addItem(false);

            choose_option.addItemListener(this);
            System.out.println("야하롱2");
            System.out.println(this.option);

            /* 현재 설정 가져오기 */
            if(get_option.equals("true")){
                choose_option.setSelectedIndex(0);
            }
            else if(get_option.equals("false")){
                choose_option.setSelectedIndex(1);
            }

            add(choose_option, BorderLayout.EAST);
        }
        /* 일반 텍스트 일 경우 */
        else{
            box_open = new JButton("OPEN BOX");
            this.option = get_option;

            box_open.addActionListener(new ActionListener(){
                /* "OPEN BOX" 버튼 클릭시 해당 옵션 변경 창(OptionTextBox) 오픈 */
                public void actionPerformed(ActionEvent e){
                    if(newBox != null){
                        newBox.dispose();
                    }
                    newBox = new OptionTextBox(option);
                    newBox.addWindowListener(new WindowListener(){
                        /* 설정 변경 후 OptionTextBox 창이 닫힐 시 값을 가져옴 */
                        public void windowClosed(WindowEvent arg0){
                            option = newBox.change_value.getText();
                        }
                        public void windowClosing(WindowEvent arg0){}
                        public void windowActivated(WindowEvent arg0){}
                        public void windowDeactivated(WindowEvent arg0){}
                        public void windowDeiconified(WindowEvent arg0){}
                        public void windowIconified(WindowEvent arg0){}
                        public void windowOpened(WindowEvent arg0){}                    
                    });
                }
            });

            add(box_open, BorderLayout.EAST);
        }
    }

    /* true / false 선택시 값 변경 */
    public void itemStateChanged(ItemEvent item){
        JComboBox source = (JComboBox) item.getSource();
        int index = source.getSelectedIndex();

        if(index == 0){
            option = "true";
        }
        else if(index == 1){
            option = "false";
        }
    }
}