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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringDataColumn extends DataColumn<List<String>, String>{

	
	
	public StringDataColumn(String columnname) {
		super(columnname, VariableType.CHARACTER);
	}

	public StringDataColumn(String columnname, List<String> input) {
		super(columnname, VariableType.CHARACTER, input);
	}
	
	
	protected StringDataColumn(String columnname, VariableType type) {
		super(columnname, type);
	}
	
	protected StringDataColumn(String columnname, VariableType type, List<String> input) {
		super(columnname, type, input);
	}
	

	public void set(String[] data){
		this.datalist=Arrays.asList(data);
	}
	
	public void setInputs(String... data){
		set(data);
	}

	public void set(ArrayList<String> data){
		setData(data);
	}
	
	public StringDataColumn add(String[] data){
		set(data);
		return this;
	}
	
	public StringDataColumn addInputs(String... data){
		set(data);
		return this;
	}
	
	
	public StringDataColumn add(ArrayList<String> data){
		setData(data);
		return this;
	}
	
	
	public static StringDataColumn create(String varname, String[] data){
		return new StringDataColumn(varname).add(data);
	}
	
	public static StringDataColumn createFromInputs(String varname, String...data){
		return new StringDataColumn(varname).add(data);
	}
	
	public static StringDataColumn create(String varname,ArrayList<String> data){
		return new StringDataColumn(varname).add(data);
	}

	@Override
	public String[] getDataArray() {
		return datalist.toArray(new String[datalist.size()]);
	}

}
