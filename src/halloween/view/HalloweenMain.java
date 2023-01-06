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
	
	JComboBox<String> cbHuman = new JComboBox<String>("human,zombie".split(","));
	String[] pumpkinS = "pumpkin,skeleton,bat".split(",");
	JComboBox<String> cbPump = new JComboBox<String>(pumpkinS);
	String[] candyS = "candy,star,cat".split(",");
	JComboBox<String> cbCandy = new JComboBox<String>(candyS);

	JLabel human = new JLabel(new ImageIcon("image/human.jpg"));
	JLabel pumpkin = new JLabel((new ImageIcon("image/pumpkin.jpg")));
	JLabel candy = new JLabel(new ImageIcon("image/candy.jpg"));
	JLabel scoreLb = new JLabel("점수 : 0");
	
	int score = 0;

	public HalloweenMain() {
		pan.setBackground(Color.white);
		JPanel panN = new JPanel();
		panN.setBackground(Color.white);

		JButton btnStart = new JButton("게임시작");
		btnStart.addActionListener(btnL);
		panN.add(btnStart);
		panN.add(cbHuman);
		cbHuman.addItemListener(comboL);
		panN.add(cbPump);
		cbPump.addItemListener(comboL);
		panN.add(cbCandy);
		cbCandy.addItemListener(comboL);

		add(pan, "Center");
		add(panN, "North");

		human.setBounds(230, 420, 100, 100);
		pan.add(human);
		panN.add(scoreLb);

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
			ImageIcon icon = null;
			String imgName = null;

			//점수 감소
			if(e.getSource() == cbPump) {
				switch (cbPump.getSelectedIndex()) {
				case 0:
					imgName = "pumpkin";
					break;
				case 1:
					imgName = "skeleton";
					break;
				case 2:
					imgName = "bat";
					break;
				}
				icon = new ImageIcon("images/" + imgName + ".gif");
				pumpkin.setIcon(new ImageIcon("image/" + imgName + ".jpg"));
			}
			
			//점수 획득
			else if(e.getSource() == cbCandy) {
				switch (cbCandy.getSelectedIndex()) {
				case 0:
					imgName = "candy";
					break;
				case 1:
					imgName = "star";
					break;
				case 2:
					imgName = "cat";
					break;
				}
				candy.setIcon(new ImageIcon("image/" + imgName + ".jpg"));
			} 
			
			else if(e.getSource() == cbHuman) {
				switch (cbHuman.getSelectedIndex()) {
				case 0:
					imgName = "human";
					break;
				case 1:
					imgName = "zombie";
					break;
				}
				
				human.setIcon(new ImageIcon("image/" + imgName + ".jpg"));
			}
			setFocusable(true);
			requestFocus();
		}
	};

	public static void main(String[] args) {
		new HalloweenMain().setVisible(true);
	}

	public class HalloweenThread extends Thread{ 
		@Override
		public void run() {
			pumpkin.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
			candy.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);

			while (true) {
				pan.add(pumpkin);
				pan.add(candy);

				pumpkin.setLocation(pumpkin.getX(), pumpkin.getY() + 15);
				candy.setLocation(candy.getX(), candy.getY() + 25);

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//호박 (점수 감소)
				if(pumpkin.getY() + pumpkin.getHeight() >= human.getY()) {
					//범위 안에 들어왔을 때 (획득 o)
					if((pumpkin.getX() + 30 >= human.getX()) && (pumpkin.getWidth() + pumpkin.getX() - 30 <= human.getWidth() + human.getX())) {
						score--;
						if(score == -1) break;
						pumpkin.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}

					//획득 x
					else {
						if(pumpkin.getY() >= 450) 
							pumpkin.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}
				//캔디 (점수 증가)
				if(candy.getY() + candy.getHeight() >= human.getY()) {
					//범위 안에 들어왔을 때 (획득 o)
					if((candy.getX() + 30 >= human.getX()) && (candy.getWidth() + candy.getX() - 30 <= human.getWidth() + human.getX())) {
						score++;
						candy.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}

					//획득 x
					else {
						if(candy.getY() >= 450) 
							candy.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
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
