package cz.zweistein.gw2.app.wvwexplorer;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MidiSynth {

	private int currentChannel = 0;
	private MidiChannel[] mc;

	public MidiSynth() throws MidiUnavailableException {
		super();

		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();

		this.mc = synth.getChannels();

		synth.loadAllInstruments(synth.getDefaultSoundbank());

	}

	private void playNote(int program, int noteNumber, int velocity) {

		
		mc[currentChannel].programChange(program);
		
		mc[currentChannel].noteOn(noteNumber, velocity);

		currentChannel++;
		if (currentChannel >= mc.length) {
			currentChannel = 0;
		}

	}
	
	public void captured() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				playNote(8, 60, 600);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
				
				playNote(8, 70, 1000);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
				
				playNote(8, 40, 750);
				
			}
		}).start();
		
	}
	
	public void lost() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				playNote(9, 60, 600);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
				
				playNote(9, 70, 1000);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
				
				playNote(9, 40, 750);
				
			}
		}).start();
		
		
	}

}
