public class NBody {

    public static int n;

    public static double readRadius(String dir){
        In in = new In(dir);
        n = in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String dir){
        double xp,yp,xv,yv,mass;
        String gif;

        In in = new In(dir);
        n = in.readInt();
        in.readDouble(); //skip radius

        Body[] bodies = new Body[n];
        for (int i = 0; i < n; i++){
            xp = in.readDouble();
            yp = in.readDouble();
            xv = in.readDouble();
            yv = in.readDouble();
            mass = in.readDouble();
            gif = in.readString();
            bodies[i] = new Body(xp,yp,xv,yv,mass,gif);
        }
        return bodies;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please supply the parameters for Nbody");
        }

        double T, dt, Radius;
        Body[] bodies;
        String file;
        String bg = "images/starfield.jpg";

        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        file = args[2];
        Radius = readRadius(file);
        bodies = readBodies(file);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-Radius, Radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, bg);

        for (Body b : bodies){
            b.draw();
        }
        StdDraw.show();

        for (int time = 0; time <= Math.round(T); time+=dt){
            double[] xForces = new double[n];
            double[] yForces = new double[n];
            for (int i = 0; i < n; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, bg);
            for (int i = 0; i < n; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}