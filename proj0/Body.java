public class Body{
    static final double G = 6.67e-11;

    public double xxPos, yyPos, xxVel,yyVel,mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b){
        double D;
        D = Math.sqrt((b.xxPos-this.xxPos)*(b.xxPos-this.xxPos)+(b.yyPos-this.yyPos)*(b.yyPos-this.yyPos));
        return D;
    }

    public double calcForceExertedBy(Body b){
        double F;
        F = G*this.mass*b.mass/Math.pow(this.calcDistance(b),2);
        return F;
    }

    public double calcForceExertedByX(Body b){
        double Fx;
        Fx = this.calcForceExertedBy(b)*(b.xxPos-this.xxPos)/this.calcDistance(b);
        return Fx;
    }

    public double calcForceExertedByY(Body b){
        double Fy;
        Fy = this.calcForceExertedBy(b)*(b.yyPos-this.yyPos)/this.calcDistance(b);
        return Fy;
    }

    public double calcNetForceExertedByX(Body[] allBodys){
        double netx = 0;
        for (Body b : allBodys){
            if (!this.equals(b)) netx += this.calcForceExertedByX(b);
        }
        return netx;
    }

    public double calcNetForceExertedByY(Body[] allBodys){
        double nety = 0;
        for (Body b : allBodys){
            if (!this.equals(b)) nety += this.calcForceExertedByY(b);
        }
        return nety;
    }

    public void update(double time, double Fx, double Fy){
        double ax, ay;
        ax = Fx/this.mass;
        ay = Fy/this.mass;

        this.xxVel += ax*time;
        this.yyVel += ay*time;

        this.xxPos += this.xxVel*time;
        this.yyPos += this.yyVel*time;
    }

    public void draw(){
        String filename = "images/"+imgFileName;

        StdDraw.picture(xxPos, yyPos, filename);
    }
}