package si.mabu.harmony;
/**
 * Insert data
 * <p>
 * 
 * @author Matej Crepinsek
 * @version 1
 * 
 *          <h3>License</h3>
 * 
 *          Copyright (c) 2011 by Matej Crepinsek. <br>
 *          All rights reserved. <br>
 * 
 *          <p>
 *          Redistribution and use in source and binary forms, with or without
 *          modification, are permitted provided that the following conditions
 *          are met:
 *          <ul>
 *          <li>Redistributions of source code must retain the above copyright
 *          notice, this list of conditions and the following disclaimer.
 *          <li>Redistributions in binary form must reproduce the above
 *          copyright notice, this list of conditions and the following
 *          disclaimer in the documentation and/or other materials provided with
 *          the distribution.
 *          <li>Neither the name of the copyright owners, their employers, nor
 *          the names of its contributors may be used to endorse or promote
 *          products derived from this software without specific prior written
 *          permission.
 *          </ul>
 *          <p>
 *          THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *          "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *          LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *          FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *          COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *          INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *          BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *          LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *          CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *          LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *          ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *          POSSIBILITY OF SUCH DAMAGE.
 * 
 */


import java.util.ArrayList;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.es.ES1p1sAlgorithm;
import org.um.feri.ears.algorithms.random.RandomWalkAMAlgorithm;
import org.um.feri.ears.algorithms.random.RandomWalkAlgorithm;
import org.um.feri.ears.algorithms.tlbo.TLBOAlgorithm;
import org.um.feri.ears.benchmark.RatingBenchmark;
import org.um.feri.ears.benchmark.RatingRPUOed2;
import org.um.feri.ears.rating.Player;
import org.um.feri.ears.rating.ResultArena;
import org.um.feri.ears.util.Util;


/**
 * @author Administrator
 *
 */
public class MainBenchMarkTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Util.rnd.setSeed(System.currentTimeMillis());
        RatingBenchmark.debugPrint = true; //prints one on one results
        ArrayList<Algorithm> players = new ArrayList<Algorithm>();
        players.add(new RandomWalkAlgorithm());  
       // players.add(new RandomWalkAMAlgorithm());  
        players.add(new ES1p1sAlgorithm());
        players.add(new TLBOAlgorithm());
        players.add(new HarmonySearch());
        ResultArena ra = new ResultArena(100);
        RatingRPUOed2 suopm = new RatingRPUOed2();
        for (Algorithm al:players) {
          ra.addPlayer(al.getID(), 1500, 350, 0.06,0,0,0);
          suopm.registerAlgorithm(al);
        }
        suopm.run(ra, 50);
        ArrayList<Player> list = new ArrayList<Player>();
        list.addAll(ra.recalcRangs());
        for (Player p: list) System.out.println(p);

    }

}
