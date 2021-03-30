import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class PanelOfSetting extends JPanel implements ActionListener{
    /* 필드 */
    String setting_name;
    String option;

    ImageIcon unlock_icon = new ImageIcon("./src/img_unlock.png");
    ImageIcon   lock_icon = new ImageIcon("./src/img_lock.png");

    JToggleButton lock_toggle = new JToggleButton(unlock_icon);
    OptionTextBox newBox;

    JButton box_open;
    JComboBox choose_option;
    
    /* 생성자 */
    PanelOfSetting(String get_name, String get_option, int num){
        setLayout(new BorderLayout());

        /* 값 가져오기 */
        this.setting_name = get_name;
        this.option = get_option;

        /* 배경색 */
        if(num % 2 == 0)
            setBackground(new Color(230, 230, 230));
        
        /* 컴포넌트 생성 */
        JLabel name_label = new JLabel((String)this.setting_name);

        /* 컴포넌트 추가 */
        add(lock_toggle, BorderLayout.WEST);
        add(name_label, BorderLayout.CENTER);

        /* 잠금 버튼 설정 */
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

        /* 옵션이 true / false 선택 일 경우 */
        if(get_option.equals("true") || get_option.equals("false")){
            choose_option = new JComboBox();

            /* 선택지 true / false 추가 */
            choose_option.addItem(true);
            choose_option.addItem(false);

            /* 현재 설정 가져오기 */
            if(get_option.equals("true")){
                choose_option.setSelectedIndex(0);
            }
            else if(get_option.equals("false")){
                choose_option.setSelectedIndex(1);
            }

            choose_option.addActionListener(this);

            add(choose_option, BorderLayout.EAST);
        }
        /* 일반 텍스트 일 경우 */
        else{
            box_open = new JButton("OPEN BOX");

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
                            if(!newBox.change_value.getText().isEmpty()){
                                option = newBox.change_value.getText();
                            }
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
    public void actionPerformed(ActionEvent item){
        JComboBox source = (JComboBox) item.getSource();
        int index = source.getSelectedIndex();

        switch(index){
            case 0:
                option = "true";
                break;
            case 1:
                option = "false";
                break;
        }
    }
}