package Imagenes;

import java.awt.Point;

public class CalcRecta {
	float a;
	float b;
	
	CalcRecta(Point p1, Point p2){
		this.a = ((float)(p1.y - p2.y))/(p1.x - p2.x);
		this.b = ((float)p1.y) - a * p1.x; 
		
		System.out.println("Ecuación de la Recta");
		System.out.println("Y = "+a+"X"+b);
	}
	
	public int calcVout(int vIn){
		return (int)(a*(float)(vIn) + b);
	}
}
