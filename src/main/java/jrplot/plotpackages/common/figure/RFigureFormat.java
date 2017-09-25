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
package jrplot.plotpackages.common.figure;

public enum RFigureFormat {

	
	PNG{
		@Override
		public String toString() {
			return "png";
		}
	},
	JPEG{
		@Override
		public String toString() {
			return "jpeg";
		}
	},
	BMP{
		@Override
		public String toString() {
			return "bmp";
		}
	},
	TIFF{
		@Override
		public String toString() {
			return "tiff";
		}
	},
	POSTSCRIPT{
		@Override
		public String toString() {
			return "postscript";
		}
	};

	
	@Override
	public String toString() {
		return toString();
	}
	
	
}
