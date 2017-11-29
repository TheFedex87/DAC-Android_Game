package it.thefedex87.dac.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe GameStateManager, che gestisce i vari stati di gioco (menù, schermate di gioco, schermate dei punteggi,...)
 * @author federico.creti
 *
 */
public class GSM {
	private Stack<State> states; 
	
	public GSM(){
		states = new Stack<State>();
	}
	
	/**
	 * Inserisce uno stato sulla cima della pila
	 * @param s nuovo stato
	 */
	public void push(State s) {
		states.push(s);
	}
	
	/**
	 * Rimuove il primo stato dall pila
	 */
	public void pop(){
		dispose();
		states.pop();
	}
	
	/**
	 * Sostituisce il primo elemento della pila con un nuovo stato
	 * @param s nuovo stato
	 */
	public void set(State s) {
		if (!(s instanceof TransitionState)) 
			dispose();
		states.pop();
		states.push(s);
	}
	
	/**
	 * Aggiornamento dello stato che si trova in cima alla pila
	 * @param delta tempo trascorso dall'utlimo aggiornamento
	 */
	public void update (float delta) {
		if (states.size() > 0) states.peek().update(delta);
	}
	
	/**
	 * Disegno dello stato che si trova in cima alla pila
	 * @param sb SpriteBatch sul quale viene disegnato l'intero gioco
	 */
	public void render(SpriteBatch sb) {
		if (states.size() > 0) states.peek().render(sb);
	}
	
	/**
	 * Dispose dell'elemento che si trova in cima alla pila
	 */
	public void dispose() {
		if (states.size() > 0) states.peek().dispose();
	}
}
