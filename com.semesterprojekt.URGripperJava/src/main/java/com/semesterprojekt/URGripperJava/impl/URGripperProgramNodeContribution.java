package com.semesterprojekt.URGripperJava.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

public class URGripperProgramNodeContribution implements ProgramNodeContribution{

	private final ProgramAPIProvider apiProvider;
	private final URGripperProgramNodeView view;
	private final DataModel model;
	private final UndoRedoManager undoRedoManager;
	
	private static final String IP_KEY = "ip";
	private static final String PORT_KEY = "port";
	private static final String GRIPSTATUS_KEY = "gripStatus";
	private static final String FORCE_SELECT_KEY = "forceselect";
	private static final String	DISTANCE_SELECT_KEY = "distanceSelect";
	private static final String DISTANCE_VAL_KEY = "distanceVal";
	private static final String FORCE_VAL_KEY = "forceVal";
	
	
	//URCAPS Default values.
	private static final String DEFAULT_IP = "192.168.3.31";
	private static final String DEFAULT_PORT = "12345";
	//Gripper status boolean open/close
	private static final boolean DEFAULT_GRIPSTATUS = false;
	//selector for closing by force or distance 
	private static final boolean DEFAULT_SELECT_FORCE = false;
	private static final boolean DEFAULT_SELECT_DISTANCE = true;
	//Value for distance and force
	private static final short DEFAULT_DISTANCE_VAL = 5;
	private static final short DEFAULT_FORCE_VAL = 5;
	
	
	/**
	 * Constructor for the Gripper object in URCaps
	 * @param apiProvider API provider from Polyscope
	 * @param view view type object 
	 * @param model datatype to contain the URCaps data
	 */
	public URGripperProgramNodeContribution(ProgramAPIProvider apiProvider, URGripperProgramNodeView view,
			DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = this.apiProvider.getProgramAPI().getUndoRedoManager();
	}
	
