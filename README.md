## 💻 피하기게임 GUI
- 하늘에서 떨어지는 장애물은 피하고 점수를 획득하기

## ⌚ Project execution period
  - 2022.01.05

## 🛠 Development Environment
- MVC (모델-뷰-컨트롤러, model–view–controller) 이용
- GUI
  
  - Language : `Java 8` 
  - JDK `1.8.0_341`
  - Tool : `Eclipse`

## 📃 Main Composition
- Thread 설정
- 각 객체의 떨어지는 애니메이션 Thread
  ```java
  while (true) {
				pan.add(fall);
				pan.add(get);

				fall.setLocation(fall.getX(), fall.getY() + 15); //떨어지게
				get.setLocation(get.getX(), get.getY() + 25);

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//호박 (점수 감소)
				if(fall.getY() + fall.getHeight() >= human.getY()) {
					//획득 범위 안에 들어왔을 때 (획득 o)
					if((fall.getX() + 30 >= human.getX()) && (fall.getWidth() + fall.getX() - 30 <= human.getWidth() + human.getX())) {
						if(score % 4 == 0) score -= score / 4 * 2; //숫자 4의 배수는 패널티
						score--;
						if(score == -1) break;
						fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100); //다시 위로 이동
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
						get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100); //다시 위로 이동(캐릭터에 닿았을 때)
					}

					//획득 x
					else {
						if(get.getY() >= 450) //끝부분까지(캐릭터에 닿지 x)
							get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}
			}			
		}
  ```
- 캐릭터의 움직임(벽 충돌 방지)
  ```java
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
  ```
