import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class RoundCalc {
	public int[][] roundMat, gMat, tObj;
	public int nofObj;
	boolean hasObj;

	public RoundCalc() {

		// TODO Auto-generated constructor stub
	}

	int isFound(int[][] a, int nofObj) {
		gMat = a;
		this.nofObj = nofObj;
		int diameter = 33, obj = 0;
		int[] area;
		double roundness, maxRound = 0;
		area = findArea();
		for (int i = 1; i <= this.nofObj; i++) {
			roundness = (4 * area[i]) / (Math.PI * (diameter * diameter));
			if (roundness > maxRound) {
				maxRound = roundness;
				System.out.println(maxRound);
				System.out.println(i);
				obj = i;
			}
		}
		if (maxRound >= 0.9) {
			return obj;
		} else
			return 0;
	}

	int[] findArea() {

		int[] area = new int[this.nofObj + 1];

		for (int i = 0; i < this.nofObj + 1; i++) {
			area[i] = 0;

		}
		for (int i = 0; i < gMat.length - 1; i++) {
			for (int j = 0; j < gMat[0].length - 1; j++) {
				if (gMat[i][j] != 0) {
					hasObj = true;
					int k = gMat[i][j];

					area[k] = area[k] + 1;
				}
			}
		}
		return area;
	}

	Mat isFound(List<MatOfPoint> contours, List<MatOfPoint> hullmop, Mat image) {

		double area = 0,roundness=0;
		double e ,me=9999,maxR=0 ;

		Rect result = new Rect();
		
		for (int i = 0; i < contours.size(); i++) {
			area = Imgproc.contourArea(contours.get(i));
			if (area > 1000) {
				Rect rect = Imgproc.boundingRect(contours.get(i));
//				Core.rectangle(image, new Point(rect.x, rect.y), new Point(
//						rect.x + rect.width, rect.y + rect.height),
//						new Scalar(0, 255, 0));
//				
				
				int a, b,c;
				a = c= rect.width;
				b = rect.height;
				if(a<b){
					c=b;
				}
				e= Math.sqrt(Math.abs(a * a - b * b))/c;
				roundness=(4*area)/(Math.PI*b*a);
				
			//System.out.println(" area: "+area+" e :"+e+" roundness :"+roundness);	
				
				if(e<=.45&&roundness>=0.9){
					
					result=rect;
					System.out.println(" area: "+area+" e :"+e+" roundness :"+roundness);	
					Core.rectangle(image, new Point(result.x, result.y), new Point(
							result.x + result.width, result.y + result.height),
							new Scalar(0, 255, 0));
					//System.out.println(" area: "+area+" e :"+e+" roundness :"+roundness);
						
				}
			}
			
		}
	
		
			
		
		// System.out.println(maxRound);

		// System.out.println(rect.x
		// +","+rect.y+","+rect.height+","+rect.width);

		// Core.rectangle(image, new Point(result.x, result.y), new
		// Point(result.x
		// + result.width, result.y + result.height),
		// new Scalar(0, 255, 0));

		return image;
	}
}
