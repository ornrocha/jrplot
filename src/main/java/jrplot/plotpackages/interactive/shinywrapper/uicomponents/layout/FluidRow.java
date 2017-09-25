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

import java.util.ArrayList;

import jrplot.plotpackages.interactive.shinywrapper.uicomponents.inputwidget.SliderInput;

public class FluidRow implements UIInputWidgetLayout{
	
	
	private ArrayList<ColumnFluidRow> columselems;
	private int numbergridcolumns=0;
	
	public FluidRow() {
		columselems=new ArrayList<>();
	}
	
	
	
	
	public FluidRow addNewColumnElements(int columnwidth, int offset, UIInputWidgetLayout...elems) throws SizeOverflowException {
		
		if(offset>0 && offset<12) {
			columselems.add(new ColumnFluidRow(columnwidth, offset, elems));
		}
		else
			columselems.add(new ColumnFluidRow(columnwidth, elems));
		
		numbergridcolumns=numbergridcolumns+columnwidth;
		
		if(numbergridcolumns>12)
			throw new SizeOverflowException("The max number of the grid width of the total of the columns must be equal or less than 12");
		
		return this;
	}
	
	
	public FluidRow addNewColumnElements(int columnwidth, UIInputWidgetLayout...elems) throws SizeOverflowException {
		return addNewColumnElements(columnwidth, -1, elems);
	}



	

	@Override
	public String getUICommand() {
		
		StringBuilder str=new StringBuilder();
		str.append("fluidRow(\n");
		
		for (int i = 0; i < columselems.size(); i++) {
			
			ColumnFluidRow column=columselems.get(i);
			
			str.append("  column("+String.valueOf(column.getGridWidth())+",\n");
			if(column.getOffset()>0)
				str.append("offset = "+String.valueOf(column.getOffset())+",\n");
			
			ArrayList<UIInputWidgetLayout> elems =column.getElements();
			for (int j = 0; j < elems.size(); j++) {
				
				String elemcmd=elems.get(j).getUICommand();
				if(j<(elems.size()-1)) {
					if(!elemcmd.endsWith(","))
						elemcmd=elemcmd+",";
				}
				else if(j==(elems.size()-1)) {
					if(elemcmd.endsWith(","))
						elemcmd=elemcmd.substring(0, elemcmd.length()-1);
				}
				str.append("     "+elemcmd+"\n");
			}
			
			str.append("  )");
			if(i<(columselems.size()-1))
				str.append(",");
			str.append("\n");
			
		}
		
		str.append(")\n");
		
		
		
		return str.toString();
	}
	
	
	
	public static void main(String[] args) throws SizeOverflowException {
		
		FluidRow row=new FluidRow();
		
		row.addNewColumnElements(4, null, 
				SliderInput.createNew("ads", "sda", 0, 50, 3, 1),
				SliderInput.createNew("linktox","Slider", 0, 50, 4, 1));
		

		
		System.out.println(row.getUICommand());
	}
	
   
}
