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
package jrplot.rbinders.components.dataframe.datatype;

import java.util.List;

public abstract class DataColumn<T extends List<E>, E> {
	
	
	protected String columnname;
	protected VariableType type;
	protected List<E> datalist;

	
	public DataColumn(String columnname, VariableType type){
		this.columnname=validateName(columnname);
		this.type=type;
	}
	
	public DataColumn(String columnname, VariableType type, List<E> input){
		this.columnname=validateName(columnname);
		this.type=type;
		this.datalist=input;
	}
	
	public abstract E[] getDataArray();

    @SuppressWarnings("unchecked")
	public T getData(){
    	return (T) datalist;
    }
    
    public void setData(T data){
    	this.datalist=data;
    }

	public String getColumnname() {
		return columnname;
	}
	
	
    public E getValueAt(int index) {
    	if(index>datalist.size())
    		return null;
    	else
    		return datalist.get(index);
    }
	
	/*@SuppressWarnings("unchecked")
	public E[] getDataArray(){
		if(datalist.size()>0)
			return (E[]) datalist.toArray((T[]) Array.newInstance(datalist.get(0).getClass(), datalist.size()));
		return null;
	}*/


	public void setColumnname(String columnname) {
		this.columnname = validateName(columnname);
	}

	protected String validateName(String columnname) {
		if(columnname.contains("-"))
			return columnname.replace("-", "_");
		else
			return columnname;
	}


	public VariableType getType() {
		return type;
	}
	
	
	
	

}
