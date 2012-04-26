package com.openforevent.events.client;


import java.util.HashMap;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;  
import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapMouseOverHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler.MarkerDragEndEvent;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler.MarkerDragStartEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Events implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	//private final GreetingServiceAsync greetingService = GWT
	//		.create(GreetingService.class);
	
	// GWT module entry point method.
	  public void onModuleLoad() {
		  
		  HashMap<String, String> parameters = new HashMap<String, String>();
		  parameters.put("visitId", "1111");
		  AsyncCallback<HashMap<String, Object>> callback = new AsyncCallback<HashMap<String, Object>>() {
			   public void onFailure(Throwable caught) {
				   MessageBox.info("Message", "server error " + caught.toString(), null);
			   }

			   public void onSuccess(HashMap<String, Object> result) {
				   MessageBox.info("Message", "server success ", null);
			   }
			};
			
			GwtRpcServiceAsync gwtrcpService = GWT.create(GwtRpcService.class);
			ServiceDefTarget endpoint = (ServiceDefTarget) gwtrcpService;
			endpoint.setServiceEntryPoint("/events/control/userLocationProperties");
			gwtrcpService.processRequest(parameters, callback);


		  
	      if (Geolocation.isSupported()) {
	    	  Geolocation geo = Geolocation.getGeolocation();
	    	  geo.getCurrentPosition(new PositionCallback() {
	    		    public void onFailure(PositionError error) {
	    		    	MessageBox.info("Message", "Failure", null);
	    		    }
	    		    public void onSuccess(Position position) {
	    		        final Coordinates coords = position.getCoords();
	    		        
	    		        /*
	    		 	    * Asynchronously loads the Maps API.
	    		 	    *
	    		 	    * The first parameter should be a valid Maps API Key to deploy this
	    		 	    * application on a public server, but a blank key will work for an
	    		 	    * application served from localhost.
	    		 	   */
	    		        Maps.loadMapsApi("", "2", false, new Runnable() {
	    		  	      public void run() {
	    		  	        buildUi(coords);
	    		  	      }
	    		  	    });
	    		    }
	    		});
		  }
	  
	   
	  }

	  private void buildUi(Coordinates coords) {
	    // Open a map centered on Cawker City, KS USA
	    final LatLng cawkerCity = LatLng.newInstance(coords.getLatitude(), coords.getLongitude());
	    //coords.getAccuracy()
	    
	    final MapWidget map = new MapWidget(cawkerCity, 15);
	    map.setSize("150px", "150px");
	    // Add some controls for the zoom level
	    //map.addControl(new LargeMapControl());

	    // Add a marker
	    map.addOverlay(new Marker(cawkerCity));

	    // Add an info window to highlight a point of interest
	    //map.getInfoWindow().open(map.getCenter(),
	    //    new InfoWindowContent("World's Largest Ball of Sisal Twine"));

	    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	    dock.addNorth(map, 400);
	    
	    final Window w = new Window();  
	    w.setPlain(true);  
	    w.setSize(520, 370);  
	    w.setPosition(0, 0);	    
	    w.setLayout(new FitLayout());
	    //LayoutContainer layout = new LayoutContainer();
	    //layout.setLayout(new FlowLayout(5));
	    //layout.add(w);
	    FormPanel panel = new FormPanel();  
	    panel.setBorders(false);  
	    panel.setBodyBorder(false);  
	    panel.setLabelWidth(55);  
	    panel.setPadding(5);  
	    panel.setHeaderVisible(false);  
	    TextField<String> field = new TextField<String>();  
	    field.setFieldLabel("Search");  
	    field.setWidth(100);
	    panel.add(field, new FormData("100%"));
	    
	    MarkerOptions options = MarkerOptions.newInstance();
	    options.setDraggable(true);
	    Marker marker = new Marker(cawkerCity, options);
	    
	    marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
	        public void onDragEnd(MarkerDragEndEvent event) {
	            
	          }
	        
	      });
	      
	    marker.addMarkerDragStartHandler(new MarkerDragStartHandler() {
	      public void onDragStart(MarkerDragStartEvent event) {
	          
	      }
	    });
		
		MapWidget map1 = new MapWidget(cawkerCity, 15);
	    map1.setSize("500px", "300px");
	    map1.addOverlay(marker);
	    DockPanel dock1 = new DockPanel();
	    dock1.add(panel, DockPanel.NORTH);
	    dock1.add(map1, DockPanel.CENTER);			    
	    w.add(dock1);
	    
	    
	    
	    class MyHandler implements MapMouseOverHandler{

			@Override
			public void onMouseOver(MapMouseOverEvent event) {								
			    w.show();
			}
	    	
	    }
	    
	    map.addMapMouseOverHandler(new MyHandler());

	    // Add the map to the HTML host page
	    RootLayoutPanel.get().add(dock);
	  }


	/**
	 * This is the entry point method.
	 */
	/*public void onModuleLoad() {
		//MessageBox.info("Message", "Hello World!!", null);
		
		LayoutContainer layout = new LayoutContainer();
		layout.setLayout(new CenterLayout());  
		
		
		ToolTipConfig config = new ToolTipConfig();  
	    config.setTitle("Information");  
	    config.setText("Prints the current document");  
	    config.setCloseable(true);  
	    
	    ContentPanel panel = new ContentPanel();  
	    panel.setBodyStyle("padding: 6px");  
	    panel.setFrame(true);  
	    panel.setHeading("CenterLayout");  
	    panel.addText("I should be centered");  
	    panel.setWidth(200);  
	    panel.setToolTip(config);
	  
	    layout.add(panel);  
	    RootPanel.get("mainContainer").add(layout);
	    
	    
	    
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {

			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}


			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}*/
}
