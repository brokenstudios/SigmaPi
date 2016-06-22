package hevs.fragil.patapon.music;

import ch.hevs.gdx2d.components.audio.SoundSample;

/**
 * Helps managing sound files synchronized with the music.
 */
public class SigmaSoundSample extends SoundSample{
	MusicFlag state = MusicFlag.NOTHING;
	
	public SigmaSoundSample(String file) {
		super(file);
	}

	public enum MusicFlag {
		TOSTOP, STOPPED, NOTHING, PLAYING, TOPLAY
	}
	
	public void playBistable(){
		if(state != MusicFlag.PLAYING)
			state = MusicFlag.TOPLAY;
	}
	public void stopBistable(){
		if(state != MusicFlag.STOPPED)
			state = MusicFlag.TOSTOP;
	}
	
	public void sync() {
		switch (state) {
			case TOSTOP:
				stop();
				state = MusicFlag.STOPPED;
				break;
			case TOPLAY:
				loop();
				state = MusicFlag.PLAYING;
				break;
			default:
				break;
		}
	}
	public void toggle(){
		switch (state) {
			case STOPPED:
				state = MusicFlag.TOPLAY;
				break;
			case PLAYING:
				state = MusicFlag.TOSTOP;
				break;
			default:
				break;
		}
	}
}

