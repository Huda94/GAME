package game.game;


import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

public class MainThread extends  Thread{

    private int FPS=30;
    private double averageFPS;
    private SurfaceHolder surfaceholder;
    private GamePanel gamepanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread (SurfaceHolder surfaceholder, GamePanel gamePanel){
        super();
        this.surfaceholder = surfaceholder;
        this.gamepanel= gamePanel;

    }
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime=0;
        int framecount =0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceholder.lockCanvas();
                synchronized (surfaceholder){
                    this.gamepanel.update();
                    this.gamepanel.draw(canvas);

                }
            }catch (Exception e){}
            timeMillis = (System.nanoTime() - startTime)/100000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch (Exception e){}
            totalTime +=System.nanoTime()-startTime;
            framecount++;
            if(framecount == FPS)
            {
                averageFPS = 1000/((totalTime/framecount)/100000);
                framecount =0;
                totalTime =0;
                System.out.println(averageFPS);
            }
        }

    }
}
