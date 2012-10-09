package it.uniba.di.socialcdeforeclipse.views;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.action.ActionProfile;
import it.uniba.di.socialcdeforeclipse.controller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

public class DynamicHome implements Panel {
	
	
	private ArrayList<Control> controlli;
	private Label labelAvatar; 
	private Label labelHidden; 
	private Label labelSkills; 
	private Label labelSettings; 
	private Label username;
	private Composite postComposite; 
	private Label labelPost; 
	private Label verticalSeparator; 
	private Label labelFollowing; 
	private Label labelFollowers; 
	private Composite buttonComposite; 
	private Composite serviceComposite; 
	
	private final InputStream PATH_SKILLS = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Skills.png");
	private final InputStream PATH_SETTINGS = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Settings.png");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image get_ImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		GridLayout layout = new GridLayout(4, true);
		panel.setLayout(layout);
		 
		labelAvatar = new Label(panel,SWT.NONE); 
		GridData gridData = new GridData();
		gridData.verticalSpan = 2; 
		labelAvatar.setLayoutData(gridData); 
		if(Controller.getCurrentUser().Avatar == null || Controller.getCurrentUser().Avatar.equals(""))
		{
			labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
			labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48));
		}
		else
		{
			try {
				labelAvatar.setImage(get_ImageStream(new URL(Controller.getCurrentUser().Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48)); 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48));
				e.printStackTrace();
			} 
		}
		controlli.add(labelAvatar); 
		
		username = new Label(panel, SWT.NONE); 
		username.setText(Controller.getCurrentUser().Username); 
		username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.NONE)); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		
		buttonComposite = new Composite(panel, SWT.NONE); 
		Layout rowLayout = new RowLayout(); 
		buttonComposite.setLayout(rowLayout); 
		
		labelSkills = new Label(buttonComposite,SWT.RIGHT); 
		labelSkills.setImage(get_ImageStream(PATH_SKILLS));
		labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		
		labelSettings = new Label(buttonComposite,SWT.RIGHT); 
		labelSettings.setImage(get_ImageStream(PATH_SETTINGS));
		labelSettings.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelSettings.setData("ID_action", "labelSettings");
		
		postComposite = new Composite(panel, SWT.NONE); 
		
		GridLayout gridlayout = new GridLayout(5,false); 
		postComposite.setLayout(gridlayout); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		postComposite.setLayoutData(gridData); 
		
		
		
		labelPost = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelPost.setText("Posts\r\n" + Controller.getCurrentUser().Statuses); 
		labelPost.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelPost.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL); 
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		controlli.add(verticalSeparator); 
		
		labelFollowing = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelFollowing.setText("Following\r\n" + Controller.getCurrentUser().Followings); 
		labelFollowing.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL);
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		
		labelFollowers = new Label(postComposite, SWT.WRAP | SWT.CENTER); 
		labelFollowers.setText("Followers\r\n" + Controller.getCurrentUser().Followers); 
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		
		serviceComposite = new Composite(panel,SWT.BORDER); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true; 
		gridData.horizontalAlignment = gridData.FILL; 
		serviceComposite.setLayoutData(gridData); 
		GridLayout serviceGrid = new GridLayout(2,true); 
		serviceComposite.setLayout(serviceGrid); 
		
		 WService[] wService = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		Label service;
		SquareButton services; 
		//Button services; 
		
		if(wService.length > 0)
		{
		
			for( int i=0;i<wService.length;i++)
			{
		/*		
				services = new Button(serviceComposite, SWT.BORDER); 
				services.setText(wService[i].Name); 
				services.addPaintListener(new PaintListener() {
					
					@Override
					public void paintControl(PaintEvent e) {
						// TODO Auto-generated method stub
						GC gcelement = e.gc;
						gcelement.drawRectangle(0, 0, 100, 300); 
						gcelement.drawText("Ciao", 0, 50); 
						
					}
				});
				if(wService[i].Registered)
				{
					services.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
				}
				else
				{
					services.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
				}
				*/
				
				 services = new SquareButton(serviceComposite, SWT.NONE); 
				 
				 services.setRoundedCorners(true);
				
				// Point p = services.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
				 services.setText(wService[i].Name); 
				 //services.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN)); 
				 
				 services.setSize(100, 300);
					
				controlli.add(services);
				System.out.println("Link immagine " + wService[i].Image);
				System.out.println("Registrato " + wService[i].Registered);
				if(wService[i].Registered)
				{
				services.setDefaultColors(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN),null,Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN),null);
				}
				else
				{
					services.setDefaultColors(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN),null,Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED),null);
				}
				services.borderWidth = 3;
				services.setData("ID_action","btnServices");
				services.setData("service" , wService[i]); 
				services.addListener(SWT.Selection, azioni); 
				 
				try {
					 
					services.setImage(get_ImageStream(new URL(Controller.getPreferences("ProxyRoot") +  wService[i].Image).openStream()));
					
					//services.setBackground(panel.getDisplay().getSystemColor(SWT.COLOR_BLUE)); 
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//services.layout();
				panel.layout();
			}
		} else {
			service = new Label(serviceComposite, SWT.NONE);
			service.setText("There are no services available yet.\t\nPlease try again soon or contact your admin."); 
			gridData = new GridData(); 
			gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_CENTER; 
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER; 
			service.setLayoutData(gridData); 
			controlli.add(service); 
		}
		 
		labelSettings.addListener(SWT.PUSH, azioni); 
		
		controlli.add(serviceComposite); 
		 controlli.add(labelFollowers); 
		 controlli.add(verticalSeparator); 
		 controlli.add(labelFollowing); 
		 controlli.add(labelPost); 
		 controlli.add(postComposite); 
		 controlli.add(labelSettings); 
		 controlli.add(labelSkills); 
		 controlli.add(buttonComposite);
		 controlli.add(labelHidden); 
		 controlli.add(username); 
		 controlli.add(labelAvatar); 
		
		Controller.temporaryInformation.put("Workbench", PlatformUI.getWorkbench().getActiveWorkbenchWindow()); 
		}
	
	

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub

		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}

}
