package com.semesterprojekt.URGripperJava.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

/**
 * @author ur
 *
 */
/**
 * @author ur
 *
 */
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
	
	private static String DEFAULT_IP = "192.168.3.31";
	private static int DEFAULT_PORT = 12345;
	//Gripper status boolean open/close
	private static final boolean DEFAULT_GRIPSTATUS = false;
	//selector for closing by force or distance 
	private static final boolean DEFAULT_SELECT_FORCE = false;
	private static final boolean DEFAULT_SELECT_DISTANCE = true;
	//Value for distance and force
	private static final short DEFAULT_DISTANCE_VAL = 5;
	private static final short DEFAULT_FORCE_VAL = 5;
	
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
	
	/**
	 * Setting the port, entered by user in view, upon "set port" is pressed in view
	 * if none is set before runtime, default values will be set.
	 * @param port The port of the target TCP server
	 */
	public void onPortSelection(final int port) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(PORT_KEY, port);
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
	 * @param position type integer, 0 minimum distance, 255 maximum distance, representing minimum and maximum distance between gripper fingers
	 */
	public void onDistanceSliderSelection(final int distance) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(DISTANCE_VAL_KEY, distance);
				
			}
		});
	}
	
	public void onForceSliderSelection(final int force) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				model.set(FORCE_VAL_KEY, force);
				
			}
		});
	}
	
	public void onForceSelection(final int force) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			
			@Override
			public void executeChanges() {
				if(force == 1) {
					model.set(FORCE_SELECT_KEY, true);
				}
				else if (force ==2) {
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
				else if (distance ==2) {
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
	private int getPort() {
		return model.get(PORT_KEY, DEFAULT_PORT);
	}
	
	/**
	 * Getter for current set direction of gripper movement
	 * @return boolean, false to close, true to open.
	 */
	private boolean getGripStatus() {
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
		cmd = cmd.concat(255/20 * getDistanceValue() + ";");
		cmd = cmd.concat(255/10 * getForceValue() + ";");
		cmd = cmd.concat("END");
		
		
		return cmd;
	}
	
	@Override
	public void openView() {
//		view.setIOComboBoxItems(getOutputItems());
		
		view.setIPTextField(getIP());
		view.setPortTextField(getPort());
		view.setDistanceSlider(getDistanceValue());
		view.setForceSlider(getForceValue());
		view.setForceCheckBox(getForceSelect());
		view.setDistanceCheckBox(getDistanceSelect());
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
//		return "O/C: " + getGripStatus() + " IP: " + getIP() + " Port: " + getPort() + " Force: \n" + getForceSelect() + " Distance: " + getDistanceSelect();
		return getSocketCommand();
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		//Remember this is actual code to be run at runtime of robot execution.
		writer.appendLine("socket_open(\"" + getIP() + "\", " + getPort() + ", \"socket_0\")");
		writer.appendLine("socket_send_string(\"" + getSocketCommand() + "\", \"socket_0\")"); //"execute send" er den string der sendes;
		writer.whileNot("socket_read_string(\"socket_0\") == \"OK\"");
		writer.end();
		writer.appendLine("socket_close(\"socket_0\")");
	}

}
