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
package jrplot.plotpackages.interactive.shinywrapper.uicomponents.page;

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.mainfunctions.UIShinyComponent;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.SliderInput;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.layout.FluidRow;
import jrplot.plotpackages.interactive.shinywrapper.uicomponents.layout.SizeOverflowException;

public class FluidPage implements UIShinyPageLayout{

	
	protected String title=null;
	protected String theme=null;
	protected ArrayList<UIShinyComponent> listcomponents;
	

	
	public FluidPage() {
		listcomponents = new ArrayList<>();
	}
	

	public FluidPage(ArrayList<UIShinyComponent> listcomponents) {
		this.listcomponents = listcomponents;
	}

	public FluidPage(String title, ArrayList<UIShinyComponent> listcomponents) {
		this.title = title;
		this.listcomponents = listcomponents;
	}

	public FluidPage(String title, String theme, ArrayList<UIShinyComponent> listcomponents) {
		this.title = title;
		this.theme = theme;
		this.listcomponents = listcomponents;
	}


	public FluidPage setTitle(String title) {
		this.title = title;
		return this;
	}


	public FluidPage setTheme(String theme) {
		this.theme = theme;
		return this;
	}


	public FluidPage setListComponents(ArrayList<UIShinyComponent> listcomponents) {
		this.listcomponents = listcomponents;
		return this;
	}
	
	public void addComponents(UIShinyComponent...elems) {
		for (UIShinyComponent uiShinyComponent : elems) {
			listcomponents.add(uiShinyComponent);
		}
	}
	
	public FluidPage addComponent(UIShinyComponent elem) {
		listcomponents.add(elem);
		return this;
	}
	
	


	@Override
	public String getUICommand() {

		StringBuilder str=new StringBuilder();
		str.append(" fluidPage(\n");
		if(theme!=null)
			str.append("theme = \""+theme+"\",");
		if(title!=null)
			str.append("     titlePanel(\""+title+"\"),");


		for (int i = 0; i < listcomponents.size(); i++) {

			String elemcmd=listcomponents.get(i).getUICommand();
			if(i<(listcomponents.size()-1)) {
				if(!elemcmd.endsWith(","))
					elemcmd=elemcmd+",";
			}
			else if(i==(listcomponents.size()-1)) {
				if(elemcmd.endsWith(","))
					elemcmd=elemcmd.substring(0, elemcmd.length()-1);
			}
			str.append("     "+elemcmd+"\n");
		}

		str.append(")\n");
		return str.toString();
	}
	
	
	
	
	public static void main(String[] args) throws SizeOverflowException {
		
		FluidRow row=new FluidRow();
		
		row.addNewColumnElements(4, 
				SliderInput.createNew("ads", "sda", 0, 50, 3, 1),
				SliderInput.createNew("linktox","Slider", 0, 50, 4, 1));
		row.addNewColumnElements(4, 
				SliderInput.createNew("ads", "sda", 0, 50, 3, 1)
				);
		
		
		FluidPage page=new FluidPage().addComponent(row);
		System.out.println(page.getUICommand());
	}
	

}
