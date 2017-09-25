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
package jrplot.rbinders.components.matrix;

import java.util.ArrayList;

public abstract class DataMatrix<T> {
	
	
	
	protected String[] rownames;
	protected String[] columnnames;
	private T data;
	
	
	//public DataMatrix(){}

	public DataMatrix(T datamatrix){
		this.data=datamatrix;
	}
	
	public DataMatrix(T datamatrix, String[] rownames, String[] columnnames){
		this.data=datamatrix;
		this.rownames=rownames;
		this.columnnames=columnnames;
	}
	
	public DataMatrix(T datamatrix, ArrayList<String> rownames, ArrayList<String> columnnames){
		this.data=datamatrix;
		this.rownames=rownames.toArray(new String[rownames.size()]);
		this.columnnames=columnnames.toArray(new String[columnnames.size()]);
	}
	

	public String[] getRownames() {
		return rownames;
	}

	public void setRownames(String[] rownames) {
		this.rownames = rownames;
	}

	public String[] getColumnnames() {
		return columnnames;
	}

	public void setColumnnames(String[] columnnames) {
		this.columnnames = columnnames;
	}

	public void setMatrix(T data) {
		this.data = data;
	}

	public T getMatrix(){
		return data;
	}
	
}
