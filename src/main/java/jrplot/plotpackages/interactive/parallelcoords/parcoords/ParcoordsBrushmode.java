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
package jrplot.plotpackages.interactive.parallelcoords.parcoords;

public enum ParcoordsBrushmode {
	
	None{
		@Override
		public String getMode() {
			return "None";
		}
	},
	AXES1D{
		@Override
		public String getMode() {
			return "1D-axes";
		}
	},
	STRUMS2D{
		@Override
		public String getMode() {
			return "2D-strums";
		}
	},
	AXES1DMULTI{
		@Override
		public String getMode() {
			return "1D-axes-multi";
		}
	},
	ANGULAR{
		@Override
		public String getMode() {
			return "angular";
		}
	};
	

	
	
	
	public String getMode() {
		return getMode();
	}
}
