import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.Quick3way;
import java.util.Iterator;

/**
 * Created by zt on 06/06/2017.
 */
public class BruteCollinearPoints {

    private ResizingArrayQueue<LineSegment> _segments = new ResizingArrayQueue<LineSegment>();
    private int minCount = 4;

    public BruteCollinearPoints(Point[] pointsArray) {
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
        for(int i=0; i<N-3; i++) {
            for(int j=i+1; j<N-2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        if (points[i].slopeOrder().compare(points[j],points[k]) == 0 &&
                                points[i].slopeOrder().compare(points[k],points[l]) == 0) {
//                            System.out.println(""+points[i]+"->"+points[j]+"->"+points[k]+"->"+points[l]);
                            _segments.enqueue(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return _segments.size();
    }

    public LineSegment[] segments() {
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
//        StdDraw.show(0);
//        StdDraw.setPenRadius(0.01);  // make the points a bit larger
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show(0);
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        System.out.println(collinear.numberOfSegments());
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
    }

}
