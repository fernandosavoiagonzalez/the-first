package fsg;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Xarope extends AdvancedRobot{ // extends para criacao de robos avancados

	
	int movimentar=1;
	
	public void run() { // implementa run, classe principal
		
		setAdjustRadarForRobotTurn(true); // fica com o radar ativo sempre buscando inimigo
		
		setBodyColor(Color.DARK_GRAY); // seta uma cor para o corpo do XAROPE
		setRadarColor(Color.BLACK); // seta uma cor para o radar do XAROPE
		setGunColor(Color.BLACK); //seta uma cor para a arma do XAROPE
		setBulletColor(Color.RED); //seta uma cor para a municao do XAROPE
		setScanColor(Color.GREEN); // seta uma cor para o scanner do XAROPE
		
		setAdjustGunForRobotTurn(true); // mantem a arma pronta para quando achar o inimigo 
		
		turnRadarRightRadians(Double.POSITIVE_INFINITY); // fica girando o radar sem parar para a direita
				
	}
	/*
	 * Funcao que escaneia os robos na arena de batalha 
	 */	
	public void onScannedRobot(ScannedRobotEvent e) {
		// cria uma variavel double que recebe a distancia do robo escaneado mais a distancia do XAROPE
		double distanciaNRxRI=e.getBearingRadians()+getHeadingRadians(); 
		// cria uma variavel double que guarda a velocidade do inimigo
		double velocidadeInimigo=e.getVelocity() * Math.sin(e.getHeadingRadians() -distanciaNRxRI);
		// criando uma variavel que recebera a posicao para o disparo
		double ajustaMira;
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
		if(Math.random()>.9){
			setMaxVelocity((12*Math.random())+12);
		}
		/*
		 * Codigo para medir a distancia do inimigo para o disparo 
		 */
		if (e.getDistance() > 150) { // se a distancia do inimigo for maior que 150
			// ajusta a mira com a formula da distancia do XAROPE menos a distancia do novo inimigo mais a 
			// velocidade do robo inimigo, tudo isso dividido por 22
			ajustaMira = robocode.util.Utils.normalRelativeAngle(distanciaNRxRI- getGunHeadingRadians()+velocidadeInimigo/22);
			// ajusta o canhao para o desparo
			setTurnGunRightRadians(ajustaMira); 
			setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(distanciaNRxRI-getHeadingRadians()+velocidadeInimigo/getVelocity()));
			// anda de atras do robo inimigo baseado na formula que 
			setAhead((e.getDistance() - 140)*movimentar);
			// atira com o nivel 2 de potencia
			setFire(2); 
		}
		else{ // esse else e para quando a distancia for menor que 99
			// ajusta a mira com a formula da distancia do nosso robo menos a distancia do rovo inimigo mais a 
			// velociadade do robo inimigo, tudo isso dividido por 22
			ajustaMira = robocode.util.Utils.normalRelativeAngle(distanciaNRxRI- getGunHeadingRadians()+velocidadeInimigo/15);
			// ajusta o canhao para o desparo
			setTurnGunRightRadians(ajustaMira);
			setTurnLeft(-90-e.getBearing()); 
			// anda de atras do robo inimigo baseado na formula que 
			setAhead((e.getDistance() - 140)*movimentar);
			// atira com o nivel 3 de potencia
			setFire(3); 
		}	
	}
	/*
	 * Funcao acionada quando o robo colide com uma parede
	 */
	public void onHitWall(HitWallEvent e){
		movimentar=-movimentar; // a variavel movimentar recebe o valor dela menos 1
	}
	/*
	 * Funcao acionada quando o XAROPE se colide com outro 
	 */
	public void onHitRobot(HitRobotEvent e){
		setAhead(movimentar*55); // se movimenta para, se afastar do inimigo		
	}
	/*
	 * Quando o XAROPE e acertado 
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		//setAhead(movimentar*55+10);  // se movimenta para, se afastar do inimigo
		fire(3);
	}	
	/*
	 * Funcao acionada quando XAROPE vence uma batalha (fica somente ele vivo no campo) 
	 */
	public void onWin(WinEvent e) {
		// laco de repeticao for que se repete 50 vezes
		for (int i = 0; i < 50; i++) {
			fire(3);
			turnRight(30); // gira para direita
			fire(3);
			turnLeft(30); // gira para esquerda
		}
	}

	
	
}