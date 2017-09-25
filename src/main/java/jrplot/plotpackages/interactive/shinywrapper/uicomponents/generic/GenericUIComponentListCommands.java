/************************************************************************** 
 * Orlando Rocha (ornrocha@gmail.com)
 *
 * This is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This code is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Public License for more details. 
 * 
 * You should have received a copy of the GNU Public License 
 * along with this code. If not, see http://www.gnu.org/licenses/ 
 *  
 */
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.generic;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.mainfunctions.UIShinyComponent;

public class GenericUIComponentListCommands<T extends UIShinyComponent> implements UIShinyComponent{

	
	protected ArrayList<UIShinyComponent> uicomps=null;
	
	
	public GenericUIComponentListCommands() {
		this.uicomps=new ArrayList<>();
	}
	
	
	public GenericUIComponentListCommands<T> appendUIComponent(T component) {
		uicomps.add(component);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public GenericUIComponentListCommands<T> appendUIComponent(T...components) {
		for (int i = 0; i < components.length; i++) {
			uicomps.add(components[i]);
		}
		return this;
	}
	
	
	
	@Override
	public String getUICommand() {
	    StringBuilder strcmd=new StringBuilder();
		
	    for (int i = 0; i < uicomps.size(); i++) {
	    	UIShinyComponent comp=uicomps.get(i);
	    	
	    	if(i<(uicomps.size()-1)) {
	    		strcmd.append(comp.getUICommand());
	    		if(!comp.getUICommand().endsWith(","))
	    			strcmd.append(",");
	    	}
	    	else if(i==(uicomps.size()-1)) {
	    		String in=null;
	    		if(comp.getUICommand().endsWith(",")) {
	    			in=comp.getUICommand().substring(0, comp.getUICommand().length() - 1);
	    		}
	    		else
	    			in=comp.getUICommand();
	    		
	    		strcmd.append(in);
	    	}
	    	
	    	strcmd.append("\n");
	    	
		}
		
		return strcmd.toString();
	}
	
	
	public static GenericUIComponentListCommands newListCommands(UIShinyComponent...components) {
		GenericUIComponentListCommands<UIShinyComponent> cmds=new GenericUIComponentListCommands<>();
		cmds.appendUIComponent(components);
		return cmds;
	}

}
