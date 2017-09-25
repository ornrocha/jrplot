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
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.panel;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.UIInputWidget;

public class SidebarPanel implements UIShinyPanel{

	
	private ArrayList<UIInputWidget> inputwidgets;
	private int width=4;
	
	public SidebarPanel() {
		inputwidgets=new ArrayList<>(); 
	}

	public SidebarPanel(ArrayList<UIInputWidget> inputwidgets) {
		this.inputwidgets = inputwidgets;
	}
	
	
	public SidebarPanel addInputElements(UIInputWidget...elems) {
		for (UIInputWidget uiInputWidget : elems) {
			inputwidgets.add(uiInputWidget);
		}
		return this;
	}
	
	
	public SidebarPanel addInputElement(UIInputWidget elem) {
			inputwidgets.add(elem);
		return this;
	}
	
	public SidebarPanel setWidth(int width) {
		if(width>0 && width<12)
			this.width=width;
		return this;
	}
	
	
	@Override
	public String getUICommand() {
		
		StringBuilder str=new StringBuilder();
		str.append("sidebarPanel(\n");
		
		for (int i = 0; i < inputwidgets.size(); i++) {
			
			String elemcmd=inputwidgets.get(i).getUICommand();
			if(i<(inputwidgets.size()-1)) {
					if(!elemcmd.endsWith(","))
						elemcmd=elemcmd+",";
				}
				else if(i==(inputwidgets.size()-1)) {
					if(elemcmd.endsWith(","))
						elemcmd=elemcmd.substring(0, elemcmd.length()-1);
				}
				str.append("     "+elemcmd+"\n");
		}
		if(width!=4)
			str.append(", width ="+String.valueOf(width));

		str.append(")\n");
		return str.toString();
	}
	
	
	public static SidebarPanel createNew(UIInputWidget...elems) {
		SidebarPanel panel=new SidebarPanel();
		panel.addInputElements(elems);
		return panel;
	}

	
	
	
}
