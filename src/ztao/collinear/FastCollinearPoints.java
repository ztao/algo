import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.Quick3way;
import java.util.Iterator;
import java.util.Arrays;

/**
 * Created by zt on 06/06/2017.
 */
public class FastCollinearPoints {

    private ResizingArrayQueue<LineSegment> _segments = new ResizingArrayQueue<LineSegment>();
    private int minCount = 4;

    public FastCollinearPoints(Point[] pointsArray)     // finds all line segments containing 4 or more points
    {
        if (pointsArray == null)
            throw new IllegalArgumentException("Null points");
        int N = pointsArray.length;
        for (int i=0; i<N; i++) {
            if (pointsArray[i] == null)
                throw new IllegalArgumentException("Null point");
        }
        Point[] points = pointsArray.clone();
        Quick3way.sort(points);
        for (int i=1; i<N; i++) {
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException("Duplicate points");
        }
        if (N < minCount)
            return;
        for (int i=0; i<N; i++) {
            Point oriPoint = points[i];
            Point[] otherPoints = new Point[N-1];
            // otherPoints 为不包含 oriPoint 的 points
            for(int j=0; j < N; j++) {
                if(i>j) otherPoints[j] = points[j];
                if(i<j) otherPoints[j-1] = points[j];
            }
            // 将 otherPoints 的点根据与 oriPoint 的斜率来排序
            Arrays.sort(otherPoints, oriPoint.slopeOrder());

            int count = 0;
            int index = 0;
            double tempSlope = oriPoint.slopeTo(otherPoints[0]);
            for (int j=0; j<N-1; j++) {
                if (Double.compare(oriPoint.slopeTo(otherPoints[j]), tempSlope) == 0) {
                    count++;
                } else {
                    if (count >= 3) {
                        // 不小于原点的点形成的斜率没有打印过
                        if (otherPoints[index].compareTo(oriPoint) >= 0) {
//                            StdOut.print(oriPoint + " -> ");
//                            for (int k=index; k<j-1; k++){
//                                StdOut.print(otherPoints[k] + " -> ");
//                            }
//                            StdOut.println(otherPoints[j-1]);
                            _segments.enqueue(new LineSegment(oriPoint, otherPoints[j - 1]));
//                            oriPoint.drawTo(otherPoints[j-1]);
                        }
                    }
                    count = 1;
                    index = j;
                    tempSlope = oriPoint.slopeTo(otherPoints[j]);
                }
            }
            if (count >= 3) {
                // 不小于原点的点形成的斜率没有打印过
                if (otherPoints[index].compareTo(oriPoint) >=0){
//                    StdOut.print(oriPoint + " -> ");
//                    boolean repeat = false;
//                    for (int k=index; k<N-2; k++){
//                        StdOut.print(otherPoints[k] + " -> ");
//                        if (otherPoints[k].compareTo(oriPoint) < 0) {
//                            repeat = true;
//                        }
//                    }
//                    StdOut.println(otherPoints[N-2]);
//                    if (!repeat)
                    _segments.enqueue(new LineSegment(oriPoint,otherPoints[N-2]));
//                    oriPoint.drawTo(otherPoints[N-2]);
                }
            }
        }
    }
    public int numberOfSegments()        // the number of line segments
    {
        return _segments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        Iterator<LineSegment> itr = _segments.iterator();
        int _size = _segments.size();
        LineSegment[] _allSegments = new LineSegment[_size];
        for (int i =0; i<_size;i++) {
            _allSegments[i] = itr.next();
        }
        return _allSegments;
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i=0; i<n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x,y);
//        }
//
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        edu.princeton.cs.algs4.StdDraw.setPenRadius(0.01);  // make the points a bit larger
//        StdDraw.show();
//
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        System.out.println("共有" + collinear.numberOfSegments() + "条线段");
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
    }
}
