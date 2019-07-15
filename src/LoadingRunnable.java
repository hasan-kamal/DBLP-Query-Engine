/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import javax.swing.*;
import java.awt.*;

public class LoadingRunnable implements Runnable{

	JLabel loadingLabel;

	public LoadingRunnable(JLabel aLabel){
		loadingLabel = aLabel;
	}

	public void run(){
		try{
			
			while(!Thread.currentThread().isInterrupted()){
				Thread.sleep(500);
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						if(loadingLabel.getText()=="Loading")
							loadingLabel.setText("Loading.");
						else if(loadingLabel.getText()=="Loading.")
							loadingLabel.setText("Loading..");
						else if(loadingLabel.getText()=="Loading..")
							loadingLabel.setText("Loading...");
						else if(loadingLabel.getText()=="Loading...")
							loadingLabel.setText("Loading");
					}
				});
			}

		}catch(InterruptedException ex){
			return;
		}finally{
			System.out.println("Loading Runnable thread exiting");
		}
	}
}
