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

import org.apache.commons.lang3.ArrayUtils;

public class BooleanDataColumn extends DataColumn<List<Boolean>, Boolean>{

	
	
	public BooleanDataColumn(String columnname) {
		super(columnname, VariableType.LOGICAL);
	}

	public BooleanDataColumn(String columnname, List<Boolean> input) {
		super(columnname, VariableType.LOGICAL, input);
	}

	
	public boolean[] getDataArrayboolean(){
		return ArrayUtils.toPrimitive(getDataArray());
	}
	
	
	public void set(boolean[] data){
		this.datalist=Arrays.asList(ArrayUtils.toObject(data));
	}
	
	public void setInputs(boolean... data){
		set(data);
	}
	
	public void set(Boolean[] data){
		this.datalist=Arrays.asList(data);
	}
	
	public void set(ArrayList<Boolean> data){
		setData(data);
	}
	
	public BooleanDataColumn add(boolean[] data){
		set(data);
		return this;
	}
	
	public BooleanDataColumn addInputs(boolean... data){
		set(data);
		return this;
	}
	
	public BooleanDataColumn add(Boolean[] data){
		set(data);
		return this;
	}
	
	public BooleanDataColumn add(ArrayList<Boolean> data){
		setData(data);
		return this;
	}
	
	
	public static BooleanDataColumn create(String varname, boolean[] data){
		return new BooleanDataColumn(varname).add(data);
	}
	
	public static BooleanDataColumn createFromInputs(String varname, boolean...data){
		return new BooleanDataColumn(varname).add(data);
	}
	
	public static BooleanDataColumn create(String varname,Boolean[] data){
		return new BooleanDataColumn(varname).add(data);
	}
	
	public static BooleanDataColumn create(String varname,ArrayList<Boolean> data){
		return new BooleanDataColumn(varname).add(data);
	}

	@Override
	public Boolean[] getDataArray() {
		return datalist.toArray(new Boolean[datalist.size()]);
	}
}
