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

import jrplot.plotpackages.interactive.shinywrapper.mainfunctions.UIShinyComponent;

public class GenericUIComponentWithListUICommands extends GenericUIComponentListCommands<UIShinyComponent>{

	protected String functionname;
       
	public GenericUIComponentWithListUICommands(String functionname) {
		super();
		this.functionname=functionname;
	}
	
	
	
	
	@Override
	public String getUICommand() {
	    StringBuilder strcmd=new StringBuilder();
		
	    strcmd.append(functionname+"(");
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
	    
	    strcmd.append(")\n");
		
		return strcmd.toString();
	}
	
	
	public static GenericUIComponentWithListUICommands newListCommands(String functionname, UIShinyComponent...components) {
		GenericUIComponentWithListUICommands cmds=new GenericUIComponentWithListUICommands(functionname);
		cmds.appendUIComponent(components);
		return cmds;
	}


}
