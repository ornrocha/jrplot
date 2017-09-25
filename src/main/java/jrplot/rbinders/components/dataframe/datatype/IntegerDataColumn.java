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

public class IntegerDataColumn extends DataColumn<List<Integer>, Integer>{

	public IntegerDataColumn(String columnname) {
		super(columnname, VariableType.NUMERIC);
	}

	public IntegerDataColumn(String columnname, List<Integer> input) {
		super(columnname, VariableType.NUMERIC, input);
	}
	
	
	public int[] getDataArrayints(){
		return ArrayUtils.toPrimitive(getDataArray());
	}
	
	
	public void set(int[] data){
		this.datalist=Arrays.asList(ArrayUtils.toObject(data));
	}
	
	public void setInputs(int... data){
		set(data);
	}
	
	public void set(Integer[] data){
		this.datalist=Arrays.asList(data);
	}
	
	public void set(ArrayList<Integer> data){
		setData(data);
	}
	
	public IntegerDataColumn add(int[] data){
		set(data);
		return this;
	}
	
	public IntegerDataColumn addInputs(int... data){
		set(data);
		return this;
	}
	
	public IntegerDataColumn add(Integer[] data){
		set(data);
		return this;
	}
	
	public IntegerDataColumn add(ArrayList<Integer> data){
		setData(data);
		return this;
	}
	
	
	public static IntegerDataColumn create(String varname, int[] data){
		return new IntegerDataColumn(varname).add(data);
	}
	
	public static IntegerDataColumn createFromInputs(String varname, int...data){
		return new IntegerDataColumn(varname).add(data);
	}
	
	public static IntegerDataColumn create(String varname,Integer[] data){
		return new IntegerDataColumn(varname).add(data);
	}
	
	public static IntegerDataColumn create(String varname,ArrayList<Integer> data){
		return new IntegerDataColumn(varname).add(data);
	}

	@Override
	public Integer[] getDataArray() {
		return datalist.toArray(new Integer[datalist.size()]);
	}
	

}
