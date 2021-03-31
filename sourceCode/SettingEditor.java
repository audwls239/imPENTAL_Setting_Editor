import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SettingEditor extends JFrame implements ActionListener{
    public static void main(String args[]){
        SettingEditor main_ui = new SettingEditor();
    }

    /* 필드 */
    private final JPanel menu = new JPanel();
    private final JScrollPane options_scroll = new JScrollPane();
    private final PanelOfSetting[] setting_panel = new PanelOfSetting[50];
    private final ImageIcon frame_icon = new ImageIcon("./src/minecraft_icon.png");
    private JPanel option_panel = new JPanel();

    private final JTextField show_file_path  = new JTextField("", 20);   // 파일 경로 표시용 텍스트
    private final JButton file_open     = new JButton("OPEN");      // 파일 열기 버튼
    private final JButton file_save     = new JButton("SAVE");      // 변경 내용 저장

    private String file_path;
    private String file_name;

    private File read_file_name;
    
    /* 생성자 */
    SettingEditor(){
        super("Setting Editor");    // 타이틀
        setIconImage(frame_icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 창 닫을시 프로그램 종료
        
        setSize(400, 600);      // 창 크기 W = 400, H = 1000
        setResizable(false);    // 창 크기 변경 불가능
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));   // 박스 레이아웃 지정
        
        
        show_file_path.setEditable(false);   // 텍스트 변경 불가능
        
        file_open.addActionListener(this);
        file_save.addActionListener(this);
        
        menu.add(show_file_path);
        menu.add(file_open);
        menu.add(file_save);

        add(menu);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* 이벤트 리스너 */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "OPEN"){
            /* OPEN 버튼 클릭시 파일 열기 */
            FileDialog dialog = new FileDialog(this, "OPEN", FileDialog.LOAD);  // 읽기로 파일 열기
            dialog.setVisible(true);

            if(dialog.getFile() == null) return;     // 다이얼로그에서 취소 선택시 탈출

            this.file_path = dialog.getDirectory();
            this.file_name = dialog.getFile();

            this.show_file_path.setText(this.file_path + this.file_name);   // 파일 선택시 파일 경로 표시
            this.read_file_name = new File(this.file_path, this.file_name); // 파일 인스턴스 생성

            try(FileReader now_reading_file = new FileReader(this.read_file_name)){
                /* 입력 스트림 생성 */
                int num = 0;    // 설정 패널 구분용 번호
                option_panel = new JPanel();
                option_panel.setLayout(new BoxLayout(option_panel, BoxLayout.Y_AXIS));

                for(int i = 0; i < setting_panel.length; i++){
                    setting_panel[i] = null;
                }

                while(true){
                    int temp_char = now_reading_file.read();

                    /* # 주석 라인 무시 */
                    if(temp_char == '#'){
                        while(now_reading_file.read() != '\n');
                        continue;
                    }
                    if(temp_char == 10) continue;

                    /* 패널에 사용될 설정명 생성 */
                    String temp_name_string = "";
                    while(true){
                        if(temp_char == -1 || temp_char == 61) break;
                        temp_name_string += (char)temp_char;

                        temp_char = now_reading_file.read();
                    }

                    /* 옵션 종류 확인용 */
                    String temp_option_string = "";
                    while(true){
                        temp_char = now_reading_file.read();
                        if(temp_char == -1 || temp_char == 10 || temp_char == 13) break;

                        temp_option_string += (char)temp_char;
                    }

                    setting_panel[num] = new PanelOfSetting(temp_name_string, temp_option_string, num);
                    
                    option_panel.add(setting_panel[num]);
                    num++;

                    if(temp_char == -1){
                        options_scroll.setViewportView(option_panel);
                        add(options_scroll);
                        setVisible(true);
                        break;
                    }
                }
            }
            catch(IOException a){
                System.out.println(a);
            }
        }
        else if(e.getActionCommand() == "SAVE"){
            /* 출력 스트림 생성 */
            try(FileWriter now_writing_file = new FileWriter(this.read_file_name)){
                int num = 0;
                while(true){
                    now_writing_file.write(setting_panel[num].setting_name);
                    now_writing_file.write("=");
                    now_writing_file.write(setting_panel[num].option);
                    num++;
                    if(setting_panel[num] == null) break;

                    now_writing_file.write("\n");
                }
            }
            catch(IOException a){
                System.out.println(a);
            }
        }
    }
}