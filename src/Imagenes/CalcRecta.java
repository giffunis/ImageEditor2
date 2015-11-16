package Imagenes;

import java.awt.Point;

public class CalcRecta {
	int a;
	int b;
	
	CalcRecta(Point p1, Point p2){
		this.a = (int)((p1.y - p2.y)*1.0/(p1.x - p2.x));
		this.b = p1.y - a * p1.x; 
	}
	
	public int calcVout(int vIn){
		return (int)(a*(double)(vIn) + b);
	}
}
