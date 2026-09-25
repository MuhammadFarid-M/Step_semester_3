package abstraction_and_interfaces.assigment_problems;

public class BasicDrawingCanvas {

    abstract static class Shape {

        private static final int SHAPE_ID_BASE = 1000;
        private static int shapesCreated = 0;

        private final String shapeId;

        protected double scaleX;
        protected double scaleY;

        public Shape() {
            shapesCreated++;
            this.shapeId = "SHP-" + (SHAPE_ID_BASE + shapesCreated);
            this.scaleX = 1.0;
            this.scaleY = 1.0;
        }

        public abstract double calculateArea();

        void scale(double factor) {
            scale(factor, factor);
        }

        void scale(double xFactor, double yFactor) {
            if (xFactor <= 0 || yFactor <= 0) {
                return;
            }
            scaleX *= xFactor;
            scaleY *= yFactor;
        }

        public String getShapeId() {
            return shapeId;
        }
    }

    static class CircleShape extends Shape {

        private final double radius;

        public CircleShape(double radius) {
            this.radius = radius;
        }

        @Override
        public double calculateArea() {
            return Math.PI * (radius * scaleX) * (radius * scaleY);
        }
    }

    static class SquareShape extends Shape {

        private final double side;

        public SquareShape(double side) {
            this.side = side;
        }

        @Override
        public double calculateArea() {
            return (side * scaleX) * (side * scaleY);
        }
    }

    static void printArea(Shape shape) {
        System.out.println(shape.getShapeId() + " area -> "
                + Math.round(shape.calculateArea() * 100) / 100.0);
    }

    public static void main(String[] args) {
        CircleShape circle = new CircleShape(5.0);
        System.out.println("circle.calculateArea() -> " + circle.calculateArea()
                + " (~" + Math.round(circle.calculateArea() * 100) / 100.0 + ")");

        SquareShape square = new SquareShape(4.0);
        System.out.println("square.calculateArea() -> " + square.calculateArea());

        square.scale(2.0);
        System.out.println("after square.scale(2.0) -> " + square.calculateArea());

        square.scale(0.5, 2.0);
        System.out.println("after square.scale(0.5, 2.0) -> " + square.calculateArea());

        System.out.println();
        printArea(circle);
        printArea(square);

        System.out.println();
        System.out.println("new Shape() will not compile: Shape is abstract, so only a");
        System.out.println("specific shape with a real area formula can ever be created.");
    }
}
