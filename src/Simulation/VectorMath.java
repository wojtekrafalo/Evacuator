package Simulation;

import com.sun.javafx.geom.Vec2d;

/**
 * Project: Evacuator
 * Name: VectorMath.java
 * Purpose: Works as simple vector operations library.
 *
 * @author Bartosz Gorski "Alvirx"
 * @version 0.2 18.12.2018
 */
class VectorMath
{
    /**
     * Adds two vectors by adding their corresponding values.
     * @param A - first vector
     * @param B - second vector
     * @return calculated vector
     */
    static Vec2d add(Vec2d A, Vec2d B)
    {
        return new Vec2d(A.x+B.x, A.y+B.y);
    }

    /**
     * Multiplies vector A by number a. (multiplies each field of A by a)
     * @param A - vector to be multiplied
     * @param a - number by which vector will be multiplied
     * @return calculated vector
     */
    static Vec2d multiply(Vec2d A, double a)
    {
        return new Vec2d(A.x*a, A.y*a);
    }

    /**
     * Multiplies vector A by number a. (multiplies each field of A by a)
     * @param a - vector to be multiplied
     * @param A - number by which vector will be multiplied
     * @return calculated vector
     */
    static Vec2d multiply(double a, Vec2d A)
    {
        return multiply(A, a);
    }

    /**
     * Calculates cosinus value between two vectors.
     * @param A - first vector
     * @param B - second vector
     * @return cosinus between A an B
     */
    static double getCos(Vec2d A, Vec2d B)
    {
        double x = (norm(A) * norm(B));
        if(x == 0)
        {
            throw new IllegalArgumentException("Division by zero");
        }
        return dot(A, B)/x;
    }

    /**
     * Calculates sinus value between two vectors.
     * @param A - first vector
     * @param B - second vector
     * @return sinus between A an B
     */
    static double getSin(Vec2d A, Vec2d B)
    {
        double cos = getCos(A, B);
        return Math.sqrt(1 - Math.pow(cos, 2));
    }

    /**
     * Calculates dot product of two vectors which is the same as transposition of first
     * vector multiplied by second vector.
     * @param A - first vector
     * @param B - second vector
     * @return calculated
     */
    static double dot(Vec2d A, Vec2d B)
    {
        return A.x*B.x + A.y*B.y;
    }

    /**
     * Calculates the length of vector with Pythagoras formula.
     * @param A - vector to be measured
     * @return length of vector
     */
    static double norm(Vec2d A)
    {
        return Math.sqrt(Math.pow(A.x, 2) + Math.pow(A.y, 2));
    }

}