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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringColumnRowNames extends ColumnRowNames<List<String>, String>{

	
	
	public StringColumnRowNames() {
		super();
	}

	public StringColumnRowNames(List<String> input) {
		super(input);
	}
	

	public void set(String[] names){
		this.rownames=Arrays.asList(names);
	}
	
	public void setNames(String... names){
		set(names);
	}


	
	public StringColumnRowNames add(String[] data){
		set(data);
		return this;
	}
	
	public StringColumnRowNames addInputs(String... data){
		set(data);
		return this;
	}
	
	
	public StringColumnRowNames add(ArrayList<String> data){
		setNames(data);
		return this;
	}
	
	
	public static StringColumnRowNames create(String[] data){
		return new StringColumnRowNames().add(data);
	}
	
	public static StringColumnRowNames createFromInputs(String...data){
		return new StringColumnRowNames().add(data);
	}
	
	public static StringColumnRowNames create(ArrayList<String> data){
		return new StringColumnRowNames(data);
	}

	@Override
	public String[] getRowNamesArray() {
		return rownames.toArray(new String[rownames.size()]);
	}

	

}
