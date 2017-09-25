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
package jrplot.rbinders.components.matrix.datatype;

import java.util.ArrayList;

import jrplot.rbinders.components.matrix.DataMatrix;

public class IntDataMatrix extends DataMatrix<int[][]>{


	public IntDataMatrix(int[][] datamatrix) {
		super(datamatrix);

	}

	public IntDataMatrix(int[][] datamatrix, ArrayList<String> rownames, ArrayList<String> columnnames) {
		super(datamatrix, rownames, columnnames);
	}

	public IntDataMatrix(int[][] datamatrix, String[] rownames, String[] columnnames) {
		super(datamatrix, rownames, columnnames);
	}

	
       
	
	
	
}
