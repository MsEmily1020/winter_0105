## π» νΌνκΈ°κ²μ GUI
- νλμμ λ¨μ΄μ§λ μ₯μ λ¬Όμ νΌνκ³  μ μλ₯Ό νλνκΈ°

## β Project execution period
  - 2022.01.05

## π  Development Environment
- MVC (λͺ¨λΈ-λ·°-μ»¨νΈλ‘€λ¬, modelβviewβcontroller) μ΄μ©
- GUI
  
  - Language : `Java 8` 
  - JDK `1.8.0_341`
  - Tool : `Eclipse`

## π Main Composition
- Thread μ€μ 
- κ° κ°μ²΄μ λ¨μ΄μ§λ μ λλ©μ΄μ Thread
  ```java
  while (true) {
				pan.add(fall);
				pan.add(get);

				fall.setLocation(fall.getX(), fall.getY() + 15); //λ¨μ΄μ§κ²
				get.setLocation(get.getX(), get.getY() + 25);

				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//νΈλ° (μ μ κ°μ)
				if(fall.getY() + fall.getHeight() >= human.getY()) {
					//νλ λ²μ μμ λ€μ΄μμ λ (νλ o)
					if((fall.getX() + 30 >= human.getX()) && (fall.getWidth() + fall.getX() - 30 <= human.getWidth() + human.getX())) {
						if(score % 4 == 0) score -= score / 4 * 2; //μ«μ 4μ λ°°μλ ν¨λν°
						score--;
						if(score == -1) break;
						fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100); //λ€μ μλ‘ μ΄λ
					}

					//νλ x
					else {
						if(fall.getY() >= 450) 
							fall.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}
				//μΊλ (μ μ μ¦κ°)
				if(get.getY() + get.getHeight() >= human.getY()) {
					//λ²μ μμ λ€μ΄μμ λ (νλ o)
					if((get.getX() + 30 >= human.getX()) && (get.getWidth() + get.getX() - 30 <= human.getWidth() + human.getX())) {
						if(score % 7 == 0) score += score / 7 * 2; //μ«μ 7μ λ°°μλ μΆλ³΅
						score++;
						get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100); //λ€μ μλ‘ μ΄λ(μΊλ¦­ν°μ λΏμμ λ)
					}

					//νλ x
					else {
						if(get.getY() >= 450) //λλΆλΆκΉμ§(μΊλ¦­ν°μ λΏμ§ x)
							get.setBounds(random.nextInt(500), random.nextInt(10), 100, 100);
					}
				}
			}			
		}
  ```
- μΊλ¦­ν°μ μμ§μ(λ²½ μΆ©λ λ°©μ§)
  ```java
   KeyAdapter keyLis = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_A) {
				if(human.getX() >= 10) //λ²½ μΆ©λ λ°©μ§
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