	/**
	 * Setting ip address in data model when "set ip" button pressed in view
	 * if none is set before runtime, default values will be set.
	 * @param ip The IP address of the target TCP server
	 */
	public void onIPSelection(final String ip) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(IP_KEY, ip);
			}
		});
	}
	
	public void onDefaultIPSelection(final String ip) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DEFAULT_IP, ip);	
			}
		});
	}
	
	/**
	 * Setting the port, entered by user in view, upon "set port" is pressed in view
	 * if none is set before runtime, default values will be set.
	 * @param port The port of the target TCP server
	 */
	public void onPortSelection(final String port) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(PORT_KEY, port);
			}
		});
	}
	
	public void onDefaultPortSelection(final String port) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DEFAULT_PORT, port);
			}
		});
	}
	
	/**
	 * Setting the gripper status boolean to indicate direction of gripper movement
	 * if none is set before runtime, default values will be set.
	 * @param gripStatus false to close gripper and true to open gripper
	 */
	public void onCloseSelection(final boolean gripStatus) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(GRIPSTATUS_KEY, gripStatus);
			}
		});
	}
	
	/**
	 * Setting the wanted distance between gripperfingers when opening or closing the gripper, by the value given as parameter
	 * @param distance type integer, 0 minimum distance, 255 maximum distance, representing minimum and maximum distance between gripper fingers
	 */
	public void onDistanceSliderSelection(final int distance) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DISTANCE_VAL_KEY, distance);
				
			}
		});
	}
	
	/**
	 * function to set force according input, made with Undo/redo functionality
	 * @param force Final int force, measured in grams
	 */
	public void onForceSliderSelection(final int force) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(FORCE_VAL_KEY, force);
				
			}
		});
	}
	
	/**
	 * function to toggle the force feedback sense, when gripping an object. converted from integer 1 or 2 to boolean true or false
	 * @param force integer type converted to boolean before set in data model
	 */
	public void onForceSelection(final int force) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				if(force == 1) {
					model.set(FORCE_SELECT_KEY, true);
				}
				else if (force == 2) {
					model.set(FORCE_SELECT_KEY, false);
				}
				
			}
		});
	}
	
	public void onDistanceSelection(final int distance) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				if(distance == 1) {
					model.set(DISTANCE_SELECT_KEY, true);
				}
				else if (distance == 2) {
					model.set(DISTANCE_SELECT_KEY, false);
				}
				
			}
		});
	}
	
	/**
	 * Getter for current IP address for TCP server
	 * @return Returns string type with the current IP address
	 */
	private String getIP() {
		return model.get(IP_KEY, DEFAULT_IP);
	}
	/**
	 * Getter for port to the TCP server.
	 * @return Returning prot number as int
	 */
	private String getPort() {
		return model.get(PORT_KEY, DEFAULT_PORT);
	}

	/**
	 * Getter for current set direction of gripper movement
	 * The function is default, to be used in view class
	 * @return boolean, false to close, true to open.
	 */
	boolean getGripStatus() {
		return model.get(GRIPSTATUS_KEY, DEFAULT_GRIPSTATUS);
	}
	
	//Remember to do doc!!
	private int getDistanceValue() {
		return model.get(DISTANCE_VAL_KEY, DEFAULT_DISTANCE_VAL);
	}
	
	private int getForceValue() {
		return model.get(FORCE_VAL_KEY, DEFAULT_FORCE_VAL);
	}
	
	private boolean getForceSelect() {
		return model.get(FORCE_SELECT_KEY, DEFAULT_SELECT_FORCE);
	}
	
	private boolean getDistanceSelect() {
		return model.get(DISTANCE_SELECT_KEY, DEFAULT_SELECT_DISTANCE);
	}
	
	/**
	 * Function to concat commandstring for TCP socket, returns string
	 * @return A semi-colon separated string with the commands generated by the gripper plugin. open/close;toggle force;toggle distance; force value;distance Value
	 */
	public String getSocketCommand() {
		String cmd = null;
		
		//getting gripper open/close
		if (getGripStatus() == true) {
			cmd = "OP;";
		}
		else {
			cmd = "CL;";
		}
		
		//getting check box close by force and close by distance
		short force = (short) ((getForceSelect()) ? 1:0);
		short distance = (short) ((getDistanceSelect()) ? 1:0);
		cmd = cmd.concat(force + ";");
		cmd = cmd.concat(distance + ";");
		
		//getting values from sliders, recalculate to value between 0 and 255
		cmd = cmd.concat(getForceValue() + ";");
		cmd = cmd.concat(getDistanceValue() + ";");

		
		
		return cmd;
	}
	
	@Override
	public void openView() {		
		view.setIPTextField(getIP());
		view.setPortTextField(getPort());
		view.setDistanceSlider(getDistanceValue());
		view.setForceSlider(getForceValue());
		view.setForceCheckBox(getForceSelect());
		view.setDistanceCheckBox(getDistanceSelect());
		view.setGripperStatus(getGripStatus());
		view.setForceCheckBoxEnable(!getGripStatus());
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		//return "O/C: " + getGripStatus() + " IP: " + getIP() + " Port: " + getPort() + " Force: \n" + getForceSelect() + " Distance: " + getDistanceSelect();
		//return getSocketCommand();
		return "Force/Distance Gripper";
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		//Remember this is actual code to be run at runtime of robot execution.
		writer.appendLine("isOK = False");
		
		//open socket and send command-string
		writer.appendLine("socket_open(\"" + getIP() + "\", " + getPort() + ", \"socket_0\")");
		writer.appendLine("socket_send_string(\"" + getSocketCommand() + "\", \"socket_0\")"); //"execute send" er den string der sendes
		writer.ifCondition("socket_read_string(\"socket_0\") == \"OK\"");
		writer.appendLine("socket_close(\"socket_0\")");
		writer.elseCondition();
		writer.appendLine("popup(\"WARNING! No acknowledgement recieved from gripper\",title=\"Gripper communication warning\",warning=True, blocking=True)");
		writer.end();
		
		//status command listen
		writer.whileCondition("isOK == False");
		writer.appendLine("socket_open(\"" + getIP() + "\", " + getPort() + ", \"socket_0\")");
		writer.appendLine("socket_send_string(\"status;\", \"socket_0\")");
		
		writer.appendLine("recieveStr = socket_read_string(\"socket_0\")");
		writer.appendLine("socket_close(\"socket_0\")");
			writer.ifCondition("recieveStr == \"HALT\"");
			writer.appendLine("popup(\"Error in gripper, check gripper log!\",title=\"Gripper Fault\",error=True, blocking=True)");
			writer.elseIfCondition("recieveStr == \"OK\"");
			writer.appendLine("isOK = True");
			writer.elseIfCondition("recieveStr == \"WAIT\"");
			writer.sleep(0.1);
			writer.end();
		writer.end();
		
	}

}
