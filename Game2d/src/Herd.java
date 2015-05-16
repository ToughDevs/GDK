/*
 * Copyright (c) 2015 Nikolai Belochub
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

public class Herd {
    float herbality;
    float stomachCapacityMax;
    float stomachCapacity;
    int partDigestedAtOnce;
    int digestingTime;
    float mass;
    float energy;
    float massToReproduce;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return (   (obj instanceof Herd)
                && (herbality == ((Herd) obj).herbality)
                && (stomachCapacityMax == ((Herd) obj).stomachCapacityMax)
                && (digestingTime == ((Herd) obj).digestingTime)
                && (massToReproduce == ((Herd) obj).massToReproduce)
                && (partDigestedAtOnce == ((Herd) obj).partDigestedAtOnce));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += herbality * 5000;
        result += stomachCapacityMax * 700;
        result += digestingTime * 13;
        result += massToReproduce * 3700;
        result += partDigestedAtOnce * 3;
        return result;
    }

    void digest() {
        if (stomachCapacity != 0) {
            stomachCapacity -= stomachCapacityMax / partDigestedAtOnce;
            if (stomachCapacity < 0) {
                stomachCapacity = 0;
            }
        }
    }
}
