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

public class ColumnFluidRow {

	
	private int width=0; //The grid width of the column (must be between 1 and 12)
	private int offset=0; //The number of columns to offset this column from the end of the previous column
	private ArrayList<UIInputWidgetLayout> elems=null;
	
	
	
	
	public ColumnFluidRow(int gridwidth) {
		this.elems=new ArrayList<>();
		this.width = gridwidth;
	}




	public ColumnFluidRow(int width, int offset) {
		this.elems=new ArrayList<>();
		this.width = width;
		this.offset = offset;
	}
	
	public ColumnFluidRow(int width,UIInputWidgetLayout[] widgetelems) throws SizeOverflowException {
		this.elems=new ArrayList<>();
		this.width = width;
		addElements(widgetelems);
	}
	
	public ColumnFluidRow(int width, int offset, UIInputWidgetLayout[] widgetelems) throws SizeOverflowException {
		this.elems=new ArrayList<>();
		this.width = width;
		this.offset = offset;
		addElements(widgetelems);
	}
	
	
	public ColumnFluidRow addElements(UIInputWidgetLayout...widgetelems) throws SizeOverflowException {
		for (int i = 0; i < widgetelems.length; i++) {
			elems.add(widgetelems[i]);
		}
		if(elems.size()>12)
			throw new SizeOverflowException("The number of column elements must be equal or less than 12");
		else if(elems.size()>width)
			throw new SizeOverflowException("The number of elements exceded the grid width");
		
		return this;
	}
	
	public ColumnFluidRow addElement(UIInputWidgetLayout widgetelem) throws SizeOverflowException {

		elems.add(widgetelem);

		if(elems.size()>12)
			throw new SizeOverflowException("The number of column elements must be equal or less than 12");
		else if(elems.size()>width)
			throw new SizeOverflowException("The number of elements exceded the grid width");
		
		return this;
	}

	public int getGridWidth() {
		return width;
	}

	public int getOffset() {
		return offset;
	}

	public ArrayList<UIInputWidgetLayout> getElements() {
		return elems;
	}
	
	

}
