package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.flatironschool.javacs.Runner;

import view.IView2ModelAdapter;
import view.MainFrame;

public class Controller {
	private Runner model;
	private MainFrame view;
	
	public Controller() throws IOException {
		model = new Runner();
		view = new MainFrame(new IView2ModelAdapter() {

			@Override
			public List<Entry<String, Integer>> search(String term) {
				// TODO Auto-generated method stub
				return model.search(term);
				 
			}
			
		});
	}
	
	/**
	 * Starts the controller, creates both the model and the view, and then
	 * starts the model and view.
	 * 
	 * @param args
	 *            The input system arguments (not used)
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Controller control = new Controller();
		control.start();
	}

	/**
	 * Starts the model and view, including making the view visible
	 */
	public void start() {
//		this.model.start();
		this.view.start();
	}
}
