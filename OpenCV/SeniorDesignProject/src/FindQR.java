import java.util.HashMap;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.*;

class FindQR {
	public HashMap<String, Double> run(String inFile, String templateFile, int match_method) {
	    System.out.println("\nRunning Features2D...\n");
	    
	    
	    
	    Mat img = Imgcodecs.imread(getClass().getResource(inFile).getPath());
	    Mat templ = Imgcodecs.imread(getClass().getResource(templateFile).getPath());	  
	    
	    
	    if(img.empty() || templ.empty())
	    {
	        System.out.println("Can't read one of the images\n");
	        return null;
	    }
	    
	    
//	    KeyPoint[] keypoints = new KeyPoint[1];
//	    keypoints[0] = templ.;
//	    MatOfKeyPoint keypoint = new MatOfKeyPoint();
//	    keypoint.fromArray(keypoints[0]);
//	    
//	    
//	    FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
//	    DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
//	    
//	    Mat descriptors = new Mat();
//	    detector.detect(img,templ);
//	    extractor.compute(img,templ,descriptors);
//	    
//	    
//	    System.out.println("extractor.toString() = " + extractor.toString());
//	    System.out.println(extractor.descriptorSize());
//	    System.out.println(extractor.toString());
	    
	    
	    
	    // / Create the result matrix
	    int result_cols = img.cols() - templ.cols() + 1;
	    int result_rows = img.rows() - templ.rows() + 1;
	    Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
	
	    // / Do the Matching and Normalize
	    Imgproc.matchTemplate(img, templ, result, match_method);
	    Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
	
	    // / Localizing the best match with minMaxLoc
	    MinMaxLocResult mmr = Core.minMaxLoc(result);
	
	    Point matchLoc;
	    if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
	        matchLoc = mmr.minLoc;
	    } else {
	        matchLoc = mmr.maxLoc;
	    }
	
	    // / Show me what you got
	    Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),
	            matchLoc.y + templ.rows()), new Scalar(0, 255, 0));
	
	    //coordsResult stores the dimensions of the large image (largeWidth, largeHeight)
	    //as well as the location of the middle of the QR code (xCoord, yCoord)
	    HashMap<String, Double> coordsResult = new HashMap<String, Double>();
	    coordsResult.put("largeWidth", (double) img.cols());
	    coordsResult.put("largeHeight", (double) img.rows());
	    coordsResult.put("xCoord", matchLoc.x + templ.cols() / 2);
	    coordsResult.put("yCoord", matchLoc.y + templ.rows() / 2);
	    
	    return coordsResult;	    
	}
}