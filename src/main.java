import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class main {
	public static void main(String args[]) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m =  Highgui.imread("14.jpg",Highgui.CV_LOAD_IMAGE_COLOR);
		Mat a = new Mat();
		Imgproc.cvtColor(m, a, Imgproc.COLOR_RGB2HSV);
		//ImgWindow.newWindow(m);
		// Split the image into different channels
		
		//split into hsv channel
		
		ArrayList<Mat> hsvChannels = new ArrayList<Mat>(3);
		Core.split(a, hsvChannels);
		ImgShow is = new ImgShow();
		Mat f = new Mat(a.width(), a.height(), 0);
		//ImgWindow.newWindow(hsvChannels.get(1), "Hsv");
		
		//thresholding
		a = is.aPixel(hsvChannels.get(1),150);		
		//apply bluirring
		
		Imgproc.GaussianBlur(a, f, new Size(9, 9), 2, 2);
		// ImgWindow.newWindow(f,"after blurring");
		
		//apply morphological operation
		
		int erosion_size = 20;
		int dilation_size = 20;
		Mat destination = new Mat(f.rows(), f.cols(), f.type());
		Mat d = new Mat(f.rows(), f.cols(), f.type());

		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
				new Size(2 * erosion_size + 1, 2 * erosion_size + 1));

		Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
				new Size(2 * dilation_size + 1, 2 * dilation_size + 1));
		Imgproc.dilate(f, destination, element);
		Imgproc.erode(destination, d, element1);
		ImgWindow.newWindow(d, "after morfology");
		
		// Imgproc.Canny(d, f, 70, 3);
		
		//ImgWindow.newWindow(is.aPixel(d, 90), "without circle");
		// ImgWindow.newWindow(is.dCircle(d),"circle");
		// ImgWindow.newWindow(is.plot(f,hsvChannels.get(2) ),"plot");
		
		//thresholding
		Mat bI = is.aPixel(d, 90);
//		Highgui.imwrite("test.jpg",bI);
//		//finding pixel
//		int[][] cl = is.getBmat(bI);
//		
//		//labeling image
//		LabelImage lbi = new LabelImage();
//		int[][] lb = lbi.labelImage(cl);
//		
		//calculating roundness
		RoundCalc rc=new RoundCalc();

		findingContours fc=new findingContours();
		
		Mat cont=fc.dcontours(bI);
	
		Mat result=rc.isFound(fc.matCont, fc.matHull, m);
		
		ImgWindow.newWindow(result,"result");
		
//		 for(int i=0;i<d.rows();i++){
//		 for(int j=0;j<d.cols();j++){
//		 System.out.print(lb[i][j]);
//		 }
//		 System.out.println();
//		 }
//		

	//	System.out.println(rc.isFound());
	}

}
