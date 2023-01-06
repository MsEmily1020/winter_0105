package halloween.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HalloweenMain extends JFrame {
	Random random = new Random();
	JPanel pan = new JPanel(null);

	//모양 바꾸는 박스
	JComboBox<String> cbHuman = new JComboBox<String>("human,zombie".split(","));
	String[] fallS = "pumpkin,skeleton,bat".split(",");
	JComboBox<String> cbFall = new JComboBox<String>(fallS);
	String[] getS = "candy,star,cat".split(",");
	JComboBox<String> cbGet = new JComboBox<String>(getS);

	JLabel human = new JLabel(new ImageIcon("image/human.jpg"));
	JLabel fall = new JLabel((new ImageIcon("image/pumpkin.jpg")));
	JLabel get = new JLabel(new ImageIcon("image/candy.jpg"));
	JLabel scoreLb = new JLabel("점수 : 0");

	int score = 0;

	public HalloweenMain() {
		//배경
		pan.setBackground(Color.white);
		JPanel panN = new JPanel();
		panN.setBackground(Color.white);

		//사람
		human.setBounds(230, 420, 100, 100);
		pan.add(human);

		//게임 시작 버튼
		JButton btnStart = new JButton("게임시작");
		btnStart.addActionListener(btnL);
		panN.add(btnStart);

		//사람 바꾸는 박스
		panN.add(cbHuman);
		cbHuman.addItemListener(comboL);

		//장애물 바꾸는 박스(점수 감소)
		panN.add(cbFall);
		cbFall.addItemListener(comboL);

		//캔디 바꾸는 박스(점수 획득)
		panN.add(cbGet);
		cbGet.addItemListener(comboL);

		//점수
		panN.add(scoreLb);

		add(pan, "Center");
		add(panN, "North");

		setTitle("할로윈피하기게임");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	ActionListener btnL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			addKeyListener(keyLis);
			setFocusable(true);
			requestFocus();

			HalloweenThread t = new HalloweenThread();
			t.start();
		}
	};

	ItemListener comboL = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			fall.setIcon(new ImageIcon("image/" + (String)cbFall.getSelectedItem() + ".jpg"));
			get.setIcon(new ImageIcon("image/" + (String)cbGet.getSelectedItem() + ".jpg"));
			human.setIcon(new ImageIcon("image/" + (String)cbHuman.getSelectedItem() + ".jpg"));

			setFocusable(true);
			requestFocus();
		}
	};

	public static void main(String[] args) {
		new HalloweenMain().setVisible(true);
	}

	public class HalloweenThread extends Thread {
		@Override
		public void run() {
			fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
			get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);

			while (true) {
				pan.add(fall);
				pan.add(get);

				fall.setLocation(fall.getX(), fall.getY() + 15);
				get.setLocation(get.getX(), get.getY() + 25);

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//호박 (점수 감소)
				if(fall.getY() + fall.getHeight() >= human.getY()) {
					//범위 안에 들어왔을 때 (획득 o)
					if((fall.getX() + 30 >= human.getX()) && (fall.getWidth() + fall.getX() - 30 <= human.getWidth() + human.getX())) {
						if(score % 4 == 0) score -= score / 4 * 2; //숫자 4의 배수는 패널티
						score--;
						if(score == -1) break;
						fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}

					//획득 x
					else {
						if(fall.getY() >= 450) 
							fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}
				//캔디 (점수 증가)
				if(get.getY() + get.getHeight() >= human.getY()) {
					//범위 안에 들어왔을 때 (획득 o)
					if((get.getX() + 30 >= human.getX()) && (get.getWidth() + get.getX() - 30 <= human.getWidth() + human.getX())) {
						if(score % 7 == 0) score += score / 7 * 2; //숫자 7의 배수는 축복
						score++;
						get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}

					//획득 x
					else {
						if(get.getY() >= 450) 
							get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}

				scoreLb.setText("점수 : " + score);
			}			
		}
	}

	KeyAdapter keyLis = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_A) {
				if(human.getX() >= 10) //벽 충돌 방지
					human.setLocation(human.getX() - 15, human.getY());
			}

			if(e.getKeyCode() == KeyEvent.VK_D) {
				if(human.getX() <= 500)
					human.setLocation(human.getX() + 15, human.getY());
			}

			human.repaint();
		};
	};
}
