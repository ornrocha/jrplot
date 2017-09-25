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

public class DoubleDataColumn extends DataColumn<List<Double>, Double>{


	public DoubleDataColumn(String columnname) {
		super(columnname, VariableType.NUMERIC);
	}
	
	public DoubleDataColumn(String columnname, List<Double> input) {
		super(columnname, VariableType.NUMERIC, input);
	}

	
	public double[] getDataArraydoubles(){
		return ArrayUtils.toPrimitive(getDataArray());
	}
	
	
	public void set(double[] data){
		this.datalist=Arrays.asList(ArrayUtils.toObject(data));
	}
	
	public void setInputs(double... data){
		set(data);
	}
	
	public void set(Double[] data){
		this.datalist=Arrays.asList(data);
	}
	
	public void set(ArrayList<Double> data){
		setData(data);
	}
	
	
	public DoubleDataColumn add(double[] data){
		set(ArrayUtils.toObject(data));
		return this;
	}
	
	public DoubleDataColumn addInputs(double... data){
		set(data);
		return this;
	}
	
	public DoubleDataColumn add(Double[] data){
		set(data);
		return this;
	}
	
	public DoubleDataColumn add(ArrayList<Double> data){
		setData(data);
		return this;
	}
	
	
	public static DoubleDataColumn create(String varname, double[] data){
		return new DoubleDataColumn(varname).add(data);
	}
	
	public static DoubleDataColumn createFromInputs(String varname, double...data){
		return new DoubleDataColumn(varname).add(data);
	}
	
	public static DoubleDataColumn create(String varname,Double[] data){
		return new DoubleDataColumn(varname).add(data);
	}
	
	public static DoubleDataColumn create(String varname,ArrayList<Double> data){
		return new DoubleDataColumn(varname).add(data);
	}

	@Override
	public Double[] getDataArray() {
		return datalist.toArray(new Double[datalist.size()]);
	}
	
}
