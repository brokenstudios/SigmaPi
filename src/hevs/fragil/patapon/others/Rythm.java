package hevs.fragil.patapon.others;

import java.util.TimerTask;

import com.badlogic.gdx.utils.Timer;

public class Rythm {
  Timer t;
// TODO What is timer? Baby explain me
  public Rythm() {
	  t = new Timer();
	  t.scheduleTask(new MyAction(), 0, 1*1000);
  }

  class MyAction extends TimerTask {
    int nbrRepetitions = 3;

    public void run() {
      if (nbrRepetitions > 0) {
        System.out.println("suce mon gland salaud de FR!");
        nbrRepetitions--;
      } else {
        System.out.println("Terminé!");
        t.cancel();
        }
      }
    }

}
