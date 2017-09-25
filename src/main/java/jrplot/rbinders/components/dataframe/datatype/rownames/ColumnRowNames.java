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
package jrplot.rbinders.components.dataframe.datatype.rownames;

import java.util.List;


public abstract class ColumnRowNames <T extends List<E>,E> {

	protected List<E> rownames;
	
	
	public ColumnRowNames() {}
	
	public ColumnRowNames(List<E> names){
		this.rownames=names;
	}
	
	
	
	public abstract E[] getRowNamesArray();
	
	@SuppressWarnings("unchecked")
	public T getNames(){
	    return (T) rownames;
	}
	
	public void setNames(T data){
    	this.rownames=data;
    }
	
	public int size() {
		if(rownames!=null)
			return rownames.size();
		return 0;
	}
}
