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
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.layout;

import jrplot.plotpackages.interactive.shinywrapper.mainfunctions.UIShinyComponent;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.panel.MainPanel;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.panel.SidebarPanel;

public class SidebarLayout implements UIShinyComponent{

	
	private SidebarPanel sidepanel;
	private MainPanel mainpanel;
	private LeftRightLayoutPosition position=LeftRightLayoutPosition.defaultshiny;
	private boolean usefluidlayout=true;
	
	
	public SidebarLayout(SidebarPanel sidepanel, MainPanel mainpanel) {
		this.sidepanel=sidepanel;
		this.mainpanel=mainpanel;
	}
	

	public SidebarLayout(SidebarPanel sidepanel, MainPanel mainpanel, LeftRightLayoutPosition position) {
		this(sidepanel, mainpanel);
		this.position = position;
	}


	public SidebarLayout(SidebarPanel sidepanel, MainPanel mainpanel, LeftRightLayoutPosition position, boolean usefluidlayout) {
		this(sidepanel,mainpanel,position);
		this.usefluidlayout = usefluidlayout;
	}

	
	
	

	public LeftRightLayoutPosition getPosition() {
		return position;
	}


	public SidebarLayout setPosition(LeftRightLayoutPosition position) {
		this.position = position;
		return this;
	}


	public boolean isUsefluidlayout() {
		return usefluidlayout;
	}


	public SidebarLayout setUsefluidlayout(boolean usefluidlayout) {
		this.usefluidlayout = usefluidlayout;
		return this;
	}


	@Override
	public String getUICommand() {
		StringBuilder str=new StringBuilder();
		str.append("sidebarLayout(\n");
		
		str.append(sidepanel.getUICommand());
		str.append(",\n");
		str.append(mainpanel.getUICommand());
		if(!position.equals(LeftRightLayoutPosition.defaultshiny)) {
			str.append(", position = \""+position.toString()+"\"");	
		}
		if(!usefluidlayout) {
			str.append(", fluid = FALSE");
		}
		str.append(")\n");
		return str.toString();
	}

	
	
	
}
