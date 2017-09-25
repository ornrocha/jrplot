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

import jrplot.plotpackages.interactive.shinywrapper.uicomponents.outputwidget.UIOutputWidget;

public class MainPanel implements UIShinyPanel{

	
	private Integer width=null;
	private ArrayList<UIOutputWidget> outputwidgets;
	
	
	public MainPanel() {
		outputwidgets=new ArrayList<>();
	}
	

	public MainPanel(ArrayList<UIOutputWidget> outputwidgets) {
		this.outputwidgets = outputwidgets;
	}
	
	
	public MainPanel(Integer width, ArrayList<UIOutputWidget> outputwidgets) {
		this.width = width;
		this.outputwidgets = outputwidgets;
	}
	
	
	public MainPanel addOutputWidgets(UIOutputWidget...elems) {
		for (UIOutputWidget uiOutputWidget : elems) {
			outputwidgets.add(uiOutputWidget);
		}
		return this;
	}
	
	
	public MainPanel addOutputWidget(UIOutputWidget elem) {
		outputwidgets.add(elem);
		return this;
	}


	@Override
	public String getUICommand() {
		
		StringBuilder str=new StringBuilder();
		str.append("mainPanel(\n");
		
		for (int i = 0; i < outputwidgets.size(); i++) {
			
			String elemcmd=outputwidgets.get(i).getUICommand();
			if(i<(outputwidgets.size()-1)) {
					if(!elemcmd.endsWith(","))
						elemcmd=elemcmd+",";
				}
				else if(i==(outputwidgets.size()-1)) {
					if(elemcmd.endsWith(","))
						elemcmd=elemcmd.substring(0, elemcmd.length()-1);
				}
				str.append("     "+elemcmd+"\n");
		}
		if(width!=null)
			str.append(", width = "+String.valueOf(width));

		str.append(")\n");
		return str.toString();
	}
	
	public static MainPanel createNew(UIOutputWidget...elems) {
		MainPanel panel=new MainPanel();
		return panel.addOutputWidgets(elems);
	}

}
