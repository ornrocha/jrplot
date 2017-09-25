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
import java.util.List;

public class FactorsDataColumn extends StringDataColumn{

	public FactorsDataColumn(String columnname) {
		super(columnname, VariableType.FACTOR);
	}

	public FactorsDataColumn(String columnname, List<String> input) {
		super(columnname, VariableType.FACTOR, input);
	}
	
	
	public static FactorsDataColumn create(String varname, String[] data){
		return (FactorsDataColumn) new FactorsDataColumn(varname).add(data);
	}
	
	/*public static FactorsDataColumn createFromInputs(String varname, String...data){
		return (FactorsDataColumn) new FactorsDataColumn(varname).add(data);
	}*/
	
	public static FactorsDataColumn create(String varname,ArrayList<String> data){
		return (FactorsDataColumn) new FactorsDataColumn(varname).add(data);
	}

}
