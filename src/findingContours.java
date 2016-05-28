import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class findingContours {
	
	public List<MatOfPoint> matCont,matHull;
	
	Mat dcontours(Mat image){
	    // Load the library
		int x=0;
		

	    // Consider the image for processing
	   
		 ImgWindow.newWindow(image);
	    Mat imageBlurr = new Mat(image.size(), Core.DEPTH_MASK_8U);
	    Mat imageA = new Mat(image.size(), Core.DEPTH_MASK_ALL);
//	    
	    Imgproc.GaussianBlur(image, imageBlurr, new Size(5,5), 0);
	    Imgproc.threshold(imageBlurr, image, 90, 366, Imgproc.THRESH_BINARY);
	    // Imgproc.adaptiveThreshold(imageBlurr, image, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,3, 1);
	    ImgWindow.newWindow(image,"threshold");
	 	    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();    
	    Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	    
	    ImgWindow.newWindow(image);
	    //System.out.println(contours.size());
	    
	    List<MatOfPoint> getHull=new ArrayList<MatOfPoint>(contours.size());
	   //Imgproc.drawContours(image, contours, 1, new Scalar(255,0,255));
	    
	   getHull= getConvexHull(contours);
	   
	  
	   Mat hullImg=drawHull(image, contours, getHull);
	   
	   ImgWindow.newWindow(hullImg);
	   //System.out.println(round);
	   matCont=contours;
	   matHull=getHull;
	    
	   return image;
	    }

	public List<MatOfPoint> getConvexHull( List<MatOfPoint> contours){
		// Find the convex hull
        List<MatOfInt> hull = new ArrayList<MatOfInt>();
        for(int i=0; i < contours.size(); i++){
            hull.add(new MatOfInt());
        }
        for(int i=0; i < contours.size(); i++){
            Imgproc.convexHull(contours.get(i), hull.get(i));
        }

        // Convert MatOfInt to MatOfPoint for drawing convex hull

        // Loop over all contours
        List<Point[]> hullpoints = new ArrayList<Point[]>();
        for(int i=0; i < hull.size(); i++){
            Point[] points = new Point[hull.get(i).rows()];

            // Loop over all points that need to be hulled in current contour
            for(int j=0; j < hull.get(i).rows(); j++){
                int index = (int)hull.get(i).get(j, 0)[0];
                points[j] = new Point(contours.get(i).get(index, 0)[0], contours.get(i).get(index, 0)[1]);
            }

            hullpoints.add(points);
        }

        // Convert Point arrays into MatOfPoint
        List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>();
        for(int i=0; i < hullpoints.size(); i++){
            MatOfPoint mop = new MatOfPoint();
            mop.fromArray(hullpoints.get(i));
            hullmop.add(mop);
        }

        return hullmop;
	}
	Mat drawHull(Mat binaryImage,List<MatOfPoint> contours,List<MatOfPoint> hullmop){
		 Mat overlay = new Mat(binaryImage.size(), CvType.CV_8UC3);
	        Scalar color = new Scalar(0, 255, 0);   // Green
	        for(int i=0; i < contours.size(); i++){
	            //Imgproc.drawContours(overlay, contours, i, color);
	            Imgproc.drawContours(overlay, hullmop, i, color);
	        }
	        return overlay;
	}
}
